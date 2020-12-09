package com.bjtu.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterConfig {

    private List<JedisOperation> counters;

    public List<JedisOperation> init_config() {
        counters = new ArrayList<JedisOperation>();
        String path ="src/main/resources/jsonFiles/config.json";
        Gson gson = new Gson();

        Map jo = gson.fromJson(ReadFile(path),Map.class);
        List configList = (List) jo.get("counterConfig");
        Map<String ,Object> config = null;
        for(int i = 0; i< configList.size();i++){
            config = (Map<String, Object>) configList.get(i);
            String type = (String)config.get("type");
            if(type.equals("num")){
                NumCounter nc = new NumCounter(Integer.parseInt((String)config.get("incrValue")),(String)config.get("counterName"),
                        (String)config.get("counterIndex"),Integer.parseInt((String)config.get("initValue")),100);
                counters.add(nc);
            }else{
                HashMap<String,Integer> initValue = new HashMap<String,Integer>();
                Map<String,String> valueInJson = (Map<String, String>) config.get("initValue");
                for(String key : (valueInJson.keySet())){
                    initValue.put(key,Integer.parseInt(valueInJson.get(key)));
                }
                FreqCounter fc = new FreqCounter(Integer.parseInt((String) config.get("incrValue")),(String) config.get("counterName"),
                        (String)config.get("counterIndex"),initValue,100);
                counters.add(fc);
            }
        }
        System.out.println("读取文件成功！");
        return counters;
    }

    public String ReadFile(String path) {
        //String path = this.getClass().getClassLoader().getResource("./jsonFiles/config.json").getPath();
        String laststr = "";
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //int line=1;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line"+line+":"+tempString);
                laststr = laststr + tempString;
                //line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                }
            }
        }
        return laststr;
    }
}
