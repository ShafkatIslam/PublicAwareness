-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 06, 2019 at 08:04 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `fsa`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `user_registration`
--

CREATE TABLE IF NOT EXISTS `user_registration` (
`id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `approved_status` int(11) NOT NULL,
  `user_type` int(1) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_registration`
--

INSERT INTO `user_registration` (`id`, `user_name`, `password`, `approved_status`, `user_type`) VALUES
(1, 'admin', '123', 1, 1),
(2, 'amin', '123', 0, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hospitallist`
--
ALTER TABLE `hospitallist`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `locationlist`
--
ALTER TABLE `locationlist`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `police_stalist`
--
ALTER TABLE `police_stalist`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_registration`
--
ALTER TABLE `user_registration`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hospitallist`
--
ALTER TABLE `hospitallist`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `locationlist`
--
ALTER TABLE `locationlist`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `police_stalist`
--
ALTER TABLE `police_stalist`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `user_registration`
--
ALTER TABLE `user_registration`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
