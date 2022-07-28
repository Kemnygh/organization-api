package dao;

import models.Department;
import models.Post;
import models.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    List<Post> getAllPostsByUser(int userId);
    // CREATE
    void add(User user);

    void addUserToDepartment(User user, Department department);


    // READ
    User findById(int id);

    // UPDATE
    void update(int id,String firstName, String lastName, String staffId, String position, String phoneNo, String email, String photo);

    // DELETE
    void deleteById(int id);
    void clearAllUsers();

    void deleteAllPostsByUser(int userId);

    List<User> search(String user);
}
