package com.la.other;


import com.la.util.PrintUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class OtherMain {
    private static int sRandomSeed = 10;
    private static int lengthMaxRandomSeed = 500;
    private static int lengthMinRandomSeed = 100;
    private static int tMinNumber = 1;
    private static int tMaxNumber = 10;
    private static List<Map<String, Map<String, Object>>> resesList = new LinkedList<>();

    private static int dataSize = 10;
    private static CountDownLatch count = new CountDownLatch(dataSize);

    public static void main(String[] args) throws InterruptedException {
                    int length = new Random().nextInt(lengthMaxRandomSeed - lengthMinRandomSeed) + lengthMinRandomSeed;
                    int randomCapacitySeed = length;
                    int tNumber = new Random().nextInt(tMaxNumber - tMinNumber) + tMinNumber;
                    int[] ts = new int[tNumber];
                    int t;
                    for (int i = 0; i < ts.length; i++) {
                        ts[i] = new Random().nextInt(length) / 5 + length / 5;
                    }
                    if (ts.length == 1) {
                        t = ts[0];
                    } else {
                        t = (length + 1) - 1;
                    }
                    int[][] capacityMatrix = NewUtils.newRandomCapacityMatrix(length, randomCapacitySeed, ts);
                    int sumFlow = length * randomCapacitySeed;
                    int s = new Random().nextInt(sRandomSeed);
                    System.out.println("交通图的点数目:" + length);
                    System.out.println("道路的容量随机值：" + randomCapacitySeed);
                    System.out.println("拥堵点：" + s);
                    System.out.println("拥堵数量：" + sumFlow);
                    System.out.println("疏散点们:" + PrintUtils.toString(ts));
                    System.out.println("（超级）汇点:" + t);
                    System.out.println("交通容量图:");
                    PrintUtils.printArray(capacityMatrix);
                    ConclusionTask conclusionTask = new ConclusionTask()
                            .setS(s)
                            .setT(t).setCapacityMatrix(capacityMatrix)
                            .setSumFlow(sumFlow);

                    Map<String, Map<String, Object>> conResMap = conclusionTask.call();
                    System.out.println();
                    System.out.println(conResMap);


    }
}
