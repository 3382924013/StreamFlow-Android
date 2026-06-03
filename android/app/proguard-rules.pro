# StreamFlow ProGuard Rules

# Keep public classes and methods
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment

# Keep model classes
-keep class com.streamflow.model.** { *; }
-keep class com.streamflow.data.** { *; }
-keep class com.streamflow.utils.** { *; }

# Keep UI classes
-keep class com.streamflow.ui.** { *; }
-keep class com.streamflow.adapter.** { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# RecyclerView
-keep class androidx.recyclerview.widget.** { *; }

# ViewPager2
-keep class androidx.viewpager2.widget.** { *; }

# Material Design
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# CircleImageView
-keep class de.hdodenhof.circleimageview.** { *; }

# Flexbox
-keep class com.google.android.flexbox.** { *; }

# General AndroidX
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
