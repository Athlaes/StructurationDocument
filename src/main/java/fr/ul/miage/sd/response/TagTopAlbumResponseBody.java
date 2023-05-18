package fr.ul.miage.sd.response;

import java.util.List;

public class TagTopAlbumResponseBody {
    private List<AlbumResponseBody> album;

    public List<AlbumResponseBody> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumResponseBody> album) {
        this.album = album;
    }
}
