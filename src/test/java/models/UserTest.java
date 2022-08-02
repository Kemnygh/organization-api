package models;

import dao.DepartmentalPostsDaoImpl;
import dao.GeneralPostsDaoImpl;
import dao.UserDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Connection conn;

    private static UserDaoImpl userDao;


    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        userDao = new UserDaoImpl(sql2o);
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
    void getFirst_name() {
        User user = setUpUser();
        assertEquals("John", user.getFirst_name());
    }

    @Test
    void getLast_name() {
        User user = setUpUser();
        assertEquals("Doe", user.getLast_name());
    }

    @Test
    void getStaff_id() {
        User user = setUpUser();
        assertEquals("EK001", user.getStaff_id());
    }

    @Test
    void getUser_position() {
        User user = setUpUser();
        assertEquals("Manager", user.getUser_position());
    }

    @Test
    void getPhone_no() {
        User user = setUpUser();
        assertEquals("012345678", user.getPhone_no());
    }

    @Test
    void getEmail() {
        User user = setUpUser();
        assertEquals("john.doe@org.com", user.getEmail());
    }

    @Test
    void getPhoto() {
        User user = setUpUser();
        assertEquals("resources/assets/images/test.png", user.getPhoto());
    }

    @Test
    void createdDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        User user = setUpUser();
        int userId = user.getId();
        long currentTime = System.currentTimeMillis();
        long timestamp = userDao.findById(userId).getCreated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));

    }

    @Test
    void updatedDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        User user = setUpAnotherUser();
        int userId = user.getId();
        userDao.update(userId,"Janet", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png", 1);
        long currentTime = System.currentTimeMillis();
        long timestamp = userDao.findById(userId).getUpdated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));
    }

    @Test
    void getDeleted() throws Exception{
        User user = setUpUser();
        int userId = user.getId();
        assertEquals("FALSE", userDao.findById(userId).getDeleted());
    }

    @Test
    void getDepartment_id() {
        User user = setUpUser();
        assertEquals(1, user.getDepartment_id());
    }

    //helpers
    public User setUpUser() {
        User user = new User("John", "Doe", "EK001", "Manager", "012345678", "john.doe@org.com", "resources/assets/images/test.png", 1);
        userDao.add(user);
        return user;
    }

    public User setUpAnotherUser() {
        User user = new User("Jane", "Doe", "EK002", "Manager", "123456789", "jane.doe@org.com", "resources/assets/images/test.png", 1);
        userDao.add(user);
        return user;
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
        }
        try (Connection con = sql2o.open()) {
            con.createQuery(sql1)
                    .executeUpdate();
        }
        try (Connection con = sql2o.open()) {
            con.createQuery(sql2)
                    .executeUpdate();
        }
        try (Connection con = sql2o.open()) {
            con.createQuery(sql3)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}