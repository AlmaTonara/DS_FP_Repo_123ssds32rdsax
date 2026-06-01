package com.mycompany.dsfinalprojectapp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

public class Benchmarking {
    // Prints to a text file
    private static PrintWriter writer;
    
    // Testing for 1D, with timers
    public static long test1D_add(OneD grid) {
        long startTime = System.nanoTime();
        grid.insert(new Entity(1, 1));
        return System.nanoTime() - startTime;
    }

    public static long test1D_remove(OneD grid) {
        long startTime = System.nanoTime();
        grid.remove(1, 1);
        return System.nanoTime() - startTime;
    }

    public static long test1D_get(OneD grid, int qW, int qH) {
        long startTime = System.nanoTime();
        grid.getRange(0, 0, qW, qH);
        return System.nanoTime() - startTime;
    }

    public static long test1D_move(OneD grid, int i, int j) {
        long startTime = System.nanoTime();
        grid.move(i, i, j, j);
        return System.nanoTime() - startTime;
    }

    public static void test1D(int mW, int mH, int qW, int qH, int n) {
        OneD grid = new OneD(mW, mH);
        int x = 0, y = 0;

        for (int i = 0; i < n; i++) {
            grid.insert(new Entity(x, y));

            x++;
            if (x >= mW) {
                x = 0;
                y++;
            }
        }

        warmup1D(grid, qW, qH);

        runTest1D(grid, qW, qH);
    }

    // Basic warmup
    private static void warmup1D(OneD grid, int qW, int qH) {
        for (int i = 0; i < 10; i++) {
            test1D_add(grid);
            test1D_remove(grid);
            test1D_get(grid, qW, qH);
            test1D_move(grid, 1, 2);
        }
    }

    // Run the test
    private static void runTest1D(OneD grid, int qW, int qH) {
        long addT = 0, remT = 0, getT = 0, movT = 0;
        int runs = 1000;

        int i = 1;
        int j = 2;
        for (int k = 0; k < runs; k++) {
            addT += test1D_add(grid);
            remT += test1D_remove(grid);
            getT += test1D_get(grid, qW, qH);
            movT += test1D_move(grid, i, j);
            
            // This part is surprisingly necessary otherwise move breaks
            if (i == 2){
                i--;
                j++;
            } else {
                i++;
                j--;
            }
        }

        printResult("1D Grid", addT, remT, getT, movT, runs);
    }

    // Testing for 2D
    public static long test2D_add(TwoD grid) {
        long startTime = System.nanoTime();
        grid.insert(new Entity(1, 1));
        return System.nanoTime() - startTime;
    }

    public static long test2D_remove(TwoD grid) {
        long startTime = System.nanoTime();
        grid.remove(1, 1);
        return System.nanoTime() - startTime;
    }

    public static long test2D_get(TwoD grid, int qW, int qH) {
        long startTime = System.nanoTime();
        grid.getRange(0, 0, qW, qH);
        return System.nanoTime() - startTime;
    }

    public static long test2D_move(TwoD grid) {
        long startTime = System.nanoTime();
        grid.move(1, 1, 2, 2);
        return System.nanoTime() - startTime;
    }

    public static void test2D(int mW, int mH, int qW, int qH, int n) {
        TwoD grid = new TwoD(mW, mH);
        int x = 0, y = 0;

        for (int i = 0; i < n; i++) {
            grid.insert(new Entity(x, y));

            x++;
            if (x >= mW) {
                x = 0;
                y++;
            }
        }

        warmup2D(grid, qW, qH);
        runTest2D(grid, qW, qH);
    }

    private static void warmup2D(TwoD grid, int qW, int qH) {
        for (int i = 0; i < 10; i++) {
            test2D_add(grid);
            test2D_remove(grid);
            test2D_get(grid, qW, qH);
            test2D_move(grid);
        }
    }

