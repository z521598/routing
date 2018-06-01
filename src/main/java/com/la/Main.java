package com.la;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import java.util.*;

/**
 * Created by Administrator on 2018/5/30.
 */
public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static int[][] defaultCapacity = new int[][]{
            new int[]{0, 1, 20, 10},
            new int[]{0, 0, 3, 1},
            new int[]{0, 0, 0, 20},
            new int[]{0, 0, 0, 0}
    };

    public static void main(String[] args) {
        // 根据用户输入初始化流量图
        int sumFlow = 0;
        FlowGraph flowGraph = null;
        int s = 0;
        int t = 0;
        try {
            int[][] capacityMatrix;
            PrintUtils.println("是否重新绘制道路图(y/n)");
            if ("n".equals(scan.next())) {
                capacityMatrix = defaultCapacity;
            } else {
                PrintUtils.println("请输入道路图的节点数");
                int length = scan.nextInt();
                capacityMatrix = new int[length][length];
                for (int i = 0; i < length; i++) {
                    for (int j = i + 1; j < length; j++) {
                        PrintUtils.println("请输入“点" + i + "”到“点" + j + "”的道路的车辆容量(无路，则容量为0)");
                        int capacity = scan.nextInt();
                        capacityMatrix[i][j] = capacity;
                    }
                }
            }
            flowGraph = new FlowGraph(capacityMatrix);
            PrintUtils.println("道路模型初始化完成");
            PrintUtils.printArray(flowGraph.getCapacityMatrix());

            PrintUtils.println("请输入拥堵点：(0-" + (flowGraph.getNumber() - 1) + ")");
            s = scan.nextInt();
            PrintUtils.println("请输入拥堵点需要疏散的车辆数目：");
            sumFlow = scan.nextInt();
            PrintUtils.println("请输入疏散目标点：(0-" + (flowGraph.getNumber() - 1) + ")");
            t = scan.nextInt();
            if (s > t) {
                throw new InputMismatchException("疏散点应该大于拥堵点");
            }
            if (s == t) {
                PrintUtils.println("不需要疏散");
                System.exit(0);
            }

        } catch (InputMismatchException ime) {
            PrintUtils.println("输入不合法：" + ime.getMessage());
            System.exit(1);
        }
        PrintUtils.println("开始计算疏散方案，请稍后");
        PrintUtils.println("========================");
//        List<List<PathBean>> pathss = new LinkedList<>();
//        int remain = sumFlow;
//        int step = 0;
//        int[][] srcCapacityMatrix = flowGraph.getSrcCapacityMatrix();
//        while (remain >= 0) {
//            if (step != 0) {
//                PrintUtils.print("等待" + step + "单位时间后，");
//            } else {
//                PrintUtils.print("当前");
//            }
//            // 当前路线
//            List<PathBean> pathBeanList = flowGraph.maxFlow(s, t);
//            if (pathBeanList.size() != 0) {
//                PrintUtils.println("可行通的疏通路线如下");
//            } else {
//                PrintUtils.println("无可通行路线");
//            }
//            pathss.add(pathBeanList);
//            // 打印路线
//            PrintUtils.printPaths(pathBeanList);
//            int currentFlow = MainUtils.getIncrementFlow(pathBeanList);
//            int[][] nextCapacityMatrix = srcCapacityMatrix;
//            for (int i = 0; i < pathss.size(); i++) {
//                nextCapacityMatrix = MainUtils.changeByPath(nextCapacityMatrix, pathss.get(i), step - i);
//            }
//            flowGraph = new FlowGraph(nextCapacityMatrix);
////            PrintUtils.printArray(flowGraph.getCapacityMatrix());
//            remain -= currentFlow;
//            if (remain > 0) {
//                PrintUtils.println("此时待规划疏通路线的车辆数目为" + remain);
//            } else {
//                PrintUtils.println("疏通路线规划完毕");
//            }
//            PrintUtils.println();
//            step++;
//        }
        PrintUtils.println("可行通的疏通路线如下");
        List<PathBean> pathBeanList  = flowGraph.maxFlow(s, t);
        PrintUtils.printPaths(pathBeanList);
        int currentFlow = MainUtils.getIncrementFlow(pathBeanList);

        int step = sumFlow / currentFlow;
        int remain = sumFlow % currentFlow;
        if (remain != 0) {
            step += 1;
        }
        if (step != 1) {
            PrintUtils.println("分" + step + "批进行疏通，每次可疏通" + currentFlow + "辆车");
        }

    }

}
