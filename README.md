# Diploma-Projects-Management-App
This web application allows students to browse available diploma thesis projects from professors, apply for them, and track their progress. Professors can manage their thesis projects, assign them to students, and evaluate the outcomes. The system is built using Java and Spring Boot, with a secure role-based authentication system.
# Intro 
The Diploma Projects Management App streamlines the process of managing diploma thesis projects. Through the application:
  - Students can apply for thesis projects, manage their profiles, and view their thesis progress.
 - Professors can offer thesis projects, review student applications, assign projects based on various strategies, and evaluate the work done by students.
   
Key technologies include Java, Spring Boot, Spring Security, MySQL, and JUnit for testing.

# How it works 
## Authentication and Role Management
- The system uses Spring Security to manage user authentication and roles (student or professor).
- Based on the role, users are redirected to the appropriate dashboard after login.
## Core Functionality
- Student Operations: Students can create profiles, browse thesis projects, apply for topics, and track their application status.
- Professor Operations: Professors can manage thesis subjects, view student applications, assign topics using predefined strategies (e.g., based on grades or remaining courses), and evaluate theses.
- Strategy Pattern: The application uses multiple strategies for thesis assignment, such as:
  - Best grade strategy
  - Fewest courses remaining
  - Random selection
  - Threshold-based selection
## Data Management
- DAO Layer: Manages database interactions for entities such as users, students, professors, subjects, theses, and applications.
- Service Layer: Contains business logic for user management, profile updates, subject handling, thesis creation, and grading.

# How to use
## 1. Login/Registration:

- Users can sign up as either students or professors.
- Based on the role, users are directed to their respective homepages.
## 2. Student Actions:

- View available thesis projects under the "Available Thesis Subjects" section.
- Apply for a thesis and track the status of applications through the "My Applications" section.
- View the final assigned thesis and progress through the "My Diploma" section.
## 3. Professor Actions:

- Create and manage thesis subjects in the "Subjects" section.
- View student applications and assign theses using various selection strategies.
- Input and manage student grades for their thesis.
## Security and Permissions
- Only authorized users (students or professors) can access specific areas of the application.
- Passwords are securely hashed using BCrypt.
