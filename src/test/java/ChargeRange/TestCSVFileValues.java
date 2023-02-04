package ChargeRange;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Class for testing values from CSV files
public class TestCSVFileValues {

    public static int[] getInputReadings(String inputValues) {
        return Arrays.stream(inputValues
                        .replace("[", "").replace("]", "").split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static Stream<String> getExpectedStringStream(String expectedValues) {
        return Arrays.stream(expectedValues
                        .replace("[", "").replace("]", "")
                        .split("&"))
                .map(String::trim)
                .map(str -> str.replaceAll("\"",""));
    }

    public static void testCSVFileReadings(String inputValues, String expectedValues) {
        String result = ChargeRangeApplication.getReadingsRangeInCSVFormat(getInputReadings(inputValues));
        Stream<String> expectedStringStream = getExpectedStringStream(expectedValues);
        assertTrue(expectedStringStream.allMatch(result::contains));
    }

    public static void testCSVFileReadings(String inputValues, String expectedValues, A2DConverter converter) {
        String result = ChargeRangeApplication.getReadingsRangeInCSVFormat(getInputReadings(inputValues),converter);
        Stream<String> expectedStringStream = getExpectedStringStream(expectedValues);
        assertTrue(expectedStringStream.allMatch(result::contains));
    }
}
