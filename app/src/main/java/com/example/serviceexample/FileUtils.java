package com.example.serviceexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;

public class FileUtils {
    public static Bitmap readBitmapFromFile(String filename, Context context)
    {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try
        {
            // get the filename from the handler object field
            //String filename = message.obj.toString();
            //open file, decide as bitmap and set in image view
            fis = context.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(fis);

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;


    }
}
