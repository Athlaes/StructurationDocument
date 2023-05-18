package fr.ul.miage.sd.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopTracksResponse {
    List<GeoTrackResponse> track;
    @JsonProperty(value = "@attr")
    private Map<String, String> attributes = new HashMap<>();

    public List<GeoTrackResponse> getTrack() {
        return track;
    }

    public void setTrack(List<GeoTrackResponse> track) {
        this.track = track;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

}
