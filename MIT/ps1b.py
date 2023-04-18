#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

annualSalary = float(input('Enter Annual Salary: '))
portionSaved = float(input('Enter portion of salary saved per month: '))
totalCost =  float(input('Enter dream house cost: '))
semiAnnualRaise = float(input('Enter semi-annual raise, as decimal: '))


#instantiate some values to make the code readable
MONTHS_IN_YEAR = 12
currentSavings = float(0)
currentSalary = annualSalary
monthlyInterestRate = 0.04 / MONTHS_IN_YEAR
downPayment = totalCost * 0.25



salaryRecord = list() #This is a list of dictionary with keys "month", "current_savings", "annual_salary"
record = dict()
month = 0

while currentSavings < downPayment:
    
    #check whether it's time for raise
    if month % 6 == 0 and month != 0:
      salaryRaise = currentSalary * semiAnnualRaise
      currentSalary = currentSalary + salaryRaise

    #No. of months passed
    month += 1
  
    #Interest per month should change along with the increase in savings
    monthlyInterestInSavings = currentSavings * monthlyInterestRate
    
    #Add savings 
    monthlySavings = (currentSalary / MONTHS_IN_YEAR) * portionSaved
    currentSavings = currentSavings + monthlySavings + monthlyInterestInSavings
    
    #This is not really part of the assignment but I really want to do this 
    #list of record for every month that passed
    record["month"] = month
    record["current_savings"] = currentSavings
    record['annualSalary'] = currentSalary

    #If you dont cast to list, the entire list will point to the last immutable values it was given with.
    #making all of the key-value pairs the same in every index of the list
    # This is not an elegant solution, but using eval() function can turn the list of string into a dictionary again. 
    salaryRecord.append(str(record))

#Print the salary record for all months 
#for record in salaryRecord:
  #print(record)






