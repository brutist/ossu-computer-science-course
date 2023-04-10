numbers = [14, 121, 34, 14, 55]

def max_number(number):
  max = number[0]
  for number in numbers:
    if number > max:
      max = number
      print(max, number)
    else:
      print(max, number)
      continue
  return max

print('Maximum number is - ', max_number(numbers))


def min_number(number):
  min = number[0]
  for number in numbers:
    if number < min:
      min = number
    else:
      continue
  return min

print('Minimum number is - ', min_number(numbers))
  