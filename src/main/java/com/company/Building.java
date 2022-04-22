package com.company;

import com.company.model.Passenger;
import com.company.service.ElevatorService;
import com.company.service.impl.ElevatorServiceImpl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Building {
    final static Random random = new Random();
    static ElevatorService elevatorService;
    static Long floors;
    static List<Passenger>[] passengerOnFloor;

    private static Long getRandomCount(Long min, Long max) {
        return min + random.nextInt((int) (max - min + 1));
    }

    private static void fillPassenger() {
        for (int i = 0; i < passengerOnFloor.length; i++) {
            passengerOnFloor[i] = new ArrayList<>();

            saveNewPassenger(getRandomCount(0L, 10L));
        }
    }

    private static void saveNewPassenger(Long count) {
        for (int j = 0; j < count; j++) {
            passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].add(new Passenger(getRandomCount(0L, floors)));
        }
    }

    private static void showStepsInfo(int step) {
        System.out.println("*** Step " + step + " ***");
    }

    private static Long addNewPassengerElevator() {
        Long count = 0L;

        for (int i = 0; i < passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].size() && !elevatorService.isFull(); i++) {
            if (ElevatorServiceImpl.elevatorModel.isDirection()) {
                if (passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].get(i).getFloor() > ElevatorServiceImpl.elevatorModel.getCurrentFloor()) {
                    count++;
                    elevatorService.addNewPassenger(passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].get(i));
                }
            } else {
                if (passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].get(i).getFloor() < ElevatorServiceImpl.elevatorModel.getCurrentFloor()) {
                    count++;
                    elevatorService.addNewPassenger(passengerOnFloor[(int) (ElevatorServiceImpl.elevatorModel.getCurrentFloor() - 1)].get(i));
                }
            }
        }
        return count;
    }

    private static void start() {
        floors = getRandomCount(5L, 20L);
        elevatorService = new ElevatorServiceImpl(floors);
        passengerOnFloor = new List[Math.toIntExact(floors)];

        Long countAddNewPassenger;
        Long countRemoveNewPassenger;

        fillPassenger();

        for (int i = 0; i < 10; i++) {
            countRemoveNewPassenger = elevatorService.dropOffPassenger();

            countAddNewPassenger = addNewPassengerElevator();

            if (countAddNewPassenger == 0 && countRemoveNewPassenger == 0) {
                i--;
            } else {
                saveNewPassenger(countRemoveNewPassenger);
                showStepsInfo(i);
            }
            elevatorService.move();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
