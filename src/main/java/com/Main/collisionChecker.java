package com.Main;

import Entity.EntityClass;
import Tile.Map;

public class collisionChecker {

    gamePanel g;

    public collisionChecker(gamePanel gp) {
        this.g=gp;
    }

    public boolean collision(EntityClass a, EntityClass b) {
        if(a.col==b.col&&a.row==b.row)
            return true;
        return false;
    }
    public boolean collision(EntityClass e, Map m) {
        int i=e.row*g.maxWorldCol+e.col;
        int num=m.rec[i];
        if(num>77&&num<80) return true;
        return false;
    }

}
