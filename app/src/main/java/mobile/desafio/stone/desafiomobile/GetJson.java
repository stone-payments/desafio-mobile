package mobile.desafio.stone.desafiomobile;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gerson on 05/04/2017.
 */

public class GetJson extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... url) {

        String message = "";
        String targetUrl = url[0];

        Request request = new Request.Builder().url(targetUrl).build();
        try {
            Response response = client.newCall(request).execute();
            message = response.body().string();
        }
        catch (IOException e) {
        }

        return message;
    }

    protected void onPostExecute(String message){
        Log.d("TAG", message);
        delegate.processFinish(message);
    }

}
