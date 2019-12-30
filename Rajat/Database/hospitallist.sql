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
-- Table structure for table `hospitallist`
--

CREATE TABLE IF NOT EXISTS `hospitallist` (
  `id` int(11) NOT NULL,
  `knownName` text NOT NULL,
  `address` text NOT NULL,
  `Latitude` text NOT NULL,
  `Longitude` text NOT NULL,
  `cityName` text NOT NULL,
  `PhoneNumber` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hospitallist`
--

INSERT INTO `hospitallist` (`id`, `knownName`, `address`, `Latitude`, `Longitude`, `cityName`, `PhoneNumber`) VALUES
(1, 'Chittagong Medical College Hospital', '57, K.B. Fazlul Kader road, P.S. Panchlaish, P.O, Chittagong 4203', '22.3586085', '91.8296239', 'Chittagong', '01819-637685'),
(2, 'University Of Science & Technology Chittagong', 'Zakir Hossain Road, Foy’s Lake, Khulshi, Chittagong 4202, Bangladesh', '22.360948', '91.79754430000003', 'Chittagong', '031-659070'),
(3, 'Chittagong Metropolitan Hospital Pvt. Ltd.', '698/452 O.R. Nizam Road, Chittagong 4001, Bangladesh', '22.3590392', '91.82352119999996', 'Chittagong', '031-654732'),
(4, 'National Hospital Pvt. Ltd', '14/15, Dampara Lane, Mehedibug, Chittagong 4000, Bangladesh', '22.3549144', '91.82500149999998', 'Chittagong', '031-623713'),
(5, 'Chittagong General Hospital', 'Chittagong General Hospital,Anderkilla Chittagong, Bangladesh', '22.3411502', '91.83804350000003', 'Chittagong', '031- 715166'),
(6, 'Chittagong Diabetic General Hospital', 'Zakir Hossain Road, Khulsi, Chittagong 4212', '22.3411502', '91.83804350000003', 'Chittagong', ' 01844-041140'),
(7, 'Surgiscope Hospital Limited Unit-II', 'Panchlaish Road, Chittagong 4203, Bangladesh', '22.3598693', '91.83764040000005', 'Chittagong', '01777-222884'),
(8, 'CSCR Hospital', '1675/A, O.R Nizam Road, Chittagong 4203, Bangladesh', '22.363183', '91.8304928', 'Chittagong', '031-657071'),
(9, 'Popular hospital,Chittagong', '36 K.B. Fazlul Kader Rd, Chittagong, Bangladesh', '22.36057839999999', '91.8307092', 'Chittagong', ' 031-655401'),
(10, 'Royal Hospital (Pvt.) Limited', 'O.R. Nizam Rd, Chittagong 4001, Bangladesh', '22.3593162', '91.8238083', 'Chittagong', '031-658842'),
(11, 'Islami Bank Hospital', '03 Sheikh Mujib Rd, Chittagong, Bangladesh', '22.3220933', '91.81179459999998', 'Chittagong', '01731-253990'),
(12, 'Chittagong Health Point Hospital', '1501, Beside Meristops Clinic, O.R Nizam Road, Panclish, Chittagong, Bangladesh', '22.3594954', '91.82639789999996', 'Chittagong', ' 01915-443881'),
(13, 'Chittagong Medical Centre LTD.', '953, O.R. Nizam Rd, Chittagong 4000, Bangladesh', '22.3595019', '91.82290890000002', 'Chittagong', '031-651054'),
(14, 'Chittagong Mount Hospital (Pvt.) Ltd.', 'Surson Rd, Chittagong, Bangladesh', '22.3491549', '91.82940370000006', 'Chittagong', '031-623262'),
(15, 'Chittagong People''s Hospital Limited.', '94 K.B. Fazlul Kader Rd, Chittagong 4203, Bangladesh', '22.3597949', '91.8366694', 'Chittagong', '031-658911'),
(16, 'Chittagong Treatment Hospital pvt. Ltd.', '100, O. R Nizam Road, Chittagong 4000, Bangladesh', '22.3619011', '91.8298787', 'Chittagong', '031-652351'),
(17, 'Delta Health Care Chittagong Limited', '28, Katalgonj, Mirzapul Road, Panchalish, Chittagong 4212, Bangladesh', '22.3648121', '91.83400469999992', 'Chittagong', '03125-50005'),
(18, 'Ekushey Hospital Private Ltd', 'Sugandha Road 2, Chittagong 4212, Bangladesh', '22.3660526', '91.83341159999998', 'Chittagong', '031-657629'),
(19, 'Max Hospital & Diagnostic Ltd.', 'Late Alhaj Zahur Ahmed Chowdhury Rd, Chittagong, Bangladesh', '22.3553165', '91.82510950000005', 'Chittagong', '031-622519');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hospitallist`
--
ALTER TABLE `hospitallist`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hospitallist`
--
ALTER TABLE `hospitallist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
