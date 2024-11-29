// Graph.java – Rohin Arya CS2210 – Assignment 3
// This class represents a graph and provides methods to manipulate it.

// Imports for the adjacency list:
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {
	// Adjacency list to store graph nodes and their edges
	private Map<GraphNode, List<GraphEdge>> adjList = new HashMap<>();

	// Constructor initializes the graph with 'n' nodes
	public Graph(int n) {
		for (int i = 0; i < n; i++) {
			GraphNode node = new GraphNode(i);
			adjList.put(node, new ArrayList<>());
		}
	}

	// Method to add an edge to the graph
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		// Validate if nodes exist
		if (!adjList.containsKey(nodeu) || !adjList.containsKey(nodev)) {
			throw new GraphException("One or both nodes do not exist in the graph");
		}

		// Check if edge already exists between nodeu and nodev
		for (GraphEdge edge : adjList.get(nodeu)) {
			if (edge.secondEndpoint().equals(nodev) || edge.firstEndpoint().equals(nodev)) {
				throw new GraphException("Edge already exists between the nodes");
			}
		}

		// Create and insert the edge
		GraphEdge newEdge = new GraphEdge(nodeu, nodev, type, label);
		adjList.get(nodeu).add(newEdge);
		adjList.get(nodev).add(newEdge);
	}

	// Method to get a node by its name
	@Override
	public GraphNode getNode(int u) throws GraphException {
		// Search through keySet to find matches
		for (GraphNode node : adjList.keySet()) {
			if (node.getName() == u) { // == is valid, since names are integers
				return node;
			}
		}

		throw new GraphException("Node does not exist in the graph");
	}

	// Method to get all edges incident on a node
	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		// Validate if the node exists
		if (!adjList.containsKey(u)) {
			throw new GraphException("Node does not exist in the graph");
		}

		// Return an iterator over the edges connected to the node
		return adjList.get(u).iterator();
	}

	// Method to get an edge between two nodes
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		// Validate if both nodes exist
		if (!adjList.containsKey(u) || !adjList.containsKey(v)) {
			throw new GraphException("One or both nodes do not exist in the graph");
		}

		// Search for an edge between u and v
		for (GraphEdge edge : adjList.get(u)) {
			if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) {
				return edge;
			}
		}

		// No edge found, return null
		return null;
	}

	// Method to check if two nodes are adjacent
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		// Check if an edge exists here
		return getEdge(u, v) != null;
	}
}