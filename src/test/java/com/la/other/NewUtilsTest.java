package com.la.other;

import com.la.util.PrintUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewUtilsTest {

    @Test
    public void newRandomCapacityMatrix() {
        int[][] caps = NewUtils.newRandomCapacityMatrix(100,100,new int[]{98,99});
        PrintUtils.printArray(caps);
    }
}