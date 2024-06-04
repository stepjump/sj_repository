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
-- 테이블 구조 `GOOD_JOB_ACT_LIST`
--

CREATE TABLE `GOOD_JOB_ACT_LIST` (
  `userID` varchar(20) NOT NULL,
  `ACTIVE_NO` int(4) NOT NULL,
  `ACTIVE_MONEY` int(11) NOT NULL,
  `ACTIVE_LIST` varchar(3000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `USE_YN` varchar(2) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `GOOD_JOB_ACT_LIST`
--

INSERT INTO `GOOD_JOB_ACT_LIST` (`userID`, `ACTIVE_NO`, `ACTIVE_MONEY`, `ACTIVE_LIST`, `USE_YN`) VALUES
('stepjump', 3, 1000, '일반 쓰레기 버리기', 'Y'),
('stepjump', 4, 1000, '분리수거 버리기', 'Y'),
('stepjump', 1, 500, '아빠 구두닦기', 'Y'),
('stepjump', 2, 3000, '음식물 쓰레기 버리기', 'Y'),
('stepjump', 5, 2000, '분리수거 혼자 버리기', 'Y'),
('stepjump', 6, 1000, '설걷이 혼자하기', 'Y'),
('stepjump', 7, 500, '설걷이 도와주기', 'Y'),
('stepjump', 8, 500, '청소 도와주기', 'Y'),
('stepjump', 9, 300, '현관 신발정리', 'Y'),
('stepjump', 10, 500, '시연이 방정리', 'Y'),
('stepjump', 11, 500, '커피 채우기', 'Y'),
('stepjump', 12, 300, '시연이 옷정리', 'Y'),
('stepjump', 13, 500, '식사준비 정리', 'Y'),
('stepjump', 14, 300, '물고기 밥주기', 'Y'),
('stepjump', 15, 1000, '숙제하기 싫을 때 열심히 해서 다하기', 'Y'),
('stepjump', 16, 300, '스마트폰 닦기', 'Y'),
('stepjump', 17, 300, '안경 닦기', 'Y'),
('stepjump', 18, 300, '밖에 나갔다가 옷정리하기, 옷걸이', 'Y'),
('stepjump', 19, 200, '밖에 나갔다가 손씻기', 'Y'),
('stepjump', 20, 300, '밖에 나갔다가 발씻기', 'Y'),
('stepjump', 21, 1000, '일찍자기 (밤 10시 전에)', 'Y'),
('stepjump', 22, 500, '커피 타기', 'Y'),
('stepjump', 23, 500, '학원 한 개도 않빼먹고 모두 갔다오기', 'Y'),
('stepjump', 24, 500, '학교 잘 갔다오기', 'Y'),
('stepjump', 25, 1000, '토,일 엄마,아빠 청소할 때 숙제하기', 'Y'),
('stepjump', 26, 500, '심부름 같이 가기', 'Y'),
('stepjump', 27, 1000, '심부름 혼자 가기', 'Y'),
('stepjump', 28, 1500, '직접 밥하기', 'Y'),
('stepjump', 29, 500, '요리재료준비', 'Y'),
('stepjump', 30, 500, '하루 열온도체크 다하기', 'Y'),
('stepjump', 31, 500, '오전에 혼자 원격수업 하기', 'Y'),
('stepjump', 32, 500, '씨앗물 하루에 3번 주기', 'Y'),
('stepjump', 33, 300, '고봉밥 다먹기', 'Y'),
('stepjump', 34, 200, '학원 갔다오기', 'Y'),
('stepjump', 35, 600, '(팔)에 성장주사 맞기', 'Y'),
('stepjump', 36, 800, '(엉덩이)에 성장주사 맞기', 'Y'),
('stepjump', 37, 10000, '멋진 상장 받아오기', 'Y'),
('stepjump', 38, 300, '얇은책 다읽기', 'Y'),
('stepjump', 39, 500, '살짝 두꺼운책 다읽기', 'Y'),
('stepjump', 40, 1000, '두꺼운책 다읽기', 'Y'),
('stepjump', 41, 20000, '줄넘기 승급하기', 'Y'),
('stepjump', 42, 30000, '기본한달 용돈', 'Y'),
('stepjump2', 2, 200, '착한일항목02', 'Y'),
('stepjump2', 1, 100, '착한일항목01', 'Y'),
('stepjump2', 3, 300, '착한일항목03', 'Y'),
('onestore', 1, 1000, 'test1', 'Y'),
('onestore', 2, 2000, 'test2', 'Y'),
('onestore', 3, 3000, 'test3', 'Y'),
('kkm3769', 1, 1000, '밥잘먹기', 'Y'),
('kkm3769', 2, 1000, '땡깡 안부리기', 'Y'),
('kkm3769', 3, 1000, '학교잘갔다오기', 'Y');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `GOOD_JOB_ACT_LIST`
--
ALTER TABLE `GOOD_JOB_ACT_LIST`
  ADD PRIMARY KEY (`userID`,`ACTIVE_NO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
