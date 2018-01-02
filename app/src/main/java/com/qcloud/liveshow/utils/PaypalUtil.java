package com.qcloud.liveshow.utils;

import android.app.Activity;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

/**
 * 类说明：PayPal支付工具类
 * Author: iceberg
 * Date: 2017/11/29.
 */
public class PaypalUtil {


    /**配置何种支付环境，一般沙盒，正式*/
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    /**你所注册的APP Id */
    private static final String CONFIG_CLIENT_ID = "AfUcYT0DqJ8xZkRh-6PRuvx6h5ZSfAt5QIjpPK00SrUVyHd5rImlcXQwkTlDba8B6Z8RWaclOhu9-DHC";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    /** note that these credentials will differ between live & sandbox environments.*/

    private static final int REQUEST_CODE_PROFILE_SHARING = 3;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
    /**
     * 以下配置是授权支付的时候用到的
     //.merchantName("Example Merchant")
     // .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
     //.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
     */

    private static PaypalUtil me;
    private static Intent paypalService;

    private PaypalUtil() {

    }

    public static synchronized PaypalUtil getInstance(Activity activity) {
        paypalService = new Intent(activity, PayPalService.class);
        paypalService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        activity.startService(paypalService);
        if (me == null) {
            me = new PaypalUtil();
        }
        return me;
    }

    /**
     * 摧毁PayPal服务，在Activity的OnDectoty调用
     *
     * @param activity
     */
    public void unRegister(Activity activity) {
        if (activity != null && paypalService != null) {
            activity.stopService(paypalService);
        }
    }

    public void goToPlay(Activity activity, String money) {

        //创建支付对象，用于传过去给PayPal服务器进行收款
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, money);
        Intent intent = new Intent(activity, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        //这里直接调起PayPal的sdk进行付款操作
        activity.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    //这里只传一个总价格或者单个产品的信息收款情况
    private PayPalPayment getThingToBuy(String paymentIntent, String money) {
        return new PayPalPayment(new BigDecimal(money), "USD", "sample item",
                paymentIntent);

    }

    /**
     * @param paymentIntent PayPalPayment.PAYMENT_INTENT_SALE 安全
     *                      PAYMENT_INTENT_AUTHORIZE
     *                      PAYMENT_INTENT_ORDER
     * @return
     */
    //这里是购买一系列产品创建购买对象
    private PayPalPayment getStuffToBuy(String paymentIntent) {
        PayPalItem[] items =
                {
                        new PayPalItem("sample item #1", 2, new BigDecimal("87.50"), "USD",
                                "sku-12345678"),
                        new PayPalItem("free sample item #2", 1, new BigDecimal("0.00"),
                                "USD", "sku-zero-price"),
                        new PayPalItem("sample item #3 with a longer name", 6, new BigDecimal("37.99"),
                                "USD", "sku-33333")
                };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("7.21");
        BigDecimal tax = new BigDecimal("4.67");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "sample item", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);
        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");
        return payment;
    }


}
