package fr.ul.miage.sd.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopArtistsResponse {
    private List<GeoArtistResponse> artist;
    @JsonProperty(value = "@attr")
    private Map<String, String> attributes = new HashMap<>();

    public List<GeoArtistResponse> getArtist() {
        return artist;
    }

    public void setArtist(List<GeoArtistResponse> artist) {
        this.artist = artist;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
