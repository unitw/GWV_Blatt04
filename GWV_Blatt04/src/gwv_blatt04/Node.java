package gwv_blatt04;

public class Node implements Comparable  {

    private final int x;

    private final int y;
    private final char Direction;

    public Node(int x, int y, char Direction) {

        this.x = x;
        this.y = y;
        this.Direction = Direction;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getDirection() {
        return Direction;
    }
    
    @Override
	public int hashCode() {
    	int hash = 5;
    	hash = 59 * hash + this.x;
    	hash = 59 * hash + this.y;
    	hash = 59 * hash + this.Direction;
    	return hash;
	}

	@Override
	public boolean equals(Object obj) {
    	if (obj == null) {
        	return false;
    	}
    	if (getClass() != obj.getClass()) {
        	return false;
    	}
    	final Node other = (Node) obj;
    	if (this.x != other.x) {
        	return false;
    	}
    	if (this.y != other.y) {
        	return false;
    	}
    	if (this.Direction != other.Direction) {
        	return false;
    	}
    	return true;
	}

    @Override
    public int compareTo(Object o) {
        
        return 123;
      }
    
}
