package Helpers;

import org.json.JSONObject;
import storageApp.Data.User;

public class UpdateUsersJsonUtility {

    private UpdateUsersJsonUtility() {
    }

    public static JSONObject getJsonObject(User user, User updatedUser) {
        JSONObject newUser = new JSONObject();
        newUser.put("age", updatedUser.getAge());
        newUser.put("name", updatedUser.getName());
        newUser.put("sex", updatedUser.getSex());
        newUser.put("zipCode", updatedUser.getZipCode());

        JSONObject userToChange = new JSONObject();
        userToChange.put("age", user.getAge());
        userToChange.put("name", user.getName());
        userToChange.put("sex", user.getSex());
        userToChange.put("zipCode", user.getZipCode());

        JSONObject jsonUsers = new JSONObject();
        jsonUsers.put("userToChange", userToChange);
        jsonUsers.put("userNewValues", newUser);
        return jsonUsers;
    }
}
