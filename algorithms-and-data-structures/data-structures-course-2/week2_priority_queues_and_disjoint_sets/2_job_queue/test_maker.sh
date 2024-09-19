#!/bin/bash

#==============================================================================#
#   Usage: bash generate_jobs.sh [--max | --stress]
#         Generates random job processing times for a set number of threads (n)
#         and jobs (m), with processing times (t).
#         --max: Uses the largest possible values for n, m, and t.
#         --stress: Uses up to 1/8 of the largest possible values for n, m, and t.
#         If no flag is provided, defaults to 1/4 of the largest possible values.
#==============================================================================#


# Set constraints
max_n=100000     # 1 ≤ n ≤ 10^5
max_m=100000     # 1 ≤ m ≤ 10^5
max_t=1000000000 # 0 ≤ ti ≤ 10^9

# Default to 1/4 of max values if no flag is provided
n_factor=4
m_factor=4
t_factor=4

# Process optional flags for --max or --stress
if [[ "$1" == "--max" ]]; then
    n_factor=1
    m_factor=1
    t_factor=1
elif [[ "$1" == "--stress" ]]; then
    n_factor=8
    m_factor=8
    t_factor=8
fi

# Calculate n, m, and t based on the chosen factor
n=$((max_n / n_factor))  # n is either max_n, max_n/8, or max_n/4
m=$((max_m / m_factor))  # m is either max_m, max_m/8, or max_m/4
max_t=$((max_t / t_factor))  # t is either max_t, max_t/8, or max_t/4

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
