-- H2 2.3.232; 
;              
CREATE USER IF NOT EXISTS "SA" SALT 'f5652a0014541f15' HASH '12da528694abd4e8370cd7f026f1f2b89f88660fa7a3ecb86f17ee1936321158' ADMIN;          
CREATE SEQUENCE "PUBLIC"."USERS_SEQ" START WITH 1 RESTART WITH 201 INCREMENT BY 50;            
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HT_BEVERAGE"(
    "ID" BIGINT NOT NULL
);              
ALTER TABLE "PUBLIC"."HT_BEVERAGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_A" PRIMARY KEY("ID");   
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HT_BEVERAGE;              
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_BEVERAGE"(
    "IN_STOCK" INTEGER,
    "IS_ALCOHOLIC" BOOLEAN,
    "NO_OF_BOTTLES" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID" BIGINT,
    "PRICE" FLOAT(53),
    "VOLUME" FLOAT(53),
    "VOLUME_PERCENT" FLOAT(53),
    "BEVERAGE_TYPE" CHARACTER VARYING(31) NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "PICTURE" CHARACTER VARYING(255),
    "SUPPLIER" CHARACTER VARYING(255)
);               
ALTER TABLE "PUBLIC"."HTE_BEVERAGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("HTE_IDENTITY");        
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_BEVERAGE;             
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_BOTTLE"(
    "IN_STOCK" INTEGER,
    "IS_ALCOHOLIC" BOOLEAN,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID" BIGINT,
    "PRICE" FLOAT(53),
    "VOLUME" FLOAT(53),
    "VOLUME_PERCENT" FLOAT(53),
    "BEVERAGE_TYPE" CHARACTER VARYING(31) NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "PICTURE" CHARACTER VARYING(255),
    "SUPPLIER" CHARACTER VARYING(255)
);              
ALTER TABLE "PUBLIC"."HTE_BOTTLE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_7" PRIMARY KEY("HTE_IDENTITY");          
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_BOTTLE;               
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_CRATE"(
    "IN_STOCK" INTEGER,
    "NO_OF_BOTTLES" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "BOTTLE_ID" BIGINT,
    "ID" BIGINT,
    "PRICE" FLOAT(53),
    "BEVERAGE_TYPE" CHARACTER VARYING(31) NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "PICTURE" CHARACTER VARYING(255)
);     
ALTER TABLE "PUBLIC"."HTE_CRATE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("HTE_IDENTITY");           
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_CRATE;
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_USERS"(
    "BIRTHDAY" DATE,
    "RN_" INTEGER NOT NULL,
    "ID" BIGINT,
    "EMAIL" CHARACTER VARYING(255),
    "FULL_NAME" CHARACTER VARYING(255),
    "PASSWORD" CHARACTER VARYING(255),
    "ROLE" CHARACTER VARYING(255),
    "USERNAME" CHARACTER VARYING(255)
);           
ALTER TABLE "PUBLIC"."HTE_USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B2" PRIMARY KEY("RN_");   
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_USERS;
CREATE CACHED TABLE "PUBLIC"."ORDERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 26) NOT NULL,
    "PRICE" FLOAT(53) NOT NULL,
    "USER_ID" BIGINT NOT NULL,
    "ORDER_STATUS" CHARACTER VARYING(255)
);    
ALTER TABLE "PUBLIC"."ORDERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8B" PRIMARY KEY("ID");       
-- 24 +/- SELECT COUNT(*) FROM PUBLIC.ORDERS;  
INSERT INTO "PUBLIC"."ORDERS" VALUES
(1, 5.0, 1, 'Pending'),
(2, 19.8, 1, 'Cancelled'),
(3, 15.0, 1, 'Cancelled'),
(4, 10.0, 1, 'Cancelled'),
(5, 4.8, 1, 'Cancelled'),
(6, 10.0, 1, 'Pending'),
(7, 1.2, 1, 'Completed'),
(8, 2.5, 1, 'Cancelled'),
(9, 2.5, 1, 'Confirmed'),
(10, 45.0, 1, 'Cancelled'),
(11, 2.4, 2, 'Shipped'),
(12, 110.39999999999999, 2, 'Pending'),
(13, 15.0, 1, 'Cancelled'),
(14, 15.0, 1, 'Cancelled'),
(15, 5.0, 1, 'Cancelled'),
(16, 5.0, 1, 'Cancelled'),
(17, 2.5, 1, 'Pending'),
(18, 2.5, 2, 'Comfirmed'),
(19, 3.7, 2, 'Cancelled'),
(20, 15.0, 1, 'Cancelled'),
(21, 30.0, 53, 'Pending'),
(22, 30.0, 53, 'Confirmed'),
(24, 140.98000000000002, 53, 'Cancelled'),
(25, 13.7, 53, 'Pending');             
CREATE CACHED TABLE "PUBLIC"."BEVERAGE"(
    "BEVERAGE_TYPE" CHARACTER VARYING(31) NOT NULL,
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 19) NOT NULL,
    "IN_STOCK" INTEGER,
    "NAME" CHARACTER VARYING(255) NOT NULL,
    "PICTURE" CHARACTER VARYING(255),
    "PRICE" FLOAT(53) NOT NULL
);              
ALTER TABLE "PUBLIC"."BEVERAGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_A0" PRIMARY KEY("ID");     
-- 15 +/- SELECT COUNT(*) FROM PUBLIC.BEVERAGE;
INSERT INTO "PUBLIC"."BEVERAGE" VALUES
('BOTTLE', 1, 103, 'CocaCola', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s', 2.5),
('CRATE', 2, 19, 'Cocacola Crate 24 Bottle', 'https://image.invaluable.com/housePhotos/abell/07/764507/H0068-L364202388.JPG', 5.0),
('CRATE', 3, 8, 'CocaCola 10 Bottle Crate', 'https://image.invaluable.com/housePhotos/abell/07/764507/H0068-L364202388.JPG', 5.0),
('BOTTLE', 4, 210, 'Pepsi', 'https://www.roforewards.com/wp-content/themes/rofo/images/rofo_Pepsi_125pts_795x600.jpg', 1.2),
('BOTTLE', 5, 114, 'Absolute Vodka', 'https://www.lidl.de/assets/1591a56989490e4550ec9143a028fb5a.jpeg', 15.0),
('CRATE', 6, 35, 'Pepsi - 24 bottle pack', 'https://www.yewel.de/media/image/product/3980/lg/pepsi-cola-zero-dose-24x033l.webp', 21.36),
('BOTTLE', 7, 220, 'Volvic', 'https://img.rewe-static.de/0263349/27778849_digital-image.png?output-quality=60&fit=inside|840:840&background-color=fff', 1.15),
('BOTTLE', 9, 150, 'Volvic', 'https://img.rewe-static.de/0263349/27778849_digital-image.png?output-quality=60&fit=inside|840:840&background-color=fff', 1.5),
('BOTTLE', 11, 50, 'Volvic', 'https://img.rewe-static.de/0263349/27778849_digital-image.png?output-quality=60&fit=inside|840:840&background-color=fff', 1.0),
('BOTTLE', 12, 220, 'Volvic', 'https://img.rewe-static.de/0263349/27778849_digital-image.png?output-quality=60&fit=inside|840:840&background-color=fff', 1.15),
('BOTTLE', 13, 220, 'Volvic', 'https://img.rewe-static.de/0263349/27778849_digital-image.png?output-quality=60&fit=inside|840:840&background-color=fff', 1.15),
('BOTTLE', 14, 250, 'Augustiner Lagerbier Hell', 'https://xn--getrnke-bestellen-tqb.klauss-und-klauss.de/media/image/3c/ab/09/augustiner-lagerbier-hell-20x-0-5l-bier-deutschland.jpg', 1.89),
('CRATE', 15, 80, 'Augustiner Lagerbier Hell 500ml - 24 Crate', 'https://i.hood.de/fit-in/3000x3000/filters:no_upscale()/images/53955/539557401.jpg', 44.9),
('BOTTLE', 16, 70, U&'Paulaner Original M\00fcnchner Hell 5 Liter', 'https://www.lidl.de/assets/gcpc887af25d3cb4648987b0e4a25c60dfe.jpeg', 15.99),
('BOTTLE', 17, 35, 'Hibiki Japanese Harmony 700 ml', 'https://m.media-amazon.com/images/I/712+S5IwzCL._AC_SL1280_.jpg', 94.0);              
CREATE CACHED TABLE "PUBLIC"."BOTTLE"(
    "IS_ALCOHOLIC" BOOLEAN,
    "SUPPLIER" CHARACTER VARYING(255) NOT NULL,
    "VOLUME" FLOAT(53) NOT NULL,
    "VOLUME_PERCENT" FLOAT(53) NOT NULL,
    "ID" BIGINT NOT NULL
);       
ALTER TABLE "PUBLIC"."BOTTLE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_75" PRIMARY KEY("ID");       
-- 11 +/- SELECT COUNT(*) FROM PUBLIC.BOTTLE;  
INSERT INTO "PUBLIC"."BOTTLE" VALUES
(FALSE, 'Coca Cola Company', 1.5, 0.0, 1),
(FALSE, 'Pepsico', 0.5, 0.0, 4),
(TRUE, 'Pernod Ricard', 0.7, 40.0, 5),
(FALSE, 'Volvic', 1.5, 0.0, 7),
(FALSE, 'Volvic', 2.0, 0.0, 9),
(FALSE, 'Volvic', 1.0, 0.0, 11),
(FALSE, 'Volvic', 1.5, 0.0, 12),
(FALSE, 'Volvic', 1.5, 0.0, 13),
(TRUE, U&'Augustiner Br\00e4u Wagner KG', 0.5, 5.2, 14),
(TRUE, 'Paulaner Brauerei GmbH & Co. KG', 5.0, 4.9, 16),
(TRUE, 'Suntory Hibiki', 0.7, 43.0, 17);          
CREATE CACHED TABLE "PUBLIC"."CRATE"(
    "NO_OF_BOTTLES" INTEGER NOT NULL,
    "ID" BIGINT NOT NULL,
    "BOTTLE_ID" BIGINT
);
ALTER TABLE "PUBLIC"."CRATE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3D" PRIMARY KEY("ID");        
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.CRATE;    
INSERT INTO "PUBLIC"."CRATE" VALUES
(24, 2, 1),
(10, 3, 1),
(24, 6, 4),
(24, 15, 14);          
CREATE CACHED TABLE "PUBLIC"."ORDER_ITEM"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 35) NOT NULL,
    "POSITION" CHARACTER VARYING(255),
    "PRICE" FLOAT(53),
    "BEVERAGE_ID" BIGINT,
    "ORDER_ID" BIGINT NOT NULL,
    "QUANTITY" INTEGER
);          
ALTER TABLE "PUBLIC"."ORDER_ITEM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");    
-- 32 +/- SELECT COUNT(*) FROM PUBLIC.ORDER_ITEM;              
INSERT INTO "PUBLIC"."ORDER_ITEM" VALUES
(1, '2', 2.5, 1, 1, 2),
(2, '3', 5.0, 2, 2, 3),
(3, '4', 1.2, 4, 2, 4),
(4, '3', 5.0, 2, 3, 3),
(5, '4', 2.5, 1, 4, 4),
(6, '4', 1.2, 4, 5, 4),
(7, '2', 5.0, 3, 6, 2),
(8, '1', 1.2, 4, 7, 1),
(9, '1', 2.5, 1, 8, 1),
(10, '1', 2.5, 1, 9, 1),
(11, '3', 5.0, 2, 10, 3),
(12, '2', 15.0, 5, 10, 2),
(13, '2', 1.2, 4, 11, 2),
(14, '3', 1.2, 4, 12, 3),
(15, '5', 21.36, 6, 12, 5),
(16, '1', 15.0, 5, 13, 1),
(17, '1', 15.0, 5, 14, 1),
(18, '1', 5.0, 2, 15, 1),
(19, '1', 5.0, 2, 16, 1),
(20, '1', 2.5, 1, 17, 1),
(21, '1', 2.5, 1, 18, 1),
(22, '1', 2.5, 1, 19, 1),
(23, '1', 1.2, 4, 19, 1),
(24, '3', 5.0, 2, 20, 3),
(25, '2', 15.0, 5, 21, 2),
(26, '0', 15.0, 5, 22, 2),
(29, '0', 15.0, 5, 24, 1),
(30, '1', 15.99, 16, 24, 2),
(31, '2', 94.0, 17, 24, 1),
(32, '1', 2.5, 1, 25, 1),
(33, '2', 5.0, 3, 25, 2),
(34, '3', 1.2, 4, 25, 1);             
CREATE CACHED TABLE "PUBLIC"."USERS"(
    "ID" BIGINT NOT NULL,
    "BIRTHDAY" DATE,
    "FULL_NAME" CHARACTER VARYING(255),
    "PASSWORD" CHARACTER VARYING(255),
    "ROLE" CHARACTER VARYING(255),
    "USERNAME" CHARACTER VARYING(255),
    "EMAIL" CHARACTER VARYING(255)
);            
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4D" PRIMARY KEY("ID");        
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.USERS;    
INSERT INTO "PUBLIC"."USERS" VALUES
(1, DATE '2025-01-01', 'Dipto Paul', '$2a$10$QqB19UrEi4yujYQYJiyaJ.PUFWdEcjoYATpdYE.Hzh68gQBwREX92', 'ROLE_ADMIN', 'diptopaul', 'imdpauld@gmail.com'),
(2, NULL, 'Mr User', '$2a$10$TuwGf.zN5KRErFVFYwrMEONYzx4vkBq6nv0wD4vll1HMIf4r8EFJS', 'ROLE_USER', 'user123', 'mruser@yahoo.com'),
(53, DATE '2025-01-02', 'Dipto Paul', '$2a$10$hru2tyG444UfJuF/TiwFwuE9r4acW3k5fKPKkO7X8KH3yyxCHt/MO', 'ROLE_USER', 'pauldipto', 'pauldip@gmail.com'),
(102, NULL, 'Dipto', '$2a$10$2EY/w6TxshJhf6QtGKF3/.xSGLuekMjzVVI1n9hHZh0ba9HhwRKdy', 'ROLE_USER', 'pauldipto2', 'imdpauld2@gmail.com');     
CREATE CACHED TABLE "PUBLIC"."USER_BILLING_ADDRESSES"(
    "USER_ID" BIGINT NOT NULL,
    "ADDRESS_ID" BIGINT NOT NULL
);      
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.USER_BILLING_ADDRESSES;   
INSERT INTO "PUBLIC"."USER_BILLING_ADDRESSES" VALUES
(2, 9),
(1, 16),
(53, 17);
CREATE CACHED TABLE "PUBLIC"."USER_DELIVERY_ADDRESSES"(
    "USER_ID" BIGINT NOT NULL,
    "ADDRESS_ID" BIGINT NOT NULL
);     
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.USER_DELIVERY_ADDRESSES;  
INSERT INTO "PUBLIC"."USER_DELIVERY_ADDRESSES" VALUES
(2, 10),
(1, 14),
(53, 18);              
CREATE CACHED TABLE "PUBLIC"."ADDRESS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 19) NOT NULL,
    "NUMBER" CHARACTER VARYING(255) NOT NULL,
    "POSTAL_CODE" CHARACTER VARYING(5) NOT NULL,
    "STREET" CHARACTER VARYING(255) NOT NULL,
    "IS_DEFAULT" BOOLEAN
);      
ALTER TABLE "PUBLIC"."ADDRESS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");       
-- 6 +/- SELECT COUNT(*) FROM PUBLIC.ADDRESS;  
INSERT INTO "PUBLIC"."ADDRESS" VALUES
(9, '9f', '96552', 'Pestalogistrasse', NULL),
(10, '9f', '96552', 'Pestalogistrasse', NULL),
(14, '9f', '96552', 'Pestalogistrasse', NULL),
(16, '9f', '96552', 'Pestalogistrasse', NULL),
(17, '9f', '96552', 'Pestalogistrasse', NULL),
(18, '9f', '96552', 'Pestalogistrasse', NULL); 
ALTER TABLE "PUBLIC"."CRATE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" CHECK("NO_OF_BOTTLES" >= 1) NOCHECK;       
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."UKR43AF9AP4EDM43MMTQ01ODDJ6" UNIQUE NULLS DISTINCT ("USERNAME");         
ALTER TABLE "PUBLIC"."ORDERS" ADD CONSTRAINT "PUBLIC"."FK32QL8UBNTJ5UH44PH9659TIIH" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;          
ALTER TABLE "PUBLIC"."ORDER_ITEM" ADD CONSTRAINT "PUBLIC"."FKT4DC2R9NBVBUJRLJV3E23IIBT" FOREIGN KEY("ORDER_ID") REFERENCES "PUBLIC"."ORDERS"("ID") NOCHECK;    
ALTER TABLE "PUBLIC"."USER_DELIVERY_ADDRESSES" ADD CONSTRAINT "PUBLIC"."FK5SSEDKQTPAFRWH0LSPE4VO6T9" FOREIGN KEY("ADDRESS_ID") REFERENCES "PUBLIC"."ADDRESS"("ID") NOCHECK;    
ALTER TABLE "PUBLIC"."CRATE" ADD CONSTRAINT "PUBLIC"."FKDE0QHHPMJHDHN3785T8PF4J92" FOREIGN KEY("BOTTLE_ID") REFERENCES "PUBLIC"."BOTTLE"("ID") NOCHECK;        
ALTER TABLE "PUBLIC"."BOTTLE" ADD CONSTRAINT "PUBLIC"."FKRV287UUMUI7PNL7QDWAFJJO92" FOREIGN KEY("ID") REFERENCES "PUBLIC"."BEVERAGE"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."USER_BILLING_ADDRESSES" ADD CONSTRAINT "PUBLIC"."FK226T76YD8381PMF3CWTR9RHET" FOREIGN KEY("ADDRESS_ID") REFERENCES "PUBLIC"."ADDRESS"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."CRATE" ADD CONSTRAINT "PUBLIC"."FK4LSU6158PQH1FKKAYM5KGWOF1" FOREIGN KEY("ID") REFERENCES "PUBLIC"."BEVERAGE"("ID") NOCHECK;             
ALTER TABLE "PUBLIC"."ORDER_ITEM" ADD CONSTRAINT "PUBLIC"."FK623K74OW8N0SQSU9G95PFSXOT" FOREIGN KEY("BEVERAGE_ID") REFERENCES "PUBLIC"."BEVERAGE"("ID") NOCHECK;               
ALTER TABLE "PUBLIC"."USER_DELIVERY_ADDRESSES" ADD CONSTRAINT "PUBLIC"."FKI1YHOTFIHH3858KNUN6481YDY" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;         
ALTER TABLE "PUBLIC"."USER_BILLING_ADDRESSES" ADD CONSTRAINT "PUBLIC"."FKA8PTDELU85UO4OXJ9AQD81S7F" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;          
