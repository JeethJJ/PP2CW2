import com.mongodb.MongoClient;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.layout.HBox;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.awt.Desktop;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class WestminsterMusicStoreManager extends Application implements StoreManager {


    private TableView table = new TableView();

    private static List<MusicItem> musicItemsList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Morphia morphia = new Morphia();
        morphia.mapPackage("WestminsterMusicStore");
        Datastore uowDatabase = morphia.createDatastore(new MongoClient(), "JJMusicStore");
        uowDatabase.ensureIndexes();
        WestminsterMusicStoreManager UOWMusicStore = new WestminsterMusicStoreManager();

        List<MusicItem> queryA = uowDatabase.createQuery(MusicItem.class).asList();
        musicItemsList.addAll(queryA);

        int selection;
        System.out.println("Welcome to Westminster Music Store!!\n");
        String functions="1. Add Item \n" +
                "2. Delete Item \n" +
                "3. Print all Items \n" +
                "4. Sort Items \n" +
                "5. Generate report \n" +
                "6. Search Item \n" +
                "7. Buy Item \n" +
                "8. Print items in table\n" +
                "9. Exit";
        do {
            System.out.println("Select option :");
            System.out.println(functions);
            System.out.print(">");
            while (!sc.hasNextInt()) {          //input validation to get integer
                System.out.println("You entered a non-numeric value\nRe-enter \n" +functions);
                System.out.print(">");
                sc.next();
            }
            selection = sc.nextInt();
            if (selection > 9 || selection < 1) {
                System.out.println("Your selection is out of range \nSelect again");
            }
            System.out.println();
            switch (selection) {
                case 1:
                    if (musicItemsList.size()<1000) {
                        //item type part
                        int availableSpace=1000-musicItemsList.size();
                        System.out.println("You have "+ availableSpace +" space left .");
                        char type = 0;
                        while (type != 'c' && type != 'v') {
                            System.out.print("Please input item type (c)d or (v)inyl :");
                            type = sc.next().toLowerCase().charAt(0);
                            if (type != 'c' && type != 'v') {
                                System.out.println("Type doesn't match \nEnter again");
                            }
                        }

                        //item id part

                        int itemId = 0;
                        while (itemId < 1) {
                            System.out.print("Input item ID : ");
                            intValidator();
                            itemId = sc.nextInt();
                            if (itemId < 1) {
                                System.out.println("Invalid Id");
                            }
                            for (MusicItem g : musicItemsList) {
                                if (g.itemId == itemId) {
                                    System.out.println("This id already exists.");
                                    itemId=0;
                                }
                            }
                        }
                        Scanner sc2 = new Scanner(System.in);
                        System.out.print("Input item title : ");
                        String title = sc2.nextLine().toLowerCase();


                        Scanner sc3 = new Scanner(System.in);
                        System.out.print("Input item genre : ");
                        String genre = sc3.nextLine();

                        Scanner sc4 = new Scanner(System.in);
                        System.out.print("Input artist name : ");
                        String artist = sc4.nextLine();

                        double price = 0;
                        while (price <= 0.0) {
                            System.out.print("Input item price in USD : ");
                            doubleValidator();
                            price = sc.nextDouble();
                            if (itemId <= 0.0) {
                                System.out.println("Invalid entry ");
                            }
                        }


                        System.out.println("Enter release date details");
                        int day = 0;
                        int month = 0;
                        int year = 0;
                        while (year < 1840 || year > 2019) {
                            System.out.print("Input year : ");
                            intValidator();
                            year = sc.nextInt();
                            if (year < 1840 || year > 2019) {
                                System.out.println("Invalid year");
                            }
                        }

                        while (month < 1 || month > 12) {
                            System.out.print("Input month : ");
                            intValidator();
                            month = sc.nextInt();
                            if (month < 1 || month > 31) {
                                System.out.println("Invalid day");
                            }
                        }

                        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                            while (day < 1 || day > 31) {
                                System.out.print("Input day : ");
                                intValidator();
                                day = sc.nextInt();
                                if (day < 1 || day > 31) {
                                    System.out.println("Invalid day");
                                }
                            }
                        } else if (month == 2) {
                            if (year % 4 == 0) {
                                while (day < 1 || day > 29) {
                                    System.out.print("Input day : ");
                                    intValidator();
                                    day = sc.nextInt();
                                    if (day < 1 || day > 29) {
                                        System.out.println("Invalid day");
                                    }
                                }
                            } else {
                                while (day < 1 || day > 28) {
                                    System.out.print("Input day : ");
                                    intValidator();
                                    day = sc.nextInt();
                                    if (day < 1 || day > 28) {
                                        System.out.println("Invalid day");
                                    }
                                }
                            }
                        }else {
                            while (day < 1 || day > 30) {
                                System.out.print("Input day : ");
                                intValidator();
                                day = sc.nextInt();
                                if (day < 1 || day > 30) {
                                    System.out.println("Invalid day");
                                }
                            }
                        }
                        ReleaseDate rDate = new ReleaseDate(day, month, year);
                        String releaseDate = rDate.getReleaseDate();

                        double diameter = 0;
                        double speed = 0;
                        if (type == 'v') {
                            while (diameter <= 0.0) {
                                System.out.print("Input vinyl's diameter in cm : ");
                                doubleValidator();
                                diameter = sc.nextDouble();
                                if (diameter <= 0.0) {
                                    System.out.println("Invalid entry ");
                                }
                            }

                            while (speed <= 0.0) {
                                System.out.print("Input vinyl's speed in m/s : ");
                                doubleValidator();
                                speed = sc.nextDouble();
                                if (speed <= 0.0) {
                                    System.out.println("Invalid entry ");
                                }
                            }

                            Vinyl v = new Vinyl(itemId, type, title, genre, artist, releaseDate, price, diameter, speed);
                            musicItemsList.add(v);
                            UOWMusicStore.addMusicItemToDatabase(v, uowDatabase);


                        }
                        if (type == 'c') {
                            Scanner sc5 = new Scanner(System.in);
                            System.out.print("Input album name : ");
                            String albumName = sc5.nextLine();

                            int totalDuration = 0;
                            int noOfSongs=0;
                            while (noOfSongs <= 0) {
                                System.out.print("Input how many songs are there in album "+albumName+" : ");
                                intValidator();
                                noOfSongs = sc.nextInt();
                                if (noOfSongs <= 0) {
                                    System.out.println("Invalid entry ");
                                }
                            }
                            System.out.println("Enter song durations one by one in seconds");
                            for(int i=1;i<=(noOfSongs);i++){
                                int duration=0;
                                while (duration <= 0) {
                                    System.out.print(i+". Enter duration : ");
                                    intValidator();
                                    duration = sc.nextInt();
                                    if (duration <= 0) {
                                        System.out.println("Invalid entry ");
                                    }
                                }
                                totalDuration=totalDuration+duration;
                            }
                            Cd c = new Cd(itemId, type, title, genre, artist, releaseDate, price, albumName, totalDuration);
                            UOWMusicStore.addMusicItemToDatabase(c, uowDatabase);
                            musicItemsList.add(c);
                        }
                        System.out.println();
                        break;
                    }else{
                        System.out.println("You have 1000 items in your store!!\nThe store is full \nDelete items to add new items.");
                    }
                case 2:
                    try {
                        int deleteId = 0;
                        String deleteType = null;
                        while (deleteId < 1) {
                            System.out.print("Input item ID if the item which should be deleted : ");
                            intValidator();
                            deleteId = sc.nextInt();
                            if (deleteId < 1) {
                                System.out.println("Invalid Id");
                            }
                        }
                        if ((uowDatabase.find(MusicItem.class).field("itemId").equal(deleteId).get()) == (null)) {
                            System.out.println("Item not found");
                        } else {
                            try {
                                for (MusicItem g : musicItemsList) {
                                    if (g.itemId == deleteId) {
                                        if (g.itemType == 'c') {
                                            deleteType = "cd";
                                        } else {
                                            deleteType = "vinyl";
                                        }
                                    }
                                }
                                final Query<MusicItem> queryB = uowDatabase.createQuery(MusicItem.class).filter("itemId =", deleteId);
                                uowDatabase.delete(queryB);
                                int i = 0;
                                for (MusicItem g : musicItemsList) {
                                    if (g.itemId == deleteId) {
                                        break;
                                    }
                                    i++;
                                }
                                musicItemsList.remove(i);
                                System.out.println("Successfully deleted.\nYou removed a " + deleteType + " from the store.");
                                int availableSpace=1000-musicItemsList.size();
                                System.out.println("You have "+ availableSpace +" space left after deleting.");
                            } catch (Exception a) {
                                System.out.println("Process Unsuccessful");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error while connecting to database \nRetry action");
                    }
                    System.out.println();
                    break;
                case 3:
                    String printItemType;
                    int printItemId;
                    String printItemTitle;
                    System.out.format("%1s%10s%10s%15s%n","|", "Item ID"+" |", "Item Type"+"|", "Item Title"+"  |");
                    System.out.format("%1s%10s%10s%15s%n","|","|","|","|");
                    for (MusicItem g : musicItemsList) {
                        printItemId=g.itemId;
                        printItemTitle=g.title;
                        if (g.itemType == 'c') {
                            printItemType = "CD";
                        } else {
                            printItemType = "Vinyl";
                        }
                        System.out.format("%1s%10s%10s%15s%n","|", printItemId+"  |", printItemType+"  |", printItemTitle+"  |");
                    }
                    System.out.println();
                    break;
                case 4:
                    String printItemTypeS;
                    int printItemIdS;
                    String printItemTitleS;
                    List<MusicItem> sortedList = new LinkedList<>(musicItemsList);
                    Comparator<MusicItem> c = Comparator.comparing(s -> s.title);
                    sortedList.sort(c);
                    System.out.format("%1s%10s%10s%15s%n","|", "Item ID"+" |", "Item Type"+"|", "Item Title"+"  |");
                    System.out.format("%1s%10s%10s%15s%n","|","|","|","|");
                    for (MusicItem g : sortedList) {
                        printItemIdS=g.itemId;
                        printItemTitleS=g.title;
                        if (g.itemType == 'c') {
                            printItemTypeS = "CD";
                        } else {
                            printItemTypeS = "Vinyl";
                        }
                        System.out.format("%1s%10s%10s%15s%n","|", printItemIdS+"  |", printItemTypeS+"  |", printItemTitleS+"  |");
                    }
                    System.out.println();
                    break;
                case 5:
//                    try {
//                        File f = new File("E:\\Uni Stuff\\Tutorials\\Semester 2\\PP2\\CourseWorks\\Assignment 02\\WestminsterMusicStore\\SalesDetails.txt");
//                        try {
//                            Desktop.getDesktop().open(f);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }catch(Exception e){
//                        new PrintWriter("SalesDetails.txt", "UTF-8");
//                        File f = new File("E:\\Uni Stuff\\Tutorials\\Semester 2\\PP2\\CourseWorks\\Assignment 02\\WestminsterMusicStore\\SalesDetails.txt");
//                        try {
//                            Desktop.getDesktop().open(f);
//                        } catch (IOException z) {
//                            e.printStackTrace();
//                        }
//                    }

                    File file=new File("Product.txt");
                    FileWriter fw=null;
                    PrintWriter pw=null;
                    try {
                        fw = new FileWriter(file, true);
                        pw= new PrintWriter(fw,true);
                        pw.println("JJ");
                        Desktop.getDesktop().open(file);
                    }catch (IOException e){
                        System.err.println("Error" + e.getMessage());
                    }finally {
                        try {
                            fw.close();
                            pw.close();
                        }catch (IOException e){
                            System.err.println("Error" + e.getMessage());

                        }

                    }
                    break;
                case 6:
                    int search = 0;
                    while (search < 1 || search > 2) {
                        System.out.print("1. Search by item Id \n2. Search by item title \n>");
                        intValidator();
                        search = sc.nextInt();
                        if (search < 1 || search > 2) {
                            System.out.println("Invalid entry ");
                        }
                    }
                    if (search == 1) {
                        int searchId = 0;
                        while (searchId < 1) {
                            System.out.print("Input item ID to search: ");
                            intValidator();
                            searchId = sc.nextInt();
                            if (searchId < 1) {
                                System.out.println("Invalid Id");
                            }
                        }
                        try {
                        if ((uowDatabase.find(MusicItem.class).field("itemId").equal(searchId).get()) == (null)) {
                            System.out.println("Item not found");
                        } else {
                            System.out.println(uowDatabase.find(MusicItem.class).field("itemId").equal(searchId).get());
                        }
                        }catch (Exception a){
                            System.out.println("Not Found");
                        }
                    } else {
                        Scanner sc6 = new Scanner(System.in);
                        System.out.print("Input item title to search: ");
                        String searchName = sc6.nextLine();
                        try {
                            if ((uowDatabase.find(MusicItem.class).field("title").equal(searchName).get()) == (null)) {
                                System.out.println("Item not found");
                            } else {
                                System.out.println(uowDatabase.find(MusicItem.class).field("title").equal(searchName).get());
                            }
                        }catch (Exception a){
                            System.out.println("Not Found");
                        }
                    }
                    System.out.println();
                    break;
                case 7:
                    double buyPrice = 0;
                    int buyItemId = 0;
                    int buyCount = 0;
                    String buyTitle = null;
                    String buyType = null;
                    while (buyItemId < 1) {
                        System.out.print("Input item ID to buy : ");
                        intValidator();
                        buyItemId = sc.nextInt();
                        if (buyItemId < 1) {
                            System.out.println("Invalid Id");
                        }
                    }

                    if ((uowDatabase.find(MusicItem.class).field("itemId").equal(buyItemId).get()) == (null)) {
                        System.out.println("Item not found");
                    } else {
                        for (MusicItem g : musicItemsList) {
                            if (g.itemId == buyItemId) {
                                buyPrice = g.price;
                                buyTitle = g.title;
                                if (g.itemType == 'c') {
                                    buyType = "CD";
                                } else {
                                    buyType = "Vinyl";
                                }
                            }
                        }
                        while (buyCount < 1) {
                            System.out.print("Input how many copies you want to buy : ");
                            intValidator();
                            buyCount = sc.nextInt();
                            if (buyCount < 1) {
                                System.out.println("Invalid amount");
                            }
                        }
                        double totalCost = buyPrice * buyCount;
                        System.out.println("You have bought " + buyCount + " copies of " + buyType + " " + buyTitle + " which costs $" + buyPrice + " per copy.\n" +
                                 "Your total cost is $" + totalCost);
                        String document = "ID = "+buyItemId+"\nItem type = "+ buyType+"\nTitle = "+buyTitle+"\nPrice per copy = $"+buyPrice+"\nNo of copies = "+buyCount+"\nTotal price = $"+totalCost+" \n\n";
                        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        String sales = "Sales on "+timeStamp+"\n";
                        try {
                            Files.write(Paths.get("SalesDetails.txt"), sales.getBytes(), StandardOpenOption.APPEND);
                            Files.write(Paths.get("SalesDetails.txt"), document.getBytes(), StandardOpenOption.APPEND);
                        }catch (IOException e) {

                        }
                    }

                    System.out.println();
                    break;
                case 8:
                    try {
                        launch(args);
                    } catch (Exception e) {
                        System.out.println("Re-run  the program to open the GUI again");
                    }
                    System.out.println();
                    break;
                case 9:
                    System.out.println("You chose to exit the program... \nExiting program...");
                    System.exit(0);


            }
        } while (selection != 9);
    }


    private static void doubleValidator() {
        while (!sc.hasNextDouble()) {          //input validation to get integer
            System.out.print("You entered a invalid data type\nRe-enter :");
            sc.next();
        }
    }

    private static void intValidator() {
        while (!sc.hasNextInt()) {          //input validation to get integer
            System.out.print("You entered a non-numeric value\nRe-enter :");
            sc.next();
        }
    }

    final static String[] searchFXinput = {""};
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group(),1138,480);
        primaryStage.setTitle("Westminster Music Store");

        Label label1= new Label("Westminster Music Store");
        label1.setFont(new Font("Arial", 30));
        Label label= new Label("Available Items");
        label.setFont(new Font("Arial", 20));
        Label label2= new Label("Search : ");
        label2.setFont(new Font("Arial", 15));
        TextField textField = new TextField();

        textField.setPromptText("Enter item title to search");
        Button button = new Button("Clear");


        textField.setOnKeyPressed(text -> {
            if (text.getCode() == KeyCode.BACK_SPACE || text.getCode() == KeyCode.DELETE) {
                text.consume(); // to cancel character-removing keys
            }
            String x=text.getText();
            searchFXinput[0] = searchFXinput[0] + x;
            table.setItems(getMusicItem());
        });
        // action event
        EventHandler<ActionEvent> event = e -> {
            searchFXinput[0] ="";
            textField.clear();
            table.setItems(getMusicItem());
        };
        // when button is pressed
        button.setOnAction(event);
        table.setEditable(true);
        //columns
        TableColumn itemIdColumn = new TableColumn("Item Id");
        itemIdColumn.setMinWidth(75);
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn titleColumn = new TableColumn("Item Title");
        titleColumn.setMinWidth(250);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn genreColumn = new TableColumn("Genre");
        genreColumn.setMinWidth(75);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn rdateColumn = new TableColumn("Release Date");
        rdateColumn.setMinWidth(100);
        rdateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        TableColumn artistColumn = new TableColumn("Artist");
        artistColumn.setMinWidth(150);
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn priceColumn = new TableColumn("Price ($)");
        priceColumn.setMinWidth(75);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn speedColumn = new TableColumn("Speed (m/s)");
        speedColumn.setMinWidth(75);
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn diameterColumn = new TableColumn("Diameter (cm)");
        diameterColumn.setMinWidth(75);
        diameterColumn.setCellValueFactory(new PropertyValueFactory<>("diameter"));

        TableColumn albumNameColumn = new TableColumn("Album Name");
        albumNameColumn.setMinWidth(100);
        albumNameColumn.setCellValueFactory(new PropertyValueFactory<>("albumname"));

        TableColumn totalDurationColumn = new TableColumn("Total Duration (S)");
        totalDurationColumn.setMinWidth(125);
        totalDurationColumn.setCellValueFactory(new PropertyValueFactory<>("totalDuration"));

        final HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(label1);
        final HBox hbox = new HBox();
        hbox.getChildren().addAll(label2,textField,button);
        hbox.setSpacing(10);
        final HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(hbox2,hbox);
        hbox3.setSpacing(500);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getChildren().addAll(hbox3,label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        table.setItems(getMusicItem());
        table.getColumns().addAll(itemIdColumn,titleColumn,genreColumn,rdateColumn,artistColumn,priceColumn,speedColumn,diameterColumn,albumNameColumn,totalDurationColumn);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public ObservableList<MusicItem> getMusicItem(){
        String search="";
        for (String m : searchFXinput){
            search=search+m;
        }
        ObservableList<MusicItem> musicItem = FXCollections.observableArrayList();
        if(search.equals("")) {
            for (MusicItem m : musicItemsList) {
                musicItem.addAll(m);
            }
        } else {
            for (MusicItem m : musicItemsList) {
                String titleByChar = m.title;
                String searchByChar = "";
                for (int i = 0; i < titleByChar.length(); i++) {
                    char c = titleByChar.charAt(i);
                    searchByChar = searchByChar + c;
                    if (searchByChar.equals(search)) {
                        musicItem.addAll(m);
                    }
                }
            }

        }
        return musicItem;
    }

    @Override
    public String toString() {
        return "WestminsterMusicStoreManager{" +
                "table=" + table +
                '}';
    }

    @Override
    public void addMusicItemToDatabase(MusicItem item, Datastore UOWdatabase) {
        Key<MusicItem> musicItem = UOWdatabase.save(item);
        System.out.println("Item successfully added!");
    }

    @Override
    public void deleteItem(String a) {
        System.out.println("Deleted Successfully");
    }

    @Override
    public void printItem() {
    }

    @Override
    public void sortItem() {
    }

    @Override
    public void generateReport() {
    }

    @Override
    public void searchItem() {
    }

    @Override
    public void buyItem() {
    }

}
