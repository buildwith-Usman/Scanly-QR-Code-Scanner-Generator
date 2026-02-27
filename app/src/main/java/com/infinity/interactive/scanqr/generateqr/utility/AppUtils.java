package com.infinity.interactive.scanqr.generateqr.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.activity.EntryActivity;
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference;
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey;


public class AppUtils {

    private static final int sel = 0;
    private static long backPressed = 0;
    private final int IDD_RATE_DIALOG = 0;
    AlertDialog.Builder ad;


    public static void tapToExit(Activity activity) {
        if (backPressed + 2500 > System.currentTimeMillis()) {
            activity.finishAffinity();
        } else {
            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tapAgain));
        }
        backPressed = System.currentTimeMillis();
    }

    public static void showRateDialog(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setTitle(activity.getResources().getString(R.string.rate_menu_title));
        alertDialog.setMessage(activity.getResources().getString(R.string.rate_menu_message));
        //alertDialog.setIcon(R.drawable.save);

        alertDialog.setPositiveButton(activity.getResources().getString(R.string.rate_menu_yes_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                rateThisApp(activity);
                AppPreference.getInstance(activity.getApplicationContext()).setInteger(PrefKey.RATE_DIALOG_VALUE, 1);
            }
        });

        alertDialog.setNegativeButton(activity.getResources().getString(R.string.rate_menu_no_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed No button. Write Logic Here
                AppPreference.getInstance(activity.getApplicationContext()).setInteger(PrefKey.RATE_DIALOG_VALUE, 1);
                activity.finishAffinity();
            }
        });

        alertDialog.setNeutralButton(activity.getResources().getString(R.string.rate_menu_maybe_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                activity.finishAffinity();
            }
        });

        alertDialog.show();

    }

    public static void showSettingsRateDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayout = vi.inflate(R.layout.rate_dialog, null);
        //alertDialog.setIcon(R.drawable.save);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(customLayout);


        TextView t1, t2, t3, t4, t5;

        t1 = customLayout.findViewById(R.id.starOne);
        t2 = customLayout.findViewById(R.id.starTwo);
        t3 = customLayout.findViewById(R.id.starThree);
        t4 = customLayout.findViewById(R.id.starFour);
        t5 = customLayout.findViewById(R.id.starFive);


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t2.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t3.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t4.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t5.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                Toast.makeText(activity, "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t2.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t3.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t4.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t5.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                Toast.makeText(activity, "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t2.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t3.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t4.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                t5.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                Toast.makeText(activity, "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t2.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t3.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t4.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t5.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_grey));
                alertDialog.dismiss();
                rateThisApp(activity);
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t2.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t3.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t4.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                t5.setBackground(activity.getResources().getDrawable(R.drawable.ratingstar_yellow));
                alertDialog.dismiss();
                rateThisApp(activity);
            }
        });


        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

    }


    public static void showPermissionDialog(final Activity activity, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.app_permission_dialog, null);
        AlertDialog alert = alertDialog.create();
        alert.setView(customLayoutpermission);
        //alertDialog.setIcon(R.drawable.save);
        TextView gotIt = customLayoutpermission.findViewById(R.id.got);
        TextView t1, t2, t3, t4, t5, t6, t7, t8;
        t1 = customLayoutpermission.findViewById(R.id.txt8);
        t2 = customLayoutpermission.findViewById(R.id.txt9);
        t3 = customLayoutpermission.findViewById(R.id.txt10);
        t4 = customLayoutpermission.findViewById(R.id.txt11);
        t5 = customLayoutpermission.findViewById(R.id.txt12);
        t6 = customLayoutpermission.findViewById(R.id.txt13);
        t7 = customLayoutpermission.findViewById(R.id.txt14);


        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alert.dismiss();
            }
        });


        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void share(Activity activity, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            /*This will be the actual content you wish you share.*/
//            String shareBody = "Here is the share content body";
            /*The type of the content is text, obviously.*/
            intent.setType("text/plain");
            /*Applying information Subject and Body.*/
//            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
            intent.putExtra(Intent.EXTRA_TEXT, text);
            /*Fire!*/
            activity.startActivity(Intent.createChooser(intent, "Share"));
//            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//            intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
//            activity.startActivity(Intent.createChooser(intent, "Share"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void shareApp(Activity activity) {
        share(activity, activity.getResources().getString(R.string.share) + " https://play.google.com/store/apps/details?id=" + activity.getPackageName());
    }

    public static void rateThisApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    public static void vibrateDevice(Context context) {
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        // Vibrate for 500 milliseconds
//        v.vibrate(500);
//    }


    public static void saveImage(Activity activity, String imageName, Bitmap bitmap) {
        String folderName = Constants.SAVE_TO;
        //to immediately display the saved image in the default gallery

        try {

            OutputStream fos;
            String imagePath;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                imagePath = Environment.DIRECTORY_PICTURES + File.separator + folderName;
                ContentResolver resolver = activity.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, imagePath);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            } else {

                imagePath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName;
                File file = new File(imagePath);
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdir();
                }
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                imageName = imageName + n;

                if (imageName.contains("/")) {
                    imageName = imageName.replace("/", "");
                    imageName = imageName.replace("/", "");

                }

                File image = new File(imagePath, imageName + ".png");
                fos = new FileOutputStream(image);

            }

            boolean saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Objects.requireNonNull(fos).flush();
            fos.close();

            if (saved) {
                new SingleMediaScanner(activity.getApplicationContext(), imagePath + File.separator + imageName + ".png");
                Toast.makeText(activity.getApplicationContext(),
                        activity.getString(R.string.saved_to) + " '" + Environment.DIRECTORY_PICTURES + File.separator + folderName + "'",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity.getApplicationContext(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }

    }

    public static void shareImage(Activity activity, Bitmap bitmap) {
        try {
            //saving file in cache directory
            File imagesFolder = new File(activity.getCacheDir(), "images");
            if (!imagesFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                imagesFolder.mkdir();
            }
            File file = new File(imagesFolder, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            stream.flush();
            stream.close();

            //for avoid some problem
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            //get file uri from file provider
            Uri uri = FileProvider.getUriForFile(activity.getApplicationContext(),
                    "com.infinity.interactive.scanqr.generateqr.fileprovider", file);

            //share
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
//
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
            activity.startActivity(Intent.createChooser(shareIntent, "Share via"));

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), e + "share", Toast.LENGTH_SHORT).show();
            Log.i("ShareError", e + "");
        }
    }


    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        showToast(context, context.getResources().getString(R.string.copied));
    }


    public static void searchInWeb(Activity activity, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, text);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                String escapedQuery = URLEncoder.encode(text, "UTF-8");
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent1);
            } catch (Exception e1) {
                Toast.makeText(activity.getApplicationContext(), "No browsers found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void executeAction(Activity activity, String finalReadyResult, String scannedResult, int type, int constant) {
        if (type == Constants.TYPE_WEB || type == Constants.TYPE_YOUTUBE || type == Constants.TYPE_FACEBOOK || type == Constants.TYPE_TWITTER || type == Constants.TYPE_INSTAGRAM || type == Constants.TYPE_LINKDEIN) {

            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(finalReadyResult)));
            } catch (Exception e) {
                Toast.makeText(activity, "An error occurred", Toast.LENGTH_SHORT).show();
            }


        } else if (type == Constants.TYPE_PHONE) {
            if (constant == Constants.TO_CALL) {
                if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", finalReadyResult, null)));
                }
            } else if (constant == Constants.ADD_CONTACT) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, "");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, finalReadyResult);
                int PICK_CONTACT = 100;
                activity.startActivityForResult(intent, PICK_CONTACT);
            }

        } else if (type == Constants.TYPE_SMS) {
            if (constant == Constants.ADD_CONTACT) {
                try {
                    String number = "", message = "";
                    scannedResult = scannedResult.replace("SMSTO:", "");
                    scannedResult = scannedResult.replace("smsto:", "");
                    number = scannedResult;
                    if (scannedResult.contains(":")) {
                        String[] str = scannedResult.split(":");
                        number = str[0];
                        message = str[1];
                        //scannedResult = number + "\n" + message;
                    }
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, "");
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                    int PICK_CONTACT = 100;
                    activity.startActivityForResult(intent, PICK_CONTACT);
                } catch (Exception e) {
                    Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                }
            } else if (constant == Constants.SEND_SMS) {
                try {
                    String number = "", message = "";
                    scannedResult = scannedResult.replace("SMSTO:", "");
                    scannedResult = scannedResult.replace("smsto:", "");
                    //scannedResult = "rrrrr";
                    number = scannedResult;
                    if (scannedResult.contains(":")) {
                        String[] str = scannedResult.split(":");
                        number = str[0];
                        if (str.length > 1) {
                            for (int i = 1; i < str.length; i++) {
                                if (message.equals("")) {
                                    message = str[i];
                                } else {
                                    message = message + ":" + str[i];
                                }
                            }
                        }
                        //scannedResult = number + "\n" + message;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                    intent.putExtra("sms_body", message);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (type == Constants.TYPE_EMAIL) {
            if (constant == Constants.SEND_EMAIL) {
                if (scannedResult.contains("mailto:") || scannedResult.contains("MAILTO:")) {
                    try {
                        scannedResult = scannedResult.replace("mailto:", "");
                        scannedResult = scannedResult.replace("MAILTO:", "");

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{scannedResult});
                        //i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                        //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                        activity.startActivity(Intent.createChooser(i, "Send Email"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity.getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                    }
                } else if (scannedResult.contains("matmsg:") || scannedResult.contains("MATMSG:")) {
                    try {
                        String email = "", sub = "", body = "";
                        Matcher m = Pattern.compile("to:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            email = m.group(1);
                            email = email.substring(0, email.indexOf(";"));

                        }
                        m = Pattern.compile("sub:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            sub = m.group(1);
                            sub = sub.substring(0, sub.indexOf(";"));
                        }
                        scannedResult = scannedResult.replace("\n", " ");
                        m = Pattern.compile("body:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            body = m.group(1);
                            body = body.substring(0, body.indexOf(";"));
                        }

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        i.putExtra(Intent.EXTRA_SUBJECT, sub);
                        i.putExtra(Intent.EXTRA_TEXT, body);
                        activity.startActivity(Intent.createChooser(i, "Send Email"));
                        //result = email + "\n" + sub + "\n" + body;
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity.getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else if (type == Constants.TYPE_TEXT || type == Constants.TYPE_GEO || type == Constants.TYPE_BARCODE) {
            searchInWeb(activity, finalReadyResult);
        } else if (type == Constants.WIFI_CONNECT) {
            try {

                String ssid = "", typeOfWIFI = "", password = "";
                Matcher m = Pattern.compile("s:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                while (m.find()) {
                    ssid = m.group(1);
                    ssid = ssid.substring(0, ssid.indexOf(";"));
                }
                m = Pattern.compile("t:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                while (m.find()) {
                    typeOfWIFI = m.group(1);
                    typeOfWIFI = typeOfWIFI.substring(0, typeOfWIFI.indexOf(";"));
                }
                m = Pattern.compile("p:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                while (m.find()) {
                    password = m.group(1);
                    password = password.substring(0, password.indexOf(";"));
                }
                if (typeOfWIFI.equals("")) typeOfWIFI = "nopass";

                WifiConfiguration conf = new WifiConfiguration();
                conf.SSID = "\"" + ssid + "\"";

                if (typeOfWIFI.equals("WEP")) {
                    conf.wepKeys[0] = "\"" + password + "\"";
                    conf.wepTxKeyIndex = 0;
                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                } else if (typeOfWIFI.equals("WPA")) {
                    conf.preSharedKey = "\"" + password + "\"";
                } else if (typeOfWIFI.equals("nopass")) {
                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Wi-Fi protocol error", Toast.LENGTH_SHORT).show();
                }
                WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.addNetwork(conf);

                WifiManager wifi = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);

                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                for (WifiConfiguration i : list) {
                    if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                        wifiManager.disconnect();
                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();
                        Toast.makeText(activity.getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
            }
        } else if (type == Constants.TYPE_VCARD) {
            String name = "", org = "", title = "", tel = "", url = "", email = "", adr = "", note = "";
            Matcher m;
            try {
                if (constant == Constants.TO_CALL) {
                    if (scannedResult.contains("BEGIN:VCARD") || scannedResult.contains("begin:vcard")) {
                        m = Pattern.compile("TEL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            tel = m.group(1);
                        }
                    } else {
                        m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            tel = m.group(1);
                            if (scannedResult.contains("MECARD") || scannedResult.contains("mecard")) {
                                tel = tel.substring(0, tel.indexOf(";"));
                            }
                        }
                    }

                    if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                        activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel, null)));
                    }
                } else if (constant == Constants.ADD_CONTACT) {
                    m = Pattern.compile("N:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                    while (m.find()) {
                        name = m.group(1);
                        if (scannedResult.contains("MECARD") || scannedResult.contains("mecard")) {
                            name = name.substring(0, name.indexOf(";"));
                        }
                    }
                    if (scannedResult.contains("BEGIN:VCARD") || scannedResult.contains("begin:vcard")) {
                        m = Pattern.compile("TEL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            tel = m.group(1);
                        }
                    } else {
                        m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            tel = m.group(1);
                            tel = tel.substring(0, tel.indexOf(";"));
                        }
                    }
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, tel);
                    int PICK_CONTACT = 100;
                    activity.startActivityForResult(intent, PICK_CONTACT);
                } else if (constant == Constants.GO_URL) {
                    m = Pattern.compile("URL:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                    while (m.find()) {
                        url = m.group(1);
                        if (scannedResult.contains("MECARD") || scannedResult.contains("mecard")) {
                            assert url != null;
                            url = url.substring(0, url.indexOf(";"));
                        }
                    }
                    assert url != null;
                    if (url.contains("http://") || url.contains("https://")) {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else {
                        url = "https://" + url;
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }

                } else if (constant == Constants.SEND_EMAIL) {
                    if (scannedResult.contains("BEGIN:VCARD") || scannedResult.contains("begin:vcard")) {
                        m = Pattern.compile("EMAIL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            email = m.group(1);
                        }
                    } else {
                        m = Pattern.compile("EMAIL:(.*)", Pattern.CASE_INSENSITIVE).matcher(scannedResult);
                        while (m.find()) {
                            email = m.group(1);
                            email = email.substring(0, email.indexOf(";"));
                        }
                    }

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    activity.startActivity(Intent.createChooser(i, "Send Email"));
                }
            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static ResultOfTypeAndValue getResourceType(String result) {

        if (result != null && (result.contains("https://youtu.be") || result.contains("https://www.youtube.com"))) {
            return new ResultOfTypeAndValue(Constants.TYPE_YOUTUBE, result);
        } else if (result != null && result.contains("https://www.facebook.com/")) {
            return new ResultOfTypeAndValue(Constants.TYPE_FACEBOOK, result);
        } else if (result != null && result.contains("https://twitter.com/")) {

            return new ResultOfTypeAndValue(Constants.TYPE_TWITTER, result);
        } else if (result != null && result.contains("https://www.linkedin.com/")) {

            return new ResultOfTypeAndValue(Constants.TYPE_LINKDEIN, result);
        } else if (result != null && result.contains("https://www.instagram.com/")) {

            return new ResultOfTypeAndValue(Constants.TYPE_INSTAGRAM, result);
        } else if (result != null && Patterns.WEB_URL.matcher(result).matches()) {
            if (!result.startsWith("http://") && !result.startsWith("https://")) {
                result = "http://" + result;
            }
            return new ResultOfTypeAndValue(Constants.TYPE_WEB, result);
        } else if (result != null && (result.contains("BEGIN:VCARD") || result.contains("begin:vcard")
                || result.contains("MECARD") || result.contains("mecard"))) {
            String name = "", org = "", title = "", tel = "", tel2 = "", url = "", email = "", adr = "", birthDay = "", note = "";
            try {
                Matcher m = Pattern.compile("N:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    name = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        name = name.substring(0, name.indexOf(";"));

                    }
                }
                m = Pattern.compile("FN:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    name = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        name = name.substring(0, name.indexOf(";"));
                    }
                }
                m = Pattern.compile("ORG:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    org = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        org = org.substring(0, org.indexOf(";"));

                    }
                }
                m = Pattern.compile("TITLE:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    title = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        title = title.substring(0, title.indexOf(";"));

                    }
                }
                m = Pattern.compile("URL:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    url = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        url = url.substring(0, url.indexOf(";"));

                    }
                }
                if (result.contains("BEGIN:VCARD") || result.contains("begin:vcard")) {
                    m = Pattern.compile("TEL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        tel = tel + "\n" + m.group(1);

                    }
                    m = Pattern.compile("EMAIL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        email = m.group(1);

                    }
                    m = Pattern.compile("ADR.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        adr = adr + "\n" + m.group(1);

                    }
                    if (adr.contains(";")) {
                        String[] adrArray = adr.split(";");
                        adr = "";
                        for (String a : adrArray) {
                            adr = adr + a + "\n";
                        }


                    }
                } else {
                    m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        tel = m.group(1);
                        tel = tel.substring(0, tel.indexOf(";"));

                    }
                    m = Pattern.compile("EMAIL:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        email = m.group(1);
                        email = email.substring(0, email.indexOf(";"));


                    }
                    m = Pattern.compile("ADR:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        adr = m.group(1);
                        adr = adr.substring(0, adr.indexOf(";"));

                    }
                }

                m = Pattern.compile("BDAY:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    birthDay = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        birthDay = birthDay.substring(0, adr.indexOf(";"));
                    }
                }
                m = Pattern.compile("NOTE:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    note = m.group(1);
                    if (result.contains("MECARD") || result.contains("mecard")) {
                        note = note.substring(0, note.indexOf(";"));
                    }
                }
                result = name + "\n" + org + "\n" + title + "\n" + tel + "\n" + url + "\n" + email + "\n" + adr + "\n" + birthDay + "\n" + note;

                Constants.name = name;
                Constants.tel = tel;
                Constants.email = email;
                Constants.address = adr;

                String[] s = result.split("\n");
                result = "";
                ArrayList<String> arr = new ArrayList<>();
                for (String i : s) {
                    if (result.equals("")) {
                        if (!i.equals("")) {
                            result = i;
                        }
                    } else {
                        if (!i.equals("")) {
                            result = result + "\n" + i;
                        }
                    }
                }
            } catch (Exception e) {
                return new ResultOfTypeAndValue(Constants.TYPE_VCARD, result);
            }
            return new ResultOfTypeAndValue(Constants.TYPE_VCARD, result);
        } else if (result != null && ((Patterns.PHONE.matcher(result).matches() ||
                result.contains("tel:") || Patterns.PHONE.matcher(result).matches() ||
                result.contains("TEL:"))) && !result.contains("barcode:")) {
            try {
                result = result.replace("tel:", "");
                result = result.replace("TEL:", "");
            } catch (Exception e) {
                return new ResultOfTypeAndValue(Constants.TYPE_PHONE, result);
            }
            return new ResultOfTypeAndValue(Constants.TYPE_PHONE, result);
        } else if (result != null && (Patterns.EMAIL_ADDRESS.matcher(result).matches() ||
                result.contains("mailto:") || result.contains("MAILTO:") ||
                result.contains("matmsg:") || result.contains("MATMSG:"))) {
            if (result.contains("mailto:") || result.contains("MAILTO:")) {

                result = result + ";";
                if (result.contains("?")) {
                    result = result.replace("?", ";");
                }
                if (result.contains("=")) {
                    result = result.replace("=", ":");
                }
                if (result.contains("&")) {
                    result = result.replace("&", ";");
                }


                try {
                    String email = "", sub = "", body = "";
                    Matcher m = Pattern.compile("to:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        email = m.group(1);
                        email = email.substring(0, email.indexOf(";"));

                        Constants.emailType = email;
                    }


                    m = Pattern.compile("subject:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        sub = m.group(1);
                        if (result.contains("SUBJECT") || result.contains("subject")) {
                            sub = sub.substring(0, sub.indexOf(";"));

                            Constants.subjectType = sub;
                        }
                    }
                    result = result.replace("\n", " ");
                    m = Pattern.compile("body:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        body = m.group(1);

                        if (result.contains("body") || result.contains("BODY") || result.contains("Body")) {
                            body = body.substring(0, body.indexOf(";"));

                            Constants.bodyType = body;
                        }
                    }
                    result = email + "\n" + sub + "\n" + body;

                } catch (Exception e) {

                    return new ResultOfTypeAndValue(Constants.TYPE_EMAIL, result);
                }

                return new ResultOfTypeAndValue(Constants.TYPE_EMAIL, result);
            } else if (result.contains("matmsg:") || result.contains("MATMSG:")) {
                try {

                    String email = "", sub = "", body = "";
                    Matcher m = Pattern.compile("to:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        email = m.group(1);
                        email = email.substring(0, email.indexOf(";"));
                        Constants.emailType = email;

                    }
                    m = Pattern.compile("sub:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        sub = m.group(1);
                        sub = sub.substring(0, sub.indexOf(";"));
                        Constants.subjectType = sub;

                    }
                    result = result.replace("\n", " ");
                    m = Pattern.compile("body:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        body = m.group(1);
                        body = body.substring(0, body.indexOf(";"));
                        Constants.bodyType = body;

                    }
                    result = email + "\n" + sub + "\n" + body;

                } catch (Exception e) {
                    return new ResultOfTypeAndValue(Constants.TYPE_EMAIL, result);
                }
                return new ResultOfTypeAndValue(Constants.TYPE_EMAIL, result);
            } else {
                return new ResultOfTypeAndValue(Constants.TYPE_EMAIL, result);
            }

        } else if (result != null && result.contains("barcode:")) {


            return new ResultOfTypeAndValue(Constants.TYPE_BARCODE, result);
        } else if (result != null && (result.contains("WIFI:") || result.contains("wifi:"))) {
            try {
                String ssid = "", type = "", password = "";
                Matcher m = Pattern.compile("s:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    ssid = m.group(1);
                    ssid = ssid.substring(0, ssid.indexOf(";"));
                    Constants.wifiName = ssid;
                }
                m = Pattern.compile("t:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    type = m.group(1);
                    type = type.substring(0, type.indexOf(";"));
                    Constants.wifiSec = type;
                }
                m = Pattern.compile("p:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    password = m.group(1);
                    password = password.substring(0, password.indexOf(";"));
                    Constants.wifiPass = password;
                }
                if (type.equals("")) {
                    type = "nopass";
                    Constants.wifiSec = type;
                }

                result = ssid + "\n" + type + "\n" + password;
            } catch (Exception e) {
                return new ResultOfTypeAndValue(Constants.TYPE_WIFI, result);
            }
            return new ResultOfTypeAndValue(Constants.TYPE_WIFI, result);
        } else if (result != null && (result.contains("SMSTO:") || result.contains("smsto:"))) {
            try {
                String number = "", message = "";
                result = result.replace("SMSTO:", "");
                result = result.replace("smsto:", "");
                number = result;
                if (result.contains(":")) {
                    String[] str = result.split(":");
                    number = str[0];
                    if (str.length > 1) {
                        for (int i = 1; i < str.length; i++) {
                            if (message.equals("")) {
                                message = str[i];
                            } else {
                                message = message + ":" + str[i];
                            }
                        }
                    }
                    result = number + "\n" + message;

                }
            } catch (Exception e) {

                return new ResultOfTypeAndValue(Constants.TYPE_SMS, result);
            }
            return new ResultOfTypeAndValue(Constants.TYPE_SMS, result);
        } else if (result != null && (result.contains("GEO:") || result.contains("geo:"))) {
            result = result + ";";
            if (result.contains("?")) {
                try {

                    String geo = "", add = "", body = "";
                    Matcher m = Pattern.compile("geo:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        geo = m.group(1);
                        geo = geo.substring(0, geo.indexOf("?"));
                        Constants.geoLatiLongi = geo;

                    }
                    try {
                        if (geo.contains("?") || geo.contains(";") || geo.contains(":")) {
                            geo = geo.replace("?", ",");
                            geo = geo.replace(";", ",");
                            geo = geo.replace(":", ",");
                        }
                        String[] latArray = geo.split(",");
                        Constants.latitudeAddress = latArray[0];
                        Constants.longitudeAddress = latArray[1];


                    } catch (Exception e) {
                        return new ResultOfTypeAndValue(Constants.TYPE_TEXT, result);
                    }
                    m = Pattern.compile("q=(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                    while (m.find()) {
                        add = m.group(1);
                        add = add.substring(0, add.indexOf(";"));
                        Constants.locationAddress = add;

                    }

                    result = geo + "\n" + add + "\n";

                } catch (Exception e) {

                    return new ResultOfTypeAndValue(Constants.TYPE_GEO, result);
                }
            }


            return new ResultOfTypeAndValue(Constants.TYPE_GEO, result);


        } else if (result != null && (result.contains("BEGIN:VEVENT") || result.contains("begin:vevent") || result.contains("Begin:Vevent"))) {


            result = result.replace("\n", ";");

            try {

                String beginEvent = "", summary = "", description = "", location = "", dtstart = "", dtend = "";
                Matcher m = Pattern.compile("BEGIN:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {

                    beginEvent = m.group(1);
                    if (result.contains("BEGIN:") || result.contains("begin:")) {

                        beginEvent = beginEvent.substring(0, beginEvent.indexOf(";"));

                    }

                }
                m = Pattern.compile("SUMMARY:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    summary = m.group(1);
                    if (result.contains("SUMMARY:") || result.contains("summary:")) {

                        summary = summary.substring(0, summary.indexOf(";"));
                        Constants.titleEvent = summary;

                    }
                }
                m = Pattern.compile("DTSTART:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    dtstart = m.group(1);
                    if (result.contains("DTSTART:") || result.contains("dtstart:")) {

                        dtstart = dtstart.substring(0, dtstart.indexOf(";"));
                        Constants.eventStartTime = dtstart;

                    }
                }
                m = Pattern.compile("DTEND:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    dtend = m.group(1);
                    if (result.contains("DTEND:") || result.contains("dtend:")) {

                        dtend = dtend.substring(0, dtend.indexOf(";"));
                        Constants.eventEndTime = dtend;

                    }
                }
                m = Pattern.compile("LOCATION:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    location = m.group(1);
                    if (result.contains("LOCATION:") || result.contains("location:")) {

                        location = location.substring(0, location.indexOf(";"));
                        Constants.eventLocation = location;

                    }
                }
                m = Pattern.compile("DESCRIPTION:(.*)", Pattern.CASE_INSENSITIVE).matcher(result);
                while (m.find()) {
                    description = m.group(1);
                    if (result.contains("DESCRIPTION:") || result.contains("description:")) {

                        description = description.substring(0, description.indexOf(";"));
                        Constants.eventDescription = description;

                    }
                }

                result = beginEvent + "\n" + summary + "\n" + dtstart + "\n" + dtend + "\n" + location + "\n" + description;

            } catch (Exception e) {

                return new ResultOfTypeAndValue(Constants.TYPE_EVENT, result);
            }
            return new ResultOfTypeAndValue(Constants.TYPE_EVENT, result);
        } else if (result != null && result.contains("whatsapp://send?phone=")) {

            return new ResultOfTypeAndValue(Constants.TYPE_WHATSAPP, result);
        } else {
            return new ResultOfTypeAndValue(Constants.TYPE_TEXT, result);
        }

    }


    public static void LocalizeApp(Activity mActivity, int selectedIndex, boolean canceable) {


        // Create an array of options to display in the dialog
        String[] options = mActivity.getResources().getStringArray(R.array.local);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);

        // set the custom icon to the alert dialog


        // title of the alert dialog
        alertDialog.setTitle(R.string.choose_language);


        // list of the items to be displayed to the user in the
        // form of list so that user can select the item from


        // the function setSingleChoiceItems is the function which
        // builds the alert dialog with the single item selection
        alertDialog.setSingleChoiceItems(options, selectedIndex, (dialog, which) -> {
            // update the selected item which is selected by the user so that it should be selected
            // when user opens the dialog next time and pass the instance to setSingleChoiceItems method

            if (which == 0) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "en");


            } else if (which == 1) {


                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "it");

            } else if (which == 2) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "fr");

            } else if (which == 3) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "ru");

            } else if (which == 4) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "pt");

            } else if (which == 5) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "es");

            } else if (which == 6) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "ar");

            } else if (which == 7) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "de");

            } else if (which == 8) {
                changeLang(mActivity, "tr");
                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "tr");

            } else if (which == 9) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "iw");

            } else if (which == 10) {

                AppPreference.getInstance(mActivity).setString(PrefKey.languageKey, "zh");

            }
            AppPreference.getInstance(mActivity).setInteger(PrefKey.languageKPos,which);
            ActivityUtils.getInstance().restartActivity(mActivity, EntryActivity.class, true);

            dialog.dismiss();
        });

        // set the negative button if the user is not interested to select or change already selected item
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {

        });



        // create and build the AlertDialog instance with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();


        // show the alert dialog when the button is clicked
        customAlertDialog.show();


    }


    public static void changeLang(Activity activity, String languageToLoad) {
        Locale myLocale = new Locale(languageToLoad);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }


}


