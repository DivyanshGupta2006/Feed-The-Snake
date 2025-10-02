package com.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.gamePanel;

public class Food extends EntityClass {

    gamePanel g;
    BufferedImage app;

    public Food(gamePanel gp) {
        super(gp);
        this.g=gp;
        setImage();
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(app, this.col*g.tileSize, this.row*g.tileSize, g.tileSize, g.tileSize, null);
    }

    public void setImage() {
        try {
            app=ImageIO.read(getClass().getResourceAsStream("/res_food/Apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placeFood() {
        this.col = g.r.nextInt(g.screenWidth/g.tileSize);
        this.row = g.r.nextInt(g.screenHeight/g.tileSize);
        int i= row*g.maxWorldCol+col;
        int num=g.map.rec[i];
        if(num>77&&num<80) {
            placeFood();
        }else if(g.cC.collision(g.s.snakeHead, this)) {
            placeFood();
        }
        for(int j=0;j<g.s.snakeBody.size();j++) {
            if(g.cC.collision(g.s.snakeBody.get(j), this)) {
                placeFood();
            }
        }
    }
}
