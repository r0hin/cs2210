import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Maze {

	// instance variables you may need
	// a variable storing the graph, a variable storing the id of the starting node,
	// a variable storing the id of the end node
	// a variable storing the read number of coins, maybe even a variable storing
	// the path so far so that you don't perform accidental
	// (and unnecessary cycles).
	// if you maintain nodes on a path in a list, be careful to make a list of
	// GraphNodes,
	// otherwise removal from the list is going to behave in a weird way.
	// REMEMBER your nodes have a field mark.. maybe that field could be useful to
	// avoid cycles...

	private Graph graph;
	private int start;
	private int end;
	private int coins;
	private List<GraphNode> path;

	public Maze(String inputFile) throws MazeException {
		// initialize your graph variable by reading the input file!
		// to maintain your code as clean and easy to debug as possible use the provided
		// private helper method
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(new File(inputFile)));
			readInput(inputReader);
		} catch (IOException e) {
			throw new MazeException("Error reading input file: " + e.getMessage());
		} catch (GraphException e) {
			throw new MazeException("Error creating graph: " + e.getMessage());
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public Iterator<GraphNode> solve() {
		try {
			Iterator<GraphNode> result = DFS(coins, graph.getNode(start));
			// print the path
			for (GraphNode node : path) {
				System.out.print(node.getName() + " ");
			}
			System.out.println();
			return result;
		} catch (GraphException e) {
			System.err.println("NO sol");
			return null;
		}

	}

	private Iterator<GraphNode> DFS(int k, GraphNode go) throws GraphException {
		// Initialize path and mark the starting node
		path = new ArrayList<>();
		go.mark(true);
		path.add(go);

		// Base case: reached the exit with enough coins
		if (go.getName() == end && k >= 0) {
			return path.iterator();
		}

		// Get incident edges
		Iterator<GraphEdge> edges = graph.incidentEdges(go);
		while (edges.hasNext()) {
			GraphEdge edge = edges.next();
			GraphNode neighbor = edge.secondEndpoint();

			if (!neighbor.isMarked()) {
				int newK = k - (edge.getType() == 0 ? 1 : 0);
				if (newK >= 0) {
					Iterator<GraphNode> result = DFS(newK, neighbor);
					if (result != null) {
						return result;
					}
				}
			}
		}

		// Backtrack
		path.remove(path.size() - 1);
		go.mark(false);
		return null;

	}

	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		int S = Integer.parseInt(inputReader.readLine()); // Read and discard scale
		int A = Integer.parseInt(inputReader.readLine()); // Maze width
		int L = Integer.parseInt(inputReader.readLine()); // Maze length
		int k = Integer.parseInt(inputReader.readLine()); // Number of coins

		// Maze dimensions
		int rows = 2 * L - 1;
		int cols = 2 * A - 1;

		// Graph structure
		this.graph = new Graph(rows * cols);

		// Reading maze
		String[] maze = new String[rows];
		for (int i = 0; i < rows; i++) {
			maze[i] = inputReader.readLine();
		}

		// Parse the grid and build the graph
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char current = maze[i].charAt(j);

				if (i % 2 == 0 && j % 2 == 0) {
					// Node: room, entrance, or exit
					int currentNode = i / 2 * A + j / 2;
					if (current == 's') {
						start = currentNode;
					} else if (current == 'x') {
						end = currentNode;
					}
				} else if (i % 2 == 0 || j % 2 == 0) {
					// Edge: wall, corridor, or door
					int node1, node2;
					if (i % 2 == 0) {
						// Horizontal edge
						node1 = (i / 2) * A + (j / 2);
						node2 = node1 + 1;
					} else {
						// Vertical edge
						node1 = (i / 2) * A + (j / 2);
						node2 = node1 + A;
					}

					// Handle different edge types
					if (current == 'w') {
						insertEdge(node1, node2, 0, "wall");
					} else if (current == 'c') {
						insertEdge(node1, node2, 1, "corridor");
					} else if (Character.isDigit(current)) {
						int cost = Character.getNumericValue(current);
						insertEdge(node1, node2, cost, "door");
					}
				}
			}
		}
	}

	private void insertEdge(int node1, int node2, int linkType, String label)
			throws GraphException {
		// Get nodes from graph
		GraphNode graphNode1 = graph.getNode(node1);
		GraphNode graphNode2 = graph.getNode(node2);

		if (graphNode1 == null || graphNode2 == null) {
			throw new GraphException("Invalid nodes for edge insertion.");
		}

		this.graph.insertEdge(graphNode1, graphNode2, linkType, label);
	}

}
