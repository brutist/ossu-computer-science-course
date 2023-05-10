import string

title = 'The pu!@rplE!!cow is soft and cuddly'
title = title.lower()
phrase = 'purple Cow'

for i in title:
    if i in string.punctuation:
        title = title.replace(i, '')

result = phrase.lower() in title
print('Phrase:', phrase)
print('Title:', title)
print(result)        
