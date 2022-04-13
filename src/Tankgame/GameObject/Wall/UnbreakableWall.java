package Tankgame.GameObject.Wall;

import Tankgame.GameObject.Wall.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(int x, int y, BufferedImage wallImage){
        super(x,y,wallImage);
    }

    @Override
    public void drawImage(Graphics g){
        super.drawImage(g);
    }

    @Override
    public void update(){}


}