DROP DATABASE IF EXISTS accounting_ledger;
 
CREATE DATABASE IF NOT EXISTS accounting_ledger;
 
use accounting_ledger;
 
DROP TABLE IF EXISTS transactions;
 
CREATE TABLE IF NOT EXISTS transactions  (
transaction_id INT NOT NULL AUTO_INCREMENT,
date DATE  NOT NULL,
time TIME NOT NULL,
description varchar(200),
vendor VARCHAR (100) NOT NULL, 
amount DECIMAL (10,2) NOT NULL DEFAULT 0,
 
PRIMARY KEY (transaction_id)
);
 
INSERT INTO transactions ( date, time , description, vendor,amount)
VALUES ( '2025-04-29', '09:44:11' , 'testSave', 'Izel', 12.0),
('2025-04-25','20:45:11', 'Deposit','Izel', 500.00),
('2025-04-22','11:40:22','Donation','Local Shelter',-20.00),
('2025-04-21','12:30:30','Online Purchase','eBay',-25.75),
('2025-04-21','14:35:17','Transfer from Savings','Chase',300.00),
('2025-04-20','09:05:15','Dinner','Olive Garden',-28.40),
('2025-03-29','08:45:01','Training Program','CodeAcademy',-75.00),
('2025-03-28','16:20:00','Payment from Client','WebCo',300.00),
('2025-03-27','11:45:33','Doctor Visit','HealthCare Inc',-90.00),
('2025-03-26','12:30:00','Gym Fee','Golds Gym',-45.00),
('2024-04-26','13:00:00','Laptop Purchase','Best Buy',-850.00),
('2024-04-19','09:30:00','Gift','Izel',50.00),
('2025-04-30','22:59:22','test','alper',10.0),
('2025-04-30','23:32:11','izel','izel',12.0),
('2025-05-01','13:45:37','test colors','izel',-10.0),
('2025-05-01','23:13:49','Description','Vendor',100.0),
('2025-05-02','00:10:38','izel','testing',1.0),
('2025-05-02','08:56:04','Description','Vendor',10.0),
('2025-05-02','08:56:21','Description for Payment','Vendor',-100.0);

SELECT * FROM transactions;