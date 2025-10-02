package com.Tile;

import Main.gamePanel;

public class Map {

    gamePanel g;
    public int[] rec;
    public Map(gamePanel gp) {
        this.g=gp;
        rec=new int[g.maxWorldCol*g.maxWorldRow];
    }
}
