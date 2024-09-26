#!/bin/bash

#==============================================================================#
#   Usage: bash test_maker.sh [--max | --stress]
#   Generates multiple pairs of strings `s` and `t` consisting of lower case 
#   Latin letters. The total length of all `s`s and `t`s does not exceed 100,000.
#
#   --max: Uses the largest possible total length for `s` and `t` (100,000 characters).
#
#   --stress: Uses a smaller length, where the total is 1/8 of the maximum (12,500).
#==============================================================================#

# Constants
MAX_TOTAL_LENGTH=100000  # Maximum total length for all s's and t's
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
total_length=$((MAX_TOTAL_LENGTH / 2))

# Check for optional flags
if [[ "$1" == "--max" ]]; then
    total_length=$MAX_TOTAL_LENGTH
elif [[ "$1" == "--stress" ]]; then
    total_length=$((MAX_TOTAL_LENGTH / STRESS_FACTOR))
fi

# Initialize remaining length
remaining_length=$total_length

# Generate pairs of strings s and t until the total length exceeds the limit
while [[ $remaining_length -gt 0 ]]; do
    # Randomly decide the length of s and t, ensuring they don't exceed the remaining length
    max_len=$((remaining_length / 2))
    s_length=$((RANDOM % max_len + 1))  # Make sure length is at least 1
    t_length=$((RANDOM % max_len + 1))

    # Generate strings s and t
    s=$(generate_random_string "$s_length")
    t=$(generate_random_string "$t_length")

    # Output the pair of strings
    echo "$s $t"

    # Decrease the remaining length
    remaining_length=$((remaining_length - s_length - t_length))
done
