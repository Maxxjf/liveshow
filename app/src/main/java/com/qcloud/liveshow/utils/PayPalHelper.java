package com.qcloud.liveshow.utils;

/**
 * 类说明：${必须填}
 * Author: iceberg
 * Date: 2018/1/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.qcloud.liveshow.beans.PayResult;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.qclib.callback.DataCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import timber.log.Timber;

/**
 * 类说明：PayPal支付工具类
 * Author: xie_max
 * Date: 2018/01/16.
 */
public class PayPalHelper {

    //配置何种支付环境，一般沙盒，正式
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // 注意，这些凭证将在live & sandbox环境中有所不同。
    //测试 APP Id
    private static final String CONFIG_CLIENT_ID = "AfUcYT0DqJ8xZkRh-6PRuvx6h5ZSfAt5QIjpPK00SrUVyHd5rImlcXQwkTlDba8B6Z8RWaclOhu9-DHC";
//正式 APP Id
//    private static final String CONFIG_CLIENT_ID = "Aar0YBvD9FqRa6ACGOYEwgqOa-0VEg9Wqgq8K0buH5W-AcndZWaZ3nf_toH6TKhMvd5-oYA3GVfD58BZ";

    private static final int REQUEST_CODE_PAYMENT = 1;//付款请求的代码
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;//请求代码未来支付
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;//请求代码资料付款

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
    //以下配置是授权支付的时候用到的
                .merchantName("Example Merchant")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    private static PayPalHelper payPalHelper;

    private PayPalHelper() {

    }

    public static PayPalHelper getInstance() {
        if (payPalHelper == null) {
            synchronized (PayPalHelper.class) {
                payPalHelper = new PayPalHelper();
            }
        }
        return payPalHelper;
    }

    /**
     * 启动PayPal服务
     *
     * @param context
     */
    public void startPayPalService(Context context) {
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);
    }

    /**
     * 停止PayPal服务  sdfsdfsdssaaass
     *
     * @param context
     */
    public void stopPayPalService(Context context) {
        context.stopService(new Intent(context, PayPalService.class));
    }

    /**
     * 开始执行支付操作
     *
     * @param context
     */
    public void doPayPalPay(Context context,String name,double money,String id) {
            /*
             * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
             * Change PAYMENT_INTENT_SALE to
             *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
             *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
             *     later via calls from your server.
             *
             * Also, to include additional payment details and an item list, see getStuffToBuy() below.
             */
        PayPalPayment thingToBuy = getStuffToBuy(name,money,id);
            /*
             * See getStuffToBuy(..) for examples of some available payment options.
             */
        Intent intent = new Intent(context, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    /**
     * This method shows use of optional payment details and item list.
     *@param name  物品名字
     *@param money  物品价格
     *@param id  物品id
     * 直接给PP创建支付的信息，支付对象实体信息
     */
    public PayPalPayment getStuffToBuy(String name,double money,String id) {
        //--- include an item list, payment amount details
        //具体的产品信息列表
        PayPalItem[] items =
                {
                        new PayPalItem(name, 1, new BigDecimal(money), "USD",
                                id)
                };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.00");
        BigDecimal tax = new BigDecimal("0.00");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "钻石币充值", PayPalPayment.PAYMENT_INTENT_SALE);
        payment.items(items).paymentDetails(paymentDetails);
        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("AV8D钻石币充值");
        return payment;
    }

    /**
     * 处理支付之后的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void confirmPayResult( int requestCode, int resultCode, Intent data,double amount,String id, final DoResult doResult) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Timber.e(confirm.toJSONObject().toString(4));
                        Timber.e(confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        //                        displayResultText("PaymentConfirmation info received from PayPal");
                        // 这里直接跟服务器确认支付结果，支付结果确认后回调处理结果
                        JSONObject jsonObject = confirm.toJSONObject();
                        if (jsonObject != null) {
                            JSONObject response = jsonObject.optJSONObject("response");
                            if (response != null) {
                                String paymentId = response.optString("id");
                                try {
                                    new ProfitModelImpl().paypal(amount,id,paymentId, new DataCallback<PayResult>() {
                                        @Override
                                        public void onSuccess(PayResult bean) {
                                            if (bean!=null){
                                                    doResult.confirmSuccess(bean);
                                            }
                                        }

                                        @Override
                                        public void onError(int status, String errMsg) {
                                            doResult.confirmNetWorkError();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    doResult.confirmNetWorkError();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        Timber.e("返回的Json数据有误:");
                        doResult.confirmNetWorkError();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Timber.e("用户取消了.");
                doResult.customerCanceled();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                doResult.invalidPaymentConfiguration();
                Timber.e( "提交了无效的支付或PayPal配置。请参见文档。");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        doResult.confirmFuturePayment();
                        Timber.e( auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Timber.e( authorization_code);

                        //                        sendAuthorizationToServer(auth);
                        //                        displayResultText("Future Payment code received from PayPal");

                    } catch (JSONException e) {
                        doResult.confirmNetWorkError();
                        Timber.e("返回的Json数据有误:");
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Timber.e("用户取消了.");
                doResult.customerCanceled();
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                doResult.invalidPaymentConfiguration();
                Timber.e( "提交了无效的支付或PayPal配置。请参见文档。");
            }
        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Timber.e( auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Timber.e( authorization_code);

                        //                        sendAuthorizationToServer(auth);
                        //                        displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Timber.e("返回的Json数据有误:");
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Timber.e("用户取消了.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Timber.e( "提交了无效的支付或PayPal配置。请参见文档。");
            }
        }
    }

    /**
     * c处理完结果之后回调
     */
    public interface DoResult {
        //与服务确认支付成功
        void confirmSuccess(PayResult result);

        //网络异常或者json返回有问题
        void confirmNetWorkError();

        //用户取消支付
        void customerCanceled();

        //授权支付
        void confirmFuturePayment();

        //订单支付验证无效
        void invalidPaymentConfiguration();
    }

}

