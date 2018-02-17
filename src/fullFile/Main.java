package fullFile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fullFileLayout.fxml"));
        primaryStage.setTitle("Part Finder v.1.0 2018");
        primaryStage.setScene(new Scene(root, 800, 200));
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
