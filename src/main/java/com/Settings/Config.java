package com.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.Main.gamePanel;

public class Config {

    gamePanel g;
    public Config(gamePanel gp) {
        this.g=gp;
    }
    public void saveConfig() {
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter("src/Settings/config.txt"));

            //Full Screen
            if(g.fullScreenOn==true) {
                bw.write("On");
            }
            if(g.fullScreenOn==false) {
                bw.write("Off");
            }
            bw.newLine();

            //High Score
            bw.write(String.valueOf(g.highScore));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Settings/config.txt"));

            try {
                String s= br.readLine();
                if(s.equals("On")) {
                    g.fullScreenOn=true;
                }
                if(s.equals("Off")) {
                    g.fullScreenOn=false;
                }
                s=br.readLine();
                g.highScore=Integer.parseInt(s);
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
