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
        // マップの最大セル数を取得（例: 22）
        int maxCols = FrameApp.getMaxScreenCol();
        int maxRows = FrameApp.getMaxScreenRow();

        // クランプ処理
        if (startCol < 0) startCol = 0;
        if (startCol >= maxCols) startCol = maxCols - 1;
        if (startRow < 0) startRow = 0;
        if (startRow >= maxRows) startRow = maxRows - 1;

        if (goalCol < 0) goalCol = 0;
        if (goalCol >= maxCols) goalCol = maxCols - 1;
        if (goalRow < 0) goalRow = 0;
        if (goalRow >= maxRows) goalRow = maxRows - 1;

        resetNodes();

        startNode = node[startCol][startRow];
        startNode.gCost = 0;
        startNode.fCost = 0;
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        // 全マップの走査（2重ループの方が読みやすい）
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                int tileNum = gameWindow.getTileManager().mapTileNum[col][row];
                if (gameWindow.getTileManager().tile[tileNum].collision || tileNum == 2) {
                    node[col][row].solid = true;
                }
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

    public ArrayList<Node> getPathList() {
        return pathList;
    }
}