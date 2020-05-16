package com.cbsystem.view;

import com.cbsystem.Main;
import com.cbsystem.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;


public class DeleteReservationsController {

    // scene base table for the reservations that are currently on the system
    @FXML
    private TableView<Reservation> reservationsTable;

    private TableColumn<Reservation, Integer> numberCol;
    private TableColumn<Reservation, Double> priceCol;
    private TableColumn<Reservation, String> nameCol;

    // base scene variables
    private Stage dialogStage;
    private boolean okClicked = false;

    private Main mainApp;


    @FXML
    private void initialize() {
        // create and initialize the columns
        numberCol = new TableColumn("Number");
        priceCol = new TableColumn("Price");
        nameCol = new TableColumn("Attendee");

        // set observable property that columns will look for every time a new object of type Reservation is added
        numberCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("attendeeName"));

        // add columns to table
        reservationsTable.getColumns().addAll(numberCol, nameCol, priceCol);

    }

    public void setMain(Main mainApp){
        // Get the controller for the CBS
        this.mainApp = mainApp;

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    // copy reservation data to local scene data
    public void setReservationList() {

        reservationsTable.getItems().addAll(mainApp.getReservationData());
    }

    // close button function handle
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // delete button function handle
    @FXML
    private void handleDelete() {
        // get current selected item in the table
        Reservation toDelete = reservationsTable.getSelectionModel().getSelectedItem();
        // delete item from the table
        reservationsTable.getItems().remove(toDelete);

        // update profit
        toDelete.getReservedConcert().setCurrentProfit(toDelete.getReservedConcert().getCurrentProfit()-toDelete.getPrice());

        // delete reservation from the reservation list and from DB
        mainApp.removeReservationFromDB(toDelete);
        mainApp.getReservationData().remove(toDelete);
    }

}
