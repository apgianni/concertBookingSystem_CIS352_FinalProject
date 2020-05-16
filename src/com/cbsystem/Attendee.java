package com.cbsystem;

import java.util.HashMap;

// @ TODO note that the attendee class is not needed anymore since there is no login. Can be removed

public class Attendee {

    // Attendee attributes
    private int ID;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private HashMap<Integer, Reservation> tickets;

    public Attendee(int ID, String firstName, String lastName, String mobileNumber) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;

    }

    @Override
    public String toString() {
        return "Attendee{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", tickets=" + tickets +
                '}';
    }

    // access methods
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public HashMap<Integer, Reservation> getTickets() {
        return tickets;
    }

    public void setTickets(HashMap<Integer, Reservation> tickets) {
        this.tickets = tickets;
    }

}
