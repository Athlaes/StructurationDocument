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
import fr.ul.miage.sd.metier.Track;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.response.TagsResponse;
import fr.ul.miage.sd.response.TrackResponseBody;
import fr.ul.miage.sd.service.MongoService;

public class TrackRepository {
    private static final String ERROR_PROCESS = "Erreur dans le processing de l'objet";
    private static final String ERROR_MAPPING = "Erreur dans le mapping de l'objet";
    private static TrackRepository repository = null;
    private MongoCollection<Document> collection;

    public TrackRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"tracks");
        this.collection.createIndex(Indexes.ascending("mbid"));
    }

    public static TrackRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new TrackRepository();
        }
        return repository;
    }

    public TrackResponseBody findOne(String mbid) {
        FindIterable<Document> findIterable = this.collection.find(new Document("mbid", mbid));
        Document document = findIterable.first();
        if(Objects.nonNull(document)){
            return this.parseToTrackResponse(document);
        }
        return null;
    }

    public List<TrackResponseBody> findAllByTags(String searchedTag) {
        List<TrackResponseBody> responseList = new ArrayList<>();
        FindIterable<Document> findIterable = this.collection.find(new Document("tagsNames", searchedTag));
        for (Document document : findIterable) {
            TrackResponseBody trackResponse = this.parseToTrackResponse(document);
            responseList.add(trackResponse);
        }
        return responseList;
    }

    private TrackResponseBody parseToTrackResponse(Document document) {
        try {
            Track track = App.objectMapper.readValue(document.toJson(), Track.class);
            TrackResponseBody trackResponse= App.objectMapper.readValue(document.toJson(), TrackResponseBody.class);

            if (Objects.nonNull(track.getTagsNames())) {
                List<TagResponse> tagList = new ArrayList<>();
                for (String tagName : track.getTagsNames()) {
                    tagList.add(TagRepository.getInstance().findOne(tagName));
                }
                trackResponse.setToptags(new TagsResponse(tagList));
            }

            if (Objects.nonNull(track.getArtistMbid())) {
                trackResponse.setArtist(ArtistRepository.getInstance().findOne(track.getArtistMbid()));
            }
            return trackResponse;
        }catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESS, e);
        }
    }

    public String createOrUpdate(TrackResponseBody trackResponse) {
        try {
            Track track = App.objectMapper.readValue(App.objectMapper.writeValueAsString(trackResponse), Track.class);

            if (Objects.nonNull(trackResponse.getToptags()) && Objects.nonNull(trackResponse.getToptags().getTags())) {
                List<String> tagList = new ArrayList<>();
                for (TagResponse tag : trackResponse.getToptags().getTags()) {
                    String name = TagRepository.getInstance().createOrUpdate(tag);
                    tagList.add(name);
                }
                track.setTagsNames(tagList);
            }

            if (Objects.nonNull(trackResponse.getAlbum())) {
                track.setAlbumMbid(AlbumRepository.getInstance().createOrUpdate(trackResponse.getAlbum()));
            }

            TrackResponseBody foundedTrack = this.findOne(trackResponse.getMbid());
            if (Objects.nonNull(foundedTrack)) {
                if (foundedTrack.getListeners() < track.getListeners()) {
                    track.setEvolution("+");
                } else if (foundedTrack.getListeners() > track.getListeners()){
                    track.setEvolution("-");
                } else {
                    track.setEvolution("=");
                }
            }

            if (Objects.nonNull(trackResponse.getArtist())) {
                track.setArtistMbid(ArtistRepository.getInstance().createOrUpdate(trackResponse.getArtist()));
            }
            
            Document trackDocument = Document.parse(App.objectMapper.writeValueAsString(track));
            this.collection.updateOne(new Document("mbid", trackResponse.getMbid()), new Document("$set", trackDocument), new UpdateOptions().upsert(true));

            return track.getMbid();
        } catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESS, e);
        }
    }
}
