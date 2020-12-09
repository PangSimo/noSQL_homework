package com.bjtu.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisOperation {

    private Jedis jedis;

    public JedisOperation(){
        jedis = JedisInstance.getInstance().getResource();
    }

    public void setInt(String key, int value){
        String strValue = ""+value;
        jedis.setex(key,100,strValue);
    }
    public String get(String key){
        return jedis.get(key);
    }

    public void incr (String key,int times){
        for(int i = 0;i<times;i++) {
            jedis.incr(key);
        }
    }
    public void incr(String key,String field,int times){
        jedis.hincrBy(key,field,times);
    }


    public void setHash(String key,String field,String value){
        jedis.hset(key,field,value);
        jedis.expire(key,100);
    }

    public List<String> getHashField(String key, String... field){
        return jedis.hmget(key,field);
    }
    public Map<String ,String> hgetAll(String key){
        return jedis.hgetAll(key);
    }

    public void setStr(String key,String value){
        jedis.setex(key,100,value);
    }
    public void setList(String key, String... values){
        for(String value : values){
            jedis.lpush(key,value);
        }
        jedis.expire(key,100);
    }
    public void setSet(String key, String... values){
        for(String value : values){
            jedis.sadd(key,value);
        }
        jedis.expire(key,100);
    }
    public void expire(String key,int seconds){
        jedis.expire(key, seconds);
    }
    public List<String> getList(String key){
        return jedis.lrange(key,0,-1);
    }
    public Set<String> getSet(String key){
        return jedis.smembers(key);
    }
    public void setZSet(String key, String... values){
        for(int i = 0;i < values.length;i++){
            jedis.zadd(key,i,values[i]);
        }
        jedis.expire(key,100);
    }
    public Set<String> getZSet(String key){
        return jedis.zrangeByScore(key,Double.MIN_VALUE,Double.MAX_VALUE);
    }
    public long timeLeft(String key){
        return jedis.ttl(key);
    }
    public void expireMore(){}
}
