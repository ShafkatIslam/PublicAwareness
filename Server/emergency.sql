-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 20, 2019 at 03:47 PM
-- Server version: 10.1.38-MariaDB-cll-lve
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tikabarta_alert`
--

-- --------------------------------------------------------

--
-- Table structure for table `emergency`
--

CREATE TABLE `emergency` (
  `id` int(11) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `Details` varchar(100) NOT NULL,
  `Location` varchar(20) NOT NULL,
  `People` int(11) NOT NULL,
  `Date` varchar(20) NOT NULL,
  `Time` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Latitude` decimal(10,6) DEFAULT NULL,
  `Longitude` decimal(10,6) DEFAULT NULL,
  `Image_Name` varchar(255) NOT NULL,
  `Path` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emergency`
--

INSERT INTO `emergency` (`id`, `Type`, `Details`, `Location`, `People`, `Date`, `Time`, `Name`, `Latitude`, `Longitude`, `Image_Name`, `Path`) VALUES
(1, 'fire', 'Help', 'Chittagong', 10, '2019/05/16', '13:32', 'User', '22.360204', '91.827449', '', ''),
(2, 'crime', 'Help please,Crime Crime!', 'Chittagong', 15, '2019/05/16', '14:07', 'User', '22.360230', '91.827451', '', ''),
(3, 'crime', 'Help please,Crime Crime!', 'Chittagong', 15, '2019/05/16', '14:07', 'User', '22.360230', '91.827451', '', ''),
(4, 'fire', 'Help', 'Ctg', 5, '2019/05/19', '23:29', 'User', '22.338497', '91.833896', '', 'http://www.tikabarta.com/alert/'),
(5, 'fire', 'Help', 'Chittagong ', 5, '2019/05/19', '23:53', 'User', '22.338497', '91.833896', '', 'http://www.tikabarta.com/alert/'),
(6, 'fire', 'Help', 'Chittagong ', 5, '2019/05/19', '23:53', 'User', '22.338497', '91.833896', '', 'http://www.tikabarta.com/alert/'),
(7, 'fire', 'Help', 'Ctg', 5, '2019/05/19', '23:56', 'User', '22.344803', '91.841489', '', 'http://www.tikabarta.com/alert/'),
(8, 'fire', 'Help', 'Ctg', 5, '2019/05/20', '01:06', 'User', '22.344612', '91.840993', '', 'http://www.tikabarta.com/alert/'),
(9, 'fire', 'Help', 'ctg', 5, '2019/05/20', '01:15', 'User', '22.344612', '91.840993', '', 'http://www.tikabarta.com/alert/'),
(10, 'fire', 'Please Help', 'Chittagong', 5, '2019/05/20', '10:49', 'User', '22.344831', '91.837777', 'Chittagong', 'images/10:49.jpg'),
(11, 'fire', 'Help', 'ctg', 5, '2019/05/20', '10:54', 'User', '22.360202', '91.827460', 'ctg', 'images/ctg.jpg'),
(12, 'fire', 'Help', 'ctg', 5, '2019/05/20', '11:51', 'User', '22.360191', '91.827492', 'ctg', 'images/ctg.jpg'),
(13, 'fire', 'Help', 'ctg1', 5, '2019/05/20', '11:58', 'User', '22.360191', '91.827492', 'ctg1', 'images/ctg1.jpg'),
(14, 'fire', 'Help', 'ctg1', 5, '2019/05/20', '11:58', 'User', '22.360191', '91.827492', 'ctg1', 'images/ctg1.jpg'),
(15, 'crime', 'Help Emergency', 'Agrabad', 10, '2019/05/20', '12:07', 'User', '22.360192', '91.827480', 'Agrabad', 'images/14.jpg'),
(16, 'fire', 'Help Please,Help Them', 'GEC', 10, '2019/05/20', '14:41', 'User', '22.360191', '91.827492', 'GEC', 'images/15.jpg'),
(17, 'fire', 'Help Help', 'Chittagong ', 5, '2019/05/20', '15:26', 'User', '22.360588', '91.819527', 'Chittagong ', 'images/16.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `emergency`
--
ALTER TABLE `emergency`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `emergency`
--
ALTER TABLE `emergency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
