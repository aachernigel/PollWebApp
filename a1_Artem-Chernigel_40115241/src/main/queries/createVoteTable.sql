CREATE TABLE `polldb`.`vote` (
  `pollID` VARCHAR(10) NOT NULL,
  `pin#` VARCHAR(10) NOT NULL,
  `option` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pollID`, `pin#`));