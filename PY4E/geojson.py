#In this assignment you will write a Python program somewhat similar to http://www.py4e.com/code3/geojson.py. The program will prompt for a location, contact a web service and retrieve JSON for the web service and parse that data, and retrieve the first place_id from the JSON. A place ID is a textual identifier that uniquely identifies a place as within Google Maps.
#API End Points

#To complete this assignment, you should use this API endpoint that has a static subset of the Google Data:

#http://py4e-data.dr-chuck.net/json?
#This API uses the same parameter (address) as the Google API. This API also has no rate limit so you can test as often as you like. If you visit the URL with no parameters, you get "No address..." response.
#To call the API, you need to include a key= parameter and provide the address that you are requesting as the address= parameter that is properly URL encoded using the urllib.parse.urlencode() function as shown in http://www.py4e.com/code3/geojson.py

#Make sure to check that your code is using the API endpoint as shown above. You will get different results from the geojson and json endpoints so make sure you are using the same end point as this autograder is using.

#Test Data / Sample Execution

#You can test to see if your program is working with a location of "South Federal University" which will have a place_id of "ChIJNeHD4p-540AR2Q0_ZjwmKJ8".

import urllib.request, urllib.parse, urllib.error
import json
import ssl

TEST_URL = 'http://py4e-data.dr-chuck.net/json?'
GOOGLE_URL = 'https://maps.googleapis.com/maps/api/geocode/json?'
URL = ''
api_call= dict()

# Ignore SSL certificate errors
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

location = input('Enter location: ')
if len(location) < 1: exit()

chose_URL = input('Would you like to use Google API? Y or N\n ').lower()

api_call['address'] = location

if chose_URL == 'y':
  URL = GOOGLE_URL
  api_call['key'] = 'AIzaSyAekoWqVdXIEbSaL0Pm7pIuL0yhoCwd3c0'
  
elif chose_URL == 'n':
  URL = TEST_URL
  api_call['key'] = 42

else:
  print('Enter Y or N only.')
  exit()



complete_URL = URL + urllib.parse.urlencode(api_call)

file = urllib.request.urlopen(complete_URL, context = ctx)
data = file.read().decode()
jsonObject = json.loads(data)
results = jsonObject['results'][0]['place_id']


#Print some status for the user
print('Retrieving', complete_URL)
print('Retrieved', len(data), 'characters')
print('Place id', results)