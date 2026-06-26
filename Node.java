public class Node implements Comparable<Node> {
    public int x;
    public int y;
    public Node parent;

    public int g;   //g=cost from starrt to current node
    public int h;   //h=heuristic cost to goal
    public int f; //f=g+h

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    public Node(int x, int y, Node parent, int g, int h)
    {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public static int heuristic(int x1, int y1, int x2, int y2) {
        //using manhattan distance as heuristic
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}