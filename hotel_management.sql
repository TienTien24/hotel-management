-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2026 at 08:58 AM
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
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `status` enum('PENDING','CONFIRMED','CANCELLED','COMPLETED') DEFAULT NULL,
  `checked_in_at` datetime(6) DEFAULT NULL,
  `checked_out_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `guest_address` varchar(255) DEFAULT NULL,
  `guest_email` varchar(255) DEFAULT NULL,
  `guest_full_name` varchar(255) DEFAULT NULL,
  `guest_phone` varchar(255) DEFAULT NULL,
  `number_of_guests` int(11) DEFAULT NULL,
  `payment_method` enum('COD','VNPAY','MOMO') DEFAULT NULL,
  `payment_status` enum('UNPAID','PENDING','PAID','FAILED') DEFAULT NULL,
  `review_comment` text DEFAULT NULL,
  `review_created_at` datetime(6) DEFAULT NULL,
  `review_rating` int(11) DEFAULT NULL,
  `review_title` varchar(255) DEFAULT NULL,
  `guest_id_image_url` varchar(255) DEFAULT NULL,
  `guest_id_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`check_in_date`, `check_out_date`, `total_price`, `id`, `room_id`, `user_id`, `status`, `checked_in_at`, `checked_out_at`, `created_at`, `guest_address`, `guest_email`, `guest_full_name`, `guest_phone`, `number_of_guests`, `payment_method`, `payment_status`, `review_comment`, `review_created_at`, `review_rating`, `review_title`, `guest_id_image_url`, `guest_id_number`) VALUES
