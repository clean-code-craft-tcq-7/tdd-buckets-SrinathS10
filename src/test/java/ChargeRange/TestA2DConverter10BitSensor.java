package ChargeRange;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestA2DConverter10BitSensor {
    static A2DConverter a2DConverter;

    @BeforeAll
    public static void setup() {
        a2DConverter = new A2DConverterImplementation(10, 15, true);
    }

    @Test
    @DisplayName("Test for A2D 10-bit value conversion")
    public void testValueConversion() {
        assertSame(a2DConverter.convertAnalogValue(0),15);
        assertSame(a2DConverter.convertAnalogValue(511),0);
        assertSame(a2DConverter.convertAnalogValue(1022),15);
        assertSame(a2DConverter.convertAnalogValue(700),6);
        assertSame(a2DConverter.convertAnalogValue(50),14);

        int[] readings = new int[] {0,400,800,1000,2000};
        int[] expectedOutput = new int[]{15,3,8,14};
        int[] result = a2DConverter.convertAnalogReadings(readings);
        assertSame(result.length, 4);
        assertTrue(Arrays.equals(result, expectedOutput));
    }

    @ParameterizedTest
    @DisplayName("Integration test for 10 bit readings")
    @CsvFileSource(resources = "/test_10_bit_readings.csv", delimiterString = ";",
            numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = true)
    public void testFor12BitReadings(String inputString, String expectedValues) {
        TestCSVFileValues.testCSVFileReadings(inputString, expectedValues, a2DConverter);
    }
}
