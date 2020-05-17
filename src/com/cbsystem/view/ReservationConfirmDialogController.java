package com.cbsystem.view;

import com.cbsystem.Main;
import com.cbsystem.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReservationConfirmDialogController {

    // labels fot the confrim scene to be referenced
    @FXML
    private Label resNumber;

    @FXML
    private Label resName;

    @FXML
    private Label resConcert;

    @FXML
    private Label resVenue;

    @FXML
    private Label resDate;

    @FXML
    private Label resSeats;

    @FXML
    private Label resPrice;

    // scene variables
    private Stage dialogStage;
    private boolean okClicked = false;

    private Main mainApp;


    @FXML
    private void initialize() {

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

    public void showReservationDetails(Reservation res)
    {
        // update labels values base on the reservation details
        resNumber.setText(String.valueOf(res.getSerialNumber()));
        resName.setText(String.valueOf(res.getAttendeeName()));
        resConcert.setText(String.valueOf(res.getReservedConcert().getName()));
        resVenue.setText(String.valueOf(res.getReservedConcert().getVenue().getName()));
        resDate.setText(res.getReservedConcert().getDate().toString());
        resSeats.setText(res.getReservedSeats().toString());
        resPrice.setText(String.valueOf(res.getPrice()));
    }

    // close the dialog when the user as check the data
    @FXML
    private void handleOk() {
        dialogStage.close();
    }



}
