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
import fr.ul.miage.sd.response.TagResponseBody;
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

    public TagResponseBody findOne(String name) {
        try {
            FindIterable<Document> findIterable = this.collection.find(new Document("name", new Document("$regex", "(?i)"+name)));
            Document document = findIterable.first();
            if(Objects.nonNull(document)){
                return App.objectMapper.readValue(document.toJson(), TagResponseBody.class);
            }
            return null;
        }catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le processing de l'objet", e);
        }
    }

    public String createOrUpdate(TagResponseBody tagResponse) {
        try {
            Document tagDocument = Document.parse(App.objectMapper.writeValueAsString(tagResponse));
            this.collection.updateOne(new Document("name", tagResponse.getName()), new Document("$set", tagDocument), new UpdateOptions().upsert(true));
            return tagResponse.getName();
        } catch (JsonMappingException e) {
            throw new MongoClientException("Erreur dans le mapping de l'objet", e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException("Erreur dans le processing de l'objet", e);
        }
    }
}
