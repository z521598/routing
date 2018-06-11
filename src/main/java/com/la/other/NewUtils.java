package com.la.other;

import com.la.path.PathBean;
import com.la.util.MainUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NewUtils {
    public static int[][] newRandomCapacityMatrix(int length, int randomCapacitySeed) {
        int[][] capacityMatrix = new int[length][];
        for (int i = 0; i < length; i++) {
            capacityMatrix[i] = new int[length];
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (new Random().nextInt(10) / 2 == 0) {
                    capacityMatrix[i][j] = 0;
                } else {
                    capacityMatrix[i][j] = new Random().nextInt(randomCapacitySeed);
                }
            }
        }
        return capacityMatrix;
    }

    // ts需要在length之内
    public static int[][] newRandomCapacityMatrix(int length, int randomCapacitySeed, int[] ts) {
        int newLength = length + 1;
        int[][] capacityMatrix = newRandomCapacityMatrix(length, randomCapacitySeed);
        int t = newLength - 1;
        int[][] tempCapacityMatrix = MainUtils.deepCopy(capacityMatrix);
        capacityMatrix = new int[newLength][];
        for (int i = 0; i < tempCapacityMatrix.length; i++) {
            capacityMatrix[i] = Arrays.copyOf(tempCapacityMatrix[i], newLength);
        }
        capacityMatrix[newLength - 1] = new int[newLength];
        // 更新超级汇点带来的二维容量数组的变化
        for (int i = 0; i < ts.length; i++) {
            int originT = ts[i];
            capacityMatrix[originT][t] = Integer.MAX_VALUE;
        }

        return capacityMatrix;
    }

    public static double getSumTime(int sumFlow, int currentFlow, List<PathBean> pathBeanList) {
        double sumTime = 0;

        double step = (double) sumFlow / (double) currentFlow;

        int singleTime = 0;
        if (sumFlow > currentFlow) {
            for (int i = 0; i < pathBeanList.size(); i++) {
                PathBean pathBean = pathBeanList.get(i);
                int flow = pathBean.getFlow();
                int time = pathBean.getPath().size() - 1;
                singleTime += flow * time;
            }
        }
        sumTime = step * singleTime;
        return sumTime;
    }


}
