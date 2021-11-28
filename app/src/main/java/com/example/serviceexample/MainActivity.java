package com.example.serviceexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID = 1;
    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startBackService(View view)
    {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);

    }

    public void stopBeckService(View view) {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }

    public void moveToSecond(View view)
    {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

    public void startMyForegroundService(View view)
    {
        Intent intent = new Intent(this,ForegroundService.class);
        ContextCompat.startForegroundService(this,intent);
    }

    public void scheduleJobCancel(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);
        Log.d(TAG, "scheduleJobCancel: ");
    }

    public void scheduleJobStart(View view)
    {
        ComponentName componentName = new ComponentName(this,ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true) // even if REBOOT -> needs permission boot completer
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        // over API21
        int resultCode = jobScheduler.schedule(info);

        Log.d(TAG, "scheduleJobStart: " + resultCode);


    }
}