package com.driver.services;

import com.driver.model.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    Payment pay(Integer reservationId, int amountSent, String mode) throws Exception;
}
