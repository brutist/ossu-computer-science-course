#!/bin/bash

#==============================================================================#
#   Usage: bash test_maker.sh [--max | --stress]
#   Generates two strings `s` and `t` consisting of lower case Latin letters.
#   The total length of all `s`s and `t`s does not exceed 100,000 characters.
#
#   --max: Uses the largest possible total length for `s` and `t` (100,000 characters).
#
#   --stress: Uses a smaller length, where the total is 1/8 of the maximum (12,500).
#==============================================================================#

# Constants
MAX_LENGTH=100000  # Maximum total length for s and t
STRESS_FACTOR=8  # Factor for stress testing
LATIN_ALPHABET="abcdefghijklmnopqrstuvwxyz"

# Function to generate a random string of a given length
generate_random_string() {
    local length=$1
    local str=""
    for ((i = 0; i < length; i++)); do
        str+="${LATIN_ALPHABET:RANDOM % ${#LATIN_ALPHABET}:1}"
    done
    echo "$str"
}

# Default total length (half of the max)
total_length=$((MAX_LENGTH / 2))

# Check for optional flags
if [[ "$1" == "--max" ]]; then
    total_length=$MAX_LENGTH
elif [[ "$1" == "--stress" ]]; then
    total_length=$((MAX_LENGTH / STRESS_FACTOR))
fi

# Generate random lengths for s and t such that their total doesn't exceed total_length
s_length=$((RANDOM % total_length))
t_length=$((total_length - s_length))

# Generate strings s and t
s=$(generate_random_string "$s_length")
t=$(generate_random_string "$t_length")

# Output strings s and t
echo "$s"
echo "$t"
