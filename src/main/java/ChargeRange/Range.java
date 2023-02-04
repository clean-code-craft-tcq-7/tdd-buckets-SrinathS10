package ChargeRange;

import java.util.List;

public class Range {
    public int minValue;
    public int maxValue;
    public int readingCount;
    public List<Integer> readingsList;

    public Range(int minValue, int maxValue, int readingCount, List<Integer> readingsList) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.readingCount = readingCount;
        this.readingsList = readingsList;
    }

    /*
    Below method assumes single number as range string if startReading and endReading is same
    */
    public String getCSVFormatRangeString() {
        String rangeStr;
        if(minValue == maxValue) {
            rangeStr = String.valueOf(minValue);
        }
        else {
            rangeStr = minValue + "-" + maxValue;
        }
        return rangeStr + ", " + readingCount + "\n";
    }
}
