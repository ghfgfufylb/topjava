package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) {
        ValidationUtil.checkNew(meal);
        return repository.save(userId, meal);
    }

    @Override
    public void update(int userId, Meal meal) {
        ValidationUtil.checkIdConsistent(meal, meal.getId());
    }

    @Override
    public void delete(int userId, int mealId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(userId, mealId), userId);
    }

    @Override
    public Meal get(int userId, int mealId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(userId, mealId), userId);
    }

    @Override
    public List<MealWithExceed> getAll(int userId) throws NotFoundException {
        return MealsUtil.getWithExceeded(new ArrayList<>(repository.getAll(userId)),DEFAULT_CALORIES_PER_DAY);
    }
}