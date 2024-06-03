package com.fancode.helper;

import com.fancode.constant.ToDoStatus;
import com.fancode.pojo.todos.ToDos;
import com.fancode.pojo.users.Users;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FanCodeHelper {

    public static double calculateStatusPercentage(int complete, int incomplete, ToDoStatus status){
        int total = complete + incomplete;
        if (total == 0) return 0;

        switch (status){
            case COMPLETE:
                return  (double)complete / total * 100;
            case INCOMPLETE:
                return (double)incomplete / total * 100;
            default:
                return 0;
        }
    }

    /**
     * Creates a predicate for filtering based on a threshold percentage.
     * @param min The minimum percentage value to be used for filtering.
     * @param max The maximum percentage value to be used for filtering.
     * @return    A predicate that filters values greater than the specified threshold.
     */
    public static Predicate<Double> createPercentageRnagePredicate(double min, double max) {
        return percentage -> percentage > min && percentage <= max;
    }

    /**
     * Creates a predicate for filtering based on location coordinates.
     * @param minValue The minimum value of the coordinates.
     * @param maxValue The maximum value of the coordinates.
     * @return         A predicate for filtering based on the specified coordinate range.
     */
    public static Predicate<Double> createLocationRangeFilter(double minValue, double maxValue) {
        return coordinates -> coordinates > minValue && coordinates < maxValue;
    }

    /**
     * Filters users based on their location.
     * @param users      A list of users.
     * @param latFilter  A predicate to filter users by their latitude coordinates.
     * @param longFilter A predicate to filter users by their longitude coordinates.
     * @return           A list of users who fall within the specified latitude and longitude coordinates.
     */
    public static List<Users> filterUsersByLocation(List<Users> users, Predicate<Double> latFilter, Predicate<Double> longFilter) {
        return users.stream()
                .filter(user -> latFilter.test(Double.parseDouble(user.getAddress().getGeo().getLat())))
                .filter(user -> longFilter.test(Double.parseDouble(user.getAddress().getGeo().getLng())))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of user IDs from the provided list of users.
     * @param users A list of user objects.
     * @return      A list of user IDs.
     */
    public static Set<Integer> getUserIds(List<Users> users) {
        return users.stream().map(user -> user.getId()).collect(Collectors.toSet());
    }

    /**
     * Calculates the number of completed and incompleted todos for each user.
     * @param userToDo A list of users todo items.
     * @param userIds  A list of city users.
     * @return         A map containing the counts of completed and incompleted todos for each user.
     */
    public static Map<Integer, Map<ToDoStatus, Integer>> getCityUserToDoStatusCount(List<ToDos> userToDo, Set<Integer> userIds) {
        return userToDo.stream()
                .filter(user -> userIds.contains(user.getUserId()))
                .collect(Collectors.groupingBy(ToDos::getUserId,
                        Collectors.groupingBy(todo ->
                                todo.getCompleted() ? ToDoStatus.COMPLETE : ToDoStatus.INCOMPLETE, Collectors.summingInt(todo -> 1))));
    }

    /**
     * Calculates the percentage of completed or incompleted todos for City user.
     * @param userToDosMap A map where the key is the user ID and the value is the count of their todos,
     *                     separated into completed and incompleted statuses.
     * @param status       The todo status (completed or incompleted) for which the percentage should be calculated.
     * @return             The percentage of todos matching the specified status for each user.
     */
    public static Map<Integer, Double> getCityUserToDoStatusPercentage(Map<Integer, Map<ToDoStatus, Integer>> userToDosMap, ToDoStatus status) {
        Map<Integer, Double> userTodoStatusPercentage = new HashMap<>();
        userToDosMap.forEach((userId, todoStatus) -> {
            Integer completeCount = todoStatus.getOrDefault(ToDoStatus.COMPLETE, 0);
            Integer incompleteCount = todoStatus.getOrDefault(ToDoStatus.INCOMPLETE, 0);
            double calculatedPercentage = calculateStatusPercentage(completeCount, incompleteCount, status);
            userTodoStatusPercentage.put(userId, calculatedPercentage);
        });
        return userTodoStatusPercentage;
    }

    /**
     * Filters users based on todo status percentage.
     * @param userStatusPercentages A map of user IDs to their todo status percentages.
     * @param percentagePredicate   A predicate to filter users by their todo status percentage.
     * @return                      A set of user IDs who meet the location and percentage criteria.
     */
    public static Set<Integer> filterUsersByToDoStatusPercentage(Map<Integer, Double> userStatusPercentages,
                                                                 Predicate<Double> percentagePredicate) {
        return userStatusPercentages.entrySet().stream()
                .filter(entry -> percentagePredicate.test(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
