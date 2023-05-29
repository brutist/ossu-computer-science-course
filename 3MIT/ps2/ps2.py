# 6.0002 Problem Set 5
# Graph optimization
# Name: Jonathan Mauring Jr
# Collaborators: None
# Time:

#
# Finding shortest paths through MIT buildings
#
import unittest
from graph import Digraph, Node, WeightedEdge
import time
#
# Problem 2: Building up the Campus Map
#
# Problem 2a: Designing your graph
#
# What do the graph's nodes represent in this problem? What
# do the graph's edges represent? Where are the distances
# represented?
#
# Answer: The nodes are the buildings and the edges are the total distance and total outdoor distance from 
#          one building to the next (denoted by building numbers). The distances were represented as edges
#           which will be stored as a weighted edge- a value in dictionary of nodes


# Problem 2b: Implementing load_map
def load_map(map_filename):
    """
    Parses the map file and constructs a directed graph

    Parameters:
        map_filename : name of the map file

    Assumes:
        Each entry in the map file consists of the following four positive
        integers, separated by a blank space:
            From To TotalDistance DistanceOutdoors
        e.g.
            32 76 54 23
        This entry would become an edge from 32 to 76.

    Returns:
        a Digraph representing the map
    """

    # TODO
    print("Loading map from file...")

    # open file
    filhand = open(map_filename)
    
    g = Digraph()
    for d in filhand:
        d = d.strip().split(' ')
        src = Node(d[0])
        dest = Node(d[1])
        edge = WeightedEdge(src, dest, int(d[2]), int(d[3]))

        # create nodes in the map
        if not g.has_node(src):
            g.add_node(src)
        if not g.has_node(dest):
            g.add_node(dest)

        # add edge to the graph
        g.add_edge(edge)
    
    filhand.close()
    return g

# Problem 2c: Testing load_map
# Include the lines used to test load_map below, but comment them out
class testLoadMap(unittest.TestCase):
    def test_load_map(self):
        expected = 'a->b (10, 9)\na->c (12, 2)\nb->c (1, 1)'
        graph = load_map('test_load_map.txt')
        self.assertEqual(str(graph), expected)
    
    
#
# Problem 3: Finding the Shortest Path using Optimized Search Method
#
# Problem 3a: Objective function
#
# What is the objective function for this problem? What are the constraints?
#
# Answer: The objective function is minimizing the distance traveled. 
#           i.e best path is one with the smallest total distance
#         The constraint is sum of outdoor distance <= maximum distance outdoors.
        
        
def total_distance(digraph, path):
    '''
    Finds the total distance of a given path.

    Parameters:
        digraph: Digraph instance
            The graph to which the given path is found
        path: list of strings
            Assumes a non-empty list and strings are node names found in digraph.
            Represent the path from start to end, given that for every succeeding 
            nodes there is a weighted edge connecting them.

    Returns
        distance: int
            This is the total distance from start to end node in the path
    '''
    distance = 0
    for i in range(1, len(path)):
        
        for edge in get_edges_for_node(Node(path[i-1])):
            if edge.get_destination() == path[i]:
                total += edge.get_total_distance()

    return distance

# Problem 3b: Implement get_best_path
def get_best_path(digraph, start, end, path, max_dist_outdoors, best_dist,
                  best_path):
    """
    Finds the shortest path between buildings subject to constraints.

    Parameters:
        digraph: Digraph instance
            The graph on which to carry out the search
        start: string
            Building number at which to start
        end: string
            Building number at which to end
        path: list composed of [[list of strings], int, int]
            Represents the current path of nodes being traversed. Contains
            a list of node names, total distance traveled, and total
            distance outdoors.
        max_dist_outdoors: int
            Maximum distance spent outdoors on a path
        best_dist: int
            The smallest distance between the original start and end node
            for the initial problem that you are trying to solve
        best_path: list of strings
            The shortest path found so far between the original start
            and end node.

    Returns:
        A tuple with the shortest-path from start to end, represented by
        a list of building numbers (in strings), [n_1, n_2, ..., n_k],
        where there exists an edge from n_i to n_(i+1) in digraph,
        for all 1 <= i < k and the distance of that path.

        If there exists no path that satisfies max_total_dist and
        max_dist_outdoors constraints, then return None.
    """
    print('PATH:' ,path)
    print('Best dist:', best_dist)
    print('Best path:', best_path)
    print()

    # keep track of the current path
    path[0] += [start]

    if (not digraph.has_node(Node(start)) or (not digraph.has_node(Node(end)))):
        raise ValueError('nodes not in graph')

    elif start == end:
        return path[0], path[1]

    else:
        # path is represented with [[list of node names], total_distance, outdoor_distance]
        # weighted edges are represented [src, dest, total_distance, outdoor_distance]
        for e in digraph.get_edges_for_node(Node(start)):
            child = e.get_destination().get_name()
            dist = e.get_total_distance() + path[1]
            outdoor_dist = e.get_outdoor_distance() + path[2]

            if child not in path[0] and outdoor_dist <= max_dist_outdoors:
                updated_path = [path[0], dist, outdoor_dist]
            
                possible_path = get_best_path(digraph, child, end, updated_path,
                max_dist_outdoors, best_dist - dist, best_path)

                if possible_path != None:
                    if possible_path[1] < best_dist:
                        best_path = possible_path[0]
                        best_dist = possible_path[1]

   
    return best_path, best_dist




