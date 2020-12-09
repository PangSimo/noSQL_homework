package com.bjtu.redis;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FreqCounter extends JedisOperation{
    private int incrNumber = 1;
    private String counterName;
    private String counterID;
    private HashMap<String,Integer> initValue;
    private int expireTime = 100;

    public FreqCounter(int incrNumber,String counterName,String counterID,HashMap<String,Integer> initValue,int expireTime){
        super();
        this.incrNumber = incrNumber;
        this.counterName = counterName;
        this.counterID = counterID;
        this.initValue = initValue;
        initCounter();
    }
    private void initCounter(){
        for(String key : initValue.keySet()){
            super.setHash(counterID,key,initValue.get(key).toString());
        }
    }
    public void expireMore(){
        long timeLeft = super.timeLeft(counterID);
        super.expire(counterID,(int)timeLeft+100);
    }
    public void incr(){
        super.incr(counterID,new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()),incrNumber);
    }
    public void incr(String time){
        super.incr(counterID,time,incrNumber);
    }

    public String getCountAtTime(String time){
        return super.getHashField(counterID,time).get(0);
    }

    public Map<String,String> getAllFieldsAndValues(){
        return super.hgetAll(counterID);
    }

    public int getIncrNumber() {
        return incrNumber;
    }

    public void setIncrNumber(int incrNumber) {
        this.incrNumber = incrNumber;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterID() {
        return counterID;
    }

    public void setCounterID(String counterID) {
        this.counterID = counterID;
    }

    public HashMap<String, Integer> getInitValue() {
        return initValue;
    }

    public void setInitValue(HashMap<String, Integer> initValue) {
        this.initValue = initValue;
    }
    public String toString(){
        String allData = "";
        Map<String, String> fieldsAndValues = getAllFieldsAndValues();
        for(String key : fieldsAndValues.keySet()){
            allData += key+" : "+fieldsAndValues.get(key)+"\n\t";
        }
        if(allData.length()>=2){
            allData = allData.substring(0,allData.length()-2);
        }else{
            allData = "counter 已过期";
        }
        return "CounterName: "+counterName+"\nCounterID: "+counterID+"\nValue: \n\t"+allData+
                "\nCounterType: freq\nIncrease number: "+incrNumber+"\nExpiration time: "+super.timeLeft(counterID)+"!!\n";
    }
}
