package gui;

import reviews.CreateTables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class CRApplication extends Application{

    private static Stage stg;

    //Resource used to learn how to switch between scenes: https://www.youtube.com/watch?v=HBBtlwGpBek
    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(CRApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 550, 600);
        primaryStage.setTitle("Course Review");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}