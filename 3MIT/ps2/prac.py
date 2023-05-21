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
    Assumes that src and dest are nodes
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

class Digraph(object):
    '''
    Digraph is represented as a dictionary.
    Each nodes are the key and the values are list of destination that the node can reach.
    '''

    def __init__(self):
        self.edges = {}

    def addNode(self, node):
        # add the node as key in dictionary
        if node in self.edges:
            raise ValueError('Duplicate node')
        else:
            self.edges[node] = []
        
    def addEdge(self, edge):
        # add an edge by mapping the source as key and appending the destination to the list.
        src = edge.getSource()
        dest = edge.getDestination()
        if not (src in self.edges and dest in self.edges):
            raise ValueError('Node not in graph')
        self.edges[src].append(dest)

    def getChildrenOf(self, node):
        # returns the children of the given node
        return self.edges[node]

    def hasNode(self, node):
        # returns if the given node is in the graph
        return node in self.edges
    
    def getNode(self, name):
        # returns the node object of a name if it exists
        for n in self.edges:
            if n == name:
                return n
        raise NameError(name)

    def __str__(self):
        # prints all of the sources and its destinations
        result = ''
        for src in self.edges:
            for dest in self.edges[src]:
                result = result + src.getName() + ' ->> ' + dest.getName() + '\n'

        return result[:-1] # omit final newline