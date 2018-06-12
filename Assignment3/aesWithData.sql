CREATE DATABASE  IF NOT EXISTS `aes` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `aes`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: aes
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `courseID` varchar(3) NOT NULL,
  `courseName` varchar(45) DEFAULT NULL,
  `subjects_subjectID` varchar(3) NOT NULL,
  PRIMARY KEY (`courseID`,`subjects_subjectID`),
  KEY `fk_courses_subjects1_idx` (`subjects_subjectID`),
  CONSTRAINT `fk_courses_subjects1` FOREIGN KEY (`subjects_subjectID`) REFERENCES `subjects` (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('01','algebra1','01'),('02','algebra2','01');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_has_users`
--

DROP TABLE IF EXISTS `courses_has_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses_has_users` (
  `courses_courseID` varchar(3) NOT NULL,
  `courses_subjects_subjectID` varchar(3) NOT NULL,
  `users_userID` int(11) NOT NULL,
  PRIMARY KEY (`courses_courseID`,`courses_subjects_subjectID`,`users_userID`),
  KEY `fk_courses_has_users_users1_idx` (`users_userID`),
  KEY `fk_courses_has_users_courses1_idx` (`courses_courseID`,`courses_subjects_subjectID`),
  CONSTRAINT `fk_courses_has_users_courses1` FOREIGN KEY (`courses_courseID`, `courses_subjects_subjectID`) REFERENCES `courses` (`courseid`, `subjects_subjectid`),
  CONSTRAINT `fk_courses_has_users_users1` FOREIGN KEY (`users_userID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_has_users`
--

LOCK TABLES `courses_has_users` WRITE;
/*!40000 ALTER TABLE `courses_has_users` DISABLE KEYS */;
INSERT INTO `courses_has_users` VALUES ('01','01',1),('01','01',2),('01','01',4),('01','01',5);
/*!40000 ALTER TABLE `courses_has_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exams` (
  `examCode` varchar(4) NOT NULL,
  `examDate` date DEFAULT NULL,
  `numOfStudentsStarted` int(11) DEFAULT NULL,
  `numOfStudentsFinished` varchar(45) DEFAULT NULL,
  `numOfStudentsNotFinished` int(11) DEFAULT NULL,
  `changeDurationApproval` varchar(1) DEFAULT NULL,
  `testDurtionChange` int(11) DEFAULT NULL,
  `changeReason` varchar(45) DEFAULT NULL,
  `examAverageGrade` int(11) DEFAULT NULL,
  `examMedianGrade` int(11) DEFAULT NULL,
  `tests_testID` varchar(10) NOT NULL,
  `courses_courseID` varchar(3) NOT NULL,
  `courses_subjects_subjectID` varchar(3) NOT NULL,
  PRIMARY KEY (`examCode`,`tests_testID`,`courses_courseID`,`courses_subjects_subjectID`),
  KEY `fk_exams_tests1_idx` (`tests_testID`),
  KEY `fk_exams_courses1_idx` (`courses_courseID`,`courses_subjects_subjectID`),
  CONSTRAINT `fk_exams_courses1` FOREIGN KEY (`courses_courseID`, `courses_subjects_subjectID`) REFERENCES `courses` (`courseid`, `subjects_subjectid`),
  CONSTRAINT `fk_exams_tests1` FOREIGN KEY (`tests_testID`) REFERENCES `tests` (`testid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES ('e111','2018-07-30',1,'1',1,NULL,NULL,NULL,NULL,NULL,'010101','01','01'),('e222','2018-07-30',1,'1',1,NULL,NULL,NULL,NULL,NULL,'010101','01','01');
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionID` varchar(15) NOT NULL COMMENT 'Combination os SubjectID & QuestionNum',
  `questionNum` int(7) unsigned zerofill DEFAULT NULL,
  `teacherName` varchar(45) DEFAULT NULL,
  `questionText` varchar(256) DEFAULT NULL,
  `answer1` tinyint(4) DEFAULT '0',
  `answer2` tinyint(4) DEFAULT '0',
  `answer3` tinyint(4) DEFAULT '0',
  `answer4` tinyint(4) DEFAULT '0',
  `courses_courseID` varchar(3) NOT NULL,
  PRIMARY KEY (`questionID`,`courses_courseID`),
  UNIQUE KEY `qNum_UNIQUE` (`questionNum`),
  KEY `fk_questions_courses1_idx` (`courses_courseID`),
  CONSTRAINT `fk_questions_courses1` FOREIGN KEY (`courses_courseID`) REFERENCES `courses` (`courseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES ('01101',0000001,'yosi','1+1=?1/2/3/4 ',0,1,0,0,'01'),('01102',0000002,'yosi','2+2=?4/5/6/7',1,0,0,0,'01'),('01103',0000003,'yosi','3+3=?1/2/3/6',0,0,0,1,'01'),('01104',0000004,'yosi','5+5=?10/11/12/13',1,0,0,0,'01'),('01105',0000005,'yosi','10*10=?20/40/100/200',0,0,1,0,'01');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_test`
--

DROP TABLE IF EXISTS `student_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_test` (
  `solutionDuration` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `gradeChangeReason` varchar(45) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `gradeConfirmition` tinyint(4) DEFAULT NULL,
  `lecturerComments` varchar(45) DEFAULT NULL,
  `examSubmitted` tinyint(4) DEFAULT NULL,
  `answers` varchar(45) DEFAULT NULL,
  `users_userID` int(11) NOT NULL,
  `exams_examCode` varchar(4) NOT NULL,
  `selectedExamType` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`users_userID`,`exams_examCode`),
  KEY `fk_student_test_exams1_idx` (`exams_examCode`),
  CONSTRAINT `fk_student_test_exams1` FOREIGN KEY (`exams_examCode`) REFERENCES `exams` (`examcode`),
  CONSTRAINT `fk_student_test_users` FOREIGN KEY (`users_userID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_test`
--

LOCK TABLES `student_test` WRITE;
/*!40000 ALTER TABLE `student_test` DISABLE KEYS */;
INSERT INTO `student_test` VALUES (85,'',NULL,NULL,NULL,'good luck',1,NULL,1,'e111','manual'),(90,NULL,NULL,NULL,NULL,'good luck',1,NULL,4,'e111','manual'),(100,NULL,NULL,NULL,NULL,'good luck',1,NULL,5,'e111','computerized');
/*!40000 ALTER TABLE `student_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `subjectID` varchar(3) NOT NULL,
  `subjectName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES ('01','mathematics'),('02','physics '),('03','chemistry');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests` (
  `testID` varchar(10) NOT NULL,
  `testDuration` int(11) DEFAULT NULL,
  `pointsPerQuestion` int(11) DEFAULT NULL,
  `remarksForStudents` varchar(45) DEFAULT NULL,
  `remarksForLecturer` varchar(45) DEFAULT NULL,
  `testAuthor` varchar(45) DEFAULT NULL,
  `numOfQuestions` int(11) DEFAULT NULL,
  `courses_courseID` varchar(3) NOT NULL,
  `courses_subjects_subjectID` varchar(3) NOT NULL,
  PRIMARY KEY (`testID`,`courses_courseID`,`courses_subjects_subjectID`),
  KEY `fk_tests_courses1_idx` (`courses_courseID`,`courses_subjects_subjectID`),
  CONSTRAINT `fk_tests_courses1` FOREIGN KEY (`courses_courseID`, `courses_subjects_subjectID`) REFERENCES `courses` (`courseid`, `subjects_subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
INSERT INTO `tests` VALUES ('010101',120,20,'please read instructions','easy test','yosi',5,'01','01');
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests_has_questions`
--

DROP TABLE IF EXISTS `tests_has_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests_has_questions` (
  `tests_testID` varchar(10) NOT NULL,
  `questions_questionID` varchar(15) NOT NULL,
  `questions_courses_courseID` varchar(3) NOT NULL,
  PRIMARY KEY (`tests_testID`,`questions_questionID`,`questions_courses_courseID`),
  KEY `fk_tests_has_questions_questions1_idx` (`questions_questionID`,`questions_courses_courseID`),
  KEY `fk_tests_has_questions_tests1_idx` (`tests_testID`),
  CONSTRAINT `fk_tests_has_questions_questions1` FOREIGN KEY (`questions_questionID`, `questions_courses_courseID`) REFERENCES `questions` (`questionid`, `courses_courseid`),
  CONSTRAINT `fk_tests_has_questions_tests1` FOREIGN KEY (`tests_testID`) REFERENCES `tests` (`testid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests_has_questions`
--

LOCK TABLES `tests_has_questions` WRITE;
/*!40000 ALTER TABLE `tests_has_questions` DISABLE KEYS */;
INSERT INTO `tests_has_questions` VALUES ('010101','01101','01'),('010101','01102','01'),('010101','01103','01'),('010101','01104','01'),('010101','01105','01');
/*!40000 ALTER TABLE `tests_has_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `role` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'dani','student','dani','123'),(2,'yosi','lecturer','yosi','456'),(3,'ronit','manager','ronit','789'),(4,'tali','student','tali','012'),(5,'raz','student','raz','013');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-11 21:54:25
