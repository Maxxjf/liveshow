package com.qcloud.liveshow.utils;

import android.widget.AdapterView;

import com.qcloud.liveshow.adapter.EmojiAdapter;
import com.qcloud.liveshow.widget.customview.KeyBackEditText;

/**
 * 类说明：点击表情的全局监听管理类
 * Author: Kuzan
 * Date: 2017/11/17 17:56.
 */
public class EmojiClickManagerUtil {
    private static EmojiClickManagerUtil mInstance;
    private KeyBackEditText mEditText; //输入框

    public static EmojiClickManagerUtil getInstance() {
        if (mInstance == null) {
            synchronized (EmojiClickManagerUtil.class) {
                mInstance = new EmojiClickManagerUtil();
            }
        }
        return mInstance;
    }

    /**
     * 绑定EditText
     * */
    public void attachToEditText(KeyBackEditText editText) {
        this.mEditText = editText;
    }

    /**
     * 取消绑定EditText
     * */
    public void unAttachToEditText() {
        mEditText = null;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final EmojiAdapter mAdapter) {
        return (parent, view, position, id) -> {
            // 如果点击了表情,则添加到输入框中
            String emotionName = mAdapter.getList().get(position);

            // 获取当前光标位置,在指定位置上添加表情图片文本
            int curPosition = mEditText.getSelectionStart();
            StringBuilder sb = new StringBuilder(mEditText.getText().toString());
            sb.insert(curPosition, emotionName);

            // 特殊文字处理,将表情等转换一下
            mEditText.setText(sb.toString());

            // 将光标设置到新增完表情的右侧
            mEditText.setSelection(curPosition + emotionName.length());
        };
    }
}
