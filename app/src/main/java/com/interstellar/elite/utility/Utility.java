package com.interstellar.elite.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class Utility {


    public static String PUSH_BROADCAST_ACTION = "Elite_Push_BroadCast_Action";
    public static String CHAT_BROADCAST_ACTION = "Elite_Chat_Action";
    public static String CHAT_DATA = "Elite_Chat_Data";
    public static String PUSH_NOTIFY = "notifyFlag";
    public static String PUSH_SHARED_DATA = "push_shared_data";
    public static String PUSH_DATA_FROM_LOGIN= "push_data_from_login";
    public static String PUSH_LOGIN_PAGE = "pushloginPage";


    public static MultipartBody.Part getMultipartImage(File file) {
        RequestBody imgBody = RequestBody.create( file ,MediaType.parse("image/*"));
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("docfile", file.getName(), imgBody);
        return imgFile;
    }


    public static HashMap<String, Integer> getBody(Context context, int userid, int doctype , int orderID) {
        HashMap<String, Integer> body = new HashMap<String, Integer>();


        body.put("userid", (userid));
        body.put("doctype", (doctype));
        body.put("order_id", (orderID));

        return body;
    }

    public static File createDirIfNotExists() {

        File file = new File(Environment.getExternalStorageDirectory(), "/ElitePlus");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");

            }
        }
        return file;
    }

    public static   String currentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("ddMMMyyyyhhmmss");  //it will give you the date in the formate that is given in the image
        String datetime = dateformat.format(c.getTime()); // it will give you the date
        return datetime;
    }
}
