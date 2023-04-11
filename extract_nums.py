# Write code using find() and string slicing (see section 6.10) to extract the number at the end of the line below. Convert the extracted value to a floating point number and print it out.

#this one is a more complex code for extracting the number, though not what the assignment is looking for

text = "X-DSPAM-Confidence:    0.8475"

def extract_number(text):
  start_of_number = 0
  end_of_number = 0
  number = 0
  
  for letter in text:
    if letter.isnumeric() == True:
      start_of_number = text.index(letter)
      break

  spliced_number = text[start_of_number:]
  
  for letter in spliced_number:
    if letter == ".":
      continue

    if letter.isnumeric() == True:
      if text.index(letter) <= len(text):
        end_of_number = text.index(letter) + 1
      else:
        continue

    if letter.isnumeric() == False:
      end_of_number = text.index(letter)
      break
    

  number = (text[start_of_number:end_of_number])

  return number

#print(extract_number(text))


#text = "X-DSPAM-Confidence:    0.8475"

def get_number(text):
  start_number = text.find(':')

  number = float(text[start_number + 1:])
  
  print(number)

get_number(text)