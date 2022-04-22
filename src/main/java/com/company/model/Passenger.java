package com.company.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Passenger implements Comparator<Passenger> {
    Long floor;

    @Override
    public int compare(Passenger o1, Passenger o2) {
        return o1.getFloor().compareTo(o2.getFloor());
    }

    @Override
    public String toString() {
        return floor.toString();
    }
}
