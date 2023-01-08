CREATE TABLE IF NOT EXISTS `currency`
(
	code              VARCHAR(3)      NOT NULL COMMENT '幣別代碼',
	name_zh           VARCHAR(30)     NOT NULL COMMENT '幣別中文名稱',
	created_date      TIMESTAMP NOT NULL COMMENT '建立時間',
	modified_date     TIMESTAMP COMMENT'最後更新時間',
	PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS `currency_history`
(
	id                INT             AUTO_INCREMENT COMMENT '自增主鍵',
	code              VARCHAR(3)      NOT NULL COMMENT '幣別代碼',
	name_zh           VARCHAR(30)     NOT NULL COMMENT '幣別中文名稱',
	created_date      TIMESTAMP NOT NULL COMMENT  '建立時間',
	deleted_date       TIMESTAMP NOT NULL COMMENT  '刪除時間',
	PRIMARY KEY (id)
);