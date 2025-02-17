# Tiny Ledger

[![Java CI/CD](https://github.com/EwanDubashinski/tiny-ledger/actions/workflows/ci.yml/badge.svg)](https://github.com/EwanDubashinski/tiny-ledger/actions/workflows/ci.yml)
[![CodeQL](https://github.com/EwanDubashinski/tiny-ledger/actions/workflows/codeql.yml/badge.svg)](https://github.com/EwanDubashinski/tiny-ledger/actions/workflows/codeql.yml)
[![codecov](https://codecov.io/gh/EwanDubashinski/tiny-ledger/branch/main/graph/badge.svg)](https://codecov.io/gh/EwanDubashinski/tiny-ledger)

A simple REST API service that implements a basic ledger system supporting deposits, withdrawals, and balance inquiries.

## Features

- Record deposits and withdrawals
- View current balance
- View transaction history
- Input validation for monetary amounts
- Proper error handling for insufficient funds and invalid amounts

## Technical Stack

- Java 21
- Spring Boot 3.4.2
- Maven
- OpenAPI/Swagger for API documentation
- JUnit 5 for testing
- JaCoCo for test coverage

## Development Approach

- API-First development using OpenAPI specification
- The API contract is defined in `src/main/resources/api/openapi.yaml`
- Can be tested using Postman, Insomnia, or similar tools by importing the OpenAPI specification
- Initially project was created with Spring Initializr

## Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Docker (optional)

## Running the Application

### Using Maven

```bash
./mvnw spring-boot:run
```

### Using Docker

```bash
# Build the image
docker build -t tiny-ledger .

# Run the container
docker run -p 8080:8080 tiny-ledger
```

## API Documentation

Once the application is running, you can access the Swagger UI at:
http://localhost:8080/swagger-ui.html

## API Examples

### View Current Balance

```bash
curl http://localhost:8080/balance
```

Response:
```json
{
    "balance": "0.00"
}
```

### Make a Deposit

```bash
curl -X POST http://localhost:8080/deposit \
     -H "Content-Type: application/json" \
     -d '{"amount": "100.00"}'
```

Response:
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "type": "deposit",
    "amount": "100.00",
    "timestamp": "2024-02-20T10:30:00Z"
}
```

### Make a Withdrawal

```bash
curl -X POST http://localhost:8080/withdrawal \
     -H "Content-Type: application/json" \
     -d '{"amount": "50.00"}'
```

Response:
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174001",
    "type": "withdrawal",
    "amount": "50.00",
    "timestamp": "2024-02-20T10:35:00Z"
}
```

### View Transaction History

```bash
curl http://localhost:8080/history
```

Response:
```json
[
    {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "type": "deposit",
        "amount": "100.00",
        "timestamp": "2024-02-20T10:30:00Z"
    },
    {
        "id": "123e4567-e89b-12d3-a456-426614174001",
        "type": "withdrawal",
        "amount": "50.00",
        "timestamp": "2024-02-20T10:35:00Z"
    }
]
```

## Design Decisions and Assumptions

1. **In-Memory Storage**: Using a simple in-memory repository implementation as per requirements.

2. **Simplified Account Model**: 
    - Using a global balance instead of individual accounts for simplification
    - No multi-currency support in this version
    - Amounts can have smaller parts than 0.01 to support potential crypto use cases

3. **Money Handling**:
    - Using combination of BigDecimal and String representation for simplicity
    - Considered but opted against Java Money or similar solutions for this implementation
    - All monetary values are validated on input
    - Amounts stored as strings in the API to maintain precision
    - Internally using BigDecimal for calculations

4. **Domain-Driven Design Elements**:
    - Validation moved to value objects level to support DDD principles
    - Immutable value objects for core domain concepts (Amount, Balance, TransactionId)
    - While the codebase could be split into application infrastructure and business domain code, kept current structure for simplification

5. **Error Handling**:
    - Invalid amounts (negative, zero, or malformed)
    - Insufficient funds for withdrawals
    - All errors return appropriate HTTP status codes and error messages

## Limitations

As per the assignment requirements, the following are not implemented:
- Authentication/Authorization
- Persistent storage
- Transactional operations
- Logging/Monitoring
- Rate limiting

## Testing

Run the tests using:

```bash
./mvnw test
```

For test coverage report:

```bash
./mvnw verify
```

The coverage report will be generated in `target/site/jacoco/index.html`
