CREATE TABLE `contenttypes` (
  `content_type_id` int NOT NULL,
  `content_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `guguns` (
  `no` int NOT NULL AUTO_INCREMENT,
  `sido_code` int DEFAULT NULL,
  `gugun_code` int DEFAULT NULL,
  `gugun_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code`),
  KEY `gugun_code_idx` (`gugun_code`),
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk` FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `likes_places` (
  `user_id` int NOT NULL,
  `place_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`place_id`),
  KEY `attraction_no` (`place_id`),
  CONSTRAINT `likes_places_ibfk_2` FOREIGN KEY (`place_id`) REFERENCES `places` (`place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `place_comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `place_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  KEY `place_id` (`place_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `place_comments_ibfk_1` FOREIGN KEY (`place_id`) REFERENCES `places` (`place_id`) ON DELETE CASCADE,
  CONSTRAINT `place_comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `places` (
  `place_id` int NOT NULL AUTO_INCREMENT,
  `content_id` int DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `content_type_id` int DEFAULT NULL,
  `area_code` int DEFAULT NULL,
  `si_gun_gu_code` int DEFAULT NULL,
  `first_image1` varchar(100) DEFAULT NULL,
  `first_image2` varchar(100) DEFAULT NULL,
  `map_level` int DEFAULT NULL,
  `latitude` decimal(20,17) DEFAULT NULL,
  `longitude` decimal(20,17) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `addr2` varchar(100) DEFAULT NULL,
  `homepage` varchar(1000) DEFAULT NULL,
  `overview` varchar(10000) DEFAULT NULL,
  `count` int DEFAULT '0',
  `like_count` int DEFAULT '0',
  PRIMARY KEY (`place_id`),
  KEY `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id`),
  KEY `attractions_sido_to_sidos_code_fk_idx` (`area_code`),
  KEY `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code`),
  CONSTRAINT `attractions_area_to_sidos_code_fk` FOREIGN KEY (`area_code`) REFERENCES `sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk` FOREIGN KEY (`si_gun_gu_code`) REFERENCES `guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk` FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56644 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plan_places` (
  `plan_place_id` int NOT NULL AUTO_INCREMENT,
  `plan_id` int NOT NULL,
  `visit_date` date NOT NULL,
  `visit_time` time NOT NULL,
  `place_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`plan_place_id`),
  KEY `plan_id` (`plan_id`),
  CONSTRAINT `plan_places_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `plans` (`plan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plans` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `location` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`plan_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `plans_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `playlist` (
  `playlist_id` int NOT NULL AUTO_INCREMENT,
  `plan_place_id` int DEFAULT NULL,
  PRIMARY KEY (`playlist_id`),
  KEY `plan_place_id` (`plan_place_id`),
  CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`plan_place_id`) REFERENCES `plan_places` (`plan_place_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `playlist_trackid` (
  `playlist_id` int NOT NULL,
  `spotify_track_id` varchar(45) NOT NULL,
  PRIMARY KEY (`playlist_id`,`spotify_track_id`),
  CONSTRAINT `playlist_trackid_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_comments_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE,
  CONSTRAINT `post_comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `posts` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sidos` (
  `no` int NOT NULL AUTO_INCREMENT,
  `sido_code` int DEFAULT NULL,
  `sido_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE KEY `sido_code_UNIQUE` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;