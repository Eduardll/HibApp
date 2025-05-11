//import org.example.CRUD;
//import org.example.spring.User;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.junit.jupiter.api.*;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//public class TestDao {
//    private CRUD<User> crud;
//    public SessionFactory sessionFactory;
//    @Container
//    public static PostgreSQLContainer<?> sqlContainer = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("database")
//            .withUsername("name")
//            .withPassword("password");
//    @BeforeAll
//    static void startContainer(){
//        sqlContainer.start();
//    }
//    @AfterAll
//    static void stopContainer(){
//        sqlContainer.stop();
//    }
//
//    @BeforeEach
//    void createTable(){
//        Configuration configuration = new Configuration();
//        configuration.setProperty("hibernate.connection.url", sqlContainer.getJdbcUrl());
//        configuration.setProperty("hibernate.connection.username", sqlContainer.getUsername());
//        configuration.setProperty("hibernate.connection.password", sqlContainer.getPassword());
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        configuration.setProperty("hibernate.current_session_context_class", "thread");
//        configuration.addAnnotatedClass(User.class);
//
//        sessionFactory = configuration.buildSessionFactory();
//        crud = new CRUD<>(User.class);
//        crud.sessionFactory = sessionFactory;
//    }
//    @AfterEach
//    void stopSession(){
//        sessionFactory.close();
//    }
//
//    @Test
//    void testCreateAndReadById() {
//        User user = new User();
//        user.setName("ed ed");
//        user.setAge(15);
//        user.setEmail("lol@kek.lolkek");
//        crud.create(user);
//        User foundUser = crud.readById(1L);
//
//        assertNotNull(foundUser);
//        assertEquals("ed ed", foundUser.getName());
//        assertEquals("lol@kek.lolkek", foundUser.getEmail());
//        assertEquals(15, foundUser.getAge());
//    }
//
//    @Test
//    void testReadAll() {
//        User user = new User();
//        user.setName("ed ed");
//        user.setAge(15);
//        user.setEmail("lol@kek.lolkek");
//
//        User user2 = new User();
//        user2.setName("ad ad");
//        user2.setAge(15);
//        user2.setEmail("lol@kek.lolkek");
//        crud.create(user);
//        crud.create(user2);
//        List<User> users = crud.readall();
//
//        assertEquals(2, users.size());
//        assertTrue(users.stream().anyMatch(u -> u.getName().equals("ed ed")));
//        assertTrue(users.stream().anyMatch(u -> u.getName().equals("ad ad")));
//    }
//
//    @Test
//    void testUpdate() {
//        User user = new User();
//        user.setName("ed ed");
//        user.setAge(15);
//        user.setEmail("lol@kek.lolkek");
//        crud.create(user);
//        User existingUser = crud.readById(1L);
//        existingUser.setName("ed1 ed1");
//        crud.update(existingUser);
//        User updatedUser = crud.readById(1L);
//
//        assertNotNull(updatedUser);
//        assertEquals("ed1 ed1", updatedUser.getName());
//    }
//
//    @Test
//    public void testDelete() {
//        User user = new User();
//        user.setName("ed ed");
//        user.setAge(15);
//        user.setEmail("lol@kek.lolkek");
//        crud.create(user);
//        crud.deleteById(1L);
//        User deletedUser = crud.readById(1L);
//
//        assertNull(deletedUser);
//    }
//
//}
//
