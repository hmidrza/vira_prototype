package com.vira.prototype.controller.rest;

import com.vira.prototype.persistence.dto.CarDTO;
import com.vira.prototype.persistence.service.infc.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/cars")
public class CarResource {

    @Autowired
    private CarService carService;

    @GetMapping("/all")
    public Iterable findAll() {
        return carService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO create(@RequestBody CarDTO car) {
        return carService.save(car);
    }

    @GetMapping(value = "/pdf",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportPDF(){
        byte[] bytes = carService.exportPDF();
        HttpHeaders httpHeaders = new HttpHeaders();
        String filename = new Date().getTime() + "";
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + new Date().getTime() + ".pdf");
        return new ResponseEntity<>(bytes,httpHeaders,HttpStatus.OK);
    }
}
