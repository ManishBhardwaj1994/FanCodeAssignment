package com.fancode.helper;

import com.fancode.pojo.todos.ToDos;
import com.fancode.pojo.users.Users;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;
import static com.fancode.helper.GsonHelper.getGson;

public class ResponseHelper {

    private static <T> List<T> getResponse(Response response, Class<T[]> clazz) {
        if (response == null) {
            return null;
        }
        return Arrays.asList(getGson().fromJson(response.getBody().asString(), clazz));
    }

    public static List<ToDos> getToDosResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch users");
        return getResponse(response, ToDos[].class);
    }

    public static List<Users> getUsersResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch ToDo List");
        return getResponse(response, Users[].class);
    }
}
