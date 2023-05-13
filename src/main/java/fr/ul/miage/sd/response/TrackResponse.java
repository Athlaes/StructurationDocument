package fr.ul.miage.sd.response;

import java.util.List;

import fr.ul.miage.sd.metier.Wiki;

public class TrackResponse {
    private String name;
    private String mbid;
    private int duration;
    private int listeners;
    private int playcount;
    private ArtistResponse artist;
    private String albumId;
    private String albumPos;
    private List<TagResponse> toptags; 
    private Wiki wiki;

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setAlbumPos(String albumPos) {
        this.albumPos = albumPos;
    }

    public void setArtist(ArtistResponse artist) {
        this.artist = artist;
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

    public void setTags(List<TagResponse> tags) {
        this.toptags = tags;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumPos() {
        return albumPos;
    }

    public ArtistResponse getArtist() {
        return artist;
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

    public List<TagResponse> getTags() {
        return toptags;
    }

    public Wiki getWiki() {
        return wiki;
    }
}
