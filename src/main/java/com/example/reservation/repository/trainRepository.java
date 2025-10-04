package com.example.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reservation.model.Train;

public interface trainRepository extends JpaRepository<Train, Long>{
	Train findByTrainNumber(String trainNumber);
}
