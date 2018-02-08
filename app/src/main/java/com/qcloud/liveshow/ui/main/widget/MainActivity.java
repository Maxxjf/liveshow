package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.enums.StartHomeEnum;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.netty.NettyClientBus;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.anchor.widget.AnchorActivity;
import com.qcloud.liveshow.ui.anchor.widget.ApplyAnchorActivity;
import com.qcloud.liveshow.ui.home.widget.HomeFragment;
import com.qcloud.liveshow.ui.main.presenter.impl.MainPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.liveshow.ui.mine.widget.MineFragment;
import com.qcloud.liveshow.ui.room.widget.RoomActivity;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.liveshow.utils.NettyUtil;
import com.qcloud.liveshow.utils.RoomInfoUtil;
import com.qcloud.liveshow.widget.pop.BindingGeneralizeRelationPop;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.StringUtils;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity<IMainView, MainPresenterImpl> implements IMainView, BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar mNavigationBar;
    @Bind(R.id.btn_live_show)
    ImageView mBtnLiveShow;

    private HomeFragment mHomeFragment;
    private MineFragment mMineFragment;

    private int lastSelectedPosition = 0;

    private long exitTime = 0;

    private BindingGeneralizeRelationPop mBindingPop;
    private String bindCode;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected boolean isPaddingStatus() {
        return false;
    }

    @Override
    protected void initViewAndData() {
        initBottomNavBar();
        int startEnum = getIntent().getIntExtra("START_ENUM", 0);
        switchStart(startEnum);
        loadBasicData();
        mBtnLiveShow.post(() -> bindingGeneralizeRelation());
        connectIM();
        jumpToRoomActivity();
//        startActivity(new Intent(this, TestActivity.class));
//        UmengTool.getSignature(this);

    }
    /**
     * 如果有分享接收到，会跳到直播间
     * */
    private void jumpToRoomActivity() {
        if (RoomInfoUtil.mRoomBean!=null){
            RoomActivity.openActivity(this,0,RoomInfoUtil.mRoomBean);
        }
    }

    /**
     * 加载基础数据
     * */
    private void loadBasicData() {
        BasicsUtil.loadDiamonds();
        BasicsUtil.loadGift();
        BasicsUtil.getContactWay();
    }

    /**
     * 绑定分佣关系
     * */
    private void bindingGeneralizeRelation() {
        boolean isFirst = ConstantUtil.getBoolean(AppConstants.IS_FIRST_LOGIN, false);
        if (isFirst) {
            if (mBindingPop == null) {
                initBindingPop();
                ConstantUtil.writeBoolean(AppConstants.IS_FIRST_LOGIN,false);
            }
            mBindingPop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 初始化Netty
     * */
    public void connectIM() {
        NettyClientBus.Initialization(this, AppConstants.NETTY_HOST, AppConstants.NETTY_PORT);
    }

    private void initBottomNavBar() {
        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED_NO_TITLE);
        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mNavigationBar.setAutoHideEnabled(true);
        BottomNavigationItem homeItem = new BottomNavigationItem(R.drawable.icon_home_select, R.string.tab_home);
        homeItem.setInactiveIconResource(R.drawable.icon_home_normal);
        BottomNavigationItem liveItem = new BottomNavigationItem(R.drawable.icon_live_show, R.string.tab_live_show);
        liveItem.setInactiveIconResource(R.drawable.icon_live_show);
        BottomNavigationItem mineItem = new BottomNavigationItem(R.drawable.icon_mine_select, R.string.tab_mine);
        mineItem.setInactiveIconResource(R.drawable.icon_mine_normal);
        mNavigationBar.addItem(homeItem)
                .addItem(liveItem)
                .addItem(mineItem)
                .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                .initialise();

        mNavigationBar.setTabSelectedListener(this);
    }

    private void initBindingPop() {
        mBindingPop = new BindingGeneralizeRelationPop(this);
        mBindingPop.setOnHolderClick(view -> {
            if (view.getId() == R.id.btn_ok) {
                bindCode = mBindingPop.getCode();
                if (StringUtils.isEmptyString(bindCode)) {
                    ToastUtils.ToastMessage(mContext, R.string.tip_input_recommend_code);
                } else {
                    mPresenter.submitBinding(bindCode);
                }
            } else {
                mBindingPop.dismiss();
            }
        });
    }

    @OnClick({R.id.btn_live_show})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void switchFragment(int key) {
        if (mNavigationBar!=null){
            mNavigationBar.selectTab(key);
        }else{
            ToastUtils.ToastMessage(this,"NavigationBar为空");
        }
    }
    @Override
    public void onHomeClick() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance(StartHomeEnum.StartHot.getKey());
        }
        replaceFragment(mHomeFragment, R.id.fragment_container, false);
    }

    @Override
    public void onLiveShowClick() {
        if (BaseApplication.loginAuth()) {
            //AnchorActivity.openActivity(this);
            mPresenter.getApplyStatus();
            startLoadingDialog();
        }
    }

    @Override
    public void onMineClick() {
        if (mMineFragment == null) {
            mMineFragment = new MineFragment();
        }
        replaceFragment(mMineFragment, R.id.fragment_container, false);
    }

    @Override
    public void showPending() {
        // 审核中
        Timber.e("审核中");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_pending);
            pop.showCancel(false);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void showAgree() {
        // 审核通过
        Timber.e("审核通过");
        stopLoadingDialog();
        AnchorActivity.openActivity(this);
    }

    @Override
    public void showDisagree() {
        // 审核不通过
        Timber.e("审核不通过");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            final TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_disagree);
            pop.setCancelBtn(R.string.btn_no);
            pop.setOkBtn(R.string.btn_yes);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
            pop.setOnHolderClick(view -> {
                if (view.getId() == R.id.btn_ok) {
                    ApplyAnchorActivity.openActivity(MainActivity.this);
                } else {
                    pop.dismiss();
                }
            });
        }
    }

    @Override
    public void showNotApply() {
        // 未提交审核
        Timber.e("未提交审核");
        stopLoadingDialog();
        final TipsPop pop=new TipsPop(this);
        pop.setTips(R.string.toast_apply_none);
        pop.showAtLocation(mBtnLiveShow,Gravity.CENTER,0,0);
        pop.setOnHolderClick(view -> {
            if (view.getId() == R.id.btn_ok) {
                ApplyAnchorActivity.openActivity(MainActivity.this);
            } else {
                pop.dismiss();
            }
        });

    }

    @Override
    public void showDisable() {
        // 禁用
        Timber.e("禁用");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_disable);
            pop.showCancel(false);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void bindingSuccess() {
        if (isRunning) {
            mBindingPop.dismiss();
            ToastUtils.ToastMessage(this, R.string.toast_binding_success);
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    /**
     * 底部导航栏
     */
    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        switchStart(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void switchStart(int startEnum) {
        if (startEnum == StartMainEnum.StartHome.getKey()) {
            onHomeClick();
        } else if (startEnum == StartMainEnum.StartLiveShow.getKey()) {
            onLiveShowClick();
        } else if (startEnum == StartMainEnum.StartMine.getKey()) {
            onMineClick();
        } else {
            onHomeClick();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int startEnum = intent.getIntExtra("START_ENUM", 1);
        switchStart(startEnum);
    }

    public static void openActivity(Context context, int startEnum) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("START_ENUM", startEnum);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NettyClientBus.recycle();
        NettyUtil.clearIsAuth();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.ToastMessage(this, R.string.toast_exit_app);
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().getAppManager().AppExit(this);
                RealmHelper.getInstance().closeRealm();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
