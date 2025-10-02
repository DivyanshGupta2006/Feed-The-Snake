package com.Entity;

import com.Main.gamePanel;

public class EntityClass {

    gamePanel g;
    public int col,row;
    public EntityClass(gamePanel gp) {
        this.g=gp;
    }
    public EntityClass(int x, int y) {
        col=x;
        row=y;
    }
}
