package com.la.gui;

import com.la.graph.FlowGraph;
import com.la.thread.Task;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/1.
 */
public class RunListener implements ActionListener {
    private MainFrame mainFrame;

    public RunListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int[][] capacityMatrix;
            int t = 0;
            int s = Integer.parseInt(mainFrame.getsTextField().getText());
            String tString = mainFrame.gettTextField().getText();

            if (tString.contains(",")) { // 判断是否为多疏散点
                t = 21;
                int[][] tempCapacityMatrix = MainUtils.deepCopy(MainFrame.defaultMatrix);
                capacityMatrix = new int[22][];
                for (int i = 0; i < tempCapacityMatrix.length; i++) {
                    capacityMatrix[i] = Arrays.copyOf(tempCapacityMatrix[i], 22);
                }
                // 获取全部汇点
                String[] tsStrs = tString.split(",");
                int[] ts = new int[tsStrs.length];
                for (int i = 0; i < ts.length; i++) {
                    ts[i] = Integer.parseInt(tsStrs[i]);
                }
                capacityMatrix[21] = new int[22];
                // 更新超级汇点带来的二维容量数组的变化
                for (int i = 0; i < ts.length; i++) {
                    int originT = ts[i];
                    capacityMatrix[originT][21] = Integer.MAX_VALUE;
                }
            } else {
                t = Integer.parseInt(tString);
                capacityMatrix = MainFrame.defaultMatrix;
            }
            int sumFlow = Integer.parseInt(mainFrame.getCarTextField().getText());
            PrintUtils.printArray(capacityMatrix);
            Task bfsTask = new Task().setS(s).setT(t)
                    .setConsoleTextArea(mainFrame.getMethod1ConsoleTextArea())
                    .setMethodTimeLabel(mainFrame.getMethod1TimeLabel())
                    .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                    .setMethod("bfs").setMethodName("基于BFS遍历增广路径的FF算法");

            Task dfsTask = new Task().setS(s).setT(t)
                    .setConsoleTextArea(mainFrame.getMethod2ConsoleTextArea())
                    .setMethodTimeLabel(mainFrame.getMethod2TimeLabel())
                    .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                    .setMethod("dfs").setMethodName("基于DFS遍历增广路径的FF算法");
            Task dijTask = new Task().setS(s).setT(t)
                    .setConsoleTextArea(mainFrame.getMethod3ConsoleTextArea())
                    .setMethodTimeLabel(mainFrame.getMethod3TimeLabel())
                    .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                    .setMethod("dij").setMethodName("基于Dijkstra算法遍历增大容量增广路径的FF算法");

            try {
                java.util.List<Map<String, Object>> resMapList = new LinkedList<>();
                resMapList.add(bfsTask.call());
                resMapList.add(dfsTask.call());
                resMapList.add(dijTask.call());
                // 比较行驶路径
                StringBuilder pathSizeCon = new StringBuilder();
                String lessPathSizeMethod = null;
                int lessPathSize = Integer.MAX_VALUE;
                for (int i = 0; i < resMapList.size(); i++) {
                    Map<String, Object> map = resMapList.get(i);
                    Integer pathSize = (Integer) map.get(Task.PATH_SIZE);
                    String methodName = (String) map.get(Task.METHOD_NAME);
                    if (pathSize < lessPathSize) {
                        lessPathSize = pathSize;
                        lessPathSizeMethod = methodName;
                    } else if (pathSize == lessPathSize) {
                        lessPathSizeMethod += ("," + methodName);
                    }
                    pathSizeCon.append(methodName).append("疏通路径条数为").append(pathSize).append("。");
                }
                pathSizeCon.append("\n").append(lessPathSizeMethod).append("路径条数最少").append("，最利于指挥疏散。");

                // 比较行驶时间
                StringBuilder sumTimeCon = new StringBuilder();
                String lessTimeMethod = null;
                int lessTime = Integer.MAX_VALUE;
                for (int i = 0; i < resMapList.size(); i++) {
                    Map<String, Object> map = resMapList.get(i);
                    Integer sumTime = (Integer) map.get(Task.SUM_TIME);
                    String methodName = (String) map.get(Task.METHOD_NAME);
                    if (lessTime > sumTime) {
                        lessTime = sumTime;
                        lessTimeMethod = methodName;
                    } else if (lessTime == sumTime) {
                        lessTimeMethod += ("," + methodName);
                    }
                    sumTimeCon.append(lessTimeMethod).append("行驶时间总量为").append(sumTime).append("。");
                }
                sumTimeCon.append("\n").append(lessTimeMethod).append("行驶时间总量最少").append("，司机走的路最少");


                // 比较行驶时间
                StringBuilder runTimeCon = new StringBuilder();
                String runTimeLessMethod = null;
                long runLessTime = Long.MAX_VALUE;
                for (int i = 0; i < resMapList.size(); i++) {
                    Map<String, Object> map = resMapList.get(i);
                    long runTime = (long) map.get(Task.RUN_TIME);
                    String methodName = (String) map.get(Task.METHOD_NAME);
                    if (runLessTime > runTime) {
                        runLessTime = runTime;
                        runTimeLessMethod = methodName;
                    } else if (runLessTime == runTime) {
                        runTimeLessMethod += ("," + methodName);
                    }
                    runTimeCon.append(methodName).append("规划时间为").append(runTime).append("。");
                }
                runTimeCon.append("\n").append(runTimeLessMethod).append("规划时间最少").append("，能够最快得到解决拥堵的方案");


                // 结论
                mainFrame.getConclusionTextArea().setText(pathSizeCon.toString() + "\n" + sumTimeCon.toString() + "\n" + runTimeCon.toString());

            } catch (Exception exx) {
                exx.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }


    }
}
