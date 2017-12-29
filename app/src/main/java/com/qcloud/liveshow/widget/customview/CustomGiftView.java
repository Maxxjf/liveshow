package com.qcloud.liveshow.widget.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 类说明：自定义礼物收礼View （准备弃用）
 * Author: iceberg
 * Date: 2017/12/27.
 *
 */

public class CustomGiftView extends LinearLayout {
    private Timer timer;
    private List<View> giftViewCollection = new ArrayList<>();
    public CustomGiftView(Context context) {
        this(context,null);
    }

    public CustomGiftView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomGiftView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomGiftView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     *<br> Description: 停止
     *<br> Author:      iceberg
     *<br> Date:        2017/12/27
     */
    public void pause() {
        if (null != timer) {
            timer.cancel();
        }
    }
    /**
     *<br> Description: 取消
     *<br> Author:      iceberg
     *<br> Date:        2017/12/27
     */
    public void cancel() {
        if (null != timer) {
            timer.cancel();
        }
    }
    /**
     *<br> Description: 清除礼物
     *<br> Author:      iceberg
     *<br> Date:        2017/12/27
     */
    public void resume() {
        clearTiming();
    }

    /**
     * 定时清除礼物
     */
    private void clearTiming() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = CustomGiftView.this.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = CustomGiftView.this.getChildAt(i);
                    UserHeadImageView crvheadimage = (UserHeadImageView) view.findViewById(R.id.gift_user);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (Long) crvheadimage.getTag();
                    if ((nowtime - upTime) >= 3000) {
                        final int j = i;
                        post(new Runnable() {
                            @Override
                            public void run() {
                                CustomGiftView.this.removeViewAt(j);
                            }
                        });
//                        removeGiftView(i);
                        return;
                    }
                }
            }
        };
        if (null != timer) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(task, 0, 100);
    }

    /**
     * 添加礼物view,(考虑垃圾回收)
     */
    private View addGiftView() {
        View view = null;
        if (giftViewCollection.size() <= 0) {
            /*如果垃圾回收中没有view,则生成一个*/
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_gift_notic, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 10;
            view.setLayoutParams(lp);
            this.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) { }
                //复用Item，当一个View移除时将它放到池内
                @Override
                public void onViewDetachedFromWindow(View view) {
                    if (giftViewCollection.size() < 5) {
                        giftViewCollection.add(view);
                    }
                }
            });
        } else {
            //如果Item池内有缓存的view，将它取出来，并从池中删除
            view = giftViewCollection.remove(0);
        }
        return view;
    }

    /**
     *<br> Description: 展示礼物
     *<br> Author:      iceberg
     *<br> Date:        2017/12/27
     */
    public void showGift(GiftInfo giftInfo) {
        View giftView = this.findViewWithTag(giftInfo.getGiftID() + giftInfo.getSenderFace());
        if (giftView == null) {/*该用户不在礼物显示列表*/

            giftView = addGiftView();/*获取礼物的View的布局*/
            giftView.setTag(giftInfo.getGiftID() + giftInfo.getSenderFace());/*设置view标识*/

            UserHeadImageView crvheadimage = (UserHeadImageView) giftView.findViewById(R.id.gift_user);
            final SGTextView giftNum = (SGTextView) giftView.findViewById(R.id.gift_number);/*找到数量控件*/
            TextView sender = (TextView) giftView.findViewById(R.id.tv_gift_user_name);
            TextView giftName = (TextView) giftView.findViewById(R.id.tv_gift_name);
            giftName.setText("送出一个"+giftInfo.getGiftUrl());
            sender.setText(giftInfo.getSenderNickName());
            giftNum.setText("x1");/*设置礼物数量*/
            giftNum.setStyle("#ffffff", "#fc9520", "#fc9520", 3, 90);//渐变颜色
            giftNum.setShadowLayer(2, 0, 2, "#ff000000");//阴影
            crvheadimage.setTag(System.currentTimeMillis());/*设置时间标记*/
            giftNum.setTag(1);/*给数量控件设置标记*/

            this.addView(giftView,0);/*将礼物的View添加到礼物的ViewGroup中*/
//            llgiftcontent.invalidate();/*刷新该view*/
            Animation inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.gift_out_anim);
            giftView.startAnimation(inAnim);/*开始执行显示礼物的动画*/
            inAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) {
                    //注释调，第一次添加没动画
//                    giftNumAnim.start(giftNum);
                    Log.d("gao","" + CustomGiftView.this.getHeight());
                }
                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        } else {/*该用户在礼物显示列表*/
            for (int i = 0;i < CustomGiftView.this.getChildCount();i ++) {
                if (giftView.equals(CustomGiftView.this.getChildAt(i))) {
                    if (i >= 3) {
                        CustomGiftView.this.removeView(giftView);
                    }
                }
            }
//                        llgiftcontent.addView(giftView,0);
            UserHeadImageView crvheadimage = (UserHeadImageView) giftView.findViewById(R.id.gift_user);/*找到头像控件*/
            SGTextView giftNum = (SGTextView) giftView.findViewById(R.id.gift_number);/*找到数量控件*/
            TextView giftName = (TextView) giftView.findViewById(R.id.tv_gift_name);
            giftName.setText("送出一个"+giftInfo.getGiftUrl());
            int showNum = (Integer) giftNum.getTag() + 1;
            giftNum.setStyle("#ffffff", "#fc9520", "#fc9520", 3, 90);//渐变颜色
            giftNum.setShadowLayer(2, 0, 2, "#ff000000");//阴影
            giftNum.setText("x"+showNum);
            giftNum.setTag(showNum);
            crvheadimage.setTag(System.currentTimeMillis());
            NumAnim numAnim = new NumAnim();
            giftNum.setTag(R.id.tag_imageview,numAnim);
            numAnim.start(giftNum);
        }
    }

    /**
     * 数字放大动画
     */
    public static class NumAnim {
        private Animator lastAnimator = null;
        private NumAnim lastNumAnim;
        public void start(View view) {
            lastNumAnim = (NumAnim) view.getTag(NumAnim.class.hashCode());
            if (lastNumAnim != null) {
                if (lastNumAnim.lastAnimator != null) {
                    lastNumAnim.lastAnimator.removeAllListeners();
                    lastNumAnim.lastAnimator.end();
                    lastNumAnim.lastAnimator.cancel();
                }
            }

            ObjectAnimator anim1_1 = ObjectAnimator.ofFloat(view, "scaleX",3.0f,0.5f);
            ObjectAnimator anim1_2 = ObjectAnimator.ofFloat(view, "scaleY",3.0f,0.5f);
            ObjectAnimator anim1_3 = ObjectAnimator.ofFloat(view,"alpha",0.8f);
            AnimatorSet animatorSet1 = new AnimatorSet();
            animatorSet1.setDuration((long) (700 * 0.1));
            animatorSet1.setInterpolator(new LinearInterpolator());
            animatorSet1.playTogether(anim1_1,anim1_2,anim1_3);

            ObjectAnimator anim2_1 = ObjectAnimator.ofFloat(view, "scaleX",1.2f);
            ObjectAnimator anim2_2 = ObjectAnimator.ofFloat(view, "scaleY",1.2f);
            ObjectAnimator anim2_3 = ObjectAnimator.ofFloat(view, "alpha",1.0f);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setDuration((long) (700 * 0.2));
            animatorSet2.setInterpolator(new LinearInterpolator());
            animatorSet2.playTogether(anim2_1,anim2_2,anim2_3);

            ObjectAnimator anim3_1 = ObjectAnimator.ofFloat(view, "scaleX",1.0f);
            ObjectAnimator anim3_2 = ObjectAnimator.ofFloat(view, "scaleY",1.0f);
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.setDuration((long) (700 * 0.2));
            animatorSet3.setInterpolator(new LinearInterpolator());
            animatorSet3.playTogether(anim3_1,anim3_2);

            AnimatorSet animSet = new AnimatorSet();
//            lastAnimator = animSet;
//            animSet.setDuration(500);
//            animSet.setInterpolator(new OvershootInterpolator());
            animSet.playSequentially(animatorSet1, animatorSet2,animatorSet3);
            animSet.playSequentially();
            animSet.start();
        }
    }

    public static class GiftInfo {
        private String senderFace;
        private String senderNickName;
        private String giftUrl;
        private int giftID;

        public String getSenderFace() {
            return senderFace;
        }

        public void setSenderFace(String senderFace) {
            this.senderFace = senderFace;
        }

        public String getSenderNickName() {
            return senderNickName;
        }

        public void setSenderNickName(String senderNickName) {
            this.senderNickName = senderNickName;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }

        public int getGiftID() {
            return giftID;
        }

        public void setGiftID(int giftID) {
            this.giftID = giftID;
        }
    }
}