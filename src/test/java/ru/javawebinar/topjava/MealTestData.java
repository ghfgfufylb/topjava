package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public final static int FIRST_MEAL_ID = ADMIN_ID +1;
    static {
        USER_MEAL_ONE = new Meal(FIRST_MEAL_ID,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        USER_MEAL_TWO = new Meal(FIRST_MEAL_ID +1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        USER_MEAL_THREE = new Meal(FIRST_MEAL_ID +2,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        USER_MEAL_FOUR = new Meal(FIRST_MEAL_ID +3,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        USER_MEAL_FIVE = new Meal(FIRST_MEAL_ID +4,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        USER_MEAL_SIX = new Meal(FIRST_MEAL_ID +5,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        ADMIN_ONE = new Meal(FIRST_MEAL_ID +6,LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
        ADMIN_TWO = new Meal(FIRST_MEAL_ID +7,LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);
    }
    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
    public static Meal USER_MEAL_ONE;
    public static Meal USER_MEAL_TWO;
    public static Meal USER_MEAL_THREE;
    public static Meal USER_MEAL_FOUR;
    public static Meal USER_MEAL_FIVE;
    public static Meal USER_MEAL_SIX;
    public static Meal ADMIN_ONE;
    public static Meal ADMIN_TWO;

    public static List<Meal> USER_MEALS = Arrays.asList(USER_MEAL_SIX, USER_MEAL_FIVE, USER_MEAL_FOUR
    , USER_MEAL_THREE, USER_MEAL_TWO, USER_MEAL_ONE);
    public static List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_TWO, ADMIN_ONE);
}
