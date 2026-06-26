import java.util.List;

public class SearchResult {
   public String algorithmName;
   public List<Node> path;
   public int nodesVisited;
   public long timeTakenMs;
    public boolean foundPath;

    public SearchResult(String algorithm, List<Node> path, int nodesVisited, long timeTaken) {
        this.algorithmName = algorithm;
        this.path = path;
        this.nodesVisited = nodesVisited;
        this.timeTakenMs = timeTaken;
        this.foundPath = (path != null && !path.isEmpty());
    }

    public int getCostOfPath() {
        return foundPath ? path.size() - 1 : -1; //cost is number of steps, -1 if no path found
    }

    public void printSummary() {
        System.out.println("Algorithm: " + algorithmName);
        //System.out.println("Path found: " + (foundPath ? "Yes" : "No"));
        if(foundPath) {
            System.out.println("Cost of path: " + getCostOfPath());
        }
        else 
        {
            System.out.println("No path found.");
        }
        System.out.println("Nodes visited: " + nodesVisited);
        System.out.println("Time taken (ms): " + timeTakenMs);
        System.out.println("Optimality: "+ getOptimality());
        System.out.println("\n\n");
    }

    public String getOptimality() {
        if(!foundPath) return "N/A";
        if(algorithmName.equals("A*")) return "Yes (guaranteed shortest path if heuristic is admissible)";
        if(algorithmName.equals("BFS")) return "Yes (guaranteed shortest path)";
        if(algorithmName.equals("DFS")) return "Not guaranteed";
        return "Unknown";
    }
}