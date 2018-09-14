# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/erin.kelley/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses,EnclosingMethod
-optimizationpasses 5
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

-dontwarn android.support.**
-dontwarn android.support.v7.**
-dontwarn com.squareup.picasso.**

-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentPagerAdapter
-keep public class * extends android.app.Fragment
-keep public class * extends android.widget.**
-keep public class * extends android.view.View
-keep class * extends android.support.v7.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.internal.v7.** { *; }

-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-keepclassmembers class com.app.excy.models.** { *; }

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}