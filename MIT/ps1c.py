#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

def bestSavingsRate(salary, portionSaved):
    #instantiate some values to make the code readable
    MONTHS_IN_YEAR = 12
    monthsOfSaving = range(36)#months
    currentSavings = float(0)
    monthlyInterestRate = 0.04 / MONTHS_IN_YEAR
    semiAnnualRaise = 0.07
    DOWNPAYMENT = 1000000
    currentSalary = salary
  
    for month in monthsOfSaving:
      #check whether it's time for raise
      if month % 6 == 0 and month != 0:
        salaryRaise = currentSalary * semiAnnualRaise
        currentSalary = currentSalary + salaryRaise
    
      #Interest per month should change along with the increase in savings
      monthlyInterestInSavings = currentSavings * monthlyInterestRate
      
      #Add savings 
      monthlySavings = (currentSalary / MONTHS_IN_YEAR) * portionSaved
      currentSavings = currentSavings + monthlySavings + monthlyInterestInSavings
      print(currentSavings)
      
      #checking whether 36 months have passed is savings enough
      if month >= 36:
        if currentSavings < DOWNPAYMENT: 
          print('It is not possible to pay the down payment in three years.')
        else: 
          print('it is possible')


# Prompt the user for the annual salary with error check
while True:
  number = input('Enter Annual Salary: ')
  try:
    annualSalary = float(number)
    break
  except:
    print('Enter numbers only')
    continue



bestSavingsRate(annualSalary, 1)
  
  

salaryRecord = list() #This is a list of dictionary with keys "month", "current_savings", "annual_salary"
record = dict()



        
      
  
  
  
    #This is not really part of the assignment but I really want to do this 
    #list of record for every month that passed
    #record["month"] = month
    #record["current_savings"] = currentSavings#
    #record['annualSalary'] = currentSalary

    #If you dont cast to list, the entire list will point to the last immutable values it was given with.
    #making all of the key-value pairs the same in every index of the list
    # This is not an elegant solution, but using eval() function can turn the list of string into a dictionary again. 
    #salaryRecord.append(str(record))

#print result
#print('Number of months:', month)

#Print the salary record for all months 
#for record in salaryRecord:
  #print(record)











