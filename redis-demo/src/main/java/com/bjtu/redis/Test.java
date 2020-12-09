package com.bjtu.redis;
import java.util.*;

public class Test {
    public static void printMenu1(){
        System.out.println("1.Counter列表\n2.选择Counter做increase操作\n3.延后过期时间");
    }
    public static void printCounters(List<JedisOperation>counterList){
        int i = 1;
        for(JedisOperation counter : counterList){
            System.out.println("Counter"+i+" : "+counter);
            i++;
        }
    }
    public static void increase(List<JedisOperation> counters,int choise){
        JedisOperation counter = counters.get(choise-1);
        if(counter instanceof NumCounter){
            ((NumCounter) counter).incr();
        }else{
            ((FreqCounter)counter).incr();
        }
        System.out.println("increase 成功！");
    }
    public static void main(String[]args){
        //Counter counter = new Counter();
//        NumCounter numC = new NumCounter(5,"PANG","01",0,100);
//        System.out.println(numC.getNum());
//        numC.incr();
//        System.out.println(numC.getNum());
//        HashMap<String,Integer> va = new HashMap<String, Integer>();
//        va.put("2020-12-08-17",2);
//        va.put("2020-11-8-17",2);
//        FreqCounter fc = new FreqCounter(5,"PANG","02",va,100);
//        System.out.println(fc.getCountAtTime(new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date())));
//        System.out.println(fc.getCountAtTime("2020-11-8-17"));
//        fc.incr();
//        System.out.println(fc.getCountAtTime(new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date())));
//        fc.incr("2020-11-8-17");
//        System.out.println(fc.getCountAtTime("2020-11-8-17"));

        //counter.setList("haha",a);

//        CounterConfig cc = new CounterConfig();
//        List<JedisOperation> list = cc.init_config();
//        NumCounter c1 = (NumCounter)list.get(0);
//        System.out.println(c1.getNum());
//        c1.incr();
//        System.out.println(c1.getNum());
//        NumCounter c2 = (NumCounter)list.get(1);
//        System.out.println(c2.getNum());
//        c2.incr();
//        System.out.println(c2.getNum());
//        FreqCounter f1 = (FreqCounter)list.get(2);
        //System.out.println(f1.);
        //System.out.println(cc.ReadFile());
        CounterConfig cc = new CounterConfig();
        List<JedisOperation> counterList = cc.init_config();
        Scanner s = new Scanner(System.in);
        while(true){
            printMenu1();
            switch (s.nextInt()){
                case 1: printCounters(counterList);break;
                case 2:
                    System.out.println("请选择要increase的counter序号");
                    increase(counterList,s.nextInt());break;
                case 3:
                    for(JedisOperation counter: counterList){
                        counter.expireMore();
                    }
                    System.out.println("延后过期时间成功！");break;
            }
        }
    }
}
