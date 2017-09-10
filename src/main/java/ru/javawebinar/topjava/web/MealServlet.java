package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.dao = new MealDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
       Meal meal = new Meal(id.isEmpty()?null:Integer.valueOf(id),
               LocalDateTime.parse(request.getParameter("dateTime")),
               request.getParameter("description"),
               Integer.valueOf(request.getParameter("calories")));
       dao.save(meal);
       LOG.info("save meal: {}", meal );
       response.sendRedirect("meals");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) {
            LOG.info("All meals");
            dao.getAll();
            request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if(action.equals("delete")) {
            Integer id = Integer.valueOf(request.getParameter("mealId"));
            LOG.info("delete meal with id: ", id );
            dao.delete(id);
            response.sendRedirect("/topjava/meals");

        } else if(action.equals("update")) {
            Integer id = Integer.valueOf(request.getParameter("mealId"));
            Meal meal = dao.get(id);
            LOG.info("update meal: {}", meal);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);

        }else if(action.equals("create")) {
            Meal meal = new Meal(null, LocalDateTime.now(), " ", 0);
            LOG.info("create meal: {}",meal);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }


    }
}
