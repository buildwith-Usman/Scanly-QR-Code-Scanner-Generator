package com.infinity.interactive.scanqr.generateqr.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.activity.CropActivity
import com.infinity.interactive.scanqr.generateqr.activity.HelpActivity
import com.infinity.interactive.scanqr.generateqr.activity.ResultActivity
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.ActivityUtils
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils
import com.infinity.interactive.scanqr.generateqr.zxing.ZXingScannerView
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class QRScanFragment : Fragment(), ZXingScannerView.ResultHandler {
    private val isAutoFocus = false
    var seekBarZoom: IndicatorSeekBar? = null
    var resultStr: String? = null
    var type_of_code: String? = null
    var backgroundExecutor: ScheduledExecutorService? = null
    var handler: Handler? = null
    var mLock: Lock = ReentrantLock()
    var mActivity: Activity? = null
    var mContext: Context? = null
    var contentFrame: ViewGroup? = null
    var mScannerView: ZXingScannerView? = null
    var mSelectedIndices: ArrayList<Int>? = null
    var flash: ImageView? = null
    var gallery: ImageView? = null
    var camera: ImageView? = null
    var help: ImageView? = null
    var isFlash = false
    var ActivateScanner = true
    var camId = 0
    var frontCamId = 0
    var rearCamId = 0
    var allowCameraUseBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVar()
        setupFormats()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_scan, container, false)
        initView(rootView)
        initListener()
        return rootView
    }

    private fun initVar() {
        mActivity = requireActivity()
        mContext = (mActivity as FragmentActivity).applicationContext
        //        handler = new Handler();

        //check permission and load cam();
        checkCameraPermission()
    }

    private fun initView(rootView: View) {
        contentFrame = rootView.findViewById(R.id.content_frame)
        flash = rootView.findViewById(R.id.flash)
        gallery = rootView.findViewById(R.id.gallery)
        camera = rootView.findViewById(R.id.camera)
        seekBarZoom = rootView.findViewById(R.id.seekBarZoom)
        allowCameraUseBtn = rootView.findViewById(R.id.allowCameraUseBtn)
        help = rootView.findViewById(R.id.help)
        initConfigs()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initListener() {
        flash!!.setOnClickListener { toggleFlash() }
        gallery!!.setOnClickListener { //                if(checkWritePermission()){
//                    galleryImage();
//                }
            try {
                galleryImage()
                mScannerView!!.flash = false
                flash!!.setImageResource(R.drawable.flash_off_scanner)
                isFlash = false
            } catch (ignored: Exception) {
            }
        }
        camera!!.setOnClickListener { toggleCamera() }
        allowCameraUseBtn!!.setOnClickListener { checkCameraPermission() }
        seekBarZoom!!.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {}
            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {}
            override fun onDragging(progress: Float) {
                try {
                    mScannerView!!.setZoom(progress)
                } catch (ignored: Exception) {
                }
            }
        }
        help!!.setOnClickListener {
            val intent = Intent(mContext, HelpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkWritePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                mActivity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mActivity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.PERMISSION_REQ
            )
        } else {
            return true
        }
        return false
    }

    override fun handleResult(result: Result) {
        if (ActivateScanner) {

            //save result
            try {
                type_of_code = result.barcodeFormat.toString()
                if (type_of_code!!.contains("AZTEC") || type_of_code!!.contains("PDF_417") ||
                    type_of_code!!.contains("DATA_MATRIX") || type_of_code!!.contains("QR_CODE")
                ) {
                    resultStr = result.text //qr_code
                    savingResults(resultStr)
                } else if (type_of_code!!.contains("CODE_39") || type_of_code!!.contains("CODE_128") || type_of_code!!.contains(
                        "CODABAR"
                    ) || type_of_code!!.contains("CODE_93") || type_of_code!!.contains("EAN_8") || type_of_code!!.contains(
                        "EAN_13"
                    ) || type_of_code!!.contains("ITF") || type_of_code!!.contains("UPC_A") || type_of_code!!.contains(
                        "UPC_E"
                    ) || type_of_code!!.contains("UPC_EAN_EXTENSION") || type_of_code!!.contains("RSS_14") || type_of_code!!.contains(
                        "RSS_EXPANDED"
                    ) || type_of_code!!.contains("MAXICODE")
                ) {
                    resultStr =
                        "barCodeType:" + type_of_code + ";" + "barcode:" + result.text + ";" //barcode
                    savingResults(resultStr)
                } else {
                    Toast.makeText(mActivity, "Code not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(mActivity, "Code not found", Toast.LENGTH_SHORT).show()
            }
            backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
            backgroundExecutor!!.schedule(Runnable { // Your code logic goes here
                mScannerView!!.resumeCameraPreview(this@QRScanFragment)
            }, 200, TimeUnit.MILLISECONDS)

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    mScannerView.resumeCameraPreview(ScanFragment.this);
////                    Toast.makeText(mContext, "how many times", Toast.LENGTH_SHORT).show();
//                    mScannerView.resumeCameraPreview(ScanFragment.this);
//                }
//            }, 200);
//            mScannerView.resumeCameraPreview(this);
        }
        //        else {
//            mScannerView.resumeCameraPreview(this);
//        }
    }

    private fun initializeScanner() {

//        mScannerView = new app.scanqrcode.qrcodegenerator.zxing.ZXingScannerView(mActivity);
//        mScannerView.setSquareViewFinder(true);
//        mScannerView.setResultHandler((app.scanqrcode.qrcodegenerator.zxing.ZXingScannerView.ResultHandler) this);
        try {
            mScannerView = ZXingScannerView(activity)
            mScannerView!!.setResultHandler(this)
        } catch (ignored: Exception) {
        }

//        mScannerView.setAutoFocus(true);
        loadCams()
        camId = rearCamId
        activateScanner()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.PERMISSION_REQ) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.CAMERA) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        allowCameraUseBtn!!.visibility = View.GONE
                        //                        checkCameraPermission();
                        initializeScanner()
                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted))
                        allowCameraUseBtn!!.visibility = View.VISIBLE
                    }
                }

