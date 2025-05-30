package me.chisato.multisort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 320);
        stage.setMaximized(false);
        stage.setMaxHeight(320);
        stage.setMaxWidth(600);
        stage.setTitle("起始页！");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
