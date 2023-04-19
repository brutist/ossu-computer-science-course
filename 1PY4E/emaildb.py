import sqlite3

# The idea of database is;
# 1. create a new database "sqlite.connect()" - This represents the connection to the database
connection = sqlite3.connect('email.sqlite')
# 2. create the cursor "connection.cursor()" - We need the cursor to execute SQL statements and fetch results from SQL queries
cursor = connection.cursor()

# This is to check if there is already a table "Counts". If true, the cursor will remove the table 
cursor.execute('DROP TABLE IF EXISTS Counts')

# 3. create the database table "cursor.execute" 
cursor.execute('CREATE TABLE Counts(domainName TEXT, count INTEGER)')

# Ask for file name. If no input, will default to "mbox-short.txt" file
file = input('Enter file name: ')
if len(file) < 1 :
    file = 'mbox-short.txt'

# Open file
fileHandle = open(file)

# Loop through the entire file - line by line
for line in fileHandle:
    # Check whether it's the line that we're looking for
    if not line.startswith('From: '):
        continue

    # Take the domain off the emails
    # I can choose to use regex but this one is simple enough for that
    email = line.split()[1]
    domainNames = email.split('@')[1]
 
    # Look at the DB      ? is a placeholder and domainNames is the value that will be in the placeholder
    cursor.execute('SELECT count FROM Counts where domainName = ?', ('domainNames',))

    print(domainNames)




