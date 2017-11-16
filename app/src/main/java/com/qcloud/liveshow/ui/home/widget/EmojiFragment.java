package com.qcloud.liveshow.ui.home.widget;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.EmojiAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.EmojiPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IEmojiView;

import java.util.List;

import butterknife.Bind;

/**
 * 类说明：表情符号
 * Author: Kuzan
 * Date: 2017/11/16 9:59.
 */
public class EmojiFragment extends BaseFragment<IEmojiView, EmojiPresenterImpl> implements IEmojiView {

    @Bind(R.id.list_emoji)
    RecyclerView mListEmoji;

    private EmojiAdapter mAdapter;

    private int currType = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoji;
    }

    @Override
    protected EmojiPresenterImpl initPresenter() {
        return new EmojiPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        currType = getArguments().getInt("TYPE", 0);

        initList();
    }

    @Override
    protected void beginLoad() {
        mPresenter.getEmoji(currType);
    }

    private void initList() {
        mListEmoji.setLayoutManager(new GridLayoutManager(getActivity(), 8));
        mAdapter = new EmojiAdapter(getActivity());
        mListEmoji.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapterView, view, position, l) -> {

        });
    }

    public static EmojiFragment newInstance(int type) {
        EmojiFragment fragment = new EmojiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void replaceList(List<String> list) {
        if (isInFragment) {
            if (mAdapter != null && list != null) {
                mAdapter.replaceList(list);
            }
        }
    }
}
