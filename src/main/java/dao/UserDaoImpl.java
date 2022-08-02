package dao;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Sql2o sql2o;

    public UserDaoImpl(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (first_name, last_name, staff_id, user_position, phone_no, email, photo, department_id, created) VALUES (:first_name, :last_name, :staff_id, :user_position, :phone_no, :email, :photo, :department_id, date_part('epoch', now())*1000)"; //raw sql
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users WHERE deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public List<Post> getAllPostsByUser(int id) {
        List<Post> allPosts = new ArrayList<>();

        try(Connection con = sql2o.open()){
            String sqlGeneral = "SELECT * FROM posts WHERE user_id = :id and type='general' and deleted = 'FALSE'";
            List<GeneralPost> generalPosts =  con.createQuery(sqlGeneral)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(GeneralPost.class);
            allPosts.addAll(generalPosts);

            String sqlDepartmental = "SELECT * FROM posts WHERE user_id = :id and type='departmental' and deleted = 'FALSE'";
            List<DepartmentalPost> departmentalPosts =  con.createQuery(sqlDepartmental)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(DepartmentalPost.class);
            allPosts.addAll(departmentalPosts);
        }
        return allPosts;
    }


    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = :id and deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }

    }

    @Override
    public void update(int id,String firstName, String lastName, String staffId, String position, String phoneNo, String email, String photo, int departmentId) {
        String sql = "UPDATE users SET (first_name, last_name, staff_id, user_position, phone_no, email, photo, department_id, updated) = (:firstName, :lastName, :staffId, :position, :phoneNo, :email, :photo, :departmentId, date_part('epoch', now())*1000) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("firstName", firstName)
                    .addParameter("lastName", lastName)
                    .addParameter("staffId", staffId)
                    .addParameter("position", position)
                    .addParameter("phoneNo", phoneNo)
                    .addParameter("email", email)
                    .addParameter("photo", photo)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "UPDATE users SET (deleted, updated) =('TRUE', date_part('epoch', now())*1000) where id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "UPDATE users SET (deleted, updated) = ('TRUE', date_part('epoch', now())*1000)";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllPostsByUser(int id) {
        String sql = "UPDATE posts SET (deleted, updated) = ('TRUE', date_part('epoch', now())*1000) WHERE user_id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<User> search(String user) {
        String userLowercase = user.toLowerCase();
        String sql = "SELECT * FROM users WHERE (lower(first_name) like '%'||:userLowercase||'%' OR lower(last_name) like '%'||:userLowercase||'%') and deleted='FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("userLowercase", userLowercase)
                    .executeAndFetch(User.class);
        }
    }

//    @Override
//    public void addUserToDepartment(User user, Department department){
//        String sql = "INSERT INTO user_departments (user_id, department_id) VALUES (:userId, :departmentId)";
//        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("userId", user.getId())
//                    .addParameter("departmentId", department.getId())
//                    .executeUpdate();
//        } catch (Sql2oException ex){
//            System.out.println(ex);
//        }
//
//    }

}
