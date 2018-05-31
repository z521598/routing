package com.la.design.ek;

import com.la.util.PrintUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2018/5/31.
 */
public class EdmodKarp {
    int maxdata = Integer.MAX_VALUE;
    // 使用数组来表示图，值代表容量
    int[][] capacity;
    int[] flow;
    // 前驱节点，来表示路径
    int[] pre;
    // 点的数量
    int n;
    Queue<Integer> queue;

    public EdmodKarp(int[][] capacity) {
        this.capacity = capacity;
        this.n = capacity.length;
        this.pre = new int[n];
    }

    // BFS广度优先遍历的查找一条src到des的路径
    int BFS(int src, int des) {
        int i;
        this.queue = new LinkedList<Integer>();

        this.flow = new int[n];

        // 全部标记为未可达
        for (i = 0; i < n; ++i) {
            pre[i] = -1;
        }
        pre[src] = -2;
        flow[src] = maxdata;
        queue.add(src);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            //+ des节点被放入队列并pop出队列，说明有增广路径。
            if (current == des)            //找到了增广路径
                break;
            //+ 遍历当前节点到其他节点的距离
            for (i = 0; i < n; i++) {
                // 找到非源节点未被访问过的可达结点，计算其流量
                //+ capacity[current][i] 意为当前节点到其他节点的边的容量
                if (i != src && capacity[current][i] > 0 && pre[i] == -1) {
                    pre[i] = current; // 记录前驱
                    //+ 记录当前增广路径的可注入的流量
                    flow[i] = Math.min(capacity[current][i], flow[current]);   // 关键：迭代的找到增量
                    queue.add(i);
                }
            }
        }
        PrintUtils.printArray(pre);
        // 说明目标节点不可达
        if (pre[des] == -1)      //残留图中不再存在增广路径
            return -1;
        else
            return flow[des];

    }

    int maxFlow(int src, int des) {
        int increasement = 0;
        int sumflow = 0;
        while ((increasement = BFS(src, des)) != -1) {
            System.out.println("----------------------");
            int k = des;          // 利用前驱寻找路径
            while (k != src) {
                System.out.print(k + "->");
                int last = pre[k];
                // 一增加，一减少
                capacity[last][k] -= increasement; //改变正向边的容量
                capacity[k][last] += increasement; //改变反向边的容量
                k = last;
            }
            System.out.print(src);
            System.out.println(" " + increasement);
            System.out.println("-------改变后---------");
            for (int j = 0; j < n; j++) {
                for (int x = 0; x < n; x++) {
                    System.out.print("---" + capacity[j][x]);
                }
                System.out.println();
            }
            sumflow += increasement;
        }
        return sumflow;
    }

    public static void main(String args[]) {
        int[][] matrix = new int[4][4];
        matrix[0][1] = 4;
        matrix[0][3] = 2;
        matrix[1][2] = 3;
        matrix[1][3] = 2;
        matrix[2][3] = 1;

        EdmodKarp edm = new EdmodKarp(matrix);
        System.out.println("-------初始化---------");
        for (int j = 0; j < edm.n; j++) {
            for (int k = 0; k < edm.n; k++) {
                System.out.print("---" + edm.capacity[j][k]);
            }
            System.out.println();
        }
        int actual = edm.maxFlow(0, 3);
        int expected = 5;
        System.out.println("-------最终结果---------");
        for (int j = 0; j < edm.n; j++) {
            for (int k = 0; k < edm.n; k++) {
                System.out.print("---" + edm.capacity[j][k]);
            }
            System.out.println();
        }
        System.out.println(actual);
    }

}