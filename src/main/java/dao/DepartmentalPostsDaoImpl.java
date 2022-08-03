package dao;

import models.DepartmentalPost;
import models.GeneralPost;
import models.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class DepartmentalPostsDaoImpl implements DepartmentalPostDao {
    private final Sql2o sql2o;

    public DepartmentalPostsDaoImpl(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(DepartmentalPost post) {
        String sql = "INSERT INTO posts (title, content, type, user_id, department_id, created) VALUES (:title, :content, :type, :userId, :departmentId, date_part('epoch', now())*1000)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(post) //map argument onto the query, so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now row number (row key) //of db
            post.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<DepartmentalPost> getAll() {
        String sql = "SELECT * FROM posts WHERE type = 'departmental' and deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(DepartmentalPost.class);
        }
    }


    @Override
    public DepartmentalPost findById(int id) {
        String sql = "SELECT * FROM posts WHERE id = :id and deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(DepartmentalPost.class);
        }

    }

    @Override
    public void update(int id, String title, String content) {
        String sql = "UPDATE posts SET (title, content, updated) = (:title, :content, date_part('epoch', now())*1000) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "UPDATE posts SET (deleted, updated) =('TRUE', date_part('epoch', now())*1000) where id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllPosts() {
        String sql = "UPDATE posts SET (deleted, updated) =('TRUE', date_part('epoch', now())*1000)";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<DepartmentalPost> search(String post) {
        String postLowercase = post.toLowerCase();
        String sql = "SELECT * FROM posts WHERE lower(title) like '%'||:postLowercase||'%' and deleted='FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("postLowercase", postLowercase)
                    .executeAndFetch(DepartmentalPost.class);
        }
    }
}
