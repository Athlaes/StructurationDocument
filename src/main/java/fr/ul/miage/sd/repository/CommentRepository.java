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

import fr.ul.miage.sd.App;
import fr.ul.miage.sd.metier.Comment;
import fr.ul.miage.sd.service.MongoService;

public class CommentRepository {
    private static final String ERROR_PROCESSING = "Erreur dans le processing de l'objet";
    private static final String ERROR_MAPPING = "Erreur dans le mapping de l'objet";

    private static CommentRepository repository = null;
    private MongoCollection<Document> collection;

    private CommentRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"comments");
    }

    public static CommentRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new CommentRepository();
        }
        return repository;
    }

    public List<Comment> findAllComments(String mbid) {
        try {
            List<Comment> comments = new ArrayList<>();
            FindIterable<Document> iterable = this.collection.find(new Document("mbid", mbid));
            for (Document document : iterable) {
                Comment comment = App.objectMapper.readValue(document.toJson(), Comment.class);
                comments.add(comment);
            }
            return comments;
        } catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESSING, e);
        }
    }

    public void createOrUpdate(Comment comment) {
        try {
            this.collection.insertOne(Document.parse(App.objectMapper.writeValueAsString(comment)));
        } catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESSING, e);
        }
    }
}
