# Heuristic_AI_GAME_SOLVER (Using Java-8)

this project focus on solving the sliding puzzle game using three different search algorithms: Iterative Deepening, Breadth-First Search (BFS), and A* (A-Star). The goal of the project is to rearrange a given square of squares (3x3 or 4x4) into ascending order by switching adjacent squares vertically or horizontally.

The project consists of the following steps:

1. Input Parsing: The code takes as input a text file describing the initial state of the puzzle. The format is 3 or 4 text lines, with each line containing 3 or 4 characters, respectively.

2. Algorithm Selection: Users can choose between the Iterative Deepening, BFS, and A* algorithms by providing an additional command-line argument (id/bfs/astar).

3. Algorithm Implementation: Implemented the chosen search algorithm to find the optimal sequence of moves to reach the goal state.

4.Output Generation: For each run, the code generates the following outputs:

4.1)The sequence of moves to reach the goal state

4.2) The cost of the solution (number of operations)

4.3) The number of states expanded during the search

In addition to the code, the project includes:

1) Heuristic Description: A written description of the heuristic used for the A* algorithm, along with an explanation of why it is admissible.

2) Results Table: A table containing the results of running the different algorithms on the two provided files and four additional initial states (two 3x3 and two 4x4). The table includes the solution route, cost, and the number of states expanded for each algorithm and input.

3) Conclusions: A discussion of the performance of the different algorithms based on the results table

This project demonstrates the application of search algorithms to solve the sliding puzzle game and provides insights into the performance of the different algorithms when applied to various initial states.
