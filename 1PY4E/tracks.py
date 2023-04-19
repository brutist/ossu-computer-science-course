import sqlite3
import xml.etree.ElementTree as ET

input = input('Enter file name: ')
# Parse the input into a xml tree object
tree = ET.parse(input)
xmlstring = ET.tostring(tree)

print(xmlstring)
  
