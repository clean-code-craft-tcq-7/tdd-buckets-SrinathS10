package BucketSample;

import java.util.*;

public class BucketSampleApplication {

    /*
    Below method assumes single number as range string if startReading and endReading is same
     */
    private static String getBucketRange(int startReading, int endReading) {
        String range;
        if(startReading == endReading) {
            range = String.valueOf(startReading);
        }
        else {
            range = startReading + "-" + endReading;
        }
        return range;
    }

    private static boolean isAdjacentNumbersValid(int reading1, int reading2) {
        return reading1 == reading2 || reading1 == reading2-1 || reading1 == reading2+1;
    }

    /*
    Method to produce result in Map<String, Integer> format for given input integer array readings
     */
    public static Map<String, Integer> getReadingsRange(int[] readings) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        if(readings.length >= 1) {
            Arrays.sort(readings);
            int startValue=readings[0], currValue=readings[0], readingCount=1;
            for(int i=1; i<readings.length; i++) {
                if(isAdjacentNumbersValid(currValue,readings[i])) {
                    currValue = readings[i];
                    readingCount += 1;
                }
                else {
                    resultMap.put(getBucketRange(startValue,currValue), readingCount);
                    startValue = readings[i];
                    currValue = readings[i];
                    readingCount = 1;
                }
            }
            resultMap.put(getBucketRange(startValue,currValue), readingCount);
        }
        return resultMap;
    }

    /*
        Method to produce result in below CSV format(String) for given input integer array readings.
        Range, Readings\n
     */
    public static String getReadingsRangeCSV(int[] readings) {
        Map<String, Integer> resultMap = getReadingsRange(readings);
        String resultString;
        if(resultMap.isEmpty()) {
            resultString = "";
        }
        else {
            resultString = "Range, Readings\n";
            for(Map.Entry<String, Integer> entry: resultMap.entrySet()){
                resultString += (entry.getKey() + ", " + entry.getValue() + "\n");
            }
        }
        return resultString;
    }
}
