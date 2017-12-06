package com.qcloud.liveshow.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * 类说明：检查手机某个应用是否存在
 * Author: iceberg
 * Date: 2017/12/6.
 */
public class CheckAppLiveUtil {
    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断facebook是否可用
     *
     * @param context
     * @return
     */
    public static boolean isFacebookAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.facebook.katana")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * android常用应用的包名和startAcitivity名

     Contacts （通讯录）：
     package name=com.android.contacts
     classname=com.android.contacts.activities.PeopleActivity

     Phone （拨号）：
     package name=com.android.dialer
     classname=com.android.dialer.DialtactsActivity

     Google Settings （Google 设置）：

     package name=com.google.android.gms
     classname=com.google.android.gms.app.settings.GoogleSettingsActivity

     Messaging （短信）：

     package name=com.android.mms
     classname=com.android.mms.ui.ConversationList

     Play Store （应用商店）：

     package name=com.android.vending
     classname=com.android.vending.AssetBrowserActivity

     Settings （设置）：

     package name=com.android.settings
     classname=com.android.settings.Settings

     Browser （浏览器）：

     package name=com.android.browser
     classname=com.android.browser.BrowserActivity

     Calendar （日历）：

     package name=com.android.calendar
     classname=com.android.calendar.AllInOneActivity

     Camera （相机）：

     package name=com.android.camera2
     classname=com.android.camera.CameraLauncher

     Clock （时钟）：

     package name=com.android.deskclock
     classname=com.android.deskclock.DeskClock

     Email （邮箱）：

     package name=com.android.email
     classname=com.android.email.activity.Welcome

     Gallery （图库）：

     package name=com.android.gallery3d
     classname=com.android.gallery3d.app.GalleryActivity

     Gmail（google 邮箱） ：

     package name=com.google.android.gm
     classname=com.google.android.gm.ConversationListActivityGmail

     Music （音乐） ：

     package name=com.android.music
     classname=com.android.music.MusicBrowserActivity

     Sound Recorder（录音）：

     package name=com.android.soundrecorder
     classname=com.android.soundrecorder.SoundRecorder

     YouTube ：

     package name=com.google.android.youtube
     classname=com.google.android.youtube.app.honeycomb.Shell$HomeActivity

     Calculator （计算器）：

     package name=com.android.calculator2
     classname=com.android.calculator2.Calculator

     Downloads （下载）：

     package name=com.android.providers.downloads.ui
     classname=com.android.providers.downloads.ui.DownloadList

     FM Radio（收音机）：

     package name=com.pekall.fmradio
     classname=com.pekall.fmradio.FMRadio

     Maps （google 地图）：

     package name=com.google.android.apps.maps
     classname=com.google.android.maps.MapsActivity

     WhatsApp ：

     package name=com.whatsapp
     classname=com.whatsapp.Main

     Chrome（google浏览器）

     package name=com.android.chrome
     classname=com.google.android.apps.chrome.Main

     Facebook ：

     package name=com.facebook.katana
     classname=com.facebook.katana.LoginActivity

     Skype ：

     package name=com.skype.raider
     classname=com.skype.raider.Main

     Twitter ：

     package name=com.twitter.android
     classname=com.twitter.android.StartActivity

     LINE ：

     package name=jp.naver.line.android
     classname=jp.naver.line.android.activity.SplashActivity
     */

}
