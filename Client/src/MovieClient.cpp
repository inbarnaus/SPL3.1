#include <stdlib.h>
#include <connectionHandler.h>
#include <iostream>
#include <boost/thread.hpp>

class ReadFromKeyboard{
private:
    int _id;
    boost::condition_variable* _m_cond;
    boost::mutex * _mutex;
    ConnectionHandler &_connectionHandler;
public:
    ReadFromKeyboard (int id, boost::condition_variable* m_cond ,  boost::mutex* mutex , ConnectionHandler &connectionHandler) : _id(id), _m_cond(m_cond),_mutex(mutex), _connectionHandler(connectionHandler){}
    
    void run(){
        while (_connectionHandler.isconnected()) {
            const short bufsize = 1024;
            char buf[bufsize];
            {
                if(_connectionHandler.isconnected()){
                    std::cin.getline(buf, bufsize);
                    std::string line(buf);
                    if (!_connectionHandler.sendLine(line)) {
                        std::cout << "Disconnected. Exiting...\n" << std::endl;
                        break;
                    }
                    if(line=="SIGNOUT"){
                        boost::mutex::scoped_lock lock(*_mutex);
                        _m_cond->wait(lock);
                    }
                }else{
                    break;
                }
            }
        }
    }
};

class ReadFromSocket{
private:
    int _id;
    boost::condition_variable* _m_cond;
    boost::mutex * _mutex;
    ConnectionHandler &_connectionHandler;
public:
    ReadFromSocket (int id, boost::condition_variable* m_cond ,  boost::mutex* mutex, ConnectionHandler &connectionHandler) : _id(id), _m_cond(m_cond),_mutex(mutex), _connectionHandler(connectionHandler){}
    
    void run(){
        while (_connectionHandler.isconnected()) {
            std::string answer="";
            while((_connectionHandler.isconnected()) && (answer=="")){
                if (!_connectionHandler.getLine(answer)) {
                    _m_cond->notify_all();
                    break;       
                }
            }
            std::cout << answer;
            if (answer=="ACK signout succeeded\n") {\
                _connectionHandler.close();
                _m_cond->notify_all();
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
    boost::condition_variable m_cond;
    boost::mutex mutex;
    ReadFromKeyboard task1(1, &m_cond , &mutex, connectionHandler);
    ReadFromSocket task2(2,  &m_cond , &mutex, connectionHandler);
    
    boost::thread th1(&ReadFromKeyboard::run, &task1);
    boost::thread th2(&ReadFromSocket::run, &task2);
    th1.join();
    th2.join();    
    return 0;
}
    



