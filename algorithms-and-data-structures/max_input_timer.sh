#!/bin/bash
#==============================================================================#
#   Usage: bash wrapper_compare_cpp.sh <cpp_dir>
#         Wrapper script for compare_cpp.sh that automatically passes --max
#         flag, disables verbosity, and runs with num_tests=1.
#==============================================================================#

# Ensure the cpp_dir argument is provided
if [ $# -lt 1 ]; then
    echo "Usage: $0 <cpp_dir>"
    exit 1
fi

# Get the cpp_dir from the arguments
cpp_dir="$1"

# Run compare_cpp.sh with the --max flag, without -v (verbosity), and with num_tests=1
bash stress_tester.sh "$cpp_dir" 1 --max
