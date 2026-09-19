# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-09-19

### Added
- **Guest Management API**:
  - `POST /guests/checkin` - Check in guest with validation.
  - `POST /guests/{id}/checkout` - Check out guest with unclaimed parcel verification.
  - `GET /guests/{id}/status` - Query guest check-in status.
- **Parcel Delivery & Pickup API**:
  - `POST /parcels` - Receive and register delivery parcels for active guests.
  - `GET /parcels/guest/{guestId}` - Retrieve all parcels associated with a guest.
  - `POST /parcels/guest/{guestId}/pickup/{parcelId}` - Mark parcels as picked up.
- **Data Persistence**:
  - PostgreSQL integration with Spring Data JPA entities (`Guest`, `Parcel`).
  - Liquibase/SQL seed data for testing.
- **API Documentation**:
  - Springdoc OpenAPI (Swagger UI) integration at `/swagger-ui/index.html`.
- **Testing & Quality Assurance**:
  - Comprehensive unit and integration test suite using MockMvc and H2 in-memory DB.
- **Containerization & CI**:
  - Multi-stage `Dockerfile` for containerized PostgreSQL and application deployment.
  - GitHub Actions CI workflow for automated testing and validation.
