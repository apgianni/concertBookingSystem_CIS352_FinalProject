package com.cbsystem;

public class Venue {

    // basic attributes
    private int ID;
    private String name;

    // venue attributes
    private int numberOfSeats;
    private int numberOfStandees;

    // constructors
    public Venue(int _ID, String _name, int _numSeats, int _numStandees)
    {
        ID = _ID;
        name = _name;
        numberOfSeats = _numSeats;
        numberOfStandees = _numStandees;
    }


    // access methods
    public int getNumberOfSeats()
    {
        return this.numberOfSeats;
    }

    public int getNumberOfStandees()
    {
        return this.numberOfStandees;
    }

    public int getID()
    {
        return this.ID;
    }

    public String getName()
    {
        return this.name;
    }

    public String toString() {
        return "Venue: " + this.name + ", Seats: " + this.numberOfSeats + ", Standees: " + this.numberOfStandees;
    }
}
