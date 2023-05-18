package fr.ul.miage.sd.metier;

import java.util.List;

import fr.ul.miage.sd.response.ArtistResponseBody;

public class SimilarArtist {
    List<ArtistResponseBody> artist;

    public SimilarArtist() {}

    public SimilarArtist(List<ArtistResponseBody> artist) {
        this.artist = artist;
    }

    public List<ArtistResponseBody> getArtist() {
        return artist;
    }

    public void setArtist(List<ArtistResponseBody> artist) {
        this.artist = artist;
    }
}
