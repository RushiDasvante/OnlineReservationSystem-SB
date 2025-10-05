package com.example.reservation.model;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private int seats;
    private String dateOfJourney;
    private String travelClass;

    // Relations with Train
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
   

	private String username;
    @Column(unique = true)
    private String pnr;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getters & setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

	public Reservation() {
        // generate random 6-digit alphanumeric PNR on creation
        this.pnr = generatePNR();
    }
	 public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}

	public String getPnr() {
		return pnr;
	}



	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
   
    private String generatePNR() {
    	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public Reservation(String passengerName, int seats, String dateOfJourney,
                       String travelClass, String trainNumber, String trainName,
                       String source, String destination,String username) {
        this.passengerName = passengerName;
        this.seats = seats;
        this.dateOfJourney = dateOfJourney;
        this.travelClass = travelClass;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.username=username;
        this.pnr=generatePNR();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getDateOfJourney() {
		return dateOfJourney;
	}

	public void setDateOfJourney(String dateOfJourney) {
		this.dateOfJourney = dateOfJourney;
	}

	public String getTravelClass() {
		return travelClass;
	}

	public void setTravelClass(String travelClass) {
		this.travelClass = travelClass;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
    

    // getters and setters...
}
