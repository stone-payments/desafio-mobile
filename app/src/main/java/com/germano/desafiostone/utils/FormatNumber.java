package com.germano.desafiostone.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by germano on 27/08/17.
 */

public class FormatNumber {

    //Using German Locale to put "." instead "," on decimal separator
    public static String set(Number n) {
        NumberFormat format = DecimalFormat.getInstance(Locale.GERMAN);
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(3);
        return format.format(n);
    }
}
