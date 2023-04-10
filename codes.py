numbers = [1, 2, 3, 4, 5]

def max_number(number):
  max = 0
  for number in numbers:
    if number > max:
      max = number
    else:
      continue
  return max

print(max_number(numbers))
    
  
  