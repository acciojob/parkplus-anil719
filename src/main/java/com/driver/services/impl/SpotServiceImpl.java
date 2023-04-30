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

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();
        Spot spot = new Spot();
        if(numberOfWheels == 2) spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels == 4) spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);
        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setParkingLot(parkingLot);


        spot.setReservationList(new ArrayList<>());

        List<Spot> spotList = parkingLot.getSpotList();
        if (spotList == null) {
            spotList = new ArrayList<>();
        }
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

        Spot spot = parkingLot.getSpotList().get(spotId);
        spot.setPricePerHour(pricePerHour);
        Spot saved = spotRepository.save(spot);
        return saved;
    }
}
