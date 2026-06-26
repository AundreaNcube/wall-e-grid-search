import java.util.*;

public class AStar {

    private Grid grid;

    public AStar(Grid grid) {
        this.grid = grid;
    }

    public SearchResult search() {
        long startTime = System.currentTimeMillis();

        int startX = Grid.START_X;
        int startY = Grid.START_Y;
        int goalX = Grid.GOAL_X;
        int goalY = Grid.GOAL_Y;

        PriorityQueue<Node> openSet = new PriorityQueue<>(); // nodes to explore, ordered by f=g+h

        int[][] gCost = new int[Grid.SIZE][Grid.SIZE]; // cost from start to each cell
        for (int[] row : gCost)
            Arrays.fill(row, Integer.MAX_VALUE); // initialize gCost to infinity

        boolean[][] closedSet = new boolean[Grid.SIZE][Grid.SIZE]; // track visited cells

        int startH = Node.heuristic(startX, startY, goalX, goalY);
        Node startNode = new Node(startX, startY, null, 0, startH);
        openSet.add(startNode);
        gCost[startX][startY] = 0;

        int nodesVisited = 0; // keep count of nodes visited

        while (!openSet.isEmpty()) {
            Node current = openSet.poll(); // get node with lowest f=g+h

            if (closedSet[current.x][current.y])
                continue; // skip if already visited
            closedSet[current.x][current.y] = true; // mark current node as visited
            nodesVisited++;

            if (current.x == goalX && current.y == goalY) {
                long endTime = System.currentTimeMillis();
                List<Node> path = reconstructPath(current);
                return new SearchResult("A*", path, nodesVisited, endTime - startTime);
            }

            for (int[] directions : Grid.DIRECTIONS) {
                int newX = current.x + directions[0];
                int newY = current.y + directions[1];

                if (!grid.isWalkable(newX, newY) || closedSet[newX][newY])
                    continue; // skip if not walkable or already visited

                int newG = current.g + 1; // cost from start to neighbor
                if (newG < gCost[newX][newY]) { // only consider this path if it's better
                    gCost[newX][newY] = newG;
                    int newH = Node.heuristic(newX, newY, goalX, goalY);
                    Node neighborNode = new Node(newX, newY, current, newG, newH);
                    openSet.add(neighborNode);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new SearchResult("A*", null, nodesVisited, endTime - startTime);
    }

    private List<Node> reconstructPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
