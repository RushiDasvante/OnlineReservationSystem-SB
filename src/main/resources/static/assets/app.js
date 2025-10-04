const API_URL = "http://localhost:8080/api"; // Spring Boot backend URL

function login() {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  if (username === "Rushi" && password === "1234") {
    localStorage.setItem("user", username);
    window.location.href = "dashboard.html";
  } else {
    document.getElementById("login-msg").innerText = "❌ Invalid Credentials!";
  }
}

function logout() {
  localStorage.removeItem("user");
  window.location.href = "index.html";
}

function goTo(page) {
  window.location.href = page;
}

// Load trains
async function loadTrains() {
  let res = await fetch(`${API_URL}/trains`);
  let trains = await res.json();
  let html = "";
  trains.forEach(t => {
    html += `<tr>
      <td>${t.trainNumber}</td>
      <td>${t.trainName}</td>
      <td>${t.source}</td>
      <td>${t.destination}</td>
      <td>${t.availableSeats}</td>
      <td>₹${t.fare}</td>
    </tr>`;
  });
  document.getElementById("train-list").innerHTML = html;
}

// Book ticket
async function bookTicket() {
  let trainNumber = document.getElementById("trainNumber").value;
  let passengerName = document.getElementById("passengerName").value;
  let seats = document.getElementById("seats").value;
  let dateOfJourney = document.getElementById("dateOfJourney").value;
  let travelClass = document.getElementById("travelClass").value;

  let res = await fetch(`${API_URL}/reservations`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ trainNumber, passengerName, seats, dateOfJourney, travelClass })
  });

  if (res.ok) {
    document.getElementById("book-msg").innerText = "✅ Ticket booked successfully!";
  } else {
    let err = await res.text();
    document.getElementById("book-msg").innerText = "❌ Failed: " + err;
  }
}

// Load reservations
async function loadReservations() {
  let res = await fetch(`${API_URL}/reservations`);
  let reservations = await res.json();
  let html = "";
  reservations.forEach(r => {
    html += `<tr>
      <td>${r.id}</td>
      <td>${r.trainNumber}</td>
      <td>${r.passengerName}</td>
      <td>${r.seats}</td>
    </tr>`;
  });
  document.getElementById("reservation-list").innerHTML = html;
}

// Auto load where needed
if (document.getElementById("train-list")) loadTrains();
if (document.getElementById("reservation-list")) loadReservations();
if (document.getElementById("user-name")) document.getElementById("user-name").innerText = localStorage.getItem("user");
