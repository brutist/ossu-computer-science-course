#!/bin/sh

directory="."

function iterate() {
  local dir="$1"

  for file in "$dir"/*; do
    if [ -f "$file" ] && [[ "$file" == *".cpp" ]]; then
        printf '%s\n' "$file"
        clang-format -i --style=file:./.clang-format $file
    fi

    if [ -d "$file" ]; then
      iterate "$file"
    fi
  done
}

iterate "$directory"
