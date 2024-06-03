package com.fancode.helper;

import com.fancode.pojo.todos.ToDos;
import com.fancode.pojo.users.Users;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;

import static com.fancode.constant.StatusCode.OK;
import static com.fancode.helper.GsonHelper.getGson;

public class ResponseHelper {

    /**
     * Returns a list of objects from the response.
     * @param response The response object.
     * @param clazz    The class of the object.
     * @param <T>      The type of the object.
     * @return         A list of objects.
     */
    private static <T> List<T> getResponse(Response response, Class<T[]> clazz) {
        if (response == null) {
            return null;
        }
        return Arrays.asList(getGson().fromJson(response.getBody().asString(), clazz));
    }

    /**
     * Returns a list of ToDos from the response.
     * @param response The response object.
     * @return         A list of ToDos.
     */
    public static List<ToDos> getToDosResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), OK.getStatusCode(), "Failed to fetch users");
        return getResponse(response, ToDos[].class);
    }

    /**
     * Returns a list of users from the response.
     * @param response The response object.
     * @return         A list of users.
     */
    public static List<Users> getUsersResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), OK.getStatusCode(), "Failed to fetch ToDo List");
        return getResponse(response, Users[].class);
    }
}
