package com.qcloud.liveshow.widget.giftlayout;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.widget.giftlayout.widget.GiftFrameLayout;
import com.qcloud.liveshow.widget.giftlayout.widget.ICustormAnim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import timber.log.Timber;

/**
 * 类说明：礼物展示布局操作（直播页面收礼物）
 * Author: iceberg
 * Date: 2017/12/28
 */

public class GiftControl implements GiftFrameLayout.LeftGiftAnimationStatusListener {

    private static final String TAG = "GiftControl";
    protected Context mContext;
    /**
     * 自定义动画
     */
    private ICustormAnim custormAnim;
    /**
     * 是否开启隐藏动画
     */
    private boolean isHideMode;
    /**
     * 当前所有礼物动画布局的显示方式
     */
    private int curDisplayMode = FROM_BOTTOM_TO_TOP;
    public static final int FROM_BOTTOM_TO_TOP = 0;//由下往上
    public static final int FROM_TOP_TO_BOTTOM = 1;//由上往下
    /**
     * 礼物队列
     */
    private ArrayList<NettyGiftBean> mGiftQueue;

    /**
     * 添加礼物布局的父容器
     */
    private LinearLayout mGiftLayoutParent;
    /**
     * 最大礼物布局数
     */
    private int mGiftLayoutMaxNums;

    public GiftControl(Context context) {
        mContext = context;
        mGiftQueue = new ArrayList<>();
    }

    public GiftControl setCustormAnim(ICustormAnim anim) {
        custormAnim = anim;

        return this;
    }

