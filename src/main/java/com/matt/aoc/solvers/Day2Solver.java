package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day2Solver implements Solver {
    final String INCREASING = "increasing";
    final String DECREASING = "decreasing";

    boolean isPart2;

    public Day2Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        try {
            String line = file.readLine();
            while (line != null) {
                String[] splitLine = line.split(" ");
                List<Integer> lineList = Arrays.stream(splitLine).map(Integer::parseInt).toList();
                if (isLineSafe(lineList, isPart2)) {
                    answer++;
                }
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        return String.valueOf(answer);
    }

    public boolean isLineSafe(List<Integer> line, boolean allowError) {
        String direction = null;
        Integer previousValue = null;

        for (int i = 0; i < line.size(); i++) {
            Integer currentValue = line.get(i);
            if (previousValue != null) {
                //Size difference check
                if (currentValue.equals(previousValue) || Math.abs(currentValue - previousValue) > 3) {
                    if (allowError) {
                        //Give it another try, removing elements 1 by 1
                        for (int j = 0; j < line.size(); j++) {
                            List<Integer> lineWithOneRemoved = new LinkedList<>(line);
                            lineWithOneRemoved.remove(j);
                            if(isLineSafe(lineWithOneRemoved, false)) {
                                return true;
                            }
                        }
                        //No luck, shmuck
                        return false;
                    } else {
                        return false;
                    }
                }

                //Set direction if not set
                if (direction == null) {
                    direction = previousValue > currentValue ? INCREASING : DECREASING;
                } else {
                    //Check direction
                    if ((direction.equals(INCREASING) && (currentValue > previousValue)) ||
                            (direction.equals(DECREASING) && (currentValue < previousValue))) {
                        if (allowError) {
                            //Give it another try, removing elements 1 by 1
                            for (int j = 0; j < line.size(); j++) {
                                List<Integer> lineWithOneRemoved = new LinkedList<>(line);
                                lineWithOneRemoved.remove(j);
                                if (isLineSafe(lineWithOneRemoved, false)) {
                                    return true;
                                }
                            }
                            //No luck, too bad
                            return false;
                        } else {
                            return false;
                        }
                    }
                }
            }
            previousValue = currentValue;
        }

        return true;
    }

}
