package fr.ul.miage.sd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Barrage");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui.fxml"));    
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        launch(args);
    }
}
