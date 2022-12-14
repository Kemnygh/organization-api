package dao;

import models.Department;
import models.Post;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    private final Sql2o sql2o;

    public DepartmentDaoImpl(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (name, description, created) VALUES (:name, :description, :created)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(department) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row key) //of db
            department.setId(id);
            department.setCreated();//update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE deleted = 'FALSE'") //raw sql
                    .executeAndFetch(Department.class); //fetch a list
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id and deleted = 'FALSE'")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Department.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newDepartment, String description){
        String sql = "UPDATE departments SET (name, description, updated) = (:name, :description, round( date_part( 'epoch', now() ) )) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newDepartment)
                    .addParameter("description", description)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "UPDATE departments SET (deleted, updated) = ('TRUE', round(date_part('epoch',now()))) WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllDepartments() {
        String sql = "UPDATE departments SET deleted='TRUE'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAllUsersByDepartment(int departmentId) {
        ArrayList<User> users = new ArrayList<>();

        String joinQuery = "SELECT user_id FROM user_departments WHERE department_id = :departmentId";

        try(Connection con = sql2o.open()){
            List<Integer> allUsers = con.createQuery(joinQuery)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);
            for (Integer userId : allUsers){
                String userQuery = "SELECT * FROM users WHERE id = :userId";
                users.add(
                        con.createQuery(userQuery)
                                .addParameter("userId", userId)
                                .executeAndFetchFirst(User.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return users;
    }

    @Override
    public List<Post> getAllPostsByDepartment(int departmentId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE department_id = :departmentId and deleted = 'FALSE'")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Post.class);
        }
    }

    @Override
    public void deleteAllUsersByDepartment(int departmentId) {
        String sql = "DELETE from user_departments WHERE department_id = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllPostsByDepartment(int departmentId) {
        String sql = "UPDATE posts SET deleted='TRUE' where department_id = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> search(String department) {
        String sql = "SELECT * FROM departments WHERE lower(name) like '%'||:department||'%' and deleted='FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("department", department)
                    .executeAndFetch(Department.class);
        }
    }
}
