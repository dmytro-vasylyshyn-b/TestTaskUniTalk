package org.example.testtaskunitalk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestTaskUniTalkApplicationTests {

    private BetsService betsService;

    @BeforeEach
    void setup() {
        betsService = new BetsService();
    }

    @Test
    void testGetAllBeats(){
        betsService.putMoneyOnCar("AuDi", 200.0);
        betsService.putMoneyOnCar("BmW", 300.0);

        Map<String, DoubleAdder> response = (Map<String, DoubleAdder>) betsService.getAllBeats(null);
        assertEquals(300.0, response.get("bmw").doubleValue());
        assertEquals(200.0, response.get("audi").doubleValue());
        assertEquals(0.0, response.get("ferrari").doubleValue());
        assertEquals(0.0, response.get("honda").doubleValue());
    }

    @Test
    void testPutMoneyOnCar() {
        betsService.putMoneyOnCar("AuDi", 200.0);
        betsService.putMoneyOnCar("AuDi", 300.0);

        Map<String, DoubleAdder> totals = (Map<String, DoubleAdder>) betsService.getAllBeats("audi");
        assertEquals(700.0, totals.get("audi").doubleValue());
    }

    @Test
    void testErrorInvalidAmount() {
        String response = betsService.putMoneyOnCar("Honda", -100.0);
        assertEquals("pls add car name and positive amount of money", response);
    }

    @Test
    void testErrorInvalidCar() {
        String response = betsService.putMoneyOnCar("Mercedes", 100.0);
        assertEquals("pls add valid car name", response);

        Object getResponse = betsService.getAllBeats("Tesla");
        assertEquals("pls add valid car name", getResponse);
    }
}
