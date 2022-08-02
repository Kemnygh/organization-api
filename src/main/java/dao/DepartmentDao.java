package dao;

import models.Department;
import models.DepartmentalPost;
import models.Post;
import models.User;

import java.util.List;
import java.util.Map;

public interface DepartmentDao {
    // LIST
    List<Department> getAll();
    List<DepartmentalPost> getAllPostsByDepartment(int departmentId);

    // CREATE
    void add(Department department);

    // READ
    Department findById(int id);

    Map<String, String> specificById(int id);

    Map<String, Object> departmentDetails(int id);

    // UPDATE
    void update(int id, String name, String description);

    // DELETE
    void deleteById(int id);
    void clearAllDepartments();

    List<User> getAllUsersByDepartment(int departmentId);
    void deleteAllUsersByDepartment(int departmentId);
    void deleteAllPostsByDepartment(int departmentId);

    List<Department> search(String department);

    //We already know these are a way of ensuring any class we create agrees to provide a minimum standard of code necessary to run correctly.
//    The ability to inspect the code in the system and see object types is not reflection, but rather Type Introspection.
//    Reflection is then the ability to make modifications at runtime by making use of introspection. The distinction is
//    necessary here as some languages support introspection, but do not support reflection. One such example is C++
}
