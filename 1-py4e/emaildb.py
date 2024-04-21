import sqlite3

# The idea of database is;
# 1. create a new database "sqlite.connect()" - This represents the connection to the database
connection = sqlite3.connect('email.sqlite')
# 2. create the cursor "connection.cursor()" - We need the cursor to execute SQL statements and fetch results from SQL queries
cursor = connection.cursor()

# This is to check if there is already a table "Counts". If true, the cursor will remove the table 
cursor.execute('DROP TABLE IF EXISTS Counts')

# 3. create the database table "cursor.execute" 
cursor.execute('CREATE TABLE Counts(org TEXT, count INTEGER)')

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
    domainName = email.split('@')[1]
 
    # Look at the DB      ? is a placeholder and domainNames is the value that will be in the placeholder
    # The "," after the domainName is to specify that this is a tuple and not a scalar string.
    # I really don't understand how that works
    cursor.execute('SELECT count FROM Counts where org = ?', (domainName,))
   
    # The cursor is now looking at the domainName but you want to 
    # change the "count" which is beside it, so use .fetchone()
    row = cursor.fetchone()

    # Check the value of the "count" and add 1 to it
    # If this is the first time seeing the domainName, insert it into the DB and set the "count" to 1
    if row is None:
        cursor.execute('INSERT INTO Counts (org, count) VALUES (?, 1)', (domainName,))
    # If there's already a "count" in the domainName, add 1
    else:
        cursor.execute('UPDATE Counts SET count = count + 1 WHERE org = ?', (domainName,))

connection.commit()


firstTen = 'SELECT org, count FROM Counts ORDER BY count DESC LIMIT 10'

for row in cursor.execute(firstTen):
    print(row[0], row[1])

cursor.close()




