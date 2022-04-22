package com.company.service.impl;

import com.company.model.ElevatorModel;
import com.company.model.Passenger;
import com.company.service.ElevatorService;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorServiceImpl implements ElevatorService {
    public static ElevatorModel elevatorModel;

    final Passenger[] passengers;

    public ElevatorServiceImpl(Long maxFloor) {
        elevatorModel = ElevatorModel.builder()
                .maxFloor(maxFloor)
                .currentFloor(1L)
                .direction(true)
                .build();

        this.passengers = new Passenger[5];
    }

    @Override
    public void move() {
        if (!this.isFull()) {
            if (elevatorModel.isDirection()) {
                elevatorModel.incrementCurrentFloor();
            } else {
                elevatorModel.decrementCurrentFloor();
            }
        } else {
            if (elevatorModel.isDirection()) {
                elevatorModel.setCurrentFloor((long) Arrays.stream(passengers)
                        .filter(Objects::nonNull)
                        .filter(x -> x.getFloor() > elevatorModel.getCurrentFloor())
                        .mapToInt(x -> Math.toIntExact(x.getFloor()))
                        .min()
                        .orElse(0));
            } else {
                elevatorModel.setCurrentFloor((long) Arrays.stream(passengers)
                        .filter(Objects::nonNull)
                        .filter(x -> x.getFloor() < elevatorModel.getCurrentFloor())
                        .mapToInt(x -> Math.toIntExact(x.getFloor()))
                        .min()
                        .orElse(0));
            }
        }
    }

    @Override
    public boolean isFull() {
        return Arrays.stream(passengers)
                .filter(Objects::nonNull)
                .filter(x -> x.getFloor() == 0L)
                .findAny()
                .orElse(null) == null;
    }

    @Override
    public Long addNewPassenger(Passenger passenger) {
        Objects.requireNonNull(Arrays.stream(passengers)
                .filter(x -> x.getFloor() == 0L)
                .findFirst()
                .orElse(null))
                .setFloor(passenger.getFloor());

        return 1L;
    }

    @Override
    public Long dropOffPassenger() {
        Long count = Arrays.stream(passengers)
                .filter(Objects::nonNull)
                .filter(x -> x.getFloor().equals(elevatorModel.getCurrentFloor()))
                .count();

        Arrays.stream(passengers)
                .filter(Objects::nonNull)
                .filter(x -> x.getFloor().equals(elevatorModel.getCurrentFloor()))
                .forEach(x -> x.setFloor(0L));

        return count;
    }
}

