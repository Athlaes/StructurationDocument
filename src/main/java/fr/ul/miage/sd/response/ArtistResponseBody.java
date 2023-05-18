package fr.ul.miage.sd.response;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ul.miage.sd.metier.SimilarArtist;
import fr.ul.miage.sd.metier.Stats;
import fr.ul.miage.sd.metier.Wiki;

public class ArtistResponseBody {
    private String name;
    private String mbid;
    private Stats stats;    
    private SimilarArtist similar;
    @JsonProperty("tags")
    @JsonAlias("toptags")
    private TagsResponse tags; 
    private Wiki bio;
    private String url;
    private String evolution = "=";

    public ArtistResponseBody() {}

    public ArtistResponseBody(GeoArtistResponse geoArtistResponse){
        this.name = geoArtistResponse.getName();
        this.mbid = geoArtistResponse.getMbid();
        this.evolution = geoArtistResponse.getEvolution();
        this.url = geoArtistResponse.getUrl();
        this.evolution = geoArtistResponse.getEvolution();
        this.stats = new Stats();
        stats.setListeners(geoArtistResponse.getListeners());
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(Wiki bio) {
        this.bio = bio;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Wiki getBio() {
        return bio;
    }

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public TagsResponse getTags() {
        return tags;
    }

    public void setTags(TagsResponse tags) {
        this.tags = tags;
    }

    public void setSimilar(SimilarArtist similar) {
        this.similar = similar;
    }

    public SimilarArtist getSimilar() {
        return similar;
    }
}
