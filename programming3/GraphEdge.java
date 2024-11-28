
public class GraphEdge {

	private GraphNode origin;
	private GraphNode destination;
	private int type;
	private String label;

	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		origin = u;
		destination = v;
		this.type = type;
		this.label = label;
	}

	public GraphNode firstEndpoint() {
		return origin;
	}

	public GraphNode secondEndpoint() {
		return destination;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
