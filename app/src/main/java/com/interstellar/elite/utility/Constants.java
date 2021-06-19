package com.interstellar.elite.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by IN-RB on 02-02-2018.
 */

public class Constants {

    public static int SPLASH_DISPLAY_LENGTH = 2000;
    public static String DB_PRODUCT_TYPE = "dashboard_product_type";
    public static String Secure_List = "dashboard_secure_list";
    public static String Assure_List = "dashboard_assure_list";
    public static String Rto_List = "dashboard_rto_list";
    public static String RTO_RC_BOOK_List = "rto_rc_book_assistance_list";
    public static String RTO_DRIVING_LICENSE_List = "rto_driving_license_assistance_list";
    public static int PAYMENT_SUCCESS = 1;
    public static int PAYMENT_FAILURE = 0;
    public static String PAYMENT_AMOUNT = "10.05";
    public static String SERVICE_RTO = "RTO";
    public static String SERVICE_NONRTO = "NONRTO";
    public static String SUB_PRODUCT_LIST = "SUB_PRODUCT_LIST";

    public static String SERVICE_TYPE = "ELITE_SERVICE_TYPE";

    public static String RTO_PRODUCT_DATA = "ELITE_RTO_PRODUCT_DATA";
    public static String NON_RTO_PRODUCT_DATA = "ELITE_NON_RTO_PRODUCT_DATA";

    public static String SUB_PRODUCT_DATA = "ELITE_SUB_RTO_PRODUCT_DATA";

    public static String PAYMENT_REQUEST_BUNDLE = "ELITE_REQUEST_BUNDLE";
    public static String PRODUCT_PAYMENT_REQUEST = "ELITE_PRODUCT_PAYMENT_REQUEST";
    public static String REQUEST_TYPE = "ELITE_REQUEST_TYPE";

    public static String FEEDBACK_DATA = "ELITE_FEEDBACK_DATA";
    public static String CHAT_REQUEST_DATA = "ELITE_CHAT_REQUEST_DATA";

    public static int SEARCH_CITY_CODE = 5;
    public static int SEARCH_MAKE_CODE = 10;
    public static int SEARCH_MODEL_CODE = 11;
    public static int COMPANY_INSURANCE_CODE = 6;
    public static int ORDER_CODE = 2;
    public static int UPLOAD_FILE = 4;
    public static int REQUEST_CODE = 22;
    public static String SERVICE_POSTION = "ELITE_SERVICE_POSTION";

    public static String SEARCH_CITY_DATA = "ELITE_SEARCH_CITY_DATA";
    public static String SEARCH_CITY_ID = "ELITE_SEARCH_CITY_ID";

    public static String SEARCH_MAKE_DATA = "ELITE_SEARCH_MAKE_DATA";
    public static String SEARCH_MAKE_ID = "ELITE_SEARCH_MAKE_ID";

    public static String SEARCH_MODEL_DATA = "ELITE_SEARCH_MODEL_DATA";
    public static String SEARCH_MODEL_ID = "ELITE_SEARCH_MODEL_ID";

    public static final int PERMISSION_CAMMERA_STORAGE_V11_CONSTANT = 104;
    public static final int PERMISSION_CAMERA_STORACGE_CONSTANT = 103;
    public static final int PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int REQUEST_PERMISSION_SETTING = 101;


    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkInternetStatus(Context context) {

        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()  && activeNetwork.isAvailable()) {
            // notify user you are online
            return true;
        }
        return false;
    }
}
