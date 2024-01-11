package Task40;

import Task30.UserApiRequests.PostUser;
import Task40.UserFilterApiRequests.GetFilteredUsers;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetFilteredUsersTest {

    private final GetFilteredUsers filteredUsers = new GetFilteredUsers();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void filterUsersOlderThanTest() {

        User user = new User("20", "test1", "FEMALE");
        postUser.postUserResponse(user);
        String responseBody = filteredUsers.getUsersFilteredRequest("olderThan", "19");

        assertTrue(responseBody.contains(user.getAge()));
    }

    @SneakyThrows
    @Test
    void filterUsersYoungerThanTest() {

        User user = new User("10", "test1", "FEMALE");
        postUser.postUserResponse(user);
        String responseBody = filteredUsers.getUsersFilteredRequest("youngerThan", "11");

        assertTrue(responseBody.contains(user.getAge()));
    }

    @SneakyThrows
    @Test
    void filterUsersByFemaleSexTest() {

        User user = new User("10", "test1", "FEMALE");
        postUser.postUserResponse(user);
        String responseBody = filteredUsers.getUsersFilteredRequest("sex", "FEMALE");

        assertTrue(responseBody.contains(user.getSex()));
    }

    @SneakyThrows
    @Test
    void filterUsersByMaleSexTest() {

        User user = new User("10", "test1", "MALE");
        postUser.postUserResponse(user);
        String responseBody = filteredUsers.getUsersFilteredRequest("sex", "MALE");

        assertTrue(responseBody.contains(user.getSex()));
    }
}
