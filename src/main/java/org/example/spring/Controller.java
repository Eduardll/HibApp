package org.example.spring;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "CRUD операции над пользователями")
public class Controller {

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей с ссылками на каждый ресурс")
    @GetMapping
    public List<EntityModel<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        return users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(Controller.class).getUserById(user.getId())).withSelfRel(),
                        linkTo(methodOn(Controller.class).getAllUsers()).withRel("all-users")))
                .toList();
    }

    @Operation(summary = "Получить пользователя по ID", responses = {
            @ApiResponse(responseCode = "200", description = "Найден пользователь", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public EntityModel<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUsersById(id);

        return EntityModel.of(user,
                linkTo(methodOn(Controller.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(Controller.class).getAllUsers()).withRel("all-users"));
    }

    @Operation(summary = "Создать нового пользователя", description = "Возвращает созданного пользователя с ссылкой на него")
    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> createUser(@RequestBody UserDTO userDto) {
        UserDTO createdUser = userService.createUser(userDto);

        EntityModel<UserDTO> model = EntityModel.of(createdUser,
                linkTo(methodOn(Controller.class).getUserById(createdUser.getId())).withSelfRel());

        return ResponseEntity.status(201).body(model);
    }

    @Operation(summary = "Обновить данные пользователя", description = "Обновляет существующего пользователя по ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDto) {

        userDto.setId(id);
        UserDTO updatedUser = userService.updateUser(id, userDto);

        EntityModel<UserDTO> model = EntityModel.of(updatedUser,
                linkTo(methodOn(Controller.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(Controller.class).getAllUsers()).withRel("all-users"));

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}