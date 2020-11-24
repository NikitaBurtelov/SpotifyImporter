package musicdata;

public class Track {
    public String title;
    public String artist;
    public String album;

    public Track(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public Track(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
