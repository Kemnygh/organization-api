package dao;

import models.Post;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class PostsDaoImplTest {


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
//        clearDB();
    }

    @AfterAll
    public static void shutDown() throws Exception {
        conn.close();
    }

    @Test
    void add() {
        Post newPost = addPost();
    }

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
}