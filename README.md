# Todo App API

A RESTful API for a Todo application built with Spring Boot. This application allows users to manage their Todo items with functionalities for user authentication and authorization, providing a secure environment for private data management.

## Features

### User Authentication
- **Register**: Users can create new accounts using their email and password.
- **Login**: Users can authenticate with their email and password.

### Todo Management
- **Create**: Users can create new Todo items.
- **Read**: Users can retrieve their own Todo items.
- **Update**: Users can modify existing Todo items.
- **Delete**: Users can remove Todo items.

### Authorization
- **Private Data**: Users can only access and modify their own Todo items.
- **Security**: The application implements appropriate security measures to protect user data.

### Additional Features
- **Error Handling**: Informative error messages for invalid requests or unauthorized actions.
- **Pagination**: Efficient retrieval of large Todo lists through pagination.
- **Sorting**: Users can sort their Todo items by various criteria (e.g., due date, priority).
- **Search**: Users can search for Todo items based on keywords or other attributes.
- **Completion Status**: Tracks the completion status of Todo items.
- **Logging**: Monitors and traces errors for better debugging and performance tracking.

## Tech Stack
- **Backend**: Spring Boot
- **Security**: Spring Security with JWT
- **Database**: MySQL with Spring JPA
- **Testing**: JUnit for unit testing

## Requirements
- Java 11 or later
- MySQL Server
- Maven
