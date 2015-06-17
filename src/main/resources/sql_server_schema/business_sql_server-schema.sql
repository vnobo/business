/******************************************drop table ******************************************
drop table customers;
drop table group_members;
drop table group_authorities;
drop table groups;
drop table authorities;
drop table customers;

drop table authority_in_menu;
drop table authority_menus;

******************************************drop table ******************************************/

CREATE TABLE users (
  username  NVARCHAR(50)  NOT NULL PRIMARY KEY,
  password  NVARCHAR(256) NOT NULL,
  enabled   BIT           NOT NULL,
  firstTime DATETIME DEFAULT getdate(),
  lastTime  DATETIME DEFAULT getdate()
);
GO

CREATE TABLE authorities (
  username  NVARCHAR(50) NOT NULL,
  authority NVARCHAR(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
GO
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
GO


CREATE TABLE groups (
  id         INT IDENTITY PRIMARY KEY,
  group_name NVARCHAR(50) NOT NULL
);
GO
CREATE UNIQUE INDEX ix_groups_group_name ON groups (group_name);

GO

CREATE TABLE group_authorities (
  group_id  INT          NOT NULL,
  authority NVARCHAR(50) NOT NULL,
  CONSTRAINT fk_group_authorities_group FOREIGN KEY (group_id) REFERENCES groups (id)
);
GO
CREATE UNIQUE INDEX ix_group_authorities_g_a ON group_authorities (group_id, authority);
GO

CREATE TABLE group_members (
  id       INT IDENTITY PRIMARY KEY,
  username NVARCHAR(50) NOT NULL,
  group_id INT          NOT NULL,
  CONSTRAINT fk_group_members_group FOREIGN KEY (group_id) REFERENCES groups (id),
  CONSTRAINT fk_group_members_user FOREIGN KEY (username) REFERENCES users (username),
);
GO


CREATE TABLE roles_menus (
  id         INT PRIMARY KEY,
  menu_name  NVARCHAR(50) NOT NULL,
  url        NVARCHAR(128),
  parent_id  INT DEFAULT 0,
  group_name NVARCHAR(50) NOT NULL,
  order_id   INT DEFAULT 0, --菜单显示顺序
  authority  NVARCHAR(50)
);
GO
CREATE UNIQUE INDEX ix_authority_menus_menu_name ON roles_menus (menu_name, authority);
GO


CREATE TABLE customers (
  username  NVARCHAR(50) PRIMARY KEY,
  name      NVARCHAR(50), --公司名称
  consignee NVARCHAR(50), --收货人
  phone     NVARCHAR(50),
  address   NVARCHAR(128),
  email     NVARCHAR(50),
  fax       NVARCHAR(20), --传真
  area      NVARCHAR(20)
);
GO


CREATE TABLE order_goods (
  goodsid        INT PRIMARY KEY,
  deptid         INT        DEFAULT 999999, --类别编码
  price          DEC(12, 2) DEFAULT 0, --批发售价
  imageurl       NVARCHAR(128), --商品图片地址
  editor         NVARCHAR(50),
  last_date_time DATETIME   DEFAULT getdate()
);
GO


CREATE TABLE order_goods_log (
  id           INT IDENTITY NOT NULL,
  goodsid      INT          NOT NULL,
  Editor       NVARCHAR(50) NOT NULL,
  lastDateTime DATETIME     NOT NULL DEFAULT getdate(),
  edit_type    NVARCHAR(1)  NOT NULL DEFAULT 'A' --操作类型('A'=增加,'D'=删除,'U'=修改)
);
GO


CREATE TABLE dept_list (
  id        INT PRIMARY KEY, --类别编码
  name      NVARCHAR(20) NOT NULL, --类别名称
  parent_id INT DEFAULT 0,
);
GO

CREATE TABLE dept_list_log (
  id       INT IDENTITY NOT NULL, --类别编码
  name     NVARCHAR(20) NOT NULL, --类别名称
  Modiopid NVARCHAR(10) NOT NULL, --操作人
  Moditime DATETIME     NOT NULL, --操作时间
  Modiattr NVARCHAR(10) NOT NULL    --操作类型('A'=增加,'D'=删除,'U'=修改)
);
GO


CREATE TABLE purchase (
  sheetid    NVARCHAR(20) PRIMARY KEY, --单据号
  refsheetid NVARCHAR(20), --验收单号
  khbank     NVARCHAR(128), --开户银行
  accno      NVARCHAR(32), --帐号
  phone      NVARCHAR(20), --联系电话
  area       NVARCHAR(6), --邮政编码
  email      NVARCHAR(32), --email地址
  address    NVARCHAR(128), --客户地址
  consignee  NVARCHAR(10)               NOT NULL, --收货人
  editor     NVARCHAR(10)               NOT NULL, --制单人
  editdate   DATETIME DEFAULT getdate(), --制单时间
  hander     NVARCHAR(10), --经手人
  handdate   DATETIME, --经手时间
  checker    NVARCHAR(10), --审核人
  checkdate  DATETIME DEFAULT getdate(), --审核时间
  flag       INT DEFAULT 0              NOT NULL, --审核状态(0=未审核,1=客户提交,2=信息提交,99=撤销,100=完结)
  note       NVARCHAR(255) --备注
);
GO

CREATE TABLE purchaseitem (
  id        dec(19, 0) IDENTITY PRIMARY KEY,
  sheetid   NVARCHAR(20), --单据号
  goodsid   INT          NOT NULL, --商品编码
  goodsname NVARCHAR(64) NOT NULL, --商品编码
  barcode   NVARCHAR(18) NOT NULL, --商品条码
  price     dec(10, 2), --商品售价
  cost      dec(12, 4), --商品进价
  qty       dec(12, 3) DEFAULT 1, --订单数量
  valqty    dec(12, 3) DEFAULT 1, --实收数量
  retqty    dec(12, 3) DEFAULT 0, --退货数量
  sendqty    dec(12, 3) DEFAULT 0, --发货数量
  pknum     NVARCHAR(10) NOT NULL, --订货规格
  memo      NVARCHAR(255), --备注
  promflag  INT        DEFAULT 0  --是否促销

);
GO



CREATE TABLE Worklist (
  CurrentFlag INT NOT NULL, --当前状态
  NextFlag    INT NOT NULL, --下个状态
  Status      INT NOT NULL, --是否启用(0=不启用，1=启用)
  SNote       VARCHAR(50), --说明
);
GO

CREATE TABLE bulletins (
  id       INT      IDENTITY PRIMARY KEY,
  title    NVARCHAR(124),
  context  NVARCHAR(2048),
  username NVARCHAR(64),
  buldate  DATETIME DEFAULT getdate()
);
GO