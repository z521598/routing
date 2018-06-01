package com.la.util;

import com.la.path.PathBean;

import java.util.List;

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

    public static void print(Object str) {
        System.out.print(str);
    }

    public static void println(Object str) {
        System.out.println(str);
    }

    private static void printPath(List<Integer> path) {
        for (int i = path.size() - 1; i >= 0; i--) {
            print("“点" + path.get(i) + "”" + "-->");
        }
        print("“通过”");
    }

    public static void printPaths(List<PathBean> pathBeanList) {
        for (int i = 0; i < pathBeanList.size(); i++) {
            println(pathBeanList.get(i).getFlow() + "辆车疏通的路线：");
            printPath(pathBeanList.get(i).getPath());
        }

    }

    public static void println() {
        System.out.println();
    }


}
