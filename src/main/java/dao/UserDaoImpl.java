package dao;

import models.Department;
import models.Post;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Sql2o sql2o;

    public UserDaoImpl(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (firstName, lastName, staffId, position, phoneNo, email, departmentId, photo, created) VALUES (:firstName, :lastName, :StaffId, :position, :phoneNo, :email, :departmentId, :photo, now())"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(user) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row key) //of db
            user.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<User> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE deleted = 'FALSE'") //raw sql
                    .executeAndFetch(User.class); //fetch a list
        }
    }

    @Override
    public User findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE id = :id and deleted = 'FALSE'")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(User.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String firstName, String lastName, String staffId, String position,  String phoneNo, String email, int departmentId, String photo){
        String sql = "UPDATE sites SET (firstName, lastName, staffId, position, phoneNo, email, departmentId, photo, updated) = (:firstName, :lastName, :staffId, :position, :phoneNo, :email, :departmentId, :photo, now()) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("firstName", firstName)
                    .addParameter("lastName", lastName)
                    .addParameter("staffId", staffId)
                    .addParameter("position", position)
                    .addParameter("phoneNo", phoneNo)
                    .addParameter("email", email)
                    .addParameter("departmentId", departmentId)
                    .addParameter("photo", photo)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "UPDATE users SET deleted='TRUE' WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "UPDATE users SET deleted='TRUE'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Post> getAllPostsByUser(int userId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE userId = :userId and deleted = 'FALSE'")
                    .addParameter("userId", userId)
                    .executeAndFetch(Post.class);
        }
    }


    @Override
    public void deleteAllPostsByUser(int userId) {
        String sql = "UPDATE posts SET deleted='TRUE' where userId = :userId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<User> search(String user) {
        String sql = "SELECT * FROM users WHERE (lower(firstName) like '%'||:user||'%' OR lower(lastName) like '%'||:user||'%') and deleted='FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("post", user)
                    .executeAndFetch(User.class);
        }
    }


}
