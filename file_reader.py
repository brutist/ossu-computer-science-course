#Write a program that prompts for a file name, then opens that file and reads through the file, looking for lines of the form:
#X-DSPAM-Confidence:    0.8475
#Count these lines and extract the floating point values from each of the lines and compute the average of those values and produce an output as shown below. Do not use the sum() function or a variable named sum in your solution.
#You can download the sample data at http://www.py4e.com/code3/mbox-short.txt when you are testing below enter mbox-short.txt as the file name.

filhandle = open(input('Enter filename: '), 'r')

def average_float(filhandle):
  
  total = 0
  line_count = 0
  
  for line in filhandle:
    start_of_number = 0
    number = 0
    
    if  not line.startswith('X-DSPAM-Confidence:'):
      continue
    else:
      line_count += 1
      start_of_number = line.find(':') + 1
      number = line[start_of_number:len(line)]
      number = float(number.strip())
      total += number

  average = total/line_count

  return average


print('Average spam confidence:', average_float(filhandle))

