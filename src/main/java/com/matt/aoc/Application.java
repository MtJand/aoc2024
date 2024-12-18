package com.matt.aoc;

import com.matt.aoc.helpers.SolverHelper;
import com.matt.aoc.solvers.*;

public class Application {

    public static void main(String[] args) {
        String answer = SolverHelper.applySolver("day10.txt", new Day10Solver(true));
        System.out.println(answer);
    }
}
