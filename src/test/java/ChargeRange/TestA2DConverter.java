package ChargeRange;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestA2DConverter {

    static A2DConverter a2DConverter12bit;
    static A2DConverter a2DConverter10bit;

    @BeforeAll
    static void setup() {
        a2DConverter12bit = new A2DConverterImplementation(12, 10);
        a2DConverter10bit = new A2DConverterImplementation(10, 15, true);
    }

    @Test
    @DisplayName("Test for removing error readings")
    public void testRemoveErrorReadings() {
        int[] readings = new int[] {0,1,1024,4096,2048,4094,6000};
        int[] expectedOutput = new int[]{0,1,1024,2048,4094};
        int[] result = a2DConverter12bit.removeErrorReadings(readings);
        assertSame(result.length, 5);
        assertArrayEquals(result, expectedOutput);
    }

    static Stream<Arguments> getTestConfigurationValuesForConversion() {
        return Stream.of(
                // 12-Bit sensor
                Arguments.of(
                        a2DConverter12bit,
                        new int[] {0,1,1024,4096,2048,4094,6000},
                        new int[]{0,0,3,5,10}),
                // 10-Bit sensor
                Arguments.of(
                        a2DConverter10bit,
                        new int[] {0,400,800,1000,2000},
                        new int[]{15,3,8,14})
        );
    }

    @ParameterizedTest
    @DisplayName("Test for A2D value conversion")
    @MethodSource("getTestConfigurationValuesForConversion")
    void testValueConversion(A2DConverter a2DConverter, int[] inputReadings, int[] expectedValues) {
        int[] result = a2DConverter.convertAnalogReadings(inputReadings);
        assertSame(result.length, expectedValues.length);
        assertArrayEquals(result, expectedValues);
    }

    @ParameterizedTest
    @DisplayName("Integration test for 12 bit readings")
    @CsvFileSource(resources = "/test_12_bit_readings.csv", delimiterString = ";",
            numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = true)
    public void testFor12BitReadings(String inputString, String expectedValues) {
        TestCSVFileValues.testCSVFileReadings(inputString, expectedValues, a2DConverter12bit);
    }

    @ParameterizedTest
    @DisplayName("Integration test for 10 bit readings")
    @CsvFileSource(resources = "/test_10_bit_readings.csv", delimiterString = ";",
            numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = true)
    public void testFor10BitReadings(String inputString, String expectedValues) {
        TestCSVFileValues.testCSVFileReadings(inputString, expectedValues, a2DConverter10bit);
    }
}
