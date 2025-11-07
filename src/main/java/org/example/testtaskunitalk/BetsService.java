package org.example.testtaskunitalk;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;

@Service
public class BetsService {

    private static final Map<String, DoubleAdder> BETS = new ConcurrentHashMap<>();
    private static final List<String> CARS = Arrays.asList("ferrari", "bmw", "audi", "honda");

    static {
        CARS.forEach((car) -> BETS.put(car, new DoubleAdder()));
    }

    public String putMoneyOnCar(String car, double money) {
        if(car.isBlank() || money <= 0) {
            return "pls add car name and positive amount of money";
        }

        car = car.toLowerCase();
        if(!CARS.contains(car)){
            return "pls add valid car name";
        }

        BETS.get(car).add(money);
        return "success " + car + " - " + money;
    }

    public Object getAllBeats(String car) {
        if(car == null || car.isBlank()){

            return BETS.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        car = car.toLowerCase();
        if(!CARS.contains(car)) {
            return "pls add valid car name";
        }

        return Map.of(car, BETS.get(car));
    }
}
