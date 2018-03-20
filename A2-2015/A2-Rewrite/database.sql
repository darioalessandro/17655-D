/* Created by dario.talarico@gm.com */
/* A2 CMU */

USE eep_operations;

DROP TABLE IF EXISTS `auth_logs`;
DROP TABLE IF EXISTS `order_item`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `company`;
DROP TABLE IF EXISTS `product_category`;

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

CREATE TABLE `company` (
 `id` varchar(10) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `product_category` (
  `id` varchar(20) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_first_name` varchar(64) DEFAULT NULL,
  `customer_last_name` varchar(64) DEFAULT NULL,
  `customer_address` varchar(256) NOT NULL,
  `customer_phone` varchar(45) NOT NULL,
  `shipped_flag` tinyint(4) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `order_item` (
  `order_id` int(11) NOT NULL,
  `product_company_id` varchar(10) NOT NULL, 
  `product_category` varchar(30) NOT NULL,
  `product_code` varchar(10) NOT NULL,
  `quantity` float(10,2) NOT NULL,
  `unit_price` float(10,2) NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  PRIMARY KEY (`order_id`, `product_company_id`, `product_category`, `product_code`),
  FOREIGN KEY (`order_id`) REFERENCES eep_operations.order(id),
  FOREIGN KEY (`product_company_id`, `product_category`, `product_code`) REFERENCES product(company_id, category, product_code)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Insert data */

/* Create companies */

INSERT INTO eep_operations.company (id) VALUES ('EEP');
INSERT INTO eep_operations.company (id) VALUES ('Leaf Tech');

/* Create categories */

INSERT INTO eep_operations.product_category (id) VALUES ('cultureboxes');
INSERT INTO eep_operations.product_category (id) VALUES ('genomics');
INSERT INTO eep_operations.product_category (id) VALUES ('referencematerials');
INSERT INTO eep_operations.product_category (id) VALUES ('seeds');
INSERT INTO eep_operations.product_category (id) VALUES ('shrubs');
INSERT INTO eep_operations.product_category (id) VALUES ('trees');
/* Add all Leaf tech inventory data */

INSERT INTO eep_operations.product (company_id, category, product_code, description, quantity, price) VALUES
  ('Leaf Tech','cultureboxes','PB001','Magenta Vessels',10,350.00),
  ('Leaf Tech','cultureboxes','PB002','Mebrane Raft',75,20.00),
  ('Leaf Tech','cultureboxes','PB003','Growth Mebrane Wetting Agent',200,7.50),
  ('Leaf Tech','cultureboxes','PB004','Phytatray (sterile)',59,86.50),
  ('Leaf Tech','cultureboxes','PB005','B-Cap',45,250.00),
  ('Leaf Tech','cultureboxes','PB006','Sun Bags',170,50.00),
  ('Leaf Tech','cultureboxes','PB007','Plant tissue culture vessel',25,62.60);

INSERT INTO eep_operations.product  (company_id, category, product_code, description, quantity, price) VALUES
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

INSERT INTO eep_operations.product (company_id, category, product_code, description, quantity, price) VALUES
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

INSERT INTO eep_operations.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EEP','seeds','MJ001','Madagascar Jasimine',100,22.00),
  ('EEP','seeds','BP001','Butterfly Pea',100,26.00),
  ('EEP','seeds','CS001','Camellia Senensis Tea',80,48.00),
  ('EEP','seeds','CO001','Chocolate Orage Rudbeckia',120,28.00),
  ('EEP','seeds','PP001','Purple Pitcher Plant',60,62.00),
  ('EEP','seeds','AG001','American Ginsing',600,12.00),
  ('EEP','seeds','QS001','Queen Sago',200,20.00),
  ('EEP','seeds','AS001','Alpine Strawberry',400,16.00),
  ('EEP','seeds','DG001','Dwarf Godetia',20,36.00),
  ('EEP','seeds','BB001','Black Bat Plant',40,100.00),
  ('EEP','seeds','MB001','Miniature Blue Popcorn',500,11.00),
  ('EEP','seeds','BM002','Black Maui Orchids',200,150.00),
  ('EEP','seeds','VF002','Venus Fly Trap',500,30.00),
  ('EEP','seeds','BC003','Black Currant Whirl Hollyhock',300,23.00);

INSERT INTO eep_operations.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EEP', 'shrubs','BC001','Blue Camphor',60,45.00),
  ('EEP', 'shrubs','KP001','Kangaroo Paw',68,34.00),
  ('EEP', 'shrubs','YB001','Yellow Buckeye',71,28.00),
  ('EEP', 'shrubs','BB001','Butterfly Bush',36,81.00),
  ('EEP', 'shrubs','BB002','Boxwood Bush',48,31.00),
  ('EEP', 'shrubs','YC001','Yello Callicarpa',30,67.00),
  ('EEP', 'shrubs','SB001','Smoke Bush',100,26.00),
  ('EEP', 'shrubs','AF001','African Forsythia',80,38.00),
  ('EEP', 'shrubs','BP001','Blue Paeonia',60,41.00),
  ('EEP', 'shrubs','JB001','Juniper Berry',40,18.00),
  ('EEP', 'shrubs','SH001','Saaz Hops',300,6.00),
  ('EEP', 'shrubs','QS002','Queen Sago',30,60.00),
  ('EEP', 'shrubs','GN003','Great Northern Camellias',120,97.00);

INSERT INTO eep_operations.product (company_id, category, product_code, description, quantity, price) VALUES
  ('EEP','trees','EF001','Elephant Foot',6,800.00),
  ('EEP','trees','BB001','Black Bamboo',30,100.00),
  ('EEP','trees','BF001','Banyan Fig',13,325.00),
  ('EEP','trees','RSM001','Red Snakebark Maple',200,98.00),
  ('EEP','trees','BE001','Box Elder',400,36.00),
  ('EEP','trees','JYM001','Japanese Yama Maple',500,42.00),
  ('EEP','trees','EOM001','European Olive',62,225.00),
  ('EEP','trees','YK001','Yemen Khat',21,625.00),
  ('EEP','trees','AT001','Asian Teak',83,260.00),
  ('EEP','trees','WW001','Worm Wood',91,115.00),
  ('EEP','trees','LC001','Lemon Cypress',280,67.00),
  ('EEP','trees','GB001','Ginkgo Biloba',75,80.00),
  ('EEP','trees','CT001','Cigar Tree',10,83.00),
  ('EEP','trees','AM002','Arden Maple',40,70.00),
  ('EEP','trees','FL002','Finger Leaf Elm',16,75.00);
  
INSERT INTO eep_operations.order (customer_first_name, customer_last_name, customer_address, customer_phone, shipped_flag) VALUES 
	('Paul', 'Fenton', '123 Blue Cheese Ave. The Moon', '222-333-4092', false),
    ('Dario', 'Lecina', '1600 Pennsylvania Ave.', '222-323-4444', false),
    ('Kayla', 'VanHaverbeck', '1 Route 66', '222-323-4433', true);
    
INSERT INTO eep_operations.order_item (order_id, product_company_id, product_category, product_code, quantity, unit_price) VALUES 
	(1, 'Leaf Tech', 'cultureboxes', 'PB001', 1, 350.00),
    (1, 'Leaf Tech', 'cultureboxes', 'PB002', 1, 20.00),
    (2, 'Leaf Tech', 'cultureboxes', 'PB002', 2, 20.00),
    (2, 'Leaf Tech', 'cultureboxes', 'PB003', 1, 7.50),
    (3, 'EEP', 'shrubs', 'SH001', 10, 6.00);