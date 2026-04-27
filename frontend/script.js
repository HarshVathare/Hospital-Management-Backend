// Configuration
const API_BASE = "http://localhost:8080/api";
let currentUser = null;
let accessToken = localStorage.getItem("accessToken") || null;
let refreshToken = localStorage.getItem("refreshToken") || null;

// DOM Elements
const authContainer = document.getElementById("authContainer");
const patientDashboard = document.getElementById("patientDashboard");
const adminDashboard = document.getElementById("adminDashboard");
const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");

// Initialize
document.addEventListener("DOMContentLoaded", function () {
  setupEventListeners();
  checkAuthStatus();
});

function setupEventListeners() {
  loginForm.addEventListener("submit", handleLogin);
  registerForm.addEventListener("submit", handleRegister);
  document
    .getElementById("bookAppointmentForm")
    ?.addEventListener("submit", handleBookAppointment);

  // Navigation
  document.querySelectorAll(".nav-item").forEach((item) => {
    item.addEventListener("click", (e) => {
      e.preventDefault();
      const section = item.dataset.section;
      switchSection(section, item.closest(".dashboard-container"));
    });
  });

  // Search & Filters
  document
    .getElementById("doctorSearch")
    .addEventListener("input", debounce(filterDoctors, 300));
  document
    .getElementById("specialtyFilter")
    .addEventListener("change", filterDoctors);
}

// Auth Functions
async function handleLogin(e) {
  e.preventDefault();
  showLoading("login");

  try {
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    const response = await fetch(`${API_BASE}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    const data = await response.json();

    if (!response.ok) throw new Error(data.message || "Login failed");

    accessToken = data.accessToken;
    refreshToken = data.refreshToken;
    currentUser = data;

    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);

    hideLoading("login");
    showDashboard();
  } catch (error) {
    hideLoading("login");
    showToast("Login failed: " + error.message, "error");
  }
}

async function handleRegister(e) {
  e.preventDefault();
  showLoading("reg");

  try {
    const userData = {
      name: document.getElementById("regName").value,
      email: document.getElementById("regEmail").value,
      password: document.getElementById("regPassword").value,
      age: parseInt(document.getElementById("regAge").value) || null,
      gender: document.getElementById("regGender").value || null,
      medicalHistory: document.getElementById("regMedicalHistory").value || "",
    };

    const response = await fetch(`${API_BASE}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData),
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || "Registration failed");
    }

    hideLoading("reg");
    showToast("Registration successful! Please login.");
    showLogin();
  } catch (error) {
    hideLoading("reg");
    showToast("Registration failed: " + error.message, "error");
  }
}

function showDashboard() {
  authContainer.style.display = "none";

  if (currentUser.role === "ADMIN") {
    adminDashboard.classList.remove("hidden");
    document.getElementById("adminName").textContent = currentUser.email;
    loadAdminDashboard();
  } else {
    patientDashboard.classList.remove("hidden");
    document.getElementById("patientName").textContent =
      currentUser.name || currentUser.email;
    loadPatientHome();
  }
}

function checkAuthStatus() {
  if (accessToken && refreshToken) {
    // Verify token and show dashboard
    verifyToken()
      .then((valid) => {
        if (valid) {
          showDashboard();
        }
      })
      .catch(() => {
        logout();
      });
  }
}

async function verifyToken() {
  const response = await fetch(`${API_BASE}/auth/verify`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return response.ok;
}

function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  currentUser = null;
  accessToken = null;
  refreshToken = null;

  authContainer.style.display = "block";
  patientDashboard.classList.add("hidden");
  adminDashboard.classList.add("hidden");
  showLogin();
  document.getElementById("loginForm").reset();
}

// Navigation
function switchSection(sectionId, dashboard) {
  // Update active nav
  dashboard
    .querySelectorAll(".nav-item")
    .forEach((item) => item.classList.remove("active"));
  dashboard
    .querySelector(`[data-section="${sectionId}"]`)
    .classList.add("active");

  // Switch content
  dashboard
    .querySelectorAll(".content-section")
    .forEach((sec) => sec.classList.remove("active"));
  document.getElementById(sectionId).classList.add("active");
}

// Patient Features
let allDoctors = [];

