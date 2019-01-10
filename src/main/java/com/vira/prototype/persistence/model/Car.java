package com.vira.prototype.persistence.model;


import com.vira.prototype.persistence.dto.CarDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car",schema = "vira")
@Getter
@Setter
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Column(name = "name",columnDefinition = "VARCHAR(20)",length = 20,nullable = false)
    private String name;

    @Basic
    @Column(name = "year",columnDefinition = "INT(4)",length = 4,nullable = false)
    private int year;

    @Basic
    @Column(name = "color",columnDefinition = "VARCHAR(20)",length = 20,nullable = false)
    private String color;

    public Car(long id,String name, int year, String color) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
    }

    public Car() {
    }

    public CarDTO convertToDTO(){
        CarDTO carDTO = new CarDTO();
        carDTO.setId(this.getId());
        carDTO.setName(this.getName());
        carDTO.setColor(this.getColor());
        carDTO.setYear(this.getYear());
        return carDTO;
    }

    public static Car convertToEntity(CarDTO carDTO){
        return new Car(carDTO.getId(),carDTO.getName(),carDTO.getYear(),carDTO.getColor());
    }
}
