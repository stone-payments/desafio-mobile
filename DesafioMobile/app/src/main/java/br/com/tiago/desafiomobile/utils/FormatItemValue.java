package br.com.tiago.desafiomobile.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class FormatItemValue {

    @NonNull
    public static String sellerFormat(String seller) {
        return "Vendedor: ".concat(seller);
    }

    @NonNull
    public static String priceFormat(Double price) {
        return "R$".concat(String.valueOf(price));
    }

    @NonNull
    public static Bitmap imageFormat(String uri) throws IOException {
        try {
            return BitmapFactory.decodeStream(new URL(uri).openConnection().getInputStream());
        } catch (IOException ioe) {
            throw new IOException("Erro ao obter imagem: ");
        }
    }

    @NonNull
    public static String fateFormat(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return fmt.format(date);
    }

    @NonNull
    public static String cardNumberFormat(String cardNumber) {
        return cardNumber.substring(cardNumber.length() - 4);
    }


}
