## 🌐 Языки / Languages

- 🇬🇧 [English](README.md)

# ⏱️ TimeTracker — Техническое задание: трекер рабочего времени

TimeTracker — это серверное приложение, разработанное как техническое задание. Оно реализует систему учёта времени, потраченного сотрудниками на выполнение рабочих задач. Проект построен на Spring Boot и использует JWT-аутентификацию, PostgreSQL и Docker.

---

## ⚙️ Основной функционал

- 👤 Регистрация и вход с JWT-аутентификацией
- 📁 Управление проектами и задачами
- ⏳ Учёт времени сессий сотрудников
- 🧑‍💼 Разграничение прав: пользователь и администратор
- 📊 Получение активности и истории по проектам

---

## 🛠️ Технологии

- Java 17+  
- Spring Boot  
- Spring Security (JWT)  
- Spring Data JPA  
- PostgreSQL  
- Docker & Docker Compose  
- Maven

---

## 🚀 Быстрый старт

1. Клонируйте репозиторий:
```bash
git clone https://github.com/noverlean/TimeTracker.git
cd TimeTracker
```

2. Соберите и запустите окружение:
```bash
docker-compose build
docker-compose up
```

3. Готово! Вы можете использовать API через любой REST-клиент (например, Postman).
- Базовый URL: http://localhost:8080
- После регистрации или входа вы получите JWT-токен, который необходимо передавать в заголовке Authorization: Bearer <token>

## 🔐 Аутентификация
- POST /signup — регистрация нового пользователя Пример тела запроса:

```json
{
  "email": "user@example.com",
  "password": "yourPassword"
}
```
- POST /login — вход в систему Пример тела запроса:

```json
{
  "email": "user@example.com",
  "password": "yourPassword"
}
```

# 📌 Основные эндпоинты
## 👑 ADMIN
- Получить список пользователей GET /users

- Получить список проектов GET /products

- Создать новый проект POST /products

```json
{
  "title": "Project Title",
  "description": "Project Description"
}
```
- Добавить пользователя в проект PATCH /projects/{projectId}/link/users/email={email}

- Завершить или возобновить проект PATCH /projects/{projectId}/finish PATCH /projects/{projectId}/resume

##👤 USER

- Получить свои проекты GET /users/self/projects
  
- Начать сессию POST /projects/{projectId}/sessions/start
  
- Завершить сессию POST /projects/{projectId}/sessions/finish

## 🔐 AUTHENTICATED
- Получить проект по ID GET /projects/{projectId}

- Получить список пользователей проекта GET /projects/{projectId}/users

- Получить активность пользователя в проекте GET /projects/{projectId}/users/email={email}/sessions

- Получить пользователя по email GET /users/email={email}

- Получить свой профиль GET /users/self
