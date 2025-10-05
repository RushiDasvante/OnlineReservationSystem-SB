package com.example.reservation.controller;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.Train;
import com.example.reservation.model.User;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.UserRepository;
import com.example.reservation.repository.trainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private trainRepository trainRepository;

//    @GetMapping
//    public List<Reservation> getAll() {
//        return reservationRepository.findAll();
//    }

    @PostMapping
    public Reservation book(@RequestBody Reservation req, @RequestParam String username) {
        // Find the train
        Train train = trainRepository.findByTrainNumber(req.getTrainNumber());
        if (train == null) throw new RuntimeException("Train not found!");
        if (req.getSeats() > train.getAvailableSeats()) throw new RuntimeException("Not enough seats available!");

        // Reduce train seats
        train.setAvailableSeats(train.getAvailableSeats() - req.getSeats());
        trainRepository.save(train);

        // Find the user
        User user = userRepository.findByUsername(username);

        // Create reservation using correct username
        Reservation reservation = new Reservation(
            req.getPassengerName(),
            req.getSeats(),
            req.getDateOfJourney(),
            req.getTravelClass(),
            train.getTrainNumber(),
            train.getTrainName(),
            train.getSource(),
            train.getDestination(),
            username // use logged-in username
        );
        reservation.setUser(user); // set the User entity to populate user_id

        return reservationRepository.save(reservation);
    }

    @GetMapping
    public List<Reservation> getUserReservations(@RequestParam String username) {
        return reservationRepository.findByUsername(username);
    }



//    @GetMapping("/reservations")
//    public List<Reservation> getAllReservations() {
//        return reservationRepository.findAll(); // returns PNR with each reservation
//    }

 // Cancel reservation by PNR
    @DeleteMapping("/{pnr}")
    public ResponseEntity<String> cancelReservation(@PathVariable String pnr) {
        Optional<Reservation> res = reservationRepository.findByPnr(pnr);
        if (res.isPresent()) {
            reservationRepository.delete(res.get());
            return ResponseEntity.ok("Cancelled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PNR not found");
        }
    }



}
