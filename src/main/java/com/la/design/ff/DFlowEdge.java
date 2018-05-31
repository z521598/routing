package com.la.design.ff;

/**
 * Created by Administrator on 2018/5/30.
 */
public class DFlowEdge {
    private int v;
    private int w;
    // 容量
    private int capacity;
    // 流量
    private int flow;

    public DFlowEdge(int v, int w, int capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }


    public int otherNode(int v) {
        if (v == this.v) {
            return this.w;
        } else {
            return this.v;
        }
    }

    // 返回能够增加的最大流量
    //+ 正数代表可以增流，非正数代表不能增流
    public int residualCapacityTo(int v) {
        if (v == this.v) {
            return flow;
        } else {
            return capacity - flow;
        }
    }

    // 增加这条边的流量
    public void addResidualFlowTo(int v, int d) {
        if (v == this.v) {
            flow -= d;
        } else {
            flow += d;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DFlowEdge{");
        sb.append("v=").append(v);
        sb.append(", w=").append(w);
        sb.append(", capacity=").append(capacity);
        sb.append(", flow=").append(flow);
        sb.append('}');
        return sb.toString();
    }
}
