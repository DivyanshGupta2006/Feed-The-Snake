package com.AI;
public class Node {
    Node parent;
    public int col, row;
    int gCost,hCost,fCost;
    boolean open,checked;
    public boolean solid;

    public Node(int col, int row) {
        this.col=col;
        this.row=row;
    }
}
