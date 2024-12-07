package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Solver implements Solver {
    boolean isPart2;

    private final String MUL_REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    private final String DONT_REGEX = "don't\\(\\).*?do\\(\\)";

    public Day3Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        StringBuilder input = new StringBuilder();

        try {
            String line = file.readLine();
            while (line != null) {
                input.append(line);
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        String inputString = input.toString();

        if(isPart2) {
            Pattern sanitizePattern = Pattern.compile(DONT_REGEX);
            Matcher sanitizeMatcher = sanitizePattern.matcher(inputString);
            while (sanitizeMatcher.find()) {
                inputString = inputString.replace(sanitizeMatcher.group(0), "");
            }
        }


        Pattern pattern = Pattern.compile(MUL_REGEX);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            answer += (num1*num2);
        }

        return String.valueOf(answer);
    }
}
