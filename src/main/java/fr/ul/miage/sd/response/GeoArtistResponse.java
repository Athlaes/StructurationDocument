package fr.ul.miage.sd.response;

public class GeoArtistResponse {
    private String name;
    private int listeners;
    private String mbid;
    private String url;
    private String evolution = "=";
    
    public GeoArtistResponse() {}
 
    public GeoArtistResponse(ArtistResponseBody artistResponse) {
        this.name = artistResponse.getName();
        this.evolution = artistResponse.getEvolution();
        this.mbid = artistResponse.getMbid();
        this.evolution = artistResponse.getEvolution();
        this.url = artistResponse.getUrl();
        this.listeners = artistResponse.getStats().getListeners();
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
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
