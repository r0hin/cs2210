// GraphNode.java – Rohin Arya CS2210 – Assignment 3
// This class represents a node in a graph.

public class GraphNode {
	private int name; // Name of the node
	private boolean mark; // Mark of the node

	// Constructor
	public GraphNode(int name) {
		this.name = name;
	}

	// Setter for mark
	public void mark(boolean mark) {
		this.mark = mark;
	}

	// Getter for mark
	public boolean isMarked() {
		return mark;
	}

	// Getter for name
	public int getName() {
		return name;
	}
}
