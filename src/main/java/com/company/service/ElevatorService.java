package com.company.service;

import com.company.model.Passenger;

public interface ElevatorService {
    void move();

    boolean isFull();

    Long addNewPassenger(Passenger passenger);

    Long dropOffPassenger();
}
