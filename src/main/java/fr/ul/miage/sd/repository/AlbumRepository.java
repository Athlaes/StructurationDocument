package fr.ul.miage.sd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.MongoClientException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;

import fr.ul.miage.sd.App;
import fr.ul.miage.sd.metier.Album;
import fr.ul.miage.sd.response.AlbumResponseBody;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.response.TagsResponse;
import fr.ul.miage.sd.response.TrackResponseBody;
import fr.ul.miage.sd.service.MongoService;

public class AlbumRepository {
    private static final String ERROR_PROCESS = "Erreur dans le processing de l'objet";
    private static final String ERROR_MAPPING = "Erreur dans le mapping de l'objet";
    private static AlbumRepository repository = null;
    private MongoCollection<Document> collection;

    public AlbumRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"albums");
        this.collection.createIndex(Indexes.ascending("mbid"));
    }

    public static AlbumRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new AlbumRepository();
        }
        return repository;
    }

    public AlbumResponseBody findOne(String mbid) {
        FindIterable<Document> findIterable = this.collection.find(new Document("mbid", mbid));
        Document document = findIterable.first();
        if(Objects.nonNull(document)){
            return this.parseToAlbumResponse(document);
        }
        return null;
    }

    public List<AlbumResponseBody> findAllByTags(String searchedTag) {
        List<AlbumResponseBody> responseList = new ArrayList<>();
        FindIterable<Document> findIterable = this.collection.find(new Document("toptagsNames", searchedTag));
        for (Document document : findIterable) {
            if(Objects.nonNull(document)){
                AlbumResponseBody albumResponse = this.parseToAlbumResponse(document);
                responseList.add(albumResponse);
            }
        }
        return responseList;
    }

    private AlbumResponseBody parseToAlbumResponse(Document document) {
        try {
            Album album = App.objectMapper.readValue(document.toJson(), Album.class);
            AlbumResponseBody albumResponse= App.objectMapper.readValue(document.toJson(), AlbumResponseBody.class);

            if (Objects.nonNull(album.getToptagsNames())) {
                List<TagResponse> tagList = new ArrayList<>();
                for (String tagNames : album.getToptagsNames()) {
                    tagList.add(TagRepository.getInstance().findOne(tagNames));
                }
                albumResponse.setToptags(new TagsResponse(tagList));
            }

            if (Objects.nonNull(album.getTracksIds())) {
                List<TrackResponseBody> trackList = new ArrayList<>();
                for (String mbidTrack : album.getTracksIds()) {
                    trackList.add(TrackRepository.getInstance().findOne(mbidTrack));
                }
                albumResponse.setTracks(trackList);
            }
            
            return albumResponse;
        }catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESS, e);
        }
    }

    public String createOrUpdate(AlbumResponseBody albumResponse) {
        try {
            Album album = App.objectMapper.readValue(App.objectMapper.writeValueAsString(albumResponse), Album.class);

            if (Objects.nonNull(albumResponse.getToptags())) {
                List<String> tagList = new ArrayList<>();
                for (TagResponse tag : albumResponse.getToptags().getTags()) {
                    String name = TagRepository.getInstance().createOrUpdate(tag);
                    tagList.add(name);
                }
                album.setToptagsNames(tagList);
            }

            if (Objects.nonNull(albumResponse.getTracks())) {
                List<String> trackList = new ArrayList<>();
                for (TrackResponseBody track : albumResponse.getTracks()) {
                    String mbid = TrackRepository.getInstance().createOrUpdate(track);
                    trackList.add(mbid);
                }
                album.setTracksIds(trackList);
            }

            Document albumDocument = Document.parse(App.objectMapper.writeValueAsString(album));
            this.collection.updateOne(new Document("mbid", albumResponse.getMbid()), new Document("$set", albumDocument), new UpdateOptions().upsert(true));

            return album.getMbid();
        } catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESS, e);
        }
    }
}
