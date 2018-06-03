package com.la.util;

import com.la.path.PathBean;

import java.util.*;

/**
 * Created by Administrator on 2018/6/1.
 */
public class MainUtils {
    public static int getMaxFlow(List<PathBean> pathBeanList) {
        int currentFlow = 0;
        for (int i = 0; i < pathBeanList.size(); i++) {
            currentFlow += pathBeanList.get(i).getFlow();
        }
        return currentFlow;
    }

    private static class PlanPathBean {
        // 时间相同的路径
        List<PathBean> pathBeanList = new LinkedList<>();
        // 容量总和
        int capacity = 0;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("PlanPathBean{");
            sb.append("pathBeanList=").append(pathBeanList);
            sb.append(", capacity=").append(capacity);
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * 背景：车都一样，对容量的解释：单位时间内可以通过此路的车的数量
     *
     * @param carNumber
     * @param pathBeanList
     */

    public static double sumTime(int carNumber, List<PathBean> pathBeanList) {
        double time = 0;
        // 将多条路线进行抽象
        Map<Integer, PlanPathBean> map = new LinkedHashMap<>();
        for (int i = 0; i < pathBeanList.size(); i++) {
            PathBean pathBean = pathBeanList.get(i);
            int key = pathBean.getPath().size() - 1;
            PlanPathBean planPathBean = map.get(key);
            if (planPathBean == null) {
                planPathBean = new PlanPathBean();
                map.put(key, planPathBean);
            }
            planPathBean.pathBeanList.add(pathBean);
            planPathBean.capacity += pathBean.getFlow();
        }
        System.out.println(map);

        int maxFlow = MainUtils.getMaxFlow(pathBeanList);
        if (carNumber <= maxFlow) {
            // 总时间为最长路径的时间
            for (int i = 0; i < pathBeanList.size(); i++) {
                time = Math.max(time, (pathBeanList.get(i).getPath().size() - 1));
            }
            return time;
        }

        // 只有超过最大流的余数部分才需要进行规划出行路线
        double remain = (double) carNumber / (double) maxFlow;
        return remain;
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
