package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.anchor.presenter.impl.PreAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
import com.qcloud.liveshow.widget.dialog.InputDialog;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.pop.TollStandardPicker;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;
import com.qcloud.qclib.widget.customview.wheelview.DateTimePicker;
import com.qcloud.qclib.widget.customview.wheelview.TimePicker;
import com.qcloud.qclib.widget.customview.wheelview.WheelView;

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
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.img_title_clear)
    ImageView mImgTitleClear;
    @Bind(R.id.tv_notice)
    TextView mTvNotice;
    @Bind(R.id.img_notice_clear)
    ImageView mImgNoticeClear;
    @Bind(R.id.tv_toll_standard)
    TextView mTvTollStandard;
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
    @BindString(R.string.tag_hour_minute)
    String tagHourMinute;

    /**fragment点击事件回调*/
    private OnFragmentClickListener mListener;
    /**调用摄像头回调*/
    private final int REQUEST_CODE = 0x001;

    private SelectPicturePop mPicturePop;

    private TollStandardPicker mTollPicker;
    private TimePicker mStartPicker;
    private TimePicker mEndPicker;
    private String startTime = "00:00";
    private String endTime = "00:00";

    private InputDialog mInputDialog;
    private boolean isInputTitle = true;

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

    /**
     * 初始化输入消息弹窗
     * */
    private void initInputDialog() {
        mInputDialog = new InputDialog(getActivity());
        mInputDialog.setOnFinishInputListener(new InputDialog.OnFinishInputListener() {
            @Override
            public void onFinishInput(String message) {
                if (isInputTitle) {
                    mTvTitle.setText(message);
                    mImgTitleClear.setVisibility(View.VISIBLE);
                } else {
                    mTvNotice.setText(message);
                    mImgNoticeClear.setVisibility(View.VISIBLE);
                }
            }
        });
        mInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
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

    private void initTollPicker() {
        mTollPicker = new TollStandardPicker(getActivity());
        mTollPicker.setOnStandardPickListener(new TollStandardPicker.OnStandardPickListener() {
            @Override
            public void onStandardPicked(int index, Integer item) {
                mTvTollStandard.setText(String.valueOf(item));
            }
        });
        mTollPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    private void initStartPicker() {
        mStartPicker = new TimePicker(getActivity(), DateTimePicker.HOUR_24);
        mStartPicker.setUseWeight(true);
        mStartPicker.setCycleDisable(false);
        mStartPicker.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle), ContextCompat.getColor(mContext, R.color.colorSubTitle));
        mStartPicker.setLineSpaceMultiplier(3);
        mStartPicker.setTextSize(14);

        mStartPicker.setRangeStart(0, 0);//00:00
        mStartPicker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mStartPicker.setSelectedItem(currentHour, currentMinute);

        mStartPicker.setCancelTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        mStartPicker.setFinishTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        mStartPicker.setTitleText(R.string.tag_start_time);
        mStartPicker.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        mStartPicker.setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        // 选中线条颜色
        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setColor(ContextCompat.getColor(mContext, R.color.colorLineWhite));//线颜色
        mStartPicker.setDividerConfig(config);
        mStartPicker.setLabel(":", "");

        mStartPicker.initWheelView();
        mStartPicker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                startTime = String.format(tagHourMinute, hour, minute);
                mBtnTimeStart.setText(startTime);
                if (DateUtils.compareTime(startTime, endTime, "HH:mm") > 0) {
                    mBtnTimeEnd.setText("次日" + endTime);
                } else {
                    mBtnTimeEnd.setText(endTime);
                }
            }
        });
        mStartPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    private void initEndPicker() {
        mEndPicker = new TimePicker(getActivity(), DateTimePicker.HOUR_24);
        mEndPicker.setUseWeight(true);
        mEndPicker.setCycleDisable(false);
        mEndPicker.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle), ContextCompat.getColor(mContext, R.color.colorSubTitle));
        mEndPicker.setLineSpaceMultiplier(3);
        mEndPicker.setTextSize(14);

        mEndPicker.setRangeStart(0, 0);//00:00
        mEndPicker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mEndPicker.setSelectedItem(currentHour, currentMinute);

        mEndPicker.setCancelTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        mEndPicker.setFinishTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        mEndPicker.setTitleText(R.string.tag_end_time);
        mEndPicker.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        mEndPicker.setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        // 选中线条颜色
        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setColor(ContextCompat.getColor(mContext, R.color.colorLineWhite));//线颜色
        mEndPicker.setDividerConfig(config);
        mEndPicker.setLabel(":", "");

        mEndPicker.initWheelView();
        mEndPicker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                endTime = String.format(tagHourMinute, hour, minute);
                if (DateUtils.compareTime(startTime, endTime, "HH:mm") > 0) {
                    mBtnTimeEnd.setText("次日" + endTime);
                } else {
                    mBtnTimeEnd.setText(endTime);
                }
            }
        });
        mEndPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    @OnClick({R.id.btn_exit, R.id.btn_switch_camera, R.id.btn_begin, R.id.layout_change_cover,
            R.id.tv_title, R.id.tv_notice, R.id.img_title_clear, R.id.img_notice_clear,
            R.id.tv_toll_standard, R.id.btn_time_start, R.id.btn_time_end})
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
    public void onInputTitleClick() {
        isInputTitle = true;
        if (mInputDialog == null) {
            initInputDialog();
        }
        mInputDialog.show();
    }

    @Override
    public void onInputNoticeClick() {
        isInputTitle = false;
        if (mInputDialog == null) {
            initInputDialog();
        }
        mInputDialog.show();
    }

    @Override
    public void onClearTitleClick() {
        mTvTitle.setText("");
        mImgTitleClear.setVisibility(View.GONE);
    }

    @Override
    public void onClearNoticeClick() {
        mTvNotice.setText("");
        mImgNoticeClear.setVisibility(View.GONE);
    }

    @Override
    public void onSelectDiamondsClick() {
        if (mTollPicker == null) {
            initTollPicker();
        }
        mTollPicker.refreshData(20);
        mTollPicker.showAtLocation(mTvTollStandard, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTimeStartClick() {
        if (mStartPicker == null) {
            initStartPicker();
        }
        mStartPicker.showAtLocation(mBtnTimeStart, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTimeEndClick() {
        if (mEndPicker == null) {
            initEndPicker();
        }
        mEndPicker.showAtLocation(mBtnTimeEnd, Gravity.BOTTOM, 0, 0);
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
                                images.get(0), R.drawable.bitmap_user_head, 0, 0, true, false);
                    }
                    //mPresenter.uploadFile(images.get(0));
                } else {
                    ToastUtils.ToastMessage(mContext, R.string.toast_get_picture_failure);
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTollPicker != null && mTollPicker.isShowing()) {
            mTollPicker.dismiss();
        }
        if (mStartPicker != null && mStartPicker.isShowing()) {
            mStartPicker.dismiss();
        }
        if (mEndPicker != null && mEndPicker.isShowing()) {
            mEndPicker.dismiss();
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
