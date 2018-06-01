package com.la.gui;

import com.la.graph.FlowGraph;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2018/6/1.
 */
public class MainFrame extends JFrame {
    private FlowGraph flowGraph;
    private JTextArea flowGraphTextArea = new JTextArea();
    private JPanel centerPanel = new JPanel();
    private JPanel sPanel = new JPanel();
    private JLabel sLabel = new JLabel("拥堵点：");
    private JTextField sTextField = new JTextField("0", 10);
    private JPanel carPanel = new JPanel();
    private JLabel carLabel = new JLabel("车辆数目");
    private JTextField carTextField = new JTextField(10);
    private JPanel tPanel = new JPanel();
    private JLabel tLabel = new JLabel("疏散点：");
    private JTextField tTextField = new JTextField(10);
    private JButton runButton = new JButton("开始计算方案");
    private JTextArea consoleTextArea = new JTextArea(8, 8);

    public MainFrame(int[][] capacityMatrix) throws HeadlessException {
        flowGraphTextArea.setEditable(false);
        flowGraphTextArea.setText(PrintUtils.toString(capacityMatrix));
        add(flowGraphTextArea, BorderLayout.NORTH);

        sPanel.add(sLabel);
        sPanel.add(sTextField);
        centerPanel.add(sPanel, BorderLayout.EAST);
        carPanel.add(carLabel);
        carPanel.add(carTextField);
        centerPanel.add(carPanel,BorderLayout.CENTER);
        tPanel.add(tLabel);
        tPanel.add(tTextField);
        centerPanel.add(tPanel, BorderLayout.WEST);
        runButton.addActionListener(new RunListener(this));
        centerPanel.add(runButton, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        flowGraph = new FlowGraph(capacityMatrix);

        consoleTextArea.setEditable(false);
        add(new JScrollPane(consoleTextArea), BorderLayout.SOUTH);

        pack();
        setTitle("交通疏散规划");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public FlowGraph getFlowGraph() {
        return flowGraph;
    }

    public JTextField gettTextField() {
        return tTextField;
    }

    public JTextField getsTextField() {
        return sTextField;
    }

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    public JTextField getCarTextField() {
        return carTextField;
    }
}
