import dao.DepartmentDaoImpl;
import dao.UserDaoImpl;
import org.sql2o.Sql2o;


import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

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
    }
}
