
input = open((input('Enter filename here: ')))
filhand = input


def print_file(filhand):
  for line in filhand:
    print(line)


print_file(filhand)


def line_count(filhand):
  count = 0
  for line in filhand:
    count += 1
  
  return count

print('Number of lines:' , line_count(filhand))
