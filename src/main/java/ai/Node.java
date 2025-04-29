package ai;

public class Node {

    public int col, row;
    public boolean open, checked, solid;
    public double gCost, hCost, fCost;
    public Node parent;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
        this.open = false;
        this.checked = false;
        this.solid = false;
        this.gCost = 999999;
        this.hCost = 0;
        this.fCost = 999999;
        this.parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node other = (Node) obj;
        return this.col == other.col && this.row == other.row;
    }
}