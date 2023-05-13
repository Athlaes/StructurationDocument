package fr.ul.miage.sd.metier;

import java.util.List;

public class Artist {
    private String name;
    private String mbid;
    private Stats stats;    
    private List<String> similarIds;
    private List<String> tags;
    private Wiki bio;

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public List<String> getSimilarIds() {
        return similarIds;
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

    public void setSimilarIds(List<String> similarIds) {
        this.similarIds = similarIds;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
