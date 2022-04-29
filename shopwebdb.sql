-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th4 29, 2022 lúc 05:04 PM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.1.2

SET
SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET
time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shopwebdb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin`
(
    `id`         bigint(20) NOT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name`  varchar(255) DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    `username`   varchar(255) DEFAULT NULL,
    `role_id`    bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`id`, `first_name`, `last_name`, `password`, `username`, `role_id`)
VALUES (1, 'Nguyen', 'Truong', '$2a$10$L7olvyA6sVgrkmPslh0W0eW/BRFHEA9BEzbuDxPPcfVMnvozmG.Pm', 'nickadmin', 1),
       (2, 'Nguyen', 'Van B', '$2a$10$jJHKarVrYMlwTjjK.S21mOYWlANoItPXOx7zBLlPPSMi9mTG7jeiO', 'nickstaff', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category`
(
    `id`   int(11) NOT NULL,
    `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `name`)
VALUES (1, 'Men Clothes'),
       (2, 'Mobile & Gadgets'),
       (3, 'Health'),
       (4, 'Women Clothes'),
       (5, 'Computer & Accessories'),
       (6, 'Home Appliances');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discount`
--

CREATE TABLE `discount`
(
    `discount_id`    bigint(20) NOT NULL,
    `discount_name`  varchar(255) DEFAULT NULL,
    `discount_price` int(11) DEFAULT NULL,
    `created_date`   date         DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `discount`
--

INSERT INTO `discount` (`discount_id`, `discount_name`, `discount_price`, `created_date`)
VALUES (1, 'Discount 13%', 13, '2022-04-29'),
       (3, 'Disount 5%', 5, '2022-04-29'),
       (4, 'Discount 50%', 50, '2022-04-15');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `item_cart_detail`
--

CREATE TABLE `item_cart_detail`
(
    `id`        bigint(20) NOT NULL,
    `quantity`  int(11) NOT NULL,
    `productid` bigint(20) DEFAULT NULL,
    `userid`    bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order`
--

