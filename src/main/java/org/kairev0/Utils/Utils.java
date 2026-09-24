package org.kairev0.Utils;

import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static final Scanner scanner = new Scanner(System.in);
    public static final Random random = new Random();

    public static void printField(int[][] field) {
        if (field != null) {
            for (int[] ints : field) {
                for (int anInt : ints) {
                    System.out.print(anInt + " ");
                }
                System.out.println();
            }
        }
    }
}
