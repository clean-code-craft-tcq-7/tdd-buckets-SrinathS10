package ChargeRange;

public interface A2DConverter {
    int[] removeErrorReadings(int[] readings);
    int convertAnalogValue(int reading);
    int[] convertAnalogReadings(int[] readings);
}
