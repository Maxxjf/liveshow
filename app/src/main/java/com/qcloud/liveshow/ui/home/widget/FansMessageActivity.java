package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FansMessageAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.home.presenter.impl.FansMessagePresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:07.
 */
public class FansMessageActivity extends SwipeBaseActivity<IFansMessageView, FansMessagePresenterImpl> implements IFansMessageView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;
    @Bind(R.id.et_message)
    ClearEditText mEtMessage;
    @Bind(R.id.btn_emoticon)
    ImageView mBtnEmoticon;

    private FansMessageAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_fans_message;
    }

    @Override
    protected FansMessagePresenterImpl initPresenter() {
        return new FansMessagePresenterImpl();
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
    protected void initViewAndData() {
        initRefreshList();

        //监听键盘
        mEtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        //onSendClick();
                        return true;
                    case KeyEvent.KEYCODE_BACK:

                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    private void initRefreshList() {
        PullRefreshUtil.setRefresh(mListMessage, false, true);
        mListMessage.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListMessage.refreshFinish();
            }
        });

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FansMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FansMessageActivity.class);
        context.startActivity(intent);
    }
}
