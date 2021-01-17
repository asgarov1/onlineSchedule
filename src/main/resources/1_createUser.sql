alter session set "_ORACLE_SCRIPT"=true;
create user gui identified by password;

grant create any table TO gui;
grant execute any procedure to gui;
grant create session to gui;

alter user GUI quota 50m on system;
ALTER SESSION SET CURRENT_SCHEMA = GUI;
--GRANT ALL PRIVILEGES TO gui;