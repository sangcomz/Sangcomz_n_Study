package xyz.sangcomz.sangcomz_n_study.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.TypedValue;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by sangc on 2015-12-18.
 */
public class Utils {
    public static int convertDP(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    public static File BitmapToFileCache(Bitmap bitmap) {
        final long unixTime = System.currentTimeMillis() / 1000L;
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/food_note_tmp/";
        String fileName = unixTime + "tmp_sns.jpg";
        File file = new File(filePath);
        OutputStream out = null;
        // If no folders
        if (!file.exists()) {
            file.mkdirs();
//             Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        File fileCacheItem = new File(file, fileName);
        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileCacheItem;
    }

    public static Drawable drawableFromUrl(Context context, String url) throws IOException {
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide
                    .with(context)
                    .load(url)
                    .asBitmap()
                    .into(600, 600)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new BitmapDrawable(null, myBitmap);
    }
}
