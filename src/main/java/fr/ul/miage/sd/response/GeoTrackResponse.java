package fr.ul.miage.sd.response;

public class GeoTrackResponse {
    private String name;
    private String mbid;
    private String url;
    private int listeners;
    private String evolution = "+";

    public GeoTrackResponse () {}

    public GeoTrackResponse(TrackResponse trackResponse) {
        this.name = trackResponse.getName();
        this.listeners = trackResponse.getListeners();
        this.url = trackResponse.getUrl();
    }

    public String getEvolution() {
        return evolution;
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
    public String getUrl() {
        return url;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
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

    public void setUrl(String url) {
        this.url = url;
    }
}
