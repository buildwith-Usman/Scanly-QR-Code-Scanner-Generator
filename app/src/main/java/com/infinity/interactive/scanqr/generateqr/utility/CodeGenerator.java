package com.infinity.interactive.scanqr.generateqr.utility;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Arrays;

import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;


public class CodeGenerator extends AsyncTask<Void, Void, Bitmap> {

    public static final int QR_DIMENSION = 800, BAR_HEIGHT = 340, BAR_WIDTH = 1080;
//    public static final int QR_DIMENSION = 1080, BAR_HEIGHT = 640, BAR_WIDTH = 2060;

    private static final int WHITE = 0xFFFFFFFF;

    private static final int TYPE_QR = 0;
    private static final int TYPE_BAR = 1;
    private static int BLACK = 0xFF000000;
    private static int TYPE;
    private ResultListener resultListener;
    private String input;

    public static void setBLACK(int BLACK) {
        CodeGenerator.BLACK = BLACK;
    }


    public void generateQRFor(String input) {
        this.input = input;
        TYPE = TYPE_QR;
    }

    public void generateBarFor(String input) {
        this.input = input;
        TYPE = TYPE_BAR;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            if (TYPE == TYPE_QR) {
                return createQRCode(this.input);

            } else {
                return createBarcode(this.input);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (resultListener != null) {
            resultListener.onResult(bitmap);
        }
    }

    private Bitmap createQRCode(String str) throws WriterException {
        BitMatrix result;

//        Hashtable hints = new Hashtable();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hints.put(EncodeHintType.MARGIN, 1);


        try {

            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, QR_DIMENSION, QR_DIMENSION);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }


        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];


        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, QR_DIMENSION, 0, 0, w, h);

        return bitmap;
    }

    private Bitmap createBarcode(String data) throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(data);

        // Use 1 as the height of the matrix as this is a 1D Barcode.
        BitMatrix bm = writer.encode(finalData, Constants.format, BAR_WIDTH, 1);
        int bmWidth = bm.getWidth();

        Bitmap imageBitmap = Bitmap.createBitmap(bmWidth, BAR_HEIGHT, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < bmWidth; i++) {
            // Paint columns of width 1
            int[] column = new int[BAR_HEIGHT];
            Arrays.fill(column, bm.get(i, 0) ? BLACK : Color.WHITE);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, BAR_HEIGHT);
        }

        return imageBitmap;
    }

    public interface ResultListener {
        void onResult(Bitmap bitmap);
    }


}


