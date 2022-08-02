import com.google.gson.Gson;
import dao.DepartmentDaoImpl;
import dao.DepartmentalPostsDaoImpl;
import dao.GeneralPostsDaoImpl;
import dao.UserDaoImpl;
import models.Department;
import org.sql2o.Sql2o;


import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 5050; //return port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        DepartmentDaoImpl departmentDao = new DepartmentDaoImpl(sql2o);
        UserDaoImpl userDao = new UserDaoImpl(sql2o);
        GeneralPostsDaoImpl generalPostsDao = new GeneralPostsDaoImpl(sql2o);
        DepartmentalPostsDaoImpl departmentalPostsDao = new DepartmentalPostsDaoImpl(sql2o);
        Gson gson = new Gson();

        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(departmentDao.getAll());//send it back to be displayed
        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int restaurantId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.findById(restaurantId));
        });
    }


}
