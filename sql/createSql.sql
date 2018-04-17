-- 临时图片存储区域
create table imgup.img_temp
(
	ID int(12) auto_increment
		primary key,
	IMG_NAME varchar(256) null,
	CREATE_TIME timestamp default CURRENT_TIMESTAMP not null,
	SUB_SYSTEM varchar(64) null,
	IMG_SIZE varchar(256) null
)
;


-- 正式图片存储区域
create table imgup.img_permanent
(
	ID int(12) auto_increment
		primary key,
	IMG_NAME varchar(256) null,
	CREATE_TIME timestamp default CURRENT_TIMESTAMP not null,
	SUB_SYSTEM varchar(64) null,
	IMG_SIZE varchar(256) null
)
;




