package Tankgame.GameUtility;

import Tankgame.GameObject.*;
import Tankgame.GameObject.Wall.BreakableWall;
import Tankgame.GameObject.Wall.UnbreakableWall;
import Tankgame.GameObject.Wall.Wall;
import Tankgame.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private Background BG;
    private long tick = 0;
    private static long tickCount = 0;
    private Collision co;
    private static ArrayList<GameObject> gameObjects;
    private static ArrayList<Wall> walls;
    private static ArrayList<Bullet> bulletList1;
    private static ArrayList<Bullet> bulletList2;
    private static ArrayList<PowerUp> powerUpList;
    private MusicPlayer music = new MusicPlayer();


    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){

        try {
            //this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();

                //check collision
                checkCollision();

                //check if end game
                if(t1.getStatus() || t2.getStatus()){
                    music.stopMusic();
                    this.lf.setFrame("end");
                    return;
                }

                this.repaint();   // redraw game
                tickCount++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(40);
        this.t1.setY(40);
        this.t2.setX(1900);
        this.t2.setY(1900);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        music.playMusic("Music.wav");
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.gameObjects = new ArrayList<>();
        this.co = new Collision();
        this.walls = new ArrayList<>();
        this.powerUpList = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */

            //draw background
            BG = new Background(0, 0, Resource.getResource("background"));
            this.gameObjects.add(BG);

            //Read map
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(TRE.class.getClassLoader().getResourceAsStream("maps/map1")));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null){
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for (int curRow = 0; curRow < numRows; curRow++){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++)
                    switch (mapInfo[curCol]){
                        case "4":
                            PowerUp power = new PowerUp(curCol * 30, curRow * 30, Resource.getResource("powerUp"));
                            this.gameObjects.add(power);
                            this.powerUpList.add(power);
                            break;
                        case "2":
                            BreakableWall br = new BreakableWall(curCol * 30, curRow * 30, Resource.getResource("breakableWall"));
                            this.gameObjects.add(br);
                            this.walls.add(br);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall ubr = new UnbreakableWall(curCol * 30, curRow * 30, Resource.getResource("unbreakableWall"));
                            this.gameObjects.add(ubr);
                            this.walls.add(ubr);
                    }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //Create two tanks
        t1 = new Tank(40, 40, 0, 0, 0, Resource.getResource("Tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.gameObjects.add(t1);
        this.bulletList1 = t1.getBulletList();

        t2 = new Tank(1900, 1900, 0, 0, 180, Resource.getResource("Tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);
        this.gameObjects.add(t2);
        this.bulletList2 = t2.getBulletList();

    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        Graphics2D buffer = world.createGraphics();
        buffer.fillRect(0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));


        //Split the screen and draw the miniMap
        BufferedImage leftHalf = world.getSubimage(t1.getCenterX() - GameConstants.GAME_SCREEN_WIDTH/4, t1.getCenterY() -GameConstants.GAME_SCREEN_HEIGHT/2,
                GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.getCenterX()- GameConstants.GAME_SCREEN_WIDTH/4, t2.getCenterY() -GameConstants.GAME_SCREEN_HEIGHT/2,
                GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0,0, GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2,0,null);
        g2.scale(0.1, 0.1);
        g2.drawImage(miniMap,6000,5600, null);

        //set live count
        Font newFont = new Font("TimesRoman", Font.BOLD , 300);
        g2.setFont(newFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Player1 Lives: " + t1.getLiveCount(), 1500,700);
        g2.drawString("Player2 Lives: " + t2.getLiveCount(), 10000, 700);

    }


    public void checkCollision(){

        //check tank collide bullet
        co.handleCollision(t1,bulletList2);
        co.handleCollision(t2,bulletList1);

        //check wall collide bullet
        for(Wall w: walls){
            co.handleCollision(w,bulletList1);
            co.handleCollision(w,bulletList2);
        }

        //check tank collide wall
        for(Wall w: walls){
            co.handleCollision(t1,w);
            co.handleCollision(t2,w);
        }

        //check tank collide wall
        for(PowerUp p: powerUpList){
            co.handleCollision(t1,p);
            co.handleCollision(t2,p);
        }

        //check tank collide tank
        co.handleCollision(t1,t2);

    }

    public static long getTickCount() {
        return tickCount;
    }
}