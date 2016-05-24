package mdbclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                        .getClassLoader()
                        .getResource("mdbclient/res/main_layout.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MDB Client");
        stage.show();
        stage.setResizable(false);
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
