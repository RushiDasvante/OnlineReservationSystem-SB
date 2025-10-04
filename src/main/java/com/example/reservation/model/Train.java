package com.example.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String trainNumber;   // e.g. 12009

    private String trainName;
    private String source;
    private String destination;
    private String classType;

    private int availableSeats;   // ✅ new field
    private double fare;          // ✅ new field
    
    private LocalTime departureTime;   // e.g. 08:30
    
    private LocalTime arrivalTime;     // e.g. 14:45

    @ElementCollection
    private List<LocalDate> scheduleDates; // list of available journey dates
    
    public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public List<LocalDate> getScheduleDates() {
		return scheduleDates;
	}
	public void setScheduleDates(List<LocalDate> scheduleDates) {
		this.scheduleDates = scheduleDates;
	}
	
    
    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
}
