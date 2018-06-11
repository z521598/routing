package com.la.other;


import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OtherMain2 {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private static int sRandomSeed = 10;
    private static int lengthMaxRandomSeed = 500;
    private static int lengthMinRandomSeed = 100;
    private static int tMinNumber = 1;
    private static int tMaxNumber = 10;
    private static List<Map<String, Map<String, Object>>> resesList = new LinkedList<>();

    // 数据量
    private static int dataSize = 5;
    private static CountDownLatch count = new CountDownLatch(dataSize);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < dataSize; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
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
//                    System.out.println("交通图的点数目:" + length);
//                    System.out.println("道路的容量随机值：" + randomCapacitySeed);
//                    System.out.println("拥堵点：" + s);
//                    System.out.println("拥堵数量：" + sumFlow);
//                    System.out.println("疏散点们:" + PrintUtils.toString(ts));
//                    System.out.println("（超级）汇点:" + t);
//                    System.out.println("交通容量图:");
//                    PrintUtils.printArray(capacityMatrix);
                    ConclusionTask conclusionTask = new ConclusionTask()
                            .setS(s)
                            .setT(t).setCapacityMatrix(capacityMatrix)
                            .setSumFlow(sumFlow);

//                    Map<String, Map<String, Object>> conResMap = conclusionTask.call();
//                    System.out.println();
//                    System.out.println(conResMap);
                    resesList.add(conclusionTask.call());
                    count.countDown();
                }
            });
        }
        count.await();

        Map<String, Map<String, Integer>> map = new HashMap<>();
        for (Map<String, Map<String, Object>> resMap : resesList) {
            Set<Map.Entry<String, Map<String, Object>>> resSet = resMap.entrySet();
            for (Map.Entry<String, Map<String, Object>> mapEntry : resSet) {
                String res = mapEntry.getKey();
                String method = mapEntry.getValue().keySet().iterator().next();
                if (map.get(res) == null) {
                    map.put(res, new HashMap<String, Integer>());
                }
                Map<String, Integer> integerMap = map.get(res);
                if (integerMap.get(method) == null) {
                    integerMap.put(method, 0);
                }
                integerMap.put(method, integerMap.get(method) + 1);
            }
        }

//        System.out.println(map);
        // 格式化打印
        System.out.println("=============================================");
        System.out.println("数据总量：" + dataSize);
        Set<Map.Entry<String, Map<String, Integer>>> resesSet = map.entrySet();
        for (Map.Entry<String, Map<String, Integer>> mapEntry : resesSet) {
            String key = mapEntry.getKey();
            System.out.println("“" + key + "”对比维度：");
            Map<String, Integer> integerMap = mapEntry.getValue();
            for (Map.Entry<String, Integer> mapEntry2 : integerMap.entrySet()) {
                String key2 = mapEntry2.getKey();
                Integer value2 = mapEntry2.getValue();
                System.out.println("“" + key2 + "”算法最优的次数：" + value2);
            }
        }

        executor.shutdownNow();
    }
}
