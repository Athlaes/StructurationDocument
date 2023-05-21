package fr.ul.miage.sd.response;


import java.util.Objects;

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

    public boolean hasPartialData() {
        boolean partial = false;
        if (Objects.isNull(this.evolution) ||
            Objects.isNull(this.mbid) ||
            Objects.isNull(this.name) ||
            Objects.isNull(this.url) ||
            Objects.isNull(this.bio) ||
            Objects.isNull(this.similar) ||
            Objects.isNull(this.tags) ||
            Objects.isNull(this.stats)) {
            partial = true;
        }
        return partial;
    }

    public String toString() {
        String res = String.format("Nom : %s%nMbid : %s%nUrl : %s%n", this.name, this.mbid, this.url);
        if (Objects.nonNull(this.bio)) {
            res += String.format("Description : %s%nApparue le : %s%n", bio.getSummary(), bio.getPublished());
        }
        if (Objects.nonNull(this.stats)) {
            res += String.format("Nombre d'écoute : %s%n", this.stats.getListeners());
        }
        return res;
    }
}
