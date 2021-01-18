-- TODO: fix second

ALTER SESSION SET CURRENT_SCHEMA = GUI;

--Trigger check User Data valid
CREATE OR REPLACE TRIGGER check_student
    before insert on student
    for each row
declare

begin

    -- Prüfen ob Vorname eine Nummer enthält

    if REGEXP_LIKE(:new.firstname, '[[:digit:]]') then
        raise_application_error(-20200, 'Der Vorname enthaelt eine Nummer');
    end if;

    -- Prüfen ob Nachname eine Nummer enthält
    if REGEXP_LIKE(:new.lastname, '[[:digit:]]') then
        raise_application_error(-20200, 'Der Nachname enthaelt eine Nummer');
    end if;

    --Prüfen ob das Password eine Nummer enthält
    if not REGEXP_LIKE(:new.password, '[[:digit:]]') then
        raise_application_error(-20200, 'Das Passwort enthaelt keine Nummer und ist daher unsicher');
    end if;

end;
/

--Trigger check Room COurse Time

CREATE OR REPLACE TRIGGER check_room_for_lecture
    before insert on LECTURE
    for each row
declare

    CURSOR GET_TIME IS select * from LECTURE l
                                         join ROOM r on r.id = l.room_id where room_id = :new.room_id;

    TIMESTAMP coursetime;
    TIMESTAMP endtime;

begin
    coursetime := :new.DATETIME;
    endtime:= coursetime + interval '1' hour;

    FOR vResult IN GET_TIME LOOP
            if vResult.DATETIME > coursetime and vResult.DATETIME < endtime then
                raise_application_error(-20200, 'Raum ist zurzeit belegt');
            end if;
        END LOOP;

end;
/
/