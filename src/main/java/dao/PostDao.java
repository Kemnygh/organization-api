package dao;

import models.Post;
import models.User;

import java.util.List;

public interface PostDao {
    List<Post> getAll();

    // CREATE
    void add(Post post);


    // READ
    Post findById(int id);

    // UPDATE
    void update(int id,String title, String content);

    // DELETE
    void deleteById(int id);
    void clearAllPosts();

    List<Post> search(String post);
}
