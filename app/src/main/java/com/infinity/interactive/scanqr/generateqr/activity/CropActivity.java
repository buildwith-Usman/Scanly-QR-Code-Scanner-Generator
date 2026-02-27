package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference;
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey;
import com.infinity.interactive.scanqr.generateqr.utility.ActivityUtils;


public class CropActivity extends AppCompatActivity {


    ImageView buttonDone;
    Context mContext;
    String resultStr, type_of_code;
    Activity mActivity;
//    CropImageView mCropView;
    ImageView backCrop;

    Result result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        initVars();
        initViews();
        initFunctionality();
        initListener();

    }

    private void initVars() {
        mActivity = this;
        mContext = mActivity.getApplicationContext();

    }

    private void initListener() {

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (mCropView.getCroppedBitmap() != null) {
//
//                    result = parseInfoFromBitmap(mCropView.getCroppedBitmap());
//                    if (result != null) {
//                        try {
//
//                            type_of_code = (result.getBarcodeFormat().toString());
//                            Log.d("check0075", type_of_code + "");
//                            if (type_of_code.contains("AZTEC") || type_of_code.contains("PDF_417") ||
//                                    type_of_code.contains("DATA_MATRIX") || type_of_code.contains("QR_CODE")) {
//                                resultStr = result.getText();//qr_code
//
//                                buttonDone.setEnabled(true);
//
//                            } else if (type_of_code.contains("CODE_39") || type_of_code.contains("CODE_128") || type_of_code.contains("CODABAR") || type_of_code.contains("CODE_93") || type_of_code.contains("EAN_8") || type_of_code.contains("EAN_13") || type_of_code.contains("ITF") || type_of_code.contains("UPC_A") || type_of_code.contains("UPC_E") || type_of_code.contains("UPC_EAN_EXTENSION") || type_of_code.contains("RSS_14") || type_of_code.contains("RSS_EXPANDED") || type_of_code.contains("MAXICODE")) {
//                                resultStr = "barCodeType:" + type_of_code + ";" + "barcode:" + result.getText() + ";";//barcode
//
//                                buttonDone.setEnabled(true);
//
//                            }
//
//                            savingResults(resultStr);
//                        } catch (Exception e) {
//                            finish();
//                            e.printStackTrace();
//                        }
////                        detected.setVisibility(View.VISIBLE);
////                        buttonDone.setEnabled(true);
//                    } else {
//                        finish();
////                        detected.setVisibility(View.GONE);
////                        buttonDone.setEnabled(false);
//                    }
//
//                }


            }
        });

        backCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void vibrate(boolean isVibrate) {
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (isVibrate) {
            assert v != null;
            v.vibrate(100);
        }
    }


    void savingResults(final String resultStr) {
        // boolean isCopy = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_COPY, false);
        // boolean isSound = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_SOUND, false);
        boolean isVibrate = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_VIBRATE, true);
        final boolean isAutoOpenURL = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_AUTO_URL, false);

        //vibrate
        vibrate(isVibrate);
        //sound
        // sound(isSound);

        //saving result
        ArrayList<String> previousResult = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED);
        previousResult.add(this.resultStr);
        AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_SCANNED, previousResult);

        //Copy to clipboard
        // copyToClipboard(isCopy);

        //save date of scan
        String currentDate;
        if (Locale.getDefault().equals(Locale.US)) {
            currentDate = new SimpleDateFormat("MM.dd.yyyy HH:mm").format(Calendar.getInstance().getTime());
        } else {
            currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
        }
        ArrayList<String> previousDate = AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_SCANNED);
        previousDate.add(currentDate);
        AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_SCANNED, previousDate);

        //saving color (standart black)
        ArrayList<String> previousColor = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED);
        previousColor.add(Integer.toString(Color.BLACK));
        AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_SCANNED, previousColor);

//        AppUtils.showScannedDialog(mActivity);


        ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
//        ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
        //Auto Open URL
//        autoOpenUrl(isAutoOpenURL, resultStr);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                alert.dismiss();
//                ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
//            }
//        },500);
        // TODO Sample fullscreen Ad implementation
        // fullscreen ad show
//        if(AdManager.getInstance(mContext).showFullScreenAd(mActivity)){
//
//            AdManager.getInstance(mContext).getInterstitialAd().setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    super.onAdShowedFullScreenContent();
//                }
//
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
//                    AdManager.getInstance(mContext).loadFullScreenAd(mActivity);
//                    ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
//                    //Auto Open URL
//                    autoOpenUrl(isAutoOpenURL, resultStr);
//                }
//
//                @Override
//                public void onAdImpression() {
//                    super.onAdImpression();
//                }
//
//                @Override
//                public void onAdClicked() {
//                    super.onAdClicked();
//                }
//            });
////            AdManager.getInstance(mContext).getInterstitialAd().setAdListener(new AdListener() {
////                @Override
////                public void onAdClosed() {
////                    super.onAdClosed();
////                    //full ads load
////                    AdManager.getInstance(mContext).loadFullScreenAd(mActivity);
////                    ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
////                    //Auto Open URL
////                    autoOpenUrl(isAutoOpenURL, resultStr);
////                }
////            });
//        } else {
//            ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false, Constants.SCAN_FRAGMENT, 0);
//            //Auto Open URL
//            autoOpenUrl(isAutoOpenURL, resultStr);
//        }

    }


