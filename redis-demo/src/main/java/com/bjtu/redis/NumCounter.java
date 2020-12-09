package com.bjtu.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumCounter extends JedisOperation{

    private int incrNumber = 1;
    private String counterName;
    private String counterID;
    private int initValue = 5;
    private int expireTime = 100;


    public NumCounter(int incrNumber,String counterName,String counterID,int initValue,int expireTime){
        super();
        this.incrNumber = incrNumber;
        this.counterName = counterName;
        this.counterID = counterID;
        this.initValue = initValue;
        this.expireTime = expireTime;
        initCounter();
    }
    public void expireMore(){
        long timeLeft = super.timeLeft(counterID);
        super.expire(counterID,(int)timeLeft+100);
    }

    private void initCounter(){
        super.setInt(counterID,initValue);
    }

    public void incr(){
        super.incr(counterID,incrNumber);
    }

    public String getNum(){
        return super.get(counterID);
    }

    public String getCounterID() {
        return counterID;
    }

    public void setCounterID(String counterID) {
        this.counterID = counterID;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public int getIncrNumber() {
        return incrNumber;
    }

    public void setIncrNumber(int incrNumber) {
        this.incrNumber = incrNumber;
    }

    public int getInitValue() {
        return initValue;
    }

    public void setInitValue(int initValue) {
        this.initValue = initValue;
    }

    public String toString(){
        String number = getNum();
        if(number == null){
            number = "counter 已过期";
        }
        return "CounterName: "+counterName+"\nCounterID: "+counterID+"\nValue: "+number+
                "\nCounterType: number\nIncrease number: "+incrNumber+"\nExpiration time: "+super.timeLeft(counterID)+"!!\n";
    }
}
