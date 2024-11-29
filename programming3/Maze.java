// Maze.java – Rohin Arya CS2210 – Assignment 3
// This class represents a maze and provides a method to solve it.

// Imports for reading the input file:
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
// Import for exceptions:
import java.io.IOException;
// Imports for the graph and solving:
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Maze {
	private Graph graph; // Graph representing the maze
	private int start; // Start node
	private int end; // End node
	private int coins; // Number of coins

	private List<GraphNode> path; // Path of nodes for solution

	// Constructor, reads the input file
	public Maze(String inputFile) throws MazeException {
		BufferedReader inputReader = null;
		try {
			// Read the input file
			inputReader = new BufferedReader(new FileReader(new File(inputFile)));
			readInput(inputReader);
		} catch (IOException e) {
			throw new MazeException("Error reading input file: " + e.getMessage());
		} catch (GraphException e) {
			throw new MazeException("Error creating graph: " + e.getMessage());
		}
	}

	// Getter for the graph
	public Graph getGraph() {
		return graph;
	}

	// Solves the maze
	public Iterator<GraphNode> solve() {
		try {
			path = new ArrayList<>(); // Reset/initialize the path
			boolean found = DFS(coins, graph.getNode(start), new ArrayList<GraphNode>());
			if (found) {
				// Return an iterator over the path
				return path.iterator();
			} else {
				// No solution
				return null;
			}
		} catch (GraphException e) {
			return null;
		}
	}

	// I created a recursive DFS solution which explores all possible paths.
	// k is the number of remaining coins, current is the current node, and
	// traversed is the list of nodes that have been visited
	private boolean DFS(int k, GraphNode current, ArrayList<GraphNode> traversed) throws GraphException {
		// Base case: current is null!
		if (current == null) {
			return false;
		}

		// End case: current is the target node!
		if (current.getName() == end) {
			traversed.add(current); // Add the end node to the traversed list

			while (!traversed.isEmpty()) {
				// Move all traversed nodes to path
				GraphNode traversedNode = traversed.remove(traversed.size() - 1);
				path.add(traversedNode);
			}

			return true; // Found a solution
		}

		// Explore all neighbors and traverse them before backtracking!
		Iterator<GraphEdge> edges = graph.incidentEdges(current);
		while (edges.hasNext()) {
			// Get the neighbor
			GraphEdge edge = edges.next();
			GraphNode neighbor = edge.secondEndpoint();

			if (neighbor.getName() == current.getName()) {
				// Since edges are defined as either a->b or b->a (eg. go up, down, left, or
				// right), we have to check which one is the neighbor to the current.
				neighbor = edge.firstEndpoint();
			}

			// Print info line (uncomment for dubgging)
			// System.err.println("Analyze: " + neighbor.getName() + " as at "
			// + current.getName());

			int coinsRequired = edge.getType();
			String label = edge.getLabel();

			// If the node was traversed, skip
			boolean hasBeenTraversed = false;
			for (GraphNode node : traversed) {
				if (node.getName() == neighbor.getName()) {
					hasBeenTraversed = true;
				}
			}

			// The reason we skip is because we've already previously checked solutions at
			// this node, which are necessarily shorter than in a loop
			if (hasBeenTraversed) {
				continue;
			}

			// If the connection is a wall, skip worrying about this neighbour
			if (label.equals("wall")) {
				continue;
			}

			// If the connection is a door
			if (label.equals("door")) {
				// Check if we have enough coins
				if (k - coinsRequired < 0) {
					continue;
				}

				// Add current to traversed and explore it!
				traversed.add(current);
				boolean found = DFS(k - coinsRequired, neighbor, traversed);
				if (found) {
					return true;
				}

				// Its not the correct path, so un-traverse it (backtrack)!
				traversed.remove(traversed.size() - 1);
			} else {
				// Add current to traversed and explore it!
				traversed.add(current);
				boolean found = DFS(k, neighbor, traversed);
				if (found) {
					return true;
				}

				// Its not the correct path, so un-traverse it (backtrack)!
				traversed.remove(traversed.size() - 1); // Backtrack
			}
		}

		// No or no efficient solution from this node
		return false;
	}

	// Reads the input file and populates the graph, start, end, and coins
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		inputReader.readLine(); // Read and discard scale
		int A = Integer.parseInt(inputReader.readLine()); // Maze width
		int L = Integer.parseInt(inputReader.readLine()); // Maze length
		this.coins = Integer.parseInt(inputReader.readLine()); // Number of coins

		// Since the graph is only "room" nodes, there are L*A nodes
		this.graph = new Graph((L * A));

		// Input file dimensions, logically
		int rows = 2 * L - 1;
		int cols = 2 * A - 1;

		// Setup an array of rows according to the file
		String[] maze = new String[rows];
		for (int i = 0; i < rows; i++) {
			maze[i] = inputReader.readLine();
		}

		// ROOMS are: row even indices AND col even indices
		// So let's traverse rooms and find the start and end
		for (int i = 0; i < rows; i += 2) {
			for (int j = 0; j < cols; j += 2) {
				char current = maze[i].charAt(j);
				if (current == 's') {
					start = (i / 2) * A + (j / 2);
				} else if (current == 'x') {
					end = (i / 2) * A + (j / 2);
				}
			}
		}

		// Horizontal edges are: row even indices AND col odd indices
		for (int i = 0; i < rows; i += 2) {
			for (int j = 1; j < cols; j += 2) {
				int lowerCol = j - 1;
				int lowerRow = i;

				int upperCol = j + 1;
				int upperRow = i;

				// We're connecting row i, col (j-1) to row i, col (j+1)
				// Translate these coordinates to room indices by dividing by 2

				int leftRoom = ((lowerRow) / 2) * A + (lowerCol / 2);
				int rightRoom = ((upperRow) / 2) * A + (upperCol / 2);

				char current = maze[i].charAt(j);
				if (current == 'w') {
					// Wall edge, use label "wall"
					insertEdge(leftRoom, rightRoom, 0, "wall");
				} else if (current == 'c') {
					// Corridor edge, use label "corridor"
					insertEdge(leftRoom, rightRoom, 0, "corridor");
				} else if (Character.isDigit(current)) {
					// Door edge, use linkType as the cost
					int cost = Character.getNumericValue(current);
					insertEdge(leftRoom, rightRoom, cost, "door");
				}
			}
		}

		// VERTICAL edges are: row odd indices AND col even indices
		for (int i = 1; i < rows; i += 2) {
			for (int j = 0; j < cols; j += 2) {
				int lowerCol = j;
				int lowerRow = i - 1;

				int upperCol = j;
				int upperRow = i + 1;

				// We're connecting row (i-1), col j to row (i+1), col j
				// Translate these coordinates to room indices by dividing by 2

				int lowerRoom = ((lowerRow) / 2) * A + (lowerCol / 2);
				int upperRoom = ((upperRow) / 2) * A + (upperCol / 2);

				char current = maze[i].charAt(j);
				if (current == 'w') {
					// Wall edge, use label "wall"
					insertEdge(upperRoom, lowerRoom, 0, "wall");
				} else if (current == 'c') {
					// Corridor edge, use label "corridor"
					insertEdge(upperRoom, lowerRoom, 0, "corridor");
				} else if (Character.isDigit(current)) {
					// Door edge, use linkType as the cost
					int cost = Character.getNumericValue(current);
					insertEdge(upperRoom, lowerRoom, cost, "door");
				}
			}
		}

	}

	// Helper method to insert an edge
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {

		// Get relevant nodes from graph
		GraphNode graphNode1 = graph.getNode(node1);
		GraphNode graphNode2 = graph.getNode(node2);

		if (graphNode1 == null || graphNode2 == null) {
			throw new GraphException("Invalid nodes for edge insertion.");
		}

		// Insert the edge
		this.graph.insertEdge(graphNode1, graphNode2, linkType, label);
	}
}