    private static void runTest2D(TwoD grid, int qW, int qH) {
        long addT = 0, remT = 0, getT = 0, movT = 0;
        int runs = 1000;

        for (int i = 0; i < runs; i++) {
            addT += test2D_add(grid);
            remT += test2D_remove(grid);
            getT += test2D_get(grid, qW, qH);
            movT += test2D_move(grid);
        }

        printResult("2D Grid", addT, remT, getT, movT, runs);
    }

    // Testing for Hash Grid
    public static long testHG_add(HashGrid grid) {
        long startTime = System.nanoTime();
        grid.insert(new Entity(1, 1));
        return System.nanoTime() - startTime;
    }

    public static long testHG_remove(HashGrid grid) {
        long startTime = System.nanoTime();
        grid.remove(1, 1);
        return System.nanoTime() - startTime;
    }

    public static long testHG_get(HashGrid grid, int qW, int qH) {
        long startTime = System.nanoTime();
        grid.getRange(0, 0, qW, qH);
        return System.nanoTime() - startTime;
    }

    public static long testHG_move(HashGrid grid) {
        long startTime = System.nanoTime();
        grid.move(1, 1, 2, 2);
        return System.nanoTime() - startTime;
    }

    public static void testHG(int mW, int mH, int qW, int qH, int n) {
        HashGrid grid = new HashGrid(mW, mH);
        int x = 0, y = 0;

        for (int i = 0; i < n; i++) {
            grid.insert(new Entity(x, y));

            x++;
            if (x >= mW) {
                x = 0;
                y++;
            }
        }

        warmupHG(grid, qW, qH);
        runTestHG(grid, qW, qH);
    }

    private static void warmupHG(HashGrid grid, int qW, int qH) {
        for (int i = 0; i < 10; i++) {
            testHG_add(grid);
            testHG_remove(grid);
            testHG_get(grid, qW, qH);
            testHG_move(grid);
        }
    }

    private static void runTestHG(HashGrid grid, int qW, int qH) {
        long addT = 0, remT = 0, getT = 0, movT = 0;
        int runs = 1000;

        for (int i = 0; i < runs; i++) {
            addT += testHG_add(grid);
            remT += testHG_remove(grid);
            getT += testHG_get(grid, qW, qH);
            movT += testHG_move(grid);
        }

        printResult("Hash Grid", addT, remT, getT, movT, runs);
    }

    // Prints results onto a file
    private static void printResult(String name, long addT, long remT, long getT, long movT, int runs) {
        writer.println(name);
        writer.println("addT = " + (addT / runs));
        writer.println("remT = " + (remT / runs));
        writer.println("movT = " + (movT / runs));
        writer.println("getT = " + (getT / runs));
        writer.println();

        writer.flush();
    }

    public static void main(String[] args) {
        // Tries to receive input
        try{
            int mapWidth=Integer.parseInt(JOptionPane.showInputDialog("Map Width"));
            int mapHeight=Integer.parseInt(JOptionPane.showInputDialog("Map Height"));
            int queryWidth=Integer.parseInt(JOptionPane.showInputDialog("Query Width"));
            int queryHeight=Integer.parseInt(JOptionPane.showInputDialog("Query Height"));
            int entityCount=Integer.parseInt(JOptionPane.showInputDialog("Entity Count"));

            writer=new PrintWriter(new FileWriter("benchmark_results.txt"));

            writer.println("Map Size = "+mapWidth+" x "+mapHeight);
            writer.println("Query Size = "+queryWidth+" x "+queryHeight);
            writer.println("Entity Count = "+entityCount);
            writer.println();

            // Does the testing
            test1D(mapWidth,mapHeight,queryWidth,queryHeight,entityCount);
            test2D(mapWidth,mapHeight,queryWidth,queryHeight,entityCount);
            testHG(mapWidth,mapHeight,queryWidth,queryHeight,entityCount);

            // Closes it
            writer.close();

            // Popup to notify success
            JOptionPane.showMessageDialog(null,"Benchmark complete.\nResults saved to benchmark_results.txt");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
    }
}