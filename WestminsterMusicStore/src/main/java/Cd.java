import org.mongodb.morphia.annotations.Entity;

@Entity("MusicItem")
public class Cd extends MusicItem {
    private String albumname;
    private int totalDuration;


    public Cd(int itemId, char itemType, String title, String genre, String artist, String releaseDate, double price, String albumname, int totalDuration) {
        super(itemId, itemType, title, genre, artist, releaseDate, price);
        this.albumname = albumname;
        this.totalDuration = totalDuration;

    }

    public Cd() {
    }

    @Override
    public String toString() {
        return "Cd{" +
                "albumname='" + albumname + '\'' +
                ", totalDuration=" + totalDuration +
                ", itemId=" + itemId +
                ", itemType=" + itemType +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", price=" + price +
                '}';
    }

    public String getAlbumname() {
        return albumname;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
