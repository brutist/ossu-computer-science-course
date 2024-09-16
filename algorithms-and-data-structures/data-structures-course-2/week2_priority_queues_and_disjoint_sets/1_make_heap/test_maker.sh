#!/bin/bash

# Generate a random number n between 1 and 100000
n=$((1 + RANDOM % 100))

# Output the first line containing n
echo "$n"

# Generate an array of n distinct random numbers between 0 and 10^9
declare -A numbers_seen  # Associative array to ensure distinct numbers
counter=0

# Function to generate random number between 0 and 10^9 using /dev/urandom
generate_random_number() {
    echo $(( $(od -An -N4 -tu4 < /dev/urandom) % 1000 ))
}

while [ $counter -lt $n ]; do
    rand_num=$(generate_random_number)  # Generate a random number between 0 and 10^9
    if [ -z "${numbers_seen[$rand_num]}" ]; then
        numbers_seen[$rand_num]=1
        echo -n "$rand_num"
        counter=$((counter + 1))
        if [ $counter -lt $n ]; then
            echo -n " "
        fi
    fi
done

# End the output with a new line
echo
