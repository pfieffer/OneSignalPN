package np.com.ravi.onesignalint;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by ravi on 12/16/17.
 */

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
