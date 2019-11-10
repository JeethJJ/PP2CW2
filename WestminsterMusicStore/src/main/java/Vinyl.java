import org.mongodb.morphia.annotations.Entity;

import java.util.Objects;

@Entity("MusicItem")
public class Vinyl extends MusicItem {

    private double diameter;
    private double speed;

    public Vinyl(int itemId, char itemType, String title, String genre, String artist, String releaseDate, double price, double diameter, double speed) {
        super(itemId, itemType, title, genre, artist, releaseDate, price);
        this.diameter = diameter;
        this.speed = speed;
    }

    public Vinyl() {
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "diameter=" + diameter +
                ", speed=" + speed +
                ", itemId=" + itemId +
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
        if (!super.equals(o)) return false;
        Vinyl vinyl = (Vinyl) o;
        return Double.compare(vinyl.diameter, diameter) == 0 &&
                Double.compare(vinyl.speed, speed) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), diameter, speed);
    }

    public double getDiameter() {
        return diameter;
    }

    public double getSpeed() {
        return speed;
    }
}
