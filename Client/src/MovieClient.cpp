#include <stdlib.h>
#include <connectionHandler.h>
#include <iostream>
#include <boost/thread.hpp>
    
class ReadFromKeyboard{
private:
    int _id;
    boost::mutex * _mutex;
    ConnectionHandler &_connectionHandler;
public:
    ReadFromKeyboard (int id, boost::mutex* mutex, ConnectionHandler &connectionHandler) : _id(id), _mutex(mutex), _connectionHandler(connectionHandler){}
    
    void run(){
        while (1) {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
                    std::string line(buf);
                    int len=line.length();
            {
                boost::mutex::scoped_lock lock(*_mutex);
                if (!_connectionHandler.sendLine(line)) {
                    std::cout << "Disconnected. Exiting...\n" << std::endl;
                    break;
                }
            }
                    // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
            std::cout << "Sent " << len+1 << " bytes to server" << std::endl;
        }
    }
};

class ReadFromSocket{
private:
    int _id;
    boost::mutex * _mutex;
    ConnectionHandler &_connectionHandler;
public:
    ReadFromSocket (int id, boost::mutex* mutex, ConnectionHandler &connectionHandler) : _id(id), _mutex(mutex), _connectionHandler(connectionHandler) {}
    
    void run(){
        while (1) {
            int len;
            // We can use one of three options to read data from the server:
            // 1. Read a fixed number of characters
            // 2. Read a line (up to the newline character using the getline() buffered reader
            // 3. Read up to the null character
            std::string answer;
            // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
            // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
            {
                boost::mutex::scoped_lock lock(*_mutex);
                if (!_connectionHandler.getLine(answer)) {
                    std::cout << "Disconnected. Exiting...\n" << std::endl;
                    break;
                }
            }
            
            len=answer.length();
            // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
            // we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
            answer.resize(len-1);
            std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
            if (answer == "bye") {
                std::cout << "Exiting...\n" << std::endl;
                break;
            }
        }
    }
};
    
int main(int argc, char *argv[]){
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    boost::mutex mutex;
    ReadFromKeyboard task1(1, &mutex, connectionHandler);
    ReadFromSocket task2(2, &mutex, connectionHandler);
    
    boost::thread th1(&ReadFromKeyboard::run, &task1); 
    boost::thread th2(&ReadFromSocket::run, &task2); 
    th1.join();
    th2.join();    
    return 0;
}

