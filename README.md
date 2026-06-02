# Telecaller Android CRM

![Kotlin](https://img.shields.io/badge/Kotlin-Android-blue)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-green)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-orange)
![Room](https://img.shields.io/badge/Database-Room-yellow)
![Retrofit](https://img.shields.io/badge/Networking-Retrofit-red)
![Firebase](https://img.shields.io/badge/Notifications-FCM-ffca28)

A production-ready Android CRM application built using **Kotlin** and **Jetpack Compose** for managing leads, campaigns, donations, donor records, prasadam distribution, communication templates, and telecaller activities.

The application follows the **MVVM Architecture** pattern and integrates with a dedicated **Node.js + Express + MongoDB** backend to provide a complete end-to-end CRM solution.

---

## Features

### Authentication & Security
- User Registration
- Email OTP Verification
- Secure Login
- Forgot Password
- Password Reset
- JWT-based Authentication
- Session Management

### Lead Management
- Create Leads
- Update Leads
- Delete Leads
- Lead Status Tracking
- Follow-up Activities
- Lead Activity History

### Campaign Management
- Create Campaigns
- View Campaigns
- Campaign Tracking

### Donation & Donor Management
- Record Donations
- Donation History
- Donor Profiles
- Donor Search
- Donation Analytics

### Prasadam Management
- Create Prasadam Records
- View Prasadam History
- User-wise Tracking

### Communication Tools
- Message Templates
- Reusable Outreach Messages

### Dashboard & Analytics
- Lead Statistics
- Activity Tracking
- Performance Insights

### User Features
- Profile Management
- Notification Settings
- Language & Region Settings
- Firebase Push Notifications

---

## Tech Stack

### Frontend
- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose

### Architecture
- MVVM
- Repository Pattern
- ViewModels
- State Management

### Networking
- Retrofit
- OkHttp
- REST APIs

### Local Storage
- Room Database

### Notifications
- Firebase Cloud Messaging (FCM)

---

## Backend Integration

This Android application is powered by a dedicated backend built using Node.js, Express.js, MongoDB Atlas, Cloudinary, JWT Authentication, and Brevo Email Services.

### Backend Repository

🔗 https://github.com/PALASHSHETE27/Telecaller-backend

### Production API

```text
https://telecaller-bn20.onrender.com
```

---

## System Architecture

```text
Android Application
        │
        ▼
 Jetpack Compose UI
        │
        ▼
     ViewModels
        │
        ▼
    Repositories
        │
 ┌──────┴──────┐
 ▼             ▼
Room DB    Retrofit APIs
                  │
                  ▼
        Telecaller Backend
       (Node.js + Express)
                  │
      ┌───────────┼───────────┐
      ▼           ▼           ▼
 MongoDB     Cloudinary     Brevo
   Atlas       Storage      Email
```

---

## Project Structure

```text
app/
├── data/
│   ├── local/
│   ├── repository/
│   └── model/
│
├── network/
├── notifications/
├── session/
├── ui/
│   ├── components/
│   ├── screens/
│   └── theme/
│
├── utils/
├── viewmodel/
└── MainActivity.kt
```

---

## Modules

- Authentication
- Dashboard
- Lead Management
- Campaign Management
- Donation Management
- Donor Management
- Prasadam Management
- Message Templates
- Call Statistics
- Profile Management
- Settings
- Notifications
- Offline Storage

---

## Installation

Clone the repository:

```bash
git clone https://github.com/PALASHSHETE27/Telecaller-Android.git
```

Navigate to the project:

```bash
cd Telecaller-Android
```

Open the project in Android Studio and run it on an emulator or physical device.

---

## Backend Repository

GitHub:
https://github.com/PALASHSHETE27/Telecaller-backend

---

## Author

**Palash Shete**

Android Developer | Mobile Application Developer | Full Stack Developer

Built with Kotlin, Jetpack Compose, MVVM Architecture, Retrofit, Room Database, Firebase Cloud Messaging, and Node.js Backend Services.

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 07 57 PM" src="https://github.com/user-attachments/assets/e2f1f087-d4c9-4fb9-be64-7beb19435b8a" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 15 23 PM" src="https://github.com/user-attachments/assets/44761caf-baf5-4914-8c60-3d44d6669e75" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 15 35 PM" src="https://github.com/user-attachments/assets/64eda8d6-0421-4a81-a4b5-1bf3db16f7ad" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 13 38 PM" src="https://github.com/user-attachments/assets/05543cca-7eff-4942-8707-a414fe071c00" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 08 28 PM" src="https://github.com/user-attachments/assets/1f65adba-94ff-483c-b26c-39e4bd3e3bc0" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 09 09 PM" src="https://github.com/user-attachments/assets/51c3f8de-1962-4b70-a8a4-cc24193ed9ec" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 08 59 PM" src="https://github.com/user-attachments/assets/6472d221-9805-4794-9391-a46030e3d115" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 09 21 PM" src="https://github.com/user-attachments/assets/fffd2658-6db1-4078-becd-563ade5f4ce6" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 09 50 PM" src="https://github.com/user-attachments/assets/c4e5085e-50da-4231-98c7-432b13ae8178" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 10 00 PM" src="https://github.com/user-attachments/assets/c7286918-3101-44a4-9e0a-cba54bbe809d" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 10 50 PM" src="https://github.com/user-attachments/assets/cd663951-198d-440e-aa88-a5c899ed5842" />

<img width="258" height="528" alt="Screenshot 2026-06-02 at 6 10 36 PM" src="https://github.com/user-attachments/assets/4ba42356-81d4-4fdc-8ef8-abf549e96138" />