# Problem 3c: Implement directed_dfs
def directed_dfs(digraph, start, end, max_total_dist, max_dist_outdoors):
    """
    Finds the shortest path from start to end using a directed depth-first
    search. The total distance traveled on the path must not
    exceed max_total_dist, and the distance spent outdoors on this path must
    not exceed max_dist_outdoors.

    Parameters:
        digraph: Digraph instance
            The graph on which to carry out the search
        start: string
            Building number at which to start
        end: string
            Building number at which to end
        max_total_dist: int
            Maximum total distance on a path
        max_dist_outdoors: int
            Maximum distance spent outdoors on a path

    Returns:
        The shortest-path from start to end, represented by
        a list of building numbers (in strings), [n_1, n_2, ..., n_k],
        where there exists an edge from n_i to n_(i+1) in digraph,
        for all 1 <= i < k

        If there exists no path that satisfies max_total_dist and
        max_dist_outdoors constraints, then raises a ValueError.
    """
    path = [[], 0, 0]
    best_path = None
    result = get_best_path(digraph, start, end, path, max_dist_outdoors, max_total_dist, best_path)

    if result == None:
        raise ValueError('No available paths')
 
    elif result[1] > max_total_dist:
        raise ValueError('Exceeded maximum allowable distance')
    else:
        return result[0]

# ================================================================
# Begin tests -- you do not need to modify anything below this line
# ================================================================

class Ps2Test(unittest.TestCase):
    LARGE_DIST = 99999

    def setUp(self):
        self.graph = load_map("mit_map.txt")

    def test_load_map_basic(self):
        self.assertTrue(isinstance(self.graph, Digraph))
        self.assertEqual(len(self.graph.nodes), 37)
        all_edges = []
        for _, edges in self.graph.edges.items():
            all_edges += edges  # edges must be dict of node -> list of edges
        all_edges = set(all_edges)
        self.assertEqual(len(all_edges), 129)

    def _print_path_description(self, start, end, total_dist, outdoor_dist):
        constraint = ""
        if outdoor_dist != Ps2Test.LARGE_DIST:
            constraint = "without walking more than {}m outdoors".format(
                outdoor_dist)
        if total_dist != Ps2Test.LARGE_DIST:
            if constraint:
                constraint += ' or {}m total'.format(total_dist)
            else:
                constraint = "without walking more than {}m total".format(
                    total_dist)

        print("------------------------")
        print("Shortest path from Building {} to {} {}".format(
            start, end, constraint))

    def _test_path(self,
                   expectedPath,
                   total_dist=LARGE_DIST,
                   outdoor_dist=LARGE_DIST):
        start, end = expectedPath[0], expectedPath[-1]
        self._print_path_description(start, end, total_dist, outdoor_dist)
        dfsPath = directed_dfs(self.graph, start, end, total_dist, outdoor_dist)
        print("Expected: ", expectedPath)
        print("DFS: ", dfsPath)
        self.assertEqual(expectedPath, dfsPath)

    def _test_impossible_path(self,
                              start,
                              end,
                              total_dist=LARGE_DIST,
                              outdoor_dist=LARGE_DIST):
        self._print_path_description(start, end, total_dist, outdoor_dist)
        with self.assertRaises(ValueError):
            directed_dfs(self.graph, start, end, total_dist, outdoor_dist)

    def test_path_one_step(self):
        self._test_path(expectedPath=['32', '56'])

    def test_path_no_outdoors(self):
        self._test_path(
            expectedPath=['32', '36', '26', '16', '56'], outdoor_dist=0)

    def test_path_multi_step(self):
        self._test_path(expectedPath=['2', '3', '7', '9'])

    def test_path_multi_step_no_outdoors(self):
        self._test_path(
            expectedPath=['2', '4', '10', '13', '9'], outdoor_dist=0)

    def test_path_multi_step2(self):
        self._test_path(expectedPath=['1', '4', '12', '32'])

    def test_path_multi_step_no_outdoors2(self):
        self._test_path(
            expectedPath=['1', '3', '10', '4', '12', '24', '34', '36', '32'],
            outdoor_dist=0)

    def test_impossible_path1(self):
        self._test_impossible_path('8', '50', outdoor_dist=0)

    def test_impossible_path2(self):
        self._test_impossible_path('10', '32', total_dist=100)


if __name__ == "__main__":
    unittest.main() 
    
    