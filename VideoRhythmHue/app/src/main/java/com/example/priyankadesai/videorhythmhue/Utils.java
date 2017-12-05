package com.example.priyankadesai.videorhythmhue;

/**
 * Created by rajvi on 04-12-2017.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.Date;

import static android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC;

public class Utils {
    public static long   timer = 0;
    public static Bitmap takeScreenShot(View view) {
       // View view = activity.findViewById(R.id.videoView);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap b1 = null;
        Bitmap b = null;
        Uri uri = Uri.parse("android.resource://com.example.priyankadesai.videorhythmhue/" + R.raw.sample);
        retriever.setDataSource(view.getContext(), uri);
        b1 = retriever.getFrameAtTime(timer * 1000 * 1000 , OPTION_CLOSEST_SYNC);
        timer += 5;
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
