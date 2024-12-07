package com.matt.aoc.solvers;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day5Solver implements Solver {
    boolean isPart2;

    public Day5Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        boolean countLines = false;

        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> manuals = new ArrayList<>();

        try {
            String line = file.readLine();
            while (line != null) {
                if(line.isBlank()) {
                    countLines = true;
                } else if(countLines) {
                    List<Integer> manual = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                    manuals.add(manual);
                } else {
                    String[] rule = line.split("\\|");
                    Rule parsedRule = new Rule(Integer.parseInt(rule[1]), Integer.parseInt(rule[0]));
                    rules.add(parsedRule);
                }
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }


        List<List<Integer>> validManuals = new ArrayList<>();
        List<List<Integer>> invalidManuals = new ArrayList<>();
        manuals.forEach(manual -> {
            boolean valid = true;

            for (int i = 0; i < manual.size(); i++) {
                Integer currentPage = manual.get(i);
                Set<Integer> requiredPreviousPages = rules.stream().filter(rule -> rule.afterPage == currentPage).map(rule -> rule.beforePage).collect(Collectors.toSet());

                for(Integer requiredPreviousPage : requiredPreviousPages) {
                        if(manual.indexOf(requiredPreviousPage) > i) {
                            valid = false;
                            break;
                        }
                }
            }

            if(valid) {
                validManuals.add(manual);
            } else {
                invalidManuals.add(manual);
            }
        });

        if(!isPart2) {
            for (List<Integer> manual : validManuals) {
                answer += manual.get((int) Math.ceil(manual.size() / 2));
            }
        } else {
            for(List<Integer> manual : invalidManuals) {
                List<Integer> orderedManual = new ArrayList<>(manual);

                orderedManual.sort((a, b) -> {
                    Set<Integer> requiredPreviousPages = rules.stream().filter(rule -> rule.afterPage == a).map(rule -> rule.beforePage).collect(Collectors.toSet());
                    return requiredPreviousPages.contains(b) ? -1 : 1;
                });

                answer += orderedManual.get((int) Math.ceil(orderedManual.size() / 2));
            }
        }

        return String.valueOf(answer);
    }

    private class Rule {
        int afterPage;
        int beforePage;

        Rule(int afterPage, int beforePage) {
            this.afterPage = afterPage;
            this.beforePage = beforePage;
        }
    }
}
