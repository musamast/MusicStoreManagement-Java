package musicstoremanagement;

import java.util.ArrayList;

public class Album {

    private int id;
    private String title;
    private int ReleaseYear;
    private final String[] genres = {"Classical", "Jazz", "Metal", "Pop", "R&B & Soul", "Rap & Hip-Hop", "Rock", "Other"};
    private String genre;
    private int rating;
    private int quantity;
    private double price;
    private ArrayList<Track> tracks;

    public Album(int id, String title, int ReleaseYear, int rating, int quantity, double price, String genre) {
        tracks = new ArrayList();
        this.id = id;
        this.title = title;
        this.ReleaseYear = ReleaseYear;
        this.rating = rating;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
    }

    public Album() {
        tracks = new ArrayList();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Album(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(int ReleaseYear) {
        this.ReleaseYear = ReleaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public Boolean addTrack(Track track) {
        boolean found = false;
        for (Track oldTrack : tracks) {
            if (oldTrack.getTitle() == null ? track.getTitle() == null : oldTrack.getTitle().equals(track.getTitle())) {
                found = true;
            }
        }
        if (!found) {
            tracks.add(track);
            return true;
        } else {
            return false;
        }
    }

    public String[] getGenres() {
        return genres;
    }

}
