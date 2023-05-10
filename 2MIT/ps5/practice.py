import string

title = 'The purplE!!cow is soft and cuddly'
title = title.lower()
word_separator = string.punctuation + ' '

for j in title:
    if j in word_separator:
        title = title.replace(j, ' ')

title = ' '.join(title.split())

print('Title:', title)
print(word_separator.index(string.punctuation))