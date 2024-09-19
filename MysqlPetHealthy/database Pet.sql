USE pethealthy;
drop database pethealthy;
create schema pethealthy;
CREATE TABLE `shopping_cart`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT UNSIGNED NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `quantity` BIGINT NOT NULL,
    `total` BIGINT NOT NULL
);
CREATE TABLE `post`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `comment`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` TEXT NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `product_id` BIGINT UNSIGNED NOT NULL,
    `post_id` BIGINT UNSIGNED NOT NULL,
    `parent_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `image`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `url` VARCHAR(255) NOT NULL,
    `pet_id` BIGINT UNSIGNED NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `product_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `Appointment`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `name_user` VARCHAR(255) NOT NULL,
    `number_phone` VARCHAR(255) NOT NULL,
    `name_pet` VARCHAR(255) NOT NULL,
    `type_id` BIGINT UNSIGNED NOT NULL,
    `start_date` DATETIME NOT NULL
);
ALTER TABLE
    `Appointment` ADD UNIQUE `appointment_type_id_unique`(`type_id`);
CREATE TABLE `type_product`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `product`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `EXP` DATETIME NOT NULL,
    `MFG` DATETIME NOT NULL,
    `price` BIGINT NOT NULL,
    `note` VARCHAR(255) NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `type_product_id` BIGINT UNSIGNED NOT NULL,
    `quantity` BIGINT NOT NULL
);
CREATE TABLE `pet`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `gender` ENUM('') NOT NULL,
    `type_id` BIGINT UNSIGNED NOT NULL,
    `weight` VARCHAR(255) NOT NULL,
    `age` VARCHAR(255) NOT NULL,
    `color` VARCHAR(255) NOT NULL,
    `size` BIGINT NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `type_pet`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL
);
CREATE TABLE `user`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NULL,
    `password` VARCHAR(255) NULL,
    `role_id` BIGINT UNSIGNED NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `birthday` DATE NOT NULL
);
ALTER TABLE
    `user` ADD UNIQUE `user_role_id_unique`(`role_id`);
CREATE TABLE `collection`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `post_id` BIGINT UNSIGNED NOT NULL,
    `product_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `medical_report`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `name_boss` VARCHAR(255) NOT NULL,
    `name_pet` VARCHAR(255) NOT NULL,
    `gender` ENUM('') NOT NULL,
    `create_date` DATETIME NOT NULL,
    `number_phone` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `symptom` VARCHAR(255) NOT NULL,
    `diagnosed` VARCHAR(255) NOT NULL,
    `pet_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `role`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `code` VARCHAR(255) NOT NULL
);
CREATE TABLE `evalute`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `value` BIGINT NOT NULL,
    `post_id` BIGINT UNSIGNED NOT NULL
);
ALTER TABLE
    `pet` ADD CONSTRAINT `pet_type_id_foreign` FOREIGN KEY(`type_id`) REFERENCES `type_pet`(`id`);
ALTER TABLE
    `post` ADD CONSTRAINT `post_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `image` ADD CONSTRAINT `image_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);
ALTER TABLE
    `Appointment` ADD CONSTRAINT `appointment_type_id_foreign` FOREIGN KEY(`type_id`) REFERENCES `type_pet`(`id`);
ALTER TABLE
    `product` ADD CONSTRAINT `product_id_foreign` FOREIGN KEY(`id`) REFERENCES `evalute`(`id`);
ALTER TABLE
    `medical_report` ADD CONSTRAINT `medical_report_pet_id_foreign` FOREIGN KEY(`pet_id`) REFERENCES `pet`(`id`);
ALTER TABLE
    `product` ADD CONSTRAINT `product_type_product_id_foreign` FOREIGN KEY(`type_product_id`) REFERENCES `type_product`(`id`);
ALTER TABLE
    `collection` ADD CONSTRAINT `collection_post_id_foreign` FOREIGN KEY(`post_id`) REFERENCES `post`(`id`);
ALTER TABLE
    `shopping_cart` ADD CONSTRAINT `shopping_cart_id_foreign` FOREIGN KEY(`id`) REFERENCES `product`(`id`);
ALTER TABLE
    `collection` ADD CONSTRAINT `collection_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);
ALTER TABLE
    `type_product` ADD CONSTRAINT `type_product_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `user` ADD CONSTRAINT `user_role_id_foreign` FOREIGN KEY(`role_id`) REFERENCES `role`(`id`);
ALTER TABLE
    `comment` ADD CONSTRAINT `comment_post_id_foreign` FOREIGN KEY(`post_id`) REFERENCES `post`(`id`);
ALTER TABLE
    `comment` ADD CONSTRAINT `comment_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `collection` ADD CONSTRAINT `collection_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `image` ADD CONSTRAINT `image_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `comment` ADD CONSTRAINT `comment_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);
ALTER TABLE
    `product` ADD CONSTRAINT `product_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `image` ADD CONSTRAINT `image_pet_id_foreign` FOREIGN KEY(`pet_id`) REFERENCES `pet`(`id`);
ALTER TABLE
    `pet` ADD CONSTRAINT `pet_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);