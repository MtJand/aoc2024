package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.*;

public class Day9Solver implements Solver {
    boolean isPart2;

    public Day9Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        BigInteger answer = new BigInteger(String.valueOf(0));

        try {
            String line = file.readLine();
            List<String> expandedLine = expandLine(line);

            if (!isPart2) {
                for (int i = expandedLine.size() - 1; i >= 0; i--) {
                    if (!expandedLine.get(i).equals(".")) {
                        int firstDotIndex = expandedLine.indexOf(".");
                        if (firstDotIndex < i) {
                            Collections.swap(expandedLine, i, firstDotIndex);
                        } else {
                            break;
                        }
                    }
                }
            } else {
                Set<String> checkedValues = new HashSet<>();
                for (int i = expandedLine.size() - 1; i >= 0; i--) {
                    String value = expandedLine.get(i);
                    //Found a new file, how big is it?
                    if (!value.equals(".") && !checkedValues.contains(value)) {
                        int count = Collections.frequency(expandedLine, expandedLine.get(i));
                        int bottomIndex = i - (count - 1);

                        //Find enough empty space to fit it in
                        int dotStart = -1;
                        int dotEnd;
                        for (int j = 0; j < bottomIndex + 1; j++) {
                            if (expandedLine.get(j).equals(".")) {
                                if (dotStart == -1) {
                                    dotStart = j;
                                }
                            } else {
                                if (dotStart != -1) {
                                    dotEnd = j - 1;
                                    int dotCount = (dotEnd - dotStart) + 1;
                                    if (dotCount >= count) {
                                        //Swap it over
                                        for (int k = 0; k < count; k++) {
                                            Collections.swap(expandedLine, bottomIndex + k, dotStart + k);
                                        }
                                        break;
                                    } else {
                                        //Not enough room, try again
                                        dotStart = -1;
                                    }
                                }
                            }
                        }

                        i = bottomIndex;
                        checkedValues.add(value);
                    }
                }
            }

            //Add em up
            for (int i = 0; i < expandedLine.size(); i++) {
                String s = expandedLine.get(i);
                if (!s.equals(".")) {
                    long value = Long.parseLong(s);
                    BigInteger cellValue = BigInteger.valueOf(value * (long) i);
                    answer = answer.add(cellValue);
                } else if (!isPart2) {
                    break;
                }
            }

        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        return String.valueOf(answer);
    }

    private static List<String> expandLine(String line) {
        char[] splitLine = line.toCharArray();
        List<String> expandedLine = new ArrayList<>();

        int fileIndex = 0;
        for (int i = 0; i < splitLine.length; i++) {
            int value = Integer.parseInt(String.valueOf(splitLine[i]));

            for (int j = 0; j < value; j++) {
                if (i % 2 == 0) {
                    expandedLine.add(String.valueOf(fileIndex));
                } else {
                    expandedLine.add(".");
                }
            }

            if (i % 2 == 0) {
                fileIndex++;
            }
        }
        return expandedLine;
    }
}
