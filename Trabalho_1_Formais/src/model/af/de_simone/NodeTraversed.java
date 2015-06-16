package model.af.de_simone;

public class NodeTraversed {
	private Node n;
	private boolean dir;
	
	public NodeTraversed(Node n, boolean dir){
		this.n = n;
		this.dir = dir;
	}

	public Node getNode() {
		return n;
	}

	public boolean getDir() {
		return dir;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		NodeTraversed other = (NodeTraversed)obj;
		return this.n.equals(other.n) && this.dir == other.dir;
	}
	
	@Override
	public String toString() {
		return n+"-"+(dir?"TRUE":"FALSE");
	}
}
