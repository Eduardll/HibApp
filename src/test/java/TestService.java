import org.example.CRUD;
import org.example.InterfaceCRUD;
import org.example.ServiceSloy;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestService {
    public CRUD<User> crud;
    private ServiceSloy serviceSloy;

    @BeforeEach
    void setUp(){
        crud = Mockito.mock(CRUD.class); // Используйте поле класса
        serviceSloy = new ServiceSloy(crud);
    }

    @Test
    public void Create(){
        String name = "ed ed";
        Integer age = 15;
        String email = "lol@kek.lolkek";
        serviceSloy.createUser(name,email,age);

        verify(crud,times(1)).create(any(User.class));
    }

    @Test
    public void readById(){
        User user = new User();
        user.setId(1L);
        user.setName("ed ed");
        user.setAge(15);
        user.setEmail("lol@kek.lolkek");
        when(crud.readById(1L)).thenReturn(user);
        User foundUser = serviceSloy.readById(1L);

        assertNotNull(foundUser);
        assertEquals("ed ed", foundUser.getName());
        assertEquals("lol@kek.lolkek", foundUser.getEmail());
        assertEquals(15, foundUser.getAge());
        verify(crud, times(1)).readById(1L);
    }
    @Test
    public void update(){
        User user = new User();
        user.setId(1L);
        user.setName("ed ed");
        user.setAge(15);
        user.setEmail("lol@kek.lolkek");
        when(crud.readById(1L)).thenReturn(user);
        serviceSloy.update(1L, "ad ad", 25, "kekes.kekus@kukus.lol");

        verify(crud, times(1)).readById(1L);
        verify(crud, times(1)).update(any(User.class));
    }
    @Test
    public void deleteById(){
        User user = new User();
        user.setName("ed ed");
        user.setAge(15);
        user.setEmail("lol@kek.lolkek");
        when(crud.readById(1L)).thenReturn(user);
        serviceSloy.deleteById(1L);

        verify(crud, times(1)).readById(1L);
        verify(crud, times(1)).deleteById(1L);
    }
    @Test
    public void readAll(){
        User user = new User();
        user.setName("ed ed");
        user.setAge(15);
        user.setEmail("lol@kek.lolkek");

        User user2 = new User();
        user2.setName("ad ad");
        user2.setAge(15);
        user2.setEmail("lol@kek.lolkek");

        when(crud.readall()).thenReturn(Arrays.asList(user, user2));

        List<User> users = serviceSloy.readAll();
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("ed ed")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("ad ad")));
        verify(crud, times(1)).readall();
    }
}
