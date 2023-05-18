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
import fr.ul.miage.sd.response.TrackResponse;
import fr.ul.miage.sd.service.MongoService;

public class TrackRepository {
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

    public TrackResponse findOne(String mbid) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("mbid", mbid));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                Track track = App.objectMapper.readValue(document.toJson(), Track.class);
                TrackResponse trackResponse= App.objectMapper.readValue(document.toJson(), TrackResponse.class);

                if (Objects.nonNull(track.getTagsNames())) {
                    List<TagResponse> tagList = new ArrayList<>();
                    for (String tagName : track.getTagsNames()) {
                        tagList.add(TagRepository.getInstance().findOne(tagName));
                    }
                    trackResponse.setTags(tagList);
                }
   
                if (Objects.nonNull(track.getArtistMbid())) {
                    trackResponse.setArtist(ArtistRepository.getInstance().findOne(track.getArtistMbid()));
                }

                return trackResponse;
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }

    public String createOrUpdate(TrackResponse trackResponse) {
        try {
            Track track = App.objectMapper.readValue(App.objectMapper.writeValueAsString(trackResponse), Track.class);

            if (Objects.nonNull(trackResponse.getToptags())) {
                List<String> tagList = new ArrayList<>();
                for (TagResponse tag : trackResponse.getToptags()) {
                    String name = TagRepository.getInstance().createOrUpdate(tag);
                    tagList.add(name);
                }
                track.setTagsNames(tagList);
            }

            TrackResponse foundedTrack = this.findOne(trackResponse.getMbid());
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
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
