package org.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final KafkaSend kafkaSend;
    @Autowired
    public UserService(UserRepository userRepository,UserMapping userMapping,KafkaSend kafkaSend){
        this.userRepository = userRepository;
        this.userMapping = userMapping;
        this.kafkaSend = kafkaSend;
    }


    public UserDTO createUser(UserDTO userDTO){
        User user = userMapping.toEntity(userDTO);
        User save = userRepository.save(user);
        UserDTOKafka userDTOKafka = new UserDTOKafka("created",save.getAge(),save.getName(),save.getEmail());
        kafkaSend.sendUserCreatedEvent(userDTOKafka);
        return userMapping.toDTO(save);
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users){
            userDTOS.add(userMapping.toDTO(user));
        }
        return userDTOS;
    }

    public UserDTO getUsersById(Long id){
        User user = userRepository.findById(id).orElseThrow();
        return userMapping.toDTO(user);
    }

    public void deleteUser(Long id){
        UserDTOKafka userDTOKafka = new UserDTOKafka("delete",id);
        kafkaSend.sendUserDeletedEvent(userDTOKafka);
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id).orElseThrow();

        user.setAge(userDTO.getAge());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User update = userRepository.save(user);

        UserDTOKafka userDTOKafka = new UserDTOKafka("update",update.getAge(),update.getName(),update.getEmail());
        kafkaSend.sendUserUpdateEvent(userDTOKafka);
        return userMapping.toDTO(update);
    }
}
