import string
import math

def build_shift_dict(shift):
    '''
    Creates a dictionary that can be used to apply a cipher to a letter.
    The dictionary maps every uppercase and lowercase letter to a
    character shifted down the alphabet by the input shift. The dictionary
    should have 52 keys of all the uppercase letters and all the lowercase
    letters only.
    
    shift (integer): the amount by which to shift every letter of the 
    alphabet. 0 <= shift < 26

    Returns: a dictionary mapping a letter (string) to 
                another letter (string). 
    '''
    # PSEUDOCODE
    # ASSERT that the shift value be only within 0 to 26 only
    assert shift >= 0 and shift < 26, 'input shift; out of range'

    # create a list of letters of the alphabet (small and capital letters) and instantiate the shift dictionary
    small_letters = list(string.ascii_lowercase)
    capital_letters = list(string.ascii_uppercase)
    all_letters = small_letters + capital_letters
    shifted_letters = dict()

    # create a dictionary (key = letter) with (value = letter - input shift)
    for key in all_letters:
        if key.islower():
            shifted_letters[key] = small_letters[small_letters.index(key) - shift]
        else:
            shifted_letters[key] = capital_letters[capital_letters.index(key) - shift]
    
    return shifted_letters

print(build_shift_dict(7))


print(1%10)
print(1//10)

x = 2
x = x + 2

y = 2
y = 2 + y

print("x: ", x, "y: ", y)

def intToString(i):
    s = '0123456789'
    if i == 0:
        return '0'
    
    convert = ''
    while i > 0:
        convert = s[i%10] + convert
        i = i // 10

    return convert

result = 'PASSED'
for j in range(300):
    if str(j) != intToString(j):
        result = 'FAILED'
        print(j)
        break

print("IntToString Test result:", result)


def factorial(n):
    if n <= 1:
        return 1
    else:
        return n * factorial(n - 1)

result = 'PASSED'
for j in range(10):
    if math.factorial(j) != factorial(j):
        result = 'FAILED'

print('Factorial Test Result:', result)
print(2**2)
