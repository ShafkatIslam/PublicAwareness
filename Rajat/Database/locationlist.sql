-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2019 at 12:02 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cdcol`
--

-- --------------------------------------------------------

--
-- Table structure for table `locationlist`
--

CREATE TABLE IF NOT EXISTS `locationlist` (
  `id` int(11) NOT NULL,
  `address` text NOT NULL,
  `Latitude` text NOT NULL,
  `Longitude` text NOT NULL,
  `countryName` text NOT NULL,
  `cityName` text NOT NULL,
  `postalCode` text NOT NULL,
  `knownName` text NOT NULL,
  `stateName` text NOT NULL,
  `datetime` text NOT NULL,
  `PhoneNumber` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locationlist`
--

INSERT INTO `locationlist` (`id`, `address`, `Latitude`, `Longitude`, `countryName`, `cityName`, `postalCode`, `knownName`, `stateName`, `datetime`, `PhoneNumber`) VALUES
(1, 'dewan Haat, Sheikh Mujib Rd, Chittagong, Bangladesh', '22.3353526', '91.81275979999998', 'Bangladesh', 'Chittagong', '', 'Bangladesh Fire Service and Civil Defense, Agrabad, Chittagong', 'Chittagong Division', '2017-10-12 19:40:38', '031-630334'),
(2, 'Anderkilla, Chittagong, Bangladesh', '22.3370824', '91.83449350000001', 'Bangladesh', 'Chittagong', '4000', 'Fire Service and Civil Defence Nandankanon', 'Chittagong Division', '2017-10-13 12:37:53', '031-630334'),
(3, 'Fire service & civil defense,Nabab Sirajuddullah Road', '22.3496861', '91.8384673', 'Bangladesh', 'Chittagong', '4000', 'Fire Service and Civil Defence,Anderkilla', 'Chittagong Division', '2017-10-14 20:39:49', '031-619575'),
(5, 'Lamar bazer,CMB moor,Kalurghat,Chittagong', '22.1956', '91.5049', 'Bangladesh', 'Chittagong', '', 'Fire service & civil defense,Lamar bazer ', 'Chittagong Division', '2017-10-16 18:58:01', '031-630233,01730-002419'),
(6, 'CEPZ Entry Rd, Chittagong 4223, Bangladesh', '22.2927868', '91.78114729999993', 'Bangladesh', 'Chittagong', '4223', 'CEPZ Fire Station', 'Chittagong Division', '2017-10-16 20:19:56', '031-800419,01730002423'),
(7, 'Nearest Shah Amanat International Airport, Potenga Rd, Chittagong 4205, Bangladesh', '22.2456982', '91.81467870000006', 'Bangladesh', 'Chittagong', '', 'Fire service & civil defense,\r\n', 'Chittagong Division', '2017-10-16 20:19:56', '0'),
(8, 'Port Internal Rd, Chittagong, Bangladesh', '22.3110316', '91.80072989999996', 'Bangladesh', 'Chittagong', '', 'Bandor fire service Station', 'Chittagong Division', '2017-10-16 20:19:59', '031-2520339 01730-002420');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `locationlist`
--
ALTER TABLE `locationlist`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `locationlist`
--
ALTER TABLE `locationlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
