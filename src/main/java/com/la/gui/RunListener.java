package com.la.gui;

import com.la.Main;
import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

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
            // 得到容量图
            FlowGraph flowGraph = new FlowGraph(capacityMatrix);

            // 多算法，同时跑
            // TODO: 2018/6/3
            JLabel method1Label = mainFrame.getMethod1TimeLabel();
            JTextArea consoleTextArea = mainFrame.getMethod1ConsoleTextArea();
            long start = System.currentTimeMillis();
            List<PathBean> pathBeanList = flowGraph.maxFlow(s, t);
            long end = System.currentTimeMillis();
            long time = end - start;

            String res = PrintUtils.toString(pathBeanList);
            int currentFlow = MainUtils.getIncrementFlow(pathBeanList);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    method1Label.setForeground(Color.red);
                    method1Label.setText(method1Label.getText() + time + "毫秒");
                    consoleTextArea.setText(res);
                }
            });
            int step = sumFlow / currentFlow;
            int remain = sumFlow % currentFlow;
            if (remain != 0) {
                step += 1;
            }
            if (step != 1) {
                final int finalStep = step;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        consoleTextArea.setText(consoleTextArea.getText() + "\n" + "分" + finalStep + "批进行疏通，每次可疏通" + currentFlow + "辆车"
                        );
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }


    }
}
