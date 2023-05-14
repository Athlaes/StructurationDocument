package fr.ul.miage.sd.repository;

import java.util.Objects;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.MongoClientException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;

import fr.ul.miage.sd.App;
import fr.ul.miage.sd.response.TagResponse;
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

    public TagResponse findOne(String country) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("country", country));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                return App.objectMapper.readValue(document.toJson(), TagResponse.class);
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }

    public String createOrUpdate(TagResponse tagResponse) {
        try {
            Document tagDocument = Document.parse(App.objectMapper.writeValueAsString(tagResponse));
            this.collection.updateOne(new Document("name", tagResponse.getName()), new Document("$set", tagDocument), new UpdateOptions().upsert(true));
            return tagResponse.getName();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
