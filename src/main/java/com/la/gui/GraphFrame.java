package com.la.gui;

import com.la.util.MainUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2018/6/2.
 */
@Deprecated
public class GraphFrame extends JFrame {
    private JLabel tipLabel = new JLabel("使用二维数组代表道路，数字为道路的容量，不通路则为0");
    private String defaultMatrix = "0 1 2 3\n" +
            "0 0 3 0\n" +
            "0 0 1 1\n" +
            "0 0 0 0";
    private JTextArea graphTextArea = new JTextArea(defaultMatrix, 8, 8);
    private JButton confrimButton = new JButton("确定");

    public GraphFrame() throws HeadlessException {
        add(tipLabel, BorderLayout.NORTH);
        add(graphTextArea, BorderLayout.CENTER);
        add(confrimButton, BorderLayout.SOUTH);
        confrimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String graphMatrixString = graphTextArea.getText();
                    int[][] capacityMatrix = MainUtils.convertToArray(graphMatrixString);
                    graphFrame.dispose();
                    new MainFrame();
                } catch (Exception ex) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "请检查数据格式", "解析失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setLocationRelativeTo(null);
        pack();
        setTitle("请输入道路");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static GraphFrame graphFrame;

    public static void main(String[] args) {
        graphFrame = new GraphFrame();
    }
}
