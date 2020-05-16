package com.cbsystem;

import java.util.ArrayList;
import java.util.HashMap;

public class Reservation {

    // reservation attributes
    private int serialNumber;
    private double price;
    private String attendeeName;
    private Concert reservedConcert;
    private ArrayList<Seat> reservedSeats;

    // base constructor to initialize temp variables
    public Reservation()
    {
        this.serialNumber = 0;
        this.price = 0.0;
        this.attendeeName = "John Doe";
        this.reservedConcert = new Concert();
        this.reservedSeats = new ArrayList<Seat>();
    }

    // full constructor for a reservation
    public Reservation(int serialNumber, double price, String attendeeName, Concert reservedConcert) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.attendeeName = attendeeName;
        this.reservedConcert = reservedConcert;
        this.reservedSeats = new ArrayList<Seat>();
    }


    // Access Methods
    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public Concert getReservedConcert() {
        return reservedConcert;
    }

    public void setReservedConcert(Concert reservedConcert) {
        this.reservedConcert = reservedConcert;
    }

    public ArrayList<Seat> getReservedSeats() {
        return reservedSeats;
    }

    // methods
    // add a seat to the reservation
    public void addSeat(Seat _seat)
    {
        // add seat if it is not reserved
        if (!_seat.isReserved()) {
            // add seat to reservation
            this.reservedSeats.add(_seat);
            // set seat in concert to reserved
            this.reservedConcert.reserveSeat(_seat.getNumber());
        }
    }

    public void removeSeat(Seat _seat)
    {
        // remove seat from reservation
        this.reservedSeats.remove(_seat);
        // set seat in concert to free
        this.reservedConcert.freeSeat(_seat.getNumber());
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "serialNumber=" + serialNumber +
                ", price=" + price +
                ", attendeeName='" + attendeeName + '\'' +
                ", reservedConcert=" + reservedConcert +
                ", reservedSeats=" + reservedSeats +
                '}';
    }
}
