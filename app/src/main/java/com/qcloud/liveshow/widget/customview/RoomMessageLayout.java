package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.enums.CharStatusEnum;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.utils.DensityUtils;
import com.qcloud.qclib.utils.ScreenUtils;

/**
 * 类说明：图文混合信息
 * Author: iceberg
 * Date: 2017/11/10
 */
public class RoomMessageLayout extends BaseLinearLayout {
    private ImageView imgAnchorLevel;
    private ImageView imgIsFans;
    private TextView tvNickname;
    private TextView tvMessage;
    private TextView tvMessage2;
    private ProgressBar progressBar;
    private ImageView imgSendFail;
    private LinearLayout rootLayout;
    private int ScreenWidth;
    private NettyReceiveGroupBean mCurrentBean;//当前的群聊

    public RoomMessageLayout(Context context) {
        this(context, null);
    }


    public RoomMessageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoomMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_room_message;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void initViewAndData() {
        imgAnchorLevel = (ImageView) mView.findViewById(R.id.img_anchor_level);
        imgIsFans = (ImageView) mView.findViewById(R.id.img_is_fans);
        tvNickname = (TextView) mView.findViewById(R.id.tv_nickname);
        tvMessage = (TextView) mView.findViewById(R.id.tv_message);
        tvMessage2 = (TextView) mView.findViewById(R.id.tv_message2);
        progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        imgSendFail = (ImageView) mView.findViewById(R.id.img_send_fail);
        rootLayout=(LinearLayout)mView.findViewById(R.id.root_layout);
    }

    public void refreshMessageInfo(NettyReceiveGroupBean bean) {
        if (bean != null) {
            this.mCurrentBean=bean;
            MemberBean memberBean = bean.getUser();
            NettyContentBean contentBean = bean.getContent();

            if (memberBean != null) {
                GlideUtil.loadImage(mContext, imgAnchorLevel, memberBean.getIcon(), R.drawable.icon_member_level_1, false, true);
//                imgIsFans.setVisibility(memberBean.isAttention()? View.VISIBLE : View.GONE);
                imgIsFans.setVisibility(View.GONE);
                tvNickname.setText(memberBean.getNickName() + ":");
                if (bean.getCharStatusEnum() == CharStatusEnum.SUCCESS.getKey()) {
                    progressBar.setVisibility(View.GONE);
                    imgSendFail.setVisibility(View.GONE);
                } else if (bean.getCharStatusEnum() == CharStatusEnum.FAIL.getKey()) {
                    progressBar.setVisibility(View.GONE);
                    imgSendFail.setVisibility(View.VISIBLE);
                } else if (bean.getCharStatusEnum() == CharStatusEnum.INPROGRESS.getKey()) {
                    progressBar.setVisibility(View.VISIBLE);
                    imgSendFail.setVisibility(View.GONE);
                } else if (bean.getCharStatusEnum()==CharStatusEnum.IS_BLOCKED.getKey()){//已被禁言
                    rootLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    imgSendFail.setVisibility(View.GONE);
                }
                if (contentBean != null) {
                    setText(contentBean, memberBean);
                }
            }
        }
    }


    public void setText(NettyContentBean contentBean, MemberBean memberBean) {
        String nickName = memberBean.getNickName() + ":";
        String text = contentBean.getText();
        ScreenWidth = ScreenUtils.getScreenWidth(BaseApplication.getInstance());//屏幕的长度
        String result;
        int resultWidth;
        int iconWidth = DensityUtils.dp2px(BaseApplication.getInstance(), 160);//图标的长度
        tvMessage.setText("");
        tvMessage2.setText("");
        for (int i = 0; i < text.length(); i++) {
            char single = text.charAt(i);//单个字节
            result = nickName + text.substring(0, i);//文字的长度
            resultWidth = getCharacterWidth(result, getResources().getDimension(R.dimen.micro_text_size));
            if ((iconWidth + resultWidth) < ScreenWidth) {
                tvMessage.append(String.valueOf(single));
            } else {
                tvMessage2.setVisibility(View.VISIBLE);
                tvMessage2.append(String.valueOf(single));
            }
        }
    }

    public static int getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        int textWidth = (int) paint.measureText(text);// 得到总体长度
        // int width = text_width/text.length();//每一个字符的长度
        return textWidth;
    }
}
