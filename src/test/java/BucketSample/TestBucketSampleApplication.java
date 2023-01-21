package BucketSample;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestBucketSampleApplication {
    @Before
    public void testObjectCreationEnabled() {
        BucketSampleApplication bucketSampleApplication = new BucketSampleApplication();
    }

    @Test
    public void testReadingsRangeNormal() {
        Map<String, Integer> result;
        result = BucketSampleApplication.getReadingsRange(new int[]{1,2,3,4});
        assertSame(result.size(), 1);
        assertTrue(result.containsKey("1-4"));
        assertTrue(result.containsValue(4));

        //unsorted array
        result = BucketSampleApplication.getReadingsRange(new int[]{15,14,12,10,13,9,8,7,1,2,11,4,3,6,5});
        assertSame(result.size(), 1);
        assertTrue(result.containsKey("1-15"));
        assertTrue(result.containsValue(15));

        //multiple buckets
        result = BucketSampleApplication.getReadingsRange(new int[]{20,8,1,2,9,21,4,5,6,20,22,21,22,9});
        assertSame(result.size(), 4);
        assertTrue(result.keySet().containsAll(Arrays.asList("1-2","20-22","8-9","4-6")));
        assertSame(result.values().stream().mapToInt(Integer::intValue).sum(), 14);
    }

    @Test
    public void testReadingsRangeCSV() {
        String result = BucketSampleApplication.getReadingsRangeCSV(new int[]{4,5,6});
        assertNotNull(result);
        assertTrue(result.startsWith("Range, Readings"));
        assertTrue(result.endsWith("\n"));
        assertTrue(result.contains("4-6, 3"));

        //multiple buckets
        result = BucketSampleApplication.getReadingsRangeCSV(new int[]{20,8,1,2,9,21,4,5,6,20,22,21,22,9});
        assertTrue(result.contains("1-2, 2\n4-6, 3\n8-9, 3\n20-22, 6\n"));
    }

    @Test
    public void testReadingsForEmptyArray() {
        Map<String, Integer> resultMap = BucketSampleApplication.getReadingsRange(new int[]{});
        assertTrue(resultMap.isEmpty());
        String resultStr = BucketSampleApplication.getReadingsRangeCSV(new int[]{});
        assertTrue(resultStr.isEmpty());
    }

    @Test
    public void testForSingleRangeValue() {
        String resultStr = BucketSampleApplication.getReadingsRangeCSV(new int[]{5});
        assertTrue(resultStr.contains("5, 1\n"));

        Map<String, Integer> resultMap;
        resultMap = BucketSampleApplication.getReadingsRange(new int[]{4,4,4,4});
        assertSame(resultMap.size(), 1);
        assertTrue(resultMap.containsKey("4"));
        assertTrue(resultMap.containsValue(4));

        //Single value range at start
        resultMap = BucketSampleApplication.getReadingsRange(new int[]{3,8,9,6,7,8,8});
        assertTrue(resultMap.keySet().containsAll(Arrays.asList("3","6-9")));
        assertTrue(resultMap.values().contains(1));
        assertTrue(resultMap.values().contains(6));

        //Single value range at end
        resultStr = BucketSampleApplication.getReadingsRangeCSV(new int[]{10,14,12,11,13,25,25});
        assertTrue(resultStr.contains("25, 2\n"));
        assertTrue(resultStr.contains("10-14, 5\n"));
    }
}
