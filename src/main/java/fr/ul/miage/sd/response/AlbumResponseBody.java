package fr.ul.miage.sd.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fr.ul.miage.sd.deserializer.ArtistDeserializer;

public class AlbumResponseBody {
    @JsonProperty("name")
    @JsonAlias("title")
    private String name;
    @JsonDeserialize(using = ArtistDeserializer.class)
    private String artist;
    private String mbid;
    @JsonProperty("tags")
    @JsonAlias("toptags")
    private TagsResponse toptags; 
    private List<TrackResponseBody> tracks;

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setTracks(List<TrackResponseBody> tracks) {
        this.tracks = tracks;
    }

    public String getArtist() {
        return artist;
    }

    public String getMbid() {
        return mbid;
    }

    public List<TrackResponseBody> getTracks() {
        return tracks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TagsResponse getToptags() {
        return toptags;
    }

    public void setToptags(TagsResponse toptags) {
        this.toptags = toptags;
    }
}
