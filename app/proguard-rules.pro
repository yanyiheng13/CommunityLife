# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/yyh/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript behavior
# class:
#-keepclassmembers class fqcn.of.javascript.behavior.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# RecyclerViewAdapter混淆规则
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(android.view.View);
}

#微信
-keep class com.tencent.mm.opensdk.** {
   *;
}

-keep class com.tencent.wxop.** {
   *;
}

-keep class com.tencent.mm.sdk.** {
   *;
}