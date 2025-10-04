package com.example.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reservation.model.Reservation;
import com.example.reservation.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepo;

    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }
}
