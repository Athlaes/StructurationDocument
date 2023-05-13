package fr.ul.miage.sd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ul.miage.sd.response.SessionResponse;
import fr.ul.miage.sd.response.TokenResponse;
import fr.ul.miage.sd.service.HTTPTools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private Label labConnexion;

    @FXML
    private void initialize() {
        this.btnDeconnexion.setDisable(true);
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
