const API_URL = "http://localhost:8080/api"; // Spring Boot backend URL
async function login() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  if (!username || !password) {
    document.getElementById("login-msg").innerText = "❌ Please enter username & password!";
    return;
  }

  try {
    const res = await fetch(`${API_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (res.ok) {
	

      const data = await res.json();
	  localStorage.setItem("username", username); // correct variable
      window.location.href = "dashboard.html"; // make sure this file exists
    } else {
      const errText = await res.text();
      document.getElementById("login-msg").innerText = "❌ " + errText;
    }
  } catch (err) {
    document.getElementById("login-msg").innerText = "❌ Error: " + err.message;
  }
}




function logout() {
  localStorage.removeItem("user");
  window.location.href = "index.html";
}
// Cancel reservation by PNR
async function cancelReservation() {
  const pnr = document.getElementById("pnr").value;

  if (!pnr) {
    document.getElementById("cancel-msg").innerText = "❌ Please enter PNR!";
    return;
  }

  try {
    const res = await fetch(`${API_URL}/reservations/${pnr}`, {
      method: "DELETE"
    });

    if (res.ok) {
      document.getElementById("cancel-msg").innerText =
        "✅ Reservation cancelled successfully!";
      document.getElementById("pnr").value = "";
    } else {
      const err = await res.text();
      document.getElementById("cancel-msg").innerText = "❌ Failed: " + err;
    }
  } catch (error) {
    document.getElementById("cancel-msg").innerText = "❌ Error: " + error.message;
  }
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
  const trainNumber = document.getElementById("trainSelect").value;
  const passengerName = document.getElementById("passengerName").value;
  const seats = document.getElementById("seats").value;
  const dateOfJourney = document.getElementById("dateOfJourney").value;
  const travelClass = document.getElementById("travelClass").value;
  const username = localStorage.getItem("user"); // get current logged-in user

  if (!trainNumber || !passengerName || !seats || !dateOfJourney) {
    document.getElementById("book-msg").innerText = "❌ Please fill all fields!";
    return;
  }

  const res = await fetch(`${API_URL}/reservations?username=${username}`, { // send username as query param
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ trainNumber, passengerName, seats, dateOfJourney, travelClass })
  });

  if (res.ok) {
    const data = await res.json(); // backend should return reservation with PNR
    document.getElementById("book-msg").innerText = "✅ Ticket booked successfully!";
    document.getElementById("pnr-display").innerText = "Your PNR: " + (data.pnr || generatePNR());

    // Clear fields
    document.getElementById("seats").value = "";
    document.getElementById("passengerName").value = "";
    document.getElementById("dateOfJourney").value = "";
    document.getElementById("travelClass").selectedIndex = 0;

    loadTrains(); // refresh dropdown & table
  } else {
    const err = await res.text();
    document.getElementById("book-msg").innerText = "❌ Booking failed: " + err;
    document.getElementById("pnr-display").innerText = "";
  }
}

// Load reservations
async function loadReservations() {
	const username = localStorage.getItem("username");
	if (!username) {
	    document.getElementById("reservation-list").innerHTML = 
	        `<tr><td colspan="5">User not logged in</td></tr>`;
	    return;
	}


  try {
    let res = await fetch(`${API_URL}/reservations?username=${username}`);
    if (!res.ok) throw new Error("Failed to fetch");

    let reservations = await res.json();
    let html = "";
    reservations.forEach(r => {
      html += `<tr>
        <td>${r.id}</td>
        <td>${r.pnr}</td>
        <td>${r.trainNumber}</td>
        <td>${r.passengerName}</td>
        <td>${r.seats}</td>
      </tr>`;
    });
    document.getElementById("reservation-list").innerHTML = html;
  } catch (err) {
    console.error(err);
    document.getElementById("reservation-list").innerHTML = 
        `<tr><td colspan="5">Failed to load reservations</td></tr>`;
  }
}

loadReservations();

// Auto load where needed
if (document.getElementById("train-list")) loadTrains();
if (document.getElementById("reservation-list")) loadReservations();
if (document.getElementById("user-name")) document.getElementById("user-name").innerText = localStorage.getItem("user");
