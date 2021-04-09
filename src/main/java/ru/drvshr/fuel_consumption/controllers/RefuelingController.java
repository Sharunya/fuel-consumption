package ru.drvshr.fuel_consumption.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import ru.drvshr.fuel_consumption.services.RefuelingService;
import ru.drvshr.fuel_consumption.services.dto.Refueling;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/refueling")
public class RefuelingController {

    private final RefuelingService refuelingService;

    public RefuelingController(RefuelingService refuelingService) {this.refuelingService = refuelingService;}

    @PutMapping("/save")
    ResponseEntity<Refueling> save(@RequestBody @Nullable Refueling refueling) {
        Refueling refuelingResponse;
        try {
            refuelingResponse = refuelingService.saveRefueling(refueling);
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(refuelingResponse);
    }

    @GetMapping("/search/{refuelingId}")
    ResponseEntity<Refueling> search(@PathVariable Long refuelingId) {
        try {
            return ResponseEntity.ok(refuelingService.search(refuelingId));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/getall")
    ResponseEntity<List<Refueling>> getAll() {
            return ResponseEntity.ok(refuelingService.getAll());
    }

    @PostMapping
    void create() {
    }

    @PutMapping
    void update() {
    }

    @DeleteMapping("/")
    void delete() {
    }
}
