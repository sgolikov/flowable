ALTER SESSION SET "_ORACLE_SCRIPT"= TRUE;
create tablespace flowable_tabspace datafile 'flowable_tabspace.dat' size 10 M autoextend on;
CREATE USER flowable IDENTIFIED BY flowable default tablespace flowable_tabspace;

GRANT CONNECT TO flowable;
GRANT CREATE SESSION TO flowable;
GRANT ALTER SESSION TO flowable;
GRANT CREATE TABLE TO flowable;