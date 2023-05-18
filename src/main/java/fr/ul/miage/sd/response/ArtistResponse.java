package fr.ul.miage.sd.response;

import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.sd.metier.Stats;
import fr.ul.miage.sd.metier.Wiki;

public class ArtistResponse {
    private String name;
    private String mbid;
    private Stats stats;    
    private List<ArtistResponse> similar;
    private List<TagResponse> tags;
    private Wiki bio;
    private String url;
    private String evolution = "=";

    public ArtistResponse() {}

    public ArtistResponse(GeoArtistResponse geoArtistResponse){
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

    public void setSimilar(List<ArtistResponse> similar) {
        this.similar = similar;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
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

    public List<ArtistResponse> getSimilar() {
        return similar;
    }

    public Stats getStats() {
        return stats;
    }

    public List<TagResponse> getTags() {
        return tags;
    }
}
