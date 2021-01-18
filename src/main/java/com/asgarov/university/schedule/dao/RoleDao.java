package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class RoleDao extends AbstractDao<Long, Role> {
    @Override
    protected String getFindAllProcedure() {
        return "find_all_roles";
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_role";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_role";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_role";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_role";
    }

    @Override
    protected SqlParameterSource getParameterMap(Role role) {
        return new MapSqlParameterSource()
                .addValue("p_name", role.name())
                .addValue("p_id", role.ordinal());
    }

    @Override
    protected Role rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Role.valueOf(resultSet.getString("name"));
    }

    @Override
    protected Role instantiateFromMap(Map<String, Object> result) {
        return Role.valueOf((String) result.get("o_name"));
    }

    @Override
    protected Long mapToKey(Object key) {
        return ((BigDecimal)(key)).longValue();
    }
}
