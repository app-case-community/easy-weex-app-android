# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.taobao.**
-dontwarn com.alibaba.android.bindingx.**
-dontwarn com.alibaba.fastjson.**
-dontwarn com.alibaba.weex.**
-dontwarn com.taobao.weex.**
-dontwarn com.taobao.gcanvas.**
-dontwarn com.alibaba.android.arouter.**
-dontwarn cn.bertsir.zbar.**

-dontnote android.**
-dontnote com.android.**
-dontnote kotlin.reflect.**
-dontnote kotlin.internal.**
-dontnote com.facebook.**
-dontnote org.apache.**
-dontnote com.taobao.gcanvas.**
-dontnote com.taobao.weex.analyzer.**
-dontnote com.taobao.weex.devtools.**
-dontnote com.alibaba.android.bindingx.**

-keep class bolts.** { *; }
-keep class okio.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep class com.google.zxing.** { *; }
-keep class cn.bertsir.zbar.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-keep class com.alibaba.android.arouter.** { *; }
-keep class com.taobao.weex.** { *; }
-keep class com.alibaba.weex.** { *; }
-keep class com.taobao.gcanvas.** { *; }
-keep class com.alibaba.android.bindingx.** { *; }
-keep class com.airbnb.lottie.** { *; }
-keep class com.facebook.** { *; }