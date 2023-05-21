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
import fr.ul.miage.sd.response.ArtistResponseBody;
import fr.ul.miage.sd.response.TagResponseBody;
import fr.ul.miage.sd.response.TagsResponse;
import fr.ul.miage.sd.service.MongoService;

public class ArtistRepository {
    private static final String ERROR_PROCESSING = "Erreur dans le processing de l'objet";
    private static final String ERROR_MAPPING = "Erreur dans le mapping de l'objet";
    private static ArtistRepository repository = null;
    private MongoCollection<Document> collection;

    private ArtistRepository() {
        this.collection = MongoService.getInstance().getCollectionInDatabase(App.COL_BEGINNING+"artists");
        this.collection.createIndex(Indexes.ascending("mbid"));
    }

    public static ArtistRepository getInstance() {
        if(Objects.isNull(repository)) {
            repository = new ArtistRepository();
        }
        return repository;
    }

    public ArtistResponseBody findOne(String mbid) {
        FindIterable<Document> findIterable = this.collection.find(new Document("mbid", mbid));
        Document document = findIterable.first();
        if(Objects.nonNull(document)){
            return this.parseToArtistResponse(document);
        }
        return null;
    }

    public ArtistResponseBody findOneByName(String name) {
        FindIterable<Document> findIterable = this.collection.find(new Document("name", new Document("$regex", "(?i)"+name)));
        Document document = findIterable.first();
        if(Objects.nonNull(document)){
            return this.parseToArtistResponse(document);
        }
        return null;
    }

    private ArtistResponseBody parseToArtistResponse(Document document) {
        try {
            Artist artist = App.objectMapper.readValue(document.toJson(), Artist.class);
            ArtistResponseBody artistResponse= App.objectMapper.readValue(document.toJson(), ArtistResponseBody.class);

            if (Objects.nonNull(artist.getTagsNames())) {
                List<TagResponseBody> tagList = new ArrayList<>();
                for (String tagName : artist.getTagsNames()) {
                    tagList.add(TagRepository.getInstance().findOne(tagName));  
                }
                artistResponse.setTags(new TagsResponse(tagList));
            }
            return artistResponse;
        }catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESSING, e);
        }
    }

    public String createOrUpdate(ArtistResponseBody artistResponse) {
        try {
            Artist artist = App.objectMapper.readValue(App.objectMapper.writeValueAsString(artistResponse), Artist.class);

            if (Objects.nonNull(artistResponse.getTags())) {
                List<String> tagList = new ArrayList<>();
                for (TagResponseBody tag : artistResponse.getTags().getTags()) {
                    String name = TagRepository.getInstance().createOrUpdate(tag);
                    tagList.add(name);
                }
                artist.setTagsNames(tagList);
            }
            
            ArtistResponseBody foundedArtist = this.findOne(artistResponse.getMbid());
            if (Objects.nonNull(foundedArtist) && Objects.nonNull(foundedArtist.getStats()) && Objects.nonNull(artist.getStats())) {
                if (foundedArtist.getStats().getListeners() < artist.getStats().getListeners()) {
                    artist.setEvolution("+");
                } else if (foundedArtist.getStats().getListeners() > artist.getStats().getListeners()){
                    artist.setEvolution("-");
                } else {
                    artist.setEvolution("=");
                }
            }
            
            Document artistDocument = Document.parse(App.objectMapper.writeValueAsString(artist));
            this.collection.updateOne(new Document("mbid", artistResponse.getMbid()), new Document("$set", artistDocument), new UpdateOptions().upsert(true));

            return artist.getMbid();
        } catch (JsonMappingException e) {
            throw new MongoClientException(ERROR_MAPPING, e);
        } catch (JsonProcessingException e) {
            throw new MongoClientException(ERROR_PROCESSING, e);
        }
    }
}
