package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.widget.customview.wheelview.SinglePicker;
import com.qcloud.qclib.widget.customview.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：收费标准
 * Author: Kuzan
 * Date: 2017/9/9 15:21.
 */
public class TollStandardPicker extends SinglePicker<Integer> {

    public static List<Integer> mList = new ArrayList<>();

    public TollStandardPicker(Context context) {
        super(context);

        initPicker();
    }

    private void initPicker() {
        setCancelTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        setFinishTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        setTitleText(R.string.tag_toll_standard_title);
        setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        setCycleDisable(false);//不禁用循环
        setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle), ContextCompat.getColor(mContext, R.color.colorSubTitle));
        setLineSpaceMultiplier(3);
        setTextSize(14);
        // 选中线条颜色
        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setColor(ContextCompat.getColor(mContext, R.color.colorLineWhite));//线颜色
        config.setRatio((float) (1.0 / 8.0));

        setDividerConfig(config);
    }

    public void refreshData(int size) {
        if (size <= 0) {
            return;
        }
        for (int i=0; i<size; i++) {
            mList.add(i);
        }

        replaceItems(mList);
    }

    public void setOnWheelListener(OnWheelListener onWheelListener) {
        super.setOnWheelListener(onWheelListener);
    }

    public interface OnWheelListener extends SinglePicker.OnWheelListener<Integer> {

    }

    public void setOnStandardPickListener(OnStandardPickListener listener) {
        super.setOnItemPickListener(listener);
    }

    public static abstract class OnStandardPickListener implements OnItemPickListener<Integer> {

        public abstract void onStandardPicked(int index, Integer item);

        @Override
        public final void onItemPicked(int index, Integer item) {
            onStandardPicked(index, item);
        }

    }
}
