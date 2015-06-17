--users
INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$1DrGwbID6aOsi6E.h8Y3fOVH9Lxar7z8ixLvYVfHwOnkxq.sILJsG', 1);
INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$10$1DrGwbID6aOsi6E.h8Y3fOVH9Lxar7z8ixLvYVfHwOnkxq.sILJsG', 1);

--customers
INSERT INTO customers (username) VALUES ('admin');
INSERT INTO customers (username) VALUES ('user');

--authorities
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');

--groups
INSERT INTO groups (group_name) VALUES ('administrators');
INSERT INTO groups (group_name) VALUES ('users');

--group_members
INSERT INTO group_members (username, group_id) VALUES ('admin', 1);
INSERT INTO group_members (username, group_id) VALUES ('admin', 2);
INSERT INTO group_members (username, group_id) VALUES ('user', 2);

--groups_authorities
INSERT INTO group_authorities (group_id, authority) VALUES ('1', 'ROLE_ADMINISTRATORS');
INSERT INTO group_authorities (group_id, authority) VALUES ('2', 'ROLE_USERS');


--roles_menus
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (1, '系统管理', NULL, NULL, 'administrators', 1, 'ROLE_ADMINISTRATORS');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (2, '用户管理', '/manageuser', 1, 'administrators', 1, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (3, '权限管理', '/managerole', 1, 'administrators', 2, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (4, '商品管理', NULL, NULL, 'administrators', 2, 'ROLE_ADMINISTRATORS');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (5, '商品信息', '/goods', 4, 'administrators', 1, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (6, '商品维护', '/repairgoods', 4, 'administrators', 2, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (7, '订单管理', NULL, NULL, 'administrators', 3, 'ROLE_ADMINISTRATORS');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (8, '管理订单', '/order', 7, 'administrators', 1, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (9, '我的订单', NULL, NULL, 'users', 4, 'ROLE_USERS');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (10, '管理订单', '/myorder', 9, 'users', 1, 'ROLE_USER');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (11, '增加订单', '/addorder', 9, 'users', 2, 'ROLE_USER');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (12, '布告管理', '/managebult', 1, 'administrators', 3, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (13, '订单报表', '/orderreports', 7, 'administrators', 2, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (14, '维护日志', '/repgoodslog', 4, 'administrators', 3, 'ROLE_ADMIN');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (15, '订单报表', '/orderreports', 9, 'users', 3, 'ROLE_USER');
INSERT INTO roles_menus (id, menu_name, url, parent_id, group_name, order_id, authority)
VALUES (16, '商品统计', '/goodsreports', 7, 'administrators', 2, 'ROLE_ADMIN');

--单据状态的流转表
--0=未提交
--1=客户已经提交，等待绿审核
--2=绿已审核，等待发货》打印拣货单
--3=绿已出库，等待收货》打印配送单，修改实际发货数量
--100=订单完成》修改实际收货数量
DELETE FROM Worklist;
INSERT INTO Worklist (CurrentFlag, NextFlag, Status, SNote)
VALUES (0, 1, 1, '');

INSERT INTO Worklist (CurrentFlag, NextFlag, Status, SNote)
VALUES (1, 2, 1, '');

INSERT INTO Worklist (CurrentFlag, NextFlag, Status, SNote)
VALUES (2, 3, 1, '');

INSERT INTO Worklist (CurrentFlag, NextFlag, Status, SNote)
VALUES (3, 100, 1, '');


---deptlist
INSERT INTO dept_list (id, name, parent_id) VALUES (999999, '未知', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (1, '蔬菜', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (2, '水果', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (3, '大肉', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (4, '干货', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (5, '调味品', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (6, '冻品', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (7, '粮油', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (8, '蛋类', 0);
INSERT INTO dept_list (id, name, parent_id) VALUES (9, '豆制品', 0);