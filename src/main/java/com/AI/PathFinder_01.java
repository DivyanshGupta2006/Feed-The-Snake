package com.AI;

import java.util.ArrayList;

import com.Main.gamePanel;

public class PathFinder_01 {

    gamePanel g;
    Node[][] node;
    public ArrayList<Node> pathList = new ArrayList<>();
    ArrayList<Node> openList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached=false;
    int steps;

    public PathFinder_01(gamePanel gp) {
        this.g=gp;
        instantiateNodes();
    }

    //HELPER
    public void instantiateNodes() {
        node=new Node[g.maxWorldCol][g.maxWorldRow];
        int col=0;
        int row=0;
        while(col<(g.maxWorldCol)&&row<(g.maxWorldRow)) {
            node[col][row]= new Node(col,row);
            col++;
            if(col==g.maxWorldCol) {
                col=0;
                row++;
            }
        }
    }

    //SET START AND TARGET
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        startNode=node[startCol][startRow];
        currentNode=startNode;
        goalNode=node[goalCol][goalRow];
        openList.add(currentNode);
        int col=0;
        int row=0;
        for(int i=0;i<g.s.snakeBody.size();i++) {
            node[g.s.snakeBody.get(i).col][g.s.snakeBody.get(i).row].solid=true;
        }
        while((col<g.maxWorldCol)&&(row<g.maxWorldRow)) {
            //Set solid
            int i=row*g.maxWorldCol+col;
            int tileNum=g.map.rec[i];
            if(tileNum>77&&tileNum<80) {
                node[col][row].solid=true;
            }

            getCost(node[col][row]);

            col++;
            if(col==g.maxWorldCol) {
                col=0;
                row++;
            }
        }
    }

    //HELPER
    public void resetNodes() {
        int col=0;
        int row=0;
        while(col<g.maxWorldCol&&row<g.maxWorldRow) {
            node[col][row].open=false;
            node[col][row].solid=false;
            node[col][row].checked=false;
            col++;
            if(col==g.maxWorldCol) {
                col=0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached=false;
        steps=0;
    }

    //HELPER
    public void getCost(Node n) {
        //G-cost
        int xD=Math.abs(n.col-startNode.col);
        int yD=Math.abs(n.row-startNode.row);
        n.gCost=xD+yD;
        //H-cost
        xD=Math.abs(n.col-goalNode.col);
        yD=Math.abs(n.row-goalNode.row);
        n.hCost=xD+yD;
        //F-cost
        n.fCost=n.gCost+n.hCost;
    }

    public boolean search() {

        while(goalReached==false && steps<=500) {
            int col=currentNode.col;
            int row=currentNode.row;

            //Update current node
            currentNode.checked=true;
            openList.remove(currentNode);

            //Add new nodes to open list
            if(row>=1) {
                openNode(node[col][row-1]);
            }
            if(col>=1) {
                openNode(node[col-1][row]);
            }
            if(row+1<g.maxWorldRow) {
                openNode(node[col][row+1]);
            }
            if(col+1<g.maxWorldCol) {
                openNode(node[col+1][row]);
            }
            int bestNodeIndex=0;
            int bestNodefCost=999;

            for(int i=0;i<openList.size();i++) {
                if(openList.get(i).fCost<bestNodefCost) {
                    bestNodeIndex=i;
                    bestNodefCost=openList.get(i).fCost;
                }
                else if(openList.get(i).fCost==bestNodefCost) {
                    if(openList.get(i).gCost<openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex=i;
                    }
                }
            }
            if(openList.size()==0) {
                break;
            }

            currentNode=openList.get(bestNodeIndex);

            if(currentNode==goalNode) {
                goalReached=true;
                trackPath();
            }
            steps++;
        }

        return goalReached;
    }

    public void openNode(Node n) {
        if(n.checked==false&&n.open==false&&n.solid==false) {
            n.open=true;
            n.parent=currentNode;
            openList.add(n);
        }
    }

    public void trackPath() {
        Node current=goalNode;
        while(current != startNode) {
            pathList.add(0,current);
            current=current.parent;
        }

    }

}
