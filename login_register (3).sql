-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2023 at 10:40 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `login_register`
--

-- --------------------------------------------------------

--
-- Table structure for table `contact_info`
--

CREATE TABLE `contact_info` (
  `id` int(11) NOT NULL,
  `person1` varchar(255) DEFAULT NULL,
  `contact1` varchar(20) DEFAULT NULL,
  `relationship1` varchar(50) DEFAULT NULL,
  `person2` varchar(255) DEFAULT NULL,
  `contact2` varchar(20) DEFAULT NULL,
  `relationship2` varchar(50) DEFAULT NULL,
  `person3` varchar(255) DEFAULT NULL,
  `contact3` varchar(20) DEFAULT NULL,
  `relationship3` varchar(50) DEFAULT NULL,
  `newid` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `contact_info`
--

INSERT INTO `contact_info` (`id`, `person1`, `contact1`, `relationship1`, `person2`, `contact2`, `relationship2`, `person3`, `contact3`, `relationship3`, `newid`) VALUES
(18, 'fbf', '89586', 'dB jrf', 'rvgey', '58955', 'gdhgr', '4hvdey', '58545', 'rghr', 'ghfbb');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fullname` text NOT NULL,
  `email` varchar(225) NOT NULL,
  `username` varchar(225) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `password` varchar(300) NOT NULL,
  `reset_password_otp` text NOT NULL,
  `reset_password_created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `email`, `username`, `phone_number`, `password`, `reset_password_otp`, `reset_password_created_at`) VALUES
(2, 'steve', 'steve', 'steve', '653113651', 'sweet', '', '2023-04-09 16:34:25'),
(3, 'kwame', 'kwame', 'kwame', '562666', 'sweet', '', '2023-04-09 16:34:25'),
(4, 'bennet', 'nett', 'nett', '02456698', 'sweet', '', '2023-04-09 16:34:25'),
(5, 'belinda', 'linda', 'linda', '02463321', 'sweet', '', '2023-04-09 16:34:25'),
(6, 'bismark nyantakyi', 'mark', 'mark', '0248911456', 'sweet', '', '2023-04-09 16:34:25'),
(7, 'bennet', 'sasubennet1999@gmail.com', 'bennetsasu', '0269531605', 'bennet123', '53292706', '2023-04-10 04:15:21'),
(8, 'frank raid', 'raid@gmail.com', 'raid', '021365478', 'sweet', '', '2023-04-09 16:34:25'),
(14, 'Kelvin Nyantakyi Erdmann ', 'kelvinerdmann@gmail.com', 'Erdmann ', '0248911456', 'Alucard1.', '', '0000-00-00 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contact_info`
--
ALTER TABLE `contact_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`newid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`,`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `contact_info`
--
ALTER TABLE `contact_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
