package cn.hnhczn.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.hnhczn.app.mvp.ui.activity.WelcomeActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
            Intent mainActivityIntent = new Intent(context, WelcomeActivity.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
	}
 
}
