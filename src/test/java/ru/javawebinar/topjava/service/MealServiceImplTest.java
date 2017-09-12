package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceImplTest {
   static {
        SLF4JBridgeHandler.install();
    }
    @Autowired
    MealService service;

    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(USER_MEAL_ONE, service.get(100002, USER_ID));

    }
    @Test(expected = NotFoundException.class)
    public void negotiveGet() throws Exception {
        service.get(22, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ADMIN_ONE.getId(), ADMIN_ID);
        MATCHER.assertCollectionEquals(service.getAll(ADMIN_ID), Collections.singletonList(ADMIN_TWO));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealsBetween = service.getBetweenDateTimes(USER_MEAL_ONE.getDateTime(), USER_MEAL_SIX.getDateTime(), USER_ID);
        MATCHER.assertCollectionEquals(mealsBetween, USER_MEALS);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), USER_MEALS);
    }


    @Test
    public void save() throws Exception {
        Meal meal = new Meal();
        meal.setCalories(300);
        meal.setDateTime(LocalDateTime.now());
        meal.setDescription("sad");
        service.save(meal, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(meal, ADMIN_TWO, ADMIN_ONE), service.getAll(ADMIN_ID));
    }
    @Test(expected = NotFoundException.class)
    public void NotFoundDelete() throws Exception {
        service.delete(USER_MEAL_ONE.getId(), ADMIN_ID);
    }
    @Test
    public void update() throws Exception {
        Meal meal = new Meal(USER_MEAL_ONE);
        meal.setDescription("sad");
        meal.setCalories(300);
        service.update(meal, USER_ID);
        assertEquals(meal.getDescription(), "sad");
        assertEquals(meal.getCalories(), 300);

    }
     @Test(expected = NotFoundException.class)
    public void NotFoundUpdate() throws Exception {
        service.update(USER_MEAL_ONE, ADMIN_ID );
     }

    }
