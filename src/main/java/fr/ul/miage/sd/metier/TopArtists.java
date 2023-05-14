package fr.ul.miage.sd.metier;

import java.util.List;

/**
 * TopArtists
 */
public class TopArtists {
    private String country;
    private List<String> artistsMbid;
    
    public List<String> getArtistsMbid() {
        return artistsMbid;
    }

    public String getCountry() {
        return country;
    }

    public void setArtistsMbid(List<String> artistsMbid) {
        this.artistsMbid = artistsMbid;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}