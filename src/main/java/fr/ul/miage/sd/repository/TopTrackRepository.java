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
import com.mongodb.client.model.UpdateOptions;

import fr.ul.miage.sd.App;
import fr.ul.miage.sd.metier.TopTrack;
import fr.ul.miage.sd.response.GeoTrackResponse;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.response.TopTracksResponse;
import fr.ul.miage.sd.response.TrackResponse;
import fr.ul.miage.sd.service.MongoService;

public class TopTrackRepository {
    private static TopTrackRepository repository = null;
    private MongoCollection<Document> collection;

    public TopTrackRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"geotoptracks");
    }

    public static TopTrackRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new TopTrackRepository();
        }
        return repository;
    }

    public TopTracksResponse findOne(String country) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("country", country));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                TopTrack topTrack = App.objectMapper.readValue(document.toJson(), TopTrack.class);
                TopTracksResponse topTracksResponse = new TopTracksResponse();
                if (Objects.nonNull(topTrack.getTracksMbids())) {
                    List<GeoTrackResponse> tracks = new ArrayList<>();
                    for (String mbid : topTrack.getTracksMbids()) {
                        TrackResponse trackResponse = TrackRepository.getInstance().findOne(mbid);
                        GeoTrackResponse geoTrackResponse = new GeoTrackResponse(trackResponse);
                        tracks.add(geoTrackResponse);
                    }
                    topTracksResponse.setTrack(tracks);
                }
                return topTracksResponse;
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }

    public String createOrUpdate(TopTracksResponse tracksResponse) {
        try {
            TopTrack topTracks = new TopTrack();
            topTracks.setCountry(tracksResponse.getAttributes().get("country"));

            if (Objects.nonNull(tracksResponse.getTrack())) {
                List<String> trackMbids = new ArrayList<>();
                for (GeoTrackResponse res : tracksResponse.getTrack()) {
                    TrackResponse track = new TrackResponse(res);
                    String trackMbid = TrackRepository.getInstance().createOrUpdate(track);
                    trackMbids.add(trackMbid);
                }
                topTracks.setTracksMbids(trackMbids);
            }

            Document topTracksDocument = Document.parse(App.objectMapper.writeValueAsString(topTracks));
            this.collection.updateOne(new Document("country", topTracks.getCountry()), new Document("$set", topTracksDocument), new UpdateOptions().upsert(true));
            
            return topTracks.getCountry();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
