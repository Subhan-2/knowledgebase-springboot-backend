# knowledgebase-springboot-backend
Spring Boot backend for Knowledge Base platform with JWT authentication, document sharing, and PostgreSQL integration.
# 📚 Knowledge Base Platform - Spring Boot Backend

A scalable, secure backend service for a Knowledge Base system that allows users to create, update, and share documents, with **user authentication (JWT)** and **PostgreSQL** as the database.

---

## ✅ Features

- ✅ User Registration & Login (JWT-based Authentication)
- ✅ Secure API endpoints (Spring Security + JWT Filters)
- ✅ Document CRUD (Create, Read, Update)
- ✅ Document Sharing via Mentions (e.g., `@username` auto shares the document)
- ✅ Document Search (by title or content keyword)
- ✅ View Documents Shared With User
- ✅ PostgreSQL Database integration
- ✅ Layered architecture: Controller → Service → Repository → Model → DTO → Config → Security

---

## ✅ Technology Stack

| Layer | Tech |
|---|---|
| Backend Framework | Java + Spring Boot |
| Database | PostgreSQL |
| ORM | Spring Data JPA |
| Security | Spring Security + JWT |
| Build Tool | Maven |
| Java Version | 17 |

---

## ✅ Project Structure

src/main/java/com/frigga/knowledgebase/
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── config
