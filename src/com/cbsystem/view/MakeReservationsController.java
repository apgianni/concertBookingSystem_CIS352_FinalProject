package com.cbsystem.view;

import com.cbsystem.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.*;

public class MakeReservationsController {

    @FXML
    private TextField NameField;
    @FXML
    private Label SerialField;
    @FXML
    private TextField PriceField;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TableView<Seat> selectedSeatTable;

    private TableColumn<Seat, Integer> ticketCol;
    private TableColumn<Seat, Double> priceCol;
    private TableColumn<Seat, SeatClass> typeCol;

    private Stage dialogStage;
    private boolean okClicked = false;

    private Reservation reservation;
    private HashMap<Integer, Concert> concertList;
    private Concert selectedConcert;

    private Main mainApp;

    @FXML
    private void initialize() {
        // create and intialize the columns
        ticketCol = new TableColumn("Number");
        priceCol = new TableColumn("Price");
        typeCol = new TableColumn("Type");

        // set observable property that columns will look for everytime a new object of type Reservation is added
        ticketCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("classType"));

        // add columns to table
        selectedSeatTable.getColumns().addAll(ticketCol, priceCol, typeCol);

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setReservation(Reservation res) {
        this.reservation = res;
    }

    public void setMain(Main mainApp){
        // Get the controller for the CBS
        this.mainApp = mainApp;
    }

    // set serial number by getting the current serial number from the main CBS system
    public void setSerialNumber(int number)
    {
        SerialField.setText(String.valueOf(number));
        reservation.setSerialNumber(number);
    }

    // get current concert list from the main CBS system
    public void setConcertList(HashMap<Integer, Concert> _concertList) {
        this.concertList = _concertList;

        // add each element of the Concert list to the choiceBox in the scene so the user can selected it
        Iterator it = concertList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Concert> pair = (Map.Entry) it.next();
            choiceBox.getItems().add(pair.getKey() + " : " + pair.getValue().getName());
        }

    }

    // check if ok is clicked
    public boolean isOkClicked() {
        return okClicked;
    }

    // handle for the confirm button
    @FXML
    private void handleComfirm() {

        // set the name from the filled form. the one missing is the Name field
        reservation.setAttendeeName(NameField.getText());
        // show all the data to on the confirmation window
        mainApp.showConfirmationDialog(reservation);
        // set okay as clicked and close dialog
        okClicked = true;
        dialogStage.close();

    }

    // everytime a concert is selected update the choice box and set it to the new concert
    @FXML
    private void handleConcertSelected() {
        // cut the : from the string to get the actual concert name
        String[] concertIDName = choiceBox.getValue().split(":");
        int concertID = Integer.parseInt(concertIDName[0].replace(" ", ""));
        // get the concert object via its ID number
        selectedConcert = concertList.get(concertID);

        // add concert to the reservation details
        reservation.setReservedConcert(selectedConcert);
    }

    // cancel button handle. Just closes the dialog
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // add a seat handle. calls the seat show dialog scene
    @FXML
    private void handleAddSeat() {

        // create temporary seatList to be filled by the user
        ArrayList<Seat> seatList = new ArrayList<>();
        // call the new window
        mainApp.showAddSeatDialog(selectedConcert.getSeatMap(), seatList);

        // for each returned seat. add it to the list, update the reservation price and add it to the
        // reserved seats of the reservations
        for(int i = 0; i < seatList.size(); i++)
        {
            selectedSeatTable.getItems().add(seatList.get(i));
            reservation.setPrice(reservation.getPrice() + seatList.get(i).getCost());
            reservation.addSeat(seatList.get(i));
            reservation.getReservedConcert().setCurrentProfit(reservation.getReservedConcert().getCurrentProfit()+reservation.getPrice());
            PriceField.setText(String.valueOf(reservation.getPrice()));
        }

    }

    // remove a seat handle button
    @FXML
    private void handleRemoveSeat() {

        // get the index and the selected seat
        int selectedIndex = selectedSeatTable.getSelectionModel().getSelectedIndex();
        Seat selectedSeat = selectedSeatTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            // remove seat from table
            selectedSeatTable.getItems().remove(selectedIndex);

            // remove seat from reservation
            reservation.removeSeat(selectedSeat);
            // update price
            reservation.setPrice(reservation.getPrice() - selectedSeat.getCost());
            PriceField.setText(String.valueOf(reservation.getPrice()));
        } else {}


    }

}