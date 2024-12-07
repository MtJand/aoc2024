package com.matt.aoc;

import com.matt.aoc.helpers.SolverHelper;
import com.matt.aoc.solvers.*;

public class Application {

    public static void main(String[] args) {
        String answer = SolverHelper.applySolver("day7.txt", new Day7Solver(true));
        System.out.println(answer);
    }
}
