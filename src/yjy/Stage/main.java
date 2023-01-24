package yjy.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primarystage) throws Exception {
        primaryStage = primarystage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primarystage.setTitle("登录");
        Scene scene = new Scene(root);
        primarystage.setScene(scene);
        primarystage.getIcons().add(new Image("yjy/images/logo.png"));
        primarystage.show();
        primarystage.resizableProperty().setValue(Boolean.FALSE);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }

    public static void main(String[] args) {
        Application.launch(args);
        if(!primaryStage.isShowing()){
            System.out.println("EXIT~~~");
            System.exit(0);
        }
    }

    //页面跳转
    public static void jumpTo(String uiName,String title){
        Parent root = null;
        try {
            root = FXMLLoader.load(main.class.getResource(uiName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
