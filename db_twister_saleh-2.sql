-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 18, 2019 at 02:29 PM
-- Server version: 5.7.25
-- PHP Version: 7.1.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_twister_saleh`
--

-- --------------------------------------------------------

--
-- Table structure for table `friend`
--

CREATE TABLE `friend` (
  `userID` int(11) NOT NULL,
  `followerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend`
--

INSERT INTO `friend` (`userID`, `followerID`) VALUES
(2, 1),
(1, 1),
(2, 5),
(2, 5),
(2, 5),
(2, 5),
(2, 5),
(5, 69),
(78, 78);

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `s_key` varchar(32) NOT NULL,
  `userID` int(11) NOT NULL,
  `s_date` bigint(14) NOT NULL,
  `root` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `s_key`, `userID`, `s_date`, `root`) VALUES
(138, 'US2A0NX9JHVA439LX7EYR7ZXWLX7R3UE', 7, 1555592875995, 0),
(281, 'O136T8RQWEEJQVOIZWWUY1772I7K3H4L', 31, 1555419131196, 0),
(282, '8SRGSLSFUAANVY4HC2CSW7VRAUK9SHLD', 33, 1555419410878, 0),
(283, '2HOB3WSF164SADBYY9FGK6K3J90XYZGU', 43, 1555420807800, 0),
(285, 'O92Y0J7WCYMBWH4SKBPGPPPKZKWU3EUR', 48, 1555421027721, 0),
(286, '0UBGHBF141LAEA89PU0LNTKOYDLRLI7Z', 50, 1555421058231, 0),
(287, '0MPEX77I00O3U776W7UUFIIB9WJWUBZM', 51, 1555421200166, 0),
(289, 'HRUMUU202IY80BW1VR15I89FBSX2LGH5', 56, 1555421746315, 0),
(291, '1OUQSY9US014AXNHSADSY2OPWCG0IGVP', 61, 1555422353671, 0),
(292, '1BMMMSEBTI4FX9YVIIAXT8I0OTOZIYN9', 63, 1555422492883, 0),
(294, 'YFJ2FRJ679AZIJKPLS132NDMO3DWCWLK', 65, 1555422587527, 0),
(303, 'RNYOCSB55N8NLLX4MOWYJ52U6IMZ82L1', 66, 1555427245631, 0),
(393, 'J2GSALECS6WYRHXPSJBWMGI6WMVOMJQG', 67, 1555511566690, 0),
(429, 'LG4M51FG0MP7E5OJVKJST1WAMWDSEQ2I', 68, 1555527523200, 0),
(496, '7DH2FXWN56BV1K94QF2E6MI5WAUSY6TJ', 72, 1555535380310, 0),
(551, 'GPMFTZOQ95X9P156Q63LHAWJYZDLZ7CT', 78, 1555596929618, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `login` varchar(32) NOT NULL,
  `password` blob NOT NULL,
  `nom` varchar(32) NOT NULL,
  `prenom` varchar(32) NOT NULL,
  `sexe` varchar(12) NOT NULL,
  `birthday` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `nom`, `prenom`, `sexe`, `birthday`) VALUES
(1, 'toto', 0x746f746f, 'toto', 'toto', 'M', '2019-02-28'),
(2, 'sarah', 0x626f6e6a6f7572313233342e, 'saleh', 'sarah', 'F', '1998-07-11'),
(3, 'toto3', 0x746f746f2d746f746f33, 'nom', 'prenom', 'M', '2019-02-28'),
(4, 'toto4', 0x746f746f2d746f746f33, 'nom', 'prenom', 'M', '2019-02-28'),
(5, 'loulou', 0x626f6e6a6f75722e313233, 'll', 'loulou', 'F', '2019-03-01'),
(6, 'mickey', 0x626f6e6a6f75722e313233, 'll', 'loulou', 'F', '2019-03-01'),
(7, 'mimi', 0x426f6e6a6f7572313233342e, 'mouse', 'mickey', 'H', '1965-12-22'),
(8, 'kggkj', 0x676b676a6b, 'hhdh', 'fgkgk', 'H', 'kkgjgk'),
(10, 'theboywholives', 0x426f6e6a6f7572313233342e, 'potter', 'harry', 'H', '1980-05-31'),
(14, 'sasa', 0x313233343536373839302e61, 's', 'sarah', 'H', '2019-02-02'),
(28, 'aaa', 0x626f6e6a6f7572313233342e, 'a', 'az', 'H', '1998-08-23'),
(29, 'm', 0x626f6e6a6f75722e313233, 'll', 'loulou', 'F', '2019-03-01'),
(30, 'a', 0x426f6e6a6f7572313233342e, 'a', 'a', 'H', 'a'),
(31, 'adzf', 0x626f6e6a6f7572313233342e, 'sa', 'sa', 'H', '2012-07-97'),
(32, 'az', 0x617a65, 'n', 'l', 'H', 'ae'),
(33, 'qsd', 0x617364, 'n', 'm', 'H', 'asd'),
(34, 'm1', 0x6d26, 'm1', 'm1', 'H', 'm&'),
(35, 'asa', 0x61736173617a73312e, 'as', 'saasa', 'H', 'qsdqsd'),
(36, 'lkhlkhl', 0x626f6e6a6f7572313233342e, 'hkh', 'ihli', 'H', 'igljgio'),
(37, 'loulou4', 0x626f6e6a6f75722e313233, 'll', 'loulou', 'F', '2019-03-01'),
(38, 'lkgglligmh', 0x626f6e6a6f75722e313233, 'hlmhm', 'hklkmkhm', 'H', '2010-07-09'),
(39, 'sqdqsd', 0x617a64322e, 'ksd', 'asdqsd', 'H', 'qsd'),
(40, 'qsdqsd', 0x6d312e, 'qsdqs', 'qsdqsd', 'H', 'qsdqsd'),
(41, 'mg', 0x616231, 'ghanem', 'marwan', 'H', '14/04/1992'),
(43, 'qsdqsdqsd', '', 'qsdqsd2qsdqsd', '', 'H', ''),
(44, 'qsdsqd', 0x61, 'qsdqsd', 'qsdqsd', 'H', 'qsdsqd'),
(45, 'a2', 0x61, 'qsdqsd', 'qsd', 'H', 'qsdqsd'),
(46, 'qq', '', 'qsdqs', '', 'H', ''),
(47, 'a3', 0x61, 'a', 'a', 'H', 'a'),
(48, 'a4', 0x61, 'a', 'a', 'H', ''),
(49, 'a5', 0x61, 'a', 'a', 'H', 'aze'),
(50, 'a6', 0x61, 'a', 'a', 'H', ''),
(51, 'a7', 0x61, 'a', 'a', 'H', 'toto'),
(52, 'a8', 0x61, 'a', 'a', 'H', 'toto'),
(53, 'n', 0x6e, 'n', 'n', 'H', 'toto'),
(54, 'n1', 0x6e, 'n', 'n', 'H', 'toto'),
(55, 'qsdsqqsdqsd', 0x64717364717364, 'qsdqsdqsdqsdqsd', 'qsd', 'H', 'toto'),
(56, 'qsdqsdqqqqq', 0x717364717364, 'qsdqsdqsd', 'qsdqsd', 'H', 'toto'),
(57, 'qsdqsdqsdqsdqsd', 0x717364737164, 'qsdqsd', 'qsdqsd', 'H', 'toto'),
(58, 'qsdqsdsqd', 0x717364717364, 'qsdqsd', 'qsdqsdqsd', 'H', 'toto'),
(59, 'a19', 0x61, 'a', 'a', 'H', 'toto'),
(60, 'a120', 0x61, 'a', 'a', 'H', 'toto'),
(61, 'azd', 0x617a617a7a7a61, 'azd', 'adz', 'H', 'toto'),
(62, 'm20', 0x6d6d, 'm', 'm', 'H', 'toto'),
(63, 'aaaaa', 0x71736461, 'qsdq', 'sdqsd', 'H', 'toto'),
(64, 'a20', 0x717364717364, 'qsdqsd', 'qsdqsd', 'H', 'toto'),
(65, 'a1200', 0x617364, 'mm', 'mm', 'H', 'toto'),
(66, 'a1100', 0x61, 'a', 'a', 'H', 'toto'),
(67, 'b', 0x62, 'a', 'a', 'H', 'toto'),
(68, 'farida', 0x6c6f6c61, 'farida', 'farida', 'H', 'toto'),
(69, 'truc', 0x74727563, 'truc', 'truc', 'H', 'toto'),
(70, 'bo', 0x626f, 'bo', 'bo', 'H', 'bo'),
(71, 'gfs', 0x7366, ' v', 'fsb', 'H', 'fbs'),
(72, 'ab', 0x61, 'ab', 'a', 'H', 'a'),
(73, 'sa', 0x7361, 'sa', 'sa', 'F', 'sa'),
(74, 'm3', 0x626f6e6a6f7572, 'll', 'loulou', 'F', '2019-03-01'),
(75, 'm2', 0x62, 'll', 'loulou', 'F', '2019-03-01'),
(76, 'loulou7', 0x626f6e6a6f75722e313233, 'll', 'loulou', 'F', '2019-03-01'),
(77, 'loulou8', 0x626f6e6a6f757231342e, 'll', 'loulou', 'F', '2019-03-01'),
(78, 'canardlaque', 0x476f6f646279653031313139382e, 'giang', 'ekodie', 'F', '011198'),
(79, 'ko', 0x626f6e6a6f7572313233342e, 'a', 'a', 'F', '1998-09-01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `friend`
--
ALTER TABLE `friend`
  ADD KEY `followerID` (`followerID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `key` (`s_key`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=575;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `friend`
--
ALTER TABLE `friend`
  ADD CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`followerID`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`id`);

--
-- Constraints for table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
