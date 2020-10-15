# Database Detailed Explanation

## Database Schema Design

```mysql
CREATE TABLE `order_status` (
  `user_id` BIGINT,
  `confirmed` BOOLEAN,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `product_order` (
  `product_order_id` BIGINT,
  `user_id` BIGINT,
  `product_id` BIGINT,
  `timestamp` VARCHAR(255),
  `rating` DOUBLE,
  `confirmed` BOOLEAN,
  PRIMARY KEY (`product_order_id`)
);

CREATE TABLE `user` (
  `user_id` BIGINT,
  `username` VARCHAR(50),
  `email` VARCHAR(255),
  `photoUrl` VARCHAR(20),
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `product_status` (
  `product_id` BIGINT,
  `availability` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `category` (
  `category_id` BIGINT,
  `category_name` VARCHAR(20),
  `description` TEXT,
  PRIMARY KEY (`category_id`)
);

CREATE TABLE `rating` (
  `order_id` BIGINT,
  `rating` DOUBLE,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE `product` (
  `product_id` BIGINT,
  `user_id` BIGINT,
  `product_name` VARCHAR(255),
  `category` VARCHAR(255),
  `price` DECIMAL(8,2),
  `description` TEXT,
  `imageUrls` VARCHAR(500),
  `timestamp` VARCHAR(255),
  `timezoneId` VARCHAR(255),
  `lat` DECIMAL(10,2),
  `lon` DECIMAL(11,2),
  `city` VARCHAR(255),
  `state` VARCHAR(255) NOT NULL,
  `GPSEnabled` BOOLEAN,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `order_request` (
  `request_id` BIGINT,
  `user_id` BIGINT,
  `product_id` BIGINT,
  `message` TEXT,
  `request_status` VARCHAR(255) DEFAULT 'pending',
  PRIMARY KEY (`request_id`)
);

CREATE TABLE `favorite` (
  `favorite_id` BIGINT,
  `user_id` BIGINT,
  `product_id` BIGINT,
  `timestamp` DATETIME,
  PRIMARY KEY (`favorite_id`)
);
```