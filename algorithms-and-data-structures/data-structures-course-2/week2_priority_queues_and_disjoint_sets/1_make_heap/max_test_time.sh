#!/bin/bash

# Usage: bash run_tests.sh <cpp_file>
# The script compiles the C++ file and runs a single test with generated test inputs.

# Ensure one argument (the C++ file) is passed
if [ $# -ne 1 ]; then
    echo "Usage: $0 <cpp_file>"
    exit 1
fi

# Assign parameters
cpp_file="$1"

# Ensure the cpp file exists
if [ ! -f "$cpp_file" ] || [[ "$cpp_file" != *.cpp ]]; then
    echo "Error: $cpp_file is not a valid C++ file."
    exit 1
fi

# Get the directory where the cpp file is located
cpp_dir=$(dirname "$cpp_file")

# Ensure the test_maker.sh script exists in the same directory as the cpp file
test_maker_script="$cpp_dir/test_maker.sh"
if [ ! -f "$test_maker_script" ]; then
    echo "Error: $test_maker_script not found in $cpp_dir."
    exit 1
fi

# Ensure test_maker.sh is executable
if [ ! -x "$test_maker_script" ]; then
    echo "Making $test_maker_script executable..."
    chmod +x "$test_maker_script"
fi

# Step 1: Compile the C++ file
echo "Compiling $cpp_file..."
c++ -O2 -std=c++14 -Wall "$cpp_file" -o program.out   # Use -O2 optimization flag for faster execution
if [ $? -ne 0 ]; then
    echo "Error: Compilation of $cpp_file failed."
    exit 1
fi

# Total time accumulator
total_time=0
max_input_test=100000

# Step 2: Generate and test inputs
echo "Running test..."

# Generate test input using test_maker.sh
test_input=$("$test_maker_script" "$max_input_test")

# Read and store the input in one pass
read -r n
array=($(tail -n +2 <<< "$test_input"))

# Step 2.1: Capture start time (optimized)
start_time=$(date +%s%N)

# Pipe the test input into the compiled program
program_output=$(./program.out <<< "$test_input")

# Step 2.2: Capture end time
end_time=$(date +%s%N)

# Step 2.3: Calculate time difference in nanoseconds and convert to milliseconds
elapsed_time=$(( (end_time - start_time) / 1000000 ))
echo "Time taken by the C++ program: ${elapsed_time} ms"

# Output total time taken by the C++ program
echo "Total time taken by the C++ program: ${elapsed_time} ms"

# Clean up
rm program.out
