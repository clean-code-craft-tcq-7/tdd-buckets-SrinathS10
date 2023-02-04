package ChargeRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChargeRangeApplication {

    private static boolean isAdjacentNumbersInSameRange(int reading1, int reading2) {
        return reading1 == reading2 || reading1 == reading2-1;
    }

    /*
    Method to produce result in List<Range> format for given input integer array readings
    */
    public static List<Range> getReadingsRange(int[] readings) {
        List<Range> rangeList = new ArrayList<>();
        if(readings.length >= 1) {
            Arrays.sort(readings);
            int startValue=readings[0], currValue=readings[0];
            List<Integer> readingsList = new ArrayList<>();
            readingsList.add(currValue);

            for(int i=1; i<readings.length; i++) {
                if (!isAdjacentNumbersInSameRange(currValue, readings[i])) {
                    rangeList.add(new Range(startValue, currValue, readingsList.size(), readingsList));
                    readingsList.clear();
                    startValue = readings[i];
                }
                currValue = readings[i];
                readingsList.add(currValue);
            }
            rangeList.add(new Range(startValue,currValue,readingsList.size(),readingsList));
            readingsList.clear();
        }
        return rangeList;
    }

    /*
    Method to produce result in below CSV format(String) for given input integer array readings.
    Range, Readings\n
    */
    public static String getReadingsRangeInCSVFormat(int[] readings) {
        List<Range> rangeList = getReadingsRange(readings);
        String rangeListString = "";
        if(!rangeList.isEmpty()) {
            rangeListString = "Range, Readings\n";
            for(Range range : rangeList) {
                rangeListString = rangeListString + range.getCSVFormatRangeString();
            }
        }
        return rangeListString;
    }

    public static String getReadingsRangeInCSVFormat(int[] readings, A2DConverter converter) {
        return getReadingsRangeInCSVFormat(converter.convertAnalogReadings(readings));
    }
}
