package kelly.com.desafiostone.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kelly.com.desafiostone.models.Item;
import kelly.com.desafiostone.models.FullTransaction;
import kelly.com.desafiostone.models.ShortTransaction;

/**
 * Created by kelly on 29/11/17.
 */

public class QueryUtils {
    final static String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}

    public static ArrayList<Item> getItensData (String getUrl) {

        URL url = createUrl(getUrl);

        String jsonResponse = null;

        try {

            jsonResponse = makeHttpGet(url);

        } catch (IOException e) {
            Log.e(TAG, "HTTP request failed", e);
        }

        ArrayList<Item> itensList = JsonParse.parseItensFromJson(jsonResponse);

        return itensList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem with  the URL", e);
        }
        return url;
    }

    private static String makeHttpGet(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String sendTransaction(String postUrl, FullTransaction fullTransaction) throws UnsupportedEncodingException {
        URL url = createUrl(postUrl);

        String jsonResponse = null;
        String data = null;

        if (fullTransaction != null){
            data = getPostDataString(fullTransaction.toHashMap());
        }

        try {

            jsonResponse = makeHttpPost(url, data);

        } catch (IOException e) {
            Log.e(TAG, "HTTP request failed", e);
        }

        return jsonResponse;
    }

    private static String makeHttpPost (URL url, String data) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write( data );
            wr.flush();

            if (urlConnection.getResponseCode() == 201) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem sending JSON data.", e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
