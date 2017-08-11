package com.qcloud.liveshow.ui.home.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.HotAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.HotPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IHotView;

import butterknife.Bind;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:33.
 */
public class HotFragment extends BaseFragment<IHotView, HotPresenterImpl> implements IHotView {


    @Bind(R.id.list_hot)
    RecyclerView mListHot;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected HotPresenterImpl initPresenter() {
        return new HotPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        mListHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListHot.setAdapter(new HotAdapter(getActivity()));
    }

    @Override
    protected void beginLoad() {

    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }
}
