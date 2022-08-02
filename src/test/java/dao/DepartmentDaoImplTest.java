package dao;

import models.*;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoImplTest {

    private static Connection conn;
    private static DepartmentDaoImpl departmentDao;
    private static UserDaoImpl userDao;
    private static GeneralPostsDaoImpl generalPostsDao;
    private static DepartmentalPostsDaoImpl departmentPostDao;


    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        departmentDao = new DepartmentDaoImpl(sql2o);
        userDao = new UserDaoImpl(sql2o);
        generalPostsDao = new GeneralPostsDaoImpl(sql2o);
        departmentPostDao = new DepartmentalPostsDaoImpl(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception { //I have changed
        System.out.println("clearing database");
        clearDB();
    }

    @AfterAll
    public static void shutDown() throws Exception {
        conn.close();
    }

    @Test
    void addingDepartmentSetsId() throws Exception {
        Department newDepartment = setUpDepartment();
        assertTrue(newDepartment.getId()>0);
    }

    @Test
    void getAll() throws Exception {
        Department newDepartment = setUpDepartment();
        Department newDepartment2 = setUpAnotherDepartment();
        assertEquals(2, departmentDao.getAll().size());
    }

    @Test
    void findById() throws Exception {
        Department newDepartment = setUpDepartment();
        int departmentId = newDepartment.getId();
        int dbObjectId = departmentDao.getAll().get(0).getId();
        assertEquals(departmentId, dbObjectId);
    }

    @Test
    void specificById() throws Exception {
        Department newDepartment = setUpDepartment();
        int departmentId = newDepartment.getId();
        User user = new User("John", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png", departmentId);
        userDao.add(user);
        User anotherUser = new User("Jane", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png", departmentId);
        userDao.add(anotherUser);
        assertEquals(newDepartment.getName(),departmentDao.specificById(departmentId).get("name"));
        assertEquals(newDepartment.getDescription(),departmentDao.specificById(departmentId).get("description"));
        assertEquals(Integer.toString(departmentDao.getAllUsersByDepartment(departmentId).size()),departmentDao.specificById(departmentId).get("no of employees"));
    }

    @Test
    void departmentDetails() throws Exception {
        Department newDepartment = setUpDepartment();
        int departmentId = newDepartment.getId();
        User user = new User("John", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png", departmentId);
        userDao.add(user);
        User anotherUser = new User("Jane", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png", departmentId);
        userDao.add(anotherUser);
        DepartmentalPost post = new DepartmentalPost("TGIF", "Friday fun day", user.getId(), departmentId);
        departmentPostDao.add(post);
        DepartmentalPost anotherPost = new DepartmentalPost("Election Day", "Tuesday 9th August 2022", anotherUser.getId(), departmentId);
        departmentPostDao.add(anotherPost);
        assertEquals(newDepartment.getName(),departmentDao.departmentDetails(departmentId).get("name"));
        assertEquals(3,departmentDao.departmentDetails(departmentId).size());
    }

    @Test
    void update() throws Exception {
        Department newDepartment = setUpAnotherDepartment();
        int departmentId = newDepartment.getId();
        String departmentName = newDepartment.getName();
        departmentDao.update(departmentId,"Marketing & Research", "Promotion of business and getting insights");
        String departmentNameAfterUpdate = departmentDao.findById(departmentId).getName();
        System.out.println(departmentNameAfterUpdate);
        assertNotEquals(departmentName, departmentNameAfterUpdate);
    }

    @Test
    void deleteById() throws Exception {
        Department newDepartment = setUpDepartment();
        int departmentId = newDepartment.getId();
        departmentDao.deleteById(departmentId);
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    void clearAllDepartments() {
        Department newDepartment = setUpDepartment();
        Department newDepartment2 = setUpAnotherDepartment();
        departmentDao.clearAllDepartments();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    void getAllUsersByDepartment() {
        User newUser = setUpUser();
        User anotherUser = setUpAnotherUser();
        int departmentId = newUser.getDepartment_id();
        assertEquals(newUser.getDepartment_id(),anotherUser.getDepartment_id());
        assertEquals(2,departmentDao.getAllUsersByDepartment(departmentId).size());

    }

    @Test
    void getAllPostsByDepartment() {
        Post newPost = addPost();
        Post anotherPost = addAnotherPost();
       assertEquals(1,  departmentDao.getAllPostsByDepartment(anotherPost.getDepartmentId()).size());
    }

    @Test
    void deleteAllUsersByDepartment() {
        User newUser = setUpUser();
        User anotherUser = setUpAnotherUser();
        int departmentId = newUser.getDepartment_id();
        departmentDao.deleteAllUsersByDepartment(departmentId);
        assertEquals(0,departmentDao.getAllUsersByDepartment(departmentId).size());
    }

    @Test
    void deleteAllPostsByDepartment() {
        DepartmentalPost anotherPost = addAnotherPost();
        departmentDao.deleteAllPostsByDepartment(anotherPost.getDepartmentId());
        assertEquals(0,  departmentDao.getAllPostsByDepartment(anotherPost.getDepartmentId()).size());
    }

    @Test
    void search() {
        List<Object> searchedDepartments = new ArrayList<>();
        Department newDepartment = setUpDepartment();
        String searchString = "Fin";
        searchedDepartments.add(departmentDao.search(searchString));
        assertEquals(1,searchedDepartments.size());
    }
// helpers
    public Department setUpDepartment (){
        Department department = new Department("Finance", "Department to run all monetary aspects of the business");
        departmentDao.add(department);
        return department;
    }

    public Department setUpAnotherDepartment (){
        Department department = new Department("Marketing", "Department in charge of promoting the business");
        departmentDao.add(department);
        return department;
    }

    public User setUpUser (){
        User user = new User("John", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png", 1);
        userDao.add(user);
        return user;
    }

    public User setUpAnotherUser (){
        User user = new User("Jane", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png", 1);
        userDao.add(user);
        return user;
    }

    public GeneralPost addPost (){
        models.GeneralPost post = new GeneralPost("Ways of working", "To incrementally refine our culture we need to start here", 1);
        generalPostsDao.add(post);
        return post;
    }

    public DepartmentalPost addAnotherPost (){
        DepartmentalPost post = new DepartmentalPost("TGIF", "Friday fun day", 2, 1);
        departmentPostDao.add(post);
        return post;
    }


    public void clearDB() {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        String sql = "DELETE FROM departments";
        String sql1 = "DELETE FROM users";
        String sql2 = "DELETE FROM posts";
        String sql3 = "DELETE FROM user_departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        }try (Connection con = sql2o.open()) {
            con.createQuery(sql1)
                    .executeUpdate();
        }try (Connection con = sql2o.open()) {
            con.createQuery(sql2)
                    .executeUpdate();
        }try (Connection con = sql2o.open()) {
            con.createQuery(sql3)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }




}