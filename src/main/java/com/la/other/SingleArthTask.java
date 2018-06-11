package com.la.other;

import com.la.graph.FlowGraph;
import com.la.path.PathBean;
import com.la.util.MainUtils;
import com.la.util.PrintUtils;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/6/3.
 */
public class SingleArthTask implements Callable<Map<String, Object>> {
    private String methodName;
    public final static String METHOD_NAME = "methodName";
    public final static String RUN_TIME = "runTime";
    public final static String SUM_TIME = "sumTime";
    public final static String PATH_SIZE = "pathSize";
    private int s;
    private int t;
    private int sumFlow;
    private String method;
    private FlowGraph flowGraph;

    public String getMethodName() {
        return methodName;
    }

    public SingleArthTask setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }


    public FlowGraph getFlowGraph() {
        return flowGraph;
    }

    public SingleArthTask setFlowGraph(FlowGraph flowGraph) {
        this.flowGraph = flowGraph;
        return this;
    }

    public int getS() {
        return s;
    }

    public SingleArthTask setS(int s) {
        this.s = s;
        return this;
    }

    public int getT() {
        return t;
    }

    public SingleArthTask setT(int t) {
        this.t = t;
        return this;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public SingleArthTask setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public SingleArthTask setMethod(String method) {
        this.method = method;
        return this;
    }


    @Override
    public Map<String, Object> call() {
        Map<String, Object> map = new HashMap<>();
        map.put(SingleArthTask.METHOD_NAME, methodName);
        List<PathBean> pathBeanList = null;
        long start = System.currentTimeMillis();
        if ("bfs".equals(method)) {
            pathBeanList = flowGraph.maxFlowByBfs(s, t);
        } else if ("dfs".equals(method)) {
            pathBeanList = flowGraph.maxFlowByDfs(s, t);
        } else if ("dij".equals(method)) {
            pathBeanList = flowGraph.maxFlowByFlowFirst(s, t);
        }

        map.put(PATH_SIZE, pathBeanList.size());
        long end = System.currentTimeMillis();
        long runTime = end - start;
        map.put(RUN_TIME, runTime);
        // 路径的字符串表示
        // String res = PrintUtils.toString(pathBeanList);
        int currentFlow = MainUtils.getMaxFlow(pathBeanList);
        double sumTime = NewUtils.getSumTime(sumFlow,currentFlow,pathBeanList);
        map.put(SUM_TIME, sumTime);
        return map;
    }
}
