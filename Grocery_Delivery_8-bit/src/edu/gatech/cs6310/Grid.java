package edu.gatech.cs6310;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// CLI visualization of grid map. Each cell take three chars
public class Grid {

    private String[][] grid = new String[10][10];

    // constructor. no parameters, only initialize empty grid map of " · "
    public Grid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " · ";
            }
        }
    }

    // read points. replace placeholders by store/customer/drone id
    public void updatePoint(String id, int x, int y) {
        String idAbbreviation = id.substring(0, 3);
        grid[x][y] = idAbbreviation;
    }

    // read path
    public void updatePath(List<Point> path) {
        for (Point pathPoint : path) {
            grid[pathPoint.x][pathPoint.y] = "███";
        }
    }

    // read storm
    // ░░░ weak intensity
    // ▒▒▒ mid intensity
    // ▓▓▓ strong intensity
    public void updateStorm(Storm storm) {
        List<Point> stormArea = storm.getArea();
        Point centerPoint = storm.getCenter();
        for (Point stormPoint : stormArea) {
            grid[stormPoint.x][stormPoint.y] = "░░░";
        }
        grid[centerPoint.x][centerPoint.y] = storm.getStormId().substring(0, 3);

    }


    // print the grid map using CLI
    // resources - https://en.wikipedia.org/wiki/Box-drawing_character
    public void visualize() {
        // upper edge of the grid map
        System.out.print(" ╔═");
        for (int i = 0; i < 10; i++) {
            System.out.print("═╤═");
        }
        System.out.print("═╗ ");
        System.out.println();
        // contents, and edges
        for (int i = 0; i<10; i++) {
            System.out.print(" ╟─");
            for (int j = 0; j<10; j++) {
                System.out.print(grid[j][i]);
            }
            System.out.print("─╢ ");
            System.out.println();
        }
        // lower edge
        System.out.print(" ╚═");
        for (int i = 0; i < 10; i++) {
            System.out.print("═╧═");
        }
        System.out.print("═╝ ");
        System.out.println();
    }

}

