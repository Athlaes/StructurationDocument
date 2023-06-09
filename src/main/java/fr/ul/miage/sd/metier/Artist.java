package fr.ul.miage.sd.metier;

import java.util.List;

import fr.ul.miage.sd.response.GeoArtistResponse;

public class Artist {
    private String name;
    private String mbid;
    private Stats stats;    
    private SimilarArtist similar;
    private List<String> tagsNames;
    private Wiki bio;
    private String url;
    private String evolution = "=";

    public Artist(){}

    public Artist(GeoArtistResponse geoArtistResponse){
        this.name = geoArtistResponse.getName();
        this.mbid = geoArtistResponse.getMbid();
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

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Wiki getBio() {
        return bio;
    }

    public void setBio(Wiki bio) {
        this.bio = bio;
    }

    public List<String> getTagsNames() {
        return tagsNames;
    }

    public void setTagsNames(List<String> tagsNames) {
        this.tagsNames = tagsNames;
    }

    public SimilarArtist getSimilar() {
        return similar;
    }

    public void setSimilar(SimilarArtist similar) {
        this.similar = similar;
    }
}
