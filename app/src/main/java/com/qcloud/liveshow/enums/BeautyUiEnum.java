package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;

/**
 * 类说明：美颜效果
 * Author: Kuzan
 * Date: 2017/9/26 10:11.
 */
public enum BeautyUiEnum {
    /**不使用美颜*/
    DISABLE(ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE, "0"),
    /**柔美效果*/
    SOFT(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT, "16"),
    /**肌肤美白*/
    SKINWHITEN(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SKINWHITEN, "17"),
    /**美丽的幻想*/
    ILLUSION(ImgTexFilterMgt.KSY_FILTER_BEAUTY_ILLUSION, "18"),
    /**降噪，消除干扰*/
    DENOISE(ImgTexFilterMgt.KSY_FILTER_BEAUTY_DENOISE, "19"),
    /**光滑*/
    SMOOTH(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SMOOTH, "20"),
    /**外部柔美*/
    SOFT_EXT(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_EXT, "21"),
    /**美柔锐化*/
    SOFT_SHARPEN(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_SHARPEN, "22"),
    /**专业美容*/
    PRO(ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO, "23"),
    /**专业美容*/
    PRO1(ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO1, "24"),
    /**专业美容*/
    PRO2(ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO2, "25"),
    /**专业美容*/
    PRO3(ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO3, "26"),
    /**专业美容*/
    PRO4(ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO4, "27");

    private int key;
    private String value;

    BeautyUiEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static BeautyUiEnum valueOf(int key) {
        switch (key) {
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE:
                return DISABLE;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT:
                return SOFT;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SKINWHITEN:
                return SKINWHITEN;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_ILLUSION:
                return ILLUSION;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_DENOISE:
                return DENOISE;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SMOOTH:
                return SMOOTH;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_EXT:
                return SOFT_EXT;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_SHARPEN:
                return SOFT_SHARPEN;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO:
                return PRO;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO1:
                return PRO1;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO2:
                return PRO2;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO3:
                return PRO3;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO4:
                return PRO4;
            default:
                return DISABLE;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
