package ChargeRange;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
    @DisplayName("Testing input with multiple ranges")
    @MethodSource("getTestReadingsData")
    public void testReadingsRangeFromInputData(int[] inputReadings, String expectedOutput) {
        assertTrue(expectedOutput.equals(ChargeRangeApplication.getReadingsRangeInCSVFormat(inputReadings)));
    }

    static Stream<Arguments> getTestReadingsData() {
        return Stream.of(
                Arguments.of(new int[]{2,3,6,7,10,9},
                        "Range, Readings\n2-3, 2\n6-7, 2\n9-10, 2\n"),
                Arguments.of(new int[]{3,5,4,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24},
                        "Range, Readings\n3-5, 3\n8-24, 17\n"),
                Arguments.of(new int[]{20,8,1,2,9,21,4,5,6,20,22,21,22,9},
                        "Range, Readings\n1-2, 2\n4-6, 3\n8-9, 3\n20-22, 6\n"),
                Arguments.of(new int[]{4,6,6,6,4,4,4,18,10,10,12,10,12,10,10},
                        "Range, Readings\n4, 4\n6, 3\n10, 5\n12, 2\n18, 1\n")
        );
    }
}