//    private BinaryBitmap binaryBitmapFromJpegData(byte[] data, int rotation) {
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//        if (rotation != 0) {
//            try {
//                bitmap = rotateBitmap(bitmap, rotation);
//            }catch (Exception ignored){
//
//            }
//        }
//
//        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
//        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
//
//        return new BinaryBitmap(new HybridBinarizer(source));
//    }
//
//
//
//
//    public static Bitmap rotateBitmap(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    Uri sourceUri;
//
//    BinaryBitmap bitmap = null;

//    public static Bitmap compressImage(Bitmap image) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 100baos
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 100) { // 100kb,
//            baos.reset();// baosbaos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// options%baos
//            options -= 10;// 10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(
//                baos.toByteArray());// baosByteArrayInputStream
//        return BitmapFactory.decodeStream(isBm, null, null);
//    }


    public Result parseInfoFromBitmap(Bitmap bMap) {


        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
//copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            Log.d("CheckResult", result.getBarcodeFormat().toString());
            return result;
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
        }


        return null;

    }


    private void initFunctionality() {


//        mCropView.setCropMode(CropImageView.CropMode.FREE);

        if (Constants.galleryBitmap != null) {
//            mCropView.setImageBitmap(Constants.galleryBitmap);
//            mCropView.setImageBitmap(Constants.galleryBitmap);
//            cropImageView.setImageBitmap(Constants.galleryBitmap);
        }

//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            if(mCropView.getCroppedBitmap()!=null){
//                mCropView.getCroppedBitmap().compress(Bitmap.CompressFormat.PNG,100,stream);
//            }
//
//            byte[] byteArray = stream.toByteArray();
//
//            Reader reader = new MultiFormatReader();// use this otherwise
//
//            Map<DecodeHintType, Object> tmpHintsMap = new EnumMap<>(DecodeHintType.class);
//            tmpHintsMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); //thorough search Qrcode
//
//            //rotate image if nothing found
//
//
//            for (int i = 0; i < 8; i++) {
//
//                try {
//                    bitmap = binaryBitmapFromJpegData(byteArray, i * 45);
//                    break;
//                }catch (Exception ignored){
//
//                }
//
//                try {
//                    //get result
//                    result = reader.decode(bitmap, tmpHintsMap);
////                    Toast.makeText(mContext, "result detect", Toast.LENGTH_SHORT).show();
//                    buttonDone.setEnabled(true);
//                    detected.setVisibility(View.VISIBLE);
//                    break;
//                } catch (NotFoundException | ChecksumException | FormatException e) {
//                    if (i == 7) {
//                        buttonDone.setEnabled(false);
//                        detected.setVisibility(View.GONE);
////                        Toast.makeText(mContext, getResources()
////                                .getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            detected.setVisibility(View.GONE);
//            buttonDone.setEnabled(false);
////            Toast.makeText(mContext, getResources().getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//
//        }

//        if(mCropView.getCroppedBitmap()!=null){
//
//            result=parseInfoFromBitmap(mCropView.getCroppedBitmap());
//            if(result!=null){
//                detected.setVisibility(View.VISIBLE);
//                buttonDone.setEnabled(true);
//            }
//            else {
//                detected.setVisibility(View.GONE);
//                buttonDone.setEnabled(false);
//            }
//
//        }

//        mCropView.setOnTouchListener(new View.OnTouchListener() {
//          @Override
//          public boolean onTouch(View v, MotionEvent event) {
//
//              switch (event.getAction()) {
//                  case MotionEvent.ACTION_DOWN:
//                      Log.d("checkTouchEvents","action down");
//                      break;
//                  case MotionEvent.ACTION_MOVE:
//                      Log.d("checkTouchEvents","action move");
//                      break;
//                  case MotionEvent.ACTION_CANCEL:
//                      Log.d("checkTouchEvents","action cancel");
//                      break;
//                  case MotionEvent.ACTION_UP:
//
//                      if(mCropView.getCroppedBitmap()!=null){
//
//                           result=parseInfoFromBitmap(mCropView.getCroppedBitmap());
//                            if(result!=null){
//                                detected.setVisibility(View.VISIBLE);
//                                buttonDone.setEnabled(true);
//                            }
//                            else {
//                                detected.setVisibility(View.GONE);
//                                buttonDone.setEnabled(false);
//                            }
//
//                      }
//
//                      Log.d("checkTouchEvents","action up");
//                      break;
//
//
//          }
//              return false;
//          }
//      });


    }

    private void initViews() {
//        mCropView = findViewById(R.id.crop_image_menu_crop);
        buttonDone = findViewById(R.id.buttonDone);
        backCrop = findViewById(R.id.backButtonFromQR);
    }
}