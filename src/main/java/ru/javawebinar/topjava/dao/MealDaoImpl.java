package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class MealDaoImpl implements MealDao {
    private static final Logger LOG = LoggerFactory.getLogger(MealDao.class);
    private AtomicInteger id = new AtomicInteger(0);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

     {
        for(Meal meal: MEALS) {
            save(meal);
        }
    }
    @Override
    public Meal save(Meal meal) {
        if(meal.getId() == null) {
            LOG.info("Meal initialize id");
            meal.setId(id.incrementAndGet());
        }
        LOG.info("save meal: {}",  meal);
        return repository.put(meal.getId(), meal);
    }

    @Override
    public Meal delete(Integer id) {
        LOG.info("delete meal from repository");
        return repository.remove(id);
    }

    @Override
    public Meal get(Integer id) {
        LOG.info("retrieve meal with id: {} ", id );
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        LOG.info("All meals");
        return new ArrayList<>(repository.values());
    }

    public static void main(String[] args) {
        MealDaoImpl d = new MealDaoImpl();
        ;
    }
}
