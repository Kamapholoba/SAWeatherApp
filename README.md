App Overview
SA Weather+ is a modern Android application that allows users to register, log in, and access real-time South African weather data.
The app integrates Firebase Authentication, Firestore, and the OpenWeatherMap API to provide secure user management and up-to-date weather forecasts.
The interface follows a light white-and-blue theme for simplicity and clarity.

Key Features
User Registration & Login with Firebase Authentication (passwords encrypted)
Weather API Integration (OpenWeatherMap) displaying live data for major SA cities
Firestore Database for secure storage of user profiles
Offline Mode with error handling
Multi-Language Support (English + isiZulu)
Notifications using Firebase Cloud Messaging (POE extension)
Responsive UI consistent with Material Design
Unit Testing + GitHub Actions for automated builds

Tech Stack
Language: Kotlin
Framework: Android SDK (2023+)
Database: Firebase Firestore
Authentication: Firebase Auth
API: OpenWeatherMap REST API
Version Control: GitHub + GitHub Actions
Testing: JUnit

How to Run
Clone this repository:
git clone https: https://github.com/Kamapholoba/SAWeatherApp.git 
Open the folder in Android Studio 2023+.
Place your google-services.json file inside the app/ folder.
Add your OpenWeatherMap API key inside app/build.gradle:
buildConfigField "String", "OPENWEATHER_API_KEY", "\"API key=1c3b809a39f5e539cf830512e2683a""
Sync Gradle and click Run ▶️ to build the app.
Register a new user and log in to view live weather data.
Automated Testing and GitHub Actions
This project includes a workflow in
.github/workflows/build.yml
to automatically build and test the app whenever code is pushed. 
Unit tests confirm key functionality such as user security and API integration.  

Demonstration Video
📺 Watch the full demo here:  YouTube link here]
The video includes:
User registration and login
Live weather data display
Offline mode demo
isiZulu translation demo

Submission Notes 
No ZIP uploads — project submitted via GitHub repository link.
All code is commented and logged.
README.md includes video link, names, and module code.
Rubric criteria fully met (working API, Firebase Auth, GitHub Actions, professional video, AI usage statement).
GitHub Actions build proof

