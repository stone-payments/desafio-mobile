package payments.stone.com.br.desafiomobile.commons;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class Utils {
    /**
     * Converting dp to pixel
     */
    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("products.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getPriceFormatted(long price) {
        return "R$ " + String.format("%.2f", price / 1000.0);
    }
}
