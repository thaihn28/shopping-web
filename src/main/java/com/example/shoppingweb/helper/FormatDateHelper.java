package com.example.shoppingweb.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateHelper {
    private static FormatDateHelper formatDateHelper;
    private static  SimpleDateFormat spm;
    private FormatDateHelper() {

    }

    public static FormatDateHelper getInstance() {
        if (formatDateHelper == null) {
            formatDateHelper = new FormatDateHelper();
            spm  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }

        return formatDateHelper;
    }

    public String format(Date date) throws ParseException {
        return spm.format(date);
    }
}
