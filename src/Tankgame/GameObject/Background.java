package Tankgame.GameObject;

import Tankgame.GameUtility.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject {

    public Background(int x, int y, BufferedImage BGimg){
        super(x,y,0,0,0,BGimg);
    }

    @Override
    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.objectImg, x, y, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT,null);

    }

    public void update(){}

}