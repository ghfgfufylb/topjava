package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static ru.javawebinar.topjava.model.User.ADMIN_ID;
import static ru.javawebinar.topjava.model.User.USER_ID;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    private final static Comparator<Meal> MEAL_REVERS_COMPARATOR = Comparator.comparing(Meal::getDate).reversed();

    private final static Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
               save(USER_ID, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
               save(ADMIN_ID, new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "АЗавтрак", 1600));
               save(ADMIN_ID, new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 13, 0), "АОбед", 860));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if(meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        LOG.info("save " + meal);
        return meals.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("delete " + id );
       return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info(userId + " get " + id);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("get all " + userId);
        return repository.get(userId).values();
    }
}


