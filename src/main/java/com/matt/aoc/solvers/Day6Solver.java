package com.matt.aoc.solvers;

import java.awt.dnd.InvalidDnDOperationException;
import java.io.BufferedReader;
import java.util.*;

public class Day6Solver implements Solver {
    boolean isPart2;

    public Day6Solver(boolean isPart2) {
        this.isPart2 = isPart2;
    }

    @Override
    public String solve(BufferedReader file) {
        int answer = 0;

        List<char[]> map = new ArrayList<>();
        int lineLength = 0;
        int guardX = 0;
        int guardY = 0;
        int initialGuardX = 0;
        int initialGuardY = 0;
        String guardDirection = "";
        String initialGuardDirection = "";
        Set<String> visitedPositions = new HashSet<>();

        Map<Character, String> directionMap = Map.of('v', "DOWN", '^', "UP", '<', "LEFT", '>', "RIGHT");


        try {
            String line = file.readLine();
            boolean guardFound = false;
            lineLength = line.length();
            while (line != null) {
                char[] lineChars = line.toCharArray();

                for (int i = 0; i < lineChars.length; i++) {
                    if(lineChars[i] == '^' || lineChars[i] == '>' || lineChars[i] == '<' || lineChars[i] == 'v') {
                        guardX = i;
                        guardDirection = directionMap.get(lineChars[i]);
                        guardFound = true;
                    }
                }

                if(!guardFound) {
                    guardY++;
                }

                map.add(lineChars);
                line = file.readLine();
            }
        } catch (Exception e) {
            // Error handling is for nerds
            throw new RuntimeException(e);
        }

        initialGuardX = guardX;
        initialGuardY = guardY;
        initialGuardDirection = guardDirection;
        visitedPositions.add(String.format("{%d, %d}", guardX, guardY));

        while(guardY >= 0 && guardY < map.size() && guardX >= 0 && guardX < lineLength) {
           if(checkForObstacles(guardX, guardY, map, guardDirection)){
               System.out.println(String.format("Obstacle %s from {%d, %d}. Rotating 90 degrees right.", guardDirection, guardX, guardY));
               guardDirection = rotate90DegreesRight(guardDirection);
               System.out.println(String.format("New direction %s", guardDirection));
           } else {
               switch (guardDirection) {
                   case "UP":
                       guardY--;
                       break;
                   case "RIGHT":
                       guardX++;
                       break;
                   case "DOWN":
                       guardY++;
                       break;
                   case "LEFT":
                       guardX--;
                       break;
                   default:
                       throw new InvalidDnDOperationException();
               }
               if(guardY >= 0 && guardY < map.size() && guardX >= 0 && guardX < lineLength) {
                   visitedPositions.add(String.format("{%d, %d}", guardX, guardY));
                   System.out.println(String.format("Stepped %s, new location {%d, %d}", guardDirection, guardX, guardY));
               } else {
                   System.out.println(String.format("Stepped %s out of map", guardDirection));
               }

           }
        }

        if(!isPart2) {
            answer = visitedPositions.size();
            return String.valueOf(answer);
        }

        if(isPart2) {
            for (String visitedPosition : visitedPositions) {
                String[] positionSplit = visitedPosition.split(",");
                int posX = Integer.parseInt(positionSplit[0].trim().replace("{", "").replace("}", ""));
                int posY = Integer.parseInt(positionSplit[1].trim().replace("{", "").replace("}", ""));

                System.out.println(String.format("Checking with new obstacle at {%d, %d}", posX, posY));

                guardX = initialGuardX;
                guardY = initialGuardY;
                guardDirection = initialGuardDirection;
                boolean loop = false;
                Set<String> newVisited = new HashSet<>();
                newVisited.add(String.format("{%d, %d, %s}", guardX, guardY, guardDirection));

                while(guardY >= 0 && guardY < map.size() && guardX >= 0 && guardX < lineLength) {
                    if(checkForObstacles(guardX, guardY, map, guardDirection, posX, posY)){
                        guardDirection = rotate90DegreesRight(guardDirection);
                    } else {
                        switch (guardDirection) {
                            case "UP":
                                guardY--;
                                break;
                            case "RIGHT":
                                guardX++;
                                break;
                            case "DOWN":
                                guardY++;
                                break;
                            case "LEFT":
                                guardX--;
                                break;
                            default:
                                throw new InvalidDnDOperationException();
                        }

                        if(guardY >= 0 && guardY < map.size() && guardX >= 0 && guardX < lineLength) {
                            if(newVisited.contains(String.format("{%d, %d, %s}", guardX, guardY, guardDirection))) {
                                System.out.println(String.format("Found loop with new obstacle at {%d, %d}", posX, posY));
                                loop = true;
                                break;
                            }

                            newVisited.add(String.format("{%d, %d, %s}", guardX, guardY, guardDirection));
                        } else {
                            System.out.println(String.format("Stepped %s out of map with new obstacle at {%d, %d}", guardDirection, posX, posY));
                        }

                    }
                }

                if(loop) {
                    answer++;
                }
            }
        }

        return String.valueOf(answer);
    }

    private String rotate90DegreesRight(String direction) {
        return switch (direction) {
            case "UP":
                yield "RIGHT";
            case "RIGHT":
                yield "DOWN";
            case "DOWN":
                yield "LEFT";
            case "LEFT":
                yield "UP";
            default:
                yield "";
        };
    }

    private boolean checkForObstacles(int guardX, int guardY, List<char[]> map, String direction, int aditionalX, int aditionalY) {
        return switch (direction) {
            case "UP":
                if(guardY - 1 < 0){
                    yield false;
                }
                yield map.get(guardY-1)[guardX] == '#' || (guardY-1 == aditionalY && guardX == aditionalX);
            case "RIGHT":
                if(guardX + 1 >= map.get(guardY).length){
                    yield false;
                }
                yield map.get(guardY)[guardX+1] == '#' || (guardY == aditionalY && guardX+1 == aditionalX);
            case "DOWN":
                if(guardY + 1 >= map.size()){
                    yield false;
                }
                yield map.get(guardY+1)[guardX] == '#' || (guardY+1 == aditionalY && guardX == aditionalX);
            case "LEFT":
                if(guardX - 1 < 0){
                    yield false;
                }
                yield map.get(guardY)[guardX-1] == '#' || (guardY == aditionalY && guardX-1 == aditionalX);
            default:
                yield false;
        };
    }

    private boolean checkForObstacles(int guardX, int guardY, List<char[]> map, String direction) {
        return checkForObstacles(guardX, guardY, map, direction, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
