package kelly.com.desafiostone.network;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kelly.com.desafiostone.models.Item;

/**
 * Created by kelly on 29/11/17.
 */

public class JsonParse {

    final static String TAG = JsonParse.class.getSimpleName();

    public static ArrayList<Item> parseItensFromJson(String itensJsonString){
        if (TextUtils.isEmpty(itensJsonString)) {
            return null;
        }

        ArrayList<Item> itensList = new ArrayList<>();

        try {
            JSONArray itensJsonArray = new JSONArray(itensJsonString);

            for (int i = 0; i < itensJsonArray.length(); i++) {

                Item item = new Item();

                JSONObject itensObject = itensJsonArray.getJSONObject(i);
                item.setTitle(itensObject.getString("title"));
                item.setPrice(itensObject.getDouble("price") /100);
                item.setSeller(itensObject.getString("seller"));
                item.setImage(itensObject.getString("thumbnailHd"));

                itensList.add(item);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the news JSON results", e);
        }
        return itensList;
    }

}
