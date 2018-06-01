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
    public static String toString(int[][] array) {
        if (array == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < array.length; j++) {
            if (j == 0) {
                sb.append("   ");
                for (int k = 0; k < array.length; k++) {
                    sb.append("\"" + k + "\" ");
                }
                sb.append("\n");
            }
            sb.append("\"" + j + "\" ");
            for (int x = 0; x < array[j].length; x++) {
                sb.append(array[j][x]);
                if (x != array[j].length - 1) {
                    if (array[j][x] >= 10) {
                        sb.append("--");
                    } else {
                        sb.append("---");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
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
        println("“通过”");
    }


    public static void printPaths(List<PathBean> pathBeanList) {
        for (int i = 0; i < pathBeanList.size(); i++) {
            print(pathBeanList.get(i).getFlow() + "辆车疏通的路线：");
            printPath(pathBeanList.get(i).getPath());
        }
    }
    public static String toString(List<PathBean> pathBeanList){
       StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pathBeanList.size(); i++) {
            sb.append(pathBeanList.get(i).getFlow() + "辆车疏通的路线：");
            PathBean pathBean = pathBeanList.get(i);
            for (int j = pathBean.getPath().size() - 1; j >= 0; j--) {
                sb.append("“点" + pathBean.getPath().get(j) + "”" + "-->");
            }
            sb.append("“通过”").append("\n");
        }
        return sb.toString();
    }

    public static void println() {
        System.out.println();
    }


}
