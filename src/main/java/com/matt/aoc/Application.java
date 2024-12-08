package com.matt.aoc;

import com.matt.aoc.helpers.SolverHelper;
import com.matt.aoc.solvers.*;

public class Application {

    public static void main(String[] args) {
        String answer = SolverHelper.applySolver("day8.txt", new Day8Solver(true));
        System.out.println(answer);
    }
}
