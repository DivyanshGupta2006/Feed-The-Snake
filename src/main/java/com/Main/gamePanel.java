package com.Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import Tile.*;
import java.util.Random;
import javax.swing.JPanel;

import AI.*;
import Entity.*;
import Input.KeyHandler;
import Recording.Recorder;
import Settings.Config;

public class gamePanel extends JPanel implements Runnable {

    //Variables
    public final int screenWidth=1200;
    public final int screenHeight=600;
    public final int tileSize=30;
    public int FPS=60;
    public final int maxWorldRow=screenHeight/tileSize;
    public final int maxWorldCol=screenWidth/tileSize;
    public final int titleState=1;
    public final int fState=2;
    public final int eState=3;
    public final int gameOverState=4;
    public final int autoState=5;
    public final int pauseState=6;
    public int screenWidth2=screenWidth;
    public int screenHeight2=screenHeight;
    public boolean fullScreenOn=false;
    public boolean showPath=false;
    public int gameState=0;
    public int prevGameState=0;
    public int highScore=0;
    public int score=0;
    public boolean initial=true;
    public double increment;
    public int autoStateFPS=20;
    public boolean showingRec=false;

    //Classes
    public KeyHandler kH= new KeyHandler(this);
    Thread gameLoop;
    BufferedImage tempScreen;
    Graphics2D g2d;
    public UI ui=new UI(this);
    public Random r = new Random();
    public Map map= new Map(this);
    public TileClass tC = new TileClass(this);
    public collisionChecker cC=new collisionChecker(this);
    public Config config = new Config(this);
    public PathFinder_01 pF = new PathFinder_01(this);
    public Recorder rec = new Recorder(this);

    //Game-elements
    public Snake s= new Snake(this);
    public Snake s2= new Snake(this);
    public Food food=new Food(this);
    public int mult=1;

