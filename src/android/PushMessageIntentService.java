package com.alipush;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.AliyunMessageIntentService;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by liyazhou on 17/8/22.
 * 为避免推送广播被系统拦截的小概率事件,我们推荐用户通过IntentService处理消息互调,接入步骤:
 * 1. 创建IntentService并继承AliyunMessageIntentService
 * 2. 覆写相关方法,并在Manifest的注册该Service
 * 3. 调用接口CloudPushService.setPushIntentService
 * 详细用户可参考:https://help.aliyun.com/document_detail/30066.html#h2-2-messagereceiver-aliyunmessageintentservice
 */

public class PushMessageIntentService extends AliyunMessageIntentService {
    private static final String TAG = "PushMessageIntentService";

    /** 回调类型 */
    private static final String ONMESSAGE = "message";
    private static final String ONNOTIFICATION = "notification";
    private static final String ONNOTIFICATIONOPENED = "notificationOpened";
    private static final String ONNOTIFICATIONRECEIVED = "notificationReceived";
    private static final String ONNOTIFICATIONREMOVED = "notificationRemoved";
    private static final String ONNOIFICATIONCLICKEDWITHNOACTION = "notificationClickedWithNoAction";
    private static final String ONNOTIFICATIONRECEIVEDINAPP = "notificationReceivedInApp";

    /**
     * 推送通知的回调方法
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        Log.i(TAG,"收到一条推送通知 ： " + title + ", summary:" + summary);
        sendPushData(ONNOTIFICATION, title, summary, extraMap);
    }

    /**
     * 推送消息的回调方法
     * @param context
     * @param cPushMessage
     */
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        Log.i(TAG,"收到一条推送消息 ： " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        sendPushData(ONMESSAGE, cPushMessage.getTitle(), cPushMessage.getContent(), null, null,
                cPushMessage.getMessageId());
    }

    /**
     * 从通知栏打开通知的扩展处理
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.i(TAG,"onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
        sendPushData(ONNOTIFICATIONOPENED, title, summary, extraMap);
    }

    /**
     * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.i(TAG,"onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
        sendPushData(ONNOIFICATIONCLICKEDWITHNOACTION, title, summary, extraMap);
    }

    /**
     * 通知删除回调
     * @param context
     * @param messageId
     */
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.i(TAG, "onNotificationRemoved ： " + messageId);
        try {
            JSONObject data = new JSONObject();
            setStringData(data, "id", messageId);
            setStringData(data, "type", ONNOTIFICATIONREMOVED);
            AliyunPush.pushData(data);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,相关详情请参考https://help.aliyun.com/document_detail/30066.html#h3-3-4-basiccustompushnotification-api
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     * @param openType
     * @param openActivity
     * @param openUrl
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.i(TAG,"onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
        sendPushData(ONNOTIFICATIONRECEIVEDINAPP, title, summary, extraMap, openUrl);
    }

    /**
     * 设定字符串类型JSON对象，如值为空时不设定
     *
     * @param jsonObject JSON对象
     * @param name       关键字
     * @param value      值
     * @throws JSONException JSON异常
     */
    private void setStringData(JSONObject jsonObject, String name, String value) throws JSONException {
        if (value != null && !"".equals(value)) {
            jsonObject.put(name, value);
        }
    }

    private void sendPushData(String type, String title, String content, Map<String, String> extraMap,
            String... openUrl) {
        try {
            JSONObject data = null;
            if (extraMap != null && !"".equals(extraMap)) {
                data = new JSONObject(extraMap);
            } else {
                data = new JSONObject();
            }
            if (openUrl.length != 0) {
                if (openUrl[0] != null && !"".equals(openUrl[0])) {
                    setStringData(data, "url", openUrl[0]);
                }
                if (openUrl.length > 1 && openUrl[1] != null && !"".equals(openUrl[1])) {
                    setStringData(data, "id", openUrl[1]);
                }
            }
            setStringData(data, "type", type);
            setStringData(data, "title", title);
            setStringData(data, "content", content);
            AliyunPush.pushData(data);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    private void sendPushData(String type, String title, String content, String extraMap) {
        Log.i(LOG_TAG, type);
        if (AliyunPush.pushCallbackContext == null) {
            return;
        }
        try {
            JSONObject data = new JSONObject();
            if (extraMap != null && !"".equals(extraMap)) {
                try {
                    data = new JSONObject(extraMap);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    setStringData(data, "extra", extraMap);
                }
            }
            setStringData(data, "type", type);
            setStringData(data, "title", title);
            setStringData(data, "content", content);
            AliyunPush.pushData(data);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
}