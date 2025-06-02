package org.example.spring;

public class UserDTOKafka {
    private String operation;
    private String email;
    private String name;
    private Integer age;
    private Long id;

    public UserDTOKafka(String operation, Integer age, String name, String email) {
    }
    public UserDTOKafka(String operation, long id){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
