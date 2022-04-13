package Tankgame.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject {

    private boolean visible = true;

    public PowerUp(int x, int y, BufferedImage powerUpImage){
        super(x,y,0,0, 0, powerUpImage);
    }

    @Override
    public void drawImage(Graphics g){
        if(visible){
            super.drawImage(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.drawImage(this.objectImg, x, y, null);
        }
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void speedUp(Tank t){t.setR(4);}

    public void update(){};
}
