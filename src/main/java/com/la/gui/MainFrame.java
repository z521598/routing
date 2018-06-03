package com.la.gui;

import com.la.graph.FlowGraph;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2018/6/1.
 */
public class MainFrame extends JFrame {
    private JPanel topPanel = new JPanel();
    private JLabel titleLabel = new JLabel("X城区道路规划图");
    private JLabel imageLabel = new JLabel();
    private JPanel centerPanel = new JPanel();
    private JPanel sPanel = new JPanel();
    private JLabel sLabel = new JLabel("拥堵点：");
    private JTextField sTextField = new JTextField("0", 10);
    private JPanel carPanel = new JPanel();
    private JLabel carLabel = new JLabel("车辆数目");
    private JTextField carTextField = new JTextField(10);
    private JPanel tPanel = new JPanel();
    private JLabel tLabel = new JLabel("疏散点(若多个，请使用“,”逗号间隔)：");
    private JTextField tTextField = new JTextField(10);
    private JButton runButton = new JButton("开始计算方案");

    private JPanel bottomPanel = new JPanel();
    // 算法1
    private JPanel method1Panel = new JPanel();
    private JPanel method1LabelPanel = new JPanel();
    private JLabel method1Name = new JLabel("基于BFS遍历增广路径的FF算法");
    private JLabel method1TimeLabel = new JLabel("耗时");
    private JTextArea method1ConsoleTextArea = new JTextArea(10, 40);

    // 算法2
    private JPanel method2Panel = new JPanel();
    private JPanel method2LabelPanel = new JPanel();
    private JLabel method2Name = new JLabel("基于DFS遍历增广路径的FF算法");
    private JLabel method2TimeLabel = new JLabel("耗时");
    private JTextArea method2ConsoleTextArea = new JTextArea(10, 40);

    // 算法3
    private JPanel method3Panel = new JPanel();
    private JPanel method3LabelPanel = new JPanel();
    private JLabel method3Name = new JLabel("基于Dijkstra算法遍历增大容量增广路径的FF算法");
    private JLabel method3TimeLabel = new JLabel("耗时");
    private JTextArea method3ConsoleTextArea = new JTextArea(10, 40);


    // 结论
    private JPanel conclusionPanel = new JPanel();
    private JLabel conclusionLabel = new JLabel("结论：");
    private JTextArea conclusionTextArea = new JTextArea(7, 5);

    public MainFrame() throws HeadlessException {

        topPanel.add(titleLabel);
        imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("graph.png")));
        topPanel.add(imageLabel);
        add(topPanel, BorderLayout.NORTH);

        sPanel.add(sLabel);
        sPanel.add(sTextField);
        centerPanel.add(sPanel, BorderLayout.EAST);
        carPanel.add(carLabel);
        carPanel.add(carTextField);
        centerPanel.add(carPanel, BorderLayout.CENTER);
        tPanel.add(tLabel);
        tPanel.add(tTextField);
        centerPanel.add(tPanel, BorderLayout.WEST);
        runButton.addActionListener(new RunListener(this));
        centerPanel.add(runButton, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        bottomPanel.setLayout(new BorderLayout());
        method1Panel.setLayout(new BorderLayout());
        method1LabelPanel.add(method1Name, BorderLayout.CENTER);
        method1LabelPanel.add(method1TimeLabel, BorderLayout.EAST);
        method1Panel.add(method1LabelPanel, BorderLayout.NORTH);
        method1Panel.add(new JScrollPane(method1ConsoleTextArea), BorderLayout.CENTER);
        bottomPanel.add(method1Panel, BorderLayout.WEST);

        method2Panel.setLayout(new BorderLayout());
        method2LabelPanel.add(method2Name, BorderLayout.CENTER);
        method2LabelPanel.add(method2TimeLabel, BorderLayout.EAST);
        method2Panel.add(method2LabelPanel, BorderLayout.NORTH);
        method2Panel.add(new JScrollPane(method2ConsoleTextArea), BorderLayout.CENTER);
        bottomPanel.add(method2Panel, BorderLayout.CENTER);

        method3Panel.setLayout(new BorderLayout());
        method3LabelPanel.add(method3Name, BorderLayout.CENTER);
        method3LabelPanel.add(method3TimeLabel, BorderLayout.EAST);
        method3Panel.add(method3LabelPanel, BorderLayout.NORTH);
        method3Panel.add(new JScrollPane(method3ConsoleTextArea), BorderLayout.CENTER);
        bottomPanel.add(method3Panel, BorderLayout.EAST);

        conclusionPanel.setLayout(new BorderLayout());
        conclusionPanel.add(conclusionLabel, BorderLayout.NORTH);
        conclusionPanel.add(new JScrollPane(conclusionTextArea), BorderLayout.SOUTH);
        bottomPanel.add(conclusionPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setTitle("交通疏散规划");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public JTextField gettTextField() {
        return tTextField;
    }

    public JTextField getsTextField() {
        return sTextField;
    }

    public JTextField getCarTextField() {
        return carTextField;
    }

    public JTextArea getConclusionTextArea() {
        return conclusionTextArea;
    }

    public static int[][] defaultMatrix = new int[][]{
            new int[]{0, 10, 6, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 17, 0, 0, 10, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 13, 0, 0, 0, 20, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 14, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 9, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 10, 2, 15, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 13, 0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 5, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 10, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 7},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 10},
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public JLabel getMethod1TimeLabel() {
        return method1TimeLabel;
    }

    public JTextArea getMethod1ConsoleTextArea() {
        return method1ConsoleTextArea;
    }

    public JLabel getMethod2TimeLabel() {
        return method2TimeLabel;
    }

    public JTextArea getMethod2ConsoleTextArea() {
        return method2ConsoleTextArea;
    }

    public JLabel getMethod3TimeLabel() {
        return method3TimeLabel;
    }

    public JTextArea getMethod3ConsoleTextArea() {
        return method3ConsoleTextArea;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }


}
