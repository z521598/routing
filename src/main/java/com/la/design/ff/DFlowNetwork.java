package com.la.design.ff;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30.
 */
public class DFlowNetwork {
    // 点的数量
    private int V;
    // “每个点对应的边”的集合
    private List<DFlowEdge>[] adj;

    public DFlowNetwork(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList();
        }
    }

    public Iterable<DFlowEdge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public void addEdge(DFlowEdge e) {
        int v = e.from();
        adj[v].add(e);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < V; i++) {
            result += i + ":";
            for (DFlowEdge e : adj[i]) {
                result += " " + e.toString();
            }
            result += "\n";
        }
        return result;
    }
}
