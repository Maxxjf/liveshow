package com.qcloud.liveshow.enums;

import com.qcloud.liveshow.R;

/**
 * 类说明：表情分类
 * Author: Kuzan
 * Date: 2017/11/16 9:34.
 */
public enum EmojiClassifyEnum {
    /**最近使用*/
    Clock(0, "最近使用", R.drawable.icon_emoji_clock),
    /**笑脸*/
    Smile(1, "笑脸", R.drawable.icon_emoji_smile),
    /**动物*/
    Animal(2, "动物", R.drawable.icon_emoji_animal),
    /**活动*/
    Ball(3, "活动", R.drawable.icon_emoji_ball),
    /**食物*/
    Food(4, "食物", R.drawable.icon_emoji_food),
    /**旅游*/
    Travel(5, "旅游", R.drawable.icon_emoji_travel),
    /**旗帜*/
    Flag(6, "旗帜", R.drawable.icon_emoji_flag),
    /**删除*/
    Delete(7, "删除", R.drawable.icon_emoji_delete);

    private int key;
    private String value;
    private int res;

    EmojiClassifyEnum(int key, String value, int res) {
        this.key = key;
        this.value = value;
        this.res = res;
    }

    public static EmojiClassifyEnum valueOf(int key) {
        switch (key) {
            case 0:
                return Clock;
            case 1:
                return Smile;
            case 2:
                return Animal;
            case 3:
                return Ball;
            case 4:
                return Food;
            case 5:
                return Travel;
            case 6:
                return Flag;
            case 7:
                return Delete;
            default:
                return Clock;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public int getRes() {
        return this.res;
    }
}
