# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.8

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = "/home/rotem/Downloads/Program Files/clion-2017.2.3/bin/cmake/bin/cmake"

# The command to remove a file.
RM = "/home/rotem/Downloads/Program Files/clion-2017.2.3/bin/cmake/bin/cmake" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/Client.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Client.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Client.dir/flags.make

CMakeFiles/Client.dir/src/connectionHandler.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/connectionHandler.cpp.o: ../src/connectionHandler.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Client.dir/src/connectionHandler.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/connectionHandler.cpp.o -c "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/connectionHandler.cpp"

CMakeFiles/Client.dir/src/connectionHandler.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/connectionHandler.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/connectionHandler.cpp" > CMakeFiles/Client.dir/src/connectionHandler.cpp.i

CMakeFiles/Client.dir/src/connectionHandler.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/connectionHandler.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/connectionHandler.cpp" -o CMakeFiles/Client.dir/src/connectionHandler.cpp.s

CMakeFiles/Client.dir/src/connectionHandler.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/connectionHandler.cpp.o.requires

CMakeFiles/Client.dir/src/connectionHandler.cpp.o.provides: CMakeFiles/Client.dir/src/connectionHandler.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/connectionHandler.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/connectionHandler.cpp.o.provides

CMakeFiles/Client.dir/src/connectionHandler.cpp.o.provides.build: CMakeFiles/Client.dir/src/connectionHandler.cpp.o


CMakeFiles/Client.dir/src/MovieClient.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/MovieClient.cpp.o: ../src/MovieClient.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/Client.dir/src/MovieClient.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/MovieClient.cpp.o -c "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/MovieClient.cpp"

CMakeFiles/Client.dir/src/MovieClient.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/MovieClient.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/MovieClient.cpp" > CMakeFiles/Client.dir/src/MovieClient.cpp.i

CMakeFiles/Client.dir/src/MovieClient.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/MovieClient.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/src/MovieClient.cpp" -o CMakeFiles/Client.dir/src/MovieClient.cpp.s

CMakeFiles/Client.dir/src/MovieClient.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/MovieClient.cpp.o.requires

CMakeFiles/Client.dir/src/MovieClient.cpp.o.provides: CMakeFiles/Client.dir/src/MovieClient.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/MovieClient.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/MovieClient.cpp.o.provides

CMakeFiles/Client.dir/src/MovieClient.cpp.o.provides.build: CMakeFiles/Client.dir/src/MovieClient.cpp.o


# Object files for target Client
Client_OBJECTS = \
"CMakeFiles/Client.dir/src/connectionHandler.cpp.o" \
"CMakeFiles/Client.dir/src/MovieClient.cpp.o"

# External object files for target Client
Client_EXTERNAL_OBJECTS =

Client: CMakeFiles/Client.dir/src/connectionHandler.cpp.o
Client: CMakeFiles/Client.dir/src/MovieClient.cpp.o
Client: CMakeFiles/Client.dir/build.make
Client: CMakeFiles/Client.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable Client"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Client.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Client.dir/build: Client

.PHONY : CMakeFiles/Client.dir/build

CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/connectionHandler.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/MovieClient.cpp.o.requires

.PHONY : CMakeFiles/Client.dir/requires

CMakeFiles/Client.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Client.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Client.dir/clean

CMakeFiles/Client.dir/depend:
	cd "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion" "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion" "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug" "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug" "/home/rotem/Code/Assignment3 SPL/308339597-312231665/ServerC/ClientClion/cmake-build-debug/CMakeFiles/Client.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/Client.dir/depend

