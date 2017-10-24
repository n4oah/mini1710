-- 회원테이블 (Member): id, password, nincname, email, reg_date, level, ip, score
CREATE TABLE member (
    id VARCHAR2(32) PRIMARY KEY,
    pwd VARCHAR2(128) NOT NULL,
    nickname VARCHAR2(32) UNIQUE NOT NULL,
    email VARCHAR2(64) NOT NULL,
    reg_date DATE DEFAULT SYSDATE,
    level_no NUMBER(2) DEFAULT 1,
    ip VARCHAR2(32),
    score NUMBER(8)
);

-- 카테고리 테이블 (Kategorie): cate_no, used name, group_num, order_num
CREATE TABLE Category (
    cate_no NUMBER PRIMARY KEY,
    used number(1) NOT NULL,
    name VARCHAR(32) NOT NULL UNIQUE,
    group_num number(2) NOT NULL,
    order_num number(2) NOT NULL
);

CREATE SEQUENCE cate_seq;

-- 게시판 테이블 (Board): board_no, cate_no, writer, writer_id, title, content, writer_date, hit_cnt, like_cnt
CREATE TABLE board (
    board_no NUMBER PRIMARY KEY,
	cate_no NUMBER NOT NULL,
	writer VARCHAR2(32) NOT NULL,
	writer_id VARCHAR2(32) NOT NULL,
	title VARCHAR2(32) NOT NULL,
	content VARCHAR2(2000) NOT NULL,
	writer_date DATE DEFAULT SYSDATE,
	hit_cnt NUMBER DEFAULT 0,
	like_cnt NUMBER DEFAULT 0
);

CREATE SEQUENCE board_seq;

-- 댓글 테이블 (Comment): no, board_no, writer, writer_id, content, writer_date
CREATE TABLE board_comment (
	no NUMBER PRIMARY KEY,
	board_no NUMBER NOT NULL,
	writer VARCHAR2(32) NOT NULL,
	writer_id VARCHAR2(32) NOT NULL,
	content VARCHAR2(500) NOT NULL,
	writer_date DATE DEFAULT SYSDATE
);

CREATE SEQUENCE comment_seq;

-- 첨부 파일 테이블 (Board_File): no, board_no, file_path, ori_name, system_name, file_size
CREATE TABLE board_file (
	no NUMBER PRIMARY KEY,
	board_no NUMBER NOT NULL,
	file_path VARCHAR2(128) NOT NULL,
	ori_name VARCHAR2(256) NOT NULL,
	system_name VARCHAR2(256) NOT NULL,
	file_size NUMBER NOT NULL
);

CREATE SEQUENCE board_file_seq;

-- 스케치 퀴즈 테이블 (Sketch_Quiz): quiz, score
CREATE TABLE sketch_quiz (
	quiz VARCHAR2(32) PRIMARY KEY,
	score NUMBER(3) NOT NULL
);

INSERT INTO sketch_quiz (quiz, score) VALUES ('호랑이', 10);
INSERT INTO sketch_quiz (quiz, score) VALUES ('사자', 10);
INSERT INTO sketch_quiz (quiz, score) VALUES ('고양이', 15);
INSERT INTO sketch_quiz (quiz, score) VALUES ('강아지', 15);
INSERT INTO sketch_quiz (quiz, score) VALUES ('낙타', 10);