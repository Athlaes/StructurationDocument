package fr.ul.miage.sd.response;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fr.ul.miage.sd.deserializer.ArtistDeserializer;
import fr.ul.miage.sd.deserializer.TagsDeserializer;

public class AlbumResponseBody {
    @JsonProperty("name")
    @JsonAlias("title")
    private String name;
    @JsonDeserialize(using = ArtistDeserializer.class)
    private String artist;
    private String mbid;
    @JsonProperty("tags")
    @JsonAlias("toptags")
    @JsonDeserialize(using = TagsDeserializer.class)
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

    public Boolean hasPartialData() {
        boolean partial = false;
        if (Objects.isNull(this.getArtist()) ||
            Objects.isNull(this.getMbid()) || 
            Objects.isNull(this.getName()) ||
            Objects.isNull(this.getToptags()) ||
            Objects.isNull(this.getTracks())) {
            partial = true;
        }
        return partial;
    }

    public String toString() {
        String res = String.format("Nom : %s%nMbid : %s%nArtiste : %s%n", this.name, this.mbid, this.artist);
        if (Objects.nonNull(this.toptags)) {
            res += String.format("Tags : %s%n", this.toptags.toString());
        }
        return res;
    }
}
