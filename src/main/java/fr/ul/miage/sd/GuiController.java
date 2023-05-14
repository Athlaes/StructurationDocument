package fr.ul.miage.sd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ul.miage.sd.repository.TopArtistRepository;
import fr.ul.miage.sd.response.GeoArtistResponse;
import fr.ul.miage.sd.response.GeoTopArtistsResponse;
import fr.ul.miage.sd.response.SessionResponse;
import fr.ul.miage.sd.response.TokenResponse;
import fr.ul.miage.sd.response.TopArtistsResponse;
import fr.ul.miage.sd.service.HTTPTools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class GuiController {
    @FXML
    private Button btnConnexion;

    @FXML
    private Button btnDeconnexion;

    @FXML
    private Button btnRechercher;

    @FXML
    private Label labConnexion;

    @FXML
    private Label labArtiste;

    @FXML
    private Label labTag;

    @FXML
    private Label labAlbum;

    @FXML
    private Label labTrack;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtArtiste;

    @FXML
    private TextField txtTrack;

    @FXML
    private TextField txtAlbum;

    @FXML
    private ChoiceBox<String> selectType;

    @FXML
    private ChoiceBox<String> selectCountry;

    @FXML
    private ChoiceBox<String> selectClassement;

    @FXML
    private TableView<GeoArtistResponse> tabClassement;

    @FXML
    private TableColumn<Object, String> colName;

    @FXML
    private TableColumn<Object, String> colNbEcoute;

    @FXML
    private TableColumn<Object, String> colMbid;

    @FXML
    private TableColumn<Object, String> colUrl;

    @FXML
    private TableColumn<Object, String> colEvolution;


    private final ObservableList<String> selectTypeFields = FXCollections.observableArrayList("Musique", "Artiste", "Tag", "Album");

    private final ObservableList<String> selectClassementFields = FXCollections.observableArrayList("Artiste", "Musique");


    @FXML
    private void initialize() {
        this.btnDeconnexion.setDisable(true);
        this.setupTabClassement();

        
        this.selectType.setItems(selectTypeFields);
        this.selectType.getSelectionModel().select("Musique");
        this.manageSelectionChange("Musique");
        this.selectType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                manageSelectionChange(arg2);
            }
        });
        
        this.selectCountry.setItems(App.pays);
        this.selectCountry.getSelectionModel().select("France");
        this.selectCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                manageCountryChange(arg2);
            }
        });
        
        this.selectClassement.setItems(this.selectClassementFields);
        this.selectClassement.getSelectionModel().select("Artiste");
        this.selectType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                getClassement();
            }
        });

        this.getClassement();
    }

    private void setupTabClassement() {
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNbEcoute.setCellValueFactory(new PropertyValueFactory<>("listeners"));
        this.colEvolution.setCellValueFactory(new PropertyValueFactory<>("evolution"));
        this.colMbid.setCellValueFactory(new PropertyValueFactory<>("mbid"));
        this.colUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
    }

    private void manageCountryChange(String value) {
        // TODO : TO WRITTE
    }

    private void manageSelectionChange(String value) {
        switch (value) {
            case "Musique" : 
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(false);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(true);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(false);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(true);
                break;
            case "Artiste" :
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(false);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(false);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(false);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(false);
                break;
            case "Tag" : 
                this.txtTag.setVisible(true);
                this.txtAlbum.setVisible(false);
                this.txtArtiste.setVisible(false);
                this.txtTrack.setVisible(false);
                this.labTag.setVisible(true);
                this.labAlbum.setVisible(false);
                this.labArtiste.setVisible(false);
                this.labTrack.setVisible(false);
                break;
            case "Album" : 
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(true);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(false);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(true);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(false);
                break;
        }
    }

    private void getClassement() {
        String categ = this.selectClassement.getSelectionModel().getSelectedItem();
        String pays = this.selectCountry.getSelectionModel().getSelectedItem();
        try {
            String method = "";
            if (categ.equalsIgnoreCase("Artiste")) {
                method = "geo.gettopartists";
            } else if (categ.equalsIgnoreCase("Musique")) {
                method = "geo.gettoptracks";
            }
            GeoTopArtistsResponse response = App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=%s&country=%s&limit=10", method, pays), true), GeoTopArtistsResponse.class);
            TopArtistRepository.getInstance().createOrUpdate(response.getTopartists());
            ObservableList<GeoArtistResponse> geoArtists = FXCollections.observableArrayList(response.getTopartists().getArtist());
            this.tabClassement.setItems(geoArtists);
        } catch (IOException e) {
            App.logger.error("Impossible d'effectuer l'appel pour le classement", e);
        }
    }

    @FXML
    private void connectUser() {
        try {
            // Obtention du token à usage unique
            String urlToken = "http://ws.audioscrobbler.com/2.0/?method=auth.gettoken";
            TokenResponse response = App.objectMapper.readValue(HTTPTools.sendGet(urlToken, false), TokenResponse.class);
            // Obtention de l'accord de l'utilisateur via le navigateur
            Desktop.getDesktop().browse(new URI("http://www.last.fm/api/auth/?&token="+response.getToken()+"&api_key="+App.API_KEY));
            // Obtention de la signature
            App.hashSignature(response.getToken());
            this.getSession(response);
            this.labConnexion.setText(String.format("Bonjour %s !", App.getSession().getName()));
            this.btnConnexion.setDisable(true);
            this.btnDeconnexion.setDisable(false);
        } catch (JsonMappingException e) {
            App.logger.error("Impossible de mapper la requête", e);
        } catch (JsonProcessingException e) {
            App.logger.error("Erreur dans le process", e);
        } catch (IOException | URISyntaxException e) {
            App.logger.error("Erreur Dans l'ouverture du navigateur pour autorisation", e);
        }
    }

    private void getSession(TokenResponse tokenResponse) throws IOException {
        String urlSession = "http://ws.audioscrobbler.com/2.0/?method=auth.getSession&token="+tokenResponse.getToken();
        while(Objects.isNull(App.getSession())) {
            String tmpRes = HTTPTools.sendGet(urlSession, true);
            if (Objects.nonNull(tmpRes)) {
                SessionResponse sessionResponse = App.objectMapper.readValue(tmpRes, SessionResponse.class);
                App.setSession(sessionResponse.getSession());
            } else {
                App.logger.info("L'utilisateur n'a pas accepté l'application sur le navigateur");
            }
        }
    }
}
