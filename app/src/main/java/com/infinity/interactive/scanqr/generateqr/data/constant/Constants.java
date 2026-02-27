package com.infinity.interactive.scanqr.generateqr.data.constant;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;

import java.util.Date;


public class Constants {

    public static final int PERMISSION_REQ = 445;
    public static String SAVE_TO = "QR_Barcode_scanner";

    public static boolean removeAds=false;

    public static boolean timerStart=false;

    public static int AdsShowEditCount = 0;
    public static int AdsShowCountResult = 2;

    public static int TYPE_WEB = 1;
    public static int TYPE_YOUTUBE = 2;
    public static int TYPE_PHONE = 3;
    public static int TYPE_EMAIL = 4;
    public static int TYPE_TEXT = 5;
    public static int TYPE_BARCODE = 6;
    public static int TYPE_WIFI = 7;
    public static int TYPE_SMS = 8;
    public static int TYPE_VCARD = 9;
    public static int TYPE_GEO = 10;
    public static int TYPE_EVENT = 11;
    public static int TYPE_FACEBOOK = 12;
    public static int TYPE_TWITTER = 13;
    public static int TYPE_LINKDEIN = 14;
    public static int TYPE_INSTAGRAM = 15;
    public static int TYPE_WHATSAPP = 16;

    //Actions
    public static int SEARCH_IN_WEB = 1;
    public static int TO_CALL = 2;
    public static int ADD_CONTACT = 3;
    public static int GO_URL = 4;
    public static int SEND_EMAIL = 5;
    public static int SEND_SMS = 6;
    public static int WIFI_CONNECT = 7;

    //resultForFragment
    public static int SCAN_FRAGMENT = 1;
    public static int HISTORY_SCAN_FRAGMENT = 2;
    public static int HISTORY_GENERATE_FRAGMENT = 3;

    public static String name = "";
    public static String org = "";
    public static String title = "";
    public static String tel = "";
    public static String url = "";
    public static String email = "";
    public static String address = "";
    public static String birthday = "";
    public static String note = "";

    public static String emailType="";
    public static String subjectType="";
    public static String bodyType="";

    public static String geoLatiLongi="";
    public static String locationAddress="";
    public static String latitudeAddress="";
    public static String longitudeAddress="";

    public static String wifiName="";
    public static String wifiSec="";
    public static String wifiPass="";

    public static String titleEvent="";
    public static String eventDes="";
    public static String eventStartTime="";
    public static String eventEndTime="";
    public static String eventDescription="";
    public static String eventLocation="";
    public static String fbUserName="";

    public static Bitmap galleryBitmap= null;
    public static Bitmap editorBitmap= null;
    public static Bitmap editBitmap= null;

    public static int selectedIndex=0;

    public static int finalImageEditor=0;

    public static String barcodeType="";

    public static Bitmap finalBitmap=null;

    public static BarcodeFormat format=BarcodeFormat.CODE_128;

    public static int adLogic=1;

    public static int adLogicResultBottomBar =2;

//    public static int lockTemplate=1;
    public static int unLockTemplate=0;

    public  static boolean templateUnlockGenerator=false;

    public static boolean Banner=false;
    public static boolean country=false;

    public static boolean check = false;

    public static boolean appOpen=false;

    public static boolean selectFromMain=true;
    public static boolean templateUnlockFromInnerActivity=false;
    public static boolean posterSelected=false;

    public static boolean isSelectingFile=false;

    public static int badMaNikalDeneWalaAdClick=0;

    public static boolean nativeLoaded=false;

    public static Date oldDate;


}
