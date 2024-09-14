#!/bin/sh

#==============================================================================#
#   Usage: bash compare_cpp.sh <cpp_file1> <cpp_file2>
#         This script compiles two C++ files and compares their standard output
#         based on input files from a folder named 'test' in the same directory.
#==============================================================================#

# Check if two C++ files are provided as arguments
if [ $# -ne 2 ]; then
    echo "Usage: $0 <cpp_file1> <cpp_file2>"
    exit 1
fi

# C++ files to compile
cpp_file1="$1"
cpp_file2="$2"

# Check if the provided files exist and have a .cpp extension
if [ ! -f "$cpp_file1" ] || [[ "$cpp_file1" != *.cpp ]]; then
    echo "Error: $cpp_file1 is not a valid C++ file."
    exit 1
fi

if [ ! -f "$cpp_file2" ] || [[ "$cpp_file2" != *.cpp ]]; then
    echo "Error: $cpp_file2 is not a valid C++ file."
    exit 1
fi

# Directory where the 'tests' folder is located (assumed to be in the same folder as the script)
test_folder="$(dirname "$cpp_file1")/tests"

# Check if test folder exists
if [ ! -d "$test_folder" ]; then
    echo "Error: Test folder $test_folder not found."
    exit 1
fi

# Compile both C++ files
echo "Compiling $cpp_file1..."
c++ -std=c++14 -Wall "$cpp_file1" -o prog1.out
if [ $? -ne 0 ]; then
    echo "Error: Compilation of $cpp_file1 failed."
    exit 1
fi

echo "Compiling $cpp_file2..."
c++ -std=c++14 -Wall "$cpp_file2" -o prog2.out
if [ $? -ne 0 ]; then
    echo "Error: Compilation of $cpp_file2 failed."
    exit 1
fi

# Iterate over all files in the test folder that match the pattern "xx"
for input_file in "$test_folder"/[0-9][0-9]; do
    # Skip if no matching files are found
    if [ ! -f "$input_file" ]; then
        continue
    fi

    # Run both programs with the input file and capture their output
    output_prog1=$(./prog1.out < "$input_file")
    output_prog2=$(./prog2.out < "$input_file")

    # Compare the output of both programs
    if [ "$output_prog1" = "$output_prog2" ]; then
        echo "Test $(basename "$input_file"): PASS"
    else
        echo "Test $(basename "$input_file"): FAIL"
        echo "Output from $cpp_file1:"
        echo "$output_prog1"
        echo "Output from $cpp_file2:"
        echo "$output_prog2"
    fi
done

# Clean up compiled files
rm prog1.out prog2.out
