package com.vira.prototype;

import com.vira.prototype.persistence.model.Car;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CarTest {

    private static final String name[] = {"bmw","benz","lamborghini","porsche","toyota"};

    private static final String color[] = {"yellow","red","green","blue","brown","white","black","orange"};

    private static final String API_ROOT = "http://localhost:8080/api/cars";

    private Car createRandomCar(){
        Car car = new Car();
        car.setName(name[randomNumber(0,name.length - 1)]);
        car.setColor(color[randomNumber(0,color.length - 1)]);
        car.setYear(randomNumber(1900,2020));
        return car;
    }

    private String createBookAsUri(Car car) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(car)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void createBook(){
        Car car = createRandomCar();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(car)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void createPDF(){
        Response response = RestAssured.get(API_ROOT + "/pdf");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetAllBooks_thenOK() {

        Response response = RestAssured.get(API_ROOT + "/all");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    private static int randomNumber(int min,int max){
        return (int) (Math.random() * max + min);
    }

    private static double randomNumber(double min,double max){
        return Math.random() * max + min;
    }

}
