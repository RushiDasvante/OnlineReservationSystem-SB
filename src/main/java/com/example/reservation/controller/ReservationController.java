package com.example.reservation.controller;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.Train;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.trainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private trainRepository trainRepository;

    @GetMapping
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @PostMapping
    public Reservation book(@RequestBody Reservation req) {
        Train train = trainRepository.findByTrainNumber(req.getTrainNumber());

        if (train == null) {
            throw new RuntimeException("Train not found!");
        }

        if (req.getSeats() > train.getAvailableSeats()) {
            throw new RuntimeException("Not enough seats available!");
        }

        // reduce seats
        train.setAvailableSeats(train.getAvailableSeats() - req.getSeats());
        trainRepository.save(train);

        // store reservation
        Reservation reservation = new Reservation(
                req.getPassengerName(),
                req.getSeats(),
                req.getDateOfJourney(),
                req.getTravelClass(),
                train.getTrainNumber(),
                train.getTrainName(),
                train.getSource(),
                train.getDestination()
        );

        return reservationRepository.save(reservation);
    }

}
