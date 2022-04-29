package com.example.shoppingweb.helper;

import java.text.DecimalFormat;

public class FormatPriceHelper {
    private static FormatPriceHelper instance;
    private static DecimalFormat decimalFormat;

    private FormatPriceHelper(){

    }

    public static FormatPriceHelper getInstance(){
        if(instance == null){
            instance = new FormatPriceHelper();
            decimalFormat = new DecimalFormat("###,###,###");
        }
        return instance;
    }

    public String formatPrice(int price){
        return decimalFormat.format(price);
    }
    public String formatDiscountPrice(Double price){
        return decimalFormat.format(price);
    }


}
