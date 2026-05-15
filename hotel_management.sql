-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2026 at 09:42 AM
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
('2026-05-05', '2026-05-08', 135, 12, 270, 3, 'COMPLETED', '2026-05-05 07:26:18.000000', '2026-05-08 07:26:18.000000', '2026-05-15 07:26:18.000000', '123 Main St', 'john@gmail.com', 'John Doe', '0111222333', 2, 'VNPAY', 'PAID', 'Phòng rất đẹp và dịch vụ tuyệt vời!', '2026-05-09 07:26:18.000000', 5, 'Kỳ nghỉ tuyệt vời', NULL, NULL),
('2026-05-10', '2026-05-12', 100, 13, 271, 3, 'COMPLETED', '2026-05-10 07:26:18.000000', '2026-05-12 07:26:18.000000', '2026-05-15 07:26:18.000000', '456 Oak Ave', 'jane@gmail.com', 'Jane Smith', '0444555666', 1, 'MOMO', 'PAID', 'Trải nghiệm tốt, nhưng hơi ồn ào một chút.', '2026-05-13 07:26:18.000000', 4, 'Trải nghiệm tốt', NULL, NULL),
('2026-04-25', '2026-04-27', 110, 14, 272, 3, 'COMPLETED', '2026-04-25 07:26:18.000000', '2026-04-27 07:26:18.000000', '2026-05-15 07:26:18.000000', '789 Pine Ln', 'peter@gmail.com', 'Peter Jones', '0777888999', 3, 'COD', 'PAID', 'Khách sạn sang trọng, rất đáng tiền!', '2026-04-28 07:26:18.000000', 5, 'Sang trọng và đẳng cấp', NULL, NULL),
('2026-05-03', '2026-05-05', 340, 15, 300, 3, 'COMPLETED', '2026-05-03 07:26:18.000000', '2026-05-05 07:26:18.000000', '2026-05-15 07:26:18.000000', 'Hà Nội', 'lethid@gmail.com', 'Lê Thị D', '0123456789', 2, 'VNPAY', 'PAID', 'Phòng Deluxe có view biển tuyệt đẹp, ngắm bình minh ngay tại giường. Dịch vụ phòng rất nhanh chóng.', '2026-05-06 07:26:18.000000', 5, 'View biển cực đẹp', NULL, NULL),
('2026-04-20', '2026-04-23', 1050, 16, 320, 3, 'COMPLETED', '2026-04-20 07:26:18.000000', '2026-04-23 07:26:18.000000', '2026-05-15 07:26:18.000000', 'Đà Nẵng', 'phamvane@gmail.com', 'Phạm Văn E', '0987654321', 4, 'MOMO', 'PAID', 'Chúng tôi đã có một kỳ nghỉ gia đình tuyệt vời tại phòng Suite. Không gian rộng rãi, các con rất thích hồ bơi vô cực của khách sạn.', '2026-04-24 07:26:18.000000', 5, 'Kỳ nghỉ gia đình ấm áp', NULL, NULL);

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
  `status` enum('AVAILABLE','BOOKED','OCCUPIED','MAINTENANCE') DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`price`, `id`, `category`, `description`, `image_url`, `room_number`, `type`, `status`, `capacity`) VALUES
