package yjy.bgm;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Play0 extends Thread{
    Player player;
    String music;
    Thread thread;
    public Play0(String file) {
        this.music = file;
    }
    public void run() {
        try {
            play();
            Stop();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
        player = new Player(buffer);
        player.play();
    }
    public void Stop() throws FileNotFoundException, JavaLayerException{
        player.close();
        thread = null;
    }
}