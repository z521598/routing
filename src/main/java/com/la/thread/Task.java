package com.la.thread;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/6/3.
 */
public class Task implements Callable<List<PathBean>> {
    private JLabel methodTimeLabel;
    private JTextArea consoleTextArea;
    private FlowGraph flowGraph;
    private int s;
    private int t;
    private int sumFlow;
    private String method;

    public JLabel getMethodTimeLabel() {
        return methodTimeLabel;
    }

    public Task setMethodTimeLabel(JLabel methodTimeLabel) {
        this.methodTimeLabel = methodTimeLabel;
        return this;
    }

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    public Task setConsoleTextArea(JTextArea consoleTextArea) {
        this.consoleTextArea = consoleTextArea;
        return this;
    }

    public FlowGraph getFlowGraph() {
        return flowGraph;
    }

    public Task setFlowGraph(FlowGraph flowGraph) {
        this.flowGraph = flowGraph;
        return this;
    }

    public int getS() {
        return s;
    }

    public Task setS(int s) {
        this.s = s;
        return this;
    }

    public int getT() {
        return t;
    }

    public Task setT(int t) {
        this.t = t;
        return this;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public Task setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public Task setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public List<PathBean> call() throws Exception {
        List<PathBean> pathBeanList = null;
        try {

            long start = System.currentTimeMillis();
            Thread.sleep(1);
            if ("bfs".equals(method)) {
                pathBeanList = flowGraph.maxFlowByBfs(s, t);
            } else if ("dfs".equals(method)) {
                pathBeanList = flowGraph.maxFlowByDfs(s, t);
            }

            long end = System.currentTimeMillis();
            long runTime = end - start;
            String res = PrintUtils.toString(pathBeanList);
            int currentFlow = MainUtils.getMaxFlow(pathBeanList);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    methodTimeLabel.setForeground(Color.red);
                    methodTimeLabel.setText("耗时" + runTime + "毫秒");
                    consoleTextArea.setText(res);
                }
            });
            int sumTime = 0;
            int step = sumFlow / currentFlow;
            int remain = sumFlow % currentFlow;
            double discuss = (double) sumFlow / (double) currentFlow;
            if (remain != 0) {
                step += 1;
            }
            if (sumFlow > currentFlow) {
                for (int i = 0; i < pathBeanList.size(); i++) {
                    PathBean pathBean = pathBeanList.get(i);
                    int time = pathBean.getPath().size() - 1;
                    sumTime += discuss * time;
                }
            }
            if (step != 1) {
                final int finalStep = step;
                final int finalSumTime = sumTime;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String txt = consoleTextArea.getText() + "\n" + "分" + finalStep + "批进行疏通，每次可疏通" + currentFlow + "辆车";
                        txt += ("\n车辆最大滞留时间为" + (new BigDecimal(discuss).setScale(2, RoundingMode.UP).doubleValue() - 1) + "个单位时间");
                        if (finalSumTime != 0) {
                            txt += ("\n所有车辆行驶时间之和为" + finalSumTime+"个单位时间");
                        }
                        consoleTextArea.setText(txt);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathBeanList;
    }
}
