#!/bin/sh

#==============================================================================#
#   Usage: run as "bash automatic_tester.sh 'path_to_cpp_file' <verbosity>"
#         Automatically compiles using "c++ -std=c++14 -Wall "$cpp_file" -o a.out"
#         Test file names should be in format xx {x = 0-9} and test answers should be
#         in format xx.a located in the same folder named "tests"
#         <verbosity> is true or false. If false, expected vs. actual output will not be shown.
#==============================================================================#

# Check if C++ file argument is provided
if [ $# -lt 1 ]; then
    echo "Usage: $0 <cpp_file> [verbosity]"
    exit 1
fi

# C++ file to compile
cpp_file="$1"

# Verbosity flag (default is true)
verbose=${2:-true}

# Check if the provided file exists and has a .cpp extension
if [ ! -f "$cpp_file" ] || [[ "$cpp_file" != *.cpp ]]; then
    echo "Error: $cpp_file is not a valid C++ file."
    exit 1
fi

# Directory where the 'test' folder is located (assumed to be in the same folder as the script)
test_folder="$(dirname "$cpp_file")/tests"

# Check if test folder exists
if [ ! -d "$test_folder" ]; then
    echo "Error: Test folder $test_folder not found."
    exit 1
fi

# Compile the C++ file using c++ with the -std=c++14 and -Wall flags
echo "Compiling $cpp_file..."
c++ -std=c++14 -Wall "$cpp_file" -o a.out

# Check if compilation was successful by checking if a.out exists
if [ ! -f "a.out" ]; then
    echo "Error: Compilation failed."
    exit 1
fi

# Iterate over all files in the test folder that match the pattern "xx"
for input_file in "$test_folder"/[0-9][0-9]; do
    # Skip if no matching files are found
    if [ ! -f "$input_file" ]; then
        continue
    fi

    # Derive the expected output file (xx.a)
    output_file="${input_file}.a"

    # Check if the expected output file exists
    if [ -f "$output_file" ]; then
        # Run a.out with the input file
        program_output=$(./a.out < "$input_file")
        
        # Read the expected output from the correct output file
        expected_output=$(cat "$output_file")
        
        # Compare the program's output with the expected output
        if [ "$program_output" = "$expected_output" ]; then
            echo "Test $(basename "$input_file"): PASS"
        else
            echo "Test $(basename "$input_file"): FAIL"
            if [ "$verbose" = true ]; then
                echo "Expected:"
                echo "$expected_output"
                echo "Got:"
                echo "$program_output"
            fi
        fi
    else
        echo "Skipping test $(basename "$input_file"): Expected output file $output_file missing"
    fi
done
