package Tankgame.GameUtility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    // this class store all resources which are used in TRE

    private static Map <String, BufferedImage> resources;

    static{
        Resource.resources = new HashMap<>();
        try{
            Resource.resources.put("Tank1", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank1.gif"))));
            Resource.resources.put("Tank2", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank2.gif"))));
            Resource.resources.put("bullet", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet.png"))));
            Resource.resources.put("breakableWall", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall1.gif"))));
            Resource.resources.put("unbreakableWall", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall2.gif"))));
            Resource.resources.put("background", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp"))));
            Resource.resources.put("powerUp", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Rocket.gif"))));
        }catch (IOException ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage getResource(String key){
        return Resource.resources.get(key);
    }

}
