package com.cloud.shangwu.businesscloud.im.gcm;


import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.SplashActivity;
import com.quickblox.sample.core.gcm.CoreGcmPushListenerService;
import com.quickblox.sample.core.utils.NotificationUtils;
import com.quickblox.sample.core.utils.ResourceUtils;

public class GcmPushListenerService extends CoreGcmPushListenerService {
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void showNotification(String message) {
        NotificationUtils.showNotification(this, SplashActivity.class,
                ResourceUtils.getString(R.string.notification_title), message,
                R.mipmap.ic_notification, NOTIFICATION_ID);
    }
}