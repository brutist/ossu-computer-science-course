#!/bin/bash

declare -r data="../../data/data.dat"

# question 1
wc -l $data

## question 2
grep 'd[ao]lor' $data | wc -l

# question 3
wc -w $data

# question 4
grep '^mol' $data | wc -l

# question 5
ls -R $data | grep '\.txt$' | wc -l