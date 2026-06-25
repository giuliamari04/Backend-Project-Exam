# EventHub Backend

## Description

**EventHub Backend** is a backend application developed with **Spring Boot** for managing online and physical events. It provides a complete user management system with JWT authentication and role-based authorization (USER, ORGANIZER, and ADMIN). Users can register, book events, write reviews, receive notifications, and manage their profiles, while organizers can create and manage events. The application also integrates external APIs to automatically retrieve event coordinates through geocoding and display real-time weather information for physical events. The project follows a layered architecture using Controllers, Services, Repositories, and DTOs.

## Technologies

* Java 21
* Spring Boot
* Spring Security
* JWT
* PostgreSQL
* Hibernate
* Lombok
* Maven

## Features

* JWT Authentication (Login/Register)
* User Management
* Category Management
* Online Events
* Physical Events
* Event Booking
* Reviews
* Notifications
* Weather API Integration
* Geocoding API Integration

## Architecture

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## External APIs

* Open-Meteo
* OpenStreetMap Nominatim

## Security

* JWT Authentication
* USER Role
* ORGANIZER Role
* ADMIN Role

## Running the Project

Start the application using:

```bash
mvn spring-boot:run
```

or run the `EventhubApplication` class directly from your IDE.

## Postman Collection

```
postman\EventHub Backend Exam.postman_collection.json
```

## Author

**Giulia Mariano**
