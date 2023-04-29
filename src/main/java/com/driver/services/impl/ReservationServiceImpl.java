package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        User user = userRepository3.findById(userId).get();
        if(parkingLot == null || user == null) throw new Exception("Cannot make reservation") ;

        List<Spot> spotList = parkingLot.getSpotList();
        List<Spot> available = new ArrayList<>();
        for(Spot s : spotList){
            if(!s.getOccupied() && s.getPricePerHour() == timeInHours) available.add(s);
        }
        if(available.isEmpty()) throw new Exception("Cannot make reservation") ;

        int price = Integer.MAX_VALUE;
        Spot spot = null;
        for(Spot s : available){
            if(s.getPricePerHour() < price){
                price = s.getPricePerHour();
                spot = s;
            }
        }

        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        reservation.setSpot(spot);
        spot.getReservationList().add(reservation);
        return reservationRepository3.save(reservation);
    }
}
