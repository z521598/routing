package com.la.other;

import com.la.graph.FlowGraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class ConclusionTask implements Callable<Map<String, Map<String, Object>>> {

    public static String PATH_SIZE_CON = "路径数目";
    public static String SUM_TIME_CON = "疏散总时间";
    public static String PLAN_TIME_CON = "计划时间";

    private int s;
    private int t;
    private int sumFlow;
    private int[][] capacityMatrix;


    // xx维度下,xx算法最优，为多少
    @Override
    public Map<String, Map<String, Object>> call() {
        Map<String, Map<String, Object>> resMap = new HashMap<>();

        SingleArthTask bfsTask = new SingleArthTask().setS(s).setT(t)
                .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                .setMethod("bfs").setMethodName("基于BFS遍历增广路径的FF算法");

        SingleArthTask dfsTask = new SingleArthTask().setS(s).setT(t)
                .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                .setMethod("dfs").setMethodName("基于DFS遍历增广路径的FF算法");
        SingleArthTask dijTask = new SingleArthTask().setS(s).setT(t)
                .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                .setMethod("dij").setMethodName("基于Dijkstra算法遍历增大容量增广路径的FF算法");

        List<Map<String, Object>> resMapList = new LinkedList<>();
        resMapList.add(bfsTask.call());
        resMapList.add(dfsTask.call());
        resMapList.add(dijTask.call());


        // 比较行驶路径
        StringBuilder pathSizeCon = new StringBuilder();
        String lessPathSizeMethod = null;
        int lessPathSize = Integer.MAX_VALUE;
        for (int i = 0; i < resMapList.size(); i++) {
            Map<String, Object> map = resMapList.get(i);
            Integer pathSize = (Integer) map.get(SingleArthTask.PATH_SIZE);
            String methodName = (String) map.get(SingleArthTask.METHOD_NAME);
            System.out.println(methodName + "路径条数为" + pathSize);
            if (pathSize < lessPathSize) {
                lessPathSize = pathSize;
                lessPathSizeMethod = methodName;
            } else if (pathSize == lessPathSize) {
                lessPathSizeMethod += ("," + methodName);
            }
            pathSizeCon.append(methodName).append("疏通路径条数为").append(pathSize).append("。");
        }
        Map<String, Object> pathSizeMap = new HashMap<>();
        pathSizeMap.put(lessPathSizeMethod, lessPathSize);
        resMap.put(PATH_SIZE_CON, pathSizeMap);

        System.out.println();
        // 比较行驶时间
        StringBuilder sumTimeCon = new StringBuilder();
        String lessTimeMethod = null;
        double lessTime = Double.MAX_VALUE;
        for (int i = 0; i < resMapList.size(); i++) {
            Map<String, Object> map = resMapList.get(i);
            Double sumTime = (Double) map.get(SingleArthTask.SUM_TIME);
            String methodName = (String) map.get(SingleArthTask.METHOD_NAME);
            System.out.println(methodName + "行驶时间总量为" + sumTime);
            if (lessTime > sumTime) {
                lessTime = sumTime;
                lessTimeMethod = methodName;
            } else if (lessTime == sumTime) {
                lessTimeMethod += ("," + methodName);
            }
            sumTimeCon.append(methodName).append("行驶时间总量为").append(sumTime).append("。");
        }
        sumTimeCon.append("\n").append(lessTimeMethod).append("行驶时间总量最少").append("，司机走的路最少");
        Map<String, Object> sumTimeMap = new HashMap<>();
        sumTimeMap.put(lessTimeMethod, lessTime);
        resMap.put(SUM_TIME_CON, sumTimeMap);

        System.out.println();
        // 比较行驶时间
        StringBuilder runTimeCon = new StringBuilder();
        String runTimeLessMethod = null;
        long runLessTime = Long.MAX_VALUE;
        for (int i = 0; i < resMapList.size(); i++) {
            Map<String, Object> map = resMapList.get(i);
            long runTime = (long) map.get(SingleArthTask.RUN_TIME);
            String methodName = (String) map.get(SingleArthTask.METHOD_NAME);
            System.out.println(methodName + "规划时间为" + runTime);
            if (runLessTime > runTime) {
                runLessTime = runTime;
                runTimeLessMethod = methodName;
            } else if (runLessTime == runTime) {
                runTimeLessMethod += ("," + methodName);
            }
            runTimeCon.append(methodName).append("规划时间为").append(runTime).append("。");
        }
        Map<String, Object> runTimeMap = new HashMap<>();
        runTimeMap.put(runTimeLessMethod, runLessTime);
        resMap.put(PLAN_TIME_CON, runTimeMap);

        return resMap;
    }

    public int getS() {
        return s;
    }

    public ConclusionTask setS(int s) {
        this.s = s;
        return this;
    }

    public int getT() {
        return t;
    }

    public ConclusionTask setT(int t) {
        this.t = t;
        return this;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public ConclusionTask setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
        return this;
    }

    public int[][] getCapacityMatrix() {
        return capacityMatrix;
    }

    public ConclusionTask setCapacityMatrix(int[][] capacityMatrix) {
        this.capacityMatrix = capacityMatrix;
        return this;
    }
}
