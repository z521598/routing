package com.la.graph.objShow;

import com.la.util.StdRandom;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2018/5/30.
 */
public class FordFulkerson {
    private FlowEdge[] edgeTo;
    private int value;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        System.out.println("==========================");
        String result = "";
        System.out.println(result);
        // 一直增加流量直到无法再增加为止
        while (hasAugmentingPath(G, s, t)) {
            // 找出增广路的瓶颈
            int bottle = Integer.MAX_VALUE;
            int v = t;
            while (v != s) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
                v = edgeTo[v].otherNode(v);
            }

            // 增加整条路径的流量
            v = t;
            System.out.print("路线(");
            while (v != s) {
                System.out.print(v + "->");
                edgeTo[v].addResidualFlowTo(v, bottle);
                v = edgeTo[v].otherNode(v);
            }
            System.out.print(s);
            System.out.print(")允许通过" + bottle + "车通过");
            System.out.println();
            // 最大流增加
            value += bottle;
        }
    }

    public int value() {
        return value;
    }

    // 判断是否有增广路
    // 有增广路的条件就是存在一条路径，这条路径上所有的边都能增加流量。
    //+ 残余图
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()]; // 注意，这句话是必须要有的。因为每次增广路径都不一样。
        boolean[] visited = new boolean[G.V()];

        // BFS 广度优先遍历
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true; // 注意：这句话不要遗漏
        while (!q.isEmpty()) {
            int v = q.poll();
            // 能够通过的条件是流量能够增加
            for (FlowEdge e : G.adj(v)) {
                int w = e.otherNode(v);
                if (e.residualCapacityTo(w) > 0 && !visited[w]) {
                    edgeTo[w] = e;
                    q.add(w);
                    visited[w] = true;
                }
            }
        }

        // 有增广路的条件就是S点能够到达T点。
        return visited[t];
    }

    public static void main(String[] argv) {
        FlowNetwork g = new FlowNetwork(4);
        int[] data = {0, 1, r(), 0, 2, r(), 2, 1, r(), 1, 3, r(), 2, 3, r(), 0, 3, r()};
        for (int i = 0; i < data.length; i += 3) {
            FlowEdge flowEdge = new FlowEdge(data[i], data[i + 1], data[i + 2]);
            g.addEdge(flowEdge);
        }
        System.out.println(g);
        System.out.println();
        System.out.println((new FordFulkerson(g, 0, 3).value()));
    }

    private static int r() {
        return StdRandom.uniform(1000);
    }
}
