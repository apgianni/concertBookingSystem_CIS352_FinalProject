package com.cbsystem;

import com.cbsystem.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Main extends Application {

    // scene base variables
    private Stage primaryStage;
    private AnchorPane rootLayout;

    // global variables for the reservation system
    private ObservableList<Reservation> ReservationData = FXCollections.observableArrayList();
    private HashMap<Integer,Seat> currentSeatMap = new HashMap<>();

    private ConcertBookingSystem GlobalCBS = new ConcertBookingSystem();

    // global value for reservation serial Number
    private int serialNumber = 1;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Concert Booking System");
        this.primaryStage.centerOnScreen();
        // anti-aliasing for fonts
        System.setProperty("prism.lcdtext", "false");
        showBookingOverview();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void MainApp(){

        // @ adding some hardcoded info
        System.out.println("It all begins here");

        // Test Venues
        Venue O2 = new Venue(1, "O2", 50, 500);
        Venue MadisonSquare = new Venue( 2, "Madison Square", 100, 100);

        // Test Concerts
        Concert BillieEilish2020 = new Concert(2, "Billie Eilish", new Date(), MadisonSquare);
        Concert Muse = new Concert(1, "Muse", new Date(), O2);

        // testing CBS
        GlobalCBS = new ConcertBookingSystem();
        GlobalCBS.addConcert(BillieEilish2020);
        GlobalCBS.addConcert(Muse);

        populateReservationsFromDB();
    }

    // methods to access Global CBS system data
    public HashMap<Integer,Seat> getSeatList(){
        return currentSeatMap;
    }

    public ObservableList<Reservation> getReservationData(){
        return ReservationData;
    }

    public HashMap<Integer, Concert> getConcertList() { return GlobalCBS.getConcertList();}

    // main screen loader function. Updates the main GUI stage/scene to the main screen
    public void showBookingOverview(){
        try
        {
            // Java FX loader creation and scene setting based on the main screen design
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ReservationsMain.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // create a new CBS app
            MainApp();

            // set the controller to the main menu controller
            ReservationsOverviewController controller = loader.getController();
            controller.setMain(this);

            // show GUI
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // delete a reservation loader function. Updates the main GUI stage/scene to the delete reservation screen
    public boolean showReservationsDeleteDialog()
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/DeleteReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Delete Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // set the controller to the delete controller
            DeleteReservationsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setReservationList();

            // show
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // reservation loader function. Updates the main GUI stage/scene to the confirm reservation screen
    public boolean showConfirmationDialog(Reservation res)
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ReservationConfirmDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirm Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the Booking into the controller.
            ReservationConfirmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            // show the reservation details for the selected reservation by passing the called Reservation res parameter
            controller.showReservationDetails(res);

            // show
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // add seat loader function. Updates the main GUI stage/scene to the confirm add seat screen with all the possible seats
    public boolean showAddSeatDialog(HashMap<Integer, Seat> seatMap, ArrayList<Seat> chosenSeats)
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddSeat.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Seat");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddSeatController controller = loader.getController();
            controller.setMain(this);
            controller.setDialogStage(dialogStage);

            // sets the seatMap of the current selected concert as the current seatMap
            currentSeatMap = seatMap;
            controller.setSeatMap(seatMap);
            controller.showSeatList(chosenSeats);

            // show
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // show reservation edit/make function. Updates the main GUI stage/scene to the edit reservation
    public boolean showReservationEditDialog(Reservation reservation) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MakeReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Make New Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // initiate reservation edit dialog
            MakeReservationsController controller = loader.getController();
            controller.setMain(this);
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);
            controller.setConcertList(GlobalCBS.getConcertList());
            controller.setSerialNumber(serialNumber++);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // called when we need to add a new record to the DB
    public void addReservationToDB(Reservation reservation){
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connect = db.getConnection();
            String sql = "Insert into ConcertBookingSystem.Reservations(id,attendee,venue,concert,date, seats, price)VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1, reservation.getSerialNumber()); // id
            ps.setString(2, reservation.getAttendeeName()); // attendee
            ps.setString(3, reservation.getReservedConcert().getVenue().getName()); // venue
            ps.setString(4, reservation.getReservedConcert().getName()); // concert
            ps.setString(5, reservation.getReservedConcert().getDate().toString()); // date


            String listOfSeats_string = "";
            for(Seat seat : reservation.getReservedSeats())
            {
                listOfSeats_string += seat.toString() + " ";
            }
            ps.setString(6, listOfSeats_string); // seats
            ps.setDouble(7, reservation.getPrice()); // price
            ps.executeUpdate();

            connect.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // called at the beginning of the program to populate the UI list
    public void populateReservationsFromDB()
    {
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connect = db.getConnection();
            String sql = "SELECT id, attendee, venue, concert, date, seats, price FROM ConcertBookingSystem.Reservations";
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs =  ps.executeQuery();

            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                //int age = rs.getInt("age");
                String attendee = rs.getString("attendee");
                //String venue = rs.getString("venue");
                String concert = rs.getString("concert");
                //String date = rs.getString("date");
                double price = rs.getDouble("price");
                String listofSeasts_string = rs.getString("seats");

                // find concert by string name;
                System.out.println(concert);
                Concert resConcert = GlobalCBS.findConcertByName(concert);
                System.out.println(resConcert);
                Reservation res = new Reservation(id, price, attendee, resConcert);

                // update concert seating
                String[] seats = listofSeasts_string.split(" ");

                for( String seat : seats)
                {
                    if(!seat.equals("Standing")) {
                        resConcert.reserveSeat(Integer.parseInt(seat));
                    }
                    else
                    {
                        resConcert.reserveSeat(0);
                    }
                }

                // update concert profit
                res.getReservedConcert().setCurrentProfit(res.getReservedConcert().getCurrentProfit()+res.getPrice());


                // add reservation to UI list
                getReservationData().add(res);

            }

            connect.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // called when we want to remove a record from the DB
    public void removeReservationFromDB(Reservation reservation){
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connect = db.getConnection();
            String sql = "DELETE FROM ConcertBookingSystem.Reservations WHERE id=?";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1, reservation.getSerialNumber()); // id
            ps.executeUpdate();
            connect.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}