package com.alipush;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.MeizuRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;

import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;

// import com.growingio.android.sdk.collection.Configuration;
// import com.growingio.android.sdk.collection.GrowingIO;

public class PushApplication extends Application {

    private static final String TAG = "Cordova Alipush PushApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // initGrowingIO(this);
            initPushService(this);
            initManService(this);
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
        ApplicationInfo appInfo = applicationContext.getPackageManager()
                .getApplicationInfo(applicationContext.getPackageName(), PackageManager.GET_META_DATA);
        final String XiaoMiAppId = appInfo.metaData.get("XIAOMI_APPID").toString();
        final String XiaoMiAppKey = appInfo.metaData.get("XIAOMI_APPKEY").toString();
        final String OPPOAppKey = appInfo.metaData.get("OPPO_APPKEY").toString();
        final String OPPOAppSecret = appInfo.metaData.get("OPPO_SECRET").toString();
        final String VIVOAppId = appInfo.metaData.get("com.vivo.push.app_id").toString();
        final String VIVOAppKey = appInfo.metaData.get("com.vivo.push.api_key").toString();
        final String MeizuAppId = appInfo.metaData.get("MEIZU_APPID").toString();
        final String MeizuAppKey = appInfo.metaData.get("MEIZU_APPKEY").toString();
        final String NotificationChannelId = appInfo.metaData.get("NotificationChannelId").toString();

        // 创建NotificationChannel
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
            // mChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/qqqq"), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            // 最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);

            // 设置8.0系统的通知小图标,必须要纯色的图
            // PushServiceFactory.getCloudPushService().setNotificationSmallIcon(R.drawable.notify);
        }

        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setLogLevel(CloudPushService.LOG_INFO);
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "init cloudchannel success");
                String deviceId = pushService.getDeviceId();
                Log.i(TAG, "deviceId " + deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 华为通道
        HuaWeiRegister.register(this);
        // OPPO通道
        if (OPPOAppKey != null && OPPOAppKey.length() > 1 && OPPOAppSecret != null && OPPOAppSecret.length() > 1) {
            Log.i(TAG, "OPPO Push registered - OPPOAppKey:" + OPPOAppKey + " , OPPOAppSecret:" + OPPOAppSecret);
            OppoRegister.register(applicationContext, OPPOAppKey, OPPOAppSecret);
        }
        // 小米通道
        if (XiaoMiAppId != null && XiaoMiAppId.length() > 1 && XiaoMiAppKey != null && XiaoMiAppKey.length() > 1) {
            Log.i(TAG, "XiaoMi Push registered - XiaoMiAppId:" + XiaoMiAppId + " ,XiaoMiAppKey:" + XiaoMiAppKey);
            MiPushRegister.register(applicationContext, XiaoMiAppId, XiaoMiAppKey);
        }
        // 魅族通道
        if (MeizuAppId != null && MeizuAppId.length() > 1 && MeizuAppkey != null && MeizuAppkey.length() > 1) {
            Log.i(TAG, "OPPO Push registered - MeizuAppId:" + MeizuAppId + " , MeizuAppkey:" + MeizuAppkey);
            MeizuRegister.register(applicationContext, MeizuAppId, MeizuAppkey);
        }
        // VIVO通道
        if (VIVOAppId != null && VIVOAppId.length() > 1 && VIVOAppkey != null && VIVOAppkey.length() > 1) {
            Log.i(TAG, "VIVO Push registered - VIVOAppId:" + VIVOAppId + " , VIVOAppkey:" + VIVOAppkey);
            VivoRegister.register(applicationContext);
        }
    }

    /**
     * 初始化移动数据分析
     *
     * @param applicationContext
     */
    private void initManService(final Context applicationContext) throws PackageManager.NameNotFoundException {
        // 获取MAN服务
        MANService manService = MANServiceProvider.getService();
        // 打开调试日志，线上版本建议关闭
        // manService.getMANAnalytics().turnOnDebug();
        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作(如需关闭crash report，建议在init方法调用前关闭crash),详见文档5.4
        // manService.getMANAnalytics().turnOffCrashReporter();
        // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可；1.1.6版本及之后的版本，请在init方法之前调用此方法设置channel.
        // manService.getMANAnalytics().setChannel("某渠道");
        // MAN初始化方法之一，从AndroidManifest.xml中获取appKey和appSecret初始化，若您采用上述
        // 2.3中"统一接入的方式"，则使用当前init方法即可。
        manService.getMANAnalytics().init(this, getApplicationContext());
    }

    /**
     * 初始化GrowingIO
     *
     * @param applicationContext
     */
    // private void initGrowingIO(final Context applicationContext) throws PackageManager.NameNotFoundException {
    //     GrowingIO.startWithConfiguration(this, new Configuration());
    // }
}
