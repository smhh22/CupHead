//besmellah
//کیان ایزدپناه دیباگ
//امیرحسین روان نخجوانی و محسن قاسمی گرفتن تصاویر و فریم‌ها

package com.cuphead;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class App extends Application {
    private static Scene scene;
    private static MediaPlayer musicMediaPlayer;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("login_menu");
        scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void setMenu(String menuName) {
        Parent root = loadFXML(menuName);
        scene.setRoot(root);
    }

    private static Parent loadFXML(String name) {
        try {
            URL address = new URL(App.class.getResource("fxml/" + name + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(App.class.getResource("fxml/" + name + ".fxml").toExternalForm());
            return null;
        }
    }

    public static void exit() {
        System.exit(0);
    }
}