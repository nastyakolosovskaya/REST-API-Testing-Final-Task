package storageApp.Data;

public class User {

    public User(String age, String name, String sex, String zipCode) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.zipCode = zipCode;
    }

    public User(String age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getZipCode() {
        return zipCode;
    }

    private String age;
    private String name;

    private String sex;
    private String zipCode;
}
