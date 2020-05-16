package com.cbsystem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Concert {

    private int ID;
    private String name;
    private Date date;
    private Venue venue;

    private int seatCapacity;
    private int standingCapacity;
    private int currentSeatOccupancy;
    private int currentStandingOccupancy;
    private double currentProfit;
    private HashMap<Integer, Seat> seatMap;

    // base constructor
    public Concert()
    {
        // empty constructor
    }

    // full constructor
    public Concert(int ID, String name, Date date, Venue venue) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.venue = venue;

        this.seatCapacity = venue.getNumberOfSeats();
        this.standingCapacity = venue.getNumberOfStandees();

        this.currentSeatOccupancy = 0;
        this.currentStandingOccupancy = 0;
        this.currentProfit = 0;

        this.seatMap = new HashMap<Integer, Seat>();

        seatMap.put(0, new Seat(0, 10.0, SeatClass.STANDING));
        for(int i = 1; i <= venue.getNumberOfSeats(); i++)
        {
            if(i < 30)
            {
                seatMap.put(i, new Seat(i, 30.0, SeatClass.FIRST_CLASS));
            }
            else
            {
                seatMap.put(i, new Seat(i, 20.0, SeatClass.SECOND_CLASS));

            }
        }
    }

    @Override
    public String toString() {
        return "Concert{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", venue=" + venue +
                ", seatCapacity=" + seatCapacity +
                ", standingCapacit=" + standingCapacity +
                ", currentSeatOccupancy=" + currentSeatOccupancy +
                ", currentStandingOccupancy=" + currentStandingOccupancy +
                ", seatMap=" + seatMap +
                '}';
    }

    // access methods
    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String _name)
    {
        this.name = _name;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date _date)
    {
        this.date = _date;
    }

    public double getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(double currentProfit) {
        this.currentProfit = currentProfit;
    }

    public int getSeatCapacity()
    {
        return seatCapacity;
    }

    public Venue getVenue() {
        return venue;
    }

    public int getStandingCapacity() {
        return standingCapacity;
    }

    public int getCurrentSeatOccupancy() {
        return currentSeatOccupancy;
    }

    public int getCurrentStandingOccupancy() {
        return currentStandingOccupancy;
    }

    public HashMap<Integer, Seat> getSeatMap() {
        return seatMap;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public void setStandingCapacit(int standingCapacit) {
        this.standingCapacity = standingCapacit;
    }

    // methods
    public boolean isSeatReserved(int _seat)
    {
        return this.seatMap.get(_seat).isReserved();
    }

    public void reserveSeat(int _seat)
    {
        this.seatMap.get(_seat).setReserved(true);
        this.currentSeatOccupancy++;
    }

    public void freeSeat(int _seat)
    {
        this.seatMap.get(_seat).setReserved(false);
        this.currentSeatOccupancy--;
    }

    // status printing information
    public void printSeatMap() {
        java.util.Iterator iter = seatMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Seat> element = (Map.Entry<Integer, Seat>) iter.next();
            if(element.getValue().isReserved()) {
                System.out.println(element.getKey() + " = reserved");
            }
            else
            {
                System.out.println(element.getKey() + " = free");
            }
            iter.remove(); // avoids a ConcurrentModificationException
        }
    }

    public void printConcertStatus()
    {
        System.out.println("Concert{" +
                "Concert with ID = " + ID + "\n" +
                "Name = '" + name + '\'' + "happening on " + date + " at " + venue + " and has " + (currentSeatOccupancy/seatCapacity)*100.0 + " % seats occupied and  "
                        + (currentStandingOccupancy/standingCapacity)*100.0 + " % standing occupied and  " + " this is the current seat map ");
        printSeatMap();
    }
}
