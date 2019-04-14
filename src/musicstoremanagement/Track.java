package musicstoremanagement;

public class Track {

    private String title;
    private String duration;
    private Singer artist;
    private int rating;
     private final String[] languages = {"English", "Hindi", "Urdu", "Portuguese", "Spanish", "French", "Hebrew", "Other"};
private String language;
    public Track(String title, String duration, Singer artist, int rating,String language) {
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.rating = rating;
        this.language=language;

    }

    public Track() {  }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Singer getArtist() {
        return artist;
    }

    public void setArtist(Singer artist) {
        this.artist = artist;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
