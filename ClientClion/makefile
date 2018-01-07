# All Targets
all: BBclient

# Tool invocations
# Executable "BBclient"
BBclient: \
	bin/main.o \
	bin/connectionHandler.o \
	bin/echoClient[1].o
	@echo 'Building target: BBclient'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BBclient bin/main.o \
	bin/connectionHandler.o \
    bin/echoClient[1].o
	@echo 'Finished building target: BBclient'
	@echo ' '

# Depends on the source and header files
bin/main.o: src/main.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/main.o src/main.cpp

# Depends on the source and header files
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp
# Depends on the source and header files
bin/echoClient[1].o: src/echoClient[1].cpp
    g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/echoClient[1].o src/echoClient[1].cpp

#Clean the build directory
clean:
	rm -f bin/*