package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.EmojiViewPagerAdapter;
import com.qcloud.liveshow.enums.EmojiClassifyEnum;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.widget.indicator.FixedIndicatorView;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;
import com.qcloud.qclib.widget.indicator.transition.OnTransitionImageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：表情符号
 * Author: Kuzan
 * Date: 2017/11/16 11:20.
 */
public class EmojiLayout extends BaseLinearLayout {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.view_page_indicator)
    FixedIndicatorView mIndicator;
    @Bind(R.id.img_delete)
    ImageView mImgDelete;

    private KeyBackEditText mEditText;

    private OnEmojiClickListener mEmojiClick;

    private IndicatorViewPager mIndicatorViewPager;
    private EmojiViewPagerAdapter mAdapter;

    public EmojiLayout(Context context) {
        this(context, null);
    }

    public EmojiLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_emoji;
    }

    @Override
    protected void initViewAndData() {

    }

    public void initIndicator(FragmentManager manager) {
        int selectColor = ContextCompat.getColor(mContext, R.color.colorTitle);
        int unSelectColor = ContextCompat.getColor(mContext, R.color.colorLine);
        mIndicator.setOnTransitionListener(new OnTransitionImageListener()
                .setColor(selectColor, unSelectColor));

        // 设置viewpager保留界面不重新加载的页面数量
        mViewPager.setOffscreenPageLimit(6);

        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        mAdapter = new EmojiViewPagerAdapter(mContext, manager);
        mIndicatorViewPager.setAdapter(mAdapter);

        mAdapter.replaceList(initData());
    }

    @OnClick(R.id.img_delete)
    public void onViewClicked() {
        if (mEditText != null) {
            mEditText.dispatchKeyEvent(new KeyEvent(
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
    }

    private List<EmojiClassifyEnum> initData() {
        List<EmojiClassifyEnum> list = new ArrayList<>();
        list.add(EmojiClassifyEnum.Clock);
        list.add(EmojiClassifyEnum.Smile);
        list.add(EmojiClassifyEnum.Animal);
        list.add(EmojiClassifyEnum.Ball);
        list.add(EmojiClassifyEnum.Food);
        list.add(EmojiClassifyEnum.Travel);
        list.add(EmojiClassifyEnum.Flag);

        return list;
    }

    public void onDestroy() {
//        if (mAdapter != null) {
//            for (int i = 0; i < mAdapter.getCount(); i++) {
//                BaseFragment fragment = (BaseFragment) mAdapter.getFragmentForPage(i);
//                if (fragment != null) {
//                    fragment.detach();
//                }
//            }
//        }
        unAttachToEditText();
    }

    /**
     * 绑定EditText
     * */
    public void attachToEditText(KeyBackEditText editText) {
        this.mEditText = editText;
    }

    /**
     * 取消绑定EditText
     * */
    public void unAttachToEditText() {
        mEditText = null;
    }

    /**
     * 点击事件抽象方法
     */
    public void setOnEmojiClickListener(OnEmojiClickListener listener) {
        this.mEmojiClick = listener;
    }

    public interface OnEmojiClickListener {
        void onEmojiClick(View view, String emoji);
    }
}
