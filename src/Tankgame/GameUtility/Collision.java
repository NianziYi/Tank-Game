package Tankgame.GameUtility;

import Tankgame.GameObject.*;
import Tankgame.GameObject.Wall.BreakableWall;
import Tankgame.GameObject.Wall.Wall;

import java.util.ArrayList;

public class Collision {
    
    //check bullet collide tank
    public void handleCollision(Tank t, ArrayList<Bullet> bList) {
        for (int i = 0; i < bList.size(); i++) {
            Bullet b = bList.get(i);
            if (t.getHitbox().intersects(b.getHitbox())) {
                //t get damage
                t.getAttack(b);
                //bullet is not visible
                b.setVisible(false);
                bList.remove(b);
            }

        }
    }

    //check bullet collide wall
    public void handleCollision(Wall w, ArrayList<Bullet> bList){
        for(int i = 0; i < bList.size(); i++){
            Bullet b = bList.get(i);
            if(w.getHitbox().intersects(b.getHitbox())){
                if(w instanceof BreakableWall){
                    w.setVisible(false);
                    w.removeHitBox();
                }
                b.setVisible(false);
                bList.remove(b);
            }
        }
    }

    //check tank collide wall
    public void handleCollision(Tank t, Wall w){
        if(t.getHitbox().intersects(w.getHitbox())){
            if(t.isDownPressed() || t.isUpPressed()){
                t.setX(t.getOldx());
                t.setY(t.getOldy());
            }
        }
    }

    public void handleCollision(Tank t1, Tank t2){
        if(t1.getHitbox().intersects(t2.getHitbox())){
            if(t1.isDownPressed() || t1.isUpPressed()){
                t1.setX(t1.getOldx());
                t1.setY(t1.getOldy());
            }
            if(t2.isDownPressed() || t2.isUpPressed()){
                t2.setX(t2.getOldx());
                t2.setY(t2.getOldy());
            }
        }
    }

    //check tank collide powerUp
    public void handleCollision(Tank t, PowerUp p){
        if(t.getHitbox().intersects(p.getHitbox())){
            p.setVisible(false);
            p.speedUp(t);
        }
    }

}






