# spring-rest-assured-playground
[![Build](https://github.com/paczek224/spring-rest-assured-playground/actions/workflows/ci.yaml/badge.svg)](https://github.com/paczek224/spring-rest-assured-playground/actions/workflows/ci.yaml)
[![Allure Report](https://img.shields.io/badge/Allure-Report-ff69b4)](https://paczek224.github.io/spring-rest-assured-playground/)

A showcase project designed to demonstrate the technical skills of a
**Test Automation Engineer**.

## Overview

This project is built with **Spring Boot** and exposes a simple web
server with multiple endpoints backed by services and database
repositories.
It is intended as a playground for practicing and presenting various
testing approaches.

## Features

-   **Spring Boot application** with REST endpoints.
-   **Dedicated test profile** using **WireMock**.
-   **Dockerfile** for easy containerization.
-   **TestContainers**
-   **Maven GitHub Workflow** that builds the app, runs tests, and
    generates an **Allure report**.

## Testing Highlights

All tests are located under the `test` package and demonstrate:

-   ✅ **JUnit 5 integration**
-   ✅ **Parameterized tests**
-   ✅ **Contract tests**
-   ✅ **REST Assured** usage, including:
    -   Query parameters
    -   Path parameters
    -   Authentication
-   ✅ **Serialization & Deserialization**
-   ✅ **Service mocking with WireMock**
-   ✅ **TestContainers**
-   ✅ **Grafana**
-   ✅ **Prometheus**
-   ✅ **Kafka**

## CI/CD

The project includes a **GitHub Actions workflow** with Maven:

1.  Build the application
2.  Run the test suite
3.  Generate **Allure reports** for test results

## Docker

A simple **Dockerfile** is provided to run the application inside a
container.

------------------------------------------------------------------------

### How to Run
**Run locally:**

**Run with Docker Compose (with Grafana and Prometheus):**

* (add .env file with secrets)
```
APP_USERNAME=user
APP_PASSWORD=password

ADMIN_USERNAME=admin.user
ADMIN_PASSWORD=admin.password
```

* docker-compose (spring app, kafka, grafana prometheus)
``` bash
 docker-compose up --build
```

* docker-compose (with sonarQube)
``` bash
 docker-compose --profile sonar up --build
```

* Run tests
``` bash
 mvn test -Dspring.profiles.active=test -Dtest=RegressionTests
```

* Prometheus      http://localhost:9090/ 
* Grafana         http://localhost:3000/
* SonarQube       http://localhost:9000/
* Kafka UI       http://localhost:8888/

------------------------------------------------------------------------

### Technologies Used

-   Java + Spring Boot
-   JUnit 5
-   Pact
-   REST Assured
-   WireMock
-   Maven
-   GitHub Actions
-   Allure Report
-   Docker
-   Test containers
-   Grafana
-   Prometheus
-   SonarQube
-   Kafka

------------------------------------------------------------------------

### Purpose

This project is not intended for production use.
Its goal is to **demonstrate testing practices and tools** in the
context of modern Test Automation Engineering.
