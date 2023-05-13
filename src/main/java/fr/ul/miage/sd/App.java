package fr.ul.miage.sd;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ul.miage.sd.metier.Session;
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

    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final String API_KEY = "d8fc70d26389ad515a644a77aa5d37b6";
    public static final String SHARED_SECRET = "5f036c442937a2a86c8024dfe5d3cd75";
    public static final Logger logger = LoggerFactory.getLogger(App.class);
    private static String signature = null;
    private static Session session = null;

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

    public static void hashSignature(String token) {
        App.signature = DigestUtils.md5Hex(String.format("api_key%smethodauth.getSessiontoken%s%s", App.API_KEY, token, App.SHARED_SECRET)).toUpperCase();
    }

    public static String getSignature() {
        return App.signature;
    }

    public static void setSignature(String signature) {
        App.signature = signature;
    }

    public static Session getSession() {
        return App.session;
    }

    public static void setSession(Session session) {
        App.session = session;
    }
}
