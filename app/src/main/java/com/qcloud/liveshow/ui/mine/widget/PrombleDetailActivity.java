package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.ProblemDetailPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IPrombleDetailView;

import butterknife.Bind;

/**
 * 类说明：问题详情
 * Author: iceberg
 * Date: 2017/10/31.
 */
public class PrombleDetailActivity extends SwipeBaseActivity<IPrombleDetailView, ProblemDetailPresenterImpl> implements IPrombleDetailView {
    String question;//问题
    String answer;//答案
    @Bind(R.id.tv_question)
    TextView tvQuestion;
    @Bind(R.id.tv_answer)
    TextView tvAnswer;


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(question);

    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected int setStatusBarColor() {
        return Color.WHITE;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_promble_detail;
    }

    @Override
    protected ProblemDetailPresenterImpl initPresenter() {
        return null;
    }

    @Override
    protected void initViewAndData() {
        question = getIntent().getStringExtra("question");
        answer = getIntent().getStringExtra("answer");
        if (question != null && answer != null) {
            tvQuestion.setText(question);
            tvAnswer.setText(answer);
        }
    }

    public static void openActivity(Context context, String question, String answer) {
        Intent intent = new Intent(context, PrombleDetailActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("answer", answer);
        context.startActivity(intent);

    }

}
