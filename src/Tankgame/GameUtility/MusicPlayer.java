package Tankgame.GameUtility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlayer {
    AudioInputStream audio;
    InputStream stream;
    InputStream bufferedIn;
    Clip clip;

    public void playMusic(String music){
        try{
            stream = MusicPlayer.class.getClassLoader().getResourceAsStream(music);
            bufferedIn = new BufferedInputStream(stream);
            audio = AudioSystem.getAudioInputStream(bufferedIn);
            this.clip = AudioSystem.getClip();
            this.clip.open(audio);
            this.clip.start();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void stopMusic(){
        this.clip.close();
    }
}
