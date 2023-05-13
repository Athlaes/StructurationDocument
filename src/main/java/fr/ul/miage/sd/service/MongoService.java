package fr.ul.miage.sd.service;

import java.util.Objects;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoService {
    private static MongoService instance = null;

    private MongoDatabase database;

    public MongoService() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("SD2023_projet");
    }

    public static synchronized MongoService getInstance(){
        if(Objects.isNull(instance)){
            instance = new MongoService();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getCollectionInDatabase(String collectionName){
        return this.database.getCollection(collectionName);
    }
}
