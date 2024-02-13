package Task40;

import Task30.UserApiRequests.PostUser;
import Task40.UserFilterApiRequests.GetFilteredUsers;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Data.UserBuilder;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetFilteredUsersTest {

    private final GetFilteredUsers filteredUsers = new GetFilteredUsers();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @Step
    int postUser(User user) {
        int responseBody = postUser.postUser(user);
        return responseBody;
    }

    @Step
    String getFilteredUsers(String parameter, String value) {
        String responseBody = filteredUsers.getUsersFiltered(parameter, value);
        return responseBody;
    }

    @Test
    @Description("Scenario #1: Filter users by older than parameter")
    @Story("Filter User")
    void filterUsersOlderThanTest() {

        User user = new UserBuilder().setAge("20").setName("test40").setSex("FEMALE").createUser();
        postUser(user);
        String responseBody = getFilteredUsers("olderThan", "19");

        assertTrue(responseBody.contains(user.getAge()));
    }

    @Test
    @Description("Scenario #2: Filter users by younger than parameter")
    @Story("Filter User")
    void filterUsersYoungerThanTest() {

        User user = new UserBuilder().setAge("10").setName("test41").setSex("FEMALE").createUser();
        postUser(user);
        String responseBody = getFilteredUsers("youngerThan", "11");

        assertTrue(responseBody.contains(user.getAge()));
    }

    @Test
    @Description("Scenario 3: Filter users by sex (Female) parameter")
    @Story("Filter User")
    void filterUsersByFemaleSexTest() {

        User user = new UserBuilder().setAge("10").setName("test42").setSex("FEMALE").createUser();
        postUser(user);
        String responseBody = getFilteredUsers("sex", "FEMALE");

        assertTrue(responseBody.contains(user.getSex()));
    }

    @Test
    @Description("Scenario 3: Filter users by sex (Male) parameter")
    @Story("Filter User")
    void filterUsersByMaleSexTest() {

        User user = new UserBuilder().setAge("10").setName("test43").setSex("MALE").createUser();
        postUser(user);
        String responseBody = getFilteredUsers("sex", "MALE");

        assertTrue(responseBody.contains(user.getSex()));
    }
}
