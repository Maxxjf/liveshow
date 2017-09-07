package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.anchor.presenter.impl.PreAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;
import com.qcloud.qclib.widget.customview.RatioImageView;
import com.qcloud.qclib.widget.customview.wheelview.DateTimePicker;
import com.qcloud.qclib.widget.customview.wheelview.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：主播前的准备
 * Author: Kuzan
 * Date: 2017/9/2 11:19.
 */
public class PreAnchorFragment extends BaseFragment<IPreAnchorView, PreAnchorPresenterImpl> implements IPreAnchorView {

    @Bind(R.id.btn_exit)
    ImageView mBtnExit;
    @Bind(R.id.btn_switch_camera)
    ImageView mBtnSwitchCamera;
    @Bind(R.id.img_cover)
    RatioImageView mImgCover;
    @Bind(R.id.layout_change_cover)
    FrameLayout mLayoutChangeCover;
    @Bind(R.id.et_title)
    ClearEditText mEtTitle;
    @Bind(R.id.et_notice)
    ClearEditText mEtNotice;
    @Bind(R.id.et_toll_standard)
    EditText mEtTollStandard;
    @Bind(R.id.tv_toll_standard_remark)
    TextView mTvTollStandardRemark;
    @Bind(R.id.btn_time_start)
    TextView mBtnTimeStart;
    @Bind(R.id.btn_time_end)
    TextView mBtnTimeEnd;
    @Bind(R.id.btn_begin)
    TextView mBtnBegin;

    @BindString(R.string.tag_toll_standard_remark)
    String tollStandardRemark;

    /**fragment点击事件回调*/
    private OnFragmentClickListener mListener;
    /**调用摄像头回调*/
    private final int REQUEST_CODE = 0x001;

    private SelectPicturePop mPicturePop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pre_anchor;
    }

    @Override
    protected PreAnchorPresenterImpl initPresenter() {
        return new PreAnchorPresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void beginLoad() {
        if (mTvTollStandardRemark != null) {
            mTvTollStandardRemark.setText(String.format(tollStandardRemark, 10));
        }
    }

    private void initSelectPicturePop() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int screenW = dm.widthPixels * 9 / 10;   // 获取分辨率宽度

        mPicturePop = new SelectPicturePop(getActivity());
        mPicturePop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_take_a_picture) {
                    ImageSelectUtil.startCamera(getActivity(), REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
                    ImageSelectUtil.openPhoto(getActivity(), REQUEST_CODE, screenW, screenW);
                }
            }
        });
    }

    @OnClick({R.id.btn_exit, R.id.btn_switch_camera, R.id.btn_begin, R.id.layout_change_cover,
            R.id.btn_time_start, R.id.btn_time_end})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onExitClick() {
        getActivity().finish();
    }

    @Override
    public void onSwitchCameraClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnSwitchCamera);
        }
    }

    @Override
    public void onChangeCoverClick() {
        if (mPicturePop == null) {
            initSelectPicturePop();
        }
        mPicturePop.showAtLocation(mImgCover, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTimeStartClick() {
        TimePicker picker = new TimePicker(getActivity(), DateTimePicker.HOUR_24);
        picker.setUseWeight(true);
        picker.setCycleDisable(false);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.initWheelView();
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                ToastUtils.ToastMessage(getActivity(), hour + ":" + minute);
            }
        });
        picker.showAtLocation(mBtnTimeStart, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTimeEndClick() {

    }

    @Override
    public void onBeginAnchorClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnBegin);
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isInFragment) {
            if (isShow) {
                ToastUtils.ToastMessage(getActivity(), errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtil.SELECT_RESULT);
                if (images != null && !images.isEmpty()) {
                    if (isInFragment && mImgCover != null) {
                        GlideUtil.loadImage(mContext, mImgCover,
                                images.get(0), R.drawable.icon_user_head_default, 0, 0, true, false);
                    }
                    //mPresenter.uploadFile(images.get(0));
                } else {
                    ToastUtils.ToastMessage(mContext, R.string.toast_get_picture_failure);
                }
            }
        }
    }

    /**
     * 点击事件回调
     * */
    public void setOnFragmentClickListener(OnFragmentClickListener listener) {
        this.mListener = listener;
    }

    public static PreAnchorFragment newInstance() {
        PreAnchorFragment fragment = new PreAnchorFragment();
        return fragment;
    }
}
