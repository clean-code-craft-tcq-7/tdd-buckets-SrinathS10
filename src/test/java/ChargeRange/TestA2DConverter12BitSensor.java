package ChargeRange;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestA2DConverter12BitSensor {

    static A2DConverter a2DConverter;

    @BeforeAll
    public static void setup() {
        a2DConverter = new A2DConverterImplementation(12, 10);
    }

    @Test
    @DisplayName("Test for removing error readings")
    public void testRemoveErrorReadings() {
        int[] readings = new int[] {0,1,1024,4096,2048,4094,6000};
        int[] expectedOutput = new int[]{0,1,1024,2048,4094};
        int[] result = a2DConverter.removeErrorReadings(readings);
        assertSame(result.length, 5);
        assertTrue(Arrays.equals(result, expectedOutput));
    }

    @Test
    @DisplayName("Test for A2D value conversion")
    public void testValueConversion() {
        assertSame(a2DConverter.convertAnalogValue(1146),3);
        assertSame(a2DConverter.convertAnalogValue(0),0);
        assertSame(a2DConverter.convertAnalogValue(4094),10);
        assertSame(a2DConverter.convertAnalogValue(3500),9);

        int[] readings = new int[] {0,1,1024,4096,2048,4094,6000};
        int[] expectedOutput = new int[]{0,0,3,5,10};
        int[] result = a2DConverter.convertAnalogReadings(readings);
        assertSame(result.length, 5);
        assertTrue(Arrays.equals(result, expectedOutput));
    }

    @ParameterizedTest
    @DisplayName("Integration test for 12 bit readings")
    @CsvFileSource(resources = "/test_12_bit_readings.csv", delimiterString = ";",
            numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = true)
    public void testFor12BitReadings(String inputString, String expectedValues) {
        TestCSVFileValues.testCSVFileReadings(inputString, expectedValues, a2DConverter);
    }
}
