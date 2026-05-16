# FundBridge Future Improvements

This file tracks improvements that would make the FundBridge backend stronger for real users, production deployment, and resume/interview discussion.

## Priority 1: Fix Correctness And Security Issues

- Done: Move all hardcoded secrets and credentials to environment variables:
  - JWT signing secret
  - PostgreSQL username/password
  - Cloudinary cloud name, API key, and API secret
- Still required outside code: Rotate the Cloudinary credentials because they were previously committed in source code.
- Done: Fix investor-to-startup communication:
  - `StartupClient` should call the startup service, not the users service.
  - `InvestmentService.addInvestment` should validate `request.getStartupId()`, not `userId`.
- Done: Add authorization checks so users cannot update another user's password by passing a different user id.
- Done: Configure cookies for production:
  - `Secure=true`
  - `HttpOnly=true`
  - `SameSite`
  - HTTPS-only deployment
- Done: Replace `System.out.println` with structured logging or remove debug console output.
- Done: Remove `spring.jpa.hibernate.ddl-auto=update` from default config. Use database migrations before production.

## Priority 2: Testing And Reliability

- Add service-layer unit tests for users, startups, investors, funding, and investment flows.
- Add controller tests with `MockMvc` or WebTestClient for authentication and authorization behavior.
- Add integration tests using Testcontainers PostgreSQL so tests do not depend on a manually running local database.
- Add tests for important edge cases:
  - duplicate user registration
  - invalid login
  - startup update by non-owner
  - duplicate investment in the same startup
  - invalid or expired JWT
- Add a CI workflow that runs tests on every push or pull request.

## Priority 3: API And Domain Improvements

- Add pagination, sorting, and filtering to list endpoints such as startups and investors.
- Add role-based access control:
  - startup owners can manage only their own startups
  - investors can manage only their own investor profile and portfolio
  - admin-only endpoints for moderation
- Add startup approval or verification flow before startups are publicly visible.
- Improve investment workflow by separating:
  - investor interest
  - startup acceptance/rejection
  - committed investment
  - portfolio entry
- Add richer matching between investors and startups based on industry, stage, location, ticket size, and funding goal.
- Add email verification and forgot-password flow.
- Add refresh-token persistence, rotation, and revocation instead of stateless refresh tokens only.

## Priority 4: Architecture And Production Readiness

- Create separate profiles for `local`, `test`, and `prod`.
- Dockerize each Spring Boot service, not only PostgreSQL and pgAdmin.
- Add service discovery or configurable service URLs instead of hardcoded localhost ports.
- Add centralized exception response format across all services.
- Add request tracing/correlation ids between gateway, users, startup, and investor services.
- Add actuator health checks and readiness checks.
- Add rate limiting for login, registration, and file-upload endpoints.
- Add OpenAPI examples and clear endpoint descriptions for better Swagger documentation.
- Add Flyway or Liquibase migrations for predictable database schema changes.

## Priority 5: Resume-Boosting Features

- Add a recommendation endpoint that returns matched startups for an investor.
- Add a dashboard summary endpoint:
  - total startups
  - total investors
  - funding required
  - investments made
  - top industries
- Add document verification status for pitch decks and startup documents.
- Add notifications for investor interest and startup responses.
- Add audit logs for sensitive actions such as login, password change, investment creation, and document upload.
- Add soft deletes for users, startups, and investor profiles.
- Add basic admin moderation APIs.

## Suggested Resume Talking Points

- Built a Spring Boot microservices backend for a startup-investor marketplace.
- Implemented JWT cookie-based authentication with access and refresh tokens.
- Designed separate services for users, startup profiles, investor profiles, funding details, documents, and investments.
- Used PostgreSQL, Spring Data JPA, DTOs, mappers, validation, custom exceptions, Swagger/OpenAPI, Feign clients, and an API gateway.
- Integrated Cloudinary-based upload support for profile images and startup documents.
- Containerized local PostgreSQL and pgAdmin with Docker Compose.

## Best Next Three Tasks

1. Fix the investor service startup-client bug and investment validation bug.
2. Move all secrets/configuration to environment variables and rotate exposed credentials.
3. Add Testcontainers-based integration tests so the project can be confidently shown during interviews.
