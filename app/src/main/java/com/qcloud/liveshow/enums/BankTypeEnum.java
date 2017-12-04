package com.qcloud.liveshow.enums;

import java.util.HashMap;
import java.util.Map;

public enum BankTypeEnum  {
    ABC(1,"中国农业银行"),
    ICBC(2,"中国工商银行"),
    BOC(3,"中国银行"),
    CMB(4,"招商银行"),
    BCM(5,"交通银行"),
    CCB(6,"中国建设银行");
    
    private Integer key;
    private String name;
    
     BankTypeEnum(Integer key,String name){
        this.key = key;
        this.name = name;
    }
    
    public Integer getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static BankTypeEnum get(Integer key){
        for(BankTypeEnum b : BankTypeEnum.values()){
            if (b.getKey() == key) {
                
                return b;
            }
            
        }
        return ABC;
        
    }
    
    public static Map<Integer, String>	getBankTypeEnumMap() {
    	Map<Integer, String> map = new HashMap<>();
    	for(BankTypeEnum b : BankTypeEnum.values()){
    		map.put(b.getKey(), b.getName());
    	}
    	return map;
    }
    
}
