package com.qcloud.liveshow.ui.anchor.widget;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.LiveInfoBean;
import com.qcloud.liveshow.beans.SubmitStartLiveBean;
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.presenter.impl.PreAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.pop.TollStandardPicker;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.UploadFileBean;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.StringUtils;
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

import static com.qcloud.liveshow.R.id.img_cover;
import static com.qcloud.liveshow.R.id.tv_toll_standard;

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
    @Bind(img_cover)
    RatioImageView mImgCover;
    @Bind(R.id.layout_change_cover)
    FrameLayout mLayoutChangeCover;
    @Bind(R.id.et_title)
    EditText mEtTitle;
    @Bind(R.id.img_title_clear)
    ImageView mImgTitleClear;
    @Bind(R.id.et_notice)
    EditText mEtNotice;
    @Bind(R.id.img_notice_clear)
    ImageView mImgNoticeClear;
    @Bind(tv_toll_standard)
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
    private String mStartTime;
    private String mEndTime = "";

    private String mTitle;
    private String mNotice;
    private String mDiamonds = "0";
    private String mCover; //我的直播id
    private SubmitStartLiveBean mSubmitBean;

//    private InputDialog mInputDialog;
    private boolean isInputTitle = true;

    private int upperLimit;//设置收费最大值

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
        mStartTime = DateUtils.getCurrTime("HH:mm");
        mBtnTimeStart.setText(mStartTime);
        new AnchorPresenterImpl().finishLive();//结束直播
        mPresenter.getLiveinfo();

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
//    private void initInputDialog() {
//        mInputDialog = new InputDialog(getActivity());
//        mInputDialog.setOnFinishInputListener(new InputDialog.OnFinishInputListener() {
//            @Override
//            public void onFinishInput(String message) {
//                mInputDialog.dismiss();
//            }
//        });
//        mInputDialog.setOnTextChangeListener(new InputDialog.OnTextChangeListener() {
//            @Override
//            public void onTextChange(String message) {
//                if (isInputTitle) {
//                    mTvTitle.append(message);
//                    if (mImgTitleClear.getVisibility() != View.VISIBLE) {
//                        mImgTitleClear.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    mTvNotice.append(message);
//                    if (mImgNoticeClear.getVisibility() != View.VISIBLE) {
//                        mImgNoticeClear.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });
//        mInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //SystemBarUtil.hideNavBar(getActivity());
//            }
//        });
//    }

    private void initSelectPicturePop() {
        mPicturePop = new SelectPicturePop(getActivity());
        mPicturePop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_take_a_picture) {
                    ToastUtils.ToastMessage(getActivity(), "暂时不支持照相，敬请期待~");
                    //ImageSelectUtil.startCamera(getActivity(), REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
                    ImageSelectUtil.openPhoto(getActivity(), REQUEST_CODE, false);
                }
            }
        });
        mPicturePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //SystemBarUtil.hideNavBar(getActivity());
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
                //SystemBarUtil.hideNavBar(getActivity());
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
                mStartTime = String.format(tagHourMinute, hour, minute);
                mBtnTimeStart.setText(mStartTime);
                if (DateUtils.compareTime(mStartTime, mEndTime, "HH:mm") > 0) {
                    mBtnTimeEnd.setText("次日" + mEndTime);
                } else {
                    mBtnTimeEnd.setText(mEndTime);
                }
            }
        });
        mStartPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //SystemBarUtil.hideNavBar(getActivity());
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
                mEndTime = String.format(tagHourMinute, hour, minute);
                if (DateUtils.compareTime(mStartTime, mEndTime, "HH:mm") > 0) {
                    mBtnTimeEnd.setText("次日" + mEndTime);
                } else {
                    mBtnTimeEnd.setText(mEndTime);
                }
            }
        });
        mEndPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    @OnClick({R.id.btn_exit, R.id.btn_switch_camera, R.id.btn_begin, R.id.layout_change_cover,
            R.id.img_title_clear, R.id.img_notice_clear,
            tv_toll_standard, R.id.btn_time_start, R.id.btn_time_end})
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

