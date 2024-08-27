#!/bin/sh

for file_name in ./**/*.{cpp,h,hpp}; do
    if [ -f "$file_name" ]; then
        printf '%s\n' "$file_name"
        clang-format -i --style=file:./.clang-format $file_name
    fi
done
