#Open the file romeo.txt and read it line by line. For each line, split the line into a list of words using the split() method. The program should build a list of words. For each word on each line check to see if the word is already in the list and if not append it to the list. When the program completes, sort and print the resulting words in python sort() order as shown in the desired output.
#You can download the sample data at http://www.py4e.com/code3/romeo.txt

filhandle = open(input('Enter filename: '))

file = filhandle
word_list = list()

def list_words(file):
  file_lines = list()
  line_word_list = list()
  
  for l in file:
    file_lines.append(l)

  for line in file_lines:
    line_word_list = line.split()
    
    for word in line_word_list:
      if word in word_list:
        continue
      
      else:
        word_list.append(word)
  word_list.sort()
  return word_list

print(list_words(file))


    
    
    