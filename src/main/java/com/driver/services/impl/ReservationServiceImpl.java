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

        try{
            Reservation reservation = new Reservation();
            User user = null;
            user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = null;
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spotList = parkingLot.getSpotList();

            if(user == null && parkingLot == null){
               // reservation.setSpot(null);
                //reservationRepository3.save(reservation);
                throw new Exception("Cannot make reservation");
            }

            //find spot with minimum price                              //spot can be reserved if SpotType having wheels equals or greater than passed wheels
            Spot spotRequired = null;                                   //price also we should have minimum one
            int minPrice = Integer.MAX_VALUE;
            for(Spot spot : spotList){
                if(!spot.getOccupied()) {
                    if (getNumberOfVehicles(spot.getSpotType()) >= numberOfWheels && spot.getPricePerHour() * timeInHours < minPrice) {
                        minPrice = spot.getPricePerHour() * timeInHours;
                        spotRequired = spot;
                    }
                }
            }
            if(spotRequired == null){
//                reservation.setSpot(null);
//                reservationRepository3.save(reservation);
                throw new Exception("Cannot make reservation");
            }
            git
            //this part specifies that user, parkingLot, parkingSpot are valid & available
            reservation.setNumberOfHours(timeInHours);
            reservation.setUser(user);
            reservation.setSpot(spotRequired);

            //setting user attributes
            List<Reservation> reservationList = user.getReservationList();
            if(reservationList == null){
                reservationList = new ArrayList<>();
            }
            reservationList.add(reservation);
            user.setReservationList(reservationList);


            //setting spot attributes

            List<Reservation> spotReservations = spotRequired.getReservationList();
            if(spotReservations == null){
                spotReservations = new ArrayList<>();
            }
            spotReservations.add(reservation);
            spotRequired.setReservationList(spotReservations);
            spotRequired.setOccupied(false);

            userRepository3.save(user);
            spotRepository3.save(spotRequired);
            return reservation;

        }
        catch (Exception e){
            return null;
        }
    }

    public int getNumberOfVehicles(SpotType spotType){
        if( spotType.equals(SpotType.TWO_WHEELER)) return 2;
        else if(spotType.equals(SpotType.FOUR_WHEELER)) return 4;
        return Integer.MAX_VALUE;
    }
}
