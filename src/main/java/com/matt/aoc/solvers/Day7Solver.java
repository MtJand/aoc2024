package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7Solver implements Solver {
    boolean isPart2;

    public Day7Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        BigDecimal answer = BigDecimal.valueOf(0);
        long startTime = System.nanoTime();
        int linesChecked = 0;

        try {
            String line = file.readLine();
            while (line != null) {
                BigDecimal equationAnswer = BigDecimal.valueOf(Long.parseLong(line.split(":")[0].trim()));
                List<BigDecimal> numbers = Arrays.stream(line.split(":")[1].trim().split(" ")).map(Long::parseLong).map(BigDecimal::valueOf).toList();

                List<String> solution = getSolution(equationAnswer, numbers);
                boolean hasSolution = !solution.isEmpty();

                if(hasSolution) {
                    answer = answer.add(equationAnswer);
                }

                linesChecked++;
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        System.out.printf("Checked %d lines in %d ms%n", linesChecked, (System.nanoTime()-startTime)/1000000);
        return String.valueOf(answer);
    }

    public List<String> getSolution(BigDecimal answer, List<BigDecimal> numbers) {
        List<String> solution = new ArrayList<>();

        if(numbers.size() == 2) {
            if(numbers.get(0).multiply(numbers.get(1)).compareTo(answer) == 0) {
                solution.add(String.valueOf(numbers.get(0)));
                solution.add("*");
                solution.add(String.valueOf(numbers.get(1)));
            } else if (numbers.get(0).add(numbers.get(1)).compareTo(answer) == 0) {
                solution.add(String.valueOf(numbers.get(0)));
                solution.add("+");
                solution.add(String.valueOf(numbers.get(1)));
            } if(isPart2 && BigDecimal.valueOf(Long.parseLong(numbers.get(0).toString() + numbers.get(1).toString())).compareTo(answer) == 0) {
                solution.add(String.valueOf(numbers.get(0)));
                solution.add("||");
                solution.add(String.valueOf(numbers.get(1)));
            }

            return solution;
        }

        List<BigDecimal> finalNumberRemoved = numbers.subList(0, numbers.size()-1);
        List<String> subtractSolution = getSolution(answer.subtract(numbers.getLast()), finalNumberRemoved);
        if(!subtractSolution.isEmpty()) {
            subtractSolution.add("+");
            subtractSolution.add(String.valueOf(numbers.getLast()));
            return subtractSolution;
        }

        try {
            List<String> divideSolution = getSolution(answer.divide(numbers.getLast()), finalNumberRemoved);
            if (!divideSolution.isEmpty()) {
                divideSolution.add("*");
                divideSolution.add(String.valueOf(numbers.getLast()));
                return divideSolution;
            }
        } catch (Exception e) {
            //Error handling is for nerds
        }

        if(isPart2) {
            if(answer.toString().endsWith(numbers.getLast().toString())) {
                String answerString = answer.toString();
                String lastNumberString = numbers.getLast().toString();
                BigDecimal newTarget = BigDecimal.valueOf(Long.parseLong(answerString.substring(0, answerString.length() - lastNumberString.length())));
                List<String> concattedSolution = getSolution(newTarget, finalNumberRemoved);
                if(!concattedSolution.isEmpty()) {
                    concattedSolution.add("||");
                    concattedSolution.add(String.valueOf(numbers.getLast()));
                    return concattedSolution;
                }
            }
        }

        return solution;
    }
}
