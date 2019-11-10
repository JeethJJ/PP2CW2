import org.mongodb.morphia.Datastore;

public interface StoreManager {
    void addMusicItemToDatabase(MusicItem item , Datastore UOWdatabase);        // by default
    void deleteItem (String itemNo);
    void printItem();
    void sortItem();
    void generateReport();
    void searchItem();
    void buyItem();
}
