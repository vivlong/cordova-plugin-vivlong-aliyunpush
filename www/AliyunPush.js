var exec = require("cordova/exec");

var PLUGIN_NAME = "AliyunPush";

var AliyunPush = {
  errorCallback: function(msg) {
    console.log("AliyunPush Callback Error: " + msg);
  },

  callNative: function(name, args, successCallback, errorCallback) {
    if (errorCallback) {
      cordova.exec(successCallback, errorCallback, PLUGIN_NAME, name, args);
    } else {
      cordova.exec(
        successCallback,
        this.errorCallback,
        PLUGIN_NAME,
        name,
        args
      );
    }
  },

  /**
   * 阿里云推送消息透传回调
   * @param  {Function} successCallback 成功回调
   * @return {void}
   */
  onMessage: function(successCallback) {
    this.callNative("onMessage", [], successCallback);
  },

  /**
   * 没有权限时，请求开通通知权限，其他路过
   * @param {} successCallback
   * @param {*} errorCallback
   */
  requireNotifyPermission: function(msg, successCallback, errorCallback) {
    this.callNative(
      "requireNotifyPermission",
      [msg],
      successCallback,
      errorCallback
    );
  },

  /**
   * 获取设备唯一标识deviceId，deviceId为阿里云移动推送过程中对设备的唯一标识（并不是设备UUID/UDID）
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  getDeviceId: function(successCallback, errorCallback) {
    this.callNative("getDeviceId", [], successCallback, errorCallback);
  },

  /**
   * 阿里云推送绑定账号,将应用内账号和推送通道相关联，可以实现按账号的定点消息推送
   * @param  {string} account           账号
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  bindAccount: function(account, successCallback, errorCallback) {
    this.callNative("bindAccount", [account], successCallback, errorCallback);
  },

  /**
   * 阿里云推送解绑账号,将应用内账号和推送通道取消关联
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  unbindAccount: function(successCallback, errorCallback) {
    this.callNative("unbindAccount", [], successCallback, errorCallback);
  },

  /**
   * 阿里云推送绑定标签到指定目标
   * @param  {string[]} tags            标签列表
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  bindTags: function(tags, successCallback, errorCallback) {
    this.callNative("bindTags", [tags], successCallback, errorCallback);
  },

  /**
   * 阿里云推送解绑指定目标标签
   * @param  {string[]} tags            标签列表
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  unbindTags: function(tags, successCallback, errorCallback) {
    this.callNative("unbindTags", [tags], successCallback, errorCallback);
  },

  /**
   * 阿里云推送查询目标绑定标签
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  listTags: function(successCallback, errorCallback) {
    this.callNative("listTags", [], successCallback, errorCallback);
  },

  /**
   * 阿里云推送添加别名
   * @param  {string} alias             别名
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  addAlias: function(alias, successCallback, errorCallback) {
    this.callNative("addAlias", [alias], successCallback, errorCallback);
  },

  /**
   * 阿里云推送删除设备别名
   * @param  {string} alias             别名
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  removeAlias: function(alias, successCallback, errorCallback) {
    this.callNative("removeAlias", [alias], successCallback, errorCallback);
  },

  /**
   * 阿里云推送查询设备别名
   * @param  {Function} successCallback 成功回调
   * @param  {Function} errorCallback   失败回调
   * @return {void}
   */
  listAliases: function(successCallback, errorCallback) {
    this.callNative("listAliases", [], successCallback, errorCallback);
  },

  checkPushChannelStatus: function(successCallback) {
    this.callNative("checkPushChannelStatus", [], successCallback);
  },

  clearNotifications: function(successCallback) {
    this.callNative("clearNotifications", [], successCallback);
  },

  areNotificationsEnabled: function(successCallback) {
    this.callNative("areNotificationsEnabled", [], successCallback);
  },

  manUserRegister: function(usernick, successCallback) {
    this.callNative("manUserRegister", [usernick], successCallback);
  },

  manUpdateUserAccount: function(usernick, userid, successCallback) {
    this.callNative("manUpdateUserAccount", [usernick, userid], successCallback);
  },

};

module.exports = AliyunPush;