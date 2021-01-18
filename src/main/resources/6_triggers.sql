ALTER SESSION SET CURRENT_SCHEMA = GUI;

--Trigger to check if user data is valid
CREATE OR REPLACE TRIGGER check_person
    BEFORE INSERT
    ON person
    FOR EACH ROW
BEGIN
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

--Trigger check for lecture time
CREATE OR REPLACE TRIGGER check_room_for_lecture
    before insert
    on LECTURE
    for each row
declare
    new_lecture_start_time TIMESTAMP;
    new_lecture_end_time TIMESTAMP;

begin
    new_lecture_start_time := :new.datetime;
    new_lecture_end_time := new_lecture_start_time + interval '90' minute;

    FOR room_booking IN (select *
                         from LECTURE
                         where room_id = :new.room_id)
        LOOP
            if new_lecture_start_time >= room_booking.DATETIME
                and new_lecture_start_time <= room_booking.DATETIME + interval '90' minute then
                raise_application_error(-20200, 'Raum ist an der Zeit belegt');
            end if;

            if new_lecture_end_time >= room_booking.DATETIME
                and new_lecture_end_time <= room_booking.DATETIME + interval '90' minute then
                raise_application_error(-20200, 'Raum ist an der Zeit belegt');
            end if;
        END LOOP;
end;
/
