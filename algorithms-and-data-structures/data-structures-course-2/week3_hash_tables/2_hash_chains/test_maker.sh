#!/bin/bash

#==============================================================================#
#   Usage: bash generate_queries.sh [--max | --stress]
#   Generates a set of queries for a hash table, including operations such as
#   'add', 'del', 'find', and 'check'. The number of queries and the length of
#   random strings are determined based on the provided flags.
#
#   If no flags are provided:
#   - Generates a number of queries N equal to 1/4 of the largest possible N (105000).
#   - Generates random strings with a length up to 1/4 of the maximum length (15 characters).
#
#   --max: Use the largest possible number of queries N (105000).
#          Generates random strings with a maximum length of 15 characters.
#   --stress: Use up to 1/8 of the largest possible N (13125).
#             Generates random strings with a maximum length of 1/8 of the maximum length 
#             (1.875, rounded to 1 character).
#==============================================================================#

# Default values for N
max_N=105000
default_N=$((max_N / 4))
stress_N=$((max_N / 8))

# Default string length and length modifications based on flags
default_length=15
stress_length=$((default_length / 8))
max_length=$default_length
default_sub_length=$((default_length / 4))

# Set N and string length based on the flag provided (default is 1/4 of the largest N)
N=$default_N
string_length=$default_sub_length
if [ "$#" -eq 1 ]; then
    if [ "$1" = "--max" ]; then
        N=$max_N
        string_length=$max_length
    elif [ "$1" = "--stress" ]; then
        N=$stress_N
        string_length=$stress_length
    else
        echo "Invalid flag. Use --max or --stress."
        exit 1
    fi
fi

# Automatically calculate m (number of buckets), ensuring N/5 ≤ m ≤ N
m=$((N / 5 + RANDOM % (N - N / 5 + 1)))

# Generate random string of length between 1 and the calculated length using an array of alphabets
generate_random_string() {
    local length=$((RANDOM % string_length + 1))
    local alphabet=('a' 'b' 'c' 'd' 'e' 'f' 'g' 'h' 'i' 'j' 'k' 'l' 'm' 'n' 'o' 'p' 'q' 'r' 's' 't' 'u' 'v' 'w' 'x' 'y' 'z' 'A' 'B' 'C' 'D' 'E' 'F' 'G' 'H' 'I' 'J' 'K' 'L' 'M' 'N' 'O' 'P' 'Q' 'R' 'S' 'T' 'U' 'V' 'W' 'X' 'Y' 'Z')
    local rand_string=""
    for ((i = 0; i < length; i++)); do
        rand_string+="${alphabet[RANDOM % ${#alphabet[@]}]}"
    done
    echo "$rand_string"
}

# Output the number of buckets
echo "$m"

# Output the number of queries
echo "$N"

# Define the possible types of queries
queries=("add" "del" "find" "check")

# Generate N queries
for ((i = 0; i < N; i++)); do
    # Randomly choose the type of query
    query_type=${queries[RANDOM % 4]}

    if [ "$query_type" = "check" ]; then
        # For "check", we print an integer i (random index between 0 and m-1)
        index=$((RANDOM % m))
        echo "check $index"
    else
        # For "add", "del", and "find", we generate a random string with the calculated length
        random_string=$(generate_random_string)
        echo "$query_type $random_string"
    fi
done
