# Spring Boot Library Tracker

> Tracks library adoption status for the Personal Finance Manager project.
> Last updated: 2026-02-11

## Current Dependencies (`build.gradle`)

| Library | Version | Status | Notes |
|---------|---------|--------|-------|
| spring-boot-starter-web | 3.5.10 (BOM) | ACTIVE | Controllers, REST endpoints |
| spring-boot-starter-data-jpa | 3.5.10 (BOM) | ACTIVE | Entities, repositories, JPA auditing |
| spring-boot-starter-security | 3.5.10 (BOM) | ACTIVE | SecurityFilterChain, JWT filter |
| spring-boot-starter-validation | 3.5.10 (BOM) | ACTIVE | @Valid, @NotBlank, @Email, @Size on DTOs |
| spring-boot-starter-test | 3.5.10 (BOM) | MINIMAL | Only contextLoads() test exists |
| mariadb-java-client | 3.3.2 | ACTIVE | JDBC driver configured in application.yaml |
| modelmapper | 3.2.5 | UNUSED | Declared but no usage found in code |
| jjwt-api / impl / jackson | 0.12.3 | ACTIVE | Token generation, parsing, validation |
| springdoc-openapi-starter-webmvc-ui | 2.8.6 | ACTIVE | Swagger UI, OpenAPI 3.0 API documentation |
| lombok | managed | ACTIVE | @Getter, @Builder, @RequiredArgsConstructor across all classes |

---

## Common Spring Boot Libraries - Not Yet Added

Libraries frequently used in Spring Boot projects that may be needed as the project grows.

### Core / Infrastructure

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Spring Boot Actuator | `spring-boot-starter-actuator` | HIGH | Health checks, metrics, monitoring endpoints | NO |
| Spring Boot DevTools | `spring-boot-devtools` | MEDIUM | Hot reload during development | NO |
| Spring Cache | `spring-boot-starter-cache` | MEDIUM | Method-level caching (@Cacheable) | NO |
| Spring AOP | `spring-boot-starter-aop` | LOW | Cross-cutting concerns (logging, auditing) | NO |

### API / Documentation

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| SpringDoc OpenAPI | `org.springdoc:springdoc-openapi-starter-webmvc-ui` | ~~HIGH~~ | Swagger UI, API documentation | YES (v2.8.6) |

### Data / Persistence

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Spring Data Redis | `spring-boot-starter-data-redis` | MEDIUM | Caching, session store, rate limiting | NO |
| Flyway | `org.flywaydb:flyway-core` + `flyway-mysql` | HIGH | Database migration versioning | NO |
| Liquibase | `org.liquibase:liquibase-core` | HIGH | Database migration (alternative to Flyway) | NO |
| QueryDSL | `com.querydsl:querydsl-jpa` | MEDIUM | Type-safe dynamic queries for CQRS read side | NO |

### Security / Auth

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Spring OAuth2 Resource Server | `spring-boot-starter-oauth2-resource-server` | LOW | OAuth2/OIDC integration | NO |
| Spring OAuth2 Client | `spring-boot-starter-oauth2-client` | LOW | Social login (Google, GitHub, etc.) | NO |

### Messaging / Async

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Spring Mail | `spring-boot-starter-mail` | MEDIUM | Email notifications (budget alerts, reports) | NO |
| Spring WebSocket | `spring-boot-starter-websocket` | LOW | Real-time updates | NO |
| Spring Kafka | `spring-boot-starter-kafka` | LOW | Event-driven architecture | NO |
| Spring AMQP (RabbitMQ) | `spring-boot-starter-amqp` | LOW | Message queuing | NO |

### Testing

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Testcontainers | `org.testcontainers:mariadb` | HIGH | Integration tests with real DB | NO |
| REST Assured | `io.rest-assured:rest-assured` | MEDIUM | Fluent API testing | NO |
| Mockito (inline) | included in starter-test | HIGH | Already available, needs test classes | NO |

### Utilities

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| MapStruct | `org.mapstruct:mapstruct` | HIGH | Compile-time DTO mapping (replace ModelMapper) | NO |
| Jackson Datatype JSR310 | included in starter-web | ACTIVE | Java 8+ date/time serialization | AUTO |
| Apache Commons Lang | `org.apache.commons:commons-lang3` | LOW | String/Object utility methods | NO |
| Jasypt | `com.github.ulisesbocchio:jasypt-spring-boot-starter` | MEDIUM | Encrypt sensitive config properties | NO |

### Observability / Logging

| Library | Artifact | Priority | Use Case | Added? |
|---------|----------|----------|----------|--------|
| Micrometer Prometheus | `io.micrometer:micrometer-registry-prometheus` | MEDIUM | Prometheus metrics export | NO |
| Logback (SLF4J) | included in starter-web | ACTIVE | Logging framework | AUTO |

---

## Implementation Log

Track when libraries are added, who added them, and why.

| Date | Library | Action | Reason | PR/Commit |
|------|---------|--------|--------|-----------|
| 2025-02-11 | springdoc-openapi-starter-webmvc-ui (2.8.6) | ADDED | Swagger UI & OpenAPI 3.0 API documentation for all endpoints | - |

---

## Notes

- **ModelMapper** is currently declared but unused. Consider removing it or replacing with **MapStruct** for compile-time safety.
- **spring-boot-starter-test** is included but only has the default contextLoads test. Expand test coverage as features stabilize.
- Priority levels: **HIGH** = likely needed soon, **MEDIUM** = useful as project grows, **LOW** = only if specific feature requires it.
