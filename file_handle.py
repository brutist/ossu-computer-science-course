
filhand = open(input('Enter filename here: '))

def print_file(filhand):
  for line in filhand:
    print(line)
    
print_file(filhand)
print(filhand)

#Why do I need to reinput filhand? If I remove this, it will clear the fil

#filhand = open(input('Enter filename here: '))

def line_count(filhand):
  count = 0
  for line in filhand:
    count += 1
  
  return count

print('Number of lines:' , line_count(filhand))
print(filhand)