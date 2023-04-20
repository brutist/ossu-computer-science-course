#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

while True:
  try:
    annualSalary = float(input('Enter annual salary here: '))
    break
  except:
    continue

#semiAnnualRaise = 0.07
#fullCost = 1000000
#downPayment = fullCost * 0.25

# This is to store the salary record; 1 entry [difference]
record = list()

def difference(annualSalary, portionSaved, downPayment, semiAnnualRaise):
  currentSavings = 0
  month = 0
  
  while True:
    
    if currentSavings >= downPayment:
      difference = downPayment - currentSavings
      record.append(difference)
      print(month)
      break

    if month % 6 == 0 and month != 0:
      annualSalary += annualSalary * semiAnnualRaise
    
    monthlyReturnOfInvestment = currentSavings * (0.04 / 12)
    monthlySavings = (annualSalary / 12) * portionSaved
    currentSavings += monthlySavings + monthlyReturnOfInvestment
    month += 1

    print('Month: ', month, 'Annual salary:', annualSalary)
    
  
DOWNPAYMENT = 800000 * 0.25
difference(annualSalary, 0.1, DOWNPAYMENT, 0.03)

print(record)