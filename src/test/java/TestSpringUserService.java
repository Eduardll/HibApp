import org.example.spring.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MainSpring.class)
class TestSpringUserService {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapping userMapping;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("Lol kek");
        user.setEmail("lol@kek.lolkek");
        user.setAge(30);

        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setName("Lol kek");
        userDto.setEmail("lol@kek.lolkek");
        userDto.setAge(30);

        when(userMapping.toDTO(any(User.class))).thenReturn(userDto);
        when(userMapping.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals("Lol kek", users.get(0).getName());
    }

    @Test
    void testGetUserById() {
        UserDTO userDto = userService.getUsersById(1L);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Lol kek", userDto.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        UserDTO userDto = new UserDTO("Kek lol", 22, "lol@kek.lolkek");
        UserDTO createdUserDto = userService.createUser(userDto);
        Assertions.assertNotNull(createdUserDto);
        Assertions.assertEquals("Kek lol", createdUserDto.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        UserDTO userDto = new UserDTO(1L,"kek_lol", 30, "lolupdated@kek.lolkek");
        UserDTO updatedUserDto = userService.updateUser(1L, userDto);
        Assertions.assertNotNull(updatedUserDto);
        Assertions.assertEquals("kek_lol Updated", updatedUserDto.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
