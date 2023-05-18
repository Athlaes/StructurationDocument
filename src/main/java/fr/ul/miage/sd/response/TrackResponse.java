package fr.ul.miage.sd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackResponse {
    @JsonProperty("track")
    private TrackResponseBody trackResponseBody;

    public TrackResponseBody getTrackResponseBody() {
        return trackResponseBody;
    }
    public void setTrackResponseBody(TrackResponseBody trackResponseBody) {
        this.trackResponseBody = trackResponseBody;
    }
}
