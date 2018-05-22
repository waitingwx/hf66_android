# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidStudioSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes EnclosingMethod
-keepattributes *Annotation*
-keepattributes Signature
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#注意:
#1.引用外部的jar包 如果不是自己写的最好不混淆它们,因为外部jar包有可能已经混淆过
#2.不要混淆XML布局中使用的自定义控件类，混淆后加载布局会报找不到该控件错误
#3.不要混淆Manifests中配置的组件类，混淆后系统会找因不到该组件而报错
#------------------------------------------------------------------
#指定代码的压缩级别
-optimizationpasses 5
#表示混淆时不使用大小写混合类名,混淆后的类名为小写
-dontusemixedcaseclassnames
#混淆第三方库,加上此句后可在后面配置某些库不被混淆
-dontskipnonpubliclibraryclasses
#表示不进行优化，建议使用此选项，因为根据proguard-android-optimize.txt中的描述，优化可能会造成一些潜在风险，不能保证在所有版本的Dalvik上都正常运行。
#-dontoptimize
#表示不进行预校验,可以加快混淆速度。预校验是作用在Java平台上的，Android平台上不需要这项功能
#-dontpreverify
# 混淆时输出日志
-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #混淆时所采用的算法,一般不改变,用谷歌推荐算即可

#不混淆这些类的子类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#表示不混淆任何包含native方法的类的类名以及native方法名
-keepclasseswithmembernames class * {
    native <methods>;
}

#表示不混淆任何一个View中的setXxx()和getXxx()方法，因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#表示不混淆Activity中参数是View的方法，因为有这样一种用法，在XML中配置android:onClick=”buttonClick”属性，当用户点击该按钮时就会调用Activity中的buttonClick(View view)方法，如果这个方法被混淆的话就找不到了。
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#表示不混淆枚举中的values()和valueOf()方法
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#表示不混淆Parcelable实现类中的CREATOR字段，毫无疑问，CREATOR字段是绝对不能改变的，包括大小写都不能变，不然整个Parcelable工作机制都会失败。
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

#表示不混淆R文件中的所有静态字段，我们都知道R文件是通过字段来记录每个资源的id的，字段名要是被混淆了，id也就找不着了。
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembers class * {#保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
#
-keepclasseswithmembers class * {#保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#申明JAR
#-libraryjars libs/MobTools-2017.0607.1736.jar
#-libraryjars libs/SMSSDK-3.0.0.aar
#-libraryjars libs/SMSSDKGUI-3.0.0.aar

#忽略混淆时第三方包导致的异常
#-dontwarn com.android.volley.**
#不混淆第三方包
#-keep class com.android.volley.** { *;}

-dontwarn butterknife.**
-keep class butterknife.** { *;}
#
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *;}

-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}

-dontwarn com.google.**
-keep class com.google.gson.** {*;}

-dontwarn com.superrtc.sdk.**
-keep class com.superrtc.sdk.RtcConnection.** {*;}

-dontwarn com.hyphenate.**
-keep class com.hyphenate.** {*;}

-dontwarn android.net.http.**
-keep class android.net.http.** {*;}

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** {*;}

-dontwarn com.google.android.gms.common.**
# okio
-dontwarn okio.**
-keep class okio.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-dontwarn com.huawei.**
-keep class com.huawei.android.** {*;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
    public void onEventMainThread(**); #所有监听的方法都要列在这里
}


