package com.alipush;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.graphics.Color;
import android.content.pm.PackageManager;
import android.util.Log;

// import static com.alipush.PushUtils.initPushService;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
//import com.alibaba.sdk.android.push.register.MiPushRegister;
//import com.alibaba.sdk.android.push.register.OppoRegister;
//import com.alibaba.sdk.android.push.register.MeizuRegister;
//import com.alibaba.sdk.android.push.register.VivoRegister;

public class PushApplication extends Application {

    public static final String NotificationChannelId = "901209181657";

    // // 小米
    // private static String XiaoMiAppId = "";
    // private static String XiaoMiAppKey = "";
    // // OPPO
    // private static String OPPOAppKey = "";
    // private static String OPPOAppSecret = "";
    // // 魅族
    // private static String MeizuAppId = "";
    // private static String MeizuAppKey = "";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initPushService(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initPushService(final Context applicationContext) throws PackageManager.NameNotFoundException {

        //getConfig(applicationContext);

        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setLogLevel(CloudPushService.LOG_DEBUG);
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("==", "init cloudchannel success");
                String deviceId = pushService.getDeviceId();
                Log.i("== deviceId", deviceId + "");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i("==", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);

            }
        });

        // 注册NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = NotificationChannelId;
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "Notification";
            // 用户可以看到的通知渠道的描述
            String description = "Push Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 });
            // 最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);

            // 设置8.0系统的通知小图标,必须要纯色的图
            // PushServiceFactory.getCloudPushService().setNotificationSmallIcon(R.drawable.notify);
        }

        // 华为通道
        HuaWeiRegister.register(this);
        // // 小米通道
        // if (XiaoMiAppId != null && XiaoMiAppKey != null) {
        //     Log.i("XiaoMi Push registered", "XiaoMiAppId:" + XiaoMiAppId + " , XiaoMiAppKey:" + XiaoMiAppKey);
        //     MiPushRegister.register(applicationContext, XiaoMiAppId, XiaoMiAppKey);
        // }
        // // OPPO通道
        // if (OPPOAppKey != null && OPPOAppSecret != null) {
        //     Log.i("OPPO Push registered", "OPPOAppKey:" + OPPOAppKey + " , OPPOAppSecret:" + OPPOAppSecret);
        //     OppoRegister.register(applicationContext, OPPOAppKey, OPPOAppSecret);
        // }
        // // 魅族通道
        // if (MeizuAppId != null && MeizuAppkey != null) {
        //     Log.i("OPPO Push registered", "MeizuAppId:" + MeizuAppId + " , MeizuAppkey:" + MeizuAppkey);
        //     MeizuRegister.register(applicationContext, MeizuAppId, MeizuAppkey);
        // }
        // // VIVO通道
        // VivoRegister.register(applicationContext);
    }


}
