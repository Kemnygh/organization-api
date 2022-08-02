package dao;

import models.GeneralPost;
import models.Post;

import java.util.List;

public interface GeneralPostDao {
    List<GeneralPost> getAll();

    // CREATE
    void add(GeneralPost post);


    // READ
    GeneralPost findById(int id);

    // UPDATE
    void update(int id,String title, String content);

    // DELETE
    void deleteById(int id);
    void clearAllPosts();

    List<GeneralPost> search(String post);
}
