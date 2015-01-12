# Otto
-keepattributes *Annotation*
-keepclassmembers class ** {
     @com.squareup.otto.Subscribe public *;
     @com.squareup.otto.Produce public *;
}

# Butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

# Facebook
-keep class com.facebook.** { *; }
-keepattributes Signature
