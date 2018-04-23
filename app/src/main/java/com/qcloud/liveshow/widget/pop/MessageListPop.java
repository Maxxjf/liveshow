package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.PopMessageAdapter;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.utils.MessageUtil;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：消息列表弹窗
 * Author: Kuzan
 * Date: 2017/8/29 15:36.
 */
public class MessageListPop extends BasePopupWindow {
    @Bind(R.id.list_message)
    RecyclerView mListMessage;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;
    @Bind(R.id.btn_ignore_message)
    TextView ignoreMessage;

    private PopMessageAdapter mAdapter;

    private onPopItemClick mItemClick;

    public MessageListPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_message_list;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {
        PullRefreshUtil.setRefresh(mRefreshView, true, true);
        mRefreshView.setOnPullDownRefreshListener(() -> mRefreshView.refreshFinish());
        mRefreshView.setOnPullUpRefreshListener(() -> mRefreshView.refreshFinish());

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PopMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapterView, view, position, l) -> {
            if (mItemClick != null) {
              MemberBean memberBean=  mAdapter.getList().get(position);
              if (memberBean!=null){
                  memberBean.setRead(true);
                  mAdapter.notifyDataSetChanged();
                  RealmHelper.getInstance().addOrUpdateBean(memberBean);
              }
                mItemClick.onItemClick(position, mAdapter.getList().get(position));
            }
        });
    }

    public void initData() {
        List<MemberBean> beans = MessageUtil.getInstance().getCharList();
        replaceList(beans);
    }

    @Override
    protected void init() {
        super.init();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int height = (int) mContext.getResources().getDimension(R.dimen.margin_50);
        setHeight(height);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    public void replaceList(List<MemberBean> beans) {
        if (beans != null && mAdapter != null) {
            mAdapter.replaceList(beans);
        }
    }

    public void   add(MemberBean memberBean){
        if (memberBean != null && mAdapter != null) {
            mAdapter.addListBeanAtStart(memberBean);
            mAdapter.notifyDataSetChanged();
        }
    }
    @OnClick(R.id.btn_ignore_message)
    void onIgnoreClick(View view) {
        if (mViewClick!=null){
            mViewClick.onViewClick(ignoreMessage);
        }
        ignoreMessageRealm();//数据库标为未读
        ignoreMessageList();//列表数据标为未读
        
    }

    private void ignoreMessageList() {
        if (mAdapter!=null){
            List<MemberBean> charList = mAdapter.getList();
            for (MemberBean member:charList){
                member.setRead(true);
            }
        }
        mAdapter.notifyDataSetChanged();
//        notifyAll();
    }

    private void ignoreMessageRealm() {
        MessageUtil.getInstance().ignoreMessage();
    }

    /**
     * item点击事件抽象方法
     */
    public void setOnPopItemClick(onPopItemClick viewClick) {
        this.mItemClick = viewClick;
    }

    public interface onPopItemClick {
        void onItemClick(int position, MemberBean member);
    }
}
