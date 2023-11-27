CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `confirmation_token` varchar(255) DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contact_messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `contact_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `user_contacts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_contacts_ibfk_2` FOREIGN KEY (`contact_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_properties` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `location` varchar(255) NOT NULL,
  `description` text,
  `size` double DEFAULT NULL,
  `bedrooms` int DEFAULT NULL,
  `bathrooms` int DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_properties_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_tenants` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lease_start_date` date NOT NULL,
  `lease_end_date` date NOT NULL,
  `rent_amount` decimal(15,2) NOT NULL,
  `security_deposit_amount` decimal(15,2) NOT NULL,
  `property_id` int NOT NULL,
  `tenant_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `property_id` (`property_id`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `user_tenants_ibfk_1` FOREIGN KEY (`property_id`) REFERENCES `user_properties` (`id`),
  CONSTRAINT `user_tenants_ibfk_2` FOREIGN KEY (`tenant_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `friend_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `accepted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_payment_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment_date` date NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL,
  `status` varchar(20) NOT NULL,
  `tenant_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_payment_records_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `user_tenants` (`id`),
  CONSTRAINT `user_payment_records_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