    /**
     * @param giftLayoutParent 存放礼物控件的父容器
     * @param giftLayoutNums   礼物控件的数量
     * @return
     */
    public GiftControl setGiftLayout(LinearLayout giftLayoutParent, @NonNull int giftLayoutNums) {
        if (giftLayoutNums <= 0) {
            throw new IllegalArgumentException("GiftFrameLayout数量必须大于0");
        }
        if (giftLayoutParent.getChildCount() > 0) {//如果父容器没有子孩子，就进行添加
            return this;
        }
        mGiftLayoutParent = giftLayoutParent;
        mGiftLayoutMaxNums = giftLayoutNums;
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,
                transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
        transition.setAnimator(LayoutTransition.APPEARING,
                transition.getAnimator(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING,
                transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                transition.getAnimator(LayoutTransition.DISAPPEARING));
        mGiftLayoutParent.setLayoutTransition(transition);

        return this;
    }

    /**
     * 是否开启隐藏动画
     *
     * @param isHideMode
     * @return
     */
    public GiftControl setHideMode(boolean isHideMode) {
        this.isHideMode = isHideMode;
        return this;
    }

    /**
     * 当前所有礼物动画布局的显示方式
     *
     * @param displayMode {@link #FROM_BOTTOM_TO_TOP}、{@link #FROM_TOP_TO_BOTTOM}
     * @return
     */
    public GiftControl setDisplayMode(int displayMode) {
        this.curDisplayMode = displayMode;
        return this;
    }

    public void loadGift(NettyGiftBean gift) {
        loadGift(gift, true);
    }

    /**
     * 加入礼物，具有实时连击效果
     *
     * @param gift
     * @param supportCombo 是否支持实时连击，如果为true：支持，否则不支持
     */
    public void loadGift(NettyGiftBean gift, boolean supportCombo) {
        if (mGiftQueue != null) {
            if (supportCombo) {
                GiftFrameLayout giftFrameLayout;
                for (int i = 0; i < mGiftLayoutParent.getChildCount(); i++) {
                    giftFrameLayout = (GiftFrameLayout) mGiftLayoutParent.getChildAt(i);
                    if (giftFrameLayout.isShowing()) {
                        if (giftFrameLayout.getCurrentGiftId().equals(gift.getGift().getIdStr()) && giftFrameLayout.getCurrentSendUserId().equals(gift.getUser().getIdStr())) {
                            //连击
                            Timber.i("addGiftQueue: ========giftFrameLayout(" + giftFrameLayout.getIndex() + ")连击========礼物：" + gift.getGift().getIdStr() + ",连击X" + gift.getGift().getGiftCount());
                            if (gift.getGift().getJumpCombo() > 0) {
                                //触发连击，这里不要在加上giftCount了git，因为你要连击的数已经变成jumpCombo了
                                giftFrameLayout.setGiftCount(gift.getGift().getJumpCombo());
                            } else {
                                giftFrameLayout.setGiftCount(gift.getGift().getGiftCount());
                            }
                            giftFrameLayout.setSendGiftTime(gift.getGift().getSendGiftTime());
                            return;
                        }
                    }
                }
            }

            addGiftQueue(gift, supportCombo);
        }
    }

    private void addGiftQueue(final NettyGiftBean gift, final boolean supportCombo) {
        if (mGiftQueue != null) {
            if (mGiftQueue.size() == 0) {
                Log.d(TAG, "addGiftQueue---集合个数：" + mGiftQueue.size() + ",礼物：" + gift.getGift().getIdStr());
                mGiftQueue.add(gift);
                showGift();
                return;
            }
        }
        Log.d(TAG, "addGiftQueue---集合个数：" + mGiftQueue.size() + ",礼物：" + gift.getGift().getIdStr());
        if (supportCombo) {
            boolean addflag = false;
            for (NettyGiftBean model : mGiftQueue) {
                if (model.getGift().getIdStr().equals(gift.getGift().getIdStr()) && model.getUser().getIdStr().equals(gift.getUser().getIdStr())) {
                    Log.d(TAG, "addGiftQueue: ========已有集合========" + gift.getGift().getIdStr() + ",礼物数：" + gift.getGift().getGiftCount());
                    model.getGift().setGiftCount(model.getGift().getGiftCount() + gift.getGift().getGiftCount());
                    addflag = true;
                    break;
                }
            }
            //如果在现有的集合中不存在同一人发的礼物就加入到现有集合中
            if (!addflag) {
                Log.d(TAG, "addGiftQueue: --------新的集合--------" + gift.getGift().getIdStr() + ",礼物数：" + gift.getGift().getGiftCount());
                mGiftQueue.add(gift);
            }
        } else {
            mGiftQueue.add(gift);
        }

    }

    /**
     * 显示礼物
     */
    public synchronized void showGift() {
        if (isEmpty()) {
            return;
        }
        GiftFrameLayout giftFrameLayout;
        int childCount = mGiftLayoutParent.getChildCount();
        Log.d(TAG, "showGift: 礼物布局的个数" + childCount);
        if (childCount < mGiftLayoutMaxNums) {
            //没有超过最大的礼物布局数量，可以继续添加礼物布局
            giftFrameLayout = new GiftFrameLayout(mContext);
            giftFrameLayout.setIndex(0);
//            giftFrameLayout.firstHideLayout();
            giftFrameLayout.setGiftAnimationListener(this);
            giftFrameLayout.setHideMode(isHideMode);
            if (curDisplayMode == FROM_BOTTOM_TO_TOP) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGiftLayoutParent.getLayoutParams();//两个参数分别是layout_width,layout_height
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//这个就是添加其他属性的，这个是在父元素的底部。
                mGiftLayoutParent.addView(giftFrameLayout);
            } else if (curDisplayMode == FROM_TOP_TO_BOTTOM) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGiftLayoutParent.getLayoutParams();//两个参数分别是layout_width,layout_height
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);//这个就是添加其他属性的，这个是在父元素的底部。
                mGiftLayoutParent.addView(giftFrameLayout, 0);
            } else {//默认由下往上
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGiftLayoutParent.getLayoutParams();//两个参数分别是layout_width,layout_height
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//这个就是添加其他属性的，这个是在父元素的底部。
                mGiftLayoutParent.addView(giftFrameLayout);
            }
            Log.d(TAG, "showGift: begin->集合个数：" + mGiftQueue.size());
            boolean hasGift = giftFrameLayout.setGift(getGift());
            if (hasGift) {
                giftFrameLayout.startAnimation(custormAnim);
            }
            Log.d(TAG, "showGift: end->集合个数：" + mGiftQueue.size());
        } else {
            //超过了进行等待
        }
    }

    /**
     * 取出礼物
     *
     * @return
     */
    private synchronized NettyGiftBean getGift() {
        NettyGiftBean gift = null;
        if (mGiftQueue.size() != 0) {
            gift = mGiftQueue.get(0);
            mGiftQueue.remove(0);
            Log.i(TAG, "getGift---集合个数：" + mGiftQueue.size() + ",送出礼物---" + gift.getGift().getIdStr() + ",礼物数X" + gift.getGift().getGiftCount());
        }
        return gift;
    }

    /**
     * 通过获取giftId和getSendUserId当前用户giftId礼物总数
     *
     * @param giftId
     * @param userId
     * @return
     */
    public int getCurGiftCountByUserId(String giftId, String userId) {
        int curGiftCount = 0;
        GiftFrameLayout giftFrameLayout;
        NettyGiftBean giftModel;
        for (int i = 0; i < mGiftLayoutParent.getChildCount(); i++) {
            giftFrameLayout = (GiftFrameLayout) mGiftLayoutParent.getChildAt(i);
            giftModel = giftFrameLayout.getGift();
            if (giftModel != null && giftModel.getGift().getIdStr().equals(giftId) && giftModel.getUser().getIdStr().equals(userId)) {
                curGiftCount = giftModel.getGift().getGiftCount();
            } else {//自己的礼物不正在显示，还在队列中
                Iterator<NettyGiftBean> iterator = mGiftQueue.iterator();
                while (iterator.hasNext()) {
                    giftModel = iterator.next();
                    if (giftModel.getGift().getIdStr().equals(giftId) && giftModel.getUser().getIdStr().equals(userId)) {
                        curGiftCount = giftModel.getGift().getGiftCount();
                        break;
                    }
                }
            }
        }
        return curGiftCount;
    }

    /**
     * 获取正在展示礼物的个数（即GiftFragmeLayout展示的个数）
     *
     * @return
     */
    public int getShowingGiftLayoutCount() {
        int count = 0;
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutParent.getChildCount(); i++) {
            giftFrameLayout = (GiftFrameLayout) mGiftLayoutParent.getChildAt(i);
            if (giftFrameLayout.isShowing()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取正在展示礼物的个数实例（即GiftFragmeLayout展示的个数实例）
     *
     * @return
     */
    public List<GiftFrameLayout> getShowingGiftLayouts() {
        List<GiftFrameLayout> giftLayoutList = new ArrayList<>();
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutParent.getChildCount(); i++) {
            giftFrameLayout = (GiftFrameLayout) mGiftLayoutParent.getChildAt(i);
            if (giftFrameLayout.isShowing()) {
                giftLayoutList.add(giftFrameLayout);
            }
        }
        return giftLayoutList;
    }

    @Override
    public void dismiss(GiftFrameLayout giftFrameLayout) {
        reStartAnimation(giftFrameLayout, giftFrameLayout.getIndex());
    }

    private void reStartAnimation(final GiftFrameLayout giftFrameLayout, final int index) {
        //动画结束，这时不能触发连击动画
        giftFrameLayout.setCurrentShowStatus(false);
        Log.d(TAG, "reStartAnimation: 动画结束");
        AnimatorSet animatorSet = giftFrameLayout.endAnmation(custormAnim);
        if (animatorSet != null) {
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.i(TAG, "礼物动画dismiss: index = " + index);
                    //动画完全结束
                    giftFrameLayout.CurrentEndStatus(true);
                    giftFrameLayout.setGiftViewEndVisibility(isEmpty());
                    mGiftLayoutParent.removeView(giftFrameLayout);
                    showGift();
                }
            });
        }
    }

    public GiftControl reSetGiftLayout(LinearLayout giftLayoutParent, @NonNull int giftLayoutNums) {
        return setGiftLayout(giftLayoutParent, giftLayoutNums);
    }

    /**
     * 清除所有礼物
     */
    public synchronized void cleanAll() {
        if (mGiftQueue != null) {
            mGiftQueue.clear();
        }
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutParent.getChildCount(); i++) {
            giftFrameLayout = (GiftFrameLayout) mGiftLayoutParent.getChildAt(i);
            if (giftFrameLayout != null) {
                giftFrameLayout.clearHandler();
                giftFrameLayout.firstHideLayout();
            }
        }
        mGiftLayoutParent.removeAllViews();
    }

    /**
     * 礼物是否为空
     *
     * @return
     */
    public synchronized boolean isEmpty() {
        if (mGiftQueue == null || mGiftQueue.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
