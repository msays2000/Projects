package com.msays2000.strings;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KnuthMorrisPrattTest {
    KnuthMorrisPratt knuthMorrisPratt = new KnuthMorrisPratt();

    @Test
    public void searchTest() {
        Optional<List<Integer>> found = null;
        found = knuthMorrisPratt.search("abcdfabcde", "abcde");
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(Arrays.asList(5), found.get());

        found = knuthMorrisPratt.search("abcdfabcdeabcde", "abcde");
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(Arrays.asList(5, 10), found.get());

        // abcdabcdeabcdabcdaabcdabcdeabcdabcde
        //                   abcdabcdeabcdabcde
        found = knuthMorrisPratt.search("abcdabcdeabcdabcdaabcdabcdeabcdabcde", "abcdabcdeabcdabcde");
        Assert.assertTrue("match not found", found.isPresent());
        Assert.assertEquals(Arrays.asList(18), found.get());
    }

    /**
     * This builds the skip ahead table so that when we have a mismatch at any particular index in pattern,
     * instead of resetting the pattern index back to 0, we skip ahead it to the Pi[index] value so that
     * we can avoid unnecessary comparision.
     * Examples:
     *  Pattern[a,b,c,d,e] = Pi[0,0,0,0,0]
     *  Pattern[a,b,c,d,a] = Pi[0,0,0,0,1]
     *  Pattern[a,b,c,a,b] = Pi[0,0,0,1,2]
     *  Pattern[a,b,c,d,a,b,c,d,e] = Pi[0,0,0,0,1,2,3,4,0]
     *  Pattern[a,b,c,d,a,b,c,d,e,a,b,c,d,a,b,c,d,e] = Pi[0,0,0,0,1,2,3,4,0,1,2,3,4,5,6,7,8,9]
     */
    @Test
    public void buildPiTest() {
        Assert.assertEquals(Arrays.asList(0,0,0,0,0), knuthMorrisPratt.buildPi("abcde"));
        Assert.assertEquals(Arrays.asList(0,0,0,0,1), knuthMorrisPratt.buildPi("abcda"));
        Assert.assertEquals(Arrays.asList(0,0,0,1,2), knuthMorrisPratt.buildPi("abcab"));
        Assert.assertEquals(Arrays.asList(0,0,0,0,1,2,3,4,0), knuthMorrisPratt.buildPi("abcdabcde"));
        Assert.assertEquals(
                Arrays.asList(0,0,0,0,1,2,3,4,0,1,2,3,4,5,6,7,8,9),
                knuthMorrisPratt.buildPi("abcdabcdeabcdabcde"));
    }
}