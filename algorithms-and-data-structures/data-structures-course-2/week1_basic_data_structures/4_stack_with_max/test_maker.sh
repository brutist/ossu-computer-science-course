#!/bin/bash

#==============================================================================#
#   Usage: bash generate_queries.sh
#         This script generates a set of valid stack-related queries,
#         including "push v", "pop", and "max", while ensuring that no
#         "max" or "pop" is called on an empty stack.
#         The number of queries is randomized from 1 to 400,000, and
#         the push value is between 0 and 100000.
#==============================================================================#

# Randomize the number of queries between 1 and 400000
q=$(shuf -i 1-4000 -n 1)

# Print the first line with the number of queries
echo "$q"

# Initialize an empty stack counter to keep track of the number of elements
stack_size=0

# Function to generate a random number between 0 and 100000
generate_random_number() {
    echo $(( (RANDOM * 32768 + RANDOM) % 100001 ))
}

# Generate queries
for (( i=1; i<=q; i++ )); do
    if [ "$stack_size" -eq 0 ]; then
        # If the stack is empty, the only valid command is "push"
        command="push"
    else
        # If the stack is not empty, randomly select from push, pop, or max
        cmd_index=$((RANDOM % 3))
        if [ "$cmd_index" -eq 0 ]; then
            command="push"
        elif [ "$cmd_index" -eq 1 ]; then
            command="pop"
        else
            command="max"
        fi
    fi

    if [ "$command" = "push" ]; then
        # Generate a random value between 0 and 100000 for push and increase the stack size
        value=$(generate_random_number)
        echo "$command $value"
        stack_size=$((stack_size + 1))
    elif [ "$command" = "pop" ]; then
        # Decrease the stack size for pop
        echo "$command"
        stack_size=$((stack_size - 1))
    else
        # Just output max since the stack is not empty
        echo "$command"
    fi
done
