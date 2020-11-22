weighted undirected graph
This assignment package represents an weighted undirected graph.
The graph has a collection of his verticals, the node info tag represents temporary distance weight from starting node (used by the algorithms)
and a collection of the verticals neighbors that represents the edges and their weight ,
and additional methods include addNode, removeNode, hasEdge, connect, removeEdge...

private Object node_info
node_info represents a vertex info in a graph. It has a unique key and weight tag.

private Object neighbors
each neighbor has a unique key and he keeps his neighbors collection node info with tag that represents the edge weight

weighted_graph_algorithms class
Directory of algorithms based on the dijkstra's algorithm. The algorithm scans the graph by the weight distance from the first node.

The algorithms are:
•init()-
setting a pointer to a graph
•copy()-
make a deep copy of the graph
•isConnected()-
try to reach all the verticals in the graph, if the dijkstra's algorithm can't reach all verticals in the graph the function returns false and if is success returns true
•shortestPathDist(int src, int dest)-
try to reach from the vertex with src key to the vertex with dest key, the algorithm returns the weight distance.
•shortestPath(int src, int dest)-
try to reach from the vertex with src key to the vertex with dest key, the algorithm keeps for each vertex his parent, and creates a list of the path.
•save()
saves the graphs vertexes and edges in a format:
"key
ni-key,ni-weight;ni-key,ni-weight;.......
key
......
"
•load()
loads the graphs vertexes and edges in a format:
"key
ni-key,ni-weight;ni-key,ni-weight;.......
key
......
"
Data Structures
HashMap:
I used HashMap<Integer,node_info> to represent the verticals of the graph and the neighbors of each vertex.
methods: put, remove, containsKey O(1) and value (n).
I used HashMap also to keep information during the algorithms.
PriorityQueue:
I used a PriorityQueue to keep the verticals that the algorithm needs to check.
