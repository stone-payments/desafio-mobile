package com.am.store.starwars.helper.formatter;

import com.am.store.starwars.exception.StarWarHelperException;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Augusto on 14/01/2017.
 */

public class CurrencyFormatter {

    public static String transformToCurrency(String value) throws StarWarHelperException {
        try {
            if (value != null && !"".equals(value)) {
                double parsed = Double.parseDouble(value);
                String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));
                formatted = formatted.replace("R$", "R$ ");
                return formatted;
            }

            return value;
        } catch (Exception e) {
            throw new StarWarHelperException("Problems to format value " + value, e);
        }
    }
}