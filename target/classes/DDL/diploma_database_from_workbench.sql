CREATE DATABASE diploma_thesis_db;
USE diploma_thesis_db;

CREATE TABLE users (
    id int PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'PROFESSOR') NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE students (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `user_id` int,
    `full_name` VARCHAR(255) DEFAULT NULL,
    `year_of_studies` INT DEFAULT NULL,
    `current_average_grade` DOUBLE DEFAULT NULL,
    `remaining_courses` INT DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `AM` INT DEFAULT NULL, 
    `age` INT DEFAULT NULL,
    CONSTRAINT `stud_fk_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE professors (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `user_id` int UNIQUE,
    `full_name` VARCHAR(255)  DEFAULT NULL,
    `specialty` VARCHAR(255)  DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `AM` INT DEFAULT NULL, 
    `years_of_teaching` INT DEFAULT NULL,
    `age` INT DEFAULT NULL,users
    CONSTRAINT `prof_fk_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE subjects (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `objectives` TEXT NOT NULL,
    `supervisor_id` int,
	`availability` boolean DEFAULT true,
    `total_months` INT DEFAULT NULL,
    CONSTRAINT `sub_fk_professor` FOREIGN KEY (`supervisor_id`) REFERENCES `professors`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE applications (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `student_id` int,
    `subject_id` int,
    CONSTRAINT `appl_fk_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
    CONSTRAINT `appl_fk_subject` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE theses (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `supervisor_id` int,
    `subject_id` int,
	`student_id` int,
    `implementation_grade` DOUBLE,
    `report_grade` DOUBLE,
    `presentation_grade` DOUBLE,
    CONSTRAINT `thesis_fk_supervisor` FOREIGN KEY (`supervisor_id`) REFERENCES `professors`(`id`) ON DELETE CASCADE,
    CONSTRAINT `thesis_fk_subject` FOREIGN KEY (`subject_id`) REFERENCES `subjects`(`id`) ON DELETE CASCADE,
	CONSTRAINT `thesis_fk_student` FOREIGN KEY (`student_id`) REFERENCES `students`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