(45, 270, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=101', '101', 'Single', 'AVAILABLE', 2),
(50, 271, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=102', '102', 'Double', 'AVAILABLE', 2),
(55, 272, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=103', '103', 'Twin', 'AVAILABLE', 1),
(60, 273, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=104', '104', 'Single', 'AVAILABLE', 2),
(40, 274, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=105', '105', 'Double', 'AVAILABLE', 2),
(45, 275, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=106', '106', 'Twin', 'AVAILABLE', 1),
(50, 276, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=107', '107', 'Single', 'AVAILABLE', 2),
(55, 277, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=108', '108', 'Double', 'AVAILABLE', 2),
(60, 278, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=109', '109', 'Twin', 'AVAILABLE', 1),
(40, 279, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=110', '110', 'Single', 'AVAILABLE', 2),
(45, 280, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=111', '111', 'Double', 'AVAILABLE', 2),
(50, 281, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=112', '112', 'Twin', 'AVAILABLE', 1),
(55, 282, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=113', '201', 'Single', 'AVAILABLE', 2),
(60, 283, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=114', '202', 'Double', 'AVAILABLE', 2),
(40, 284, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=115', '203', 'Twin', 'AVAILABLE', 1),
(45, 285, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=116', '204', 'Single', 'AVAILABLE', 2),
(50, 286, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=117', '205', 'Double', 'AVAILABLE', 2),
(55, 287, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=118', '206', 'Twin', 'AVAILABLE', 1),
(60, 288, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=119', '207', 'Single', 'AVAILABLE', 2),
(40, 289, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=120', '208', 'Double', 'AVAILABLE', 2),
(45, 290, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=121', '209', 'Twin', 'AVAILABLE', 1),
(50, 291, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=122', '210', 'Single', 'AVAILABLE', 2),
(55, 292, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=123', '211', 'Double', 'AVAILABLE', 2),
(60, 293, 'Standard', 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.', 'https://loremflickr.com/800/600/hotel,room,bedroom/all?lock=124', '212', 'Twin', 'AVAILABLE', 1),
(110, 294, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=201', '301', 'Double', 'AVAILABLE', 3),
(120, 295, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=202', '302', 'Twin', 'AVAILABLE', 4),
(130, 296, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=203', '303', 'Ocean View', 'AVAILABLE', 2),
(140, 297, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=204', '304', 'City View', 'AVAILABLE', 3),
(150, 298, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=205', '305', 'Double', 'AVAILABLE', 4),
(160, 299, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=206', '306', 'Twin', 'AVAILABLE', 2),
(170, 300, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=207', '307', 'Ocean View', 'AVAILABLE', 3),
(180, 301, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=208', '308', 'City View', 'AVAILABLE', 4),
(190, 302, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=209', '309', 'Double', 'AVAILABLE', 2),
(100, 303, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=210', '310', 'Twin', 'AVAILABLE', 3),
(110, 304, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=211', '401', 'Ocean View', 'AVAILABLE', 4),
(120, 305, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=212', '402', 'City View', 'AVAILABLE', 2),
(130, 306, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=213', '403', 'Double', 'AVAILABLE', 3),
(140, 307, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=214', '404', 'Twin', 'AVAILABLE', 4),
(150, 308, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=215', '405', 'Ocean View', 'AVAILABLE', 2),
(160, 309, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=216', '406', 'City View', 'AVAILABLE', 3),
(170, 310, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=217', '407', 'Double', 'AVAILABLE', 4),
(180, 311, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=218', '408', 'Twin', 'AVAILABLE', 2),
(190, 312, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=219', '409', 'Ocean View', 'AVAILABLE', 3),
(100, 313, 'Deluxe', 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.', 'https://loremflickr.com/800/600/hotel,luxury,room/all?lock=220', '410', 'City View', 'AVAILABLE', 4),
(300, 314, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=301', '501', 'Junior', 'AVAILABLE', 5),
(350, 315, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=302', '502', 'Executive', 'AVAILABLE', 6),
(400, 316, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=303', '503', 'Family', 'AVAILABLE', 7),
(450, 317, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=304', '504', 'Presidential', 'AVAILABLE', 8),
(250, 318, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=305', '505', 'Junior', 'AVAILABLE', 9),
(300, 319, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=306', '506', 'Executive', 'AVAILABLE', 10),
(350, 320, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=307', '507', 'Family', 'AVAILABLE', 4),
(400, 321, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=308', '508', 'Presidential', 'AVAILABLE', 5),
(450, 322, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=309', '509', 'Junior', 'AVAILABLE', 6),
(250, 323, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=310', '510', 'Executive', 'AVAILABLE', 7),
(300, 324, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=311', '601', 'Family', 'AVAILABLE', 8),
(350, 325, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=312', '602', 'Presidential', 'AVAILABLE', 9),
(400, 326, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=313', '603', 'Junior', 'AVAILABLE', 10),
(450, 327, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=314', '604', 'Executive', 'AVAILABLE', 4),
(250, 328, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=315', '605', 'Family', 'AVAILABLE', 5),
(300, 329, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=316', '606', 'Presidential', 'AVAILABLE', 6),
(350, 330, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=317', '607', 'Junior', 'AVAILABLE', 7),
(400, 331, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=318', '608', 'Executive', 'AVAILABLE', 8),
(450, 332, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=319', '609', 'Family', 'AVAILABLE', 9),
(250, 333, 'Suite', 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.', 'https://loremflickr.com/800/600/hotel,suite,luxury/all?lock=320', '610', 'Presidential', 'AVAILABLE', 10);

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
  `is_locked` bit(1) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `full_name`, `password`, `phone`, `username`, `role`, `is_locked`, `avatar`) VALUES
(1, 'admin@hotel.com', 'System Admin', '$2a$10$iQp2IoQKt1RD0y26f/G3XOJtNFmFA313Q2dBXlrLXsWsvmvmJkYNW', '0123456789', 'admin', 'ADMIN', NULL, NULL),
(2, 'staff@hotel.com', 'Hotel Staff', '$2a$10$O3svH4BT38hkMoU3rfJE.OoOLSur467JDtMFcF/PziZWbiXDTxekC', '0987654321', 'staff', 'STAFF', NULL, NULL),
(3, 'john@gmail.com', 'John Doe', '$2a$10$JxnNBtL0U6nAd0ZEx77sOelS.GRAuuA89qVXI7YxK6Xoaww9bZxTa', '0111222333', 'customer', 'CUSTOMER', NULL, '');

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=334;

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
