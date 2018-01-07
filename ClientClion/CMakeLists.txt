cmake_minimum_required(VERSION 3.8)
project(Client)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

include_directories("/usr/local/Cellar/boost/1.57.0/include/boost")

set(SOURCE_FILES
        include/boost
        include/connectionHandler.h
        src/connectionHandler.cpp
        src/echoClient.cpp
        )
add_executable(Client ${SOURCE_FILES})
