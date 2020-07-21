-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: CSW
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(45) DEFAULT NULL,
  `actionname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `action_UNIQUE` (`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actiongroup`
--

DROP TABLE IF EXISTS `actiongroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actiongroup` (
  `id` int(11) NOT NULL,
  `actionid` int(11) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  `masterid` int(11) DEFAULT NULL,
  `mastername` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `association`
--

DROP TABLE IF EXISTS `association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `association` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `associationtype` varchar(255) DEFAULT NULL,
  `sourceobject` varchar(255) DEFAULT NULL,
  `targetobject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `classification`
--

DROP TABLE IF EXISTS `classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classification` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `noderepresentation` varchar(255) DEFAULT NULL,
  `classificationnode` varchar(255) DEFAULT NULL,
  `classificationscheme` varchar(255) DEFAULT NULL,
  `classifiedobject` varchar(255) DEFAULT NULL,
  `classification_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `classificationnode`
--

DROP TABLE IF EXISTS `classificationnode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classificationnode` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `classificationscheme`
--

DROP TABLE IF EXISTS `classificationscheme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classificationscheme` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `isinternal` tinyint(4) DEFAULT NULL,
  `nodetype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ebrim`
--

DROP TABLE IF EXISTS `ebrim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ebrim` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `ebrimcontent` text,
  `owner` varchar(45) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emailaddress`
--

DROP TABLE IF EXISTS `emailaddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emailaddress` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `emailaddress_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `externalidentifier`
--

DROP TABLE IF EXISTS `externalidentifier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `externalidentifier` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `identificationscheme` varchar(255) DEFAULT NULL,
  `registryobject` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `externalidentifier_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `externallink`
--

DROP TABLE IF EXISTS `externallink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `externallink` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `externaluri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `extrinsicobject`
--

DROP TABLE IF EXISTS `extrinsicobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extrinsicobject` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `mimetype` varchar(255) DEFAULT NULL,
  `isopaque` tinyint(4) DEFAULT NULL,
  `contentversioninfo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federation`
--

DROP TABLE IF EXISTS `federation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `federation` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `replicationsynclatency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groupmanager`
--

DROP TABLE IF EXISTS `groupmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupmanager` (
  `id` int(11) NOT NULL,
  `groupname` varchar(45) DEFAULT NULL,
  `groupinfo` varchar(45) DEFAULT NULL,
  `masterid` int(11) DEFAULT NULL,
  `mastername` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `identifiable`
--

DROP TABLE IF EXISTS `identifiable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `identifiable` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `identifiable_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `internationalstring`
--

DROP TABLE IF EXISTS `internationalstring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `internationalstring` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `localizedstring`
--

DROP TABLE IF EXISTS `localizedstring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localizedstring` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `lang` varchar(255) DEFAULT NULL,
  `charset` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `localizedstring_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master`
--

DROP TABLE IF EXISTS `master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `master` (
  `id` int(11) NOT NULL,
  `mastername` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `truename` varchar(45) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `dept` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `masterid2` int(11) DEFAULT NULL,
  `mastername2` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mastergroup`
--

DROP TABLE IF EXISTS `mastergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mastergroup` (
  `id` int(11) NOT NULL,
  `masterid` int(11) DEFAULT NULL,
  `mastername` varchar(45) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  `masterid2` int(11) DEFAULT NULL,
  `mastername2` varchar(45) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `subscription` varchar(255) DEFAULT NULL,
  `notification_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `objectref`
--

DROP TABLE IF EXISTS `objectref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `objectref` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `createreplica` tinyint(4) DEFAULT NULL,
  `objectref_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `primarycontact` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `personname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personname`
--

DROP TABLE IF EXISTS `personname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personname` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `postaladdress`
--

DROP TABLE IF EXISTS `postaladdress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postaladdress` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postalcode` varchar(255) DEFAULT NULL,
  `stateorprovince` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `streetnumber` varchar(255) DEFAULT NULL,
  `postaladdress_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registry`
--

DROP TABLE IF EXISTS `registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registry` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `cataloguinglatency` varchar(255) DEFAULT NULL,
  `conformanceprofile` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `replicationsynclatency` varchar(255) DEFAULT NULL,
  `specificationversion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registryobject`
--

DROP TABLE IF EXISTS `registryobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registryobject` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registrypackage`
--

DROP TABLE IF EXISTS `registrypackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrypackage` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servicebinding`
--

DROP TABLE IF EXISTS `servicebinding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicebinding` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `accessuri` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `targetbinding` varchar(255) DEFAULT NULL,
  `servicebinding_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slottype` varchar(255) DEFAULT NULL,
  `value` text,
  `slot_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `specificationlink`
--

DROP TABLE IF EXISTS `specificationlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specificationlink` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `servicebinding` varchar(255) DEFAULT NULL,
  `specificationobject` varchar(255) DEFAULT NULL,
  `usagedescription` varchar(255) DEFAULT NULL,
  `usageparameters` varchar(255) DEFAULT NULL,
  `specificationlink_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telephonenumber`
--

DROP TABLE IF EXISTS `telephonenumber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telephonenumber` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `areacode` varchar(255) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `phonetype` varchar(255) DEFAULT NULL,
  `telephonenumber_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `home` varchar(255) DEFAULT NULL,
  `lid` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `objecttype` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `versioninfo` varchar(255) DEFAULT NULL,
  `personname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `versioninfo`
--

DROP TABLE IF EXISTS `versioninfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `versioninfo` (
  `outid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) DEFAULT NULL,
  `versionname` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`outid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-06 17:02:40
