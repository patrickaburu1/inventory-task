
SET foreign_key_checks = 0;

INSERT INTO role (role_id, role) VALUES (1, 'SELF_REGISTRED');
INSERT INTO role (role_id, role) VALUES (2, 'CREATED');


INSERT INTO user_type (id, name, description, created_on) VALUES (1, 'PORTAL USER', 'users portal', '2020-05-28 10:35:01');
INSERT INTO user_type (id, name, description, created_on) VALUES (2, 'SYSTEM ADMIN', 'Admins', '2020-07-12 13:19:12');


INSERT INTO user (id, firstname, lastname, email, password, active, phone, locked, user_type, fire_base_token, last_login, ip, created_on, app_version) VALUES (94, 'patrick', 'kaburu', 'patrickaburu@gmail.com', '$2a$10$0tyQ70/d52C34/hRjgLfAujoCwf2WAsUsWMKePClENIxwB/rkURbm', 1, '+254710446176', 0, 1, 'eHMr3pc8RZy4Rdlm3Pf7Ew:APA91bHgCv1aMnr8gnT4jZuMadb7o9g8glwOYHTpDbzGx2xVd0DMcZen3MKOXeLN-e8ogR-HIeSqceNDf7mZy4jpLsYLVff5u1e0_IVjVXFg-yPqmGKd0EyxLE_tRCTq1Dq7pCPsIl0I', '2023-09-29 15:17:34', '0:0:0:0:0:0:0:1', '2020-06-18 10:04:47', '1.2.3');

INSERT INTO app_settings (id, name, code, value, flag, description, created_on, updated_on, updated_by) VALUES (1, 'APP_VERSION', 'APP_VERSION', '1.2.3', '1', 'Mobile app verion', '2020-06-12 12:27:55', '2020-09-09 14:10:03', 109);
INSERT INTO app_settings (id, name, code, value, flag, description, created_on, updated_on, updated_by) VALUES (2, 'Maximum no. of shop on trial', 'MAX_NO_OF_SHOPS_ON_TRIAL', '1', '1', 'Maximum no. of shops user can have on trial', '2020-06-15 12:27:55', '2020-06-15 12:27:55', null);
INSERT INTO app_settings (id, name, code, value, flag, description, created_on, updated_on, updated_by) VALUES (3, 'Marketing app versions', 'APP_VERSION_MARKETING', '1.0.1,1.0.2', '1', 'Maraketing App version', '2020-07-26 11:15:17', '2020-08-20 14:15:38', 109);
INSERT INTO app_settings (id, name, code, value, flag, description, created_on, updated_on, updated_by) VALUES (4, 'Manimum marketing earning rate', 'MARKETING_MIN_EARNING_RATE', '30', '1', 'Minimum marketing earning rate in %', '2020-06-15 12:27:55', '2020-10-23 15:26:00', 109);
INSERT INTO app_settings (id, name, code, value, flag, description, created_on, updated_on, updated_by) VALUES (5, 'Maximum marketing earning rate', 'MARKETING_MAX_EARNING_RATE', '40', '1', 'Maximum marketing earning rate in %', '2020-06-15 12:27:55', '2020-09-05 21:00:28', 109);


INSERT INTO shops (id, created_by, flag, location, name, phone, plan_id, latitude, longitude, created_on, updated_on, payment_status, trial, category_id, marketer_id, referral_code, one_off_earning, payment_due_on) VALUES (72, 94, '1', 'Kairo', 'Kairo WagaKindu', '+254710446176', 1, 0, 0, '2021-01-26 14:36:49', '2021-01-26 14:36:49', 'PAID', 1, 9, null, null, 1, '2021-01-26');


INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (1, 'SELL', 1, '2020-05-31 23:28:53', 'SELL', null, 1);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (2, 'PRODUCTS', 1, '2020-05-31 23:28:53', 'PRODUCTS', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (3, 'STOCK', 1, '2020-05-31 23:28:53', 'STOCK', '2019-08-04 23:28:53', 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (4, 'SUPPLIER', 1, '2020-05-31 23:28:53', 'SUPPLIER', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (5, 'CUSTOMER', 1, '2020-05-31 23:28:53', 'CUSTOMER', null, 1);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (6, 'REPORT', 1, '2020-05-31 23:28:53', 'REPORT', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (7, 'USERS', 1, '2020-05-31 23:28:53', 'USERS', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (8, 'STOCKOUT', 1, '2020-05-31 23:28:53', 'STOCKOUT', null, 1);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (9, 'CASH_BOX', 1, '2020-05-31 23:28:53', 'CASH BOX', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (10, 'MY_EXPENSES', 1, '2020-05-31 23:28:53', 'MY EXPENSE', null, 1);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (11, 'SETTINGS', 1, '2020-05-31 23:28:53', 'SETTINGS', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (12, 'DEBTS', 1, '2020-05-31 23:28:53', 'DEBTS', null, 0);
INSERT INTO shop_rights (id, code, created_by, created_on, name, updated_on, default_right) VALUES (21, 'INVOICES', 1, '2020-08-03 23:28:53', 'INVOICES', null, 0);

INSERT INTO shop_payment_methods (id, payment_method_id, shop_id, active, flag, created_by, created_on) VALUES (109, 1, 72, 1, '1', 94, '2023-05-01 19:37:02');
INSERT INTO shop_payment_methods (id, payment_method_id, shop_id, active, flag, created_by, created_on) VALUES (110, 2, 72, 1, '1', 94, '2021-01-26 14:36:49');
INSERT INTO shop_payment_methods (id, payment_method_id, shop_id, active, flag, created_by, created_on) VALUES (139, 3, 72, 1, '1', 94, '2023-05-01 19:36:48');

INSERT INTO shop_payment_packages (id, created_on, description, flag, is_free, name, updated_on, max_no_of_employees, color_code, trial_days, display_order) VALUES (1, null, 'Trial', '1', 1, 'Trial', null, 2, null, '14', 1);
INSERT INTO shop_payment_packages (id, created_on, description, flag, is_free, name, updated_on, max_no_of_employees, color_code, trial_days, display_order) VALUES (2, null, 'Only one (1) user. Get discounted prices for quartely and yearly plans.', '1', 0, 'Bronze', null, 1, null, '0', 2);
INSERT INTO shop_payment_packages (id, created_on, description, flag, is_free, name, updated_on, max_no_of_employees, color_code, trial_days, display_order) VALUES (3, null, 'Upto 3 employees. Get discounted prices for quartely and yearly plans.', '1', 0, 'Silver', null, 3, null, '0', 3);
INSERT INTO shop_payment_packages (id, created_on, description, flag, is_free, name, updated_on, max_no_of_employees, color_code, trial_days, display_order) VALUES (4, null, 'Upto 5 employees. Get discounted prices for quartely and yearly plans.', '1', 0, 'Diamond', null, 5, null, '0', 4);


INSERT INTO payment_methods (id, name, code, description, is_active, created_on) VALUES (1, 'CASH', 'CASH', 'CASH', 1, '2020-05-03 11:38:35');
INSERT INTO payment_methods (id, name, code, description, is_active, created_on) VALUES (2, 'MPESA', 'MPESA', 'MPESA', 1, '2020-05-02 11:00:38');
INSERT INTO payment_methods (id, name, code, description, is_active, created_on) VALUES (3, 'CREDIT', 'CREDIT', 'CREDIT', 1, '2020-05-02 11:00:38');

INSERT INTO shop_employees (id, active, role_id, shop_id, user_id, default_shop, created_by, created_on, updated_on, updated_by) VALUES (90, '1', 87, 72, 94, 1, 94, '2021-01-26 14:36:49', null, null);

INSERT INTO shop_employee_groups (id, created_by, flag, role, shop_id, created_on) VALUES (87, 94, '1', 'SUPER_ADMIN', 72, '2021-01-26 14:36:49');
INSERT INTO shop_employee_groups (id, created_by, flag, role, shop_id, created_on) VALUES (88, 94, '1', 'CASHIER', 72, '2021-01-26 14:36:49');
INSERT INTO shop_employee_groups (id, created_by, flag, role, shop_id, created_on) VALUES (168, 94, '1', 'SALES LADY', 72, '2022-01-29 17:12:53');

INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1035, 94, '2021-01-26 17:36:49', 1, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1036, 94, '2021-01-26 17:36:49', 2, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1037, 94, '2021-01-26 17:36:49', 3, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1038, 94, '2021-01-26 17:36:49', 4, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1039, 94, '2021-01-26 17:36:49', 5, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1040, 94, '2021-01-26 17:36:49', 6, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1041, 94, '2021-01-26 17:36:49', 7, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1042, 94, '2021-01-26 17:36:49', 8, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1043, 94, '2021-01-26 17:36:49', 9, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1044, 94, '2021-01-26 17:36:49', 10, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1045, 94, '2021-01-26 17:36:49', 11, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1046, 94, '2021-01-26 17:36:49', 12, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1047, 94, '2021-01-26 17:36:49', 21, 87, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1048, 94, '2021-01-26 17:36:49', 1, 88, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1049, 94, '2021-01-26 17:36:49', 5, 88, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1050, 94, '2021-01-26 17:36:49', 8, 88, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1051, 94, '2021-01-26 17:36:49', 10, 88, 72, '2021-01-26 17:36:49');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1769, 94, '2022-01-29 20:12:53', 1, 168, 72, '2022-01-29 20:12:53');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1770, 94, '2022-01-29 20:12:53', 4, 168, 72, '2022-01-29 20:12:53');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1771, 94, '2022-01-29 20:12:53', 5, 168, 72, '2022-01-29 20:12:53');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1772, 94, '2022-01-29 20:12:53', 9, 168, 72, '2022-01-29 20:12:53');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1773, 94, '2022-01-29 20:12:53', 10, 168, 72, '2022-01-29 20:12:53');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (1774, 94, '2022-01-29 20:13:34', 12, 168, 72, '2022-01-29 20:13:34');
INSERT INTO shop_employees_group_rights (id, created_by, created_on, privilege_id, role_id, shop_id, updated_on) VALUES (2144, 94, '2022-11-20 12:41:15', 3, 88, 72, '2022-11-20 12:41:15');

INSERT INTO suppliers (id, name, phone, email, address, description, created_on, updated_on, bank, bank_account, mpesa_phone, shop_id, created_by) VALUES (126, 'Un-known', 'Un-known', 'Un-known', null, 'Un-known', '2021-01-26 14:36:49', '2021-01-26 14:36:49', null, null, null, 72, 94);

INSERT INTO product_category (id, name, description, shop_id) VALUES (871, 'Un-Categorized', 'Un-Categorized', 72);
INSERT INTO product_category (id, name, description, shop_id) VALUES (872, 'Spirits', 'Spirits', 72);
INSERT INTO product_category (id, name, description, shop_id) VALUES (874, 'spirit', 'spirit', 72);
INSERT INTO product_category (id, name, description, shop_id) VALUES (875, 'beer', 'beer', 72);

INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (9445, 'KARIBIA', 'P9445', 'Karibia (KWAL)', 150.00, 90.00, 0, 871, '2021-01-26 14:58:32', '2021-01-26 14:58:32', 150.00, 2, '2021-01-26 17:58:32', '1', 72);
INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (9448, 'GIN 150ML', 'P9448', 'gin', 400.00, 350.00, 119, 874, '2021-03-28 06:35:15', '2021-03-28 06:35:15', 400.00, 5, '2021-03-28 09:35:15', '1', 72);
INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (9449, 'CHROME', 'P9449', '250 ml', 400.00, 300.00, 498, 874, '2021-03-28 06:35:15', '2021-03-28 06:35:15', 400.00, 1, '2021-03-28 09:35:15', '1', 72);
INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (9450, 'HEINKEN', 'P9450', 'beer import', 300.00, 200.00, 196, 875, '2021-03-28 06:35:15', '2021-03-28 06:35:15', 300.00, 5, '2021-03-28 09:35:15', '1', 72);
INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (12158, 'TUSKER-350ML', 'P12158', '350 ml', 350.00, 270.00, 9, 872, '2023-04-25 20:28:59', '2023-04-25 20:28:59', 300.00, 15, '2023-04-25 20:28:59', '1', 72);
INSERT INTO products (id, name, code, description, selling_price, buying_price, stock, category, created_on, updated_on, min_selling_price, re_order_level, expiry_date, flag, shop_id) VALUES (12201, 'TEST EQUITY', '9201', 'test equity', 20.00, 10.00, 12, 874, '2023-09-29 15:30:32', '2023-09-29 15:30:32', 15.00, 10, '2023-09-29 15:30:32', '1', 72);

INSERT INTO business_sell_configs (id, config_name, config_value, created_by, created_on, shop_id, updated_by, updated_on) VALUES (5, 'SELL BELOW SELLING PRICES', 0, 94, '2022-03-06 10:39:31', 72, 94, '2022-03-06 10:40:04');

SET foreign_key_checks = 1;
