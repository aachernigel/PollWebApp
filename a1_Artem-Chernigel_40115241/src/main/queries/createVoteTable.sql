CREATE TABLE `polldb`.`vote` (
  `pollID` VARCHAR(10) NOT NULL,
  `sessionID` VARCHAR(100) NOT NULL,
  `pin` VARCHAR(6) NOT NULL,
  `option` VARCHAR(100),
  `dateTime` DATETIME NULL,
  PRIMARY KEY (`pollID`, `sessionID`));