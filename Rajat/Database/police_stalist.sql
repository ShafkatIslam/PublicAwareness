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
-- Table structure for table `police_stalist`
--

CREATE TABLE IF NOT EXISTS `police_stalist` (
  `address` text NOT NULL,
  `cityName` text NOT NULL,
  `countryName` text NOT NULL,
  `datetime` text NOT NULL,
  `id` int(11) NOT NULL,
  `knownName` text NOT NULL,
  `Latitude` text NOT NULL,
  `Longitude` text NOT NULL,
  `postalcode` text NOT NULL,
  `statename` text NOT NULL,
  `PhoneNumber` text NOT NULL,
  `OC_Number` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `police_stalist`
--

INSERT INTO `police_stalist` (`address`, `cityName`, `countryName`, `datetime`, `id`, `knownName`, `Latitude`, `Longitude`, `postalcode`, `statename`, `PhoneNumber`, `OC_Number`) VALUES
('5 Kotwali Cir, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 1, 'Kotwali Police Station', '22.3337108', '91.83700669999996', 'https://www.gps-coordinates.net/\r\nhttp://www.cmp.gov.bd/index.php?cPath=280', 'Bangladesh\r\n', '031-619922', '01713-373256'),
('18 No. East Bakalia Ward, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 2, 'Bakalia Police Station', '22.3514436', '91.85194660000002', '', 'Bangladesh\r\n', '031-616346', '01713-373261'),
('Lal Chand Road, Chawk Bazar, Chittagong 4003, Bangladesh', 'Chittagong', 'Bangladesh', '', 3, 'Chawkbazar Police Station', '22.35755', '91.83900429999994', '', 'Bangladesh\r\n', '031-2860133', '01769-690064'),
('Sadarghat Rd, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 4, 'Sadarghat Police Station', '22.3273157', '91.83032000000003', '4000', 'Bangladesh\r\n', '031-631414', '01769-690065'),
('O.R. Nizam Rd, Chittagong 4203, Bangladesh', 'Chittagong', 'Bangladesh', '', 5, 'Panchlaish Model Police Station', '22.3628884', '91.83400489999997', '', 'Bangladesh\r\n', '031-652797', '01713-373258'),
('Baizid Thana road, Chittagong 4210, Bangladesh', 'Chittagong', 'Bangladesh', '', 6, 'Bayazid Bostamy Police Station', '22.379418', '91.82031499999994', '', 'Bangladesh\r\n', '031-683033', '01713-373262'),
('2 S Khulshi Rd, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 7, 'Khulshi Police Station', '22.3620643', '91.81102450000003', '', 'Bangladesh\r\n', '031-655537', '01713-373260'),
('280 Dhaka Trunk Rd, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 8, 'Doublemooring Police Station', '22.3384209', '91.81157089999999', '4000', 'Bangladesh\r\n', '031-715782', '01713-373268'),
('Thana Road,Pahartoli,Chittagong,Bangladesh', 'Chittagong', 'Bangladesh', '', 9, 'Pahartali Police Station', '22.357855', '91.78918650000003', '', 'Bangladesh\r\n', '031-751335', '01713-373273'),
('Lane No. 6, Block B, DR.Jahangir Kobir road,Halishahar, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 10, 'Halishahar Police Station', '22.3367695', '91.77498190000006', '', 'Bangladesh\r\n', '031-715790', '01713-373269'),
('Beside Akbar Shah Thana, Rd No. 1, Chittagong 4207, Bangladesh', 'Chittagong', 'Bangladesh', '', 11, 'Akbar Shah Police Station', '22.373649917858728', '91.78103191487685', '', 'Bangladesh\r\n', '031-2773855', '01769-690066'),
('Mooring Rd, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 12, 'Bandar Police Station', '22.3139143', '91.803538', '', 'Bangladesh\r\n', '031-728288', '01713-373267'),
('14 No Colony Rd, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 13, 'Patenga Police Station', '22.2454397', '91.8175526', '', 'Bangladesh\r\n', '031-2500026', '01713-373270'),
('6 No. East Sholoshohor Ward, Chittagong, Bangladesh', 'Chittagong', 'Bangladesh', '', 14, 'New Chandgaon Police Station', '22.3685671', '91.84858680000002', '', 'Bangladesh\r\n', '031-636763', '01713-373271'),
('M. A Aziz Road, Chittagong 4218, Bangladesh', 'Chittagong', 'Bangladesh', '', 15, 'EPZ Police Station', '22.2916231', '91.7826675', '4000', 'Bangladesh\r\n', '031-741100', '01769-690067');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `police_stalist`
--
ALTER TABLE `police_stalist`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `police_stalist`
--
ALTER TABLE `police_stalist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
