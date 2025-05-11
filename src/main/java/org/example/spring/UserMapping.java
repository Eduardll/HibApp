package org.example.spring;

import org.springframework.stereotype.Component;

@Component
public class UserMapping {
    public UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getEmail());
//        UserDTO userDTO = new UserDTO();
//        userDTO.setAge(user.getAge());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        return userDTO;
    }
    public User toEntity(UserDTO userDTO){
        User user = new User();
        user.setAge(userDTO.getAge());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

}