    public gamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public void setUpAndStartGame() {
        gameState=titleState;
        tempScreen=new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2d=(Graphics2D)tempScreen.getGraphics();
        if(fullScreenOn==true) {
            setFullScreen();
        }
        map=tC.createMap();
        food.placeFood();
        pF.setNodes(s2.snakeHead.col, s2.snakeHead.row, food.col, food.row);
        pF.search();
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    @Override
    public void run() {
        //Set FPS(If used timer, then no need to set FPS like this
        double drawInterval;
        double delta=0;
        long prev=System.nanoTime();
        long timer=0;
        long current;
        while(gameLoop!=null) {
            if(FPS<1){FPS=1;}
            drawInterval=(1000000000/FPS)*mult; //Using exp(10,9) because system time can only be measured in nano-seconds

            //Now drawInterval is determined, set it to game loop
            current=System.nanoTime();
            delta+=(current-prev)/(drawInterval); //Adding fraction of time passed to delta
            timer+=current-prev;//Calculating draw time
            prev=current;
            if(delta>=1){ //Fraction of time passed is more than draw interval then redraw
                delta --;

                //Call update
                update();
                //First draw to temporary screen then draw temporary screen to displayed screen
                draw();
                drawToScreen();
            }
            if(timer>=1000000000) {
                timer=0;
            }
        }
    }

    public void setFPS(int fps) {
        this.FPS=fps;
    }

    public void update() {
        if(gameState==fState || gameState==eState || gameState==autoState) {
            if(showingRec==false) {
                rec.addRecord();
            }
            //Checking for eating of *food*
            if(cC.collision(s.snakeHead,food)) {
                s.snakeBody.add(new EntityClass(this));
                increment+=0.45;
                score++;
                if(increment>1) {
                    FPS++;
                    increment--;
                }
                food.placeFood();
                if(gameState==autoState) {
//					pF.setNodes(s.snakeHead.col, s.snakeHead.row, food.col, food.row);
//					pF.search();
                    setFPS(autoStateFPS);
                }
            }
            if(gameState==autoState) {
                pF.setNodes(s.snakeHead.col, s.snakeHead.row, food.col, food.row);
                if(pF.search()==true) {
                    pF.setNodes(food.col, food.row, s.snakeHead.col, s.snakeHead.row);
                    for(int i=0;i<pF.pathList.size();i++) {
                        pF.pathList.get(i).solid=true;
                    }
                    if(pF.search()==true){
                        pF.setNodes(s.snakeHead.col, s.snakeHead.row, food.col, food.row);
                        pF.search();
                        s.followPath(pF.pathList);
                    } else{pF.setNodes(s.snakeHead.col, s.snakeHead.row, s.snakeBody.get(s.snakeBody.size()-1).col, s.snakeBody.get(s.snakeBody.size()-1).row);
                        pF.search();
                        s.followPath(pF.pathList);
                    }
                }
            }
            if(showingRec){
                if(rec.records.size()>0) {
                    s.snakeHead.col=rec.records.get(0).snakeHeadCol;
                    s.snakeHead.row=rec.records.get(0).snakeHeadRow;
                    food.col=rec.records.get(0).foodCol;
                    food.row=rec.records.get(0).foodRow;
                }
            }

            //Movement of *snake body* via utilization of *snake head*
            for(int i = s.snakeBody.size()-1;i>=0;i--)
            {
                EntityClass snakePart = s.snakeBody.get(i);
                //Assigning coordinates of *snake head* to 1st *snake part* to facilitate movement via redrawing
                if(i==0) {
                    snakePart.col=s.snakeHead.col;
                    snakePart.row=s.snakeHead.row;
                }else {
                    EntityClass prevSnakePart = s.snakeBody.get(i-1);
                    //Assigning coordinates of Previous snake part to *snake part* to facilitate movement via redrawing
                    snakePart.col=prevSnakePart.col;
                    snakePart.row=prevSnakePart.row;
                }
            }
            //Updating tile coordinates of snake head according to velocities
            s.snakeHead.col+=s.velocityx;
            s.snakeHead.row+=s.velocityy;

            //Updating col and row to traverse boundary
            if(((s.snakeHead.col*tileSize)<=(screenWidth-tileSize))&&((s.snakeHead.col)>=0)) {
                if(((s.snakeHead.row*tileSize)<=(screenHeight-tileSize))&&(((s.snakeHead.row)>=0))) {

                } else if(((s.snakeHead.row*tileSize)>=(screenHeight-tileSize))) {
                    s.snakeHead.row=0;
                } else if((s.snakeHead.row)<=0) {
                    s.snakeHead.row=((screenHeight/tileSize)-1);
                }
            } else if((s.snakeHead.col*tileSize)>=(screenWidth-tileSize)) { s.snakeHead.col=0;
                if(((s.snakeHead.row*tileSize)<=(screenHeight-tileSize))&&(((s.snakeHead.row)>=0))) {

                } else if(((s.snakeHead.row*tileSize)>=(screenHeight-tileSize))) {
                    s.snakeHead.row=0;
                } else if((s.snakeHead.row)<=0) {
                    s.snakeHead.row=((screenHeight/tileSize)-1);
                }
            } else { s.snakeHead.col=((screenWidth/tileSize)-1);
                if(((s.snakeHead.row*tileSize)<=(screenHeight-tileSize))&&(((s.snakeHead.row)>=0))) {

                } else if(((s.snakeHead.row*tileSize)>=(screenHeight-tileSize))) {
                    s.snakeHead.row=0;
                } else if((s.snakeHead.row)<=0) {
                    s.snakeHead.row=((screenHeight/tileSize)-1);
                }
            }

            if(showingRec){
                if(rec.records.size()>0) {
                    s.snakeHead.col=rec.records.get(0).snakeHeadCol;
                    s.snakeHead.row=rec.records.get(0).snakeHeadRow;
                    food.col=rec.records.get(0).foodCol;
                    food.row=rec.records.get(0).foodRow;
                }
            }
            //For initial invincibility
            if(initial==true) {
                if(s.snakeHead.col>=10||s.snakeHead.row>=10) {
                    initial =false;
                }
            }

            if(initial==false) {
                //Condition for *game over*(collision of snake head with wall or snake body or win)
                //wall
                if(cC.collision(s.snakeHead, map)) {
                    gameState=gameOverState;
                }
                //body
                for(int i=0;i < s.snakeBody.size();i++) {
                    EntityClass snakePart = s.snakeBody.get(i);
                    if(cC.collision(snakePart , s.snakeHead )) {
                        gameState=gameOverState;
                    }
                }

                //Win
                if(score>=100&&gameState!=autoState) {
                    gameState=gameOverState;
                }
                if(gameState==gameOverState) {
                    highScore=(score>highScore)?(score):(highScore);
                    config.saveConfig();
                }
            }
        }
    }

    public void reset(int g) {
        mult=1;
        FPS=5;
        score=0;
        initial=true;
        kH.showScore=false;
        showingRec=false;
        s.velocityx=0;
        s.velocityy=1;
        rec.newRecord();
        s.snakeHead.col=5;
        s.snakeHead.row=5;
        food.placeFood();
        if(g==autoState) {
            setFPS(autoStateFPS);
            pF.setNodes(s.snakeHead.col, s.snakeHead.row, food.col, food.row);
            pF.search();
        }
        s.snakeBody.clear();
        s.snakeBody.add(new EntityClass(this));
        gameState=g;
    }

    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd=ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Launcher.f);

        //Get screen dimensions
        screenWidth2=Launcher.f.getWidth();
        screenHeight2=Launcher.f.getHeight();
    }

    public void draw() {
        //Draw all things here to temporary screen
        if(gameState==fState || gameState==eState||gameState==autoState) {
            tC.draw(g2d);
            if(gameState==autoState && showPath==true) {
                for(int i=0;i<pF.pathList.size();i++) {
                    g2d.setColor(new Color(164,8,0));
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                    g2d.fillRect(pF.pathList.get(i).col*tileSize, pF.pathList.get(i).row*tileSize, tileSize, tileSize);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }
            food.draw(g2d);
            s.draw(g2d);
            if(gameState==autoState) {
                s2.draw(g2d);
            }
        }
        ui.draw(g2d);
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2,null);
        g.dispose();
    }
}
