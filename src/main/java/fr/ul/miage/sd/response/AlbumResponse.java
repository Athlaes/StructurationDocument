package fr.ul.miage.sd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumResponse {
    @JsonProperty("album")
    private AlbumResponseBody albumResponseBody;

    public AlbumResponseBody getAlbumResponseBody() {
        return albumResponseBody;
    }

    public void setAlbumResponseBody(AlbumResponseBody albumResponseBody) {
        this.albumResponseBody = albumResponseBody;
    }
}
