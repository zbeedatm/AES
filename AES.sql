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
  `courseID` int(11) NOT NULL,
  `courseName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'algebra1'),(2,'algebra2');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exams` (
  `examCode` int(11) NOT NULL,
  `examDate` date DEFAULT NULL,
  `examType` varchar(45) DEFAULT NULL,
  `numOfStudentsStarted` int(11) DEFAULT NULL,
  `numOfStudentsFinished` varchar(45) DEFAULT NULL,
  `numOfStudentsNotFinished` int(11) DEFAULT NULL,
  `changeDurationApproval` varchar(1) DEFAULT NULL,
  `testDurtionChange` int(11) DEFAULT NULL,
  `changeReason` varchar(45) DEFAULT NULL,
  `examAverageGrade` int(11) DEFAULT NULL,
  `examMedianGrade` int(11) DEFAULT NULL,
  PRIMARY KEY (`examCode`),
  CONSTRAINT `FK_TestID` FOREIGN KEY (`examCode`) REFERENCES `tests` (`testid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionID` varchar(25) NOT NULL COMMENT 'Combination os SubjectID & QuestionNum',
  `questionNum` int(7) unsigned zerofill DEFAULT NULL,
  `teacherName` varchar(45) DEFAULT NULL,
  `questionText` blob,
  `answer1` tinyint(4) DEFAULT '0',
  `answer2` tinyint(4) DEFAULT '0',
  `answer3` tinyint(4) DEFAULT '0',
  `answer4` tinyint(4) DEFAULT '0',
  `fkSubjectID` int(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`questionID`),
  UNIQUE KEY `qNum_UNIQUE` (`questionNum`),
  KEY `FK_SubjectID` (`fkSubjectID`),
  CONSTRAINT `FK_SubjectID` FOREIGN KEY (`fkSubjectID`) REFERENCES `subjects` (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES ('S001_Q0000001',0000001,'Avi Sofer','Which of the following is illegal? \na) int i = 10;\nb) float f = 45.0;\nc) double d = 45.0;\nd) boolean b = false;\n',0,1,0,0,1),('S001_Q0000002',0000002,'Avi Sofer','Which of the following returns true? \r\na) \"John == John\"\r\nb) \"John\" = \"John\"\r\nc) \"John\".equals(\"John\")\r\nd) \"John\".equals(new Button(\"John\"))\r\n',0,0,1,0,1),('S001_Q0000003',0000003,'Avi Sofer','Where in constructor, can you place a call to a constructor defined in the super class? \r\na) The first statement in the constructor\r\nb) The last statement in the constructor\r\nc) You can not call super in constructor\r\nd) Anywhere\r\n',1,0,0,0,1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentinexam`
--

DROP TABLE IF EXISTS `studentinexam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentinexam` (
  `studentInExamID` int(11) NOT NULL,
  `solutionDuration` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `gradeChangeReason` varchar(45) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `gradeConfirmition` tinyint(4) DEFAULT NULL,
  `lecturerComments` varchar(45) DEFAULT NULL,
  `examSubmitted` varchar(1) DEFAULT NULL,
  `answers` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentInExamID`),
  CONSTRAINT `FK_ExamCode` FOREIGN KEY (`studentInExamID`) REFERENCES `exams` (`examcode`),
  CONSTRAINT `FK_UserID` FOREIGN KEY (`studentInExamID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentinexam`
--

LOCK TABLES `studentinexam` WRITE;
/*!40000 ALTER TABLE `studentinexam` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentinexam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `subjectID` int(3) unsigned zerofill NOT NULL,
  `subjectName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (001,'Java Programing Language');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests` (
  `testID` int(11) NOT NULL,
  `testDuration` int(11) DEFAULT NULL,
  `pointsPerQuestion` int(11) DEFAULT NULL,
  `remarksForStudents` varchar(45) DEFAULT NULL,
  `remarksForLecturer` varchar(45) DEFAULT NULL,
  `testAuthor` varchar(45) DEFAULT NULL,
  `numOfQuestions` int(11) DEFAULT NULL,
  PRIMARY KEY (`testID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
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
INSERT INTO `users` VALUES (111,'yosi','student','yosi','123'),(222,'ronit','lecturer','ronit','456'),(333,'guy','manager','guy','789');
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

-- Dump completed on 2018-06-03 22:19:17
