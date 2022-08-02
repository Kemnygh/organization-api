package models;

import dao.DepartmentDaoImpl;
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

class DepartmentTest {
    private static Connection conn;
    private static DepartmentDaoImpl departmentDao;


    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        departmentDao = new DepartmentDaoImpl(sql2o);
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
    void NewDepartmentObjectGetsCorrectlyCreated() throws Exception {
        Department department = setUpDepartment();
        assertNotNull(department);
    }

    @Test
    void getName() {
        Department department = setUpDepartment();
        assertEquals("Finance",department.getName());
    }

    @Test
    void getDescription() {
        Department department = setUpDepartment();
        assertEquals("Department to run all monetary aspects of the business",department.getDescription());
    }

    @Test
    void createdDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        Department department = setUpDepartment();
        int departmentId = department.getId();
        long currentTime = System.currentTimeMillis();
        long timestamp = departmentDao.findById(departmentId).getCreated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));

    }

    @Test
    void updatedDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        Department department = setUpDepartment();
        int departmentId = department.getId();
        departmentDao.update(departmentId,"New Finance", "Monetary Services");
        long currentTime = System.currentTimeMillis();
        long timestamp = departmentDao.findById(departmentId).getUpdated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));
    }

    @Test
    void getDeleted() throws Exception{
        Department department = setUpDepartment();
        int departmentId = department.getId();
        assertEquals("FALSE", departmentDao.findById(departmentId).getDeleted());
    }

    //helpers
    public Department setUpDepartment (){
        Department department =  new Department("Finance", "Department to run all monetary aspects of the business");
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