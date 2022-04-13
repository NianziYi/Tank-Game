package Tankgame.GameObject.Wall;

import Tankgame.GameObject.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {

    private boolean visible = true;


    Wall(int x, int y, BufferedImage wallImage){
        super(x,y,0,0, 0, wallImage);
    }

    @Override
    public void drawImage(Graphics g){
        if(visible){
            super.drawImage(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.drawImage(this.objectImg, x, y, null);
            //g2d.drawRect(x, y, this.objectImg.getWidth(), this.objectImg.getHeight());
        }
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public abstract void update();

}
