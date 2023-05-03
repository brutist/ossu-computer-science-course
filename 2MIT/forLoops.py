

tst = 'length'
tst1 = 'l_ngth'

for x, y in zip(tst, tst1):
  if x == y:
    print(x + ": True")
  else:
    print(x + ": False")

string = 'jonathan'
list = list(string)

print(list)
print(len(string))
print(string[0])