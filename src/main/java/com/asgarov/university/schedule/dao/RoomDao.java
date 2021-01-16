package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.Room;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RoomDao extends AbstractDao<Long, Room> {
    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName() + " SET name = ? WHERE id = ?";
    }

    @Override
    protected Room rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getLong("id"));
        room.setName(resultSet.getString("name"));
        return room;
    }

    @Override
    protected Object[] updateParameters(final Room room) {
        return new Object[]{room.getName(), room.getId()};
    }

    @Override
    protected String tableName() {
        return "room";
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
}
