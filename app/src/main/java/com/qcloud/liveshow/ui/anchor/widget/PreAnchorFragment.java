package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
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
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.SubmitStartLiveBean;
import com.qcloud.liveshow.ui.anchor.presenter.impl.PreAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.pop.TollStandardPicker;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.UploadFileBean;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.ProcessImageActivity;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import java.util.ArrayList;

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

    private String mTitle;
    private String mNotice;
    private String mDiamonds = "0";
    private String mCover; //我的直播id
    private SubmitStartLiveBean mSubmitBean;

//    private InputDialog mInputDialog;

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
        mPresenter.getLiveinfo(); //得到上一次的直播信息

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
//                    ToastUtils.ToastMessage(getActivity(), "暂时不支持照相，敬请期待~");
                    ((AnchorActivity)getActivity()).stopSurface();
                            ImageSelectUtil.startCamera(getActivity(), REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
//                    ImageSelectUtil.openPhoto(getActivity(), REQUEST_CODE, false);
                    getActivity().startActivity(new Intent(getActivity(), ProcessImageActivity.class));
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



    @OnClick({R.id.btn_exit, R.id.btn_switch_camera, R.id.btn_begin, R.id.layout_change_cover,
            R.id.img_title_clear, R.id.img_notice_clear,
            tv_toll_standard})
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
    public void createLiveSuccess(RoomBean bean) {
        if (isInFragment) {
            stopLoadingDialog();
            if (bean!=null){
                ((AnchorActivity)getActivity()).setNotice(mEtNotice.getText().toString().trim());
                bean.setTitle(mEtTitle.getText().toString());
                ((AnchorActivity)getActivity()).setRoom(bean);
            }
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
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((AnchorActivity)getActivity()).startSurface();
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
