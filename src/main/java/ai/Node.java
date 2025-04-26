package ai;

public class Node {

    public int col, row;
    public double gCost = 999999;
    public double hCost = 0;
    public double fCost = 999999;
    public Node parent = null;

    // 探索時のフラグ
    public boolean open = false;
    public boolean checked = false;

    // 壁など通行不可かどうか
    public boolean solid = false;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}