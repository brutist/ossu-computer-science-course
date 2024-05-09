import csv
import re

# I know its bad style but this is just dirty code for dirty work


def clean_answers(fname):

  answers = []
  student_answers = {} # instantiate empty dict for answers
  with open(fname, newline='') as f:
    reader = csv.reader(f)
    c=1
    for row in reader:
      n=0
      for column in row:
        if re.search("\Stu", column) and len(answers) < 22:
          answers.append(n)
        n = n + 1
        
      student_answers["Student " + str(c)] = []
      c=c+1


  with open(fname, mode ='r')as file:
    csvFile = csv.reader(file)
    l=0
    for lines in csvFile:
      c=0
      for column in lines:
        if c in answers and l > 0:
          student_answers["Student " + str(l)].append(column)
        c=c+1
      l+=1   

  print(fname)
  for k, v in student_answers.items():
    print(k, v)


def get_filtered_data(loffname):
  for fname in loffname:
    clean_answers(fname)


get_filtered_data(["PostSurveyHonestyExport.csv", "PostSurveyHumilityExport.csv"])