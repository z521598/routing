package com.la.graph;

import com.la.path.PathBean;
import com.la.util.PrintUtils;
import org.junit.Test;

import java.util.List;

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

    }


    @Test
    public void testGetSrcCapacityMatrix() throws Exception {
        PrintUtils.printArray(capacity);
        List<PathBean> pathBeanList = flowGraph.maxFlowByBfs(0, 3);
        System.out.println("===========================");
        PrintUtils.printArray(flowGraph.getSrcCapacityMatrix());

    }

    @Test
    public void testMaxFlowByFlowFirst() throws Exception {
        int[][] cap = new int[][]{
                new int[]{0, 100, 2, 0},
                new int[]{0, 0, 0, 1},
                new int[]{0, 0, 0, 2},
                new int[]{0, 0, 0, 0}
        };
        List<PathBean> pathBeanList = new FlowGraph(cap).maxFlowByFlowFirst(0, 3);
        PrintUtils.printPaths(pathBeanList);
    }

    @Test
    public void testDijstraGetMaxFlow2() throws Exception {
        int[][] cap = new int[][]{
                new int[]{0, 100, 2, 0},
                new int[]{0, 0, 0, 1},
                new int[]{0, 0, 0, 2},
                new int[]{0, 0, 0, 0}
        };
        FlowGraph flowGraph = new FlowGraph(cap);
        int flow = flowGraph.dijstraGetMaxFlow(0, 3);
        System.out.println();
        System.out.println(flow);
    }
}