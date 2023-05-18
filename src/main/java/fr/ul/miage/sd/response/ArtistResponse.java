package fr.ul.miage.sd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtistResponse {
    @JsonProperty("artist")
    private ArtistResponseBody artistResponseBody;

    public void setArtistResponseBody(ArtistResponseBody artistResponseBody) {
        this.artistResponseBody = artistResponseBody;
    }

    public ArtistResponseBody getArtistResponseBody() {
        return artistResponseBody;
    }

}
