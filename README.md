# Patch Compliance Reporter
Patch Compliance Reporter is an AI-powered application developed using Spring Boot, Flask, and Groq API to generate automated patch compliance reports from vulnerability descriptions.

## Features
* AI-generated patch compliance reports
* Groq AI integration
* Spring Boot backend
* Flask AI microservice
* JWT authentication
* Input sanitisation
* Prompt injection detection
* API rate limiting
* Docker support

## Run Backend
cd backend
mvn spring-boot:run

Backend runs on:
http://localhost:8080

## Run AI Service
python -m ai_service.app

AI Service runs on:
http://127.0.0.1:5000

## Docker Run
docker-compose up --build

## API Endpoints
### Health Check
GET /health

### Login
POST /login

### Generate Report
POST /generate-report

## Sample Request
{
  "input": "Critical Windows vulnerability detected in server KB502123"
}

## Security Features
* JWT Authentication
* Input Sanitisation
* Prompt Injection Detection
* Rate Limiting
* OWASP Security Testing

## Internship Role
Generative AI Intern

Worked on:
* Flask AI development
* Groq API integration
* Security implementation
* API testing
* Docker deployment

## Conclusion
This project demonstrates secure AI-powered vulnerability analysis and automated patch compliance report generation using modern AI and backend technologies.
