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
import fr.ul.miage.sd.metier.TopArtists;
import fr.ul.miage.sd.response.ArtistResponse;
import fr.ul.miage.sd.response.GeoArtistResponse;
import fr.ul.miage.sd.response.TopArtistsResponse;
import fr.ul.miage.sd.service.MongoService;

public class TopArtistRepository {
    private static TopArtistRepository repository = null;
    private MongoCollection<Document> collection;

    public TopArtistRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"geotopartist");
    }

    public static TopArtistRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new TopArtistRepository();
        }
        return repository;
    }

    public TopArtistsResponse findOne(String country) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("country", country));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                TopArtists topArtists = App.objectMapper.readValue(document.toJson(), TopArtists.class);
                TopArtistsResponse topArtistsResponse = new TopArtistsResponse();
                if (Objects.nonNull(topArtists.getArtistsMbid())) {
                    List<GeoArtistResponse> geoArtists = new ArrayList<>();
                    for (String artistsMbid : topArtists.getArtistsMbid()) {
                        ArtistResponse artist = ArtistRepository.getInstance().findOne(artistsMbid);
                        GeoArtistResponse geoArtiste = new GeoArtistResponse(artist);
                        geoArtists.add(geoArtiste);
                    }
                    topArtistsResponse.setArtist(geoArtists);
                }
                return App.objectMapper.readValue(document.toJson(), TopArtistsResponse.class);
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }

    public String createOrUpdate(TopArtistsResponse artistsResponse) {
        try {
            TopArtists topArtists = new TopArtists();
            topArtists.setCountry(artistsResponse.getAttributes().get("country"));

            if (Objects.nonNull(artistsResponse.getArtist())) {
                List<String> artistIds = new ArrayList<>();
                for (GeoArtistResponse res : artistsResponse.getArtist()) {
                    ArtistResponse artist = new ArtistResponse(res);
                    String artisteId = ArtistRepository.getInstance().createOrUpdate(artist);
                    artistIds.add(artisteId);
                }
                topArtists.setArtistsMbid(artistIds);
            }

            Document topArtistDocument = Document.parse(App.objectMapper.writeValueAsString(topArtists));
            this.collection.updateOne(new Document("country", topArtists.getCountry()), new Document("$set", topArtistDocument), new UpdateOptions().upsert(true));
            
            return topArtists.getCountry();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
