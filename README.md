# 🩺 Member Benefits Dashboard

A full-stack web application that allows members to securely view their **health plan details**, **claims history**, and **accumulator progress** (deductible, out-of-pocket max).  
Built with **Spring Boot**, **React (Vite)**, and **PostgreSQL**, the app demonstrates a modern claims experience with federated Google authentication, REST APIs, and responsive design powered by Tailwind CSS.

---

## 🚀 Features

- **Google Sign-In (OIDC)** using `@react-oauth/google`
- **Claims Dashboard** with claim list, detail view, and provider info
- **Accumulators** section showing deductible and OOP progress
- **RESTful Spring Boot API** backed by PostgreSQL
- **Seed Data** auto-generated when a new user authenticates
- **Responsive UI** styled with Tailwind CSS
- **Server-side filtering and pagination** (in progress)
- **Dockerized database** for consistent local setup

---

## 🧱 Tech Stack

| Layer | Technologies |
|-------|---------------|
| **Frontend** | React 19, Vite, React Router v7, Axios, Tailwind CSS |
| **Backend** | Spring Boot 3, Spring Security (OAuth2 Resource Server), JPA, Lombok |
| **Database** | PostgreSQL 16 |
| **Auth** | Google OAuth 2.0 (via `@react-oauth/google`) |
| **Containerization** | Docker, Docker Compose |
| **Build Tools** | Maven (backend) & npm/vite (frontend) |

---

## 🗂️ Project Structure

gt-member-benefits-dashboard/  
├── backend/  
│ ├── src/main/java/com/greggtrunnelldashboard/backend/  
│ │ ├── controllers/  
│ │ ├── entities/  
│ │ ├── repositories/  
│ │ ├── SeedData/  
│ │ └── BackendApplication.java  
│ └── pom.xml  
│  
├── frontend/  
│ └── vite-project/  
│ ├── src/  
│ ├── package.json  
│ ├── tailwind.config.js  
│ └── vite.config.js  
│  
├── docker-compose.yml  
└── README.md

---

## ⚙️ Installation & Setup

#### 1️⃣ Prerequisites
- [Java 17+](https://adoptium.net/)
- [Node.js 20+](https://nodejs.org/)
- [Docker Desktop](https://www.docker.com/)
- Maven (if not using IntelliJ’s built-in runner)

---

##### 2️⃣  Clone the repository

git clone https://github.com/York-Solutions-B2E/gtrunnell-member-benefits-dashboard.git
cd gt-member-benefits-dashboard

##### 3️⃣ Start the Database with Docker
 ```
 docker-compose up -d

 ```

This launches a local PostgreSQL instance named **memberdb** on port **5432**.

##### 4️⃣ Run the Backend
```
cd backend
./mvnw spring-boot:run

```
The backend starts on **[http://localhost:8080](http://localhost:8080)**.  
It connects automatically to the Docker Postgres container


##### 5️⃣ Run the Frontend
```
cd frontend/vite-project
npm install
npm run dev

``` 
Frontend runs on http://localhost:5173.

##### 6️⃣ Login & Seed Data

- On first sign-in with Google, the backend’s `SeedData` component will automatically initialize sample **member**, **provider**, **plan**, and **claims** data for that user.
- After authentication, you’ll be redirected to the dashboard showing **accumulators** and **claims list**.
- In the **claims list** you'll be able to view specific **claim details**.

#### 🧩 Common Commands

| Action                  | Command                              |
| ----------------------- | ------------------------------------ |
| Start backend           | `./mvnw spring-boot:run`             |
| Start frontend          | `npm run dev`                        |
| Start Postgres          | `docker-compose up -d`               |
| Stop all containers     | `docker stop $(docker ps -q)`        |
| Clean ports (Mac/Linux) | `kill -9 $(lsof -ti:8080,5173,5432)` |
