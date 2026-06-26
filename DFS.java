import java.util.*;

public class DFS {

    private Grid grid;

    public DFS(Grid grid) {
        this.grid = grid;
    }

    public SearchResult search() {
        long startTime = System.currentTimeMillis();

        int startX = Grid.START_X;
        int startY = Grid.START_Y;
        int goalX = Grid.GOAL_X;
        int goalY = Grid.GOAL_Y;

        // DFS uses a stack to explore nodes
        Deque<Node> stack = new ArrayDeque<>();
        boolean[][] visited = new boolean[Grid.SIZE][Grid.SIZE]; // track visited cells

        Node startNode = new Node(startX, startY, null);
        stack.push(startNode);

        int nodesVisited = 0; // keep count of nodes visited

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (visited[current.x][current.y])
                continue; // skip if already visited
            visited[current.x][current.y] = true; // mark current node as visited
            nodesVisited++;

            if (current.x == goalX && current.y == goalY) {
                long endTime = System.currentTimeMillis();
                List<Node> path = reconstructPath(current);
                return new SearchResult("DFS", path, nodesVisited, endTime - startTime);
            }

            int[][] directions = Grid.DIRECTIONS;
            for (int i = directions.length - 1; i >= 0; i--) { // reverse order for DFS
                int newX = current.x + directions[i][0];
                int newY = current.y + directions[i][1];

                if (grid.isWalkable(newX, newY) && !visited[newX][newY]) {
                    stack.push(new Node(newX, newY, current));
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new SearchResult("DFS", null, nodesVisited, endTime - startTime);
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
