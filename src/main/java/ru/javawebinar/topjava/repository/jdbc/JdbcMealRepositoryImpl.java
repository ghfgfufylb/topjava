package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedPara;

    private final SimpleJdbcInsert mealInserter;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.mealInserter = new SimpleJdbcInsert(dataSource).withTableName("meals").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedPara = namedParameterJdbcTemplate;
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", meal.getId());
        params.addValue("description", meal.getDescription());
        params.addValue("calories", meal.getCalories());
        params.addValue("date_time", meal.getDateTime());
        params.addValue("user_id", userId);
        if(meal.isNew()) {
            Number key = mealInserter.executeAndReturnKey(params);
            meal.setId(key.intValue());
        } else {
            if(namedPara.update("UPDATE meals SET " +
                    " description=:description, calories=:calories, date_time=:date_time WHERE id=:id AND user_id=:user_id", params)== 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) !=0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meal = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?"
                ,ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {

        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? " +
                "ORDER BY date_time DESC ", ROW_MAPPER, userId);

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?" +
                " AND date_time BETWEEN ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}