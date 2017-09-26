package payments.stone.com.br.desafiomobile.commons;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    public static String dateFrom(String dateIso8691){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date result = new Date();
        try {
            result = dateFormat.parse(dateIso8691);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormatted = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormatted.format(result);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static String dateToIso(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return  dateFormat.format(date);
    }

    public static String dateTo(String toFormat){
        SimpleDateFormat dfDateTime  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        int year = randBetween(2017, 2017);// Here you can set Range of years you need
        int month = randBetween(8, 11);
        int hour = randBetween(9, 22); //Hours will be displayed in between 9 to 22
        int min = randBetween(0, 59);
        int sec = randBetween(0, 59);


        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, gc.getActualMaximum(gc.DAY_OF_MONTH));

        gc.set(year, month, day, hour, min,sec);

        return dfDateTime.format(gc.getTime());
    }
}
