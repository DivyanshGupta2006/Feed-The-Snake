package com.Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.AI.*;
import com.Main.gamePanel;

public class Snake extends EntityClass {

    gamePanel g;
    public ArrayList<EntityClass> snakeBody=new ArrayList<>();
    public EntityClass snakeHead;
    BufferedImage snakeH,snakeHUp,snakeHDown,snakeHLeft,snakeHRight,snakeBod;
    public int velocityx,velocityy;

    public Snake(gamePanel gp) {
        super(gp);
        this.g=gp;
        snakeHead=new EntityClass(g);
        setImages();
    }

    public void draw(Graphics2D g2d) {
        getImage();
        g2d.drawImage(snakeH, snakeHead.col*g.tileSize, snakeHead.row*g.tileSize , g.tileSize, g.tileSize, null);

        //Drawing Snake body using loop
        g2d.setColor(Color.green);
        for(int i2 = 0;i2<snakeBody.size();i2++) {
            EntityClass snakePart = snakeBody.get(i2);
            g2d.drawImage(snakeBod,snakePart.col*g.tileSize, snakePart.row*g.tileSize, g.tileSize, g.tileSize,null);
        }
    }
    public void setImages() {
        try {
            snakeHUp=ImageIO.read(getClass().getResourceAsStream("/res/res_snake/SnakeHeadUp.png"));
            snakeHDown=ImageIO.read(getClass().getResourceAsStream("/res/res_snake/SnakeHeadDown.png"));
            snakeHLeft=ImageIO.read(getClass().getResourceAsStream("/res/res_snake/SnakeHeadLeft.png"));
            snakeHRight=ImageIO.read(getClass().getResourceAsStream("/res/res_snake/SnakeHeadRight.png"));
            snakeBod=ImageIO.read(getClass().getResourceAsStream("/res/res_snake/SnakeBody.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getImage() {
        if(velocityx==0&&velocityy==-1) {
            snakeH=snakeHUp;
        }
        else if(velocityx==0&&velocityy==1) {
            snakeH=snakeHDown;
        }
        else if(velocityx==1&&velocityy==0) {
            snakeH=snakeHRight;
        }
        else if(velocityx==-1&&velocityy==0) {
            snakeH=snakeHLeft;
        }
    }
    public void followPath(ArrayList<Node> path) {
        if(path.size()>0) {
            velocityx=path.get(0).col-snakeHead.col;
            velocityy=path.get(0).row-snakeHead.row;
        }

    }
}
