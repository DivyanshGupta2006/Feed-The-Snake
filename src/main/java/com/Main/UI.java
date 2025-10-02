package com.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    public int commandNum=0;
    gamePanel g;
    public UI(gamePanel gp) {
        this.g=gp;
    }
    public void draw(Graphics2D g2d) {
        if(g.gameState==g.titleState) {
            drawTitleScreen(g2d);
        } else if(g.gameState==g.gameOverState) {
            drawGameOverScreen(g2d);
        } else if(g.gameState==g.pauseState) {
            drawPauseScreen(g2d);
        } else if(g.gameState==g.fState || g.gameState==g.eState || g.gameState==g.autoState) {
            drawInstruct(g2d);
        }
    }
    public void drawPauseScreen(Graphics2D g2d) {
        g2d.setFont(new Font("Arial",Font.BOLD,100));

        g2d.setColor(new Color(164,8,0));
        String s="PAUSED";
        int l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int x=(g.screenWidth-l)/2;
        int y=325;
        g2d.drawString(s, x, y);
    }
    public void drawGameOverScreen(Graphics2D g2d) {
        g.tC.draw(g2d);
        if(g.score<100||g.prevGameState==g.autoState ) {
            g2d.setFont(new Font("Arial", Font.BOLD,80));
            g2d.setColor(new Color(164,8,0));
            g2d.drawString ("Game Over !!! Score : " + g.score,150,320);
        } else {
            g2d.setFont(new Font("Arial", Font.BOLD,80));
            g2d.setColor(new Color(164,8,0));
            String s="You Won!";
            int x;
            int l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2;
            g2d.drawString(s, x, g.screenHeight/2+g.tileSize/2);
        }
    }
    public void drawTitleScreen(Graphics2D g2d) {

        if(g.kH.showScore==false) {
            //Draw fleeting tiles
            g.setFPS(7);
            g.map=g.tC.createMap();
            g.tC.draw(g2d);

            //Draw text
            int x=0;
            int y=0;
            int l=0;
            String s="";
            g2d.setFont(new Font("Arial",Font.BOLD,50));

            g2d.setColor(new Color(100,8,0));
            s="Snake Game by Divy";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2+300;
            y=100;
            g2d.drawString(s, x, y);
            g2d.setColor(new Color(164,8,0));
            g2d.drawString(s, x+3, y+3);

            s="Play 4D";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2+300;
            y=250;
            g2d.drawString(s, x, y);
            if(commandNum==0) {
                g2d.drawString(">", x-(2*g.tileSize), y);
            }

            s="Play 8D";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2+300;
            y=325;
            g2d.drawString(s, x, y);
            if(commandNum==1) {
                g2d.drawString(">", x-(2*g.tileSize), y);
            }
            s="AutoPlay";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2+300;
            y=400;
            g2d.drawString(s, x, y);
            if(commandNum==2) {
                g2d.drawString(">", x-(2*g.tileSize), y);
            }

            s="Exit";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2+300;
            y=475;
            g2d.drawString(s, x, y);
            if(commandNum==3) {
                g2d.drawString(">", x-(2*g.tileSize), y);
            }

            //Display Controls
            int dist=60;
            s="CONTROLS";
            l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2-400;
            y=100;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,40f));
            s="For 4D/Navigating-";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="WASD,Arrow keys,Numpad";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,40f));
            s="For 8D-";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="Numpad";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="Use Numpad after num-lock is on.";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="AutoPlay available in 4D only.";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="Press F for full-screen and restart the game.";
            y+=dist;
            g2d.drawString(s, x, y);
            g2d.setFont(g.getFont().deriveFont(Font.BOLD,30f));
            s="Press H for High Score.";
            y+=dist;
            g2d.drawString(s, x, y);
        } else {
            g.tC.draw(g2d);
            g2d.setFont(new Font("Arial", Font.BOLD,80));
            g2d.setColor(new Color(164,8,0));
            String s="Highest Score:"+g.highScore;
            int x;
            int l = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            x=(g.screenWidth-l)/2;
            g2d.drawString(s, x, g.screenHeight/2+g.tileSize/2);
        }
    }
    public void drawInstruct(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD,16));
        g2d.setColor(new Color(164,8,0));
        g2d.drawString("For Restart, Press SPACE", g.screenWidth - 200, g.tileSize);
        g2d.drawString("Score : "+ g.score,g.tileSize-15,g.tileSize);
        g2d.drawString("FPS : "+ g.FPS,g.tileSize-15,g.tileSize*2);
        g2d.drawString("For Main menu, Press BACKSPACE",  g.screenWidth-275, g.screenHeight-g.tileSize+10);
        g2d.drawString("Press P to pause",  g.tileSize-15, g.screenHeight-g.tileSize+10);
        if(g.gameState==g.autoState) {
            g2d.drawString("Press X to show path", g.screenWidth-700,  g.screenHeight-g.tileSize+10);
        }
    }
}
