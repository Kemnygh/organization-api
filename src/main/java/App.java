import com.google.gson.Gson;
import dao.DepartmentDaoImpl;
import dao.DepartmentalPostsDaoImpl;
import dao.GeneralPostsDaoImpl;
import dao.UserDaoImpl;
import models.Department;
import models.DepartmentalPost;
import models.GeneralPost;
import models.User;
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
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.findById(departmentId));
        });

        post("/user/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        post("/news/general/new", "application/json", (req, res) -> {
            GeneralPost post = gson.fromJson(req.body(), GeneralPost.class);
            post.setType();
            generalPostsDao.add(post);
            res.status(201);
            return gson.toJson(post);
        });

        post("/news/departmental/new", "application/json", (req, res) -> {
            DepartmentalPost post = gson.fromJson(req.body(), DepartmentalPost.class);
            post.setType();
            departmentalPostsDao.add(post);
            res.status(201);
            return gson.toJson(post);
        });

        get("/users/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int userId = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findById(userId));
        });

        get("/departments/:id/specific", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.specificById(departmentId));
        });

        get("/departments/:id/users", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.getAllUsersByDepartment(departmentId));
        });

        get("/departments/:id/details", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.departmentDetails(departmentId));
        });



        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }


}
