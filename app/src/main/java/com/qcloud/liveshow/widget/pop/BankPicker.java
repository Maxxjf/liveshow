package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.BankBean;
import com.qcloud.qclib.widget.customview.wheelview.SinglePicker;
import com.qcloud.qclib.widget.customview.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：银行选择器
 * Author: Kuzan
 * Date: 2017/9/1 10:31.
 */
public class BankPicker extends SinglePicker<String> {

    public static List<BankBean> mList = new ArrayList<>();
    private List<String> strList = new ArrayList<>();

    public BankPicker(Context context) {
        super(context);

        initPicker();
    }

    private void initPicker() {
        setCancelTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        setFinishTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        setTitleText(R.string.tag_opening_bank);
        setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        setCycleDisable(false);//不禁用循环
        setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle), ContextCompat.getColor(mContext, R.color.colorSubTitle));
        setLineSpaceMultiplier(3);
        setTextSize(14);
        // 选中线条颜色
        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setColor(ContextCompat.getColor(mContext, R.color.colorLineWhite));//线颜色
        config.setRatio(WheelView.DividerConfig.FILL);

        setDividerConfig(config);
    }

    public void refreshData(List<BankBean> list) {
        this.mList = list;
        if (mList == null || mList.size() <= 0) {
            return;
        }
        strList = new ArrayList<>();
        for (BankBean bean : mList) {
            strList.add(bean.getName());
        }

        replaceItems(strList);
    }

    public void setOnWheelListener(OnWheelListener onWheelListener) {
        super.setOnWheelListener(onWheelListener);
    }

    public interface OnWheelListener extends SinglePicker.OnWheelListener<String> {

    }

    public void setOnBankPickListener(OnBankPickListener listener) {
        super.setOnItemPickListener(listener);
    }

    public static abstract class OnBankPickListener implements OnItemPickListener<String> {

        public abstract void onBankPicked(int index, BankBean bean);

        @Override
        public final void onItemPicked(int index, String item) {
            BankBean bean = null;
            if (mList != null && mList.size() > 0 && index < mList.size()) {
                bean = mList.get(index);
            }
            onBankPicked(index, bean);
        }

    }
}
