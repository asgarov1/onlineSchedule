package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.Room;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class RoomDao extends AbstractDao<Long, Room> {

    @Override
    protected Room rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getLong("id"));
        room.setName(resultSet.getString("name"));
        return room;
    }

    @Override
    protected SqlParameterSource getParameterMap(Room room) {
        return new MapSqlParameterSource()
                .addValue("p_name", room.getName())
                .addValue("p_id", room.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_room";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_room";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_room";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_rooms";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_room";
    }

    @Override
    protected Room instantiateFromMap(Map<String, Object> result) {
        Room room = new Room();
        room.setId((Long) result.get("p_id"));
        room.setName((String) result.get("o_name"));
        return room;
    }
}
