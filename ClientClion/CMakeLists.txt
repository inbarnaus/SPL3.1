cmake_minimum_required(VERSION 3.8)
project(Client)
set(CMAKE_CXX_STANDARD 11)
set(SOURCE_FILES
        include/connectionHandler.h
        src/connectionHandler.cpp
        src/MovieClient.cpp
        )
add_executable(BBclient ${SOURCE_FILES})
