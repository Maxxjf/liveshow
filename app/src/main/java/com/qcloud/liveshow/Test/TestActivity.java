package com.qcloud.liveshow.Test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;

import com.github.sahasbhop.apngview.ApngDrawable;
import com.github.sahasbhop.apngview.ApngImageLoader;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.widget.customview.CustomGiftView;
import com.qcloud.liveshow.widget.giftlayout.GiftControl;
import com.qcloud.liveshow.widget.pop.TollStandardPicker;
import com.qcloud.qclib.FrameConfig;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class TestActivity extends AppCompatActivity {


    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.root)
    LinearLayout rootLayout;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.gift)
    CustomGiftView gift;
    @Bind(R.id.ll_gift_parent)
    LinearLayout ll_gift_parent;
    @Bind(R.id.num)
    NumberPicker num;
    private GiftControl giftControl;
    private TollStandardPicker mTollPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
//        img.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int i) {
//                Timber.e("-------->>>>>>>>i==="+i);
//            }
//        });
        // Display image from a file in assets
//        String uri = "assets://apng/rose.png";
//        giftControl=new GiftControl(this);
//        giftControl.setGiftLayout(ll_gift_parent, 3)
//                .setHideMode(false)
//                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
//        giftControl.setDisplayMode(GiftControl.FROM_BOTTOM_TO_TOP);
//        giftControl.setHideMode(true);

//        img.setVisibility(View.VISIBLE);


    }

    private void initTollPicker() {
        mTollPicker = new TollStandardPicker(this);
        mTollPicker.setOnStandardPickListener(new TollStandardPicker.OnStandardPickListener() {
            @Override
            public void onStandardPicked(int index, Integer item) {
                btn3.setText(String.valueOf(item));
            }
        });
        mTollPicker.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                btn3.setText("消失了");
            }
        });
    }
    @SuppressLint("WrongConstant")
    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onClick(View view) {
        Timber.e("v.getId" + view.getId());
        switch (view.getId()) {
            case R.id.btn_1:
                if (mTollPicker != null && mTollPicker.isShowing()) {
                    mTollPicker.dismiss();
                }
//                CustomGiftView.GiftInfo giftInfo=new CustomGiftView.GiftInfo();
//                giftInfo.setGiftID(new Random().nextInt(100)+2);
//                giftInfo.setGiftUrl("");
//                giftInfo.setSenderFace("");
//                giftInfo.setSenderNickName("小黄人");
//                gift.showGift(giftInfo);
                break;
            case R.id.btn_2:
                String uri = "http://dfs.test.qi-cloud.com/group1/M00/00/34/CgoVCVpEoMiAau8LAA0d97aNv9M111.png";
                ApngImageLoader.getInstance().displayApng(uri, img, new ApngImageLoader.ApngConfig(2, false));
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApngDrawable apngDrawable = ApngDrawable.getFromView(v);
                        if (apngDrawable == null) return;

                        if (apngDrawable.isRunning()) {
                            apngDrawable.stop(); // Stop animation
                        } else {
                            apngDrawable.setNumPlays(3); // Fix number of repetition
                            apngDrawable.start(); // Start animation
                        }
                    }
                });
//                img.setVisibility(View.GONE);
//                SnackbarUtils.showShortSnackbar(btn2, "Hello,S收到个八戒咖啡哈空间划分金坷垃的合法铝合金阿萨德放假了卡号等借口了复活甲ad生活缴费哈监考老师动环监控了阖家安康还记得nackBar", getResources().getColor(R.color.colorText),  getResources().getColor(R.color.black), "再次发送", getResources().getColor(R.color.big_red), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        SnackbarUtils.showLongSnackbar(btn3,"Hello,SnackBar",getResources().getColor(R.color.colorText),getResources().getColor(R.color.black));
//                    }
//                });
//                //这里最好不要直接new对象
//                GiftModel giftModel = new GiftModel();
//                giftModel.setGiftId("小气球").setGiftName("礼物名字").setGiftCount(1).setGiftPic("http://scimg.jb51.net/allimg/170921/106-1F9211106230-L.jpg")
//                        .setSendUserId("1234").setSendUserName("最帅的主播").setSendUserPic("").setSendGiftTime(System.currentTimeMillis())
//                ;
//                giftModel.setHitCombo(2);
////                            giftModel.setJumpCombo(10);
//                giftControl.loadGift(giftModel);
//                Log.d("TAG", "onClick: " + giftControl.getShowingGiftLayoutCount());
                break;
            case R.id.btn_3:
                FrameConfig.server="http://10.10.100.189:8082/";
                AppConstants.NETTY_HOST="10.10.100.189";
                break;
        }
    }
}
