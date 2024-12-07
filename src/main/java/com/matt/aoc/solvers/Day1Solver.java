package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.*;

public class Day1Solver implements Solver {
    boolean isPart2;

    public Day1Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        int answer = 0;

        try {
            String line = file.readLine();
            while(line != null) {
                String[] splitLine = line.split(" {3}");
                leftList.add(Integer.valueOf(splitLine[0]));
                rightList.add(Integer.valueOf(splitLine[1]));
                line = file.readLine();
            }

            leftList.sort(Comparator.naturalOrder());
            rightList.sort(Comparator.naturalOrder());

            assert (leftList.size() == rightList.size());

            if(!isPart2) {
                for (int i = 0; i < leftList.size(); i++) {
                    answer += Math.abs(leftList.get(i) - rightList.get(i));
                }
            } else {
                for (int i = 0; i < leftList.size(); i++) {
                    int number = leftList.get(i);
                    int multiplier = Collections.frequency(rightList, leftList.get(i));
                    answer += number * multiplier;
                }
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        return String.valueOf(answer);
    }
}
