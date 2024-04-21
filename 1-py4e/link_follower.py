# To run this, download the BeautifulSoup zip file
# http://www.py4e.com/code3/bs4.zip
# and unzip it in the same directory as this file

import urllib.request, urllib.parse, urllib.error
from bs4 import BeautifulSoup
import ssl

# Ignore SSL certificate errors 
# I really don't have any clue what this is for; probably error checking but idk
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

followed_links = input('Enter URL: ').split()
count = int(input('Enter count: ')) + 1
position = int(input('Enter position: ')) - 1

for x in range(count):
  url = followed_links[x]
  current_links = list()
  
  html = urllib.request.urlopen(url, context=ctx).read()
  soup = BeautifulSoup(html, 'html.parser')

  # Retrieve all of the anchor tags
  tags = soup('a')
  
  for tag in tags:
    current_links.append((tag.get('href', None)))

  followed_links.append(current_links[position])
  print('Retrieving:', followed_links[x])


