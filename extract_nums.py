# Write code using find() and string slicing (see section 6.10) to extract the number at the end of the line below. Convert the extracted value to a floating point number and print it out.

text = "X-DSPAM-Confidence:    0.8475"

def extract_number(text):
  start_of_number = 0
  end_of_number = 0
  number = 0
  
  for letter in text:
    if letter.isnumeric() == True:
      start_of_number = text.index(letter)
      print(start_of_number)
      break
      
  for number in text[start_of_number:len(text)]:
    if number == ".":
      continue

    if number.isnumeric() == False or number == len(text):
      end_of_number = text.index(number)
      print(end_of_number)
      break
    

  number = (text[start_of_number:end_of_number])

  return number

print(extract_number(text))



      