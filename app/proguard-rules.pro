# AutoService için keep kuralı
-keep class com.google.auto.service.** { *; }

# javax.lang.model sınıfları için keep kuralı
-keep class **.** { *; }

# Firebase için keep kuralları
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Eksik sınıflar için uyarıları engelle
-dontwarn javax.lang.model.**
-dontwarn com.emin.**  # Tüm proje içinde eksik sınıflar için uyarıları engelle
-dontwarn com.google.**  # Google kütüphaneleri için uyarıları engelle
-dontwarn com.squareup.**  # Square kütüphaneleri için uyarıları engelle
-dontwarn javax.tools.**
-dontwarn sun.reflect.annotation.**
-dontwarn org.openjdk.tools.javac.**

# Java tools sınıflarını keep et
-keep class javax.tools.** { *; }
-keep class sun.reflect.annotation.** { *; }
-keep class org.openjdk.tools.javac.** { *; }
-keep class dagger.** { *; }
-keep class androidx.room.** { *; }