//    @Override
//    public void onInputTitleClick() {
//        isInputTitle = true;
//        if (mInputDialog == null) {
//            initInputDialog();
//        }
//        mInputDialog.show();
//    }
//
//    @Override
//    public void onInputNoticeClick() {
//        isInputTitle = false;
//        if (mInputDialog == null) {
//            initInputDialog();
//        }
//        mInputDialog.show();
//    }

    @Override
    public void onClearTitleClick() {
        mEtTitle.setText("");
        mImgTitleClear.setVisibility(View.GONE);
    }

    @Override
    public void onClearNoticeClick() {
        mEtNotice.setText("");
        mImgNoticeClear.setVisibility(View.GONE);
    }

    @Override
    public void onSelectDiamondsClick() {
        if (mTollPicker == null) {
            initTollPicker();
        }
        mTollPicker.refreshData(upperLimit+1);
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
        if (check()) {
            mPresenter.createLive(mSubmitBean);
            startLoadingDialog();
        }
    }

    @Override
    public void uploadSuccess(UploadFileBean bean) {
        if (isInFragment && bean != null) {
            Timber.e(bean.toString());
            mCover = bean.getFileId();
        }
    }

    @Override
    public void createLiveSuccess() {
        if (isInFragment) {
            stopLoadingDialog();
            if (mListener != null) {
                mListener.onBtnClick(mBtnBegin);
            }
        }
    }

    @Override
    public void getLiveInfoSuccess(LiveInfoBean bean) {
        GlideUtil.loadImage(getActivity(),mImgCover,bean.getCoverUrl());
        mEtTitle.setText(bean.getTitle());
        mEtNotice.setText(bean.getNotice());
//        mBtnTimeStart.setText(bean.getRatesStartTime());
//        mBtnTimeEnd.setText(bean.getRatesEndTime());
        mTvTollStandard.setText(""+bean.getRates());
        upperLimit=bean.getUpperLimit();
        mCover = bean.getCover();
//        mTollPicker.setOffset(bean.getUpperLimit());
//        mTollPicker.set
    }

    @Override
    public void getLiveInfoError(String errMsg) {
        ToastUtils.ToastMessage(getActivity(),errMsg);
        getActivity().finish();
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isInFragment) {
            stopLoadingDialog();
            if (isShow) {
                ToastUtils.ToastMessage(getActivity(), errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    private boolean check() {
        mTitle = mEtTitle.getText().toString().trim();
        mNotice = mEtNotice.getText().toString().trim();
        mDiamonds = mTvTollStandard.getText().toString().trim();

        if (StringUtils.isEmptyString(mTitle)) {
            ToastUtils.ToastMessage(getActivity(), R.string.toast_input_live_title);
            return false;
        }
//        if (StringUtils.isEmptyString(mNotice)) {
//            ToastUtils.ToastMessage(getActivity(), R.string.toast_input_live_notice);
//            return false;
//        }
        if (StringUtils.isEmptyString(mCover)) {
            ToastUtils.ToastMessage(getActivity(), R.string.toast_input_live_cover);
            return false;
        }
        mSubmitBean = new SubmitStartLiveBean();
        mSubmitBean.setCover(mCover);
        mSubmitBean.setTitle(mTitle);
        mSubmitBean.setNotice(mNotice);
        mSubmitBean.setRates(mDiamonds);
        mSubmitBean.setFeeStartTime(mStartTime);
        mSubmitBean.setFeeEndTime(mEndTime);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtil.SELECT_RESULT);
                if (images != null && !images.isEmpty()) {
                    if (isInFragment) {
                        if (mImgCover != null) {
                            GlideUtil.loadImage(mContext, mImgCover,
                                    images.get(0), R.drawable.bitmap_add_picture, 0, 0, true, false);
                        }
                        mPresenter.uploadCoverImg(images.get(0));
                    }
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
