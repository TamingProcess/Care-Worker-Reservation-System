drop table account;
drop table schedule;
drop sequence sche_id_seq;

create user userid identified by userpassword
default tablespace users
temporary tablespace temp;

grant resource, connecto to jack;
--여기까지 관리자계정으로 실행할 것

connect userid/userpassword

--여기부터 복사해서 쿼리실행
/* account */
CREATE TABLE jack.account (
	account_id VARCHAR2(20) NOT NULL, /* account_id */
	account_type VARCHAR2(10) NOT NULL, /* account_type */
	account_password VARCHAR2(20) NOT NULL, /* account_password */
	account_name VARCHAR2(20) NOT NULL, /* account_name */
	account_tel VARCHAR2(13) NOT NULL, /* account_tel */
	account_location VARCHAR2(10) NOT NULL, /* account_location */
	account_email VARCHAR2(30) NOT NULL, /* account_email */
	account_hintForPw VARCHAR2(30) NOT NULL, /* account_hintForPw */
	account_grade VARCHAR2(1) NOT NULL, /* account_grade */
	account_cost NUMBER NOT NULL /* account_cost */
);

COMMENT ON TABLE jack.account IS 'account';

COMMENT ON COLUMN jack.account.account_id IS 'account_id';

COMMENT ON COLUMN jack.account.account_type IS 'account_type';

COMMENT ON COLUMN jack.account.account_password IS 'account_password';

COMMENT ON COLUMN jack.account.account_name IS 'account_name';

COMMENT ON COLUMN jack.account.account_tel IS 'account_tel';

COMMENT ON COLUMN jack.account.account_location IS 'account_location';

COMMENT ON COLUMN jack.account.account_email IS 'account_email';

COMMENT ON COLUMN jack.account.account_hintForPw IS 'account_hintForPw';

COMMENT ON COLUMN jack.account.account_grade IS 'account_grade';

COMMENT ON COLUMN jack.account.account_cost IS 'account_cost';

CREATE UNIQUE INDEX jack.PK_account
	ON jack.account (
		account_id ASC
	);

ALTER TABLE jack.account
	ADD
		CONSTRAINT PK_account
		PRIMARY KEY (
			account_id
		);

/* schedule */
CREATE TABLE jack.schedule (
	sche_id NUMBER NOT NULL, /* sche_id */
	carer_id VARCHAR2(20) NOT NULL, /* carer_id */
	customer_id VARCHAR2(20) NOT NULL, /* customer_id */
	sche_date DATE NOT NULL, /* sche_date */
	sche_begintime NUMBER(2) NOT NULL, /* sche_begintime */
	sche_endtime NUMBER(2) NOT NULL, /* sche_endtime */
	sche_location VARCHAR2(20) NOT NULL, /* sche_location */
	sche_messageFromCustomer VARCHAR2(200) /* sche_messageFromCustomer */
);

COMMENT ON TABLE jack.schedule IS 'schedule';

COMMENT ON COLUMN jack.schedule.sche_id IS 'sche_id';

COMMENT ON COLUMN jack.schedule.carer_id IS 'carer_id';

COMMENT ON COLUMN jack.schedule.customer_id IS 'customer_id';

COMMENT ON COLUMN jack.schedule.sche_date IS 'sche_date';

COMMENT ON COLUMN jack.schedule.sche_begintime IS 'sche_begintime';

COMMENT ON COLUMN jack.schedule.sche_endtime IS 'sche_endtime';

COMMENT ON COLUMN jack.schedule.sche_location IS 'sche_location';

COMMENT ON COLUMN jack.schedule.sche_messageFromCustomer IS 'sche_messageFromCustomer';

CREATE UNIQUE INDEX jack.PK_schedule
	ON jack.schedule (
		sche_id ASC
	);

ALTER TABLE jack.schedule
	ADD
		CONSTRAINT PK_schedule
		PRIMARY KEY (
			sche_id
		);


ALTER TABLE jack.schedule
	ADD
		CONSTRAINT FK_account_TO_schedule
		FOREIGN KEY (
			carer_id
		)
		REFERENCES jack.account (
			account_id
		);

ALTER TABLE jack.schedule
	ADD
		CONSTRAINT FK_account_TO_schedule2
		FOREIGN KEY (
			customer_id
		)
		REFERENCES jack.account (
			account_id
		);
create sequence sche_id_seq;

alter table jack.schedule add sche_servicegrade varchar2(1);

commit;

delete account;
delete schedule;

/*values for test*/

insert into account values('dummy','dummy','dummy','dummy','000-0000-0000',
'gangnam','dummy','dummy','S',0);
insert into account values('kate234','customer','kate234','Kate','010-3244-5555',
'mapo','kate234@kita.com','432etak','C',0);
insert into account values('coex','customer','coex','Coex','010-5252-3737',
'gangnam','coex@kita.com','xeoc','C',0);
insert into account values('carer1','carer','carer1','Carer1','010-2222-2222',
'gangnam','carer1@kita.com','carer1','C',10000);
insert into account values('carer2','carer','carer2','Carer2','010-1111-5252',
'songpa','carer2@kita.com','carer2','C',10000);
insert into account values('carer3','carer','carer3','Carer3','010-3333-5252',
'mapo','carer3@kita.com','carer3','C',10000);

insert into schedule values('1','carer3','coex',to_date(190525, 'YYMMDD'),10,18,'gangnam','work hard!!',4);
insert into schedule values('2','carer3','coex',to_date(190527, 'YYMMDD'),10,18,'gangnam','work hard!!',5);
insert into schedule values('3','carer3','coex',to_date(190601, 'YYMMDD'),10,18,'gangnam','make me some food',5);
insert into schedule values('4','carer3','dummy',to_date(190608, 'YYMMDD'),10,18,'gangnam','work hard!!',5);
insert into schedule values('5','carer3','coex',to_date(190603, 'YYMMDD'),10,18,'gangnam','make me some food',5);
insert into schedule values('6','carer3','dummy',to_date(190608, 'YYMMDD'),10,18,'gangnam','take me to hospital',5);
insert into schedule values('7','carer3','dummy',to_date(190608, 'YYMMDD'),10,18,'gangnam','make me some food',5);
insert into schedule values('8','carer3','coex',to_date(190606, 'YYMMDD'),10,18,'gangnam','make me some food',null);
insert into schedule values('9','carer1','kate234',to_date(190406, 'YYMMDD'),14,20,'gangnam','make me some food',null);
insert into schedule values('10','carer1','kate234',to_date(190516, 'YYMMDD'),09,15,'gangnam','make me feel good',null);
insert into schedule values('11','carer1','dummy',to_date(190608, 'YYMMDD'),09,18,'gangnam','play with my son',null);

commit;
