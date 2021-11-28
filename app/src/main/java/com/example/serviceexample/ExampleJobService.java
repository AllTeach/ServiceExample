package com.example.serviceexample;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private volatile boolean jobCancelled= false;
    private static final String IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/fir-updown-561a8.appspot.com/o/images%2Fking1.png?alt=media&token=ee775ab9-82ce-4a24-9882-ea2afaed75a4";


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        doWork(params);
        // false - means work has finished
        // true- there is still work to be done, not finished
        return true;
    }

    private void doWork(JobParameters jobParameters)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: in schduler JOB");
                for (int i = 0; i < 20; i++) {
                    // close thread if job is cancelled
                    if(jobCancelled)
                    {
                        Log.d(TAG, "job Cancelled close thread: " + i);
                        return;
                    }
                    Log.d(TAG, "run: " + i);
                    SystemClock.sleep(500);
                }
                downloadImage(IMAGE_URL);
                Log.d(TAG, "doWork: FINISHED");
                // other flag ius if we want to reschdule
                jobFinished(jobParameters, true);
            }
        }).start();
    }

    // when jbhj ob gets cancelled
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: cancelled");
        jobCancelled = true;
        // here boolean indicates whether we want to reschedule or not
        Log.d(TAG, "onStopJob: ");
        return true;
    }


    private void downloadImage(String urlStr) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            // Get InputStream from the image url
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // open and write downloaded data to file
            is = connection.getInputStream();
            fos = openFileOutput("image", MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();

            Log.d(TAG, "downloadImage: write data");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fos != null) {
                try {
                    fos.close();
                    Log.d(TAG, "downloadImage: close file output stream");


                } catch (IOException e) {
                }

            }
            if (is != null) {
                try {
                    is.close();
                    Log.d(TAG, "downloadImage: close input stream");

                } catch (IOException e) {
                }

            }

        }
    }
}