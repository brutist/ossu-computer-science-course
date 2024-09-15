#!/bin/bash

#==============================================================================#
#   Usage: bash generate_output.sh
#         This script outputs the following format:
#         - The first line contains an integer n (1 ≤ n ≤ 1000)
#         - The second line contains n integers a1, ..., an (0 ≤ ai ≤ 100000)
#         - The third line contains an integer m (1 ≤ m ≤ n)
#==============================================================================#

# Function to generate a random number between given range
generate_random_number() {
    echo $(( (RANDOM * 32768 + RANDOM) % ($2 - $1 + 1) + $1 ))
}

# Generate a random value for n (1 ≤ n ≤ 100000)
n=$(generate_random_number 1 1000)

# Output the first line (value of n)
echo "$n"

# Output the second line (n integers ai, each 0 ≤ ai ≤ 100000)
for (( i=1; i<=n; i++ )); do
    # Generate random integer ai (0 ≤ ai ≤ 100000)
    echo -n "$(generate_random_number 0 100000)"
    
    # Print a space after each integer except the last one
    if [ "$i" -lt "$n" ]; then
        echo -n " "
    fi
done
echo # for new line after printing all ai values

# Generate a random value for m (1 ≤ m ≤ n)
m=$(generate_random_number 1 "$n")

# Output the third line (value of m)
echo "$m"
