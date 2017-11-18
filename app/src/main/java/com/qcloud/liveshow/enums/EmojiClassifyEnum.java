package com.qcloud.liveshow.enums;

import com.qcloud.liveshow.R;

/**
 * 类说明：表情分类
 * Author: Kuzan
 * Date: 2017/11/16 9:34.
 */
public enum EmojiClassifyEnum {
    /**笑脸*/
    Smile(0, "笑脸", R.drawable.icon_emoji_smile),
    /**动物*/
    Animal(1, "动物", R.drawable.icon_emoji_animal),
    /**活动*/
    Ball(2, "活动", R.drawable.icon_emoji_ball),
    /**食物*/
    Food(3, "食物", R.drawable.icon_emoji_food),
    /**旅游*/
    Travel(4, "旅游", R.drawable.icon_emoji_travel),
    /**旗帜*/
    Flag(5, "旗帜", R.drawable.icon_emoji_flag),
    /**删除*/
    Delete(6, "删除", R.drawable.icon_emoji_delete);

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
                return Smile;
            case 1:
                return Animal;
            case 2:
                return Ball;
            case 3:
                return Food;
            case 4:
                return Travel;
            case 5:
                return Flag;
            case 6:
                return Delete;
            default:
                return Smile;
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
