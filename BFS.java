import java.util.*;

public class BFS {

    private Grid grid;

    public BFS(Grid grid) {
        this.grid = grid;
    }

    public SearchResult search() {
        long startTime = System.currentTimeMillis();

        int startX = Grid.START_X;
        int startY = Grid.START_Y;
        int goalX = Grid.GOAL_X;
        int goalY = Grid.GOAL_Y;

        Queue<Node> queue = new LinkedList<>(); //nodes to explore in FIFO order
        boolean[][] visited = new boolean[Grid.SIZE][Grid.SIZE]; //track visited cells

        Node startNode = new Node(startX, startY, null);
        queue.add(startNode);
        visited[startX][startY] = true; //mark start node as visited

        int nodesVisited = 0; //keep count of nodes visited

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            nodesVisited++;

            if (current.x == goalX && current.y == goalY) {
                long endTime = System.currentTimeMillis();
                List<Node> path = reconstructPath(current);
                return new SearchResult("BFS", path, nodesVisited, endTime - startTime);
            }

            for (int[] directions : Grid.DIRECTIONS) {
                int newX = current.x + directions[0];
                int newY = current.y + directions[1];

                if (grid.isWalkable(newX, newY) && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new Node(newX, newY, current));
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new SearchResult("BFS", null, nodesVisited, endTime - startTime);
    }

    private List<Node> reconstructPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path); //reverse to get path from start to goal
        return path;
    }
}