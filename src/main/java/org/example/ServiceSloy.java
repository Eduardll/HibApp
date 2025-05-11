package org.example;

import org.example.spring.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServiceSloy {
     private final Logger logger = LoggerFactory.getLogger(ServiceSloy.class);
     private CRUD<User>  userCRUD;
     public ServiceSloy(CRUD<User> userCRUD){
          this.userCRUD = userCRUD;
     }
     public void createUser(String name,String email,Integer age){
          if(name == null) throw new IllegalArgumentException("Не может быть пустого имени");
          if(age == null) throw new IllegalArgumentException("Не может быть пустого возраста");
          if(email == null || !email.contains("@")) throw new IllegalArgumentException("Не может быть пустой почты");
          logger.info("createUser данные name{},email{},age{}",name,email,age);
          User user = new User();
          user.setAge(age);
          user.setName(name);
          user.setEmail(email);
          logger.info("user данные name{},email{},age{}",user.getName(),user.getEmail(),user.getAge());
          userCRUD.create(user);
     }
     public User readById(Long id){
          User user = userCRUD.readById(id);
          if(user == null) throw new RuntimeException("По такому id не существует user");
          return user;
     }
     public void update(Long id, String name,Integer age,String email){
          if(name == null) throw new IllegalArgumentException("Не может быть пустого имени");
          if(age == null) throw new IllegalArgumentException("Не может быть пустого возраста");
          if(email == null || !email.contains("@")) throw new IllegalArgumentException("Не может быть пустой почты");
          logger.info("update данные name{},email{},age{}",name,email,age);
          User user = new User();
          user.setName(name);
          user.setAge(age);
          user.setEmail(email);
          userCRUD.update(user);
          user = userCRUD.readById(id);
          userCRUD.update(user);
     }
     public void deleteById(Long id){
          User user = userCRUD.readById(id);
          if(user == null) throw new RuntimeException("По такому id не существует user");
          userCRUD.deleteById(id);
     }
     public List<User> readAll(){
          return userCRUD.readall();
     }
}
