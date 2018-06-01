package com.la.util;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import org.junit.Test;
import sun.security.tools.PathList;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/6/1.
 */
public class MainUtilsTest {

    @Test
    public void testChangeByPath() throws Exception {
        int[][] capacity = new int[][]{
                new int[]{0, 1, 20, 10},
                new int[]{0, 0, 3, 1},
                new int[]{0, 0, 0, 20},
                new int[]{0, 0, 0, 0}
        };
        int s = 0;
        int t = 3;
        FlowGraph flowGraph = new FlowGraph(capacity);
        PrintUtils.printArray(capacity);
        List<PathBean> pathList = flowGraph.maxFlow(s, t);
        int[][] next = MainUtils.changeByPath(flowGraph.getSrcCapacityMatrix(),pathList,1);
        PrintUtils.printArray(next);
        int[][] next2 = MainUtils.changeByPath(flowGraph.getSrcCapacityMatrix(),pathList,2);
        PrintUtils.printArray(next2);

    }

}