//                else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
//
//                        galleryImage();
//
////                        saveAndShare(shouldShare, inputStr, finalBitmap);
//
//                    } else {
//                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
//                    }
//                }
            }
        }

//        if (requestCode == PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE) {
//            for (int grantResult : grantResults) {
//                if (grantResult == PackageManager.PERMISSION_GRANTED) {
//
//                    PictureFileUtils.deleteCacheDirFile(requireContext(), PictureMimeType.ofImage());
//                } else {
//                    Toast.makeText(getContext(),
//                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                mActivity!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                Constants.PERMISSION_REQ
            )
        } else {
            initializeScanner()
        }
    }

    private fun activateScanner() {
        try {
            mScannerView!!.stopCamera()
        } catch (ignored: Exception) {
//            Log.d("checkException",ignored.toString());
        }
        try {
            if (mScannerView != null) {
                if (mScannerView!!.parent != null) {
                    (mScannerView!!.parent as ViewGroup).removeView(mScannerView) // to prevent crush on re adding view
                }
                try { //for slow devices and old android versions, remove transition animation when changing cameras and remove view
                    contentFrame!!.layoutTransition = null
                    contentFrame!!.removeAllViews()
                } catch (ignored: Exception) {
                }
                try {
                    contentFrame!!.addView(mScannerView)
                } catch (ignored: Exception) {
                }
                try {
                    mScannerView!!.startCamera(camId)


                    /*zXingScannerView.setFlash(isFlash);
                zXingScannerView.setAutoFocus(isAutoFocus);*/
                } catch (e: Exception) {
                    Toast.makeText(
                        mContext,
                        resources.getString(R.string.error_restart_app),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setupFormats() {
        val formats: MutableList<BarcodeFormat> = ArrayList()
        if (mSelectedIndices == null || mSelectedIndices!!.isEmpty()) {
            mSelectedIndices = ArrayList()
            for (i in ZXingScannerView.ALL_FORMATS.indices) {
                mSelectedIndices!!.add(i)
            }
        }
        for (index in mSelectedIndices!!) {
            formats.add(ZXingScannerView.ALL_FORMATS[index])
        }
        if (mScannerView != null) {
            mScannerView!!.setFormats(formats)
        }
    }

    override fun onResume() {
        super.onResume()
        //        Log.d("callbacks","onResume called");
        if (mLock.tryLock()) {
            try {
                initializeScanner()
                activateScanner()
            } finally {
                mLock.unlock()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //        Log.d("callbacks", "onPause called");
        try {
//            if(handler!=null){
//                handler.removeCallbacks(null);
//            }
            if (backgroundExecutor != null) {
                backgroundExecutor!!.shutdown()
            }
            if (mScannerView != null) {
                mScannerView!!.stopCamera()
                seekBarZoom!!.setProgress(0f)
                flash!!.setImageResource(R.drawable.flash_off_scanner)
                mScannerView!!.flash = false
                isFlash = false
            }
        } catch (e: Exception) {
//            Toast.makeText(mContext,e.toString(),Toast.LENGTH_LONG).show();
//            Log.d("checkExc",e.toString());
            e.printStackTrace()
        }
    }

    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        if (mScannerView != null) {
            if (visible) {
                mScannerView!!.flash = isFlash
                ActivateScanner = true
            } else {
                mScannerView!!.flash = false
                ActivateScanner = false
            }
        }
    }

    fun toggleFlash() {
        if (isFlash) {
            isFlash = false
            flash!!.setImageResource(R.drawable.flash_off_scanner)
        } else {
            isFlash = true
            flash!!.setImageResource(R.drawable.flash_on_scanner)
        }
        //        AppPreference.getInstance(mContext).setBoolean(PrefKey.FLASH, isFlash);
        if (mScannerView != null) {
            mScannerView!!.flash = isFlash
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun galleryImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        //set intent type to image
//        intent.setType("image/*")
//        startActivityForResult(
//            Intent.createChooser(intent, "Select Picture"),
//            PICK_IMAGEScannerGallery
//        )

        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        // starting activity on below line.
        startActivityForResult(intent, PICK_IMAGE_SCANNER_GALLERY)

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

//        try {
//            PictureSelector.create(ScanFragment.this)
//                    .openGallery(PictureMimeType.ofImage())
//                    .isCamera(false)
//                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
//                    .selectionMode(PictureConfig.SINGLE)
//                    .previewImage(false)
//                    .compress(true)
//                    .forResult(PictureConfig.CHOOSE_REQUEST);
//        } catch (Exception ignored) {
//
//        }

//        Intent intent = new Intent(Intent.ACTION_PICK);
//        //set intent type to image
//        intent.setType("image/*");
////        galleryActivityResultLauncher.launch(intent);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    //For gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        if (resultCode != Activity.RESULT_CANCELED) {
//            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
//                // onResult Callback
//
//
//                if (imageReturnedIntent != null) {
//
//                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(imageReturnedIntent);
//
//
//                    try {
//                        if (result.size() == 1) {
////                    Toast.makeText(mActivity, result.get(0).getPath()+"", Toast.LENGTH_SHORT).show();
//                            Uri selectedImage = Uri.parse(result.get(0).getPath());
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                InputStream baseStream;
//                                InputStream imageStream;
//                                try {
////                            Toast.makeText(mActivity, selectedImage+"", Toast.LENGTH_SHORT).show();
//                                    //getting the image
//                                    baseStream = mContext.getContentResolver().openInputStream(selectedImage);
//                                    imageStream = mContext.getContentResolver().openInputStream(selectedImage);
//                                } catch (FileNotFoundException e) {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_file), Toast.LENGTH_SHORT).show();
//                                    e.printStackTrace();
//                                    return;
//                                }
//
//                                // ChecksumException
//                                try {
////                        Bitmap myBitmap = BitmapFactory.decodeFile(result.get(0).getPath());
//                                    //decoding bitmap for get width
//                                    Bitmap bMap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedImage);
//                                    Bitmap base = BitmapFactory.decodeStream(baseStream);
//                                    //set options for resize image
//                                    BitmapFactory.Options options = new BitmapFactory.Options();
//                                    options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))
//
//                                    //decoding bitmap
//                                    bMap = BitmapFactory.decodeStream(imageStream, null, options); //get image with options
//
//                                    Constants.galleryBitmap = bMap;
//
//                                    startActivity(new Intent(mContext, CropActivity.class));
//
//
//                                    result.clear();
//
//                                } catch (Exception e) {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                try {
//
//                                    Constants.galleryBitmap = BitmapFactory.decodeFile(result.get(0).getPath());
//
//                                    startActivity(new Intent(mContext, CropActivity.class));
//
//
//                                    result.clear();
//
//
//                                } catch (Exception e) {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//                    } catch (Exception ignored) {
//
//
//                    }
//
//                }
//
//
//            } else
            if (requestCode == PICK_IMAGE_SCANNER_GALLERY) {
                if (imageReturnedIntent != null) {
                    var selectedImage: Uri? = null
                    selectedImage = try {
                        imageReturnedIntent.data
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "File Corrupted", Toast.LENGTH_SHORT).show()
                        return
                    }
                    var baseStream: InputStream? = null
                    var imageStream: InputStream? = null
                    try {
                        //getting the image
                        baseStream = mActivity!!.contentResolver.openInputStream(selectedImage!!)
                        imageStream = mActivity!!.contentResolver.openInputStream(selectedImage)
                    } catch (e: FileNotFoundException) {
                        Toast.makeText(
                            mContext,
                            resources.getString(R.string.error_file),
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                        return
                    }

                    // ChecksumException
                    try {
                        //decoding bitmap for get width
                        val base = BitmapFactory.decodeStream(baseStream)
                        //set options for resize image
                        val options = BitmapFactory.Options()
                        options.inSampleSize =
                            base.width / 1000 //get compress coef  (set image px size (compressing big image))

                        //decoding bitmap
                        var bMap = MediaStore.Images.Media.getBitmap(
                            mContext!!.contentResolver,
                            selectedImage
                        )

                        //set options for resize image


                        //decoding bitmap
                        bMap = BitmapFactory.decodeStream(
                            imageStream,
                            null,
                            options
                        ) //get image with options
                        Constants.galleryBitmap = bMap
                        startActivity(Intent(mContext, CropActivity::class.java))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

//        switch (requestCode) {
//            case PICK_IMAGE:
//                if (resultCode == RESULT_OK) {
//                    //doing some uri parsing
//                    if(imageReturnedIntent!=null){
//                        Uri selectedImage=null;
//                        try{
//                            selectedImage = imageReturnedIntent.getData();
//                        }
//                        catch (Exception e){
//                            Toast.makeText(mActivity, "File Corrupted", Toast.LENGTH_SHORT).show();
//                        }
//
//                        InputStream baseStream = null;
//                        InputStream imageStream = null;
//                        try {
//                            //getting the image
//                            baseStream = mActivity.getContentResolver().openInputStream(selectedImage);
//                            imageStream = mActivity.getContentResolver().openInputStream(selectedImage);
//                        } catch (FileNotFoundException e) {
//                            Toast.makeText(mContext, getResources().getString(R.string.error_file), Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                            return;
//                        }
//
//                        // ChecksumException
//                        try {
//                            //decoding bitmap for get width
//                            Bitmap base = BitmapFactory.decodeStream(baseStream);
//                            //set options for resize image
//                            BitmapFactory.Options options = new BitmapFactory.Options();
//                            options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))
//
//                            //decoding bitmap
//                            Bitmap bMap = BitmapFactory.decodeStream(imageStream, null, options); //get image with options
//
//                            //Convert Bitmap to ByteArray
////                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                        bMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////                        byte[] byteArray = stream.toByteArray();
//
////                        Reader reader = new MultiFormatReader();// use this otherwise
////
////                        Map<DecodeHintType, Object> tmpHintsMap = new EnumMap<>(DecodeHintType.class);
////                        tmpHintsMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); //thorough search Qrcode
////
////                        //rotate image if nothing found
////                        Result result = null;
////                        for (int i = 0; i < 8; i++) {
////                            BinaryBitmap bitmap = binaryBitmapFromJpegData(byteArray, i * 45);
////                            try {
////                                //get result
////                                result = reader.decode(bitmap, tmpHintsMap);
////                                break;
////                            } catch (NotFoundException e) {
////                                if (i == 7) {
////                                    Toast.makeText(mContext, getResources()
////                                            .getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        }
//
//                            Constants.galleryBitmap=bMap;
//
//                            startActivity(new Intent(mContext, CropActivity.class));
//                            //save result
////                        type_of_code = (result.getBarcodeFormat().toString());
////                        if (type_of_code.contains("AZTEC") || type_of_code.contains("PDF_417") ||
////                                type_of_code.contains("DATA_MATRIX") || type_of_code.contains("QR_CODE")) {
////                            resultStr = result.getText();//qr_code
////                        } else {
////                            resultStr = "barcode:" + result.getText();//barcode
////                        }
////                        savingResults(resultStr);
//
//                        } catch (Exception e) {
////                        Toast.makeText(mContext, getResources().getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                }
//
//
//        }
    }

    //    private BinaryBitmap binaryBitmapFromJpegData(byte[] data, int rotation) {
    //        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
    //
    //        if (rotation != 0) {
    //            bitmap = rotateBitmap(bitmap, rotation);
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
    //    public static Bitmap rotateBitmap(Bitmap source, float angle) {
    //        Matrix matrix = new Matrix();
    //        matrix.postRotate(angle);
    //        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    //    }
    fun savingResults(resultStr: String?) {
        // boolean isCopy = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_COPY, false);
        // boolean isSound = AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_SOUND, false);
        val isVibrate =
            AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_VIBRATE, true)
        val isAutoOpenURL =
            AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_AUTO_URL, false)
        try {
            isFlash = false
            mScannerView!!.flash = false
            flash!!.setImageResource(R.drawable.flash_off_scanner)
        } catch (ignored: Exception) {
        }

        //vibrate
        vibrate(isVibrate)
        //sound
        // sound(isSound);

        //saving result
        val previousResult =
            AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
        previousResult.add(this.resultStr)
        AppPreference.getInstance(mContext)
            .setStringArray(PrefKey.RESULT_LIST_OF_SCANNED, previousResult)

        //Copy to clipboard
        // copyToClipboard(isCopy);

        //save date of scan
        val currentDate: String
        currentDate = if (Locale.getDefault() == Locale.US) {
            SimpleDateFormat("MM.dd.yyyy HH:mm").format(Calendar.getInstance().time)
        } else {
            SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().time)
        }
        val previousDate =
            AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_SCANNED)
        previousDate.add(currentDate)
        AppPreference.getInstance(mContext)
            .setStringArray(PrefKey.DATE_LIST_OF_SCANNED, previousDate)

        //saving color (standart black)
        val previousColor =
            AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED)
        previousColor.add(Integer.toString(Color.BLACK))
        AppPreference.getInstance(mContext)
            .setStringArray(PrefKey.COLOR_LIST_OF_SCANNED, previousColor)

//        AppUtils.showScannedDialog(mActivity);
        ActivityUtils.getInstance().invokeActivity(
            mActivity,
            ResultActivity::class.java,
            false,
            Constants.SCAN_FRAGMENT,
            0
        )
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

    private fun vibrate(isVibrate: Boolean) {
        val v = mContext!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (isVibrate) {
            assert(v != null)
            v.vibrate(100)
        }
    }

    /*   private void sound(boolean isSound) {
        if (isSound) {
            beepSound = MediaPlayer.create(mActivity, R.raw.beep); //For beep sound
            beepSound.start();
        }
    }*/
    /* private void copyToClipboard(boolean isCopy) {
        //Copy to clipboard
        if (isCopy) {
            ResultOfTypeAndValue resultValues = AppUtils.getResourceType(this.resultStr,mActivity);
            String finalResult = resultValues.getValue().toString();
            AppUtils.copyToClipboard(mContext, finalResult);
        }
    }*/
    private fun autoOpenUrl(isAutoOpenURL: Boolean, resultStr: String) {
        //Auto Open URL
        val resultValues = AppUtils.getResourceType(resultStr)
        if (resultValues.type == Constants.TYPE_WEB && isAutoOpenURL) {
            //If this result get from the QR code is an URL, open the browse.
            val intent = Intent()
            intent.setAction("android.intent.action.VIEW")
            val content_url = Uri.parse(resultValues.value)
            intent.setData(content_url)
            startActivity(intent)
        }
    }

    private fun toggleCamera() {
        camId = if (camId == rearCamId) {
            frontCamId
        } else {
            rearCamId
        }
        if (mScannerView != null) {
            mScannerView!!.flash = isFlash
        }
        //        flash.setImageResource(R.drawable.ic_flash_icon);
//        AppPreference.getInstance(mContext).setBoolean(PrefKey.FLASH, false);
//        isFlash = false;
        activateScanner()
    }

    private fun initConfigs() {
        flash!!.setImageResource(R.drawable.flash_off_scanner)
        if (isFlash) {
            flash!!.setImageResource(R.drawable.flash_on_scanner)
        } else {
            flash!!.setImageResource(R.drawable.flash_off_scanner)
        }
        /* if (isAutoFocus) {
            focus.setImageResource(R.drawable.ic_focus_off);
        } else {
            focus.setImageResource(R.drawable.ic_focus_on);
        }*/
    }

    private fun loadCams() {
        try {
            val numberOfCameras = Camera.getNumberOfCameras()
            for (i in 0 until numberOfCameras) {
                val info = CameraInfo()
                Camera.getCameraInfo(i, info)
                if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                    frontCamId = i
                } else if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    rearCamId = i
                }
            }
            AppPreference.getInstance(mContext).setInteger(PrefKey.CAM_ID, rearCamId)
        } catch (e: Exception) {
            Toast.makeText(mActivity, "Error Occured", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PICK_IMAGE_SCANNER_GALLERY = 1
    }
}
