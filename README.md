## OneSignal Basic Setup
>Get Push Notifications from OneSignal console to Android App. The app has no background service atm, so the app has to be running in order to receive Notifications.

### Screenshots
[![Screenshot_20171216-210533.jpg](https://s7.postimg.org/gf2qejpgr/Screenshot_20171216-210533.jpg)](https://postimg.org/image/renxq5fvr/)

[![Screenshot_20171216-212244.jpg](https://s7.postimg.org/7k1w41ntn/Screenshot_20171216-212244.jpg)](https://postimg.org/image/89kogeod3/)

### Installation ###
* Since the Android app does not do much I'll straightly show you the     `app/build.gradle` , `AppController` which is a class that extends `Application` class and the `AndroidManifest.xml ` which will help you setup OneSignal PN on android.
* But if you want to try this repository's app before you go all on your own. Just Clone the repository, change the onesignal app id in  app/build.gradle with your own OneSignal app id and just send a PN from the OneSignal console.

#### app/build.gradle
```
//added these lines
plugins {
    id 'com.onesignal.androidsdk.onesignal-gradle-plugin' version '0.8.0'
}
apply plugin: 'com.onesignal.androidsdk.onesignal-gradle-plugin'

repositories {
    maven { url 'https://maven.google.com' }
}
//upto here

apply plugin: 'com.android.application'

android {
    compileSdkVersion 26
    buildToolsVersion "27.0.1"
    defaultConfig {
        applicationId "np.com.ravi.onesignalint"
        minSdkVersion 15
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

//added the following line
        manifestPlaceholders = [onesignal_app_id: "Replace this with your onesingal app id, leave the quotes",
                                // Project number pulled from dashboard, local value is ignored.
                                onesignal_google_project_number: "REMOTE"]
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:26.+'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'

//finally this
    compile 'com.onesignal:OneSignal:[3.6.5, 3.99.99]'
}

```

#### AppController.java

```
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG); //for debugging purposes

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }
}

```

#### AndroidManifest.xml

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="np.com.ravi.onesignalint">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:name=".AppController"> <!--add this single line-->
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```


