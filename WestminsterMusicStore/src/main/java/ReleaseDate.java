public class ReleaseDate {

    private String releaseDate;


    public ReleaseDate(int day,int month, int year) {
        this.setReleaseDate(day,month,year);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int day,int month, int year) {
        this.releaseDate = day + "." + month + "." + year;
    }

}
