package com.example.reservation.controller;

import com.example.reservation.model.Train;
import com.example.reservation.repository.trainRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trains")
@CrossOrigin(origins = "*") // allow frontend requests
public class TrainController {

    @Autowired
    private trainRepository trainRepository;

    // Get all trains
    @GetMapping
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // Get train by ID
    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        Optional<Train> train = trainRepository.findById(id);
        return train.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get train by train number (realistic search)
    @GetMapping("/search/{trainNumber}")
    public ResponseEntity<Train> getTrainByNumber(@PathVariable String trainNumber) {
        Optional<Train> train = Optional.ofNullable(trainRepository.findByTrainNumber(trainNumber));
        return train.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add new train
    @PostMapping
    public Train createTrain(@RequestBody Train train) {
        return trainRepository.save(train);
    }

    // Update train
    @PutMapping("/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable Long id, @RequestBody Train updatedTrain) {
        return trainRepository.findById(id).map(train -> {
            train.setTrainNumber(updatedTrain.getTrainNumber());
            train.setTrainName(updatedTrain.getTrainName());
            train.setSource(updatedTrain.getSource());
            train.setDestination(updatedTrain.getDestination());
            //train.setAvailableSeats(updatedTrain.getAvailableSeats());
            //train.setFare(updatedTrain.getFare());
            return ResponseEntity.ok(trainRepository.save(train));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete train
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        return trainRepository.findById(id).map(train -> {
            trainRepository.delete(train);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
