package com.cbsystem;

import java.util.HashMap;
import java.util.Map;

public class ConcertBookingSystem {


    private HashMap<Integer, Concert> concertList;
    private HashMap<Integer, Venue> venueList;
    private HashMap<Integer, Attendee> attendeeList;
    private HashMap<Integer, Reservation> reservationsList;


    public ConcertBookingSystem() {
        this.concertList = new HashMap<Integer, Concert>();
        this.venueList = new HashMap<Integer, Venue>();
        this.attendeeList = new HashMap<Integer, Attendee>();
        this.reservationsList = new HashMap<Integer, Reservation>();
    }

    // access methods
    public HashMap<Integer, Concert> getConcertList() {
        return concertList;
    }

    public void setConcertList(HashMap<Integer, Concert> concertList) {
        this.concertList = concertList;
    }

    public HashMap<Integer, Venue> getVenueList() {
        return venueList;
    }

    public void setVenueList(HashMap<Integer, Venue> venueList) {
        this.venueList = venueList;
    }

    public HashMap<Integer, Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(HashMap<Integer, Attendee> attendeeList) {
        this.attendeeList = attendeeList;
    }

    public HashMap<Integer, Reservation> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(HashMap<Integer, Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }

    // methods
    @Override
    public String toString() {
        return "ConcertBookingSystem{" +
                "concertList=" + concertList +
                ", venueList=" + venueList +
                ", attendeeList=" + attendeeList +
                ", reservationsList=" + reservationsList +
                '}';
    }

    // add/remove methods
    public Concert addConcert(Concert _concert)
    {
        return this.concertList.put(_concert.getID(), _concert);
    }

    public void removeConcert(int _ID)
    {
        concertList.remove(_ID);
    }

    public void addVenue(Venue _venue)
    {
        venueList.put(_venue.getID(), _venue);
    }

    public void removeVenue(int _ID)
    {
        venueList.remove(_ID);
    }

    public void addAttendee(Attendee _attendee)
    {
        attendeeList.put(_attendee.getID(), _attendee);
    }

    public void removeAttendee(int _ID)
    {
        attendeeList.remove(_ID);
    }

    public void addReservation(Reservation _reservation)
    {
        reservationsList.put(_reservation.getSerialNumber(), _reservation);
    }

    public void removeReservation(int _ID)
    {
        reservationsList.remove(_ID);
    }

    // main methods

    public Reservation makeReservation(Attendee _attendee, Concert _concert, int[] _seats)
    {
        Reservation res = null;
        // check if attendee exists
        if(!attendeeList.containsKey(_attendee.getID()))
        {
            return null;
        }

        // check if concert exists
        if(!concertList.containsKey(_concert.getID()))
        {
            return null;
        }

        // create dummy reservation
        res = new Reservation(1000, 20.0, _attendee.getFirstName()+_attendee.getLastName(), _concert);

        // check if seats exists
        // check if seats are reserved
        for(int _seat: _seats)
        {
            if((_concert.getSeatMap()).containsKey(_seat)) {
                // reserve seats on concert
                // reserve seats on Reservation
                Seat seatToBeReserved = _concert.getSeatMap().get(_seat);
                seatToBeReserved.setReserved(true);
                res.setPrice(res.getPrice()+seatToBeReserved.getCost());
                res.addSeat(seatToBeReserved);

            }
            else
            {
                return null;
            }
        }

        // add reservation to list
        reservationsList.put(res.getSerialNumber(), res);


        // return reservation
        return res;
    }

    public Reservation addSeatToReservation(int _ID, int _seat)
    {
        // get reservation object
        Reservation target = this.reservationsList.get(_ID);

        // get seat object
        Seat seatToBeReserved = target.getReservedConcert().getSeatMap().get(_seat);

        double seatPrice = seatToBeReserved.getCost();

        // add seat to reservation
        target.addSeat(seatToBeReserved);

        // update the price
        target.setPrice(target.getPrice()+seatPrice);
        return target;
    }

    public Reservation removeSeatFromReservation(int _ID, int _seat)
    {
        // get reservation object
        Reservation target = this.reservationsList.get(_ID);

        // get seat object
        Seat seatToBeReserved = target.getReservedConcert().getSeatMap().get(_seat);

        double seatPrice = seatToBeReserved.getCost();

        // add seat to reservation
        target.removeSeat(seatToBeReserved);

        // update the price
        target.setPrice(target.getPrice()-seatPrice);
        return target;
    }

    // returns the concert by a specific name. useful to find the concert from a string from the DB records
    public Concert findConcertByName(String concertName) {
        java.util.Iterator iter = concertList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Concert> element = (Map.Entry<Integer, Concert>) iter.next();
            if (element.getValue().getName().equals(concertName)) {
                return element.getValue();
            } else {

            }

        }
        return null;
    }

}
