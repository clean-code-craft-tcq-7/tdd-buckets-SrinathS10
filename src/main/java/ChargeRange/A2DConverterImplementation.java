package ChargeRange;

import java.util.Arrays;

public class A2DConverterImplementation implements A2DConverter{
    int maxBits;
    int maxTemperature;
    private int maxReadingValue;
    boolean dischargeFlag;

    public A2DConverterImplementation(int maxBits, int maxTemperature) {
        this.maxBits = maxBits;
        this.maxTemperature = maxTemperature;
        this.dischargeFlag = false;
        setMaxReadingValue();
    }

    public A2DConverterImplementation(int maxBits, int maxTemperature, boolean dischargeFlag) {
        this.maxBits = maxBits;
        this.maxTemperature = maxTemperature;
        this.dischargeFlag = dischargeFlag;
        setMaxReadingValue();
    }

    public void setMaxReadingValue() {
        this.maxReadingValue = (int) (Math.pow(2, maxBits) - 2);
    }

    @Override
    public int[] removeErrorReadings(int[] readings) {
        return Arrays.stream(readings).filter(reading -> reading <= maxReadingValue).toArray();
    }

    @Override
    public int convertAnalogValue(int reading) {
        int readingInput = reading;
        if(this.dischargeFlag) {
            readingInput = Math.abs((reading - maxReadingValue/2) * 2);
        }
        return Math.round((float)maxTemperature * readingInput / maxReadingValue);
    }

    @Override
    public int[] convertAnalogReadings(int[] readings) {
        return Arrays.stream(removeErrorReadings(readings)).map(this::convertAnalogValue).toArray();
    }
}
