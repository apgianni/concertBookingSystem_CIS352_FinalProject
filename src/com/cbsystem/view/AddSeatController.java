package com.cbsystem.view;

import com.cbsystem.Main;
import com.cbsystem.Reservation;
import com.cbsystem.Seat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSeatController {

    /// important scene elements. in this case a grid pane for a dynamic addition of buttons
    @FXML
    private GridPane SeatList;

    private Stage dialogStage;
    private boolean okClicked = false;

    HashMap<Integer,Seat> seatList;
    private ArrayList<Button> seatButtonsList;
    private ArrayList<Seat> selectedSeats;
    private Main mainApp;


    @FXML
    private void initialize() {
        // initializes the base lists: buttons, seats and the copy of the concert SeatMap
        seatButtonsList = new ArrayList<>();
        selectedSeats = new ArrayList<>();
        seatList = new HashMap<>();
    }

    public void setMain(Main mainApp){
        // Get the controller for the CBS
        this.mainApp = mainApp;

    }

    public void setSeatMap(HashMap<Integer,Seat> concertSeats){
        // Copy seats
        seatList.putAll(concertSeats);

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void addSeatToList(int index)
    {
        this.selectedSeats.add(mainApp.getSeatList().get(index));
    }

    public void showSeatList(ArrayList<Seat> seatsToBeReserved)
    {
        // different seats CSS style
        String reserved = String.format("-fx-background-color: %s;", "#B22222");
        String selected = String.format("-fx-background-color: %s;", "#00CED1");

        // sets selected seats as the seats from the reservation
        selectedSeats = seatsToBeReserved;

        // get an iterator for the concert available seats. Needs to be called after setSeatMap!
        java.util.Iterator iter = seatList.entrySet().iterator();

        // add special standing button for seat number 0
        Button standingButton = new Button("Standing");

        // check if reserved
        if(seatList.get(0).isReserved())
        {
            // if reserved set style and disable button
            standingButton.setStyle(reserved);
            standingButton.setDisable(true);
        }

        // set on Action special listener based on setting selected style and adding seat to list
        standingButton.setOnAction(event -> {
            Button clickedButton = (Button) event.getSource();
            clickedButton.setStyle(selected);
            addSeatToList(0);

        });

        // for each seat, check its status, to see if reserved, and if not add it to the screen and add the listener
        while (iter.hasNext()) {

            Map.Entry<Integer, Seat> element = (Map.Entry<Integer, Seat>) iter.next();
            // check if seat is not Standing, since it has already been added
            if(element.getValue().getNumber() != 0) {

                // add seat number to button text
                Button newSeatButton = new Button(String.valueOf(element.getValue().getNumber()));

                // set on Action special listener based on setting selected style and adding seat to list
                newSeatButton.setOnAction(event -> {
                    Button clickedButton = (Button) event.getSource();
                    clickedButton.setStyle(selected);
                    addSeatToList(Integer.parseInt(clickedButton.getText()));

                });

                // check if it is reserved, if so disable it and set it on specific style
                if (element.getValue().isReserved()) {
                    newSeatButton.setStyle(reserved);
                    newSeatButton.setDisable(true);

                }

                seatButtonsList.add(newSeatButton);
            }
            iter.remove(); // avoids a ConcurrentModificationException"
        }


        int colCounter = 0;
        int rowCounter = 2;

        // add standing button to the whole set of columns
        SeatList.add(standingButton, 0, 0, 10, 2);
        standingButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // for every other button add them to list dynamically to a max of 10 per row!
        for(int i = 0; i < seatButtonsList.size(); i++)
        {
            if(colCounter == 10)
            {
                rowCounter++;
                colCounter = 0;
            }
            SeatList.add(seatButtonsList.get(i), colCounter, rowCounter);
            colCounter++;
        }


    }

    // return if button has been clicked or not
    public boolean isOkClicked() {
        return okClicked;
    }

    // action handle for the add button. It will just close the current window
    @FXML
    private void handleAdd() {
        dialogStage.close();

    }

}
