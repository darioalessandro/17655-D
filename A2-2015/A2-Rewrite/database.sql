/* Created by dario.talarico@gm.com */
/* A2 CMU */

USE inventory_v2;

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

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

USE inventory_v2;

DROP TABLE IF EXISTS `auth_logs`;
CREATE TABLE `auth_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `token` TEXT NOT NULL,
  `event` varchar(50) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
 `id` varchar(10) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` varchar(20) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
 `company_id` varchar(10) NOT NULL,
 `category` varchar(30) NOT NULL,
 `product_code` varchar(10) NOT NULL,
 `description` varchar(80) NOT NULL,
 `quantity` int(11) NOT NULL,
 `price` float(10,2) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`company_id`, `category`, `product_code`),
  FOREIGN KEY (`company_id`) REFERENCES company(id),
  FOREIGN KEY (`category`) REFERENCES product_category(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/* Insert data */

/* Create companies */

INSERT INTO inventory_v2.company (id) VALUES ('EPP');
INSERT INTO inventory_v2.company (id) VALUES ('Leaf Tech');

/* Create categories */

INSERT INTO inventory_v2.product_category (id) VALUES ('cultureboxes');
INSERT INTO inventory_v2.product_category (id) VALUES ('genomics');
INSERT INTO inventory_v2.product_category (id) VALUES ('referencematerials');
INSERT INTO inventory_v2.product_category (id) VALUES ('seeds');
INSERT INTO inventory_v2.product_category (id) VALUES ('shrubs');
INSERT INTO inventory_v2.product_category (id) VALUES ('trees');

/* Add all Leaf tech inventory data */

INSERT INTO inventory_v2.product (company_id, category, product_code, description, quantity, price) VALUES
  ('Leaf Tech','cultureboxes','PB001','Magenta Vessels',10,350.00),
  ('Leaf Tech','cultureboxes','PB002','Mebrane Raft',75,20.00),
  ('Leaf Tech','cultureboxes','PB003','Growth Mebrane Wetting Agent',200,7.50),
  ('Leaf Tech','cultureboxes','PB004','Phytatray (sterile)',59,86.50),
  ('Leaf Tech','cultureboxes','PB005','B-Cap',45,250.00),
  ('Leaf Tech','cultureboxes','PB006','Sun Bags',170,50.00),
  ('Leaf Tech','cultureboxes','PB007','Plant tissue culture vessel',25,62.60);

INSERT INTO inventory_v2.product  (company_id, category, product_code, description, quantity, price) VALUES
  ('Leaf Tech','genomics', 'GN001','Nylon transfer membrane sheet size 12.5 cm × 8 cm ',100,250.00),
  ('Leaf Tech','genomics','GN002','Nylon transfer membrane sheet size 15 cm × 20 cm',100,275.00),
  ('Leaf Tech','genomics','GN003','Nylon transfer membrane sheet size 20 cm × 20 cm ',100,300.00),
  ('Leaf Tech','genomics','GN004','Nylon transfer membrane sheet size 30 cm × 60 cm',100,325.00),
  ('Leaf Tech','genomics','GN005','Nylon transfer membrane disc size 137 mm',100,330.00),
  ('Leaf Tech','genomics','GN006',' Nylon transfer membrane roll size 20 cm × 3.5 m',99,340.00),
  ('Leaf Tech','genomics','GN007','Nylon transfer membrane roll size 20 cm × 12 m ',100,365.00),
  ('Leaf Tech','genomics','GN008','Nylon transfer membrane roll size 30 cm × 3.5 m ',100,385.00),
  ('Leaf Tech','genomics','GN009','Cover glasses size 22 mm × 22 mm',200,25.00),
  ('Leaf Tech','genomics','GN010','Cover glasses size 24 mm × 40 mm',200,25.00),
  ('Leaf Tech','genomics','GN011','Cover glasses size 24 mm × 50 mm ',200,30.00),
  ('Leaf Tech','genomics','GN012','Slide moat microscope slide incubator AC input 115 V',5,2300.00);

INSERT INTO inventory_v2.product (company_id, category, product_code, description, quantity, price) VALUES
  ('Leaf Tech','referencematerials','RF002','Advances In Medicinal Plant Research',10,82.00),
  ('Leaf Tech','referencematerials','RF003','Pharmaceutical Validation Methods',15,90.00),
  ('Leaf Tech','referencematerials','RF004','Pharmaceutical Substances: Syntheses, Patents and Applications',15,530.00),
  ('Leaf Tech','referencematerials','RF005','Handbook of Stability Testing in Pharmaceutical Development',18,195.90),
  ('Leaf Tech','referencematerials','RF006','The Encyclopedia of Pharmaceutical Drugs',22,75.00),
  ('Leaf Tech','referencematerials','RF007','Drug Eruption Reference Manual',60,22.00),
  ('Leaf Tech','referencematerials','RF008','Medical Botany: Plants Affecting Human Health',11,115.00),
  ('Leaf Tech','referencematerials','RF009','Chemical Engineering in the Pharmaceutical Industry',21,165.00),
  ('Leaf Tech','referencematerials','RF010','Plant Processing Equipment',7,100.00),
  ('Leaf Tech','referencematerials','RF011','Chemical Engineering Design: Principles, Practice, and Economics',17,98.00),
  ('Leaf Tech','referencematerials','RF001','Handbook of Pharmaceutical Manufacturing Formulation',22,900.00);

INSERT INTO inventory_v2.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EPP','seeds','MJ001','Madagascar Jasimine',100,22.00),
  ('EPP','seeds','BP001','Butterfly Pea',100,26.00),
  ('EPP','seeds','CS001','Camellia Senensis Tea',80,48.00),
  ('EPP','seeds','CO001','Chocolate Orage Rudbeckia',120,28.00),
  ('EPP','seeds','PP001','Purple Pitcher Plant',60,62.00),
  ('EPP','seeds','AG001','American Ginsing',600,12.00),
  ('EPP','seeds','QS001','Queen Sago',200,20.00),
  ('EPP','seeds','AS001','Alpine Strawberry',400,16.00),
  ('EPP','seeds','DG001','Dwarf Godetia',20,36.00),
  ('EPP','seeds','BB001','Black Bat Plant',40,100.00),
  ('EPP','seeds','MB001','Miniature Blue Popcorn',500,11.00),
  ('EPP','seeds','BM002','Black Maui Orchids',200,150.00),
  ('EPP','seeds','VF002','Venus Fly Trap',500,30.00),
  ('EPP','seeds','BC003','Black Currant Whirl Hollyhock',300,23.00);

INSERT INTO inventory_v2.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EPP', 'shrubs','BC001','Blue Camphor',60,45.00),
  ('EPP', 'shrubs','KP001','Kangaroo Paw',68,34.00),
  ('EPP', 'shrubs','YB001','Yellow Buckeye',71,28.00),
  ('EPP', 'shrubs','BB001','Butterfly Bush',36,81.00),
  ('EPP', 'shrubs','BB002','Boxwood Bush',48,31.00),
  ('EPP', 'shrubs','YC001','Yello Callicarpa',30,67.00),
  ('EPP', 'shrubs','SB001','Smoke Bush',100,26.00),
  ('EPP', 'shrubs','AF001','African Forsythia',80,38.00),
  ('EPP', 'shrubs','BP001','Blue Paeonia',60,41.00),
  ('EPP', 'shrubs','JB001','Juniper Berry',40,18.00),
  ('EPP', 'shrubs','SH001','Saaz Hops',300,6.00),
  ('EPP', 'shrubs','QS002','Queen Sago',30,60.00),
  ('EPP', 'shrubs','GN003','Great Northern Camellias',120,97.00);

INSERT INTO inventory_v2.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EPP','trees','EF001','Elephant Foot',6,800.00),
  ('EPP','trees','BB001','Black Bamboo',30,100.00),
  ('EPP','trees','BF001','Banyan Fig',13,325.00),
  ('EPP','trees','RSM001','Red Snakebark Maple',200,98.00),
  ('EPP','trees','BE001','Box Elder',400,36.00),
  ('EPP','trees','JYM001','Japanese Yama Maple',500,42.00),
  ('EPP','trees','EOM001','European Olive',62,225.00),
  ('EPP','trees','YK001','Yemen Khat',21,625.00),
  ('EPP','trees','AT001','Asian Teak',83,260.00),
  ('EPP','trees','WW001','Worm Wood',91,115.00),
  ('EPP','trees','LC001','Lemon Cypress',280,67.00),
  ('EPP','trees','GB001','Ginkgo Biloba',75,80.00),
  ('EPP','trees','CT001','Cigar Tree',10,83.00),
  ('EPP','trees','AM002','Arden Maple',40,70.00),
  ('EPP','trees','FL002','Finger Leaf Elm',16,75.00);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

