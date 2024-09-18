#!/bin/bash
#==============================================================================#
#   Usage: bash generate_phonebook_queries.sh [N]
#   Generates N phonebook queries, where N is the number of operations to output.
#   If N is not provided, the script randomly generates a value between 1 and 100000.
#   The script randomly generates operations including 'add', 'del', and 'find'
#   for phone numbers (up to 7 digits) and names (up to 15 characters).
#==============================================================================#

# Check if the number of queries N is provided, otherwise randomly choose N between 1 and 1000
if [ -z "$1" ]; then
  N=$((1 + RANDOM % 1000))  # Random value for N if not provided
else
  N=$1
fi

# Generate random name
generate_random_name() {
  local name_length=$((3 + RANDOM % 13))  # Generate name with length between 3 and 15
  local name=$(cat /dev/urandom | tr -dc 'a-z' | fold -w "$name_length" | head -n 1)
  echo "$name"
}

# Generate random 7-digit phone number
generate_random_number() {
  local phone_number=$((1000000 + RANDOM % 9000))  # Generate a 4-digit number for easy checking
  echo "$phone_number"
}

# Array to store phonebook contacts
declare -A phonebook

# Output the number of queries
echo "$N"

# Randomly generate queries
for ((i=0; i<N; i++)); do
  operation=$((RANDOM % 3))  # 0 = add, 1 = del, 2 = find
  phone_number=$(generate_random_number)

  case $operation in
    0)
      # Add or overwrite a contact in the phonebook
      name=$(generate_random_name)
      phonebook["$phone_number"]="$name"
      echo "add $phone_number $name"
      ;;
    1)
      # Delete a contact if it exists
      if [ -n "${phonebook[$phone_number]}" ]; then
        unset phonebook["$phone_number"]
      fi
      echo "del $phone_number"
      ;;
    2)
      # Find a contact and return the name or "not found"
      if [ -n "${phonebook[$phone_number]}" ]; then
        echo "find $phone_number"
      else
        echo "find $phone_number"
      fi
      ;;
  esac
done
