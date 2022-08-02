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

class PostTest {

    private static Connection conn;

    private static GeneralPostsDaoImpl generalPostsDao;
    private static DepartmentalPostsDaoImpl departmentPostDao;


    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
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
    void getTitle() {
        GeneralPost post = addGenPost();
        assertEquals("Ways of working", post.getTitle());
    }

    @Test
    void getContent() {
        DepartmentalPost post = addDepPost();
        assertEquals("To incrementally refine our culture we need to start here", post.getContent());
    }

    @Test
    void getUserId() {
        GeneralPost post = addGenPost();
        assertEquals(1, post.getUserId());
    }

    @Test
    void getDepartmentId() {
        DepartmentalPost post = addDepPost();
        assertEquals(1, post.getDepartmentId());
    }

    @Test
    void createdDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        DepartmentalPost post = addDepPost();
        int postId = post.getId();
        long currentTime = System.currentTimeMillis();
        long timestamp = departmentPostDao.findById(postId).getCreated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));

    }

    @Test
    void updatedDateSetsAccurately() throws Exception{
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        GeneralPost post = addGenPost();
        int postId = post.getId();
        generalPostsDao.update(postId,"New Title", "Setting up changes on posts");
        long currentTime = System.currentTimeMillis();
        long timestamp = generalPostsDao.findById(postId).getUpdated();
        assertEquals(sdf.format(currentTime), sdf.format(timestamp));
    }

    @Test
    void getDeleted() throws Exception{
        GeneralPost post = addGenPost();
        int postId = post.getId();
        assertEquals("FALSE", generalPostsDao.findById(postId).getDeleted());
    }

    @Test
    void getType() {
        GeneralPost genPost = addGenPost();
        int genPostId = genPost.getId();
        DepartmentalPost depPost = addDepPost();
        int depPostId = depPost.getId();
        assertEquals("general", generalPostsDao.findById(genPostId).getType());
        assertEquals("departmental", departmentPostDao.findById(depPostId).getType());
    }

    //helpers
    public DepartmentalPost addDepPost (){
        DepartmentalPost post = new DepartmentalPost("Ways of working", "To incrementally refine our culture we need to start here", 1,1);
        departmentPostDao.add(post);
        return post;
    }

    public GeneralPost addGenPost (){
        models.GeneralPost post = new GeneralPost("Ways of working", "To incrementally refine our culture we need to start here", 1);
        generalPostsDao.add(post);
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