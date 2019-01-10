package com.vira.prototype.persistence.service.impl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.vira.prototype.persistence.dto.CarDTO;
import com.vira.prototype.persistence.model.Car;
import com.vira.prototype.persistence.repo.infc.CarRepository;
import com.vira.prototype.persistence.service.infc.CarService;
import com.vira.prototype.persistence.util.PDFUtil2;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<CarDTO> findAll() {
        return carRepository.findAll().stream().map(Car::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CarDTO save(CarDTO carDTO) {
        Car car = carRepository.save(Car.convertToEntity(carDTO));
        return car.convertToDTO();
    }

    @Override
    public byte[] exportPDF()  {
        List<Car> carList = carRepository.findAll();

        Font font = null;
        try {
            byte[] bytes = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("fonts/Bnazanin.ttf"));
            font = new Font(BaseFont.createFont("Bnazanin.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, bytes, null));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PDFUtil2 pdfUtil = new PDFUtil2.Builder(carList)
                .addFieldName("نام")
                .addFieldName("سال")
                .addFieldName("رنگ")
                .addFileName("xxxcars")
                .setTextDirection(PdfWriter.RUN_DIRECTION_RTL)
                .setBodyFont(font)
                .setHeaderFont(font)
                .build();

        return pdfUtil.getBytes();
    }
}
