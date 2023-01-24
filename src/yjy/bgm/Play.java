package yjy.bgm;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Play implements Runnable{
    private Player player = null;
    private Thread thread = null;

    public Play(String file) {
        try {
            player = new Player(new FileInputStream(file));
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            player.play();
        } catch (JavaLayerException ex) {
            System.err.println("Problem playing audio: " + ex);
        }
    }

    public void start() {
        thread = new Thread(this, "Player thread");
        thread.start();
    }

    public void stop() {
        player.close();
        thread = null;
    }

    public Player getPlayer() {
        return player;
    }
}
