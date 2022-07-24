package dao;

import models.Department;
import models.Post;
import models.User;

import java.util.List;

public interface DepartmentDao {
    // LIST
    List<Department> getAll();
    List<Post> getAllPostsByDepartment(int departmentId);

    // CREATE
    void add(Department department);

    // READ
    Department findById(int id);

    // UPDATE
    void update(int id, String name, String description);

    // DELETE
    void deleteById(int id);
    void clearAllDepartments();

    List<User> getAllUsersByDepartment(int departmentId);
    void deleteAllUsersByDepartment(int departmentId);
    void deleteAllPostsByDepartment(int departmentId);

    List<Department> search(String department);
}
