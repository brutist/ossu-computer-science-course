#!/bin/bash

#==============================================================================#
#   Usage: bash test_maker.sh [--max | --stress]
#   Generates two strings: a pattern (P) and a text (T), containing only Latin 
#   letters (both uppercase and lowercase). The lengths of the strings are determined 
#   based on the provided flags. The pattern P is usually smaller than the text T.
#
#   If no flags are provided:
#   - Generates a text (T) with a length equal to 1/4 of the maximum possible length (125000).
#   - Generates a pattern (P) with a random length usually smaller than T, but it 
#     can occasionally be equal to T.
#
#   --max: Use the largest possible length for the text (500000).
#          Generates a random pattern (P) where the length is usually smaller 
#          than the text, but it can be equal.
#   --stress: Use up to 1/8 of the maximum possible length for the text (62500).
#             Generates a random pattern (P) with a length between 10 and 30.
#
#   The pattern (P) and the text (T) will both contain only Latin letters (A-Z, a-z).
#==============================================================================#

# Constants
MAX_T=500000  # Maximum length of text T
MAX_P=500000  # Maximum length of pattern P
STRESS_FACTOR=8  # Stress flag uses 1/8 of the max length
DEFAULT_FACTOR=4  # By default, the length is 1/4 of the max
LATIN_ALPHABET="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

# Function to generate a random string of a given length
generate_random_string() {
    local length=$1
    local chunk_size=32767  # Limit of RANDOM
    local str=""

    # Loop to generate chunks of random characters
    while (( length > 0 )); do
        local cur_len=$(( length < chunk_size ? length : chunk_size ))  # Generate smaller chunks if needed
        for (( i = 0; i < cur_len; i++ )); do
            str+="${LATIN_ALPHABET:RANDOM % ${#LATIN_ALPHABET}:1}"
        done
        length=$(( length - cur_len ))
    done

    echo "$str"
}

# Function to calculate random pattern length (usually smaller but can be equal)
calculate_pattern_length() {
    local text_len=$1
    # Make the pattern length a random percentage of text length (between 10% and 100%)
    local min_percentage=10
    local max_percentage=100
    local random_percentage=$(( RANDOM % (max_percentage - min_percentage + 1) + min_percentage ))
    echo $(( text_len * random_percentage / 100 ))
}

# Default lengths are 1/4 of the maximum
T_len=$(( MAX_T / DEFAULT_FACTOR ))
P_len=$(calculate_pattern_length "$T_len")  # Random length for P, usually smaller

# Check optional flags
if [[ "$1" == "--stress" ]]; then
    T_len=$(( MAX_T / STRESS_FACTOR ))
    P_len=$(( RANDOM % 5 + 1 ))  # Pattern length between 1 to 6
elif [[ "$1" == "--max" ]]; then
    T_len=$MAX_T
    P_len=$(calculate_pattern_length "$T_len")  # Recalculate for maximum case
fi

# Ensure |P| <= |T| (just in case)
if (( P_len > T_len )); then
    P_len=$T_len
fi

# Generate random pattern P and text T
P=$(generate_random_string "$P_len")
T=$(generate_random_string "$T_len")

# Output the results
echo "$P"
echo "$T"
