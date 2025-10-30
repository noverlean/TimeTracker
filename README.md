## 🌐 Языки / Languages

- 🇷🇺 [Русский](README.ru.md)

# ⏱️ TimeTracker — Technical Assignment: Employee Time Tracking System

TimeTracker is a backend application developed as a technical assignment. It implements a time tracking system for employees working on various tasks. The project is built with Spring Boot and uses JWT authentication, PostgreSQL, and Docker.

---

## ⚙️ Core Features

- 👤 User management
  - Registration and login with JWT authentication
  - Profile access and project assignment

- 📁 Project management
  - Create and update projects
  - Assign users to projects
  - Mark projects as finished or resume them

- ⏳ Time tracking
  - Start and finish work sessions
  - Calculate total time spent on tasks
  - View user activity history

- 🧑‍💼 Role-based access: admin and regular user
- 📊 Activity and project history endpoints

---

## 🛠️ Technologies Used

- Java 17+  
- Spring Boot  
- Spring Security (JWT)  
- Spring Data JPA  
- PostgreSQL  
- Docker & Docker Compose  
- Maven

---

## 🚀 Getting Started

1. Clone the repository:
```bash
git clone https://github.com/noverlean/TimeTracker.git
cd TimeTracker
```
   
2. Build and run the environment:
```bash
docker-compose build
docker-compose up
```

3. Done! You can now use the API with any REST client (e.g., Postman).
- Root URL: http://localhost:8080
- After signing up or logging in, you'll receive a JWT token. Include it in requests using the header: Authorization: Bearer <your_token>

---

🔐 Authentication

- POST /signup — Register a new user Request body:
```json
{
  "email": "user@example.com",
  "password": "yourPassword"
}
```

- POST /login — Log in Request body:
```json
{
  "email": "user@example.com",
  "password": "yourPassword"
}
```

---

# 📌 Key Endpoints
## 👑 ADMIN

- Get list of users GET /users

- Get list of projects GET /products

- Create a new project POST /products
```json
{
  "title": "Project Title",
  "description": "Project Description"
}
```

- Add user to a project PATCH /projects/{projectId}/link/users/email={email}

- Finish or resume a project PATCH /projects/{projectId}/finish PATCH /projects/{projectId}/resume\

## 👤 USER
- Get own projects GET /users/self/projects

- Start a work session POST /projects/{projectId}/sessions/start

- Finish a work session POST /projects/{projectId}/sessions/finish

## 🔐 AUTHENTICATED USERS
- Get project by ID GET /projects/{projectId}

- Get users assigned to a project GET /projects/{projectId}/users

- Get user activity on a project GET /projects/{projectId}/users/email={email}/sessions

- Get user by email GET /users/email={email}

- Get own profile GET /users/self
