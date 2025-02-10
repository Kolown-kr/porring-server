DROP DATABASE IF EXISTS porring;

CREATE DATABASE porring;

USE porring;

CREATE TABLE `oauth_type`
(
    `oauth_type_code` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`oauth_type_code`)
);

CREATE TABLE `reaction_type`
(
    `react_code` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`react_code`)
);

CREATE TABLE `accounts`
(
    `account_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `sub_type`   VARCHAR(20) NOT NULL,
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME    NOT NULL,
    PRIMARY KEY (`account_id`)
);

CREATE TABLE `oauth_accounts`
(
    `account_id`      BIGINT      NOT NULL,
    `oauth_type_code` VARCHAR(10) NOT NULL,
    `oauth_number`    VARCHAR(50) NOT NULL,
    PRIMARY KEY (`account_id`),
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
    FOREIGN KEY (`oauth_type_code`) REFERENCES `oauth_type` (`oauth_type_code`)
);

CREATE TABLE `email_accounts`
(
    `account_id` BIGINT       NOT NULL,
    `email`      VARCHAR(320) NOT NULL,
    `password`   CHAR(60)     NOT NULL,
    PRIMARY KEY (`account_id`),
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
);

CREATE TABLE `accounts_follow`
(
    `account_follow_id` BIGINT       NOT NULL,
    `follower_id`       BIGINT       NOT NULL,
    `followee_id`       BIGINT       NOT NULL,
    `nickname`          VARCHAR(255) NOT NULL,
    PRIMARY KEY (`account_follow_id`),
    INDEX `idx_follower` (`follower_id`),
    INDEX `idx_followee` (`followee_id`),
    FOREIGN KEY (`follower_id`) REFERENCES `accounts` (`account_id`),
    FOREIGN KEY (`followee_id`) REFERENCES `accounts` (`account_id`)
);

CREATE TABLE `boards`
(
    `board_id`    BIGINT   NOT NULL,
    `account_id`  BIGINT   NOT NULL,
    `img_urls`    TEXT     NULL,
    `description` TEXT     NULL,
    `created_at`  DATETIME NOT NULL,
    `updated_at`  DATETIME NOT NULL,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
);

CREATE TABLE `reactions`
(
    `board_id`   BIGINT      NOT NULL,
    `account_id` BIGINT      NOT NULL,
    `react_code` VARCHAR(10) NOT NULL,
    `deleted`    BIT         NOT NULL DEFAULT 0,
    PRIMARY KEY (`board_id`, `account_id`),
    INDEX `idx_account` (`account_id`),
    FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`),
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
    FOREIGN KEY (`react_code`) REFERENCES `reaction_type` (`react_code`)
);


