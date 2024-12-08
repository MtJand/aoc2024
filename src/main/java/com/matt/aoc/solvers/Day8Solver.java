package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.*;

public class Day8Solver implements Solver {
    boolean isPart2;

    public Day8Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;
        Map<Character, Set<String>> locations = new HashMap<>();
        int columnCount = 0;
        int rowCount = 0;

        try {
            String line = file.readLine();
            int lineNumber = 0;
            while (line != null) {
                char[] charArray = line.toCharArray();

                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    if (c != '.') {
                        String location = String.format("{%d, %d}", i, lineNumber);
                        Set<String> existingLocations = locations.getOrDefault(c, new HashSet<>());
                        existingLocations.add(location);
                        locations.put(c, existingLocations);
                    }
                }

                if(charArray.length > columnCount) {
                    columnCount = charArray.length;
                }
                line = file.readLine();
                lineNumber++;
            }
            rowCount = lineNumber;
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        int maxX = columnCount-1;
        int maxY = rowCount-1;
        Set<String> antinodes = new HashSet<>();

        locations.forEach((key, value) -> {
            value.forEach(coordinate -> {
                value.forEach(value1 -> {
                    if(!coordinate.equals(value1)) {
                        if(isPart2) {
                            antinodes.add(coordinate);
                            antinodes.add(value1);
                        }
                        int x1, x2, y1, y2;
                        String[] splitCoordinate = coordinate.replace("{", "").replace("}", "").split(",");
                        x1 = Integer.parseInt(splitCoordinate[0].trim());
                        y1 = Integer.parseInt(splitCoordinate[1].trim());

                        String[] splitValue1 = value1.replace("{", "").replace("}", "").split(",");
                        x2 = Integer.parseInt(splitValue1[0].trim());
                        y2 = Integer.parseInt(splitValue1[1].trim());

                        int xDiff = x1 - x2;
                        int yDiff = y1 - y2;

                        int upperAntinodeX = x1 + xDiff;
                        int upperAntinodeY = y1 + yDiff;
                        String upperAntinode = String.format("{%d, %d}", upperAntinodeX, upperAntinodeY);
                        if(upperAntinodeX <= maxX && upperAntinodeY <= maxY && upperAntinodeX >= 0 && upperAntinodeY >= 0) {
                            antinodes.add(upperAntinode);
                        }

                        int lowerAntinodeX = x2 - xDiff;
                        int lowerAntinodeY = y2 - yDiff;
                        String lowerAntinode = String.format("{%d, %d}", lowerAntinodeX, lowerAntinodeY);
                        if(lowerAntinodeX <= maxX && lowerAntinodeY <= maxY && lowerAntinodeX >= 0 && lowerAntinodeY >= 0) {
                            antinodes.add(lowerAntinode);
                        }

                        if(isPart2) {
                            while (upperAntinodeX <= maxX && upperAntinodeY <= maxY && upperAntinodeX >= 0 && upperAntinodeY >= 0) {
                                upperAntinodeX = upperAntinodeX + xDiff;
                                upperAntinodeY = upperAntinodeY + yDiff;
                                upperAntinode = String.format("{%d, %d}", upperAntinodeX, upperAntinodeY);
                                if(upperAntinodeX <= maxX && upperAntinodeY <= maxY && upperAntinodeX >= 0 && upperAntinodeY >= 0) {
                                    antinodes.add(upperAntinode);
                                }
                            }

                            while (lowerAntinodeX <= maxX && lowerAntinodeY <= maxY && lowerAntinodeX >= 0 && lowerAntinodeY >= 0) {
                                lowerAntinodeX = lowerAntinodeX - xDiff;
                                lowerAntinodeY = lowerAntinodeY - yDiff;
                                lowerAntinode = String.format("{%d, %d}", lowerAntinodeX, lowerAntinodeY);
                                if(lowerAntinodeX <= maxX && lowerAntinodeY <= maxY && lowerAntinodeX >= 0 && lowerAntinodeY >= 0) {
                                    antinodes.add(lowerAntinode);
                                }
                            }
                        }
                    }
                });
            });
        });

        answer = antinodes.size();
        return String.valueOf(answer);
    }
}
