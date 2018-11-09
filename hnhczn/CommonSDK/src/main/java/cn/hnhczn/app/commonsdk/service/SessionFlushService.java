package cn.hnhczn.app.commonsdk.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseService;

import cn.hnhczn.app.commonsdk.receiver.AlarmReceiver;

/**
 * Created by FClever on 2018/8/3.
 */
public class SessionFlushService extends BaseService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO
        //在这里做定时任务的逻辑


        send();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void init() {

    }

    private void send(){
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        //10分钟发送一次广播
        int tenMin=1*1000*60*10;
        long triggerAtTime= SystemClock.elapsedRealtime()+tenMin;
        Intent i=new Intent(SessionFlushService.this,AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(SessionFlushService.this,0,i,0);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
    }
}
