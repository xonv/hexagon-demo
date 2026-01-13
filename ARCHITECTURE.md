# Architecture Documentation

This project follows the **Hexagonal Architecture** (also known as Ports and Adapters) pattern.

## Core Principles

1.  **Domain Centric**: The `domain` package is the core. It MUST NOT depend on any other layer (no spring, no persistence, no web).
2.  **Dependency Rule**: All dependencies point INWARDS. Infrastructure -> Application -> Domain.
3.  **Ports**: The application layer defines interfaces (Ports) that the Infrastructure layer implements (Adapters).

## Package Structure

- `com.example.hexagon.domain`: **Enterprise Logic**. Pure Java.
  - Entities, Value Objects, Domain Exceptions.
- `com.example.hexagon.application`: **Application Logic**.
  - `port.in`: Use Cases / Queries (Input Ports).
  - `port.out`: Repository Interfaces / External Service Interfaces (Output Ports).
  - `service`: Implementation of Use Cases.
- `com.example.hexagon.infrastructure`: **Frameworks & Drivers**.
  - `adapter.in.web`: Controllers, DTOs, Exception Handlers.
  - `adapter.out.persistence`: JPA Entities, Repositories, Adapter Implementations.

## Coding Guidelines

- **Entities**: Do not use JPA annotations in Domain entities. Map them in the Persistence Adapter.
- **Services**: Services implement Input Ports. They orchestrate domain logic but don't contain infrastructure details.
- **Controllers**: Controllers convert HTTP requests to Use Case calls. They handle DTO conversion.
- **Exceptions**: Throw Domain Exceptions from the Application layer. Handle them globally in `GlobalExceptionHandler`.
