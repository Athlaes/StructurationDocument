package fr.ul.miage.sd.response;

import java.util.List;

public class TracksResponse {
    private List<TrackResponseBody> track;

    public TracksResponse() {}
 
    public TracksResponse(List<TrackResponseBody> tracks){
        this.track = tracks;
    }

    public List<TrackResponseBody> getTrack() {
        return track;
    }

    public void setTrack(List<TrackResponseBody> track) {
        this.track = track;
    }
}
