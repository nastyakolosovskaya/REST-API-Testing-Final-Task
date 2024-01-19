package storageApp.Data;

import lombok.Getter;

@Getter
public class User {

    private String age;
    private String name;
    private String sex;
    private String zipCode;

    public User(String age, String name, String sex, String zipCode) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.zipCode = zipCode;
    }

}
