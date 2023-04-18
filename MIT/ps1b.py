#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

annualSalary = float(input('Enter Annual Salary: '))
portionSaved = float(input('Enter portion of salary saved per month: '))
totalCost =  float(input('Enter dream house cost: '))
semiAnnualRaise = float(input('Enter semi-annual raise, as decimal: '))


#savings that will go to the downpayment
MONTHS_IN_YEAR = 12
currentSavings = float(0)
monthlySalary = annualSalary / 12#months
monthlySavings = monthlySalary * portionSaved
monthlyInterestRate = 0.04 / MONTHS_IN_YEAR
portionDownPayment = totalCost * 0.25
month = 0

#This is going to be a list of recor
salaryRecord = list() #This is a list of dictionary with keys "month", "current_savings", "annual_salary"
record = dict()

while currentSavings < portionDownPayment:
    #No. of months passed
    month += 1

    #Interest per month should change along with the increase in savings
    monthlyInterestInSavings = currentSavings * monthlyInterestRate
    
    #Add savings 
    currentSavings = currentSavings + monthlySavings + monthlyInterestInSavings
    
    #check whether it's time for raise
    if month % 6 == 0:
        annualSalary = annualSalary + (annualSalary * semiAnnualRaise)
    
    record["month"] = int(month)
    record["current_savings"] = int(currentSavings)
    record['annualSalary'] = int(annualSalary)
    
    salaryRecord.append(record)
    print(month)
print(salaryRecord)