('2026-04-22', '2026-04-25', 135, 4, 206, 3, 'COMPLETED', '2026-04-22 02:19:37.000000', '2026-04-25 02:19:37.000000', '2026-05-02 02:19:37.000000', '123 Main St', 'john@gmail.com', 'John Doe', '0111222333', 2, 'VNPAY', 'PAID', 'Phòng rất đẹp và dịch vụ tuyệt vời!', '2026-04-26 02:19:37.000000', 5, 'Kỳ nghỉ tuyệt vời', NULL, NULL),
('2026-04-27', '2026-04-29', 100, 5, 207, 3, 'COMPLETED', '2026-04-27 02:19:37.000000', '2026-04-29 02:19:37.000000', '2026-05-02 02:19:37.000000', '456 Oak Ave', 'jane@gmail.com', 'Jane Smith', '0444555666', 1, 'MOMO', 'PAID', 'Trải nghiệm tốt, nhưng hơi ồn ào một chút.', '2026-04-30 02:19:37.000000', 4, 'Trải nghiệm tốt', NULL, NULL),
('2026-04-12', '2026-04-14', 110, 6, 208, 3, 'COMPLETED', '2026-04-12 02:19:37.000000', '2026-04-14 02:19:37.000000', '2026-05-02 02:19:37.000000', '789 Pine Ln', 'peter@gmail.com', 'Peter Jones', '0777888999', 3, 'COD', 'PAID', 'Khách sạn sang trọng, rất đáng tiền!', '2026-04-15 02:19:37.000000', 5, 'Sang trọng và đẳng cấp', NULL, NULL),
('2026-04-20', '2026-04-22', 340, 7, 236, 3, 'COMPLETED', '2026-04-20 02:19:37.000000', '2026-04-22 02:19:37.000000', '2026-05-02 02:19:37.000000', 'Hà Nội', 'lethid@gmail.com', 'Lê Thị D', '0123456789', 2, 'VNPAY', 'PAID', 'Phòng Deluxe có view biển tuyệt đẹp, ngắm bình minh ngay tại giường. Dịch vụ phòng rất nhanh chóng.', '2026-04-23 02:19:37.000000', 5, 'View biển cực đẹp', NULL, NULL),
('2026-04-07', '2026-04-10', 1050, 8, 256, 3, 'COMPLETED', '2026-04-07 02:19:37.000000', '2026-04-10 02:19:37.000000', '2026-05-02 02:19:37.000000', 'Đà Nẵng', 'phamvane@gmail.com', 'Phạm Văn E', '0987654321', 4, 'MOMO', 'PAID', 'Chúng tôi đã có một kỳ nghỉ gia đình tuyệt vời tại phòng Suite. Không gian rộng rãi, các con rất thích hồ bơi vô cực của khách sạn.', '2026-04-11 02:19:37.000000', 5, 'Kỳ nghỉ gia đình ấm áp', NULL, NULL),
('2026-05-02', '2026-05-05', 135, 9, 206, 3, 'CANCELLED', NULL, NULL, '2026-05-02 07:37:41.000000', 'Janpan', 'tommy@gmail.com', 'Tommy', '0247326542', 2, 'COD', 'UNPAID', NULL, NULL, NULL, NULL, NULL, NULL),
('2026-05-10', '2026-05-12', 90, 10, 206, 3, 'PENDING', NULL, NULL, '2026-05-10 07:52:15.000000', 'VietNam', '', 'Sunny', '0125613567', 2, 'COD', 'UNPAID', NULL, NULL, NULL, NULL, NULL, NULL),
('2026-05-13', '2026-05-21', 360, 11, 226, 2, 'PENDING', NULL, NULL, '2026-05-13 06:03:56.000000', NULL, 'staff@hotel.com', 'Danny', '0214732823', 1, 'COD', 'UNPAID', NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `booking_service_usage`
--

CREATE TABLE `booking_service_usage` (
  `quantity` int(11) DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  `used_date` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','IN_PROGRESS','COMPLETED') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `contact_messages`
--

CREATE TABLE `contact_messages` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `note` text DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `status` enum('PENDING','READ','REPLIED') DEFAULT NULL,
  `replied_at` datetime(6) DEFAULT NULL,
  `reply_content` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `contact_messages`
--

INSERT INTO `contact_messages` (`created_at`, `id`, `address`, `email`, `full_name`, `note`, `phone`, `status`, `replied_at`, `reply_content`) VALUES
('2026-05-02 08:22:10.000000', 1, '', 'tonny@gmail.com', 'tonny', 'Cần tìm phòng 4 người ', '0217467623', 'READ', NULL, NULL),
('2026-05-02 13:33:55.000000', 2, 'Australia', 'tommy@gmail.com', 'tommy', 'Tôi cần 1 phòng ở được 2 người vào ngày 2/9', '0217612426', 'READ', NULL, NULL),
('2026-05-10 07:53:38.000000', 3, 'VietNam', 'Sunny@gmail.com', 'Sunny', 'Tôi muốn đặt 2 phòng cho 4 người ở có view biển ', '0247327534', 'READ', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hotel_service`
--

CREATE TABLE `hotel_service` (
  `price` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel_service`
--

INSERT INTO `hotel_service` (`price`, `id`, `description`, `name`) VALUES
(5, 2, 'Wash and dry', 'Laundry'),
(30, 3, 'Full body massage', 'Spa'),
(10, 5, 'Morning buffet', 'Breakfast'),
(5, 6, 'Wash and dry', 'Laundry'),
(30, 7, 'Full body massage', 'Spa'),
(20, 8, 'Pick up or drop off', 'Airport Transfer'),
(10, 9, 'Morning buffet', 'Breakfast'),
(5, 10, 'Wash and dry', 'Laundry'),
(20, 12, 'Pick up or drop off', 'Airport Transfer'),
(10, 13, 'Morning buffet', 'Breakfast'),
(5, 14, 'Wash and dry', 'Laundry'),
(30, 15, 'Full body massage', 'Spa'),
(20, 16, 'Pick up or drop off', 'Airport Transfer'),
(10, 17, 'Morning buffet', 'Breakfast'),
(5, 18, 'Wash and dry', 'Laundry'),
(30, 19, 'Full body massage', 'Spa'),
(20, 20, 'Pick up or drop off', 'Airport Transfer'),
(10, 21, 'Morning buffet', 'Breakfast'),
(5, 22, 'Wash and dry', 'Laundry'),
(30, 23, 'Full body massage', 'Spa'),
(20, 24, 'Pick up or drop off', 'Airport Transfer'),
(10, 25, 'Morning buffet', 'Breakfast'),
(5, 26, 'Wash and dry', 'Laundry'),
(30, 27, 'Full body massage', 'Spa'),
(20, 28, 'Pick up or drop off', 'Airport Transfer'),
(10, 29, 'Morning buffet', 'Breakfast'),
(5, 30, 'Wash and dry', 'Laundry'),
(30, 31, 'Full body massage', 'Spa'),
(20, 32, 'Pick up or drop off', 'Airport Transfer'),
(10, 33, 'Morning buffet', 'Breakfast'),
(5, 34, 'Wash and dry', 'Laundry'),
(30, 35, 'Full body massage', 'Spa'),
(20, 36, 'Pick up or drop off', 'Airport Transfer');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `room_charges` double DEFAULT NULL,
  `service_charges` double DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_status` enum('PAID','UNPAID') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `price` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `status` enum('AVAILABLE','BOOKED','OCCUPIED','MAINTENANCE','CLEANING') DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`price`, `id`, `category`, `description`, `image_url`, `room_number`, `type`, `status`, `capacity`) VALUES
(45, 206, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '101', 'Single', 'AVAILABLE', 2),
(50, 207, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '102', 'Double', 'OCCUPIED', 2),
(55, 208, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '103', 'Twin', 'MAINTENANCE', 1),
(60, 209, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '104', 'Single', 'AVAILABLE', 2),
(40, 210, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '105', 'Double', 'AVAILABLE', 2),
(45, 211, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '106', 'Twin', 'AVAILABLE', 1),
(50, 212, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '107', 'Single', 'AVAILABLE', 2),
(55, 213, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '108', 'Double', 'AVAILABLE', 2),
(60, 214, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '109', 'Twin', 'AVAILABLE', 1),
(40, 215, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '110', 'Single', 'AVAILABLE', 2),
(45, 216, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '111', 'Double', 'AVAILABLE', 2),
(50, 217, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '112', 'Twin', 'AVAILABLE', 1),
(55, 218, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '201', 'Single', 'AVAILABLE', 2),
(60, 219, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '202', 'Double', 'AVAILABLE', 2),
(40, 220, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '203', 'Twin', 'AVAILABLE', 1),
(45, 221, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '204', 'Single', 'AVAILABLE', 2),
(50, 222, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '205', 'Double', 'AVAILABLE', 2),
(55, 223, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '206', 'Twin', 'AVAILABLE', 1),
(60, 224, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '207', 'Single', 'AVAILABLE', 2),
(40, 225, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '208', 'Double', 'AVAILABLE', 2),
(45, 226, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '209', 'Twin', 'BOOKED', 1),
(50, 227, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '210', 'Single', 'AVAILABLE', 2),
(55, 228, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '211', 'Double', 'AVAILABLE', 2),
(60, 229, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '212', 'Twin', 'AVAILABLE', 1),
(110, 230, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '301', 'Double', 'AVAILABLE', 3),
(120, 231, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '302', 'Twin', 'AVAILABLE', 4),
(130, 232, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '303', 'Ocean View', 'AVAILABLE', 2),
(140, 233, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '304', 'City View', 'AVAILABLE', 3),
(150, 234, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '305', 'Double', 'AVAILABLE', 4),
(160, 235, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '306', 'Twin', 'AVAILABLE', 2),
(170, 236, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '307', 'Ocean View', 'AVAILABLE', 3),
(180, 237, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '308', 'City View', 'AVAILABLE', 4),
(190, 238, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '309', 'Double', 'AVAILABLE', 2),
(100, 239, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '310', 'Twin', 'AVAILABLE', 3),
(110, 240, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '401', 'Ocean View', 'AVAILABLE', 4),
(120, 241, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '402', 'City View', 'AVAILABLE', 2),
(130, 242, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '403', 'Double', 'AVAILABLE', 3),
(140, 243, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '404', 'Twin', 'AVAILABLE', 4),
(150, 244, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '405', 'Ocean View', 'AVAILABLE', 2),
(160, 245, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '406', 'City View', 'AVAILABLE', 3),
(170, 246, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '407', 'Double', 'AVAILABLE', 4),
(180, 247, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '408', 'Twin', 'AVAILABLE', 2),
(190, 248, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '409', 'Ocean View', 'AVAILABLE', 3),
(100, 249, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '410', 'City View', 'AVAILABLE', 4),
(300, 250, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '501', 'Junior', 'AVAILABLE', 5),
(350, 251, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '502', 'Executive', 'AVAILABLE', 6),
(400, 252, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '503', 'Family', 'AVAILABLE', 7),
(450, 253, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '504', 'Presidential', 'AVAILABLE', 8),
(250, 254, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '505', 'Junior', 'AVAILABLE', 9),
(300, 255, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '506', 'Executive', 'AVAILABLE', 10),
(350, 256, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '507', 'Family', 'AVAILABLE', 4),
(400, 257, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '508', 'Presidential', 'AVAILABLE', 5),
(450, 258, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '509', 'Junior', 'AVAILABLE', 6),
(250, 259, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '510', 'Executive', 'AVAILABLE', 7),
(300, 260, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '601', 'Family', 'AVAILABLE', 8),
(350, 261, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '602', 'Presidential', 'AVAILABLE', 9),
(400, 262, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '603', 'Junior', 'AVAILABLE', 10),
(450, 263, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '604', 'Executive', 'AVAILABLE', 4),
(250, 264, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '605', 'Family', 'AVAILABLE', 5),
(300, 265, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '606', 'Presidential', 'AVAILABLE', 6),
(350, 266, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '607', 'Junior', 'AVAILABLE', 7),
(400, 267, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '608', 'Executive', 'AVAILABLE', 8),
(450, 268, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '609', 'Family', 'AVAILABLE', 9),
(250, 269, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80', '610', 'Presidential', 'AVAILABLE', 10);

-- --------------------------------------------------------

--
-- Table structure for table `system_settings`
--

CREATE TABLE `system_settings` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `system_settings`
--

INSERT INTO `system_settings` (`id`, `address`, `email`, `hotel_name`, `logo_url`, `phone`) VALUES
(1, '170 An Dương Vương, Quy Nhơn', 'info@grandhotel.com', 'Grand Hotel', '', '0123 456 678');

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
  `username` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','STAFF','CUSTOMER') DEFAULT NULL,
  `is_locked` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `full_name`, `password`, `phone`, `username`, `role`, `is_locked`) VALUES
(1, 'admin@hotel.com', 'System Admin', '$2a$10$iQp2IoQKt1RD0y26f/G3XOJtNFmFA313Q2dBXlrLXsWsvmvmJkYNW', '0123456789', 'admin', 'ADMIN', NULL),
(2, 'staff@hotel.com', 'Hotel Staff', '$2a$10$O3svH4BT38hkMoU3rfJE.OoOLSur467JDtMFcF/PziZWbiXDTxekC', '0987654321', 'staff', 'STAFF', NULL),
(3, 'john@gmail.com', 'John Doe', '$2a$10$JxnNBtL0U6nAd0ZEx77sOelS.GRAuuA89qVXI7YxK6Xoaww9bZxTa', '0111222333', 'customer', 'CUSTOMER', NULL);

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
-- Indexes for table `system_settings`
--
ALTER TABLE `system_settings`
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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `booking_service_usage`
--
ALTER TABLE `booking_service_usage`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `contact_messages`
--
ALTER TABLE `contact_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `hotel_service`
--
ALTER TABLE `hotel_service`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=270;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
