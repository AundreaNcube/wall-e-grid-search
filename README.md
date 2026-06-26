# Wall-E Search: DFS, BFS, and A* on a 100×100 Grid

A Java implementation (University of Pretoria AI module) and comparative analysis of three classic search algorithms — Depth-First Search, Breadth-First Search, and A* — solving a pathfinding problem on a 100×100 grid. Built for a Computer Science AI module (COS314) at the University of Pretoria.

## Problem

A simulated robot ("Wall-E") needs to navigate from a start cell to a goal cell on a 100×100 grid containing:

- A **U-shaped trap** — a hard-coded concave obstacle designed specifically to mislead uninformed search strategies (an algorithm exploring left-to-right can wander deep into the trap before backtracking out)
- **Randomly seeded noise** — 15% of the remaining cells are obstacles, generated from a seed value so every run is reproducible

The grid uses 4-directional movement (up/down/left/right). The goal is to compare how each algorithm performs in terms of path optimality, nodes explored, and execution time.

```
Start: (20, 50)
Goal:  (80, 50)
```

## Algorithms implemented

| Algorithm | Data Structure | Optimal? | Heuristic |
|-----------|----------------|----------|-----------|
| DFS | Stack (`ArrayDeque`, LIFO) | No | None |
| BFS | Queue (`LinkedList`, FIFO) | Yes | None |
| A* | `PriorityQueue` ordered by f(n) | Yes | Manhattan Distance |

**A* heuristic:**
```
h(n) = |x1 - x2| + |y1 - y2|
```

## Project structure

```
├── Main.java           # Entry point — takes a seed, runs all 3 algorithms, prints results
├── Grid.java            # Builds the 100×100 grid, places the U-Trap, adds seeded noise
├── Node.java            # Represents a grid cell (coordinates, parent, g/h/f costs)
├── SearchResult.java    # Stores algorithm output (path, nodes visited, execution time)
├── DFS.java             # Depth-First Search implementation
├── BFS.java             # Breadth-First Search implementation
├── AStar.java           # A* Search implementation
└── Assignment1.jar      # Compiled executable JAR
```

## How to compile and run

**Compile:**
```bash
javac *.java
```

**Build the JAR:**
```bash
jar cfe Assignment1.jar Main *.class
```

**Run:**
```bash
java -jar Assignment1.jar
```

You'll be prompted for a seed (or press Enter to use the current time), and the program will run all three algorithms against the same grid and print a comparison table.

## Example output

```
Enter a seed for grid generation (or press Enter to use current time): 123
Using seed: 123

Start: (20,50)  Goal: (80,50)

Running Depth-First Search (DFS)...
Running Breadth-First Search (BFS)...
Running A* Search...

========================================
COMPARISON TABLE:
========================================
Cost of Path        3016 steps   106 steps   106 steps
Nodes Visited        7360         7509        1898
Time Taken (ms)        8 ms         10 ms        6 ms
Optimality?            No           Yes          Yes
```

After the results, you can optionally visualize each algorithm's path printed directly onto the grid.

## Grid legend

| Symbol | Meaning |
|--------|---------|
| `.` | Empty / traversable cell |
| `#` | Obstacle (U-Trap or noise) |
| `S` | Start node (20, 50) |
| `G` | Goal node (80, 50) |
| `*` | Path found by the algorithm |

## Key findings

**Optimality.** BFS and A* both guarantee the shortest path (106 steps in the example run above). BFS does this by exploring level by level, so it always checks every shorter possibility before moving on to longer ones. A* is also optimal here because the Manhattan distance heuristic never overestimates the true remaining distance on a 4-directional grid, which keeps it admissible. DFS, by contrast, simply commits to the first path it finds — in the example run that meant a path of 3016 steps, dramatically longer than necessary.

**Efficiency — nodes visited isn't the whole story.** DFS visited 7360 nodes, fewer than BFS's 7509, but produced a far worse path. This is the core lesson from comparing the three: a lower node count doesn't mean a better search if the algorithm has no sense of direction. BFS visits the most nodes overall because it expands uniformly outward in every direction at each distance level — thorough, but wasteful once the goal direction is known. A* visited only 1898 nodes (roughly a quarter of BFS's count) because the Manhattan distance heuristic actively prioritizes cells closer to the goal, letting the search skip large areas that are unlikely to lead anywhere useful.

**Why the U-Trap matters.** The trap is specifically punishing for DFS: travelling left-to-right, DFS's LIFO strategy pushes it to explore as deep as possible into the trap before backtracking, since it has no way to recognize a dead end in advance. BFS and A* aren't immune to the trap either, but their breadth-first or heuristic-guided exploration means they don't commit to a single bad direction the way DFS does.

**Impact of the seed value.** The seed controls where the random 15% noise lands, which affects results — for example, a seed of `123` produced a 3016-step DFS path, while a time-based seed produced 3562 steps, showing how much noise layout affects an uninformed algorithm like DFS. BFS and A* path costs also varied slightly between seeds, but both reliably returned the optimal path regardless. Across every seed tested, A* consistently outperformed BFS in nodes visited and execution time while matching it on path optimality.

**Conclusion.** A* is the strongest overall approach here — it matches BFS's guaranteed optimality while visiting a fraction of the nodes, purely because the heuristic gives it a sense of direction that the other two algorithms lack.