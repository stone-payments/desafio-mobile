package com.am.store.starwars.helper.converter;

import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.exception.StarWarHelperException;
import com.am.store.starwars.helper.AndroidLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Augusto on 15/01/2017.
 */

public class DateConveter {

    private static final String LOG_CONSTANT = DateConveter.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    public static String MMDD = "MM/dd";
    public static String HHmm = "HH:mm";

    public String extractDate(long time, String pattern) throws StarWarHelperException {

        try {
            Date date = new Date();
            date.setTime(time);

            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to extract date form time ", time, " with pattern ", pattern, e);
            throw new StarWarHelperException(e);
        }
    }
}