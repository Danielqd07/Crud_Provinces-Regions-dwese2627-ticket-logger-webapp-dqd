package org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.daos;

import org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.entities.Province;
import org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.entities.Region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProvinceDAOImpl implements ProvinceDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(ProvinceDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public ProvinceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Province> provinceRowMapper =
            (rs, rowNum) -> {

                Province province = new Province();

                province.setId(rs.getLong("id"));
                province.setCode(rs.getString("code"));
                province.setName(rs.getString("name"));

                Region region = new Region();

                region.setId(rs.getLong("region_id"));
                region.setCode(rs.getString("region_code"));
                region.setName(rs.getString("region_name"));

                province.setRegion(region);

                return province;
            };

    @Override
    public List<Province> listAllProvinces() {

        String sql = """
                SELECT p.id,
                       p.code,
                       p.name,
                       r.id AS region_id,
                       r.code AS region_code,
                       r.name AS region_name
                FROM provinces p
                INNER JOIN regions r
                    ON p.region_id = r.id
                """;

        List<Province> provinces =
                jdbcTemplate.query(sql, provinceRowMapper);

        logger.info(
                "Retrieved {} provinces from the database",
                provinces.size()
        );

        return provinces;
    }

    @Override
    public void insertProvince(Province province) {

        String sql = """
                INSERT INTO provinces (code, name, region_id)
                VALUES (?, ?, ?)
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                province.getCode(),
                province.getName(),
                province.getRegion() != null
                        ? province.getRegion().getId()
                        : null
        );

        logger.info(
                "Inserted province. Rows affected: {}",
                rowsAffected
        );
    }

    @Override
    public Province getProvinceById(Long id) {

        String sql = """
                SELECT p.id,
                       p.code,
                       p.name,
                       r.id AS region_id,
                       r.code AS region_code,
                       r.name AS region_name
                FROM provinces p
                INNER JOIN regions r
                    ON p.region_id = r.id
                WHERE p.id = ?
                """;

        List<Province> provinces =
                jdbcTemplate.query(
                        sql,
                        provinceRowMapper,
                        id
                );

        if (!provinces.isEmpty()) {

            Province province = provinces.getFirst();

            logger.info(
                    "Province retrieved: ID = {}, code = {}, name = {}",
                    province.getId(),
                    province.getCode(),
                    province.getName()
            );

            return province;
        }

        logger.warn(
                "No province found with id: {}",
                id
        );

        return null;
    }

    @Override
    public void updateProvince(Province province) {

        String sql = """
                UPDATE provinces
                SET code = ?,
                    name = ?,
                    region_id = ?
                WHERE id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                province.getCode(),
                province.getName(),
                province.getRegion() != null
                        ? province.getRegion().getId()
                        : null,
                province.getId()
        );

        logger.info(
                "Updated province. Rows affected: {}",
                rowsAffected
        );
    }

    @Override
    public boolean existsProvinceByCode(String code) {

        String sql = """
                SELECT COUNT(*)
                FROM provinces
                WHERE UPPER(code) = UPPER(?)
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                code
        );

        return count != null && count > 0;
    }

    @Override
    public boolean existsProvinceByCodeAndNotId(
            String code,
            Long id) {

        String sql = """
                SELECT COUNT(*)
                FROM provinces
                WHERE UPPER(code) = UPPER(?)
                  AND id != ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                code,
                id
        );

        return count != null && count > 0;
    }
    @Override
    public void deleteProvince(Long id) {

        String sql = """
            DELETE FROM provinces
            WHERE id = ?
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                id
        );

        logger.info(
                "Deleted province. Rows affected: {}",
                rowsAffected
        );
    }
}