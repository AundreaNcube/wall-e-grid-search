import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===================================\n");
        System.out.println("Wall-E Search\n");
        System.out.println("===================================\n");
        System.out.println();

        long seed;
        System.out.print("Enter a seed for grid generation (or press Enter to use current time): ");
        String seedInput = scanner.nextLine().trim();

        if (seedInput.isEmpty()) {
            seed = System.currentTimeMillis();
        } else {
            try {
                seed = Long.parseLong(seedInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Using current time as seed.");
                seed = System.currentTimeMillis();
            }
        }

        System.out.println("Using seed: " + seed + "\n");

        Grid grid = new Grid(seed);
        System.out.println("Buidling 100*100 grid with U-shaped trap and noise...\n");
        System.out.print("Start: (" + Grid.START_X + "," + Grid.START_Y + ")  ");
        System.out.println("Goal: (" + Grid.GOAL_X + "," + Grid.GOAL_Y + ")\n");
        System.out.println();

        // running DFS
        System.out.println("Running Depth-First Search (DFS)...");
        DFS dfs = new DFS(grid);
        SearchResult dfsResult = dfs.search();

        // running BFS
        System.out.println("Running Breadth-First Search (BFS)...");
        BFS bfs = new BFS(grid);
        SearchResult bfsResult = bfs.search();

        // running A*
        System.out.println("Running A* Search...");
        AStar aStar = new AStar(grid);
        SearchResult aStarResult = aStar.search();

        // print results
        System.out.println("\n===================================\n");
        System.out.println("Search Results: \n");

        dfsResult.printSummary();
        bfsResult.printSummary();
        aStarResult.printSummary();

        printTable(dfsResult, bfsResult, aStarResult);

        System.out.println("Would you like to visualize the paths on the grid? (y/n): ");
        String visualizeInput = scanner.nextLine().trim().toLowerCase();
        if (visualizeInput.equals("y") || visualizeInput.equals("yes")) {
            visualizePath(grid, dfsResult, "DFS Path");
            visualizePath(grid, bfsResult, "BFS Path");
            visualizePath(grid, aStarResult, "A* Path");
        }
        scanner.close();

    }

    private static void printTable(SearchResult dfs, SearchResult bfs, SearchResult aStar) {
        System.out.println("========================================");
        System.out.println("COMPARISON TABLE:");
        System.out.println("========================================");

        System.out.printf("%-30s %-15s %-15s %-15s%n",
                "Cost of Path",
                dfs.foundPath ? dfs.getCostOfPath() + " steps" : "No Path",
                bfs.foundPath ? bfs.getCostOfPath() + " steps" : "No Path",
                aStar.foundPath ? aStar.getCostOfPath() + " steps" : "No Path");
        System.out.printf("%-30s %-15s %-15s %-15s%n",
                "Nodes Visited",
                dfs.nodesVisited,
                bfs.nodesVisited,
                aStar.nodesVisited);

        System.out.printf("%-30s %-15s %-15s %-15s%n",
                "Time Taken (ms)",
                dfs.timeTakenMs + " ms",
                bfs.timeTakenMs + " ms",
                aStar.timeTakenMs + " ms");

        System.out.printf("%-30s %-15s %-15s %-15s%n",
                "Optimality?",
                "No",
                "Yes",
                "Yes");
        
        System.out.println("-".repeat(65));
    }

    private static void visualizePath(Grid grid, SearchResult result, String title) {
        System.out.println("\nVisualizing " + title + "...");
        if (!result.foundPath) {
            System.out.println(title + ": No path found to visualize.");
            return;
        }

        int[][] displayGrid = grid.getCopy();

        for (Node node : result.path) {
            if (displayGrid[node.x][node.y] != Grid.START && displayGrid[node.x][node.y] != Grid.GOAL) {
                displayGrid[node.x][node.y] = Grid.PATH;
            }
        }

        System.out.println("\n" + title + ":");
        grid.print(displayGrid);

    }
}