async function loadAllDoctors() {
  try {
    const response = await fetch(`${API_BASE}/patients/docters`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    const doctors = await response.json();
    allDoctors = doctors;
    renderDoctors(doctors);
  } catch (error) {
    console.error("Failed to load doctors:", error);
    document.getElementById("doctorsList").innerHTML =
      '<div class="loading">Failed to load doctors</div>';
  }
}

function renderDoctors(doctors) {
  const container = document.getElementById("doctorsList");
  if (doctors.length === 0) {
    container.innerHTML =
      '<div class="empty-state"><p>No doctors found</p></div>';
    return;
  }

  container.innerHTML = doctors
    .map(
      (doctor) => `
        <div class="doctor-card" onclick="bookDoctor(${doctor.id})">
            <div class="doctor-avatar">
                <i class="fas fa-user-md"></i>
            </div>
            <div class="doctor-info">
                <h3>${doctor.name}</h3>
                <div class="doctor-specialty">${doctor.specialization}</div>
                <div class="doctor-details">
                    <span><i class="fas fa-briefcase"></i> ${doctor.experianceInYears} yrs exp</span>
                    <span><i class="fas fa-circle" style="color: ${doctor.availibility_stutus === "AVAILABLE" ? "#27ae60" : "#e74c3c"}"></i> ${doctor.availibility_stutus}</span>
                </div>
            </div>
            <button class="book-btn" onclick="event.stopPropagation(); bookDoctor(${doctor.id})">
                Book Appointment
            </button>
        </div>
    `,
    )
    .join("");
}

function filterDoctors() {
  const search = document.getElementById("doctorSearch").value.toLowerCase();
  const specialty = document
    .getElementById("specialtyFilter")
    .value.toLowerCase();

  const filtered = allDoctors.filter((doctor) => {
    const matchesSearch =
      doctor.name.toLowerCase().includes(search) ||
      doctor.specialization.toLowerCase().includes(search);
    const matchesSpecialty =
      !specialty || doctor.specialization.toLowerCase().includes(specialty);
    return matchesSearch && matchesSpecialty;
  });

  renderDoctors(filtered);
}

async function loadPatientHome() {
  loadAllDoctors();
  loadAppointments();
}

async function loadAppointments() {
  try {
    const response = await fetch(`${API_BASE}/patients/appointments`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    const appointments = await response.json();
    renderAppointments(appointments);
  } catch (error) {
    console.error("Failed to load appointments");
  }
}

function renderAppointments(appointments) {
  const container = document.getElementById("appointmentsList");
  if (!appointments || appointments.length === 0) {
    container.innerHTML = `
            <div class="empty-state">
                <i class="fas fa-calendar-times"></i>
                <h3>No appointments yet</h3>
                <p>Book your first appointment with a doctor</p>
                <button class="btn-primary" onclick="switchSection('doctors', patientDashboard)">Find Doctors</button>
            </div>
        `;
    return;
  }

  container.innerHTML = appointments
    .map(
      (apt) => `
        <div class="appointment-item">
            <div>
                <h4>${apt.docter?.name || "N/A"}</h4>
                <p>${new Date(apt.appointment_Date).toLocaleString()}</p>
                <span class="status-badge">${apt.status || "Scheduled"}</span>
            </div>
            <div>
                <button class="btn-outline">View</button>
            </div>
        </div>
    `,
    )
    .join("");
}

async function bookDoctor(doctorId) {
  document.getElementById("appointmentDoctor").value = doctorId;
  openBookAppointmentModal();
}

async function handleBookAppointment(e) {
  e.preventDefault();
  const doctorId = document.getElementById("appointmentDoctor").value;
  const dateTime = document.getElementById("appointmentDateTime").value;

  try {
    const response = await fetch(`${API_BASE}/patients/appointments`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
      body: JSON.stringify({
        appointment_Date: dateTime,
        docter_Id: doctorId,
      }),
    });

    if (response.ok) {
      showToast("Appointment booked successfully!");
      closeModal("bookAppointmentModal");
      loadAppointments();
    } else {
      throw new Error("Booking failed");
    }
  } catch (error) {
    showToast("Failed to book appointment", "error");
  }
}

async function loadProfile() {
  try {
    const response = await fetch(`${API_BASE}/patients/profile`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    const profile = await response.json();

    document.getElementById("profileInfo").innerHTML = `
            <div class="profile-row">
                <span class="profile-label">Name:</span>
                <span>${profile.name}</span>
            </div>
            <div class="profile-row">
                <span class="profile-label">Email:</span>
                <span>${profile.email}</span>
            </div>
            <div class="profile-row">
                <span class="profile-label">Age:</span>
                <span>${profile.age || "N/A"}</span>
            </div>
            <div class="profile-row">
                <span class="profile-label">Gender:</span>
                <span>${profile.gender || "N/A"}</span>
            </div>
        `;
  } catch (error) {
    console.error("Failed to load profile");
  }
}

// Admin Features
async function loadAdminDashboard() {
  try {
    const response = await fetch(`${API_BASE}/admin/dashboard`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    const stats = await response.json();

    document.getElementById("adminTotalPatients").textContent =
      stats.totalPatients || 0;
    document.getElementById("adminTotalDoctors").textContent =
      stats.totalDocters || 0;
    document.getElementById("adminTotalAppointments").textContent =
      stats.totalAppointments || 0;
  } catch (error) {
    console.error("Failed to load admin dashboard");
  }
}

// Utility Functions
function showLogin() {
  document
    .querySelectorAll(".auth-view")
    .forEach((view) => view.classList.remove("active"));
  document.getElementById("loginView").classList.add("active");
}

function showRegister() {
  document
    .querySelectorAll(".auth-view")
    .forEach((view) => view.classList.remove("active"));
  document.getElementById("registerView").classList.add("active");
}

function showLoading(formId) {
  document.getElementById(`${formId}BtnText`).style.display = "none";
  document.getElementById(`${formId}Spinner`).classList.remove("hidden");
}

function hideLoading(formId) {
  document.getElementById(`${formId}BtnText`).style.display = "inline";
  document.getElementById(`${formId}Spinner`).classList.add("hidden");
}

function showToast(message, type = "success") {
  const toast = document.getElementById("toast");
  toast.textContent = message;
  toast.className = `toast ${type} show`;
  setTimeout(() => toast.classList.remove("show"), 4000);
}

function openBookAppointmentModal() {
  document.getElementById("bookAppointmentModal").classList.add("active");
  loadDoctorsForBooking();
}

function closeModal(modalId) {
  document.getElementById(modalId).classList.remove("active");
}

async function loadDoctorsForBooking() {
  // Populate doctors dropdown - same as loadAllDoctors
  const doctors = await fetch(`${API_BASE}/patients/docters`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  }).then((r) => r.json());

  const select = document.getElementById("appointmentDoctor");
  select.innerHTML =
    '<option value="">Select Doctor</option>' +
    doctors
      .map(
        (d) =>
          `<option value="${d.id}">${d.name} - ${d.specialization}</option>`,
      )
      .join("");
}

function debounce(func, wait) {
  let timeout;
  return function (...args) {
    clearTimeout(timeout);
    timeout = setTimeout(() => func.apply(this, args), wait);
  };
}
