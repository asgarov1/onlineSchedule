alter session set "_ORACLE_SCRIPT"=true;
create user gui identified by password;

grant execute any procedure to gui;
grant create session to gui;

alter user GUI quota 50m on system;
