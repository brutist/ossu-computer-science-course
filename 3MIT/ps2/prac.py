'''
This file is created to replicate the program in 6.0002 Introduction to Computational Thinking and Data Science.

All codes here were created from scratch after watching the video '3. Graph-theoretic Models'
'''
import random

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


class Edge(object):
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
        return self.source.getName() + ' ->> ' + self.source.getName()

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
            if n.getName() == name:
                return n
        raise NameError(name)

    def allNodes(self):
        nodes = []
        for node in self.edges:
            nodes.append(node)
        
        return nodes

    def __str__(self):
        # prints all of the sources and its destinations
        result = ''
        for src in self.edges:
            for dest in self.edges[src]:
                result = result + src.getName() + ' ->> ' + dest.getName() + '\n'

        return result[:-1] # omit final newline

class Graph(Digraph):
    def __init__(self):
        Digraph.__init__(self)
    # adds a similar edge from the destination to the source (non-directional graph)
    def addEdge(self, edge):
        Digraph.addEdge(self, edge)
        rev = Edge(edge.getDestination(), edge.getSource())
        Digraph.addEdge(self, rev)


def printPath(path):
    # Assumes path is a list of nodes
    result = ''
    for i in range(len(path)):
        result = result + str(path[i])
        if i != len(path) - 1:
            result = result + ' ->> '

    return result


def buildCityGraph(graphType):
    g = graphType()
    for name in ('Boston', 'Providence', 'New York', 'Chicago',
                 'Denver', 'Phoenix', 'Los Angeles'): #Create 7 nodes
        g.addNode(Node(name))
    g.addEdge(Edge(g.getNode('Boston'), g.getNode('Providence')))
    g.addEdge(Edge(g.getNode('Boston'), g.getNode('New York')))
    g.addEdge(Edge(g.getNode('Providence'), g.getNode('Boston')))
    g.addEdge(Edge(g.getNode('Providence'), g.getNode('New York')))
    g.addEdge(Edge(g.getNode('New York'), g.getNode('Chicago')))
    g.addEdge(Edge(g.getNode('Chicago'), g.getNode('Denver')))
    g.addEdge(Edge(g.getNode('Chicago'), g.getNode('Phoenix')))
    g.addEdge(Edge(g.getNode('Denver'), g.getNode('Phoenix')))
    g.addEdge(Edge(g.getNode('Denver'), g.getNode('New York')))
    g.addEdge(Edge(g.getNode('Los Angeles'), g.getNode('Boston')))
    return g

def DFS(graph, start, end, path, shortest, toPrint=False):
    '''
    graph (graph object) - could be a digraph or a graph
    start and end (node object) - the nodes to find a path from and to
    path (list) - MUST BE SET TO EMPTY LIST FIRST ([])
    shortest (None) - MUST BE SET TO NONE FIRST
    toPrint (bool) - True if you want to print some debugging messages; false otherwise

    returns shortest (list of nodes) - the shortest path that leads from start to end nodes. 
                                        This is a list of nodes.
    '''
    path = path + [start]

    if toPrint:
        print('Current DFS path:', printPath(path))
    if start == end:
        return path
    for node in graph.getChildrenOf(start):
        if node not in path: # this avoids cycles
            if shortest == None or len(path) < len(shortest):
                newPath = DFS(graph, node, end, path, shortest, toPrint)
                if newPath != None:
                    shortest = newPath
        
        elif toPrint:
            print('Already visited node', node)

    return shortest


def shortestPath(graph, start, end, toPrint=False):
    # uses DFS to find the shortest path between the given start and end nodes
    # assumes that the start and end params are NODES.
    return DFS(graph, start, end, [], None, toPrint)

def testSP(source, destination):
    '''
    source and destination (string) - name of the source and destination nodes.

    prints the shortest path between the source and destination nodes
    returns nothing
    '''
    g = buildCityGraph(Digraph)
    sp = shortestPath(g, g.getNode(source), g.getNode(destination), toPrint=True)
    
    if sp != None:
        print('Shortest path from', source, 'to', destination, ' is', printPath(sp))
    else:
        print('No available path from', source, 'to', destination)

def randomNodeName(graph):
    '''
    Takes in a graph object and returns a random name of a node in that graph.
    '''
    nodes = graph.allNodes()

    return random.choice(nodes).getName()
    
if __name__ == '__main__':
    graph = buildCityGraph(Graph)
 

    TEST = 10

    for i in range(TEST):
        testSP(randomNodeName(graph), randomNodeName(graph))
        print()
    