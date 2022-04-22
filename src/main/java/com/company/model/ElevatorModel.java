package com.company.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorModel {
    final Long maxFloor;
    Long currentFloor;
    boolean direction; //true - up : false - down

    public void incrementCurrentFloor() {
        currentFloor++;
    }

    public void decrementCurrentFloor() {
        currentFloor--;
    }
}
