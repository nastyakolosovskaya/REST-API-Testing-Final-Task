package storageApp.Data;

import lombok.Getter;

@Getter
public class User {

    private final String age;
    private final String name;
    private final String sex;
    private final String zipCode;

    public User(String age, String name, String sex, String zipCode) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.zipCode = zipCode;
    }

}
