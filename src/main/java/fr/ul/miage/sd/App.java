package fr.ul.miage.sd;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ul.miage.sd.metier.Session;
import fr.ul.miage.sd.response.CountryResponse;
import fr.ul.miage.sd.service.HTTPTools;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

    public static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(Include.NON_NULL);
    public static final String API_KEY = "d8fc70d26389ad515a644a77aa5d37b6";
    public static final String SHARED_SECRET = "5f036c442937a2a86c8024dfe5d3cd75";
    public static final Logger logger = LoggerFactory.getLogger(App.class);
    public static final String COL_BEGINNING = "GLCYE_";
    public static final ObservableList<String> pays = getPays();
    private static String signature = null;
    private static Session session = null;

    @Override
    public void start(Stage stage) throws IOException {
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

    private static ObservableList<String> getPays() {
        try {
            String url = "https://data.enseignementsup-recherche.gouv.fr/api/records/1.0/search/?dataset=curiexplore-pays&q=&sort=iso3&facet=name_en";
            CountryResponse response = objectMapper.readValue(HTTPTools.sendGet(url, false), CountryResponse.class);
            ObservableList<String> tmpPays = FXCollections.observableArrayList();
            response.getFacetGroups().get(0).getPays().forEach(x -> tmpPays.add(x.getName()));
            return tmpPays;
        } catch (Exception e) {
            logger.error("Impossible de récupérer la liste des pays", e);
            return FXCollections.observableArrayList("Redémarrer l'application pour récupérer la liste des pays");
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
