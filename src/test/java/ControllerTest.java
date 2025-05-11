import org.example.spring.Controller;
import org.example.spring.UserDTO;
import org.example.spring.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Controller.class)
public class ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    public ControllerTest(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
    }

    @BeforeEach
    void setUp() {

        UserDTO userDto = new UserDTO(1L, "Lol kek", 25, "lol@kek.lolkek");
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));
        when(userService.getUsersById(1L)).thenReturn(userDto);
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lol kek"))
                .andExpect(jsonPath("$[0].email").value("lol@kek.lolkek"));
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lol kek"))
                .andExpect(jsonPath("$.email").value("lol@kek.lolkek"));
    }

    @Test
    void testCreateUser() throws Exception {
        String userJson = """
            {
                "name": "Lol kek",
                "age": 25,
                "email": "lol@kek.lolkek"
            }
        """;

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser() throws Exception {
        String updatedJson = """
            {
                "name": "Lol Updated",
                "age": 26,
                "email": "lolupdated@kek.lolkek"
            }
        """;

        mockMvc.perform(put("/api/users/1")
                        .contentType("application/json")
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lol Updated"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.email").value("lolupdated@kek.lolkek"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}