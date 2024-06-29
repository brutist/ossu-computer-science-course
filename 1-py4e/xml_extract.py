import urllib.request, urllib.parse, urllib.error
import xml.etree.ElementTree as ET
import ssl

#Ignore ssl certificate errors
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

url = input('Enter location: ')


data = urllib.request.urlopen(url, context=ctx).read()
root = ET.fromstring(data)
counts = root.findall('.//count')

counter = 0
sum = 0
 
for count in counts:
  number = counts[counter].text
  sum += int(number)
  counter += 1

  
print(sum)


