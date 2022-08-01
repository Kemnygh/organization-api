package dao;

import models.Department;
import models.Post;
import models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private static Connection conn;
    private static DepartmentDaoImpl departmentDao;
    private static UserDaoImpl userDao;
    private static PostsDaoImpl postsDao;


    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        departmentDao = new DepartmentDaoImpl(sql2o);
        userDao = new UserDaoImpl(sql2o);
        postsDao = new PostsDaoImpl(sql2o);
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
    public void addingUserSetsId() throws Exception {
        User newUser = setUpUser();
        assertTrue(newUser.getId()>0);
    }

    @Test
    void getAllUsersReturnsCorrectNumberOfUsers() {
        User newUser = setUpUser();
        User anotherUser = setUpAnotherUser();
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    void findUserUsingFindById() {
        User newUser = setUpUser();
        int userId = newUser.getId();
        User storedUser = userDao.findById(userId);
        assertEquals(newUser.getFirst_name(), storedUser.getFirst_name());
        assertEquals(newUser.getLast_name(), storedUser.getLast_name());
    }

    @Test
    void userUpdatesCorrectly() {
        User newUser = setUpUser();
        int userId = newUser.getId();
        userDao.update(userId,"Johnson", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png");
        assertNotEquals(newUser.getFirst_name(), userDao.findById(userId).getFirst_name());
    }

    @Test
    void deleteByIdDeletesUser() {
        User newUser = setUpUser();
        int userId = newUser.getId();
        userDao.deleteById(userId);
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    void clearAllUsersExpungesUsers() {
        User newUser = setUpUser();
        User anotherUser = setUpAnotherUser();
        userDao.clearAllUsers();
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    void getAllPostsByUserReturnsUsersPosts() {
        Post newPost = addPost();
        int userId = newPost.getUserId();
        assertEquals(1,  userDao.getAllPostsByUser(userId).size() );
    }

    @Test
    void deleteAllPostsByUserExpungesAllPostsForUser() {
        Post newPost = addPost();
        int userId = newPost.getUserId();
        userDao.deleteAllPostsByUser(userId);
        assertEquals(0,  userDao.getAllPostsByUser(userId).size() );
    }

    @Test
    void searchReturnsListOfUsers() {
        User newUser = setUpUser();
        assertEquals(1,userDao.search(newUser.getFirst_name()).size());
        assertEquals(1,userDao.search(newUser.getLast_name()).size());
    }

    @Test
    void addUserToDepartmentMapsUserToDepartmentCorrectly() {
        Department newDepartment = setUpDepartment();
        User newUser = setUpUser();
        userDao.addUserToDepartment(newUser,newDepartment);
        assertEquals(1,departmentDao.getAllUsersByDepartment(newDepartment.getId()).size());

    }



    //method helpers
    public Post addPost (){
        Post post = new Post("Ways of working", "To incrementally refine our culture we need to start here", "departmental",1, 1);
        postsDao.add(post);
        return post;
    }

    public Post addAnotherPost (){
        Post post = new Post("TGIF", "Friday fun day", "departmental",2, 1);
        postsDao.add(post);
        return post;
    }


    public User setUpUser (){
        User user = new User("John", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png");
        userDao.add(user);
        return user;
    }

    public User setUpAnotherUser (){
        User user = new User("Jane", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png");
        userDao.add(user);
        return user;
    }

    public Department setUpDepartment (){
        Department department = new Department("Finance", "Department to run all monetary aspects of the business");
        departmentDao.add(department);
        return department;
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