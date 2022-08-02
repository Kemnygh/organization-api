package dao;

import models.Department;
import models.DepartmentalPost;
import models.GeneralPost;
import models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static org.junit.jupiter.api.Assertions.*;

class GeneralPostsDaoImplTest {

    private static Connection conn;
    private static GeneralPostsDaoImpl generalPostsDao;

    @BeforeAll
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "root");
        generalPostsDao = new GeneralPostsDaoImpl(sql2o);
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
        GeneralPost newPost = addPost();
        assertTrue(newPost.getId()>0);
    }

    @Test
    public void addingGeneralPostSetsTypeGeneral() throws Exception {
        GeneralPost newPost = addPost();
        int postId = newPost.getId();
        assertEquals("general", generalPostsDao.findById(postId).getType());
    }

    @Test
    void getAllGetsTheCorrectNumber() {
        addPost();
        addAnotherPost();
        assertEquals(2, generalPostsDao.getAll().size());
    }

    @Test
    void findByIdReturnsCreatedPost() {
        GeneralPost newPost = addPost();
        int postId = newPost.getId();
        GeneralPost storedPost = generalPostsDao.findById(postId);
        assertEquals(newPost.getTitle(), storedPost.getTitle());
    }

    @Test
    void updatePostEffectsChanges() {
        GeneralPost newPost = addPost();
        int postId = newPost.getId();
        generalPostsDao.update(postId,"Altered Title", "changes are being effected");
        assertNotEquals(newPost.getTitle(), generalPostsDao.findById(postId).getTitle());
    }

    @Test
    void deleteByIdDeletesCorrectPost() {
        GeneralPost newPost = addPost();
        int postId = newPost.getId();
        generalPostsDao.deleteById(postId);
        assertEquals(0, generalPostsDao.getAll().size());
    }

    @Test
    void clearAllPostsUpdatesDeletedField() {
        addPost();
        addAnotherPost();
        generalPostsDao.clearAllPosts();
        assertEquals(0, generalPostsDao.getAll().size());
    }

    @Test
    void searchReturnsListOfPosts() {
        GeneralPost newPost = addPost();
        assertEquals(1,generalPostsDao.search(newPost.getTitle()).size());
    }

    // helpers

    public GeneralPost addPost (){
        models.GeneralPost post = new GeneralPost("Ways of working", "To incrementally refine our culture we need to start here", 1);
        generalPostsDao.add(post);
        return post;
    }

    public GeneralPost addAnotherPost (){
        models.GeneralPost post = new GeneralPost("Company Branding", "Marketing collateral", 1);
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