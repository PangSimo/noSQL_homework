package com.bjtu.redis;

import java.util.*;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bjtu.redis.FileListener;

public class Test {

    private static CounterConfig cc;
    private static List<JedisOperation> counterList;
    private static FileAlterationMonitor monitor;
    // lock
    public static AtomicBoolean lock = new AtomicBoolean(false);

    private static void startObserver() throws Exception {
        String monitorDir = "src/main/resources";
        // 轮询间隔时间（1000）毫秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        FileAlterationObserver observer = new FileAlterationObserver(monitorDir);
        observer.addListener(new FileListener());
        // 创建文件变化监听器
        monitor = new FileAlterationMonitor(interval, observer);
        // 开始监听
        monitor.start();
        System.out.println("开始监听json变化");
    }

    public static void loadConfigJson() {
        cc = new CounterConfig();
        counterList = cc.init_config();
//        for(JedisOperation i : counterList){
//            System.out.println(i);
//        }
    }

    public static void printMenu1() {
        System.out.println("1.Counter列表\n2.选择Counter做increase操作\n3.延后过期时间");
    }

    public static void printCounters() {
        int i = 1;
        for (JedisOperation counter : counterList) {
            System.out.println("Counter" + i + " : " + counter);
            i++;
        }
    }

    public static void increase(int choise) {
        JedisOperation counter = counterList.get(choise - 1);
        if (counter instanceof NumCounter) {
            ((NumCounter) counter).incr();
        } else {
            ((FreqCounter) counter).incr();
        }
        System.out.println("increase 成功！");
    }

    public static void main(String[] args) throws Exception {
        startObserver();
        loadConfigJson();
        Scanner s = new Scanner(System.in);
        while (true) {
            printMenu1();
            switch (s.nextInt()) {
                case 1:
                    printCounters();
                    break;
                case 2:
                    System.out.println("请选择要increase的counter序号");
                    increase(s.nextInt());
                    break;
                case 3:
                    for (JedisOperation counter : counterList) {
                        counter.expireMore();
                    }
                    System.out.println("延后过期时间成功！");
                    break;
            }
        }
    }
}
