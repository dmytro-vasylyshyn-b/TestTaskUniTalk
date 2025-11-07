package org.example.testtaskunitalk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BetsController {

    private final BetsService betsService;

    public BetsController(BetsService betsService) {
        this.betsService = betsService;
    }

    @PostMapping("/bet")
    public ResponseEntity<String> placeBet(@RequestParam String car, @RequestParam double amount) {
        String response = betsService.putMoneyOnCar(car, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bets")
    public ResponseEntity<Object> getBets(@RequestParam(required = false) String car) {
        Object response = betsService.getAllBeats(car);
        return ResponseEntity.ok(response);
    }
}
