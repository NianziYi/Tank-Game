package Tankgame.GameObject;

import Tankgame.GameUtility.GameConstants;
import Tankgame.GameUtility.MusicPlayer;
import Tankgame.GameUtility.Resource;
import Tankgame.GameUtility.TRE;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends GameObject {

    private int R = 2;
    private int defaultHP = 1000;
    private int defaultLive = 3;
    private int hp = defaultHP;
    private int liveCount = defaultLive;
    private boolean isDead = false;
    private final float ROTATIONSPEED = 3.0f;
    private HealthBar healthBar = new HealthBar();
    ArrayList<Bullet> ammo;
    private MusicPlayer music = new MusicPlayer();

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x,y,vx,vy,angle,img);
        this.ammo = new ArrayList<>();
    }

    public void setX(int x){ this.x = x; }

    public void setY(int y) { this. y = y;}

    public void setR(int R){this.R = R;}

    public int getOldx(){return this.x - this.vx;}

    public int getOldy(){return this.y - this.vy;}

    public boolean getStatus(){return isDead;}

    public int getLiveCount(){return this.liveCount;}

    public boolean isUpPressed(){return UpPressed;}

    public boolean isDownPressed(){return DownPressed;}

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.ShootPressed = true;
    }


    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    @Override
    public void update() {

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TRE.getTickCount() % 20 == 0) {
            Bullet b = new Bullet(this.x, this.y, this.vx, this.vy, this.angle, Resource.getResource("bullet"));
            this.ammo.add(b);
            music.playMusic("tankShot.wav");
        }
        this.ammo.forEach(bullet -> bullet.update());

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        this.vx = (int) Math.round(R * Math.cos(Math.toRadians(this.angle)));
        this.vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        this.x -= this.vx;
        this.y -= this.vy;
        checkBorder();
        this.getHitbox().setLocation(this.x,this.y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.getHitbox().setLocation(x,y);
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    public int getCenterX(){

        int xVal = this.x;

        if( xVal > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/4){
            xVal = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/4;
        }else if(xVal < GameConstants.GAME_SCREEN_WIDTH /4){
            xVal = GameConstants.GAME_SCREEN_WIDTH/4;
        }

        return xVal;
    }

    public int getCenterY(){

        int yVal = this.y;

        if( yVal > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT/2){
            yVal = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT/2;
        }else if(yVal < GameConstants.GAME_SCREEN_HEIGHT/2){
            yVal = GameConstants.GAME_SCREEN_HEIGHT/2;
        }

        return yVal;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        super.drawImage(g);
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        healthBar.drawImage(g);
    }

    public ArrayList<Bullet> getBulletList(){return this.ammo;}

    public void getAttack(Bullet b){
        int damage = b.getDamage();
        hp -= damage;
        if(hp <= 0){
            hp = 0;
            this.liveCount--;
            checkDeath();
        }
    }

    public void checkDeath(){
        if(this.liveCount <= 0){
            isDead = true;
        }else{
            //reset health bar
            this.hp = defaultHP;
        }
    }

    public class HealthBar{
        private int height = 5;
        private int length = 50;

        public void drawImage(Graphics g){
            //draw base, white color
            g.setColor(Color.WHITE);
            g.fillRect(x,y,length,height);

            //draw blood, green color
            g.setColor(Color.GREEN);
            g.fillRect(x,y,hp*length/defaultHP,height);
        }
    }

}