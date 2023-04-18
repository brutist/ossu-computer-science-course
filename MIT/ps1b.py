#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

annualSalary = float(input('Enter Annual Salary: '))
portionSaved = float(input('Enter portion of salary saved per month: '))
totalCost =  float(input('Enter dream house cost: '))
semiAnnualRaise = float(input('Enter semi-annual raise, as decimal: '))


#savings that will go to the downpayment
currentSavings = float(0)
monthlySalary = annualSalary / 12#months
monthlySavings = monthlySalary * portionSaved
monthlyInterestRate = 0.04 / 12

portionDownPayment = totalCost * 0.25


def timeWithRaise(salary, percentSaved, cost, salaryRaise):
    month = 0
    while currentSavings < portionDownPayment:
    months += 1
    monthlyInterestInSavings = currentSavings * monthlyInterestRate
    currentSavings = currentSavings + monthlySavings + monthlyInterestInSavings
    #check whether it's time for raise
    if months % 6 == 0:
        annualSalary = annualSalary + (annualSalary * semiAnnualRaise)
    
    return months

print(timeWithRaise())



