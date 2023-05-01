###################################################################################################################

# I don't want to base to the skeleton code provided. So I will be creating my own complete implementation of this 
# problem set.

# Date started : May 1, 2023
# Date endded : 

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
    id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name    TEXT UNIQUE
);

CREATE TABLE Course  (
    id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    title  TEXT UNIQUE
);

CREATE TABLE Member  (
    id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    role  INTEGER
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
  


      


