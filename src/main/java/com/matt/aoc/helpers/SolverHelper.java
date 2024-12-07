package com.matt.aoc.helpers;

import com.matt.aoc.solvers.Solver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class SolverHelper {
    public static String applySolver(String fileName, Solver solver) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SolverHelper.class.getResourceAsStream("/inputs/" + fileName))));

        return solver.solve(bufferedReader);
    }
}
