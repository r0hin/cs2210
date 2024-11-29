// GraphEdge.java – Rohin Arya CS2210 – Assignment 3
// This class represents an edge in a graph.

public class GraphEdge {
	private GraphNode origin; // First endpoint
	private GraphNode destination; // Second endpoint
	private int type; // Edge type
	private String label; // Edge label

	// Constructor, set origin, destination, type, and label
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.origin = u;
		this.destination = v;
		this.type = type;
		this.label = label;
	}

	// Get the first endpoint
	public GraphNode firstEndpoint() {
		return origin;
	}

	// Get the second endpoint
	public GraphNode secondEndpoint() {
		return destination;
	}

	// Get the edge type
	public int getType() {
		return type;
	}

	// Set the edge type
	public void setType(int type) {
		this.type = type;
	}

	// Get the edge label
	public String getLabel() {
		return label;
	}

	// Set the edge label
	public void setLabel(String label) {
		this.label = label;
	}
}
