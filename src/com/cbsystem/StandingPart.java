package com.cbsystem;

// @ TODO by using the seat class (the enumerator) we can distinguish between standing and seat within the SEAT class

public class StandingPart {

    private double cost;
    private SeatClass classType;

    // access methods
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
