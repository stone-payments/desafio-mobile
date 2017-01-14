package com.am.store.starwars.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.am.store.starwars.dao.ProductImageDAO;
import com.am.store.starwars.exception.StarWarIntegrationException;
import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.view.adapter.ProductViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Augusto on 14/01/2017.
 */
public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private static final String LOG_CONSTANT = BitmapDownloaderTask.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private String url;
    private final WeakReference<ImageView> imageViewReference;
    private Bitmap bitmapDecoded = null;
    private ProductImageDAO productImageDAO;
    private String downloadKey;

    public BitmapDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.productImageDAO = new ProductImageDAO();
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        try {
            // params comes from the execute() call: params[0] is the url.

            if(params!= null && params.length > 1) {
                downloadKey = params[1];
            }

            Bitmap bitmap = productImageDAO.getImage(downloadKey);

            if(bitmap!= null) {
                return bitmap;
            }
            else {
                return downloadBitmap(params[0]);
            }
        } catch (StarWarsException e) {
            logger.error(LOG_CONSTANT, "Problems to execute AsyncTask! ", e);
        }

        return null;
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {

        if (imageViewReference != null && bitmap != null) {

            ImageView imageView = imageViewReference.get();
            BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

            // Change bitmap only if this process is still associated with it
            if (this == bitmapDownloaderTask) {
                imageView.setImageBitmap(bitmap);
                try {
                    this.productImageDAO.insertImage(downloadKey, bitmap);
                } catch (StarWarPersistenceException e) {
                    logger.error(LOG_CONSTANT, "Problems to persist BitMap", e);
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url) throws StarWarIntegrationException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            InputStream bitmapAsStream = response.body().byteStream(); // Read the data from the stream
            bitmapDecoded = BitmapFactory.decodeStream(bitmapAsStream);
        } catch (IOException e) {
            logger.error(LOG_CONSTANT, "Problems during download of image from url " + url);
            throw new StarWarIntegrationException("Problems during download of image from url " + url, e);
        }

        return getResizedBitmap(bitmapDecoded, 40, 40);
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

    public String getUrl() {
        return this.url;
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {

        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.getUrl();
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask(imageView);
            }
        }
        return null;
    }

    private void download(String url, ImageView imageView) {
        if (cancelPotentialDownload(url, imageView)) {
            BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            imageView.setImageDrawable(downloadedDrawable);
            task.execute(url);
        }
    }

    public static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference =
                    new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
            return bitmapDownloaderTaskReference.get();
        }
    }
}