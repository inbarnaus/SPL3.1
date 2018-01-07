CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system/-lboost_thread-mt

all: MovieClient
	g++ -o bin/BBclient bin/connectionHandler.o bin/MovieClient.o $(LDFLAGS) 

MovieClient: bin/connectionHandler.o bin/MovieClient.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/MovieClient.o: src/MovieClient.cpp
	g++ $(CFLAGS) -o bin/MovieClient.o src/MovieClient.cpp
	
.PHONY: clean
clean:
	rm -f bin/*
