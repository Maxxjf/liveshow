package com.qcloud.liveshow.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：输入弹窗
 * Author: Kuzan
 * Date: 2017/9/9 17:17.
 */
public class InputDialog extends Dialog {
    private Context mContext;
    @Bind(R.id.et_message)
    ClearEditText mEtMessage;
    @Bind(R.id.layout_input)
    LinearLayout mLayoutInput;

    private InputMethodManager mManager;
    private int mLastDiff = 0;

    private String mMessage;

    private OnFinishInputListener mListener;
    private OnTextChangeListener mChangeListener;

    public InputDialog(@NonNull Context context) {
        this(context, R.style.InputDialog);
    }

    public InputDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.dialog_input);
        ButterKnife.bind(this);

        initDialog();
    }

    private void initDialog() {
        mEtMessage.setImeOptions(EditorInfo.IME_ACTION_UNSPECIFIED);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (dm.widthPixels); //设置宽度
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
    }

    private void initView() {
        mEtMessage.setInputType(InputType.TYPE_CLASS_TEXT);
        //修改下划线颜色
        mEtMessage.getBackground().setColorFilter(ContextCompat.getColor(mContext, R.color.transparent), PorterDuff.Mode.CLEAR);
        mEtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mChangeListener != null) {
                    mChangeListener.onTextChange(editable.toString());
                }
            }
        });

        //监听键盘
        mEtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               Timber.e(String.valueOf(event.getKeyCode()));
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        onFinishClick();
                        return true;
//                    case KeyEvent.KEYCODE_:
//                        onFinishClick();
//                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        return false;
                    default:
                        return false;
                }
            }
        });

        mEtMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Timber.d("onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        // dialog消失键盘会消失，但是键盘消失，dialog不一定消失，比如按软键盘上的消失键，键盘消失，dialog不会消失
        // 所以在这里监听键盘的高度，如果高度为0则表示键盘消失，那么就应该dimiss dialog
        mLayoutInput.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                if (heightDifference <= 0 && mLastDiff > 0) {
                    //imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    dismiss();
                }
                mLastDiff = heightDifference;
            }
        });
    }

    @OnClick(R.id.btn_finish)
    void onFinishClick() {
        if (check()) {
            if (mListener != null) {
                mListener.onFinishInput(mMessage);
            }
            mManager.showSoftInput(mEtMessage, InputMethodManager.SHOW_FORCED);
            mManager.hideSoftInputFromWindow(mEtMessage.getWindowToken(), 0);
            mEtMessage.setText("");
            dismiss();
        }
    }

    @OnClick(R.id.layout_outside)
    void onOutsideClick(View view) {
        //点击编辑区域以上的位置，dialog消失
        if (view.getId() != R.id.layout_input) {
            dismiss();
        }
    }

    private boolean check() {
        mMessage = mEtMessage.getText().toString().trim();

        if (StringUtils.isEmptyString(mMessage)) {
            ToastUtils.ToastMessage(mContext, R.string.toast_input_not_null);
            return false;
        }

        return true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    @Override
    public void show() {
        super.show();
    }

    /**事件回调*/
    public void setOnFinishInputListener(OnFinishInputListener listener) {
        this.mListener = listener;
    }

    public interface OnFinishInputListener {
        void onFinishInput(String message);
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.mChangeListener = listener;
    }

    public interface OnTextChangeListener {
        void onTextChange(String message);
    }
}
