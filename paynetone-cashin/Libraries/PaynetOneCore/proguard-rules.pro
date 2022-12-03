# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\DepTools\adt-bundle-windows-x86_64-20130917\sdk/tools/proguard/proguard-android.txt
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

# For android test
-dontwarn com.google.common.**

-keepattributes *Annotation*,EnclosingMethod,Signature

########### For Android Support libs ###########
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

########### For Android Support Design ###########
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

########### For Android Support CardView ###########
-keep class android.support.v7.widget.RoundRectDrawable { *; }

########### For Android appcombat ###########
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

########## For RecyclerView$LayoutManager ###########
-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}

########### For v7 ###########
-keep class android.support.v7.widget.** { *; }
-dontwarn android.support.v7.widget.**

########### For butter knife ###########
-dontwarn butterknife.internal.**
-dontwarn butterknife.Views$InjectViewProcessor
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

########### For retrofit2 ###########
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**

########### For Glide ###########
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# todo check
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
#-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


# Keep the support library
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }


-keep class com.google.analytics.** { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# Application classes that will be serialized/deserialized over Gson
# or have been blown up by ProGuard in the past

## ---------------- End Project specifics ---------------- ##



-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-keep class org.apache.http.** { *; }
-keep class javax.annotation.** { *; }
-keep class sun.misc.Unsafe.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

-dontwarn com.squareup.okhttp.**
-dontwarn org.apache.http.**
-dontwarn org.apache.commons.**
-dontwarn android.net.**
-dontwarn rx.**
-dontwarn retrofit.**
-dontwarn retrofit2.Platform$Java8
-dontwarn okio.**
-dontwarn fi.foyt.foursquare.**
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**
#Realm
-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**


-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class com.squareup.okhttp3.** {
*;
}
-keep class sun.misc.Unsafe { *; }
#your package path where your gson models are stored
-keep class com.example.models.** { *; }

-dontnote com.android.vending.licensing.ILicensingService
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote org.apache.commons.logging.**
-dontnote com.android.internal.http.multipart.**
-dontnote libcore.icu.ICU
-dontnote sun.misc.Unsafe
#-dontwarn com.android.support:support-v4
#-dontwarn com.android.support:appcompat-v7
#-dontwarn com.google.android.gms:play-services-gcm
#-dontwarn com.google.android.gms:play-services-analytics
-dontwarn com.google.android.gms.**


#To remove debug logs:
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}


# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-ignorewarnings
-keep public class * {
    public private *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep public class com.google.android.gms.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

#BEGIN HIEUNV9 Tich hop Cisco SDK

#-keep class javax.ws.rs.** { *; }
#-keep class org.joda.time.** { *; }
#-keep class javax.** { *; }
#-keep class java.beans.** { *; }
#-keep class org.w3c.** { *; }
#
#-dontwarn javax.ws.rs.**
#-dontwarn org.joda.time.**
#-dontwarn javax.**
#-dontwarn org.w3c.**
#-dontwarn java.beans.**

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keep class org.webrtc.** { *; }
-keep public class org.webrtc.** { *; }

# Jackson refers to these but they're unavailable/unused in typical runtimes
-dontwarn javax.xml.**
-dontwarn javax.activation.**
-dontwarn org.joda.**
-dontwarn javax.ws.rs.**
-dontwarn org.w3c.dom.bootstrap.**
-dontwarn java.beans.Introspector
#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-dontwarn com.malinskiy.superrecyclerview.SwipeDismissRecyclerViewTouchListener*
-dontwarn com.google.common.base.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.collect.**
-dontwarn com.google.common.escape.**
-dontwarn com.google.common.eventbus.**
-dontwarn com.google.common.hash.**
-dontwarn com.google.common.io.**
-dontwarn com.google.common.math.**
-dontwarn com.google.common.net.**
-dontwarn com.google.common.primitives.**
-dontwarn com.google.common.reflect.**
-dontwarn com.google.common.util.concurrent.**
-dontwarn khttp3.ws.WebSocketCall.**
-dontwarn com.google.common.html.package-info.**

-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**
-keep public class io.realm.**
-keep class io.realm.** { *; }
#END HIEUNV29

-dontwarn com.google.common.base.**
-keep class com.google.common.base.** {*;}
-dontwarn com.google.errorprone.annotations.**
-keep class com.google.errorprone.annotations.** {*;}
-dontwarn   com.google.j2objc.annotations.**
-keep class com.google.j2objc.annotations.** { *; }
-dontwarn   java.lang.ClassValue
-keep class java.lang.ClassValue { *; }
-dontwarn   org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement { *; }
-dontwarn javax.annotation.**

####### For event bus ##########
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#viewpager indicator custom
-keep class cn.trinea.android.** { *; }
-keepclassmembers class cn.trinea.android.** { *; }
-dontwarn cn.trinea.android.**
#facebook
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#
#  compile 'com.mikepenz:iconics-core:1.7.1@aar'
#

-keep class com.mikepenz.iconics.** { *; }
-keep class com.mikepenz.community_material_typeface_library.CommunityMaterial
-keep class com.mikepenz.fontawesome_typeface_library.FontAwesome
-keep class com.mikepenz.google_material_typeface_library.GoogleMaterial
-keep class com.mikepenz.meteocons_typeface_library.Meteoconcs
-keep class com.mikepenz.octicons_typeface_library.Octicons

-keep class .R
-keep class **.R$* {
    <fields>;
}

# fresco

-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
@com.facebook.common.internal.DoNotStrip *;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

#com.daimajia.slider:library
-keep class com.daimajia.androidanimations.** { *; }
-keep interface com.daimajia.androidanimations.** { *; }

#Parceler library
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

# Retrofit
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
# We'll keep everything intact except for our classes (just to make things easier)
-keep class !com.apothesource.** { *; }
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

#sweet alert
 -keep class com.ontbee.legacyforks.cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
 }

-keepattributes EnclosingMethod
-keepattributes InnerClasses

#barcode
-keep class me.dm7.barcodescanner.** {
  *;
}

-keep class net.sourceforge.zbar.** {
  *;
}
-keepclassmembers class net.sourceforge.zbar.ImageScanner { *; }
-keepclassmembers class net.sourceforge.zbar.Image { *; }
-keepclassmembers class net.sourceforge.zbar.Symbol { *; }
-keepclassmembers class net.sourceforge.zbar.SymbolSet { *; }