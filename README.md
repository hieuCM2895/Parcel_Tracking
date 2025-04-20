# 📦 Parcel Tracking Backend

Spring Boot application for hotel receptionists to track guest check-ins and parcel deliveries.

---

## 🚀 Features

- Guest check-in / check-out
- Receive parcels for checked-in guests
- Track parcel status (received / picked up)
- Return unclaimed parcels during guest check-out
- REST API + Swagger documentation
- PostgreSQL database integration
- Docker support
- Unit & Integration testing with JUnit + MockMvc

---

## 🧰 Tech Stack

- Java 17
- Spring Boot 3.1.4
- Spring Data JPA
- PostgreSQL
- Lombok
- Springdoc OpenAPI
- Gradle
- Docker
- JUnit 5

---

## ▶️ How to Run

### 1. Start PostgreSQL with Docker

```bash
docker build -t parcel-postgres .
docker run -d -p 5432:5432 --name parcel-db parcel-postgres
```

Or use your local PostgreSQL and set up:

```
jdbc:postgresql://localhost:5432/parcel_db
username: postgres
password: your_password
```

---

### 2. Run Spring Boot App

```bash
./gradlew bootRun
```

---

### 3. Access Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

Use this to test and explore the API.

---

## 📚 API Overview

### Guest APIs

- `POST /guests/checkin` → Check-in a guest
- `POST /guests/{id}/checkout` → Check-out
- `GET /guests/{id}/status` → Check if guest is currently checked-in

### Parcel APIs

- `POST /parcels` → Receive a parcel
- `GET /parcels/guest/{guestId}` → Get guest's parcels
- `POST /parcels/guest/{guestId}/pickup/{parcelId}` → Mark parcel as picked up

---

## 🧪 Sample SQL (`data.sql`)

```sql
INSERT INTO guests (id, full_name, check_in_time, checked_in) VALUES ('1', 'John Doe', now(), true);
```

---

## 📦 Error Response Format

```json
{
  "error": "guest-404",
  "message": "Guest not found",
  "detail": "Guest with ID 999 not found"
}
```

---

Enjoy building ✨# Parcel Tracking
