package com.la.util;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<PathBean> pathList = flowGraph.maxFlowByBfs(s, t);
        int[][] next = MainUtils.changeByPath(flowGraph.getSrcCapacityMatrix(), pathList, 1);
        PrintUtils.printArray(next);
        int[][] next2 = MainUtils.changeByPath(flowGraph.getSrcCapacityMatrix(), pathList, 2);
        PrintUtils.printArray(next2);

    }

    @Test
    public void testSumTime() throws Exception {
        List<PathBean> pathBeanList = new ArrayList<>();
        PathBean pathBean = new PathBean(Arrays.asList(new Integer[]{0, 3, 6}), 4);
        PathBean pathBean2 = new PathBean(Arrays.asList(new Integer[]{0, 4, 6}), 1);
        PathBean pathBean3 = new PathBean(Arrays.asList(new Integer[]{0, 3, 4, 6}), 5);
        PathBean pathBean4 = new PathBean(Arrays.asList(new Integer[]{0, 6}), 5);
        pathBeanList.add(pathBean);
        pathBeanList.add(pathBean2);
        pathBeanList.add(pathBean3);
        pathBeanList.add(pathBean4);
        MainUtils.sumTime(100, pathBeanList);

    }
}