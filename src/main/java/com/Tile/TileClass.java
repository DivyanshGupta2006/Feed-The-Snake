package com.Tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Main.gamePanel;

public class TileClass {

    gamePanel g;
    Tiles[] tile;

    public TileClass(gamePanel gp) {
        this.g = gp;
        tile = new Tiles[5];
        setTileImages();
    }

    public void draw(Graphics2D g2d) {
        int col = 0;
        int row = 0;
        while ((col < g.maxWorldCol) && (row < g.maxWorldRow)) {
            BufferedImage image = loadTile(g.map, col, row);
            g2d.drawImage(image, col * g.tileSize, row * g.tileSize, g.tileSize, g.tileSize, null);
            col++;
            if (col >= g.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public Map createMap() {
        Map m = new Map(g);
        int i = 0;
        while (i < g.maxWorldCol * g.maxWorldRow) {
            m.rec[i] = g.r.nextInt(100) + 1;
            i++;
        }
        return m;
    }

    public void setTileImages() {
        setUp(0, "Grass001", true);
        setUp(1, "Grass002", true);
        setUp(2, "Grass01", true);
        setUp(3, "Grass02", true);
        setUp(4, "Wall", true);
    }

    public void setUp(int index, String imagePath, boolean collision) {
        try {
            tile[index] = new Tiles();
            tile[index].img = ImageIO.read(getClass().getResourceAsStream("/res/res_tiles/" + imagePath + ".png"));
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage loadTile(Map m, int col, int row) {
        BufferedImage img = null;
        int i = 0;
        if (col < g.maxWorldCol && row < g.maxWorldRow) {
            i = m.rec[row * g.maxWorldCol + col];
            if (i < 40) {
                img = tile[0].img;
            } else if (i > 40 && i < 77) {
                img = tile[2].img;
            } else if (i > 77 && i < 80) {
                img = tile[4].img;
            } else if (i > 80 && i < 90) {
                img = tile[1].img;
            } else {
                img = tile[3].img;
            }
        }
        return img;
    }

}
