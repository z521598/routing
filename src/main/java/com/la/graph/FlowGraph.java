package com.la.graph;

import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import java.util.*;

/**
 * Created by Administrator on 2018/5/31.
 */
public class FlowGraph {
    private static final int MAX = Integer.MAX_VALUE;
    // 表示点与点之间的关系和点的容量
    private int[][] capacityMatrix;

    private int[][] srcCapacityMatrix;
    // 点的数目
    private int number;
    // 源点
    private int s;
    // 汇点
    private int t;
    // 临时变量，增广路径
    private int[] path;


    public FlowGraph(int[][] capacityMatrix) {
        this.capacityMatrix = capacityMatrix;
        this.number = capacityMatrix.length;
        this.srcCapacityMatrix = MainUtils.deepCopy(capacityMatrix);
    }

    /**
     * @param s 源点
     * @param t 汇点
     * @return
     */
    public List<PathBean> maxFlow(int s, int t) {
        List<PathBean> pathBeanList = new ArrayList<>();
        this.s = s;
        this.t = t;
        int maxFlow = 0;
        // 获取流量
        int incrementFlow;
        while ((incrementFlow = dfs()) != -1) {
            maxFlow += incrementFlow;
            // 处理容量残余图
            int k = t;          // 利用前驱寻找路径

            List<Integer> path = new LinkedList<>();
            while (k != s) {
                path.add(k);
                int last = this.path[k];
                capacityMatrix[last][k] -= incrementFlow;  // 改变正向边的容量
                capacityMatrix[k][last] += incrementFlow;  // 改变反向边的容量
                k = last;
            }
            path.add(s);
            pathBeanList.add(new PathBean(path, incrementFlow));
        }
        return pathBeanList;
    }

    // 广度优先遍历，填充增广路，返回最大流量 TODO 获取流量最大的增广路
    private int dfs() {
        // 增广路径
        path = new int[number];
        for (int i = 0; i < path.length; i++) {
            path[i] = -1;
        }
        int[] flow = new int[number];

        // 记录节点是否访问过
        boolean[] visited = new boolean[number];

        Queue<Integer> dfsQueue = new LinkedList<>();
        // 源点显然为访问过
        visited[s] = true;

        // 访问过的节点入队,从起点开始遍历
        dfsQueue.add(s);
        // 设置起点的流为最大，为了后续比较寻找最小的流
        flow[s] = MAX;
        // 队列不为空则继续
        while (!dfsQueue.isEmpty()) {
            // 当前节点
            int current = dfsQueue.poll();
            // 若当前节点为汇点，则说明找到增广路径
            if (current == t) {
                break;
            }
            for (int i = 0; i < capacityMatrix.length; i++) {
                int capacity = capacityMatrix[current][i];
                // i != s    ===>   显然不能返回源点
                // capacity > 0     ===>  容量大于0，即为可达
                // visited[i] == false      ===>   该节点为被访问到 [这么写更清楚]
                if (i != s && capacity > 0 && visited[i] == false) {
                    // 记录前驱节点，即记录路径
                    path[i] = current;
                    // 各边容量的最小值即为此路径的最大流量
                    flow[i] = Math.min(capacity, flow[current]);
                    // 记录访问状态
                    visited[i] = true;
                    // 访问过的节点入队
                    dfsQueue.add(i);
                }

            }
        }
        // 若汇点未访问到则说明无增广路
        if (visited[t] == false) {
            return -1;
        } else {
            return flow[t];
        }
    }

    public int[][] getCapacityMatrix() {
        return capacityMatrix;
    }

    public int[][] getSrcCapacityMatrix() {
        return srcCapacityMatrix;
    }

    public int getNumber() {
        return number;
    }

    /**
     * @param pathBeanList 路线
     * @param step         第几步
     */

}