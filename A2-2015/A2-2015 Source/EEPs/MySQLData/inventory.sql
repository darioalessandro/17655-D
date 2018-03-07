-- MySQL dump 10.13  Distrib 5.1.42, for Win32 (ia32)
--
-- Host: localhost    Database: inventory
-- ------------------------------------------------------
-- Server version	5.1.42-community

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
-- Table structure for table `seeds`
--

DROP TABLE IF EXISTS `seeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seeds` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seeds`
--

LOCK TABLES `seeds` WRITE;
/*!40000 ALTER TABLE `seeds` DISABLE KEYS */;
INSERT INTO `seeds` VALUES ('MJ001','Madagascar Jasimine',100,22.00),('BP001','Butterfly Pea',100,26.00),('CS001','Camellia Senensis Tea',80,48.00),('CO001','Chocolate Orage Rudbeckia',120,28.00),('PP001','Purple Pitcher Plant',60,62.00),('AG001','American Ginsing',600,12.00),('QS001','Queen Sago',200,20.00),('AS001','Alpine Strawberry',400,16.00),('DG001','Dwarf Godetia',20,36.00),('BB001','Black Bat Plant',40,100.00),('MB001','Miniature Blue Popcorn',500,11.00),('BM002','Black Maui Orchids',200,150.00),('VF002','Venus Fly Trap',500,30.00),('BC003','Black Currant Whirl Hollyhock',300,23.00);
/*!40000 ALTER TABLE `seeds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shrubs`
--

DROP TABLE IF EXISTS `shrubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shrubs` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shrubs`
--

LOCK TABLES `shrubs` WRITE;
/*!40000 ALTER TABLE `shrubs` DISABLE KEYS */;
INSERT INTO `shrubs` VALUES ('BC001','Blue Camphor',60,45.00),('KP001','Kangaroo Paw',68,34.00),('YB001','Yellow Buckeye',71,28.00),('BB001','Butterfly Bush',36,81.00),('BB002','Boxwood Bush',48,31.00),('YC001','Yello Callicarpa',30,67.00),('SB001','Smoke Bush',100,26.00),('AF001','African Forsythia',80,38.00),('BP001','Blue Paeonia',60,41.00),('JB001','Juniper Berry',40,18.00),('SH001','Saaz Hops',300,6.00),('QS002','Queen Sago',30,60.00),('GN003','Great Northern Camellias',120,97.00);
/*!40000 ALTER TABLE `shrubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trees`
--

DROP TABLE IF EXISTS `trees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trees` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trees`
--

LOCK TABLES `trees` WRITE;
/*!40000 ALTER TABLE `trees` DISABLE KEYS */;
INSERT INTO `trees` VALUES ('EF001','Elephant Foot',6,800.00),('BB001','Black Bamboo',30,100.00),('BF001','Banyan Fig',13,325.00),('RSM001','Red Snakebark Maple',200,98.00),('BE001','Box Elder',400,36.00),('JYM001','Japanese Yama Maple',500,42.00),('EOM001','European Olive',62,225.00),('YK001','Yemen Khat',21,625.00),('AT001','Asian Teak',83,260.00),('WW001','Worm Wood',91,115.00),('LC001','Lemon Cypress',280,67.00),('GB001','Ginkgo Biloba',75,80.00),('CT001','Cigar Tree',10,83.00),('AM002','Arden Maple',40,70.00),('FL002','Finger Leaf Elm',16,75.00);
/*!40000 ALTER TABLE `trees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-02-10 15:33:35
