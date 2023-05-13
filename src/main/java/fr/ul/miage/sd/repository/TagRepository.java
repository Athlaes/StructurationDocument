package fr.ul.miage.sd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.MongoClientException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import fr.ul.miage.sd.App;
import fr.ul.miage.sd.response.TagResponse;
import fr.ul.miage.sd.service.MongoService;

public class TagRepository {
    private static TagRepository repository = null;
    private MongoCollection<Document> collection;

    public TagRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"tags");
    }

    public static TagRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new TagRepository();
        }
        return repository;
    }

    public TagResponse findOne(String name) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("name", name));
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
            long nbTag = this.collection.countDocuments(new Document("name", tagResponse.getName()));
            if (nbTag > 0) {
                this.collection.findOneAndUpdate(new Document("name", tagResponse.getName()), tagDocument);
            } else {
                tagDocument.put("_id", new ObjectId());
                this.collection.insertOne(tagDocument);
            }
            return tagResponse.getName();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le procesisng de l'objet", e);
        }
    }
}
