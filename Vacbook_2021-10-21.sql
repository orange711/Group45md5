# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.34)
# Database: Vacbook
# Generation Time: 2021-10-20 13:54:41 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_account` varchar(25) NOT NULL,
  `admin_password` varchar(255) NOT NULL,
  `admin_name` varchar(255) NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `admin_account` (`admin_account`) USING BTREE,
  KEY `location_id` (`location_id`) USING BTREE,
  CONSTRAINT `location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;

INSERT INTO `admin` (`admin_id`, `admin_account`, `admin_password`, `admin_name`, `location_id`)
VALUES
	(17,'Admin_1','81dc9bdb52d04dc20036dbd8313ed055','Sydney Olympic Park Vaccination Centre',9),
	(18,'Admin_2','81dc9bdb52d04dc20036dbd8313ed055','Royal Prince Alfred Hospital Vaccination Centre',7),
	(19,'Admin_3','81dc9bdb52d04dc20036dbd8313ed055','Fairfield Community Centre',40),
	(21,'Admin_4','81dc9bdb52d04dc20036dbd8313ed055','Albury Clinic',7);

/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table booking
# ------------------------------------------------------------

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `vaccine_id` int(11) NOT NULL,
  `booking_timezone` varchar(255) NOT NULL,
  `date` varchar(11) NOT NULL,
  PRIMARY KEY (`booking_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `booking_ibfk_1` (`vaccine_id`) USING BTREE,
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`vaccine_id`) REFERENCES `vaccine` (`vaccine_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;

INSERT INTO `booking` (`booking_id`, `user_id`, `vaccine_id`, `booking_timezone`, `date`)
VALUES
	(29,48,38,'19:17','2021-10-20');

/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table location
# ------------------------------------------------------------

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) NOT NULL,
  PRIMARY KEY (`location_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;

INSERT INTO `location` (`location_id`, `location`)
VALUES
	(7,'Albury (C)'),
	(8,'Armidale Regional (A)'),
	(9,'Sydney Olympic Park'),
	(10,'Balranald (A)'),
	(11,'Bathurst Regional (A)'),
	(12,'Bayside (A)'),
	(13,'Bega Valley (A)'),
	(14,'Bellingen (A)'),
	(15,'Berrigan (A)'),
	(16,'Blacktown (C)'),
	(17,'Bland (A)'),
	(18,'Blayney (A)'),
	(19,'Blue Mountains (C)'),
	(20,'Broken Hill (C)'),
	(21,'Burwood (A)'),
	(22,'Byron (A)'),
	(23,'Cabonne (A)'),
	(24,'Camden (A)'),
	(25,'Campbelltown (C) (NSW)'),
	(26,'Canada Bay (A)'),
	(27,'Canterbury-Bankstown (A)'),
	(28,'Central Coast (C) (NSW)'),
	(29,'Cessnock (C)'),
	(30,'Clarence Valley (A)'),
	(31,'Coffs Harbour (C)'),
	(32,'Coolamon (A)'),
	(33,'Cootamundra-Gundagai Regional (A)'),
	(34,'Cowra (A)'),
	(35,'Cumberland (A)'),
	(36,'Dubbo Regional (A)'),
	(37,'Dungog (A)'),
	(38,'Edward River (A)'),
	(39,'Eurobodalla (A)'),
	(40,'Fairfield (C)'),
	(41,'Federation (A)'),
	(42,'Forbes (A)'),
	(43,'NSW');

/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` char(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_lastname` varchar(20) NOT NULL,
  `user_firstname` varchar(20) NOT NULL,
  `gender` varchar(7) NOT NULL,
  `address` varchar(255) NOT NULL,
  `age` int(3) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_account` varchar(25) NOT NULL,
  `user_question` varchar(255) NOT NULL,
  `user_safe_key` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_account` (`user_account`) USING BTREE,
  KEY `age` (`age`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`user_id`, `phone_number`, `email`, `user_lastname`, `user_firstname`, `gender`, `address`, `age`, `user_password`, `user_account`, `user_question`, `user_safe_key`)
VALUES
	(48,'434273218','z5158298@gmail.com','Yan','yukang','Male','UNIT 1203 21 dunkerley place',23,'202cb962ac59075b964b07152d234b70','ward1','mother\'s name','ward'),
	(49,'0434273211','976654769@qq.com','wang','kevin','Male','Room22-202 No.28 Research Institute',23,'202cb962ac59075b964b07152d234b70','kevin','kevin','1');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vaccine
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vaccine`;

CREATE TABLE `vaccine` (
  `vaccine_id` int(11) NOT NULL AUTO_INCREMENT,
  `vaccine_type` varchar(255) NOT NULL,
  `vaccine_name` varchar(255) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `vaccine_amount` int(11) NOT NULL,
  PRIMARY KEY (`vaccine_id`) USING BTREE,
  KEY `admin_id1` (`admin_id`) USING BTREE,
  CONSTRAINT `admin_id1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `vaccine` WRITE;
/*!40000 ALTER TABLE `vaccine` DISABLE KEYS */;

INSERT INTO `vaccine` (`vaccine_id`, `vaccine_type`, `vaccine_name`, `admin_id`, `vaccine_amount`)
VALUES
	(33,'mRNA','Pfizer',17,5),
	(35,'mRNA','AstraZeneca',17,100),
	(38,'mRNA','Pfizer',19,99),
	(39,'mRNA','Moderna',19,0),
	(40,'mRNA','Pfizer',21,100);

/*!40000 ALTER TABLE `vaccine` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
