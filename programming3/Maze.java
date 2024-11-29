import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

			System.err.println("Start: " + start + " End: " + end + " Coins: " + coins);
			path = new ArrayList<>(); // Initialize the path
			boolean found = DFS(coins, graph.getNode(start), new ArrayList<GraphNode>());
			if (found) {
				return path.iterator();
			} else {
				return null;
			}
		} catch (GraphException e) {
			System.err.println("NO sol");
			return null;
		}
	}

	private boolean DFS(int k, GraphNode current, ArrayList<GraphNode> traversed) throws GraphException {
		// Base case: current is null
		if (current == null) {
			return false;
		}

		// End case: found the target node
		if (current.getName() == end) {
			// Add the end node to the traversed list
			traversed.add(current);

			// Move all traversed nodes to path
			while (!traversed.isEmpty()) {
				GraphNode thing = traversed.remove(traversed.size() - 1);
				System.err.println("Adding " + thing.getName() + " to path");
				path.add(thing);
			}
			return true;
		}

		// Explore neighbors
		Iterator<GraphEdge> edges = graph.incidentEdges(current);
		while (edges.hasNext()) {
			GraphEdge edge = edges.next();
			GraphNode neighbor = edge.secondEndpoint();

			if (neighbor.getName() == current.getName()) {
				neighbor = edge.firstEndpoint();
			}

			System.err.println("Analyzing " + neighbor.getName() + " as at " + current.getName());

			int coinsRequired = edge.getType();
			String label = edge.getLabel();

			// Check if the node has already been traversed
			boolean hasBeenTraversed = false;
			for (GraphNode node : traversed) {
				if (node.getName() == neighbor.getName()) {
					hasBeenTraversed = true;
				}
			}

			if (hasBeenTraversed) {
				System.err.println("Already been here, skipping");
				continue;
			}

			if (label.equals("wall")) {
				System.err.println("Wall, can't go to " + neighbor.getName());
				continue;
			}

			if (label.equals("door")) {
				if (k - coinsRequired < 0) {
					System.err.println("Door, can't go to " + neighbor.getName());
					continue;
				}

				// Add current to traversed and explore
				traversed.add(current);
				System.err.println("Door, going to " + neighbor.getName());
				boolean found = DFS(k - coinsRequired, neighbor, traversed);
				if (found) {
					return true;
				}
				traversed.remove(traversed.size() - 1); // Backtrack
			} else {
				// Add current to traversed and explore
				traversed.add(current);
				System.err.println("Corridor, going to " + neighbor.getName());
				boolean found = DFS(k, neighbor, traversed);
				if (found) {
					return true;
				}
				traversed.remove(traversed.size() - 1); // Backtrack
			}
		}

		return false;
	}

	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		inputReader.readLine(); // Read and discard scale
		int A = Integer.parseInt(inputReader.readLine()); // Maze width
		int L = Integer.parseInt(inputReader.readLine()); // Maze length
		this.coins = Integer.parseInt(inputReader.readLine()); // Number of coins

		// My graph is going to be only rooms
		// So it will have L*A nodes
		System.err.println(L * A);
		this.graph = new Graph((L * A));

		// Input file dimensions
		int rows = 2 * L - 1;
		int cols = 2 * A - 1;

		// Setup the maze string from input
		String[] maze = new String[rows];
		for (int i = 0; i < rows; i++) {
			maze[i] = inputReader.readLine();
		}

		// ROOMS are: row even indices AND col even indices
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

		// To get the room index from the file row/col,
		// we know that every col odd element is removed, and num cols odd = num cols
		// even + 1
		// Tehrefore new col = col / 2 (integer divison down)
		// we know that every row odd element is removed, and num rows odd = num rows
		// even + 1
		/// Therefore new row = row / 2
		// Attention should be taken to only apply this to rooms, not connectors
		// To get the index from new col/row, need to multiply row by A and add col

		// Horizontal edges are: row even indices AND col odd indices
		for (int i = 0; i < rows; i += 2) {
			for (int j = 1; j < cols; j += 2) {

				// The connector is at row, col
				int lowerCol = j - 1;
				int lowerRow = i;

				int upperCol = j + 1;
				int upperRow = i;

				System.err.println("Connecting cols " + lowerCol + " and " + upperCol + " at row " + i);

				int leftRoom = ((lowerRow) / 2) * A + (lowerCol / 2);
				int rightRoom = ((upperRow) / 2) * A + (upperCol / 2);

				System.err.println("Connecting " + leftRoom + " to " + rightRoom);

				char current = maze[i].charAt(j);
				if (current == 'w') {
					insertEdge(leftRoom, rightRoom, 0, "wall");
				} else if (current == 'c') {
					insertEdge(leftRoom, rightRoom, 0, "corridor");
				} else if (Character.isDigit(current)) {
					int cost = Character.getNumericValue(current);
					insertEdge(leftRoom, rightRoom, cost, "door");
				}

				System.err.println("Connected");
			}
		}

		// Go through VERTICAL edges (row odd indices, even cols)
		for (int i = 1; i < rows; i += 2) {
			for (int j = 0; j < cols; j += 2) {
				// The edge shall connect the upper room to the lower room
				int lowerCol = j;
				int lowerRow = i - 1;

				int upperCol = j;
				int upperRow = i + 1;

				System.err.println("Connecting rows " + lowerRow + " and " + upperRow + " at col " + j);

				int lowerRoom = ((lowerRow) / 2) * A + (lowerCol / 2);
				int upperRoom = ((upperRow) / 2) * A + (upperCol / 2);

				System.err.println("Connecting " + lowerRoom + " to " + upperRoom);

				char current = maze[i].charAt(j);
				if (current == 'w') {
					insertEdge(upperRoom, lowerRoom, 0, "wall");
				} else if (current == 'c') {
					insertEdge(upperRoom, lowerRoom, 0, "corridor");
				} else if (Character.isDigit(current)) {
					int cost = Character.getNumericValue(current);
					insertEdge(upperRoom, lowerRoom, cost, "door");
				}

				System.err.println("Connected");
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
