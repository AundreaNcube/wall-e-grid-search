import java.util.Random;

public class Grid{
    public static final int SIZE = 100;
    public static final int EMPTY = 0;
    public static final int OBSTACLE = 1;
    public static final int START = 2;
    public static final int GOAL = 3;
    public static final int PATH = 4;
    public static final int VISITED = 5;

    public static final int START_X = 20;
    public static final int START_Y = 50;
    public static final int GOAL_X = 80;
    public static final int GOAL_Y = 50;

    private int[][] grid;
    private long seed;

    public Grid(long seed) {
        this.seed = seed;
        this.grid = new int[SIZE][SIZE];
        buildGrid();
    }

    private void buildGrid(){
        for(int x = 0; x < SIZE; x++)
            for(int y = 0; y < SIZE; y++)
                grid[x][y] = EMPTY;
            
        placeUTrap();

        addNoise();

        grid[START_X][START_Y] = START;
        grid[GOAL_X][GOAL_Y] = GOAL;
    }

    private void placeUTrap() {
        //back wall x=50 from y=30 to y=70
        for(int y = 30; y <= 70; y++) {
            grid[50][y] = OBSTACLE;
        }

        //Top arm: horizontal line from (50,30) to (30,30)
        for(int x = 30; x <= 50; x++) {
            grid[x][30] = OBSTACLE;
        }

        //Bottom arm: horizontal line from (50,70) to (70,70)
        for(int x = 30; x <= 50; x++) {
            grid[x][70] = OBSTACLE;
        }
    }

    private void addNoise(){
        Random rand = new Random(seed);
        System.out.println("Seed Value used for noise: " + seed);

        int totalCells = SIZE * SIZE;
        int noiseCells = (int) (totalCells *0.15);
        int placed = 0;

        while(placed < noiseCells) {
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);

            if(grid[x][y] == EMPTY && !(x == START_X && y == START_Y) && !(x == GOAL_X && y == GOAL_Y)) {
                grid[x][y] = OBSTACLE;
                placed++;
            }
        }
    }

    public boolean isWalkable(int x, int y) {
        if(x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        return grid[x][y] != OBSTACLE;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int get(int x, int y)
    {
        return grid[x][y];
    }

    public void set(int x, int y, int value) {
        if(grid[x][y] != START && grid[x][y] != GOAL) {
            grid[x][y] = value;
        }     
    }

    public int[][] getCopy()
    {
        int[][] copy = new int[SIZE][SIZE];
        for(int x = 0; x < SIZE; x++) {
            copy[x] = grid[x].clone();
        }
        return copy;
    }

    public void print(int [][] displayGrid) {

        System.out.println("\n\nGrid: (S=Start, G=Goal, #=Obstacle, .=Empty, *=Path, v=Visited): ");

        for(int y = 0; y < SIZE; y++) {
            for(int x = 0; x < SIZE; x++) {
                switch(displayGrid[x][y]) {
                    case EMPTY:     System.out.print(". "); break;
                    case OBSTACLE:  System.out.print("# "); break;
                    case START:     System.out.print("S "); break;
                    case GOAL:      System.out.print("G "); break;
                    case PATH:      System.out.print("* "); break;
                    case VISITED:   System.out.print("v "); break;
                    default:        System.out.print(" "); break;
                }
            }
            System.out.println();
        }
    }

    public static final int[][] DIRECTIONS = {
        {0, -1}, //up
        {0, 1},  //down
        {-1, 0}, //left
        {1, 0}   //right
    };
}