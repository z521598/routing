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
}