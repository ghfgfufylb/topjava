package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

@Controller
public class MealRestController {
    private final static Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal get(int id) {
        LOG.info("get");
        return service.get(userId(), id);
    }

    public List<MealWithExceed> getAll() {
       LOG.info("get all");
        return service.getAll(userId());
    }
    public void delete(int id) {
        LOG.info("delete");
        service.delete(userId(),id);
    }

    public void update(Meal meal) {
        LOG.info("update");
        service.update(userId(), meal);
    }
    public Meal create(Meal meal) {
        LOG.info("create");
        return service.save(userId(), meal);
    }


    private int userId() {
        return AuthorizedUser.id();
    }
}