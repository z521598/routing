package com.la.gui;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            FlowGraph flowGraph = mainFrame.getFlowGraph();
            int s = Integer.parseInt(mainFrame.getsTextField().getText());
            int t = Integer.parseInt(mainFrame.gettTextField().getText());
            int sumFlow = Integer.parseInt(mainFrame.getCarTextField().getText());


            JTextArea consoleTextArea = mainFrame.getConsoleTextArea();
            List<PathBean> pathBeanList = flowGraph.maxFlow(s, t);
            String res = PrintUtils.toString(pathBeanList);
            int currentFlow = MainUtils.getIncrementFlow(pathBeanList);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
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
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }


    }
}
