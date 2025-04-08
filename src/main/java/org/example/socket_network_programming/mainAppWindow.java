package org.example.socket_network_programming.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainAppWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(mainAppWindow.class.getResource("main-app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),850,450);
        stage.setTitle("Main App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
