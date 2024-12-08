// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

}
buildscript {
    repositories {
        google()  // Firebase ve diğer Google kütüphaneleri için gerekli
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.4")  // Android Gradle Plugin
        classpath ("com.google.gms:google-services:4.3.15") // Firebase için gerekli classpath
    }
}