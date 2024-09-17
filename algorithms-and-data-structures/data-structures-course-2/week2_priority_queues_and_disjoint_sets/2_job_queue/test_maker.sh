#!/bin/bash

# Set constraints
max_n=10000     # 1 ≤ n ≤ 10^5
max_m=100000     # 1 ≤ m ≤ 10^5
max_t=1000000000 # 0 ≤ ti ≤ 10^9

# Generate random n (number of threads) and m (number of jobs)
n=$((1 + RANDOM % max_n))  # 1 ≤ n ≤ max_n
m=$((1 + RANDOM % max_m))  # 1 ≤ m ≤ max_m

# Output the first line with n and m
echo "$n $m"

# Generate m random integers for the processing times ti
for ((i=1; i<=m; i++)); do
    t=$((RANDOM % (max_t + 1)))  # 0 ≤ ti ≤ max_t
    echo -n "$t"
    if [ $i -lt $m ]; then
        echo -n " "  # Space separate numbers except for the last one
    fi
done

# End the output with a new line
echo
