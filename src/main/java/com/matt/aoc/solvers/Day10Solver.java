package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Day10Solver implements Solver {
    boolean isPart2;

    public Day10Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        int lineCount = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();

        try {
            String line = file.readLine();
            while (line != null) {
                List<Integer> lineList = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    lineList.add(Integer.parseInt(String.valueOf(c)));
                }
                map.put(lineCount, lineList);
                lineCount++;
                line = file.readLine();
            }

            System.out.println(lineCount);
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        for (int y = 0; y < map.size(); y++) {
            List<Integer> row = map.get(y);
            for (int x = 0; x < row.size(); x++) {
                if(row.get(x) == 0) {
                    List<String> foundNines = new ArrayList<>();
                    int trailheadScore = countPathsTo9(map, x, y, foundNines);
                    answer += trailheadScore;
                }
            }
        }


        return String.valueOf(answer);
    }

    private int countPathsTo9(Map<Integer, List<Integer>> map, int x, int y, List<String> foundNines) {
        if(map.get(y).get(x) == 9) {
            return 0;
        }

        int paths = 0;

        //Check Up
        if(isValidStepUp(map, x, y, x, y-1)) {
            if(map.get(y-1).get(x) == 9) {
                if(!isPart2) {
                    String nineCoords = String.format("{%d, %d}", x, y-1);
                    if(!foundNines.contains(nineCoords)) {
                        paths += 1;
                        foundNines.add(nineCoords);
                    }
                } else {
                    paths += 1;
                }
            } else {
                paths += countPathsTo9(map, x, y-1, foundNines);
            }
        }

        //Check right
        if(isValidStepUp(map, x, y, x+1, y)) {
            if(map.get(y).get(x+1) == 9) {
                if(!isPart2) {
                    String nineCoords = String.format("{%d, %d}", x+1, y);
                    if (!foundNines.contains(nineCoords)) {
                        paths += 1;
                        foundNines.add(nineCoords);
                    }
                } else {
                    paths += 1;
                }
            } else {
                paths += countPathsTo9(map, x+1, y, foundNines);
            }
        }

        // Check Down
        if(isValidStepUp(map, x, y, x, y+1)) {
            if(map.get(y+1).get(x) == 9) {
                if(!isPart2) {
                    String nineCoords = String.format("{%d, %d}", x, y + 1);
                    if (!foundNines.contains(nineCoords)) {
                        paths += 1;
                        foundNines.add(nineCoords);
                    }
                }  else {
                    paths += 1;
                }
            } else {
                paths += countPathsTo9(map, x, y+1, foundNines);
            }
        }

        // Check left
        if(isValidStepUp(map, x, y, x-1, y)) {
            if(map.get(y).get(x-1) == 9) {
                if(!isPart2) {
                    String nineCoords = String.format("{%d, %d}", x - 1, y);
                    if (!foundNines.contains(nineCoords)) {
                        paths += 1;
                        foundNines.add(nineCoords);
                    }
                } else {
                    paths += 1;
                }
            } else {
                paths += countPathsTo9(map, x-1, y, foundNines);
            }
        }

        return paths;
    }

    private boolean isValidStepUp(Map<Integer, List<Integer>> map, int x1, int y1, int x2, int y2) {
        if(x2 < 0 || y2 < 0 || y2 >= map.size() || x2 >= map.get(y2).size()) {
            return false;
        }

        Integer currentValue = map.get(y1).get(x1);
        Integer nextValue = map.get(y2).get(x2);

        return nextValue - currentValue == 1;
    }
}
