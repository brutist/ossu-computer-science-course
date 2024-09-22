#!/bin/bash

#==============================================================================#
#   Usage: bash test_maker.sh [--max | --stress]
#   Generates a string `s` consisting of small Latin letters, followed by a set 
#   of queries. The number of queries `q` and the length of the string `s` 
#   are determined based on the provided flags. Each query consists of three 
#   integers `a`, `b`, and `l`.
#
#   If no flags are provided:
#   - Generates a string `s` of length 1/4 of the maximum possible length (125000).
#   - Generates a number of queries `q` equal to 1/4 of the maximum possible queries (25000).
#
#   --max: Use the largest possible values for the string length and queries.
#          Generates a string `s` of length 500000 and 100000 queries.
#
#   --stress: Use up to 1/8 of the maximum possible length for the string and queries.
#             Generates a string `s` of length 62500 and 12500 queries, 
#             with `l` always between 1 and 10.
#
#==============================================================================#

# Constants
MAX_S=500000  # Maximum length of string s
MAX_Q=100000  # Maximum number of queries q
STRESS_FACTOR=8  # Stress flag uses 1/8 of the max
DEFAULT_FACTOR=4  # By default, the length is 1/4 of the max
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

# Function to generate random queries
generate_random_queries() {
    local s_len=$1
    local num_queries=$2
    local l_min=$3
    local l_max=$4
    for ((i = 0; i < num_queries; i++)); do
        local a=$((RANDOM % (s_len / 2)))
        local b=$((RANDOM % (s_len / 2) + s_len / 2))
        local l=$((RANDOM % (l_max - l_min + 1) + l_min))  # l between l_min and l_max
        echo "$a $b $l"
    done
}

# Default values (1/4 of the max)
S_len=$((MAX_S / DEFAULT_FACTOR))
Q_count=$((MAX_Q / DEFAULT_FACTOR))
l_min=1
l_max=$((S_len / 10))  # Default l_max is 10% of the string length

# Check optional flags
if [[ "$1" == "--stress" ]]; then
    S_len=$((MAX_S / STRESS_FACTOR))
    Q_count=$((MAX_Q / STRESS_FACTOR))
    l_max=10  # Stress mode restricts l to between 1 and 10
elif [[ "$1" == "--max" ]]; then
    S_len=$MAX_S
    Q_count=$MAX_Q
fi

# Generate string s and queries q
S=$(generate_random_string "$S_len")
Q=$(generate_random_queries "$S_len" "$Q_count" "$l_min" "$l_max")

# Output the results
echo "$S"
echo "$Q_count"
echo "$Q"
