'''
This file is created to replicate the program in 6.0002 Introduction to Computational Thinking and Data Science.

All codes here were created from scratch after watching the video '3. Graph-theoretic Models'
'''

class Node(object):
    '''
    Each node has a name
    '''

    def __init__(self, name):
        self.name = name
    
    def getName(self):
        return self.name
    
    def __str__(self):
        return self.name


class Edges(object):
    '''
    Edges are represented with a source and a destination both of which are node Objects
    '''
    def __init__(self, src, dest):
        self.source = src
        self.destination = dest
    
    def getSource(self):
        return self.source

    def getDestination(self):
        return self.destination
    
    def __str__(self):
        return self.source.getName() + ' -->> ' + self.source.getName()