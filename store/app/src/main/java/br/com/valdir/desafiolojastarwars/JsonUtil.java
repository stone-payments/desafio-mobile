package br.com.valdir.desafiolojastarwars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* {
          "page":1,
          "results": [],
          "total_results": 19640,
          "total_pages": 982
} */

public class JsonUtil {

    public static List<ItemProduto> fromJsonToList(String json) {
        List<ItemProduto> list = new ArrayList<>();
        try {

            JSONArray results = new JSONArray(json);

            for (int i = 0; i < results.length(); i++) {
                JSONObject produtoObject = results.getJSONObject(i);
                ItemProduto itemProduto = new ItemProduto(i+1, produtoObject);
                list.add(itemProduto);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
