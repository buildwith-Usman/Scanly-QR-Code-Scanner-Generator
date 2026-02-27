package com.infinity.interactive.scanqr.generateqr.utility;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private final MediaScannerConnection mMs;
    private final String absoluteFilePath;

    public SingleMediaScanner(Context context, String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
        mMs = new MediaScannerConnection(context, this);
        mMs.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMs.scanFile(absoluteFilePath, null);
//        mMs.scanFile(absoluteFilePath, "image/*");
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMs.disconnect();
    }

}