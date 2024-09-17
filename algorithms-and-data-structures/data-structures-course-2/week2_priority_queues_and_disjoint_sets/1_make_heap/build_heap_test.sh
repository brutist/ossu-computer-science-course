#!/bin/bash

# Usage: bash run_tests.sh <num_tests> <cpp_file>
# The script compiles the C++ file and runs it with generated test inputs.

# Ensure two arguments are passed
if [ $# -ne 2 ]; then
    echo "Usage: $0 <num_tests> <cpp_file>"
    exit 1
fi

# Assign parameters
num_tests="$1"
cpp_file="$2"

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
c++ -std=c++14 -Wall "$cpp_file" -o program.out
if [ $? -ne 0 ]; then
    echo "Error: Compilation of $cpp_file failed."
    exit 1
fi

# Function to check if an array satisfies the min-heap property
check_min_heap_property() {
    local n=$1
    local arr=("${@:2}")

    for ((i=0; i<n; i++)); do
        local left=$(((2*i) + 1))
        local right=$(((2*i) + 2))

        # Check if parent is greater than the left child
        if [[ $left -lt $n && ${arr[$i]} -gt ${arr[$left]} ]]; then
            echo "FAIL: Min-heap property violated at index $i with left child $left"
            return 1
        fi

        # Check if parent is greater than the right child
        if [[ $right -lt $n && ${arr[$i]} -gt ${arr[$right]} ]]; then
            echo "FAIL: Min-heap property violated at index $i with right child $right"
            return 1
        fi
    done
    echo "PASS: Min-heap property satisfied."
    return 0
}

# Step 2: Generate and test inputs
for ((test_num=1; test_num<=num_tests; test_num++)); do
    echo -n "Running test $test_num...  "

    # Generate test input using test_maker.sh
    test_input=$("$test_maker_script")

    # Extract n and the array from the test_input
    n=$(echo "$test_input" | head -n 1)  # First line is n
    array=($(echo "$test_input" | tail -n +2 | tr -s ' '))  # Second line is the array

    # Pipe the test input into the compiled program
    program_output=$(echo "$test_input" | ./program.out)

    # Extract the number of swaps and the swaps from the program output
    num_swaps=$(echo "$program_output" | head -n 1)
    swaps=$(echo "$program_output" | tail -n +2)

    # Apply the swaps to the array
    while read i j; do
        # Ensure index `i` and `j` are correctly parsed and handle 0-based indexing if needed
        if [[ -z "$i" || -z "$j" ]]; then
            echo "Error: Swap indices are empty or invalid"
        fi

        # Perform the swap
        temp=${array[$i]}
        array[$i]=${array[$j]}
        array[$j]=$temp
    done <<< "$swaps"


    # Step 3: Check if the array is a valid min-heap
    check_min_heap_property "$n" "${array[@]}"

    # Ensure the number of swaps is valid
    if [[ $num_swaps -lt 0 || $num_swaps -gt $((4*n)) ]]; then
        echo "FAIL: Number of swaps ($num_swaps) is out of valid range for test $test_num"
        continue
    fi
    done


# Clean up
rm program.out
