package storageApp.Data;

public class UserBuilder {
    private String age;
    private String name;
    private String sex;
    private String zipCode;

    public UserBuilder setAge(String age) {
        this.age = age;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserBuilder setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public User createUser() {
        return new User(age, name, sex, zipCode);
    }
}
