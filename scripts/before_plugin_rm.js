module.exports = function(context) {
  var fs = require("fs"),
    path = require("path");

  var platformRoot = path.join(context.opts.projectRoot, "platforms/android");
  var plugins = context.opts.plugins || [];
  // The plugins array will be empty during platform add
  if (
    plugins.length > 0 &&
    plugins.indexOf("cordova-plugin-aliyunpush") === -1
  ) {
    return;
  }
  var manifestFile = path.join(platformRoot, "AndroidManifest.xml");
  if (!fs.existsSync(manifestFile)) {
    manifestFile = path.join(platformRoot, "app/src/main/AndroidManifest.xml");
  }
  console.log("platformRoot:" + manifestFile);
  if (fs.existsSync(manifestFile)) {
    fs.readFile(manifestFile, "utf8", function(err, data) {
      if (err) {
        throw new Error("Unable to find AndroidManifest.xml: " + err);
      }
      var appClass = 'android:name="com.alipush.PushApplication"';
      if (data.indexOf(appClass) != -1) {
        var result = data
          .replace(appClass, "")
          .replace('tools:replace="android:allowBackup"', "")
          .replace('android:allowBackup="false"', "")
          .replace(
            'android:networkSecurityConfig="@xml/network_security_config"',
            ""
          )
          .replace('xmlns:tools="http://schemas.android.com/tools"', "");

        fs.writeFile(manifestFile, result, "utf8", function(err) {
          if (err)
            throw new Error("Unable to write into AndroidManifest.xml: " + err);
        });
        console.log(
          'android application [android:name="' + appClass + '"] has removed'
        );
      }
    });
  } else {
    console.error("AndroidManifest.xml is not existsSync.");
  }
  var emasFile = path.join(platformRoot, "app/aliyun-emas-services.json");
  if (fs.existsSync(emasFile)) {
    fs.unlink(emasFile, function(err) {
      if (err)
        throw new Error("Remove aliyun-emas-services.json Failed.: " + err);
      console.log("Remove aliyun-emas-services.json success.");
    });
  }
};
