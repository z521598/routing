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
                    .setMethod("bfs");

            Task dfsTask = new Task().setS(s).setT(t)
                    .setConsoleTextArea(mainFrame.getMethod2ConsoleTextArea())
                    .setMethodTimeLabel(mainFrame.getMethod2TimeLabel())
                    .setSumFlow(sumFlow).setFlowGraph(new FlowGraph(capacityMatrix))
                    .setMethod("dfs");

            bfsTask.call();
            try {
                dfsTask.call();
            }catch (Exception exx){
                exx.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }


    }
}
