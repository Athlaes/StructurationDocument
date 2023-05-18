package fr.ul.miage.sd.metier;

import java.util.List;

public class TopTrack {
    private String country;
    private List<String> tracksMbids;

    public String getCountry() {
        return country;
    }

    public List<String> getTracksMbids() {
        return tracksMbids;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTracksMbids(List<String> tracksMbids) {
        this.tracksMbids = tracksMbids;
    }
}
