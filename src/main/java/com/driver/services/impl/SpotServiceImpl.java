package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotServiceImpl implements SpotService {
    @Autowired
    SpotRepository spotRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setReservationList(new ArrayList<>());

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();
        spot.setParkingLot(parkingLot);
        if(numberOfWheels <= 2) spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels <= 4) spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);

        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(spot);
        // set spot in parking lot
        parkingLot.setSpotList(spotList);
        // save parking lot
        parkingLotRepository.save(parkingLot);
        // spotRepository1.save(spot);
        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository.deleteById(spotId) ;
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();

        Spot spot = null;
        List<Spot> spotList = parkingLot.getSpotList();
        for(Spot spot1 : spotList){
            if(spot1.getId() == spotId){
                spot = spot1;
            }
        }
        spot.setPricePerHour(pricePerHour);
        spotRepository.save(spot);
        parkingLot.setSpotList(spotList);
        return spot;
    }
}
