package com.la.graph.arrayShow;

import org.junit.Test;

/**
 * Created by Administrator on 2018/5/31.
 */
public class FlowGraphTest {
    int[][] capacity = new int[][]{
            new int[]{0, 1, 20, 10},
            new int[]{0, 0, 3, 1},
            new int[]{0, 0, 0, 20},
            new int[]{0, 0, 0, 0}
    };
    private FlowGraph flowGraph = new FlowGraph(capacity);


    @Test
    public void testMaxFlow() throws Exception {
        System.out.println(flowGraph.maxFlow(0, 3));
    }
}