package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4Solver implements Solver {
    char[] xmas;

    boolean isPart2;

    public Day4Solver(boolean isPart2) {
        this.isPart2 = isPart2;
        xmas = isPart2 ? new char[]{'M', 'A', 'S'} : new char[]{'X', 'M', 'A', 'S'};
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        List<char[]> wordSearch = new ArrayList<>();
        int lineLength = 0;

        try {
            String line = file.readLine();
            lineLength = line.length();
            while (line != null) {
                wordSearch.add(line.toCharArray());
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        if(!isPart2) {
            for (int yIndex = 0; yIndex < wordSearch.size(); yIndex++) {
                for (int xIndex = 0; xIndex < lineLength; xIndex++) {
                    char[] currentLine = wordSearch.get(yIndex);
                    char currentLetter = currentLine[xIndex];

                    if (currentLetter == 'X') {
                        //Right check - do we have 4 chars?
                        if (xIndex + 3 < lineLength) {
                            char[] word = new char[]{currentLine[xIndex], currentLine[xIndex + 1], currentLine[xIndex + 2], currentLine[xIndex + 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Left check - do we have 4 chars?
                        if (xIndex - 3 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], currentLine[xIndex - 1], currentLine[xIndex - 2], currentLine[xIndex - 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Up Check - do we have 4 chars?
                        if (yIndex - 3 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex - 1)[xIndex], wordSearch.get(yIndex - 2)[xIndex], wordSearch.get(yIndex - 3)[xIndex]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Down Check - do we have 4 chars?
                        if (yIndex + 3 < wordSearch.size()) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex + 1)[xIndex], wordSearch.get(yIndex + 2)[xIndex], wordSearch.get(yIndex + 3)[xIndex]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Right,down diagonal check
                        if (yIndex + 3 < wordSearch.size() && xIndex + 3 < lineLength) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex + 1)[xIndex + 1], wordSearch.get(yIndex + 2)[xIndex + 2], wordSearch.get(yIndex + 3)[xIndex + 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Left,down diagonal check
                        if (yIndex + 3 < wordSearch.size() && xIndex - 3 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex + 1)[xIndex - 1], wordSearch.get(yIndex + 2)[xIndex - 2], wordSearch.get(yIndex + 3)[xIndex - 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Right,up diagonal check
                        if (yIndex - 3 >= 0 && xIndex + 3 < lineLength) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex - 1)[xIndex + 1], wordSearch.get(yIndex - 2)[xIndex + 2], wordSearch.get(yIndex - 3)[xIndex + 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                        //Left,up diagonal check
                        if (yIndex - 3 >= 0 && xIndex - 3 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex - 1)[xIndex - 1], wordSearch.get(yIndex - 2)[xIndex - 2], wordSearch.get(yIndex - 3)[xIndex - 3]};
                            if (Arrays.equals(word, xmas)) {
                                answer++;
                            }
                        }
                    }
                }
            }
        } else {
            List<int[]> aLocations = new ArrayList<>();

            for (int yIndex = 0; yIndex < wordSearch.size(); yIndex++) {
                for (int xIndex = 0; xIndex < lineLength; xIndex++) {
                    char[] currentLine = wordSearch.get(yIndex);
                    char currentLetter = currentLine[xIndex];

                    if (currentLetter == 'M') {
                        //Right,down diagonal check
                        if (yIndex + 2 < wordSearch.size() && xIndex + 2 < lineLength) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex + 1)[xIndex + 1], wordSearch.get(yIndex + 2)[xIndex + 2]};
                            if (Arrays.equals(word, xmas)) {
                                int[] aLocation = new int[]{xIndex + 1, yIndex + 1};
                                if(aLocations.stream().anyMatch(location -> Arrays.equals(location, aLocation))) {
                                    answer++;
                                } else {
                                    aLocations.add(aLocation);
                                }
                            }
                        }

                        //Left,down diagonal check
                        if (yIndex + 2 < wordSearch.size() && xIndex - 2 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex + 1)[xIndex - 1], wordSearch.get(yIndex + 2)[xIndex - 2]};
                            if (Arrays.equals(word, xmas)) {
                                int[] aLocation = new int[]{xIndex - 1, yIndex + 1};
                                if(aLocations.stream().anyMatch(location -> Arrays.equals(location, aLocation))) {
                                    answer++;
                                } else {
                                    aLocations.add(aLocation);
                                }
                            }
                        }

                        //Right,up diagonal check
                        if (yIndex - 2 >= 0 && xIndex + 2 < lineLength) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex - 1)[xIndex + 1], wordSearch.get(yIndex - 2)[xIndex + 2]};
                            if (Arrays.equals(word, xmas)) {
                                int[] aLocation = new int[]{xIndex + 1, yIndex - 1};
                                if(aLocations.stream().anyMatch(location -> Arrays.equals(location, aLocation))) {
                                    answer++;
                                } else {
                                    aLocations.add(aLocation);
                                }
                            }
                        }

                        //Left,up diagonal check
                        if (yIndex - 2 >= 0 && xIndex - 2 >= 0) {
                            char[] word = new char[]{currentLine[xIndex], wordSearch.get(yIndex - 1)[xIndex - 1], wordSearch.get(yIndex - 2)[xIndex - 2]};
                            if (Arrays.equals(word, xmas)) {
                                int[] aLocation = new int[]{xIndex - 1, yIndex - 1};
                                if(aLocations.stream().anyMatch(location -> Arrays.equals(location, aLocation))) {
                                    answer++;
                                } else {
                                    aLocations.add(aLocation);
                                }
                            }
                        }
                    }
                }
            }
        }

        return String.valueOf(answer);
    }
}
