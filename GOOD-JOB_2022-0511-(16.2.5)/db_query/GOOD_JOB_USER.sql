-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-11-08 14:06
-- 서버 버전: 5.7.35
-- PHP 버전: 7.3.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `stepjump73`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `GOOD_JOB_USER`
--

CREATE TABLE `GOOD_JOB_USER` (
  `userID` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `userPassword` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `userAdminPassword` varchar(20) NOT NULL COMMENT '유저관리자 암호',
  `userName` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `userAge` int(11) NOT NULL,
  `userComment` varchar(100) DEFAULT NULL COMMENT '사용자 설명'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `GOOD_JOB_USER`
--

INSERT INTO `GOOD_JOB_USER` (`userID`, `userPassword`, `userAdminPassword`, `userName`, `userAge`, `userComment`) VALUES
('stepjump', '7312', '4978', '구시연', 12, '귀엽고 착하고 줄넘기 정말 잘하는 구~시연'),
('test1', '1234', '1235', '연습이', 12, '착한'),
('onestore', '1234', '1234', '두 번 ', 46, '롤 모바일 '),
('kkm3769', 'kkm3532', 'km71002915', '김지안', 0, '땡깡지안');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `GOOD_JOB_USER`
--
ALTER TABLE `GOOD_JOB_USER`
  ADD PRIMARY KEY (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
