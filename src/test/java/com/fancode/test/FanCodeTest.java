package com.fancode.test;

import com.fancode.client.FanCodeClient;
import com.fancode.constant.ToDoStatus;
import com.fancode.helper.FanCodeHelper;
import com.fancode.helper.ResponseHelper;
import com.fancode.pojo.testcase.TestCase;
import com.fancode.pojo.todos.ToDos;
import com.fancode.pojo.users.Users;
import com.fancode.testdata.FanCodeTestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.*;

public class FanCodeTest {

    @Test (dataProvider = "fanCodeTest", dataProviderClass = FanCodeTestData.class)
    public void testCityUsers(TestCase testCase){
        FanCodeClient fanCodeClient = new FanCodeClient();

        // Get the list of users
        Response responseUsers = fanCodeClient.getUsersList();
        List<Users> userList = ResponseHelper.getUsersResponse(responseUsers);

        // Filter the users based on the location
        List<Users> cityUser =  FanCodeHelper.filterUsersByLocation(userList, testCase.getLatitudeFilter(), testCase.getLongitudeFilter());

        // Get the user ids of the users from city
        Set<Integer> userIds = FanCodeHelper.getUserIds(cityUser);

        // Get the list of ToDos
        Response responseTodos = fanCodeClient.getToDosList();
        List<ToDos> todosList = ResponseHelper.getToDosResponse(responseTodos);

        // Get the ToDos status count, both completed and uncompleted, for city user.
        Map<Integer, Map<ToDoStatus, Integer>> cityUserToDoStatusCount = FanCodeHelper.getCityUserToDoStatusCount(todosList, userIds);

        // Calculate percentage of ToDos Status for city user
        Map<Integer, Double> cityUserToDoStatusPercentage =
                FanCodeHelper.getCityUserToDoStatusPercentage(cityUserToDoStatusCount, testCase.getStatus());

        // Get the list of city users whose todo completion percentage is more than 50
        Set<Integer> userWith50PercentageToDosCompletion = FanCodeHelper
                .filterUsersByToDoStatusPercentage(cityUserToDoStatusPercentage, testCase.getPercentageFilter());

        System.out.println("Users from Fan code City and with more than 50% completed ToDos");
        userWith50PercentageToDosCompletion.stream().map(userid -> "UserId: " + userid).forEach(System.out::println);
    }

}
