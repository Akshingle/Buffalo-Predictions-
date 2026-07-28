# Keep classes that are used by reflection
-keep class com.buffalomilkpredictor.** { *; }

# TensorFlow Lite
-keep class org.tensorflow.** { *; }

# MediaPipe
-keep class com.google.mediapipe.** { *; }

# ML Kit
-keep class com.google.mlkit.** { *; }

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <fields>;
}

# Serialization
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Timber logging
-keep class timber.log.** { *; }
-keep interface timber.log.** { *; }
