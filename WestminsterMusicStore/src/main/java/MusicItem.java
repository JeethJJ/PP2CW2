import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

import java.util.Objects;

public abstract class MusicItem {

    @Id
    @Property("itemId")
    @Indexed(unique = true)
    protected int itemId;
    protected char itemType;

    @Indexed(unique = true)
    protected String title;
    protected String genre;
    protected String artist;
    protected String releaseDate;
    protected double price;

    public MusicItem(int itemId, char itemType, String title, String genre, String artist, String releaseDate, double price) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.price = price;


    }

    public MusicItem(){
        super();
    }

    @Override
    public String toString() {
        return "MusicItem{" +
                "itemId=" + itemId +
                ", itemType=" + itemType +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicItem musicItem = (MusicItem) o;
        return itemId == musicItem.itemId &&
                itemType == musicItem.itemType &&
                Double.compare(musicItem.price, price) == 0 &&
                Objects.equals(title, musicItem.title) &&
                Objects.equals(genre, musicItem.genre) &&
                Objects.equals(artist, musicItem.artist) &&
                Objects.equals(releaseDate, musicItem.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemType, title, genre, artist, releaseDate, price);
    }



    public String getTitle() {
        return title;
    }

    public int getItemId() {
        return itemId;
    }

    public char getItemType() {
        return itemType;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getPrice() {
        return price;
    }
}
