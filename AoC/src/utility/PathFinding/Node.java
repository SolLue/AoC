package utility.PathFinding;

public class Node implements Comparable<Node>{
	private String data; 
	private int cost; 

	public Node(String d, int c) {
		this.data = d; 
		this.cost = c; 
	}
	public Node(String d) {
		this.data = d; 
		this.cost = 0; 
	}

	public void setCost(int c) {
		this.cost = c; 
	}

	public int getCost() {
		return this.cost; 
	}
	
	public String getData() {
		return this.data;
	}
	
	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.cost, o.cost);
	}
    
	@Override
    public int hashCode() {
        return this.data.hashCode(); // + Integer.valueOf(this.cost).hashCode();
    }

    @Override
    public boolean equals(Object o) {
    	Node c = (Node) o;
    	if(this.data.equals(c.data) && this.cost == c.cost) {
    		return true; 
    	}
    	return false;
    }
}
