package fr.ul.miage.sd.response;

import java.util.List;

public class AlbumResponse {
    private String artist;
    private String mbid;
    private List<TagResponse> toptags;
    private List<TrackResponse> tracks;

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setToptags(List<TagResponse> toptags) {
        this.toptags = toptags;
    }

    public void setTracks(List<TrackResponse> tracks) {
        this.tracks = tracks;
    }

    public String getArtist() {
        return artist;
    }

    public String getMbid() {
        return mbid;
    }

    public List<TagResponse> getToptags() {
        return toptags;
    }

    public List<TrackResponse> getTracks() {
        return tracks;
    }
}
