package fr.ul.miage.sd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ul.miage.sd.metier.Comment;
import fr.ul.miage.sd.repository.AlbumRepository;
import fr.ul.miage.sd.repository.ArtistRepository;
import fr.ul.miage.sd.repository.CommentRepository;
import fr.ul.miage.sd.repository.TagRepository;
import fr.ul.miage.sd.repository.TopArtistRepository;
import fr.ul.miage.sd.repository.TopTrackRepository;
import fr.ul.miage.sd.repository.TrackRepository;
import fr.ul.miage.sd.response.AlbumResponse;
import fr.ul.miage.sd.response.AlbumResponseBody;
import fr.ul.miage.sd.response.ArtistResponse;
import fr.ul.miage.sd.response.ArtistResponseBody;
import fr.ul.miage.sd.response.GeoArtistResponse;
import fr.ul.miage.sd.response.GeoTopArtistsResponse;
import fr.ul.miage.sd.response.GeoTopTracksResponse;
import fr.ul.miage.sd.response.GeoTrackResponse;
import fr.ul.miage.sd.response.SessionResponse;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.response.TagResponseBody;
import fr.ul.miage.sd.response.TagTopAlbumResponse;
import fr.ul.miage.sd.response.TagTopTrackResponse;
import fr.ul.miage.sd.response.TokenResponse;
import fr.ul.miage.sd.response.TopArtistsResponse;
import fr.ul.miage.sd.response.TopTracksResponse;
import fr.ul.miage.sd.response.TrackResponse;
import fr.ul.miage.sd.response.TrackResponseBody;
import fr.ul.miage.sd.service.HTTPTools;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuiController {
    /**
     *
     */
    private static final String MAP_ERROR = "Impossible de mapper l'objet";

    private static final String INFORMATIONS_NOT_GET = "Impossible de récupérer les informations !";

    private static final String ALBUM = "Album";

    private static final String MUSIQUE = "Musique";

    private static final String ARTISTE = "Artiste";

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
    private TextField txtArtiste1;

    @FXML
    private TextField txtArtiste2;

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
    private TableColumn<GeoArtistResponse, String> colName;

    @FXML
    private TableColumn<GeoArtistResponse, String> colNbEcoute;

    @FXML
    private TableColumn<GeoArtistResponse, String> colMbid;

    @FXML
    private TableColumn<GeoArtistResponse, String> colUrl;

    @FXML
    private TableColumn<GeoArtistResponse, String> colEvolution;

    @FXML
    private TableView<GeoTrackResponse> tabClassementT;

    @FXML
    private TableColumn<GeoTrackResponse, String> colNameT;

    @FXML
    private TableColumn<GeoTrackResponse, String> colNbEcouteT;

    @FXML
    private TableColumn<GeoTrackResponse, String> colMbidT;

    @FXML
    private TableColumn<GeoTrackResponse, String> colUrlT;

    @FXML
    private TableColumn<GeoTrackResponse, String> colEvolutionT;

    @FXML
    private TableView<TrackResponseBody> tabSimilarTracks;

    @FXML
    private TableColumn<TrackResponseBody, String> colSimilarTrackName;

    @FXML
    private TableColumn<TrackResponseBody, String> colSimilarTrackMbid;

    @FXML
    private TableColumn<TrackResponseBody, String> colSimilarTrackArtist;

    @FXML
    private TableColumn<TrackResponseBody, String> colSimilarTrackAlbum;

    @FXML
    private TableColumn<TrackResponseBody, String> colSimilarTrackTags;

    @FXML
    private TableView<AlbumResponseBody> tabSimilarAlbums;

    @FXML
    private TableColumn<AlbumResponseBody, String> colSimilarAlbumsName;

    @FXML
    private TableColumn<AlbumResponseBody, String> colSimilarAlbumsMbid;

    @FXML
    private TableColumn<AlbumResponseBody, String> colSimilarAlbumsArtist;

    @FXML
    private TableColumn<AlbumResponseBody, String> colSimilarAlbumsTags;

    @FXML
    private TableView<Comment> tabComments;

    @FXML
    private TableColumn<Comment, String> colNameComment;

    @FXML
    private TableColumn<Comment, String> colComments;

    @FXML
    private TextArea txtAreaComment;

    @FXML
    private Pane paneCommentaire;

    @FXML
    private TextArea textAreaInfo;

    @FXML
    private Button btnPoster;

    private String selectedMbid;

    private final ObservableList<String> selectTypeFields = FXCollections.observableArrayList(MUSIQUE, ARTISTE, "Tag", ALBUM);

    private final ObservableList<String> selectClassementFields = FXCollections.observableArrayList(ARTISTE, MUSIQUE);

    @FXML
    private void initialize() {
        this.btnDeconnexion.setDisable(true);
        this.paneCommentaire.setVisible(false);
        this.btnPoster.setDisable(true);
        this.setupTabClassement();
        this.setupSelects();
        this.getClassement();
    }

    @FXML
    private void disconnectUser() {
        App.setSession(null);   
        this.labConnexion.setText("Bonjour cher utilisateur !");
        this.paneCommentaire.setVisible(false);
    }

    private void getClassement() {
        String categ = this.selectClassement.getSelectionModel().getSelectedItem();
        String pays = this.selectCountry.getSelectionModel().getSelectedItem();
        try {
            if (categ.equalsIgnoreCase(ARTISTE)) {
                this.getArtistClassement(pays);
            } else if (categ.equalsIgnoreCase(MUSIQUE)) {
                this.getTrackClassement(pays);
            }
        } catch (IOException e) {
            App.logger.error("Impossible d'effectuer l'appel pour le classement", e);
        }
    }

    private void getArtistClassement(String pays) throws IOException {
        GeoTopArtistsResponse response = App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=%s&limit=10", pays), true, false), GeoTopArtistsResponse.class);
        TopArtistRepository.getInstance().createOrUpdate(response.getTopartists());
        TopArtistsResponse classement = TopArtistRepository.getInstance().findOne(pays);
        ObservableList<GeoArtistResponse> geoArtists = FXCollections.observableArrayList(classement.getArtist());
        geoArtists.sort((x, y)  -> {
            if (x.getListeners() < y.getListeners()) {
                return 1;
            } else if (x.getListeners() > y.getListeners()) {
                return -1;
            } else {
                return 0;
            }
        });
        this.tabClassement.setItems(geoArtists);
        this.tabClassement.setVisible(true);
        this.tabClassementT.setVisible(false);
    }

    private void getTrackClassement(String pays) throws IOException {
        GeoTopTracksResponse response = App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=%s&limit=10", pays), true, false), GeoTopTracksResponse.class);
        TopTrackRepository.getInstance().createOrUpdate(response.getTracks());
        TopTracksResponse classement = TopTrackRepository.getInstance().findOne(pays);
        ObservableList<GeoTrackResponse> geoTracks = FXCollections.observableArrayList(classement.getTrack());
        geoTracks.sort((x, y)  -> {
                if (x.getListeners() < y.getListeners()) {
                    return 1;
                } else if (x.getListeners() > y.getListeners()) {
                    return -1;
                } else {
                    return 0;
                }
        });
        this.tabClassementT.setItems(geoTracks);
        this.tabClassement.setVisible(false);
        this.tabClassementT.setVisible(true);
    }

    @FXML
    private void connectUser() {
        try {
            // Obtention du token à usage unique
            String urlToken = "http://ws.audioscrobbler.com/2.0/?method=auth.gettoken";
            TokenResponse response = App.objectMapper.readValue(HTTPTools.sendGet(urlToken, false, true), TokenResponse.class);
            // Obtention de l'accord de l'utilisateur via le navigateur
            Desktop.getDesktop().browse(new URI("http://www.last.fm/api/auth/?&token="+response.getToken()+"&api_key="+App.API_KEY));
            // Obtention de la signature
            App.hashSignature(response.getToken());
            this.getSession(response);
            this.manageSelectionChange(this.selectType.getSelectionModel().getSelectedItem());
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
            String tmpRes = HTTPTools.sendGet(urlSession, true, true);
            if (Objects.nonNull(tmpRes)) {
                SessionResponse sessionResponse = App.objectMapper.readValue(tmpRes, SessionResponse.class);
                App.setSession(sessionResponse.getSession());
            } else {
                App.logger.info("L'utilisateur n'a pas accepté l'application sur le navigateur");
            }
        }
    }

    @FXML
    private void getSimilar() {
        String artisteName1 = this.txtArtiste1.getText();
        String artisteName2 = this.txtArtiste2.getText();
        List<TagResponseBody> tagCommuns;
        ArtistResponseBody artiste1 = this.getArtistInfoWithValidation(artisteName1);
        ArtistResponseBody artiste2 = this.getArtistInfoWithValidation(artisteName2);
        if (Objects.nonNull(artiste1) && Objects.nonNull(artiste2) && Objects.nonNull(artiste1.getTags()) && Objects.nonNull(artiste2.getTags())) {
            tagCommuns = artiste1.getTags().getTags().stream().filter(x -> artiste2.getTags().getTags().stream().anyMatch(y -> y.getName().equalsIgnoreCase(x.getName()))).collect(Collectors.toList());
            if (tagCommuns.isEmpty()) {
                App.logger.warn("Il n'y a pas assez d'information sur la similarité de ces deux artistes pour effectuer une recherche");
            } else {
                List<TrackResponseBody> tracks = new ArrayList<>();
                List<AlbumResponseBody> albums = new ArrayList<>();
                for (TagResponseBody tagResponse : tagCommuns) {
                    albums.addAll(AlbumRepository.getInstance().findAllByTags(tagResponse.getName()));
                    tracks.addAll(TrackRepository.getInstance().findAllByTags(tagResponse.getName()));
                }
                this.searchMoreAlbumIfNeeded(tagCommuns, albums);
                this.searchMoreTrackIfNeeded(tagCommuns, tracks);
                
                this.tabSimilarAlbums.setItems(FXCollections.observableArrayList(albums));
                this.tabSimilarTracks.setItems(FXCollections.observableArrayList(tracks));
            }
        } else {
            App.logger.info("Aucun tag trouvé pour l'un des deux artistes");
        }    
    }

    private void searchMoreAlbumIfNeeded(List<TagResponseBody> tags, List<AlbumResponseBody> albums){
        try { 
            if (albums.size() <20) {
                App.logger.info("Pas assez de résultat, recherche dans l'API");
                for (TagResponseBody tagResponse : tags) {
                    TagTopAlbumResponse tagTopAlbumResponse = this.getTagTopAlbum(tagResponse.getName());
                    tagTopAlbumResponse.getAlbums().getAlbum().forEach(x -> AlbumRepository.getInstance().createOrUpdate(x));
                    albums.addAll(tagTopAlbumResponse.getAlbums().getAlbum());
                }
            }
        } catch (IOException e) {
            App.logger.error("Aucun album n'a pu être trouvé", e);
        }
    }

    private void searchMoreTrackIfNeeded(List<TagResponseBody> tags, List<TrackResponseBody> tracks){
        try { 
            if (tracks.size() <20) {
                App.logger.info("Pas assez de résultat, recherche dans l'API");
                for (TagResponseBody tagResponse : tags) {
                    TagTopTrackResponse tagTopTrackResponse = this.getTagTopTrack(tagResponse.getName());
                    tagTopTrackResponse.getTracks().getTrack().forEach(x -> TrackRepository.getInstance().createOrUpdate(x));
                    tracks.addAll(tagTopTrackResponse.getTracks().getTrack());
                }
            }
        } catch (IOException e) {
            App.logger.error("Aucun album n'a pu être trouvé", e);
        }
    }

    private TagTopTrackResponse getTagTopTrack(String tagName) throws IOException{
        try {
            return App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=%s", tagName), true, false), TagTopTrackResponse.class);
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    private TagTopAlbumResponse getTagTopAlbum(String tagName) throws IOException{
        try {
            return App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=tag.gettopalbums&tag=%s", tagName), true, false), TagTopAlbumResponse.class);
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    @FXML
    private void postComment() {
        if (Objects.nonNull(App.getSession()) && Objects.nonNull(App.getSession().getKey()) && !App.getSession().getName().isEmpty() && !this.txtAreaComment.getText().isEmpty() && Objects.nonNull(this.selectedMbid)) {
            Comment comment = new Comment(App.getSession().getKey(), App.getSession().getName(), this.selectedMbid, this.txtAreaComment.getText());
            CommentRepository.getInstance().createOrUpdate(comment);
            if (Objects.isNull(this.tabComments.getItems())) {
                this.tabComments.setItems(FXCollections.observableArrayList(comment));
            } else {
                this.tabComments.getItems().add(comment);
            }
        } 
    }

    @FXML
    public void search() {
        String selectedOption = this.selectType.getValue();
        List<Comment> comments = new ArrayList<>();
        switch (selectedOption) {
            case ALBUM:
                AlbumResponseBody album = this.getAlbumInfoWithValidation(this.txtAlbum.getText(), this.txtArtiste.getText());
                if (Objects.nonNull(album)) {
                    this.textAreaInfo.setText(album.toString());
                    comments = CommentRepository.getInstance().findAllComments(album.getMbid());
                    this.checkMbid(album.getMbid());
                } else {
                    this.textAreaInfo.setText(INFORMATIONS_NOT_GET);
                }
                break;
            case MUSIQUE:
                TrackResponseBody track = this.getTrackInfoWithValidation(this.txtTrack.getText(), this.txtArtiste.getText());
                if (Objects.nonNull(track)) {
                    this.textAreaInfo.setText(track.toString());
                    comments = CommentRepository.getInstance().findAllComments(track.getMbid());
                    this.checkMbid(track.getMbid());
                } else {
                    this.textAreaInfo.setText(INFORMATIONS_NOT_GET);
                }
                break;
            case "Tag":
                this.btnPoster.setDisable(false);
                TagResponseBody tag = this.getTagInfoWithValidation(this.txtTag.getText());
                if (Objects.nonNull(tag)) {
                    this.textAreaInfo.setText(tag.toString());
                } else {
                    this.textAreaInfo.setText(INFORMATIONS_NOT_GET);
                }
                break;
            case ARTISTE:
                ArtistResponseBody artist = this.getArtistInfoWithValidation(this.txtArtiste.getText());
                if (Objects.nonNull(artist)) {
                    this.textAreaInfo.setText(artist.toString());
                    comments = CommentRepository.getInstance().findAllComments(artist.getMbid());
                    this.checkMbid(artist.getMbid());
                } else {
                    this.textAreaInfo.setText(INFORMATIONS_NOT_GET);
                }
                break;
            default:
                break;
        }
        this.tabComments.setItems(FXCollections.observableArrayList(comments));
    }
        
    private ArtistResponseBody getArtistInfoWithValidation(String name) {
        try {
            ArtistResponseBody artistResponse = ArtistRepository.getInstance().findOneByName(name);
            if (Objects.isNull(artistResponse) || Objects.isNull(artistResponse.getTags()) || Objects.isNull(artistResponse.getTags().getTags()) || artistResponse.getTags().getTags().isEmpty()) {
                App.logger.info("L'artiste n'a pas été trouvé dans la base, recherche dans l'API");
                ArtistResponse res = this.getArtistInfo(name);
                if (Objects.nonNull(res)) {
                    artistResponse = res.getArtistResponseBody();
                }
            } else if (artistResponse.hasPartialData() || Objects.isNull(artistResponse.getTags().getTags()) || artistResponse.getTags().getTags().isEmpty()) {
                App.logger.info("L'artiste a été trouvé mais ses informations sont partielles, recherche dans l'API");
                ArtistResponse res = this.getArtistInfo(name);
                if (Objects.nonNull(res)) {
                    artistResponse = res.getArtistResponseBody();
                }
            } else {
                App.logger.info("Les informations sur l'artiste 1 sont complètes dans la base de donnée, pas de recherche dans l'API");
            }
            return artistResponse;
        } catch (IOException e) {
            App.logger.error("Impossible de récupérer des informations sur l'artiste", e);
            return null;
        }
    }

    private AlbumResponseBody getAlbumInfoWithValidation(String name, String artist) {
        try {
            AlbumResponseBody albumResponse = AlbumRepository.getInstance().findOneByNameAndArtist(name, artist);
            if (Objects.isNull(albumResponse)) {
                App.logger.info("L'album n'a pas été trouvé dans la base, recherche dans l'API");
                AlbumResponse res = this.getAlbumInfo(name, artist);
                if (Objects.nonNull(res)) {
                    albumResponse = res.getAlbumResponseBody();
                }
            } else if (albumResponse.hasPartialData()) {
                App.logger.info("L'album a été trouvé mais ses informations sont partielles, recherche dans l'API");
                AlbumResponse res = this.getAlbumInfo(name, artist);
                if (Objects.nonNull(res)) {
                    albumResponse = res.getAlbumResponseBody();
                }                
            } else {
                App.logger.info("Les informations sur l'album 1 sont complètes dans la base de donnée, pas de recherche dans l'API");
            }
            return albumResponse;
        } catch (IOException e) {
            App.logger.error("Impossible de récupérer des informations sur l'album", e);
            return null;
        }
    }

    private TagResponseBody getTagInfoWithValidation(String name) {
        try {
            TagResponseBody tagResponse = TagRepository.getInstance().findOne(name);
            if (Objects.isNull(tagResponse)) {
                App.logger.info("Le tag n'a pas été trouvé dans la base, recherche dans l'API");
                TagResponse res = this.getTagInfo(name);
                if (Objects.nonNull(res)) {
                    tagResponse = res.getTag();
                }
            } else if (tagResponse.hasPartialData()) {
                App.logger.info("Le tag a été trouvé mais ses informations sont partielles, recherche dans l'API");
                TagResponse res = this.getTagInfo(name);
                if (Objects.nonNull(res)) {
                    tagResponse = res.getTag();
                }
            } else {
                App.logger.info("Les informations sur le tag 1 sont complètes dans la base de donnée, pas de recherche dans l'API");
            }
            return tagResponse;
        } catch (IOException e) {
            App.logger.error("Impossible de récupérer des informations sur le tag", e);
            return null;
        }
    }

    private TrackResponseBody getTrackInfoWithValidation(String name, String artist) {
        try {
            TrackResponseBody trackResponse = TrackRepository.getInstance().findOneByNameAndArtist(name, artist);
            if (Objects.isNull(trackResponse)) {
                App.logger.info("La track n'a pas été trouvé dans la base, recherche dans l'API");
                TrackResponse res = this.getTrackInfo(name, artist);
                if (Objects.nonNull(res)) {
                    trackResponse = res.getTrackResponseBody();
                }
            } else if (trackResponse.hasPartialData()) {
                App.logger.info("La track a été trouvé mais ses informations sont partielles, recherche dans l'API");
                TrackResponse res = this.getTrackInfo(name, artist);
                if (Objects.nonNull(res)) {
                    trackResponse = res.getTrackResponseBody();
                }                
            } else {
                App.logger.info("Les informations sur la track 1 sont complètes dans la base de donnée, pas de recherche dans l'API");
            }
            return trackResponse;
        } catch (IOException e) {
            App.logger.error("Impossible de récupérer des informations sur la track", e);
            return null;
        }
    }

    private ArtistResponse getArtistInfo(String name) throws IOException {
        try {
            ArtistResponse res = App.objectMapper.readValue(HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=%s", name), true, false), ArtistResponse.class);
            if (Objects.nonNull(res)) {
                ArtistRepository.getInstance().createOrUpdate(res.getArtistResponseBody());
            }
            return res;
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    private AlbumResponse getAlbumInfo(String album, String artist) throws IOException {
        try {
            String response = HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&artist=%s&album=%s", artist, album), true, false);
            AlbumResponse res = App.objectMapper.readValue(response, AlbumResponse.class);
            if (Objects.nonNull(res)) {
                AlbumRepository.getInstance().createOrUpdate(res.getAlbumResponseBody());
            }
            return res;
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    private TagResponse getTagInfo(String name) throws IOException {
        try {
            String response = HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=%s", name), true, false);
            TagResponse tag = App.objectMapper.readValue(response, TagResponse.class);
            if (Objects.nonNull(tag)) {
                TagRepository.getInstance().createOrUpdate(tag.getTag());
            }
            return tag;
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    private TrackResponse getTrackInfo(String name, String artist) throws IOException {
        try {
            String response = HTTPTools.sendGet(String.format("http://ws.audioscrobbler.com/2.0/?method=track.getInfo&track=%s&artist=%s", name, artist), true, false);
            TrackResponse trackRepsonse = App.objectMapper.readValue(response,TrackResponse.class);    
            if (Objects.nonNull(trackRepsonse)) {
                TrackRepository.getInstance().createOrUpdate(trackRepsonse.getTrackResponseBody());
            }
            return trackRepsonse;
        } catch (IOException e) {
            throw new IOException(MAP_ERROR, e);
        }
    }

    private void checkMbid(String mbid) {
        if (Objects.nonNull(mbid) && !mbid.isEmpty()) {
            this.selectedMbid = mbid;
            this.btnPoster.setDisable(false);
        } else {
            this.btnPoster.setDisable(true);
        }
    } 

    // ========
    // ======== Fonction d'initialisation
    // ========

    private void setupTabClassement() {
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNbEcoute.setCellValueFactory(new PropertyValueFactory<>("listeners"));
        this.colEvolution.setCellValueFactory(new PropertyValueFactory<>("evolution"));
        this.colMbid.setCellValueFactory(new PropertyValueFactory<>("mbid"));
        this.colUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
        this.colNameT.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNbEcouteT.setCellValueFactory(new PropertyValueFactory<>("listeners"));
        this.colEvolutionT.setCellValueFactory(new PropertyValueFactory<>("evolution"));
        this.colMbidT.setCellValueFactory(new PropertyValueFactory<>("mbid"));
        this.colUrlT.setCellValueFactory(new PropertyValueFactory<>("url"));
        this.colSimilarAlbumsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colSimilarAlbumsMbid.setCellValueFactory(new PropertyValueFactory<>("mbid"));
        this.colSimilarAlbumsTags.setCellValueFactory(data -> {
            String res = "";
            if (Objects.nonNull(data.getValue().getToptags())) {
                for (TagResponseBody tag : data.getValue().getToptags().getTags()) {
                    res += String.format("%s,", tag.getName());
                }
            }
            return new SimpleStringProperty(res);
        });
        this.colSimilarAlbumsArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        this.colSimilarTrackAlbum.setCellValueFactory(data -> {
            String res = "n/A";
            if (Objects.nonNull(data.getValue().getAlbum())) {
                res = data.getValue().getAlbum().getName();
            }
            return new SimpleStringProperty(res);
        }); 
        this.colSimilarTrackArtist.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArtist().getName()));
        this.colSimilarTrackMbid.setCellValueFactory(new PropertyValueFactory<>("mbid"));
        this.colSimilarTrackName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colSimilarTrackTags.setCellValueFactory(data -> {
            String res = "";
            if (Objects.nonNull(data.getValue().getToptags())) {
                for (TagResponseBody tag : data.getValue().getToptags().getTags()) {
                    res += String.format("%s,", tag.getName());
                }
            }
            return new SimpleStringProperty(res);
        });
        this.colNameComment.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.colComments.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }

    private void setupSelects() {
        this.selectType.setItems(selectTypeFields);
        this.selectType.getSelectionModel().select(MUSIQUE);
        this.manageSelectionChange(MUSIQUE);
        this.selectType.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> manageSelectionChange(z));
        
        this.selectCountry.setItems(App.pays);
        this.selectCountry.getSelectionModel().select("France");
        this.selectCountry.getSelectionModel().selectedItemProperty().addListener((x, y, z)  -> getClassement());
        
        this.selectClassement.setItems(this.selectClassementFields);
        this.selectClassement.getSelectionModel().select(ARTISTE);
        this.selectClassement.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> getClassement());
    }

    private void manageSelectionChange(String value) {
        switch (value) {
            case MUSIQUE : 
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(false);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(true);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(false);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(true);
                if (Objects.nonNull(App.getSession()) && Objects.nonNull(App.getSession().getKey())) {
                    this.paneCommentaire.setVisible(true);
                }
                break;
            case ARTISTE :
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(false);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(false);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(false);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(false);
                if (Objects.nonNull(App.getSession()) && Objects.nonNull(App.getSession().getKey())) {
                    this.paneCommentaire.setVisible(true);
                }
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
                this.paneCommentaire.setVisible(false);
                break;
            case ALBUM : 
                this.txtTag.setVisible(false);
                this.txtAlbum.setVisible(true);
                this.txtArtiste.setVisible(true);
                this.txtTrack.setVisible(false);
                this.labTag.setVisible(false);
                this.labAlbum.setVisible(true);
                this.labArtiste.setVisible(true);
                this.labTrack.setVisible(false);
                if (Objects.nonNull(App.getSession()) && Objects.nonNull(App.getSession().getKey())) {
                    this.paneCommentaire.setVisible(true);
                }
                break;
            default:
        }
    }
}
