package com.la.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/5/31.
 */
public class PrintUtilsTest {

    @Test
    public void testPrintArray() throws Exception {
        int[][] capacity = new int[][]{
                new int[]{0, 1, 20, 10},
                new int[]{0, 0, 3, 1},
                new int[]{0, 0, 0, 20},
                new int[]{0, 0, 0, 0}
        };
        PrintUtils.printArray(capacity);
    }
}