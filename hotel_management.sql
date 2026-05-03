-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2026 at 06:31 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `status` enum('PENDING','CONFIRMED','CANCELLED','COMPLETED') DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `check_in_date`, `check_out_date`, `status`, `total_price`, `user_id`, `room_id`) VALUES
(1, '2026-04-22', '2026-04-23', 'CONFIRMED', 50, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `booking_service_usage`
--

CREATE TABLE `booking_service_usage` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `used_date` datetime(6) DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `contact_messages`
--

CREATE TABLE `contact_messages` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `note` text DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `status` enum('PENDING','READ','REPLIED') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hotel_service`
--

CREATE TABLE `hotel_service` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel_service`
--

INSERT INTO `hotel_service` (`id`, `description`, `name`, `price`) VALUES
(1, 'Morning buffet', 'Breakfast', 10),
(2, 'Wash and dry', 'Laundry', 5),
(3, 'Full body massage', 'Spa', 30),
(4, 'Pick up or drop off', 'Airport Transfer', 20),
(5, 'Morning buffet', 'Breakfast', 10),
(6, 'Wash and dry', 'Laundry', 5),
(7, 'Full body massage', 'Spa', 30),
(8, 'Pick up or drop off', 'Airport Transfer', 20),
(9, 'Morning buffet', 'Breakfast', 10),
(10, 'Wash and dry', 'Laundry', 5),
(11, 'Full body massage', 'Spa', 30),
(12, 'Pick up or drop off', 'Airport Transfer', 20),
(13, 'Morning buffet', 'Breakfast', 10),
(14, 'Wash and dry', 'Laundry', 5),
(15, 'Full body massage', 'Spa', 30),
(16, 'Pick up or drop off', 'Airport Transfer', 20);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `id` bigint(20) NOT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_status` enum('PAID','UNPAID') DEFAULT NULL,
  `room_charges` double DEFAULT NULL,
  `service_charges` double DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `price` double DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `status` enum('AVAILABLE','BOOKED','OCCUPIED','MAINTENANCE') DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `price`, `room_number`, `status`, `type`) VALUES
(1, 50, '101', 'AVAILABLE', 'Single'),
(2, 80, '102', 'AVAILABLE', 'Double'),
(3, 150, '201', 'AVAILABLE', 'Suite'),
(4, 120, '202', 'AVAILABLE', 'Deluxe'),
(5, 250, '301', 'AVAILABLE', 'Suite'),
(6, 50, '101', 'AVAILABLE', 'Single'),
(7, 80, '102', 'AVAILABLE', 'Double'),
(8, 150, '201', 'AVAILABLE', 'Suite'),
(9, 120, '202', 'AVAILABLE', 'Deluxe'),
(10, 250, '301', 'AVAILABLE', 'Suite'),
(11, 50, '101', 'AVAILABLE', 'Single'),
(12, 80, '102', 'AVAILABLE', 'Double'),
(13, 150, '201', 'AVAILABLE', 'Suite'),
(14, 120, '202', 'AVAILABLE', 'Deluxe'),
(15, 250, '301', 'AVAILABLE', 'Suite'),
(16, 50, '101', 'AVAILABLE', 'Single'),
(17, 80, '102', 'AVAILABLE', 'Double'),
(18, 150, '201', 'AVAILABLE', 'Suite'),
(19, 120, '202', 'AVAILABLE', 'Deluxe'),
(20, 250, '301', 'AVAILABLE', 'Suite');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','STAFF','CUSTOMER') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `full_name`, `password`, `phone`, `role`, `username`) VALUES
(1, 'admin@hotel.com', 'System Admin', '$2a$10$quXTzm4bF3fxLBR51eAG9ucCCvzkYS9EqMQmqMqSuxeN/5El2CbS.', '0123456789', 'ADMIN', 'admin'),
(2, 'staff@hotel.com', 'Hotel Staff', '$2a$10$QRljF7eWRa8Nx.szaSNAMesOoDq3ppdCdPRMN5VQprT06gunpJ9nW', '0987654321', 'STAFF', 'staff'),
(3, 'john@gmail.com', 'John Doe', '$2a$10$k1D19Kj7WlHxf/iq.OCDzupX6MFHC8r8XWLc5Q.wxPMreCGp43fcC', '0111222333', 'CUSTOMER', 'customer'),
(4, 'thuytien@gmail.com', 'thuytien', '$2a$10$Vtp5.AVuU9V8erKSH425BeCd7xZpCvqSm14BOlri64OuE8nrhF7Za', '0354762278', 'CUSTOMER', 'thuytien');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7udbel7q86k041591kj6lfmvw` (`user_id`),
  ADD KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`);

--
-- Indexes for table `booking_service_usage`
--
ALTER TABLE `booking_service_usage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeigum17gu8t8f78dxs1ky8tr6` (`booking_id`),
  ADD KEY `FK47a67qs9sswpq1be76tldmcdp` (`service_id`);

--
-- Indexes for table `contact_messages`
--
ALTER TABLE `contact_messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hotel_service`
--
ALTER TABLE `hotel_service`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_32ywtxrkeu1wnmivu6mlcqdid` (`booking_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `booking_service_usage`
--
ALTER TABLE `booking_service_usage`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `contact_messages`
--
ALTER TABLE `contact_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hotel_service`
--
ALTER TABLE `hotel_service`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK7udbel7q86k041591kj6lfmvw` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Constraints for table `booking_service_usage`
--
ALTER TABLE `booking_service_usage`
  ADD CONSTRAINT `FK47a67qs9sswpq1be76tldmcdp` FOREIGN KEY (`service_id`) REFERENCES `hotel_service` (`id`),
  ADD CONSTRAINT `FKeigum17gu8t8f78dxs1ky8tr6` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`);

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `FK4jd6uuk7w0d72riyre2w14fl7` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
