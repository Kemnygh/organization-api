package dao;

import models.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class PostsDaoImpl implements PostDao {
    private final Sql2o sql2o;

    public PostsDaoImpl(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Post post) {
        String sql = "INSERT INTO posts (title, content, type, user_id, department_id, created) VALUES (:title, :content, :type, :userId, :departmentId, round(date_part('epoch', now())))"; //raw sql
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
    public List<Post> getAll() {
        String sql = "SELECT * FROM posts WHERE deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Post.class);
        }
    }


    @Override
    public Post findById(int id) {
        String sql = "SELECT * FROM posts WHERE id = :id and deleted = 'FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Post.class);
        }

    }

    @Override
    public void update(int id, String title, String content) {
        String sql = "UPDATE posts SET (title, content, updated) = (:title, :content, now()) WHERE id=:id";
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
        String sql = "UPDATE posts SET deleted='TRUE' where id = :id";
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
        String sql = "UPDATE posts SET deleted='TRUE'";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<Post> search(String post) {
        String sql = "SELECT * FROM posts WHERE lower(title) like '%'||:post||'%' and deleted='FALSE'";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("post", post)
                    .executeAndFetch(Post.class);
        }
    }
}
