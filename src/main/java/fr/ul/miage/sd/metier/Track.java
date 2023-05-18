package fr.ul.miage.sd.metier;

import java.util.List;
public class Track {
    private String name;
    private String mbid;
    private int duration;
    private int listeners;
    private int playcount;
    private String artistMbid;
    private String albumMbid;
    private List<String> tagsNames; 
    private Wiki wiki;
    private String url;
    private String evolution = "=";

    public String getEvolution() {
        return evolution;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public void setTagsNames(List<String> tagsNames) {
        this.tagsNames = tagsNames;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public int getDuration() {
        return duration;
    }

    public int getListeners() {
        return listeners;
    }

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public int getPlaycount() {
        return playcount;
    }

    public List<String> getTagsNames() {
        return tagsNames;
    }

    public Wiki getWiki() {
        return wiki;
    }

    public String getAlbumMbid() {
        return albumMbid;
    }

    public void setAlbumMbid(String albumMbid) {
        this.albumMbid = albumMbid;
    }
}
