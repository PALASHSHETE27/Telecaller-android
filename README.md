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
