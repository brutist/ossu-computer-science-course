import string
from datetime import datetime
import zoneinfo

title = 'The purplE!!cow is soft and cuddly'
title = title.lower()
word_separator = string.punctuation + ' '

for j in title:
    if j in word_separator:
        title = title.replace(j, ' ')

title = ' '.join(title.split())

print('Title:', title)
print(word_separator.index(string.punctuation))


date_string = "3 Oct 2016 17:00:10"
date = datetime.strptime(date_string, '%w %b %Y %H:%M:%S')
print('Date:', date.replace(tzinfo = zoneinfo.ZoneInfo('US/Eastern')))
print('Modified Date:', date)
