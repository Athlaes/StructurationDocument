package fr.ul.miage.sd.metier;
import java.util.List;

public class Album {
    private String artist;
    private String mbid;
    private List<String> toptagsNames;
    private List<String> tracksIds;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public String getMbid() {
        return mbid;
    }

    public List<String> getToptagsNames() {
        return toptagsNames;
    }

    public List<String> getTracksIds() {
        return tracksIds;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setToptagsNames(List<String> toptagsNames) {
        this.toptagsNames = toptagsNames;
    }

    public void setTracksIds(List<String> tracksIds) {
        this.tracksIds = tracksIds;
    }

}
