package fr.ul.miage.sd.response;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ul.miage.sd.metier.Wiki;

public class TrackResponseBody {
    private String name;
    private String mbid;
    private int duration;
    private int listeners;
    private int playcount;
    private ArtistResponseBody artist;
    private AlbumResponseBody album;
    @JsonProperty("tags")
    @JsonAlias("toptags")
    private TagsResponse toptags; 
    private Wiki wiki;
    private String url;
    private String evolution = "=";

    public TrackResponseBody () {}

    public TrackResponseBody(GeoTrackResponse geoTrackResponse) {
        this.name = geoTrackResponse.getName();
        this.url = geoTrackResponse.getUrl();
        this.evolution = geoTrackResponse.getEvolution();
        this.listeners = geoTrackResponse.getListeners();
        this.mbid = geoTrackResponse.getMbid();
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

    public String getEvolution() {
        return evolution;
    }

    public void setArtist(ArtistResponseBody artist) {
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

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public ArtistResponseBody getArtist() {
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

    public Wiki getWiki() {
        return wiki;
    }

    public AlbumResponseBody getAlbum() {
        return album;
    }
    
    public void setAlbum(AlbumResponseBody album) {
        this.album = album;
    }

    public TagsResponse getToptags() {
        return toptags;
    }

    public void setToptags(TagsResponse toptags) {
        this.toptags = toptags;
    }

    public boolean hasPartialData() {
        boolean partial = false;
        if (Objects.isNull(this.getEvolution()) ||
            Objects.isNull(this.getMbid()) ||
            Objects.isNull(this.getName()) ||
            Objects.isNull(this.getUrl()) ||
            Objects.isNull(this.getAlbum()) ||
            Objects.isNull(this.getArtist()) ||
            Objects.isNull(this.getDuration()) ||
            Objects.isNull(this.getListeners()) ||
            Objects.isNull(this.getPlaycount()) ||
            Objects.isNull(this.getToptags()) ||
            Objects.isNull(this.getWiki())) {
            partial = true;
        }
        return partial;
    }

    public String toString() {
        String res = String.format("Nom : %s%nMbid : %s%nNombre d'écoutes : %s%nDurée : %s (s)%nUrl : %s%n", this.name, this.mbid, this.listeners, this.duration, this.url);
        if (Objects.nonNull(this.artist)) {
            res += String.format("Artiste : %s%n", this.artist.getName());
        }
        if (Objects.nonNull(this.album)) {
            res += String.format("Album : %s%n", this.album.getName());
        }
        if (Objects.nonNull(this.toptags)) {
            res += String.format("Tags : %s%n", toptags.toString());
        }
        if (Objects.nonNull(wiki)) {
            res += String.format("Description : %s%nPubliée : %s", this.wiki.getSummary(), this.wiki.getPublished());
        }
        return res;
    }
}
