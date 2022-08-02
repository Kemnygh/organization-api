package dao;

import models.DepartmentalPost;
import models.GeneralPost;
import models.Post;

import java.util.List;

public interface DepartmentalPostDao {
    List<DepartmentalPost> getAll();

    // CREATE
    void add(DepartmentalPost post);


    // READ
    DepartmentalPost findById(int id);

    // UPDATE
    void update(int id,String title, String content);

    // DELETE
    void deleteById(int id);
    void clearAllPosts();

    List<DepartmentalPost> search(String post);
}
