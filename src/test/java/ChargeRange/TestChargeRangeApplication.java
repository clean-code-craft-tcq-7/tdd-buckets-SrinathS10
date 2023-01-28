package ChargeRange;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestChargeRangeApplication {

    @Test
    @DisplayName("Testing for values present in one range")
    public void testReadingsRangeBasic() {
        List<Range> result;
        result = ChargeRangeApplication.getReadingsRange(new int[]{1,2,3});
        assertSame(result.size(), 1);
        assertSame(result.get(0).minValue,1);
        assertSame(result.get(0).maxValue,3);
        assertSame(result.get(0).readingCount, 3);

        //unsorted array
        result = ChargeRangeApplication.getReadingsRange(new int[]{5,3,6,4});
        assertSame(result.size(), 1);
        assertSame(result.get(0).minValue,3);
        assertSame(result.get(0).maxValue,6);
        assertSame(result.get(0).readingCount, 4);
    }

    @Test
    @DisplayName("Testing CSV format of Readings Range")
    public void testCSVFormatOfReadingsRange() {
        String result = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{1,2,3});
        assertNotNull(result);
        assertTrue(result.startsWith("Range, Readings\n"));
        assertTrue(result.endsWith("\n"));
        assertTrue(result.contains("1-3, 3"));
    }

    @Test
    @DisplayName("Testing with empty array input")
    public void testReadingsForEmptyArray() {
        List<Range> resultList = ChargeRangeApplication.getReadingsRange(new int[]{});
        assertTrue(resultList.isEmpty());
        String resultStr = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{});
        assertTrue(resultStr.isEmpty());
    }

    @Test
    @DisplayName("Testing Range with same minimum and maximum value")
    public void testForSingleRangeValue() {
        String result = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{5});
        assertTrue(result.contains("5, 1\n"));

        result = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{4,4,4,4,4});
        assertTrue(result.contains("4, 5\n"));

        //Single value range at start
        result = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{3,8,9,6,7,8,8});
        assertTrue(result.contains("3, 1\n"));
        assertTrue(result.contains("6-9, 6\n"));

        //Single value range at end
        result = ChargeRangeApplication.getReadingsRangeInCSVFormat(new int[]{10,14,12,11,13,25,25});
        assertTrue(result.contains("10-14, 5\n"));
        assertTrue(result.endsWith("25, 2\n"));
    }

    @ParameterizedTest
    @DisplayName("Testing for multiple range inputs")
    @CsvFileSource(resources = "/test_readings.csv", delimiterString = ";",
            numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = true)
    public void testReadingsRangeFromInputData(String inputString, String expectedString) {
        int[] inputReadings = Arrays.stream(inputString
                        .replace("[", "").replace("]", "").split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        Stream<String> expectedOutputStream = Arrays.stream(expectedString
                        .replace("[", "").replace("]", "")
                        .split("&"))
                .map(String::trim)
                .map(str -> str.replaceAll("\"",""));

        String actualOutput = ChargeRangeApplication.getReadingsRangeInCSVFormat(inputReadings);
        assertTrue(expectedOutputStream.allMatch(actualOutput::contains));
    }
}
