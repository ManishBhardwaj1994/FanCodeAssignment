package com.fancode.pojo.testcase;

import com.fancode.constant.ToDoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.function.Predicate;

@Data
@Builder
@AllArgsConstructor
public class TestCase {
    private String testId;
    private String testCaseDescription;
    private Predicate<Double> latitudeFilter;
    private Predicate<Double> longitudeFilter;
    private Predicate<Double> percentageFilter;
    private ToDoStatus status;
}
