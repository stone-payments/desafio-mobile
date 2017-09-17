package victorcruz.dms.get_post_data;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostCardInformation extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... params) {

        try{

            // conecta na url recebida e configura o request
            URL url = new URL("http://private-2ac02-desafiomobile2.apiary-mock.com/transactions");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();


            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(params[0]);
            dataOutputStream.flush();
            dataOutputStream.close();

            System.out.println("posted... " + httpURLConnection.getResponseCode());

            httpURLConnection.disconnect();


        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
