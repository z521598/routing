package com.la.util;

import com.la.path.PathBean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */
public class MainUtils {
    public static int getIncrementFlow(List<PathBean> pathBeanList) {
        int currentFlow = 0;
        for (int i = 0; i < pathBeanList.size(); i++) {
            currentFlow += pathBeanList.get(i).getFlow();
        }
        return currentFlow;
    }

    public static int[][] changeByPath(int[][] srcCapacityMatrix, List<PathBean> pathBeanList, int step) {
        int[][] newMatrix = deepCopy(srcCapacityMatrix);
        if (pathBeanList == null) {
            return newMatrix;
        }
        for (int i = 0; i < pathBeanList.size(); i++) {
            PathBean pathBean = pathBeanList.get(i);
            Integer incrementFlow = pathBean.getFlow();
            List<Integer> path = pathBean.getPath();
            // 容量不变
            if (path.size() <= step + 1) {
                continue;
            }
            int nextIndex = path.size() - 1 - step - 1;
            int preIndex = path.size() - 1 - step;
            int nextNode = path.get(nextIndex);
            int preNode = path.get(preIndex);
            newMatrix[preNode][nextNode] -= incrementFlow;
        }

        return newMatrix;
    }

    public static int[][] deepCopy(int[][] srcCapacityMatrix) {
        int[][] newMatrix = new int[srcCapacityMatrix.length][];
        for (int i = 0; i < srcCapacityMatrix.length; i++) {
            newMatrix[i] = Arrays.copyOf(srcCapacityMatrix[i], srcCapacityMatrix[i].length);
        }
        return newMatrix;
    }

    public static int[][] convertToArray(String graphMatrixString) {
        String[] array = graphMatrixString.split("\n");
        System.out.println(array.length);
        int[][] matrix = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            String[] chileArray = array[i].split(" ");
            for (int j = 0; j < chileArray.length; j++) {
                matrix[i][j] = Integer.parseInt(chileArray[j]);
            }
        }
        return matrix;
    }
}
