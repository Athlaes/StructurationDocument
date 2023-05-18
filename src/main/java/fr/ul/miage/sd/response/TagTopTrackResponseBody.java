package fr.ul.miage.sd.response;

import java.util.List;

public class TagTopTrackResponseBody {
    private List<TrackResponseBody> track;
    
    public List<TrackResponseBody> getTrack() {
        return track;
    }

    public void setTrack(List<TrackResponseBody> track) {
        this.track = track;
    }
}
