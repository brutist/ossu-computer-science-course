###################################################################################################################

# I don't want to base to the skeleton code provided. So I will be creating my own complete implementation of this 
# problem set.

# Date started : May 1, 2023
# Date ended : 

###################################################################################################################

import json
import sqlite3

# PSEUDOCODE
# create a table User, Course, and Member
con = sqlite3.connect('roster.sqlite')
cur = con.cursor()

cur.executescript('''
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Member;

CREATE TABLE User  (
    id          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name        TEXT UNIQUE
);

CREATE TABLE Course  (
    id          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    title       TEXT UNIQUE
);

CREATE TABLE Member  (
    id          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    user_id     INTEGER,
    course_id   INTEGER,
    role        INTEGER
);
''')

# ask the user for file name
name = input('Enter file name here: ')

# set default file
if len(name) == 0:
  name = 'roster_data.json'

# read and parse the file to json
filhand = open(name).read()
users = json.loads(filhand)

# populate the tables from the data file
for user in users:
    name = user[0]
    title = user[1]
    role = user[2]

    if name is None:
        continue

    cur.execute('''INSERT OR IGNORE INTO User ( name )
        VALUES ( ? )''', (name, ))
    cur.execute('SELECT id FROM User WHERE name = ?', (name, ))
    user_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Course ( title )
        VALUES ( ? )''', (title, ))
    cur.execute('SELECT id FROM Course where title = ?', (title, ))
    course_id = cur.fetchone()[0]

    cur.execute('''INSERT OR REPLACE INTO Member 
        ( user_id, course_id, role )
        VALUES ( ?, ?, ? )''', 
        (user_id, course_id, role ))

    con.commit()


file = open('answer.txt', 'w')


data1 = cur.execute('''SELECT User.name,Course.title, Member.role FROM 
    User JOIN Member JOIN Course 
    ON User.id = Member.user_id AND Member.course_id = Course.id
    ORDER BY User.name DESC, Course.title DESC, Member.role DESC LIMIT 2;''')

for line in data1:
  file.write(str(line))

data2 = cur.execute('''SELECT 'XYZZY' || hex(User.name || Course.title || Member.role ) AS X FROM 
    User JOIN Member JOIN Course 
    ON User.id = Member.user_id AND Member.course_id = Course.id
    ORDER BY X LIMIT 1;''')

for line in data2:
  file.write(str(line))