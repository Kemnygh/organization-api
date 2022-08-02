package dao;

import models.DepartmentalPost;
import models.GeneralPost;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentalPostsDaoImplTest {

    private static Connection conn;
    private static DepartmentalPostsDaoImpl departmentalPostsDao;

    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        departmentalPostsDao = new DepartmentalPostsDaoImpl(sql2o);
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
    public void addingPostSetsId() throws Exception {
        DepartmentalPost newPost = addPost();
        assertTrue(newPost.getId()>0);
    }

    @Test
    public void addingDepartmentPostSetsTypeDepartmental() throws Exception {
        DepartmentalPost newPost = addPost();
        int postId = newPost.getId();
        assertEquals("departmental", departmentalPostsDao.findById(postId).getType());
    }

    @Test
    void getAllGetsTheCorrectNumber() {
        addPost();
        addAnotherPost();
        assertEquals(2, departmentalPostsDao.getAll().size());
    }

    @Test
    void findByIdReturnsCreatedPost() {
        DepartmentalPost newPost = addPost();
        int postId = newPost.getId();
        DepartmentalPost storedPost = departmentalPostsDao.findById(postId);
        assertEquals(newPost.getTitle(), storedPost.getTitle());
    }

    @Test
    void updatePostEffectsChanges() {
        DepartmentalPost newPost = addPost();
        int postId = newPost.getId();
        departmentalPostsDao.update(postId,"Altered Title", "changes are being effected");
        assertNotEquals(newPost.getTitle(), departmentalPostsDao.findById(postId).getTitle());
    }

    @Test
    void deleteByIdDeletesCorrectPost() {
        DepartmentalPost newPost = addPost();
        int postId = newPost.getId();
        departmentalPostsDao.deleteById(postId);
        assertEquals(0, departmentalPostsDao.getAll().size());
    }

    @Test
    void clearAllPostsUpdatesDeletedField() {
        addPost();
        addAnotherPost();
        departmentalPostsDao.clearAllPosts();
        assertEquals(0, departmentalPostsDao.getAll().size());
    }

    @Test
    void searchReturnsListOfPosts() {
        DepartmentalPost newPost = addPost();
        assertEquals(1,departmentalPostsDao.search(newPost.getTitle()).size());
    }

    // helpers

    public DepartmentalPost addPost (){
        DepartmentalPost post = new DepartmentalPost("Ways of working", "To incrementally refine our culture we need to start here", 1,1);
        departmentalPostsDao.add(post);
        return post;
    }

    public DepartmentalPost addAnotherPost (){
        DepartmentalPost post = new DepartmentalPost("Company Branding", "Marketing collateral", 1, 1);
        departmentalPostsDao.add(post);
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