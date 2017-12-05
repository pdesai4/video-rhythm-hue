package com.example.priyankadesai.videorhythmhue;

/**
 * Created by rajvi on 04-12-2017.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Utils {

    public static Bitmap takeScreenShot(View view) {
       // View view = activity.findViewById(R.id.videoView);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Bitmap b = null;
        Rect frame = new Rect();
       // activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        //Find the screen dimensions to create bitmap in the same size.
        //int width = activity.getWindowManager().getDefaultDisplay(). getWidth();
        //int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        if(b1 != null){
            b = Bitmap.createBitmap(b1);
            FileOutputStream fos;
            long now = new Date().getTime();

            String path =  Environment.getExternalStorageDirectory().getAbsolutePath()  + "/PICTURES/test"+now+".jpg";

            File imageFile = new File(path);
            try {
                fos = new FileOutputStream(imageFile);
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                System.out.println("File is savedddddd ==> " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        view.destroyDrawingCache();
        System.out.println("The bitmap is ====>"+ b);
        return b;
    }

    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(strFileName);
            b.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
