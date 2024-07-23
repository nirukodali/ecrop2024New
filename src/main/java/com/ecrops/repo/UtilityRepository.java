package com.ecrops.repo;

import com.ecrops.model.KeyValueEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UtilityRepository {
    JdbcTemplate jdbcTemplate;

    public UtilityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KeyValueEntity> getVarietDetails(Integer cropCode) {
        String varietyDetailsQry = "select varietycode as key,varietyname as value from cr_variety_master where cropcode= " + cropCode + "  order by varietyname";
        return jdbcTemplate.query(varietyDetailsQry, new UtilityRowMapper<KeyValueEntity>());
    }

    public List<KeyValueEntity> getWaterResources() {
        String waterResourcesDetailsQry = "select wsrcid as key,wsrcdesc as value from waterresources ";
        return jdbcTemplate.query(waterResourcesDetailsQry, new UtilityRowMapper<KeyValueEntity>());
    }

    public List<KeyValueEntity> getCropIrrigationMethodDetails() {
        String cropIrrigationMethodQuery = "select irgcode as key,irgdesc as value from cropirrgmethod_master";
        return jdbcTemplate.query(cropIrrigationMethodQuery, new UtilityRowMapper<KeyValueEntity>());
    }

    public List<KeyValueEntity> getCropSeedDetails() {
        String cropSeedDetailsQuery = "SELECT cropschtype as key,cropschdesc as value from cropseed_scheme";
        return jdbcTemplate.query(cropSeedDetailsQuery, new UtilityRowMapper<KeyValueEntity>());
    }

    public static class UtilityRowMapper<E> implements RowMapper<KeyValueEntity> {

        public KeyValueEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            KeyValueEntity kve = new KeyValueEntity();
            kve.setKey(rs.getString("key"));
            kve.setValue(rs.getString("value"));
            return kve;
        }
    }


}
