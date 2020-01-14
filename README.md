# cordova-plugin-vivlong-aliyunpush ![npm](https://img.shields.io/npm/dt/cordova-plugin-vivlong-aliyunpush)

## Install

> 注意：
> - ionic cordova plugin add cordova-plugin-vivlong-aliyunpush --variable HMSAPPID=XXXXXX --variable OPPOAPPKEY=XXXXXX --variable OPPOSECRET=XXXXXX --variable VIVOAPPID=XXXXXX --variable VIVOAPPKEY=XXXXXX --variable ALIAPPKEY=XXXXXX --variable ALIAPPSECRET=XXXXXX
> - 阿里云EMAS上下载aliyun-emas-services.json文件，复制到项目根目录\platforms\android\app\下即可，此目录是基于Cordova Android 7.0+才有

### Android Preferences

- 对应Android系统推送，如果需要支持华为、小米、Google FCM（原GCM）系统通道，请在此页面配置对应的参数信息。可以根据需要配置一种或多种厂商辅助通道。
- [阿里云推送官方文档](https://help.aliyun.com/document_detail/92837.html?spm=a2c4g.11174283.6.637.52eb6d16cxZ6zi)

> - CHANNELID:Android8.0以上通知的ID
> - ALIAPPKEY:可不配置，阿里云appKey，阿里云移动分析中奔溃分析业务所使用
> - ALIAPPSECRET:可不配置，阿里云appSecret，阿里云移动分析中奔溃分析业务所使用
> - HMSAPPID:可不配置，华为通道AppId，如已注册，需在阿里云后台推送配置中配置
> - XIAOMIAPPID:可不配置，小米通道AppId，如已注册，需在阿里云后台推送配置中配置
> - XIAOMIAPPKEY:可不配置，小米通道AppKey，如已注册，需在阿里云后台推送配置中配置
> - OPPOAPPKEY:可不配置，OPPO通道AppKey，如已注册，需在阿里云后台推送配置中配置
> - OPPOSECRET:可不配置，OPPO通道AppSecret，如已注册，需在阿里云后台推送配置中配置
> - VIVOAPPID:可不配置，VIVO通道AppId，如已注册，需在阿里云后台推送配置中配置
> - VIVOAPPKEY:可不配置，VIVO通道AppKey，如已注册，需在阿里云后台推送配置中配置
> - MEIZUAPPID:可不配置，MEIZU通道AppId，如已注册，需在阿里云后台推送配置中配置
> - MEIZUAPPKEY:可不配置，MEIZU通道AppSecret，如已注册，需在阿里云后台推送配置中配置

## Usage

### API

```
    /**
     * 获取设备唯一标识deviceId，deviceId为阿里云移动推送过程中对设备的唯一标识（并不是设备UUID/UDID）
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void}  
     */
    getDeviceId: function(successCallback, errorCallback)

    /**
     * 阿里云推送绑定账号名
     * @param  {string} account         账号
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void} 
     */
    bindAccount: function(account, successCallback, errorCallback)

    /**
     * 阿里云推送解除账号名,退出或切换账号时调用
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void} 
     */
    unbindAccount: function(successCallback, errorCallback)

    /**
     * 阿里云推送绑定标签
     * @param  {string[]} tags            标签列表
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void}  
     */
    bindTags: function(tags, successCallback, errorCallback) 

    /**
     * 阿里云推送解除绑定标签
     * @param  {string[]} tags            标签列表
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void}               
     */
    unbindTags: function(tags, successCallback, errorCallback)

    /**
     * 阿里云推送解除绑定标签
     * @param  {Function} successCallback 成功回调
     * @param  {Function} errorCallback   失败回调
     * @return {void}           
     */
    listTags: function(successCallback, errorCallback) 
    
    /**
    * 阿里云推送消息透传回调
    * @param  {Function} successCallback 成功回调
    */
    onMessage:function(sucessCallback) ;

    # sucessCallback:调用成功回调方法，注意没有失败的回调，返回值结构如下：
    #json: {
      type:string 消息类型,
      title:string '阿里云推送',
      content:string '推送的内容',
      extra:string | Object<k,v> 外健,
      url:路由（后台发送推送时，在ExtParameters参数里写入url如{url:'demoapp://...'}）
    }

    #消息类型
    {
      message:透传消息，
      notification:通知接收，
      notificationOpened:通知点击，
      notificationReceived：通知到达，
      notificationRemoved：通知移除，
      notificationClickedWithNoAction：通知到达，
      notificationReceivedInApp：通知到达打开 app
    }

```

### Ionic3

```
  declare var AliyunPush: any;

  export class AliyunPushPlugin {

    public static getDeviceId() {
      return new Promise((resolve, reject) => {
        AliyunPush.getDeviceId(
          result => {
            resolve(result);
          },
          error => {
            reject(error);
          }
        );
      });
    }

  }
```
