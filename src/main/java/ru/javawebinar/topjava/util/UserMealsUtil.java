package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for(UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            Integer calories = meal.getCalories();
            caloriesSumPerDate.put(date, caloriesSumPerDate.getOrDefault(date, 0) + calories);
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for(UserMeal meal : mealList) {
            LocalTime time = meal.getDateTime().toLocalTime();
            LocalDate date = meal.getDateTime().toLocalDate();
            if(TimeUtil.isBetween(time, startTime, endTime)) {
                result.add(new UserMealWithExceed(meal, caloriesSumPerDate.get(date) > caloriesPerDay));
            }

        }

        System.out.println(caloriesSumPerDate);
        System.out.println(result);
        return result;
    }
}
