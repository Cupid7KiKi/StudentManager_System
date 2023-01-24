package yjy.bgm;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {

    public static Media media = new Media("file:/D:/Work-Place/JavaFX+JDBC/src/yjy/bgm/bgm.mp3");
    public static MediaPlayer mp = new MediaPlayer(media);

    public static void open(){
        mp.play();
    }

    public static void pause(){
        mp.pause();
    }

}
