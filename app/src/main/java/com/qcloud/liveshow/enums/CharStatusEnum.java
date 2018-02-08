package com.qcloud.liveshow.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送状态
 */
public enum CharStatusEnum {
    SUCCESS(1,"发送成功"),
    FAIL(2,"发送失败"),
    INPROGRESS(3,"发送中"),
    IS_BLACK(4, "已被拉黑"),
    IS_BLOCKED(5, "已被禁言");

    private Integer key;
    private String name;

     CharStatusEnum(Integer key, String name){
        this.key = key;
        this.name = name;
    }
    
    public Integer getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static CharStatusEnum get(Integer key){
        for(CharStatusEnum b : CharStatusEnum.values()){
            if (b.getKey() == key) {
                return b;
            }
        }
        return FAIL;
    }
    
    public static Map<Integer, String>	getCharStuasEnumMap() {
    	Map<Integer, String> map = new HashMap<>();
    	for(CharStatusEnum b : CharStatusEnum.values()){
    		map.put(b.getKey(), b.getName());
    	}
    	return map;
    }
    
}
