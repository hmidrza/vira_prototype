package com.vira.prototype.persistence.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarDTO implements Serializable {

    private long id;
    private String name;
    private String color;
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarDTO carDTO = (CarDTO) o;

        if (year != carDTO.year) return false;
        if (!name.equals(carDTO.name)) return false;
        return color.equals(carDTO.color);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + year;
        return result;
    }
}
