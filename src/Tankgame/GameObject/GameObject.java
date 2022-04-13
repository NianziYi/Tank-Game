package Tankgame.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x,y,vx,vy,angle;
    protected BufferedImage objectImg;
    private Rectangle hitbox;

    protected GameObject(int x, int y, int vx, int vy, int angle, BufferedImage objectImg){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.objectImg = objectImg;
        hitbox = new Rectangle(x, y, objectImg.getWidth(), objectImg.getHeight());
    }

    public void drawImage(Graphics g){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.objectImg.getWidth() / 2.0, this.objectImg.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.objectImg, rotation, null);
    }

    public abstract void update();

    public Rectangle getHitbox(){
        return this.hitbox;
    }

    public void removeHitBox(){
        this.hitbox.height = 0;
        this.hitbox.width = 0;
    }


}
