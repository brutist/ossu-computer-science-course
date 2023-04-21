#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf

def bestRate(salary):  
  isPossible = True
  low = 0
  high = 10000
  guess = int((low + high) / 2)
  portionSaved = high / 10000
  
  while isPossible:
    annualSalary = salary
    currentSavings = 0
    threeYears = 36
    downPayment = 1000000 * 0.25
    semiAnnualRaise = 0.07
    difference = currentSavings - downPayment
    month = 0
    
    while month != threeYears:
      # check if its time to end
      if currentSavings < downPayment and month >= threeYears and portionSaved == 1:
        print('It is not possible to pay the down payment in three years.')
        break
      
      # check for salary raise time
      if month % 6 == 0 and month != 0:
        annualSalary += annualSalary * semiAnnualRaise
  
      # keep track of the money.
      monthlyReturnOfInvestment = currentSavings * (0.04 / 12)
      monthlySavings = (annualSalary / 12) * portionSaved
      currentSavings += monthlySavings + monthlyReturnOfInvestment
      month += 1

      if month == threeYears:
         difference = currentSavings - downPayment
        
      # just to see the increase
      print('Month: ', month, 'Annual salary:', annualSalary, 'Current savings:', currentSavings, 'Difference: ', difference, "Portion Saved: ", portionSaved)
      

    portionSaved = guess / 10000
    print(guess)
    
    if difference < 0:
      low = guess
      guess = low + high // 2
      continue
  
    if difference > 100:
      high = guess
      guess = low + high // 2

      continue
        
    else: 
      print(portionSaved)
      break

    # Average answer is a float with a lot of decimals. I only need two decimal place. Truncate the remaining decimals
    






while True:
  try:
    annualSalary = float(input('Enter annual salary here: '))
    break
  except:
    continue

bestRate(annualSalary)
