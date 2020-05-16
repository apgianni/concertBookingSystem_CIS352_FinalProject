package com.cbsystem.view;

import com.cbsystem.Concert;
import com.cbsystem.Main;
import com.cbsystem.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;


public class ReservationsOverviewController {

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private Label serialNumberLabel;
    @FXML
    private Label attendeeNameLabel;
    @FXML
    private Label concertNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label numberOfTicketsLabel;


    private Main mainApp;

    public ReservationsOverviewController()
    {
        // empty constructor
    }

    @FXML
    // this method is called by JavaFX when the Scene is created
    private void initialize(){


    }

    public void setMain(Main mainApp){
        // Get the controller for the CBS @TODO change Main to CBS...
        this.mainApp = mainApp;

    }

    // show the reservation details
    private void showReservationDetails(Reservation reservation){
        if(reservation != null){
            attendeeNameLabel.setText(reservation.getAttendeeName());
            serialNumberLabel.setText(String.valueOf(reservation.getSerialNumber()));
            priceLabel.setText(String.valueOf(String.valueOf(reservation.getPrice())));

        } else {
            serialNumberLabel.setText("");
            attendeeNameLabel.setText("");
            priceLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteBooking() {
        mainApp.showReservationsDeleteDialog();

    }

    @FXML
    private void handleNewBooking() {
        Reservation tempRes = new Reservation();
        boolean okClicked = mainApp.showReservationEditDialog(tempRes);

        if (okClicked) {
            // if Ok, add Reservation to local list and DB
            mainApp.getReservationData().add(tempRes);
            mainApp.addReservationToDB(tempRes);

        }
    }



}
