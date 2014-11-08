package gwv_blatt04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Search {

    private final char GOAL_CHAR = 'g';
    private final char START_CHAR = 's';
    private final char UP = 'u';
    private final char RIGHT = 'r';
    private final char DOWN = 'd';
    private final char LEFT = 'l';

    private final char[][] _inputEnvironment;
    private char[][] _environment;
    private final int _startPosX;
    private final int _startPosY;
    private Node GOAL_NODE;
    private boolean[][] Feld;
    public boolean suche = false;

    private final Set<Path> _frontier;
    private final Queue _bfsQueue;

    LinkedList<Node> Astar_openList;
    LinkedList<Node> Astar_closedList;

    private int _currentPosX;
    private int _currentPosY;

    private final Stack<Character> _searchStack;

    /**
     * Creates a new Search instance that can search for a goal in a given
     * environment
     *
     * @param environment The environment to be used in the form of a char[][],
     * 'x' denotes a wall, 'g' a goal and 's' the start point
     * @param startPosX The X-Coordinate of the start point in the environment
     * array. char[y][x]
     * @param startPosY The X-Coordinate of the start point in the environment
     * array. char[y][x]
     */
    public Search(char[][] environment, int startPosX, int startPosY, Node goalNode) {
        _environment = Start.copy2DCharArray(environment);
        _inputEnvironment = Start.copy2DCharArray(environment);
        _startPosX = startPosX;
        _startPosY = startPosY;
        _currentPosX = startPosX;
        _currentPosY = startPosY;

        _searchStack = new Stack<Character>();
        _frontier = new HashSet<Path>();
        _bfsQueue = new PriorityQueue<Node>();
        GOAL_NODE = goalNode;
    }

    /**
     * Takes a direction as a char and returns the opposite direction
     *
     * @param direction a direction ('u', 'r', 'd', 'l')
     * @return the opposite direction to the specified one
     */
    private char oppositeDirection(char direction) {
        char oppositeDir = ' ';

        switch (direction) {
            case 'u':
                oppositeDir = 'd';
                break;
            case 'r':
                oppositeDir = 'l';
                break;
            case 'd':
                oppositeDir = 'u';
                break;
            case 'l':
                oppositeDir = 'r';
                break;
        }

        return oppositeDir;
    }

    /**
     * Starts a depth first search on the environment that was specified during
     * instanciation.
     *
     * @return If a path to a goal is found, the path is returned as a List, if
     * not an empty List is returned. The path is specified as a series of
     * actions ('s' = go to start point, 'u' = move up, 'd' = move down, 'l' =
     * move left and 'r' = move right) The actions correspond to the
     * environment, not the direction the robot is currently facing
     */
    public List<Character> startDFS() {
        reset(); // Resets all values to the start values, needed if multiple searches are performed

        _searchStack.push(START_CHAR);

        int schleifenzaehler = 0;

        while (!goalInReach() && !_searchStack.isEmpty()) {
            if (topIsClear()) {
                move(UP);
                _environment[_currentPosY][_currentPosX] = UP;
                _searchStack.push(UP);
            } else if (rightIsClear()) {
                move(RIGHT);
                _environment[_currentPosY][_currentPosX] = RIGHT;
                _searchStack.push(RIGHT);
            } else if (bottomIsClear()) {
                move(DOWN);
                _environment[_currentPosY][_currentPosX] = DOWN;
                _searchStack.push(DOWN);
            } else if (leftIsClear()) {
                move(LEFT);
                _environment[_currentPosY][_currentPosX] = LEFT;
                _searchStack.push(LEFT);
            } else {
                char topChar = _searchStack.pop();
                move(oppositeDirection(topChar));
            }

            // Output to the console
            ++schleifenzaehler;
            printEnvironment();
        }

        System.out.println(schleifenzaehler);

        return new ArrayList(_searchStack);
    }

    /**
     * Starts a breadth first search on the environment that was specified
     * during instanciation.
     *
     * @return If a path to a goal is found, the path is returned as a List, if
     * not an empty List is returned. The path is specified as a series of
     * actions ('s' = go to start point, 'u' = move up, 'd' = move down, 'l' =
     * move left and 'r' = move right) The actions correspond to the
     * environment, not the direction the robot is currently facing
     */
    public List<Character> startBFS() {
        int schleifenZaehler = 0;

        Node startNode = new Node(_startPosX, _startPosY, START_CHAR);

        _bfsQueue.add(startNode);
        _frontier.add(new Path(startNode));

        while (!_frontier.isEmpty()) {
            Node currentNode = (Node) _bfsQueue.poll();
            Path currentPath = null;
            // Choosing the path that ends with the first element on the queue
            for (Path path : _frontier) {
                if (currentNode.equals(path.getLastNode())) {
                    currentPath = path;
                    break;
                }
            }

            if (isGoalNode(currentNode)) {
                System.out.println(schleifenZaehler);
                reset();
                return currentPath.getCharPath();
            }

            _frontier.remove(currentPath);
            moveTo(currentNode);

            // Check wether currentNode has any neighbours
            // If a neighbour is found a new Path is added to frontier
            if (topIsClearOrGoal()) {
                move(UP);
                _environment[_currentPosY][_currentPosX] = UP;

                Node neighbour = new Node(_currentPosX, _currentPosY, UP);
                bfsAddNewPathToFrontier(currentPath, neighbour);

                move(oppositeDirection(UP));
            }
            if (rightIsClearOrGoal()) {
                move(RIGHT);
                _environment[_currentPosY][_currentPosX] = RIGHT;

                Node neighbour = new Node(_currentPosX, _currentPosY, RIGHT);
                bfsAddNewPathToFrontier(currentPath, neighbour);

                move(oppositeDirection(RIGHT));
            }
            if (bottomIsClearOrGoal()) {
                move(DOWN);
                _environment[_currentPosY][_currentPosX] = DOWN;

                Node neighbour = new Node(_currentPosX, _currentPosY, DOWN);
                bfsAddNewPathToFrontier(currentPath, neighbour);

                move(oppositeDirection(DOWN));
            }
            if (leftIsClearOrGoal()) {
                move(LEFT);
                _environment[_currentPosY][_currentPosX] = LEFT;

                Node neighbour = new Node(_currentPosX, _currentPosY, LEFT);
                bfsAddNewPathToFrontier(currentPath, neighbour);

                move(oppositeDirection(LEFT));
            }

            // Output to the console
            printEnvironment();
            ++schleifenZaehler;

        }
        System.out.println(schleifenZaehler);
        reset();

        return new ArrayList<Character>();
    }

    public List<Character> startAstarSearch() {
        int schleifenZaehler = 0;

        Node node = null;

        Astar_openList = new LinkedList<>();
        Astar_closedList.add(new Node(_startPosX, _startPosY, 1, heuristik(_startPosX, _startPosY), null));
        Astar_closedList = new LinkedList<>();

        while (!Astar_openList.isEmpty()) {

            if (GOAL_NODE != null) {
                node = GOAL_NODE;
                this.suche = false;
            } else if (this.Astar_openList.isEmpty() == false && this.suche == true) {
                node = AStarSearch();
            }
        }
        return new ArrayList<>();
    }

    private Node AStarSearch() {

        Node naechstbesterNode;

        naechstbesterNode = Astar_openList.get(getLowestCostNode(Astar_openList));
        Astar_closedList.add(Astar_openList.remove(getLowestCostNode(Astar_openList)));

        if (naechstbesterNode.getX() == GOAL_NODE.getX() && naechstbesterNode.getY() == GOAL_NODE.getY()) {
            naechstbesterNode.AusgabeXY();
            GOAL_NODE = naechstbesterNode;
            return naechstbesterNode;
        }

        //oberhalb
        openlistInRangerPrüfer(naechstbesterNode.getX(), naechstbesterNode.getY() - 1, Astar_openList, Astar_closedList, naechstbesterNode);

        //rechts
        openlistInRangerPrüfer(naechstbesterNode.getX() + 1, naechstbesterNode.getY(), Astar_openList, Astar_closedList, naechstbesterNode);

        //unterhalb
        openlistInRangerPrüfer(naechstbesterNode.getX(), naechstbesterNode.getY() + 1, Astar_openList, Astar_closedList, naechstbesterNode);

        //links
        openlistInRangerPrüfer(naechstbesterNode.getX() - 1, naechstbesterNode.getY(), Astar_openList, Astar_closedList, naechstbesterNode);

        return naechstbesterNode;
    }

    private int getLowestCostNode(LinkedList<Node> list) {
        int best = list.get(0).getKostenSum();

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getKostenSum() < best) {
                best = list.get(i).getKostenSum();
            }
        }
        //get first element with best costs
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKostenSum() == best) {
                return i;
            }
        }

        return 0;
    }

    private void openlistInRangerPrüfer(int x, int y, LinkedList<Node> openList, LinkedList<Node> closedList, Node vorher) {

        if (isPointInList(x, y, closedList) == false && Feld[x][y] == true && isPointInList(x, y, openList) == false) {
            openList.add(new Node(x, y, 1, this.heuristik(x, y), vorher));
        }
    }

    private boolean isPointInList(int x, int y, LinkedList<Node> list) {
        for (Node list1 : list) {
            if (list1.getX() == x && list1.getY() == y) {
                return true;
            }
        }

        return false;
    }

    private int heuristik(int x, int y) {
        int dx = x - GOAL_NODE.getX();
        if (dx < 0) {
            dx = -dx;
        }

        int dy = y - GOAL_NODE.getY();
        if (dy < 0) {
            dy = -dy;
        }

        return dx + dy;
    }

    private void bfsAddNewPathToFrontier(Path currentPath, Node neighbour) {
        Path newPath = currentPath.expandPath(neighbour);
        _frontier.add(newPath);
        _bfsQueue.add(neighbour);
    }

    /**
     * Changes the values of currentPosX or currentPosY to move in the specified
     * diretion
     *
     * @param direction
     */
    private void move(char direction) {
        switch (direction) {
            case UP:
                _currentPosY -= 1;
                break;
            case RIGHT:
                _currentPosX += 1;
                break;
            case DOWN:
                _currentPosY += 1;
                break;
            case LEFT:
                _currentPosX -= 1;
                break;
        }
    }

    /**
     * Changes the values of currentPosX or currentPosY to move to position
     * specified in the Node that gets passed to the method
     *
     * @param position The Node containing the information where to move
     */
    private void moveTo(Node position) {
        _currentPosX = position.getX();
        _currentPosY = position.getY();
    }

    public void printEnvironment() {
        for (int y = 0; y < 10; ++y) {
            String line = "";
            for (int x = 0; x < 20; ++x) {
                line = line + _environment[y][x];
            }
            System.out.println(line);
        }
        System.out.println();
    }

    private boolean topIsClearOrGoal() {
        return topIsClear() || _environment[_currentPosY - 1][_currentPosX] == GOAL_CHAR;
    }

    private boolean bottomIsClearOrGoal() {
        return bottomIsClear() || _environment[_currentPosY + 1][_currentPosX] == GOAL_CHAR;
    }

    private boolean leftIsClearOrGoal() {
        return leftIsClear() || _environment[_currentPosY][_currentPosX - 1] == GOAL_CHAR;
    }

    private boolean rightIsClearOrGoal() {
        return rightIsClear() || _environment[_currentPosY][_currentPosX + 1] == GOAL_CHAR;
    }

    // ----Clear-Methods: ----
    //These methods check wether  positions next to the current 
    //position are clear and have not been visited during search yet.
    private boolean topIsClear() {
        return _environment[_currentPosY - 1][_currentPosX] == ' ';
    }

    private boolean bottomIsClear() {
        return _environment[_currentPosY + 1][_currentPosX] == ' ';
    }

    private boolean leftIsClear() {
        return _environment[_currentPosY][_currentPosX - 1] == ' ';
    }

    private boolean rightIsClear() {
        return _environment[_currentPosY][_currentPosX + 1] == ' ';
    }

    // ---- Goal Checking: ----
    // These Methods check wether a goal is in next to the current postion,
    // if it is the direction to move to, to get to the goal gets added to the 
    // _searchStack and true is returned
    private boolean goalInReach() {
        if (topIsGoal()) {
            _searchStack.push(UP);
        } else if (rightIsGoal()) {
            _searchStack.push(RIGHT);
        } else if (bottomIsGoal()) {
            _searchStack.push(DOWN);
        } else if (leftIsGoal()) {
            _searchStack.push(LEFT);
        } else {
            return false;
        }

        return true;
    }

    private boolean topIsGoal() {
        return _environment[_currentPosY - 1][_currentPosX] == GOAL_CHAR;
    }

    private boolean bottomIsGoal() {
        return _environment[_currentPosY + 1][_currentPosX] == GOAL_CHAR;
    }

    private boolean leftIsGoal() {
        return _environment[_currentPosY][_currentPosX - 1] == GOAL_CHAR;
    }

    private boolean rightIsGoal() {
        return _environment[_currentPosY][_currentPosX + 1] == GOAL_CHAR;
    }

    private boolean isGoalNode(Node currentNode) {
        return currentNode.getX() == GOAL_NODE.getX()
                && currentNode.getY() == GOAL_NODE.getY();
    }

    private void reset() {
        _environment = Start.copy2DCharArray(_inputEnvironment);
        _searchStack.removeAllElements();
        _currentPosX = _startPosX;
        _currentPosY = _startPosY;
    }
}
