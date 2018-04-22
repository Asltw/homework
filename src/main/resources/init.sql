SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `time_series_data`
-- ----------------------------
DROP TABLE IF EXISTS `time_series_data`;
CREATE TABLE `time_series_data` (
  `item_id` char(36) NOT NULL,
  `trading_date` date NOT NULL,
  `stock_code` text,
  `item_value_one` double DEFAULT NULL,
  `item_value_two` double DEFAULT NULL,
  `item_value_three` double DEFAULT NULL,
  PRIMARY KEY (`item_id`,`trading_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;