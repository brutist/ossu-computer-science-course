#!/bin/sh

for test in ./sample*; do 
    printf '%s answer: ' "$test"
    ./a.out < "$test"
    printf "\n"
done