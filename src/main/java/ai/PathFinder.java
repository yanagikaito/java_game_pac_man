package ai;

import frame.FrameApp;
import tile.Tile;
import window.GameWindow;

import java.awt.*;
import java.util.*;

public class PathFinder {
    private GameWindow gameWindow;
    public Node[][] nodes;
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> pathList = new ArrayList<>();
    private boolean goalReached = false;
    private Node startNode, goalNode, currentNode;

    public PathFinder(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        instantiateNodes();
    }

    // マップサイズに合わせて全ノードを作成する（各セルにその座標を保存）
    public void instantiateNodes() {
        int maxCols = FrameApp.getMaxScreenCol();
        int maxRows = FrameApp.getMaxScreenRow();
        nodes = new Node[maxCols][maxRows];
        for (int col = 0; col < maxCols; col++) {
            for (int row = 0; row < maxRows; row++) {
                nodes[col][row] = new Node(col, row);
            }
        }
    }

    // ノードの状態をリセットする
    public void resetNodes() {
        int maxCols = FrameApp.getMaxScreenCol();
        int maxRows = FrameApp.getMaxScreenRow();
        for (int col = 0; col < maxCols; col++) {
            for (int row = 0; row < maxRows; row++) {
                Node n = nodes[col][row];
                n.open = false;
                n.checked = false;
                n.solid = false;
                n.gCost = 999999;
                n.fCost = 999999;
                n.parent = null;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
    }

    /**
     * マップのタイル状の情報（衝突情報など）を参考に、開始セルとゴールセル（巣の位置）をセットする
     *
     * @param startCol   スタートセルの列
     * @param startRow   スタートセルの行
     * @param goalCol    ゴールセルの列（巣の位置）
     * @param goalRow    ゴールセルの行
     * @param mapTileNum マップのタイル番号（[col][row]）
     * @param tiles      タイルの情報（各タイルに衝突判定フラグなどがあると仮定）
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, int[][] mapTileNum, Tile[] tiles) {
        int maxCols = FrameApp.getMaxScreenCol();
        int maxRows = FrameApp.getMaxScreenRow();
        // 座標のクランプ処理
        startCol = Math.max(0, Math.min(startCol, maxCols - 1));
        startRow = Math.max(0, Math.min(startRow, maxRows - 1));
        goalCol = Math.max(0, Math.min(goalCol, maxCols - 1));
        goalRow = Math.max(0, Math.min(goalRow, maxRows - 1));

        resetNodes();

        startNode = nodes[startCol][startRow];
        startNode.gCost = 0;
        startNode.fCost = 0;
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        // マップを走査して、壁（衝突）セルを solid にする
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                int tileNum = mapTileNum[col][row];
                if (tiles[tileNum].collision || tileNum == 2) {
                    nodes[col][row].solid = true;
                }
            }
        }
    }

    // A*（または BFS に近い形）の経路探索（最も fCost の低いポイントから展開）
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

    // fCost が最も低いノードを openList 内から探す
    private Node getLowestFCostNode(ArrayList<Node> list) {
        Node lowest = list.get(0);
        for (Node n : list) {
            if (n.fCost < lowest.fCost) {
                lowest = n;
            }
        }
        return lowest;
    }

    // current の上下左右の隣接セルを返す
    private ArrayList<Node> getNeighbors(Node current) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] d : directions) {
            int c = current.col + d[0];
            int r = current.row + d[1];
            if (c >= 0 && c < FrameApp.getMaxScreenCol() && r >= 0 && r < FrameApp.getMaxScreenRow()) {
                neighbors.add(nodes[c][r]);
            }
        }
        return neighbors;
    }

    // ゴールまでの経路を親ノードを辿って復元する
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

    public boolean isPathFound() {
        return goalReached;
    }
}