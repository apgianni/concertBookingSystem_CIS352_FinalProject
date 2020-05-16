package com.cbsystem;

public class Seat {

    // basic attributes
    private boolean reserved;
    private int number;
    private double cost;
    private SeatClass classType;

    // constructor
    public Seat(int number, double cost, SeatClass classType) {
        this.number = number;
        this.cost = cost;
        this.classType = classType;
        this.reserved = false;
    }

    // access methods
    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        if(number == 0)
        {
            return "Standing";
        }
        else
        {
            return String.valueOf(number);
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public SeatClass getClassType() {
        return classType;
    }

    public void setClassType(SeatClass classType) {
        this.classType = classType;
    }
}
