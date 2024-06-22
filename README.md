# Spring Boot Article Management System

This is a Spring Boot application designed to manage articles. It allows users to create, view, edit, and delete articles while ensuring that only the owners can modify their entries. The application handles user authentication and session management, providing a secure environment for article management.

## Features

- User registration and authentication.
- Create, read, update, and delete articles.
- Articles are sortable by creation and update dates.
- Pagination support for article listing.
- Security enhancements to prevent unauthorized users from editing or deleting articles.

## Technologies

- **Spring Boot**: Framework for building the application.
- **Spring Security**: For authentication and access control.
- **Spring Data JPA**: For database interactions.
- **Thymeleaf**: Server-side Java template engine for web views.
- **MySQL**: Database for storing user and article data.
- **Maven**: Dependency management.

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Ensure you have the following installed:
- Java JDK 11 or higher
- Maven
- MySQL

### Installing

Clone the repository to your local machine:

```bash
git clone https://github.com/yourgithubusername/spring-boot-articles.git
cd spring-boot-articles
