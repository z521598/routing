package com.la.path;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */
public class PathBean {
    private List<Integer> path = new LinkedList<>();
    private Integer flow;

    public PathBean(List<Integer> path, Integer flow) {
        this.path = path;
        this.flow = flow;
    }

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }
}
