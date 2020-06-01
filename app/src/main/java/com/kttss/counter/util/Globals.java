package com.kttss.counter.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Globals {

    //Dates formats
    public static final String DB_DATE_FORMAT="yyyy-MM-dd";
    public static final DateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");
    public static final String DISPLAY_DATE_FORMAT="dd/MM/yyyy";

    public static final String SENTRY_KEY_CRYPTED="76D460C7A5EB8B2E181B32E580A382F584C7F89BFE63DDADFF1526E20CFA2214BDF9137BEE962A2E67326333AF3F225F26EB2ACB5A4330B75AD4387BFEE447AE";

    //Globals OPERATIONS
    public static final String EDIT_OP ="EDIT";
    public static final String DELETE_OP="DELETE";
    public static final String ADD_OP="ADD";

    public static final String AD_MOB_APP_ID="ca-app-pub-1979556639756162~3646261152";
    public static final String AD_MOB_BANNER_KEY="ca-app-pub-1979556639756162/4576199440";
    public static final String AD_MOB_INTERSTITIAL="ca-app-pub-1979556639756162/3048789925";

    public static final boolean WITH_ADS=true;


    //NUMBERS FORMAT
    public static NumberFormat FORMAT_DOUBLE = new DecimalFormat("#0.00");

    //Generate Rundom Int Number
    public static int ROUNDOM_INT(int min, int max){
        Random rn = new Random();
        int range = max - min + 1;
        int randomNum =  rn.nextInt(range) + min;
        return randomNum;
    }
}