CREATE TABLE `order`
(
    `id`             bigint(20) NOT NULL,
    `approve_time`   datetime     DEFAULT NULL,
    `check_out_time` datetime     DEFAULT NULL,
    `confirm_time`   datetime     DEFAULT NULL,
    `reject_time`    datetime     DEFAULT NULL,
    `status`         varchar(255) DEFAULT NULL,
    `user_id`        bigint(20) DEFAULT NULL,
    `total_price`    double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail`
(
    `id`         bigint(20) NOT NULL,
    `quantity`   int(11) NOT NULL,
    `order_id`   bigint(20) DEFAULT NULL,
    `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products`
(
    `product_id`     bigint(20) NOT NULL,
    `description`    varchar(255) NOT NULL,
    `image_name`     varchar(255) DEFAULT NULL,
    `name`           varchar(255) NOT NULL,
    `price`          int(11) NOT NULL,
    `quantity`       int(11) NOT NULL,
    `category_id`    int(11) DEFAULT NULL,
    `discount_price` double       DEFAULT NULL,
    `discount_id`    bigint(20) DEFAULT NULL,
    `created_date`   datetime     DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`product_id`, `description`, `image_name`, `name`, `price`, `quantity`, `category_id`,
                        `discount_price`, `discount_id`, `created_date`)
VALUES (1, 'Vải cá sấu cotton cao cấp ngắn tay cực sang trọng',
        'http://localhost:8080/productImages/172e8f7015334694d2cea96134cace83.jpeg', 'Áo thun nam POLO', 10000, 99, 1,
        5000, 4, '2022-04-29 00:00:00'),
       (2, 'Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO',
        'http://localhost:8080/productImages/1a32d71426b5299936d59909870e92b6.jpeg', 'Quần Áo Thể Thao', 180, 50, 1,
        NULL, NULL, NULL),
       (3, 'Áo sơ mi nam Vettino dài tay cổ bẻ dáng ôm Hàn Quốc vải lụa thái cao cấp chống nhăn',
        'http://localhost:8080/productImages/f099429b60c56d4bc981de2c82c3ec76.jpeg', 'Áo sơ mi nam Vettino', 90, 67, 1,
        81, NULL, NULL),
       (4, 'Cho Cả Nam và Nữ QT22(40-86kg) Form Chuẩn, Chất Dày Đẹp',
        'http://localhost:8080/productImages/3ba824ff58a473fdf10abff053eeaa7d.jpeg', 'Quần Thể Thao Nỉ 3 Sọc', 79, 68,
        1, NULL, NULL, NULL),
       (5, 'Ống rộng vải Hàn cao cấp, quần tây âu co giãn tốt Premium thời trang JBAGY - JBA01',
        'http://localhost:8080/productImages/b27919ab88fc8553d4b74b6d59f2e8fb.jpeg', 'Quần baggy nam ', 143, 89, 1,
        NULL, NULL, NULL),
       (6, 'Co giãn có túi khóa kéo thương hiệu Coolmate',
        'http://localhost:8080/productImages/9c2659c1e1b36e76a285c866f47fdb0f.jpeg', 'Quần thể thao nam Ultra Short',
        78, 24, 1, NULL, NULL, NULL),
       (7, 'Mang lại cho quý khách những sản phẩm tốt nhất, đẹp nhất.',
        'http://localhost:8080/productImages/53c651e0d36be783367e3c7e70b6af6f.jpeg', 'Kính cường lực iphone 21D', 8, 50,
        2, NULL, NULL, NULL),
       (8,
        'Quay video 4K, chụp ảnh chân dung tuyệt đẹp và chụp ảnh phong cảnh rộng với hệ thống camera kép hoàn toàn mới. ',
        'http://localhost:8080/productImages/b107f2e6167adb250100ec4c8d028a57.jpeg', 'Apple iPhone 11 64GB', 1159, 100,
        2, NULL, NULL, NULL),
       (9,
        'Màn Hình Rộng, Trải Nghiệm Nhiều Hơn\r\n- Giải trí bất tận mỗi ngày, thoải mái thưởng thức nhiều nội dung hơn với màn hình tràn viền vô cực Infinity-V 6,5 inch. Tận hưởng nội dung hiển thị rõ ràng và sắc nét đến không ngờ nhờ công nghệ HD+ tiên tiến.',
        'http://localhost:8080/productImages/7b0b89fa04493bc631e94751b683ee6b.jpeg', 'Điện Thoại Samsung A12 4GB/128GB',
        359, 78, 2, NULL, NULL, NULL),
       (10, 'Điện thoại 1280 đơn giản, nhỏ gọn, cực bền.',
        'http://localhost:8080/productImages/331c288812971ce367ad0bc93cdf0f44.jpeg', 'Điện Thoại Nokia 1280', 165, 45,
        2, NULL, NULL, NULL),
       (11, 'Lightning Cáp Tai nghe ốp - Awifi Case H4-7',
        'http://localhost:8080/productImages/09d53907f3fdb3b27a0d67b39fc36982.jpeg', 'Dây cáp sạc Hoco X14 ', 27, 100,
        2, NULL, NULL, NULL),
       (12,
        '- Mực in chất lượng cao,sắc nét, không phai màu, không gây hại cho da.\r\n- Hình ảnh thiết kế đẹp, phong cách, trẻ trung.',
        'http://localhost:8080/productImages/a55465496797b8b824e9b640733a06c2.jpeg', 'Ốp lưng iphone', 9, 1009, 2, NULL,
        NULL, NULL),
       (13, 'CHUẨN NỘI ĐỊA MỸ, HÀNG CAM KẾT CHUẨN CHÍNH HÃNG FREESHIP',
        'http://localhost:8080/productImages/b4b59f85e95177a48d39acc01b0cf697.jpeg', 'MUỐI RỬA MŨI NEILMED', 2, 134, 3,
        NULL, NULL, NULL),
       (14, 'Chống Cận Thị 5ml', 'http://localhost:8080/productImages/38697d61ec208ce41caf6687cada697f.jpeg',
        'Nhỏ Mắt Sancoba Nhật Bản', 27, 345, 3, NULL, NULL, NULL),
       (15, 'Có Vòi Xịt Dùng Để Sát Khuẩn Tiệt Trùng Trong Y Tế, Spa',
        'http://localhost:8080/productImages/fbf2b5a2b8ebb2e405ac4e72aeab2428.jpeg', 'Cồn 70, 90 Độ', 30, 324, 3, NULL,
        NULL, NULL),
       (16, 'Đẹp da, đào thải độc tố gan, cân bằng nội tiết tố',
        'http://localhost:8080/productImages/32d773ec714367a1df2f5df47f0f2d75.jpeg',
        'Mầm đậu nành Healthy Care Super Lecithin', 238, 589, 3, NULL, NULL, NULL),
       (17, 'Colagen shisedo Hỗ Trợ Làm Đẹp Da 10 lọ x 50ml',
        'http://localhost:8080/productImages/0d6db4e56a51ff1753a4f4a09ee3495f.jpeg',
        'Nước Uống The collagen shiseido Nhật Bản', 339, 56, 3, NULL, NULL, NULL),
       (18, 'Quần đùi đũi dáng váy siêu hot',
        'http://localhost:8080/productImages/801dcb528a21c09e89a503cc2ae725e3.jpeg', 'Quần short đũi', 45, 67, 4, NULL,
        NULL, NULL),
       (19, 'Lưng cao khóa trước 1 khuy vải tuyết',
        'http://localhost:8080/productImages/f692d40eab50ad6f827ca745afdeb537.jpeg', 'Quần ống rộng suông nữ', 71, 98,
        4, NULL, NULL, NULL),
       (20, 'Thu Đông chất đũi lụa cao cấp mặc nhà và cho con bú cực kỳ xinh',
        'http://localhost:8080/productImages/eececec1d925abba28f245beb6662069.jpeg',
        'lưng cao khóa trước 1 khuy vải tuyết', 105, 99, 4, NULL, NULL, NULL),
       (21, 'Dạo phố hay dự tiệc cưới cực dễ thương',
        'http://localhost:8080/productImages/bac3a75d9188af4cbe791a419e8a99f3.jpeg', 'Đầm nữ trắng Voan xòe', 165, 99,
        4, NULL, NULL, NULL),
       (22, 'Áo polo dài tay dáng rộng unisex dưới 75kg TABISTORE',
        'http://localhost:8080/productImages/4cbb1d305c25fa60e4382f5aa22cce59.jpeg', 'Áo sweater polo unisex', 110, 69,
        4, NULL, NULL, NULL),
       (23, 'In đơn hàng, phiếu gửi, minicode, logo tự dán, bảo hành 12 tháng',
        'http://localhost:8080/productImages/c18feb1479a1d4f3a0903f92e143842c.jpeg', 'Máy in nhiệt Shoptida SP46', 1499,
        99, 5, NULL, NULL, NULL),
       (24, 'USB, nhỏ gọn, thuận cả 2 tay, phù hợp PC/Laptop',
        'http://localhost:8080/productImages/142753bff0653a5c452cb0e0b9d68731.jpeg', 'Chuột không dây Logitech B170',
        175, 45, 5, NULL, NULL, NULL),
       (25, 'Thiết Kế Sang Trọng Cực Kì Bắt Mắt',
        'http://localhost:8080/productImages/9d6ffb68042d2b3903b6d93af13fd7a0.jpeg', 'Bàn Phím Inphic V520', 215, 77, 5,
        NULL, NULL, NULL),
       (26, '13.3-inch, 8GB, 256GB SSD', 'http://localhost:8080/productImages/575e04c0e1d08b5f1b9f624a8d6b1419.png',
        'Apple MacBook Air (2020) M1', 23800, 77, 5, NULL, NULL, NULL),
       (27, '(8GB/256GB) | Màn Hình Fullview',
        'http://localhost:8080/productImages/6f942dc3bdd3b9e1820bc61db6965d06.jpeg',
        'Máy Tính Xách Tay Huawei Matebook D15', 13900, 56, 5, NULL, NULL, NULL),
       (28,
        'Lò nướng đa năng Comet CM6510 có thể thực hiện các món ăn qua quay, nướng, hâm, rã đông… đem lại hiệu quả phục vụ tối đa, cho bạn và người thân có thể tận hưởng những món ăn thơm ngon, bổ dưỡng.',
        'http://localhost:8080/productImages/02e20047bf7ddcf5327975f90b001929.jpeg', 'Lò nướng điện Comet CM6510 10L',
        429, 99, 6, NULL, NULL, NULL),
       (29,
        'Máy ép trái cây Comet CM9838 với đường kính lớn (65 mm) có thể ép được cả trái táo hoặc lê một cách nhanh chóng mà không cần phải gọt vỏ. ',
        'http://localhost:8080/productImages/b440b06384f1ab0ba8827a666a28c93b.jpeg', 'Máy ép trái cây 400W COMET', 859,
        46, 6, NULL, NULL, NULL),
       (30,
        'Máy xay sinh tố Comet CM9954 được thiết kế đẹp mắt với kiểu dáng hiện đại, máy chạy êm nhờ chân đế chống trượt.',
        'http://localhost:8080/productImages/6f2545047d07ae7e2e9efcdb756869e7.jpeg', 'Máy Xay Sinh Tố COMET', 355, 99,
        6, NULL, NULL, NULL),
       (31, 'Phím cảm ứng điện tử COMET 4L - CM6838',
        'http://localhost:8080/productImages/e6613f8e1846bf93c508412a127af356.jpeg', 'Nồi chiên không dầu', 1700, 99, 6,
        NULL, NULL, NULL),
       (48, 'asfgafg', 'http://localhost:8080/productImages/6f2545047d07ae7e2e9efcdb756869e7.jpeg', 'asdg', 3, 4, 1,
        2.61, 1, '2022-04-29 00:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role`
(
    `id`   bigint(20) NOT NULL,
    `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_STAFF');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `token`
--

CREATE TABLE `token`
(
    `id`          bigint(20) NOT NULL,
    `expiry_date` datetime     DEFAULT NULL,
    `token`       varchar(255) DEFAULT NULL,
    `user_id`     bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user`
(
    `id`                    bigint(20) NOT NULL,
    `address`               varchar(255) DEFAULT NULL,
    `created`               datetime     DEFAULT NULL,
    `email`                 varchar(255) DEFAULT NULL,
    `enabled`               bit(1) NOT NULL,
    `first_name`            varchar(255) DEFAULT NULL,
    `last_name`             varchar(255) DEFAULT NULL,
    `password`              varchar(255) DEFAULT NULL,
    `phone_no`              varchar(255) DEFAULT NULL,
    `username`              varchar(255) DEFAULT NULL,
    `dob`                   datetime     DEFAULT NULL,
    `forgot_password_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `address`, `created`, `email`, `enabled`, `first_name`, `last_name`, `password`, `phone_no`,
                    `username`, `dob`, `forgot_password_token`)
VALUES (1, 'Son Tay Ha Noi', '2022-04-22 12:26:53', 'client@gmail.com', b'1', 'Nguyen', 'Van B',
        '$2a$10$.wpWS.IoSRtC06z8sgr9ju37L0SEOPlx92Fsfg7qmwjyPqpiQnAaW', '0976223223', 'client', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `voucher`
--

CREATE TABLE `voucher`
(
    `voucher_id`   bigint(20) NOT NULL,
    `code`         varchar(255) NOT NULL,
    `name`         varchar(255) NOT NULL,
    `price`        int(11) NOT NULL,
    `created_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `voucher`
--

INSERT INTO `voucher` (`voucher_id`, `code`, `name`, `price`, `created_date`)
VALUES (1, 'FS15', 'Free Ship 15K', 15000, '2022-04-29'),
       (2, 'SOFF50', 'Sale off 50K', 50000, '2022-04-29'),
       (3, 'DIS20K', ' Maximum discount of 20K, orders from 100K', 20000, '2022-04-29');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK5i301uf8wo1mfse12fi8y9jn3` (`username`),
  ADD KEY `FKmmec8jdufi0j1lrhu3hpn4ugj` (`role_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
    ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `discount`
--
ALTER TABLE `discount`
    ADD PRIMARY KEY (`discount_id`);

--
-- Chỉ mục cho bảng `item_cart_detail`
--
ALTER TABLE `item_cart_detail`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK25ne8ty609xx2xla6ruijl56g` (`productid`),
  ADD KEY `FKtia866voqrrwo0vlsstum159y` (`userid`);

--
-- Chỉ mục cho bảng `order`
--
ALTER TABLE `order`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK5ds52cnxjw9c99ovccne0axk0` (`user_id`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKkkl8lqaxofq5cr97y5q9n6b0i` (`order_id`),
  ADD KEY `FK388dofyjqbdm1utcwpbrerccb` (`product_id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
    ADD PRIMARY KEY (`product_id`),
  ADD KEY `FK5drd4x1gskipdc846gum9yc5j` (`category_id`),
  ADD KEY `FKjaspawqxr48ggf01ack5ibetl` (`discount_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
    ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `token`
--
ALTER TABLE `token`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKn1okjowqyw7r2nl4hci3kahm6` (`user_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK8u3xwjr9513mbwc9mqtx80ef8` (`username`,`email`,`phone_no`);

--
-- Chỉ mục cho bảng `voucher`
--
ALTER TABLE `voucher`
    ADD PRIMARY KEY (`voucher_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admin`
--
ALTER TABLE `admin`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
    MODIFY `id` int (11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `discount`
--
ALTER TABLE `discount`
    MODIFY `discount_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `item_cart_detail`
--
ALTER TABLE `item_cart_detail`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order`
--
ALTER TABLE `order`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
    MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `token`
--
ALTER TABLE `token`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `voucher`
--
ALTER TABLE `voucher`
    MODIFY `voucher_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `admin`
--
ALTER TABLE `admin`
    ADD CONSTRAINT `FKmmec8jdufi0j1lrhu3hpn4ugj` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Các ràng buộc cho bảng `item_cart_detail`
--
ALTER TABLE `item_cart_detail`
    ADD CONSTRAINT `FK25ne8ty609xx2xla6ruijl56g` FOREIGN KEY (`productid`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKtia866voqrrwo0vlsstum159y` FOREIGN KEY (`userid`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `order`
--
ALTER TABLE `order`
    ADD CONSTRAINT `FK5ds52cnxjw9c99ovccne0axk0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
    ADD CONSTRAINT `FK388dofyjqbdm1utcwpbrerccb` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKkkl8lqaxofq5cr97y5q9n6b0i` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
    ADD CONSTRAINT `FK5drd4x1gskipdc846gum9yc5j` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FKjaspawqxr48ggf01ack5ibetl` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`);

--
-- Các ràng buộc cho bảng `token`
--
ALTER TABLE `token`
    ADD CONSTRAINT `FKn1okjowqyw7r2nl4hci3kahm6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
