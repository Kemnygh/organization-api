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

        //post: route to create new department
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //get: route to get all departments
        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(departmentDao.getAll());//send it back to be displayed
        });

        //get: route to get individual department
        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.findById(departmentId));
        });

        //post: route to create new user
        post("/user/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //post: route to create general news
        post("/news/general/new", "application/json", (req, res) -> {
            GeneralPost post = gson.fromJson(req.body(), GeneralPost.class);
            post.setType();
            generalPostsDao.add(post);
            res.status(201);
            return gson.toJson(post);
        });

        //post: route to create departmental news
        post("/news/departmental/new", "application/json", (req, res) -> {
            DepartmentalPost post = gson.fromJson(req.body(), DepartmentalPost.class);
            post.setType();
            departmentalPostsDao.add(post);
            res.status(201);
            return gson.toJson(post);
        });

        //get: route to get individual user
        get("/users/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int userId = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findById(userId));
        });

        //get: route to get department name, description & number of employees
        get("/departments/:id/specific", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.specificById(departmentId));
        });

        //get: route to get all employees in a department
        get("/departments/:id/users", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.getAllUsersByDepartment(departmentId));
        });

        //get: route to get all details for a department plus employees and news
        get("/departments/:id/details", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.departmentDetails(departmentId));
        });

        //get: route to soft delete user by marking deleted field as TRUE
        get("/user/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int userId = Integer.parseInt(req.params("id"));
            userDao.deleteById(userId);
            return "{\"message\":\"User Deleted.\"}";
        });

        //get: route to soft delete general news by marking deleted field as TRUE
        get("/general/post/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int postId = Integer.parseInt(req.params("id"));
            generalPostsDao.deleteById(postId);
            return "{\"message\":\"Post Deleted.\"}";
        });

        //get: route to soft delete departmental news by marking deleted field as TRUE
        get("/department/post/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int postId = Integer.parseInt(req.params("id"));
            departmentalPostsDao.deleteById(postId);
            return "{\"message\":\"Post Deleted.\"}";
        });


        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }


}
