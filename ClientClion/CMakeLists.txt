cmake_minimum_required(VERSION 3.8)
project(Client)

FIND_PACKAGE(Boost)
IF (Boost_FOUND)
    INCLUDE_DIRECTORIES(${Boost_INCLUDE_DIR})
    ADD_DEFINITIONS( "-DHAS_BOOST" )
ENDIF()
set(SOURCE_FILES
        include/connectionHandler.h
        src/connectionHandler.cpp
        src/MovieClient.cpp
        )
add_executable(BBclient ${SOURCE_FILES})
