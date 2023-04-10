#Write a program that repeatedly prompts a user for integer numbers until the user enters 'done'. Once 'done' is entered, print out the largest and smallest of the numbers. If the user enters anything other than a valid number catch it with a try/except and put out an appropriate message and ignore the number. Enter 7, 2, bob, 10, and 4 and match the output below.

def number_set():
  numbers = []
  done = False 
  
  while done == False:
    number = input('Enter number here: ')

    if number == 'done':
      done = True

    else:
      try:
        number = int(number)
        numbers.append(number)
      except:
        print('Invalid input')

  return numbers

def min_and_max(numbers):
  min = numbers[0]
  max = numbers[0]
  
  for number in numbers:
    if min > number:
      min = number
    if max < number:
      max = number

  print('Maximum is', max)
  print('Minimum is', min)

  return None

min_and_max(number_set())
    
    
  

