package com.la.util;

import java.util.Queue;
import java.util.Stack;

/**
 * Created by Administrator on 2018/5/31.
 */
public class PrintUtils {
    public static void printArray(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void printArray(int[][] array) {
        if (array == null) {
            return;
        }
        for (int j = 0; j < array.length; j++) {
            if (j == 0) {
                System.out.print("   ");
                for (int k = 0; k < array.length; k++) {
                    System.out.print("\"" + k + "\" ");
                }
                System.out.println();
            }
            System.out.print("\"" + j + "\" ");
            for (int x = 0; x < array[j].length; x++) {
                System.out.print(array[j][x]);
                if (x != array[j].length - 1) {
                    if (array[j][x] >= 10) {
                        System.out.print("--");
                    } else {
                        System.out.print("---");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void println(String str) {
        System.out.println(str);
    }

    public static void printPath(Stack<Integer> printQueue) {
        while (!printQueue.isEmpty()) {
            print("“点" + printQueue.pop() + "”" + "-->");
        }
        print("“通过”");
    }

    public static void println() {
        System.out.println();
    }
}
