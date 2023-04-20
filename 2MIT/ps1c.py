#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

# This is savings - downPayment
record = list()

def bestRate(annualSalary):
  currentSavings = 0
  threeYears = range(36)
  downPayment = 1000000 * 0.25
  semiAnnualRaise = 0.07
  difference = currentSavings - downPayment
  
  low = 0
  high = 1000
  average = (low + high) / 2

  
  while True:
    
    if difference < 0:
      low = average
      portionSaved = int(average)
      print('x')

    if difference > 100:
      high = average
      portionSaved = int(average)
      print('x')
      continue

    else: 
      print(int(average))
      break
  
    for month in threeYears:
      
      # check if its time to end
      if currentSavings < downPayment and month == len(threeYears):
        print('It is not possible to pay the down payment in three years.')
        record.append(difference)
        break
      
      if currentSavings >= downPayment:
        record.append(difference)
        print('It would take you', month, 'months to save for the down payment')
        break
  
      # check for salary raise time
      if month % 6 == 0 and month != 0:
        annualSalary += annualSalary * semiAnnualRaise
  
      # keep track of the money.
      monthlyReturnOfInvestment = currentSavings * (0.04 / 12)
      monthlySavings = (annualSalary / 12) * portionSaved
      currentSavings += monthlySavings + monthlyReturnOfInvestment
    
  
      # just to see the increase
      print('Month: ', month, 'Annual salary:', annualSalary, 'Current savings:', currentSavings)


while True:
  try:
    annualSalary = float(input('Enter annual salary here: '))
    break
  except:
    continue


# TO BE DETERMINED BY THE BINARY SEARCH
# PORTION_SAVED = 

bestRate(annualSalary)

print(record)