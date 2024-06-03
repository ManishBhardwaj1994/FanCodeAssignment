package com.fancode.testdata;
import com.fancode.constant.ToDoStatus;
import com.fancode.pojo.testcase.TestCase;
import org.testng.annotations.DataProvider;
import static com.fancode.helper.FanCodeHelper.*;

public class FanCodeTestData {

    @DataProvider
    public Object[][] fanCodeTest() {
        return new Object[][] {
                {
                    TestCase.builder()
                        .testCaseDescription("Get users with more than 50% completed ToDos from FanCode city")
                        .latitudeFilter(createLocationRangeFilter(-40, 5))
                        .longitudeFilter(createLocationRangeFilter(5, 100))
                        .percentageFilter(createPercentageRnagePredicate(50, 100))
                        .status(ToDoStatus.COMPLETE).build()
                }
        };
    }
}
