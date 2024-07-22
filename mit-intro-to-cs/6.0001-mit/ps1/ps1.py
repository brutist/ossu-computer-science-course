#Part A: House Hunting
# Test Case 1 
# >>>
# Enter your annual salary: 120000
# Enter the percent of your salary to save, as a decimal: .10
# Enter the cost of your dream home: 1000000
# Number of months: 183 
# >>>
# Test Case 2 
# >>>
# Enter your annual salary: 80000 
# Enter the percent of your salary to save, as a decimal: .15
# Enter the cost of your dream home: 500000
# Number of months: 105



annualSalary = float(input('Enter Annual Salary: '))
portionSaved = float(input('Enter portion of salary saved per month: '))
totalCost =  float(input('Enter dream house cost: '))

#savings that will go to the downpayment
currentSavings = float(0)
monthlySalary = annualSalary / 12#months
monthlySavings = monthlySalary * portionSaved
monthlyInterestRate = 0.04 / 12

portionDownPayment = totalCost * 0.25
months = 0

while currentSavings < portionDownPayment:
  monthlyInterestInSavings = currentSavings * monthlyInterestRate
  currentSavings = currentSavings + monthlySavings + monthlyInterestInSavings
  months += 1

print(months)


