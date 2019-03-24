package com.msays2000.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * KMP Algorithm for Pattern Searching
 * Given a text text[0..n-1] and a pattern pattern[0..m-1], write a function search(char text[], char pattern[]) that
 * returns
 * all occurrences of pattern[] in text[]. You may assume that n > m.
 * Return value is array of index where the pattern can be found.
 */
public class KnuthMorrisPratt {

    public KnuthMorrisPratt() {
    }

    public Optional<List<Integer>> search(String text, String pattern) {
        if (pattern == null || pattern.length() == 0) {
            Optional.empty();
        }
        if (text == null || text.length() == 0) {
            Optional.empty();
        }
        if (pattern.length() > text.length()) {
            Optional.empty();
        }
        List<Integer> patternFoundAt = new ArrayList<>();

        // Preprocess the pattern to build a skip ahead table.
        List<Integer> pi = buildPi(pattern);
        System.out.printf(
                "Searching text: %s, \npattern: %s\npattern: %s\n",
                text,
                Arrays.toString(pattern.toCharArray()),
                pi);
        // abcdef => t
        // abcdeg => p
        int t = 0;
        int p = 0;
        while (t < text.length()) {
            while (p < pattern.length() && t < text.length()) {
                System.out.printf("Matching t[%s]=%s, p[%s]=%s\n", t, text.charAt(t), p, pattern.charAt(p));
                if (text.charAt(t) == pattern.charAt(p)) {
                    t++;
                    p++;
                    if (p == pattern.length()) {
                        patternFoundAt.add(t - pattern.length());
                        p = 0;
                    }
                } else {
                    p = pi.get(p);
                    if (p == 0) {
                        t++;
                    }
                }
            }
        }
        if (patternFoundAt.size() > 0) {
            return Optional.of(patternFoundAt);
        }
        return Optional.empty();
    }

    /**
     * This builds the skip ahead table so that when we have a mismatch at any particular index in pattern,
     * instead of resetting the pattern index back to 0, we skip ahead it to the Pi[index] value so that
     * we can avoid unnecessary comparision.
     * Examples:
     * Pattern[a,b,c,d,e] = Pi[0,0,0,0,0]
     * Pattern[a,b,c,d,a] = Pi[0,0,0,0,1]
     * Pattern[a,b,c,a,b] = Pi[0,0,0,1,2]
     * Pattern[a,b,c,d,a,b,c,d,e] = Pi[0,0,0,0,1,2,3,4,0]
     * Pattern[a,b,c,d,a,b,c,d,e,a,b,c,d,a,b,c,d,e] = Pi[0,0,0,0,1,2,3,4,0,1,2,3,4,5,6,7,8,9]
     *
     * @param pattern
     * @return the preprocess skip ahead table.
     */
    List<Integer> buildPi(String pattern) {
        List<Integer> pi = new ArrayList<>(Collections.nCopies(pattern.length(), 0));
        for (int i = 1; i < pattern.length(); i++) {
            for (int j = 0; j < pattern.length() && i < pattern.length(); j++) {
                if (pattern.charAt(i) == pattern.charAt(j)) {
                    pi.set(i, j);
                    i++;
                } else {
                    break;
                }
            }
        }
        return pi;
    }
}
