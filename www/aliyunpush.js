var AliyunPush = function() {};

AliyunPush.prototype.errorCallback = function(msg) {
  console.log("AliyunPush Callback Error: " + msg);
};

AliyunPush.prototype.callNative = function(
  name,
  args,
  successCallback,
  errorCallback
) {
  errorCallback = errorCallback || this.errorCallback;
  cordova.exec(successCallback, errorCallback, "AliyunPush", name, args);
};

/**
 * 获取设备唯一标识deviceId，deviceId为阿里云移动推送过程中对设备的唯一标识（并不是设备UUID/UDID）
 * @param  {Function} successCallback 成功回调
 * @param  {Function} errorCallback   失败回调
 * @return {void}
 */
AliyunPush.prototype.getRegisterId = function(successCallback) {
  this.callNative("getRegisterId", [], successCallback);
};

/**
 * 阿里云推送消息透传回调
 * @param  {Function} successCallback 成功回调
 * @return {void}
 */
AliyunPush.prototype.onMessage = function(successCallback) {
  this.callNative("onMessage", [], successCallback);
};

/**
 * 没有权限时，请求开通通知权限，其他路过
 * @param {} successCallback
 * @param {*} errorCallback
 */
AliyunPush.prototype.requireNotifyPermission = function(
  msg,
  successCallback,
  errorCallback
) {
  this.callNative(
    "requireNotifyPermission",
    [msg],
    successCallback,
    errorCallback
  );
};

/**
 * 阿里云推送绑定账号名
 * @param  {string} account           账号
 * @param  {Function} successCallback 成功回调
 * @param  {Function} errorCallback   失败回调
 * @return {void}
 */
AliyunPush.prototype.bindAccount = function(
  account,
  successCallback,
  errorCallback
) {
  this.callNative("bindAccount", [account], successCallback, errorCallback);
};

/**
 * 阿里云推送解除账号名,退出切换账号时调用
 * @param  {Function} successCallback 成功回调
 * @param  {Function} errorCallback   失败回调
 * @return {void}
 */
(AliyunPush.prototype.unbindAccount = function(successCallback, errorCallback) {
  this.callNative("unbindAccount", [], successCallback, errorCallback);
}),
  /**
   * 阿里云推送绑定标签
   * @param  {string[]} tags            标签列表
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  (AliyunPush.prototype.bindTags = function(
    tags,
    successCallback,
    errorCallback
  ) {
    this.callNative("bindTags", [tags], successCallback, errorCallback);
  });

/**
 * 阿里云推送解除绑定标签
 * @param  {string[]} tags            标签列表
 * @param  {Function} successCallback 成功回调
 * @param  {Function} errorCallback   失败回调
 * @return {void}
 */
AliyunPush.prototype.unbindTags = function(
  tags,
  successCallback,
  errorCallback
) {
  this.callNative("unbindTags", [tags], successCallback, errorCallback);
};

/**
 * 阿里云推送解除绑定标签
 * @param  {Function} successCallback 成功回调
 * @param  {Function} errorCallback   失败回调
 * @return {void}
 */
AliyunPush.prototype.listTags = function(successCallback, errorCallback) {
  this.callNative("listTags", [], successCallback, errorCallback);
};

if (!window.plugins) {
  window.plugins = {};
}

if (!window.plugins.aliyunPush) {
  window.plugins.aliyunPush = new AliyunPush();
}

module.exports = new AliyunPush();
