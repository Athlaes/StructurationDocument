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
import fr.ul.miage.sd.metier.Artist;
import fr.ul.miage.sd.response.ArtistResponse;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.service.MongoService;

public class ArtistRepository {
    private static ArtistRepository repository = null;
    private MongoCollection<Document> collection;

    public ArtistRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"artists");
        this.collection.createIndex(Indexes.ascending("mbid"));
    }

    public static ArtistRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new ArtistRepository();
        }
        return repository;
    }

    public ArtistResponse findOne(String mbid) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("mbid", mbid));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                Artist artist = App.objectMapper.readValue(document.toJson(), Artist.class);
                ArtistResponse artistResponse= App.objectMapper.readValue(document.toJson(), ArtistResponse.class);

                List<ArtistResponse> artistList = new ArrayList<>();
                for (String mbidSimilar : artist.getSimilarIds()) {
                    artistList.add(this.findOne(mbidSimilar));
                }
                artistResponse.setSimilar(artistList);
                return artistResponse;
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }

    public String createOrUpdate(ArtistResponse artistResponse) throws MongoClientException {
        try {
            Artist artist = App.objectMapper.readValue(App.objectMapper.writeValueAsString(artistResponse), Artist.class);

            if (Objects.nonNull(artistResponse.getSimilar())) {
                List<String> similarList = new ArrayList<>();
                for (ArtistResponse similarArtist : artistResponse.getSimilar()) {
                    String mbid = this.createOrUpdate(similarArtist);
                    similarList.add(mbid);
                }
                artist.setSimilarIds(similarList);
            }

            if (Objects.nonNull(artistResponse.getTags())) {
                List<String> tagList = new ArrayList<>();
                for (TagResponse tag : artistResponse.getTags()) {
                    String name = TagRepository.getInstance().createOrUpdate(tag);
                    tagList.add(name);
                }
                artist.setTags(tagList);
            }
            
            ArtistResponse foundedArtist = this.findOne(artistResponse.getMbid());
            if (Objects.nonNull(foundedArtist)) {
                if (foundedArtist.getStats().getListeners() < artist.getStats().getListeners()) {
                    artist.setEvolution("+");
                } else if (foundedArtist.getStats().getListeners() > artist.getStats().getListeners()){
                    artist.setEvolution("-");
                } else {
                    artist.setEvolution("=");
                }
            }
            
            Document artistDocument = Document.parse(App.objectMapper.writeValueAsString(artist));
            App.logger.info(artistDocument.toJson());
            this.collection.updateOne(new Document("mbid", artistResponse.getMbid()), new Document("$set", artistDocument), new UpdateOptions().upsert(true));

            return artist.getMbid();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
