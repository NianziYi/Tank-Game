package Tankgame.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int R = 7;
    private boolean visible = true;
    private int damage = 100;


    public Bullet(int x, int y, int vx, int vy, int angle, BufferedImage bulletimg){
        super(x,y,vx,vy,angle,bulletimg);
    }


    public void moveForward(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.getHitbox().setLocation(x,y);
    }

    @Override
    public void update(){
        if(visible) moveForward();
    }


    @Override
    public void drawImage(Graphics g){
            if(visible) super.drawImage(g);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public int getDamage(){return this.damage;}

}
