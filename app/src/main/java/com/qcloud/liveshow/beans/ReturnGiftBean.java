package com.qcloud.liveshow.beans;

/**
 * 类说明：${必须填}
 * Author: iceberg
 * Date: 2018/1/10.
 */
public class ReturnGiftBean {
   boolean isSuccess;	//true 成功 false 失败
   int virtualCoin;	//钻石币数量

   public boolean isSuccess() {
      return isSuccess;
   }

   public void setSuccess(boolean success) {
      isSuccess = success;
   }

   public int getVirtualCoin() {
      return virtualCoin;
   }

   public void setVirtualCoin(int virtualCoin) {
      this.virtualCoin = virtualCoin;
   }

   @Override
   public String toString() {
      return "ReturnGiftBean{" +
              "isSuccess=" + isSuccess +
              ", virtualCoin=" + virtualCoin +
              '}';
   }
}
