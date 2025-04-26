package ai;

import entity.Entity;
import frame.FrameApp;
import window.GameWindow;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class PathFinder {

    private GameWindow gameWindow;
    private Node[][] node;
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> pathList = new ArrayList<>();
    private Node startNode;
    private Node goalNode;
    private Node currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        instantiateNodes();
    }

    // マップサイズに合わせてノード配列を作成する
    public void instantiateNodes() {
        node = new Node[FrameApp.getMaxScreenCol()][FrameApp.getMaxScreenRow()];
        int col = 0;
        int row = 0;
        while (col < FrameApp.getMaxScreenCol() && row < FrameApp.getMaxScreenRow()) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == FrameApp.getMaxScreenCol()) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < FrameApp.getMaxScreenCol() && row < FrameApp.getMaxScreenRow()) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            node[col][row].gCost = 999999;
            node[col][row].fCost = 999999;
            node[col][row].parent = null;

            col++;
            if (col == FrameApp.getMaxScreenCol()) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNodes();

        startNode = node[startCol][startRow];
        startNode.gCost = 0;
        startNode.fCost = 0;
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while (col < FrameApp.getMaxScreenCol() && row < FrameApp.getMaxScreenRow()) {
            int tileNum = gameWindow.getTileManager().mapTileNum[col][row];
            if (gameWindow.getTileManager().tile[tileNum].collision) {
                node[col][row].solid = true;
            }
            col++;
            if (col == FrameApp.getMaxScreenCol()) {
                col = 0;
                row++;
            }
        }
    }

    public void findPath() {
        while (!openList.isEmpty() && !goalReached) {
            currentNode = getLowestFCostNode(openList);

            if (currentNode.equals(goalNode)) {
                goalReached = true;
                pathList = reconstructPath(currentNode);
                break;
            }

            openList.remove(currentNode);
            currentNode.checked = true;
            ArrayList<Node> neighbors = getNeighbors(currentNode);
            for (Node neighbor : neighbors) {

                if (neighbor.solid) continue;

                if (neighbor.checked) continue;

                double tentativeGCost = currentNode.gCost + 1;
                if (!openList.contains(neighbor) || tentativeGCost < neighbor.gCost) {
                    neighbor.gCost = tentativeGCost;

                    neighbor.hCost = Math.abs(neighbor.col - goalNode.col) + Math.abs(neighbor.row - goalNode.row);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = currentNode;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
    }

    private Node getLowestFCostNode(ArrayList<Node> list) {
        Node lowest = list.get(0);
        for (Node n : list) {
            if (n.fCost < lowest.fCost) {
                lowest = n;
            }
        }
        return lowest;
    }

    private ArrayList<Node> getNeighbors(Node current) {
        ArrayList<Node> neighbors = new ArrayList<>();

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] d : directions) {
            int c = current.col + d[0];
            int r = current.row + d[1];

            if (c >= 0 && c < FrameApp.getMaxScreenCol() && r >= 0 && r < FrameApp.getMaxScreenRow()) {
                neighbors.add(node[c][r]);
            }
        }
        return neighbors;
    }

    private ArrayList<Node> reconstructPath(Node goalNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    public ArrayList<Point> getPathPoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (Node n : pathList) {
            int x = n.col * FrameApp.createSize();
            int y = n.row * FrameApp.createSize();
            points.add(new Point(x, y));
        }
        return points;
    }

    // 経路探索が完了しているかどうか
    public boolean isPathFound() {
        return goalReached;
    }
}