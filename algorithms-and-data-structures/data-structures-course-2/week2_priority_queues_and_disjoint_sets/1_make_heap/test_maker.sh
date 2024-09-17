#!/bin/bash

# Check if an optional parameter for n is provided
if [ -n "$1" ]; then
    n="$1"
else
    # Generate a random number n between 1 and 100000 if no parameter is provided
    n=$((1 + RANDOM % 1000))
fi

# Ensure n is within the valid range
if [ "$n" -lt 1 ] || [ "$n" -gt 100000 ]; then
    echo "Error: n must be between 1 and 100000."
    exit 1
fi

# Output the first line containing n
echo "$n"

# Generate n distinct random numbers between 0 and 10^9
declare -A seen_numbers
counter=0

# Function to generate random number between 0 and 10^9 using /dev/urandom
generate_random_number() {
    echo $(( $(od -An -N4 -tu4 < /dev/urandom) % 1000000000 ))
}

# Generate distinct random numbers until we have n unique numbers
while [ $counter -lt $n ]; do
    rand_num=$(generate_random_number)  # Generate a random number between 0 and 10^9
    if [ -z "${seen_numbers[$rand_num]}" ]; then
        seen_numbers[$rand_num]=1
        echo -n "$rand_num"
        counter=$((counter + 1))
        if [ $counter -lt $n ]; then
            echo -n " "
        fi
    fi
done

# End the output with a new line
echo
