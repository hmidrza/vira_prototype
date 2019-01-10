package com.vira.prototype.persistence.service.infc;

import com.vira.prototype.persistence.dto.CarDTO;

import java.util.List;

public interface CarService {

    List<CarDTO> findAll();

    CarDTO save(CarDTO carDTO);

    byte[] exportPDF();

}
