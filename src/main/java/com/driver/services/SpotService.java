package com.driver.services;

import com.driver.model.Spot;
import org.springframework.stereotype.Service;

@Service
public interface SpotService {
    Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour);

    void deleteSpot(int spotId);

    Spot updateSpot(int parkingLotId, int spotId, int pricePerHour);
}
