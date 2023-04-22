#Assignment Instruction = https://ocw.mit.edu/courses/6-0001-introduction-to-computer-science-and-programming-in-
#python-fall-2016/8cf75481d7047180c386de3e485bd050_MIT6_0001F16_ps1.pdf
import time
# this returns the difference between current savings and downpayment
def differenceFinder(salary, rate):
  annualSalary = salary
  currentSavings = 0
  threeYears = 36
  downPayment = 1000000 * 0.25
  semiAnnualRaise = 0.07
  month = 0
  
  while month != threeYears:
      
      # check for salary raise time
    if month % 6 == 0 and month != 0:
      annualSalary += annualSalary * semiAnnualRaise
  
    # keep track of the money.
    monthlyReturnOfInvestment = currentSavings * (0.04 / 12)
    monthlySavings = (annualSalary / 12) * rate
    currentSavings += monthlySavings + monthlyReturnOfInvestment
    month += 1
    
    if month == threeYears:
      difference = currentSavings - downPayment
      #time.sleep(.1)
      #print('Month: ', month, 'Current savings:', currentSavings, 'Difference: ', difference, "Rate: ", rate)
      
    # check if its time to end
    if currentSavings < downPayment and month == threeYears and rate == float(1):
      difference = False
      break
      
  return difference


# This returns the best saving rate for a $250 000 downpayment in 36 months 
# Only accept salary as a parameter
# Returns a tuple of the "best rate" and the number of 'bisection search'
def bestRate(salary):
  allSaved = 1
  bestRate = 0
  
  difference = differenceFinder(salary, allSaved)
  
  if difference == False:
    print('It is not possible to save for the down payment in three years')
    return None
      
  
  l = 0
  h= 10000
  guess = guess = (h+l) // 2
  bisectionSteps = 0
  
  while difference < 0 or difference > 100:
    
    rate = guess / 10000
    difference = differenceFinder(salary, rate)
    bisectionSteps += 1
    
    if difference < 0:
      l = guess
     
    if difference > 100:
      h = guess

    guess = (h+l) // 2
    bestRate = rate
  

  return bestRate, bisectionSteps

notDone = True


while notDone:
  try: 
    salary = float(input('Enter annual salary here: '))
    notDone = False
  except:
    print('Enter numbers only')
    notDone = True


results = bestRate(salary)
if results != None:
  print('Best savings rate:', results[0])
  print('Steps in bisections search:', results[1])