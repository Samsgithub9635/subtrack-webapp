🚀 SubTrack - Subscription Manager
A simple yet powerful web application to track all your subscriptions, free trials, and recurring payments. Never get charged unexpectedly again!

🎯 About The Project
This project was built to solve a common problem: forgetting to cancel free trials before they convert to paid subscriptions. SubTrack provides a clean dashboard to see all upcoming billing dates and highlights subscriptions that need immediate attention.

The application is built with a modern Java stack and follows an iterative development process, starting with an in-memory data store and evolving towards a full-fledged database-backed application.

✨ Built With
This project is built with a modern, robust technology stack:

Backend:

Spring Boot

Java 17

Maven

Frontend:

Thymeleaf

Tailwind CSS

IDE:

Cursor / Visual Studio Code

🛠️ Getting Started
To get a local copy up and running, follow these simple steps.

Prerequisites
JDK 17 or later

Maven 3.2+

An IDE like IntelliJ IDEA, VS Code, or Cursor

Installation & Running
Clone the repo

git clone https://github.com/your_username/subscription-tracker-app.git

Navigate to the project directory

cd subscription-tracker-app

Run the application

You can run the main method in SubTrackApplication.java directly from your IDE.
Or, you can run it using the Maven wrapper included in the project:

./mvnw spring-boot:run

Open your browser and navigate to http://localhost:8080

⭐ Features
Dashboard: View all subscriptions in a clean, tabular format.

Add Subscriptions: A user-friendly modal to add new services.

Automatically calculates the trial end date based on a start date and trial period.

Visual Alerts: Subscriptions with trials ending soon are highlighted to grab your attention.

In-Memory Data: For Version 1.0, the application will read initial data from an .xlsx file and manage it in memory.
