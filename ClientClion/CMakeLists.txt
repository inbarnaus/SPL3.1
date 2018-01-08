cmake_minimum_required(VERSION 3.8)
project(Client)

set(BOOST_ROOT "/usr/include")
FIND_PACKAGE(Boost 1.57.0)
if (NOT Boost_FOUND)
    message(FARAL_ERROR "Could not find boost")
endif()
set(SOURCE_FILES
        include/connectionHandler.h
        src/connectionHandler.cpp
        src/MovieClient.cpp
        )
add_executable(BBclient ${SOURCE_FILES})
