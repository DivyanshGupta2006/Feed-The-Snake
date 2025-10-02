package com.Main;

import javax.swing.JFrame;

public class Launcher {

    //Creating it to be static
    public static JFrame f;

    public static void main(String[] args){

        //Components
        f = new JFrame("Feed The Snake!");
        gamePanel gp = new gamePanel();

        //Configuration
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setFocusable(true);
        f.add(gp);
        gp.config.loadConfig();
        if(gp.fullScreenOn==true) {
            f.setUndecorated(true);
        }
        f.pack();//Sets the size same as gamePanel
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        gp.requestFocus();
        gp.setUpAndStartGame();
    }
}
