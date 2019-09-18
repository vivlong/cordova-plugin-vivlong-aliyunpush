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
      var appClass =
        '<application android:name="com.alipush.PushApplication" tools:replace="android:allowBackup" android:allowBackup="false" android:networkSecurityConfig="@xml/network_security_config"';
      if (data.indexOf(appClass) == -1) {
        var result = data
          .replace(/<application/g, appClass)
          .replace(
            /<manifest/g,
            '<manifest xmlns:tools="http://schemas.android.com/tools"'
          );

        fs.writeFile(manifestFile, result, "utf8", function(err) {
          if (err)
            throw new Error("Unable to write into AndroidManifest.xml: " + err);
        });
        console.log(
          'android application add [android:name="' + appClass + '"]'
        );
      }
    });
  } else {
    console.error("AndroidManifest.xml is not existsSync.");
  }
  var emasFileSrc = path.join(
    context.opts.plugin.dir,
    "aliyun-emas-services.json"
  );
  var emasFileDest = path.join(platformRoot, "app/aliyun-emas-services.json");
  if (fs.existsSync(emasFileSrc)) {
    fs.copyFileSync(emasFileSrc, emasFileDest, function(err) {
      if (err)
        throw new Error("Copy aliyun-emas-services.json Failed.: " + err);
      console.log("Copy aliyun-emas-services.json Success.");
    });
  }
};
