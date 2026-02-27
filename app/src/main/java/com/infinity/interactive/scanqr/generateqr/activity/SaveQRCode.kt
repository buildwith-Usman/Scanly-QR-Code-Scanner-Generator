package com.infinity.interactive.scanqr.generateqr.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.databinding.ActivityEditQrcodeBinding
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils
import com.infinity.interactive.scanqr.generateqr.utility.ResultOfTypeAndValue
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.regex.Matcher
import java.util.regex.Pattern
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class SaveQRCode() : AppCompatActivity() {
    var shareRel: RelativeLayout? = null
    var saveRel: RelativeLayout? = null
    var backImg: ImageView? = null
    var activity: Activity? = null
    var fixedTextView: TextView? = null
    var lastResult: String? = null
    lateinit var resultValues: ResultOfTypeAndValue
    var shouldShare = false
    var shouldSave = false
    var eveStart = ""
    var eveEnd = ""
    var wifiR1: RelativeLayout? = null
    var wifiR2: LinearLayout? = null
    var nameWifi: TextView? = null
    var securityWifi: TextView? = null
    var passwordWifi: TextView? = null
    var someAction = 0
    var date = Date()
    var dateEnd = Date()
    var CODE_TYPE = 0
    var arrow: ImageView? = null
    var edit: ImageView? = null
    var hiddenView: LinearLayout? = null
    var cardView: CardView? = null
    var fixed_layout: LinearLayout? = null
    var fixedLayoutImage: ImageView? = null
    var loadImage: ImageView? = null
    var dialog: Dialog? = null
    var action_btn1 = 0
    var action_btn2 = 0
    private var mContext: Context? = null
    private var result: TextView? = null
    private var result2: TextView? = null
    private var result3: TextView? = null
    private var result4: TextView? = null
    private var contentText: TextView? = null
    private var resultTextIcon: ImageView? = null
    private var resultTextIcon2: ImageView? = null
    private var resultTextIcon3: ImageView? = null
    private var resultTextIcon4: ImageView? = null
    private var resultL: RelativeLayout? = null
    private var resultL1: RelativeLayout? = null
    private var resultL2: RelativeLayout? = null
    private var resultL3: RelativeLayout? = null

    //View Binding
    private lateinit var editQrcodeBinding: ActivityEditQrcodeBinding

    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editQrcodeBinding = ActivityEditQrcodeBinding.inflate(layoutInflater)
        setContentView(editQrcodeBinding.root)
        initVars()
        initViews()
        initFunctionality()
        initListener()
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = (newWidth.toFloat()) / width
        val scaleHeight = (newHeight.toFloat()) / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        bm.recycle()
        return resizedBitmap
    }

    private fun initListener() {
        shareRel!!.setOnClickListener { v: View? ->
            shouldShare = true
            if (checkWritePermission(activity,mContext!!)) {
                AppUtils.shareImage(this@SaveQRCode, Constants.finalBitmap)

//                Intent share = new Intent(this, ShareAndCrop.class);
//                startActivity(share);
            }
        }
        saveRel!!.setOnClickListener { v: View? ->
            shouldShare = false
            shouldSave = true
            if (checkWritePermission(activity,mContext!!)) {
                AppUtils.saveImage(activity, QRCodeGeneratorScanner.inputStr, Constants.finalBitmap)
            }
        }
//        loadImage!!.setOnClickListener { v: View? ->
//            val intent: Intent = Intent(mContext, QRImageActivity::class.java)
//            startActivity(intent)
//        }
        backImg!!.setOnClickListener { v: View? ->
            try {
                val imm: InputMethodManager =
                    activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                var view1: View? = activity!!.getCurrentFocus()
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view1 == null) {
                    view1 = View(activity)
                }
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0)
            } catch (ignored: Exception) {
            }
            finish()
        }
        cardView!!.setOnClickListener { view: View? ->

            // If the CardView is already expanded, set its visibility
            //  to gone and change the expand less icon to expand more.
            if (hiddenView!!.getVisibility() == View.VISIBLE) {

                // The transition of the hiddenView is carried out
                //  by the TransitionManager class.
                // Here we use an object of the AutoTransition
                // Class to create a default transition.
                TransitionManager.beginDelayedTransition(
                    cardView,
                    AutoTransition()
                )
                arrow!!.setImageResource(R.drawable.down_arrow_scanner)
                arrow!!.setRotation(0f)
                val handler: Handler = Handler(Looper.getMainLooper())
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        hiddenView!!.setVisibility(View.GONE)
                    }
                }, 200)
            } else {
                TransitionManager.beginDelayedTransition(
                    cardView,
                    AutoTransition()
                )
                hiddenView!!.setVisibility(View.VISIBLE)
                arrow!!.setImageResource(R.drawable.down_arrow_scanner)
                arrow!!.setRotation(180f)
            }
        }
        edit!!.setOnClickListener { v: View? ->
            if (resultValues!!.getType() == Constants.TYPE_WIFI) {
                var plainTextWifi: String = nameWifi!!.getText().toString() + ""
                if (!securityWifi!!.getText().toString().contains("Result")) {
                    plainTextWifi = plainTextWifi + "\n" + securityWifi!!.getText().toString()
                }
                if (!passwordWifi!!.getText().toString().contains("Result")) {
                    plainTextWifi = plainTextWifi + "\n" + passwordWifi!!.getText().toString()
                }
            } else {
                var plainText: String = result!!.getText().toString() + ""
                if (!result2!!.getText().toString().contains("Result")) {
                    plainText = plainText + "\n" + result2!!.getText().toString()
                }
                if (!result3!!.getText().toString().contains("Result")) {
                    plainText = plainText + "\n" + result3!!.getText().toString()
                }
                if (!result4!!.getText().toString().contains("Result")) {
                    plainText = plainText + "\n" + result4!!.getText().toString()
                }
            }
        }

        editQrcodeBinding.printQrCode.setOnClickListener {
            if (checkWritePermission(activity,mContext!!)) {
                try {
                    if (Common.getAppPath(
                            applicationContext
                        ) != null
                    ) {
                        createPDFFile(
                            Common.getAppPath(
                                applicationContext
                            )
                        )
                    }
                } catch (ignored: Exception) {
                }
            }
        }

    }

    private fun showSaveDialog() {
        try {
            dialog!!.setCancelable(true)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.setDimAmount(0f)
            dialog!!.window!!.attributes.windowAnimations = R.style.DialogThemeNew
            dialog!!.setContentView(R.layout.save_dialogue)
            dialog!!.show()
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (dialog!!.window != null) {
                dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // This flag is required to set otherwise the setDimAmount method will not show any effect
                dialog!!.window!!.setDimAmount(0.7f) //0 for no dim to 1 for full dim
            }
            val savePNG = dialog!!.findViewById<LinearLayout>(R.id.save_png)
            val savePDF = dialog!!.findViewById<LinearLayout>(R.id.save_pdf)
            savePNG.setOnClickListener(View.OnClickListener {
                shouldSave = true
                dialog!!.dismiss()
                if (checkWritePermission(activity,mContext!!)) {
                    AppUtils.saveImage(
                        activity,
                        QRCodeGeneratorScanner.inputStr,
                        Constants.finalBitmap
                    )
                }
            })
            savePDF.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    Constants.isSelectingFile = true
                    shouldSave = false
                    dialog!!.dismiss()
                    if (checkWritePermission(activity,mContext!!)) {
                        try {
                            if (Common.getAppPath(
                                    applicationContext
                                ) != null
                            ) {
                                createPDFFile(
                                    Common.getAppPath(
                                        applicationContext
                                    )
                                )
                            }
                        } catch (ignored: Exception) {
                        }
                    }
                }
            })
        } catch (ignored: Exception) {
        }
    }

    private fun createPDFFile(path: String) {
        if (File(path).exists()) {
            File(path).delete()
        }
        try {
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(path))
            document.open()
            document.addCreationDate()
            val stream = ByteArrayOutputStream()
            Constants.finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val image = Image.getInstance(stream.toByteArray())
            //            image.scalePercent(30);
//            image.setAlignment(Element.ALIGN_MIDDLE);
            image.scaleToFit(PageSize.A4.width, PageSize.A4.height)
            val x = (PageSize.A4.width - image.scaledWidth) / 2
            val y = (PageSize.A4.height - image.scaledHeight) / 2
            image.setAbsolutePosition(x, y)
            document.add(image)

//            Toast.makeText(activity, "document created", Toast.LENGTH_SHORT).show();
            document.close()
            printPDFFile()
        } catch (ignored: Exception) {
        }
    }

    private fun printPDFFile() {
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        try {
            if (Common.getAppPath(applicationContext) != null) {
                val printDocumentAdapter: PrintDocumentAdapter = PdfDocumentAdapter(
                    this@SaveQRCode, Common.getAppPath(
                        applicationContext
                    )
                )
                printManager.print(
                    "Document",
                    printDocumentAdapter,
                    PrintAttributes.Builder().build()
                )
            }
        } catch (ignored: Exception) {
        }
    }

    //    private void saveAndShare(final boolean shouldShare, final String input, Bitmap bitmap) {
    //        if (checkWritePermission()) {
    ////            if (shouldShare) {
    ////                AppUtils.shareImage(mActivity, bitmap);
    ////            } else {
    ////                AppUtils.saveImage(mActivity, input, bitmap);
    ////
    ////            }
    //            AppUtils.saveImage(activity, input, bitmap);
    //
    //
    //        }
    //    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQ) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if ((permission == Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        if (shouldShare) {
//                            Intent share = new Intent(this, ShareAndCrop.class);
//                            startActivity(share);
                            AppUtils.shareImage(activity, Constants.finalBitmap)
                        } else {
                            if (shouldSave) {
                                AppUtils.saveImage(
                                    activity,
                                    QRCodeGeneratorScanner.inputStr,
                                    Constants.finalBitmap
                                )
                            } else {
                                try {
                                    if (Common.getAppPath(
                                            applicationContext
                                        ) != null
                                    ) {
                                        createPDFFile(
                                            Common.getAppPath(
                                                applicationContext
                                            )
                                        )
                                    }
                                } catch (ignored: Exception) {
                                }
                            }
                            //                            showSaveDialog();

//                        AppUtils.saveImage(activity,QRCodeGeneratorScanner.inputStr,Constants.finalBitmap);
                        }

//                        saveAndShare(shouldShare, inputStr, finalBitmap);
                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted))
                    }
                }
                //              else if (permission.equals(Manifest.permission.READ_CONTACTS)) {
////                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
//////                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//////                        startActivityForResult(intent, PICK_CONTACT);
////                    } else {
//////                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
////                    }
//                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkWritePermission(activity: Activity?, context: Context): Boolean {
        activity ?: return false

        // Define permissions for Android 14 and above
        val mediaPermissions = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )

        // Check Android version to determine which permissions to use
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Android 14 and above
            val allMediaPermissionsGranted = mediaPermissions.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }

            if (allMediaPermissionsGranted) {
                true
            } else {
                permissionsLauncher.launch(mediaPermissions)
                false
            }
        } else {
            // For Android versions below 14
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    Constants.PERMISSION_REQ
                )
                false
            }
        }
    }

    private fun initFunctionality() {

        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                // All permissions granted
            } else {
                // Permissions denied
            }
        }

        dialog = Dialog(this)
        val paramsCreateDoing = Bundle()
        paramsCreateDoing.putString("QRCompleted", "1")
        val paramsEndQRCode = Bundle()
        paramsEndQRCode.putString("QRCreateCycleEnded", "1")
        val paramsCreateSuccessFully = Bundle()
        paramsCreateSuccessFully.putString("QRCodeisCreatedSuccessfully_User", "1")

//        val firstRunEdit =
//            AppPreference.getInstance(this).getBoolean(PrefKey.EditActivityFirstRun, true)
//        if (firstRunEdit) {
//            val paramsCycle = Bundle()
//            paramsCycle.putString("QRDone", "1")
//            try {
//                FirebaseAnalytics.getInstance((mContext)!!)
//                    .logEvent("QRCreateCycleUnique", paramsCycle)
//            } catch (ignored: Exception) {
//            }
//            AppPreference.getInstance(this).setBoolean(PrefKey.EditActivityFirstRun, false)
//        }
//        val appLaunchCount =
//            AppPreference.getInstance(mContext).getIntegerDialogue(PrefKey.RATE_DIALOG_VALUE, 0)
//        AppPreference.getInstance(mContext)
//            .setInteger(PrefKey.RATE_DIALOG_VALUE, appLaunchCount + 1)
        val config = resources.configuration
        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            //in Right To Left layout
            backImg!!.rotation = 180f
        }
        loadImage!!.setImageBitmap(Constants.finalBitmap)
        lastResult = QRCodeGeneratorScanner.inputStr
        resultValues = AppUtils.getResourceType(QRCodeGeneratorScanner.inputStr)
        val finalResult = resultValues.getValue()
        if (resultValues.getType() == Constants.TYPE_TEXT) {
            //buttonAction.setVisibility(View.GONE);
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            //            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_url_icon);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_WEB) {
            //actionIcon.setImageResource(R.drawable.ic_web);


            //actionText.setText(getString(R.string.action_visit));
            action_btn1 = Constants.GO_URL
            contentText!!.visibility = View.VISIBLE
            contentText!!.text = "Website"
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            //            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_url_icon);
            resultTextIcon!!.setImageResource(R.drawable.url_scanner)
            //            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            fixedLayoutImage!!.setImageResource(R.drawable.url_scanner)
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_YOUTUBE) {
            //actionIcon.setImageResource(R.drawable.ic_video);


            //actionText.setText(getString(R.string.action_youtube));
            contentText!!.visibility = View.GONE
            action_btn1 = Constants.GO_URL
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            //            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_web_white);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            //for qr/barcode image
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_PHONE) {
            // actionIcon.setImageResource(R.drawable.ic_call);
            contentText!!.text = "Phone number"
            contentText!!.visibility = View.VISIBLE
            //actionText.setText(getString(R.string.action_call));
            action_btn1 = Constants.TO_CALL
            action_btn2 = Constants.ADD_CONTACT
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultTextIcon!!.setImageResource(R.drawable.call_scanner)
            //            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_call_icon);
//            resultActionBtn2.setVisibility(View.VISIBLE);
//            resultActionBtn2.setImageResource(R.drawable.ic_contact_icon);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            fixedLayoutImage!!.setImageResource(R.drawable.call_scanner)
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
        } else if (resultValues.getType() == Constants.TYPE_EMAIL) {
            //actionIcon.setImageResource(R.drawable.ic_email);
            resultTextIcon!!.setImageResource(R.drawable.email_scanner)
            fixedLayoutImage!!.setImageResource(R.drawable.email_scanner)

            //actionText.setText(getString(R.string.action_email));
            contentText!!.visibility = View.VISIBLE
            action_btn1 = Constants.SEND_EMAIL
            if (!Constants.emailType.isEmpty()) {
                contentText!!.visibility = View.VISIBLE
                contentText!!.text = Constants.emailType
                fixedTextView!!.text = Constants.emailType
            }
            if (Constants.subjectType.isEmpty()) {
                resultL!!.visibility = View.GONE
            }
            if (Constants.bodyType.isEmpty()) {
                resultL1!!.visibility = View.GONE
            }
            if (!Constants.subjectType.isEmpty()) {
                if (Constants.subjectType.contains("%20")) {
                    Constants.subjectType = Constants.subjectType.replace("%20", " ")
                }
                result!!.text = Constants.subjectType
                resultL!!.visibility = View.VISIBLE
            }
            if (!Constants.bodyType.isEmpty()) {
                if (Constants.bodyType.contains("%20")) {
                    Constants.bodyType = Constants.bodyType.replace("%20", " ")
                }
                result2!!.text = Constants.bodyType
                resultL1!!.visibility = View.VISIBLE
            }

//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_email_icon);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE

//            result.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (resultValues.getType() == Constants.TYPE_BARCODE) {
            resultTextIcon!!.setImageResource(R.drawable.ic_barcode_icon)
            fixedLayoutImage!!.setImageResource(R.drawable.ic_barcode_icon)
            var barCodeContent: String = ""
            if (lastResult!!.contains("barcode:")) {
                try {
                    val m = Pattern.compile("barcode:(.*)", Pattern.CASE_INSENSITIVE)
                        .matcher(lastResult)
                    while (m.find()) {
                        barCodeContent = m.group(1)
                        if (lastResult!!.contains("barcode:")) {
                            assert(barCodeContent != null)
                            barCodeContent =
                                barCodeContent.substring(0, barCodeContent.indexOf(";"))
                        }
                    }
                } catch (e: Exception) {
//                    Toast.makeText(activity, "Incorrect Barcode", Toast.LENGTH_SHORT).show();
                }
            }

//            String barCodeType="";

//            if(lastResult.contains("barCodeType:")){
//
//                try {
//                    Matcher m = Pattern.compile("barCodeType:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult);
//                    while (m.find()) {
//                        barCodeType = m.group(1);
//                        if(lastResult.contains("CODE_39")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("CODE_128")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("CODABAR")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("CODE_93")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("EAN_8")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (PRODUCT)";
//
//
//                        }
//                        else if(lastResult.contains("EAN_13")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (PRODUCT)";
//
//                        }
//                        else if(lastResult.contains("ITF")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("UPC_A")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (PRODUCT)";
//
//                        }
//                        else if(lastResult.contains("UPC_E")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (PRODUCT)";
//
//                        }
//                        else if(lastResult.contains("UPC_EAN_EXTENSION")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("RSS_14")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("RSS_EXPANDED")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                        else if(lastResult.contains("MAXICODE")){
//                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"));
//                            barCodeType=barCodeType + " (TEXT)";
//                        }
//                    }
//                }catch (Exception e){
//                    Toast.makeText(activity, "Incorrect Barcode Type", Toast.LENGTH_SHORT).show();
//                }
//            }
//            tileOfResult.setText(barCodeType);

//            if(lastResult.contains("barcode:")){
//                try {
//                    Matcher m = Pattern.compile("barcode:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult);
//                    while (m.find()) {
//                        barCodeContent = m.group(1);
//                        if(lastResult.contains("barcode:")){
//                            barCodeContent = barCodeContent.substring(0, barCodeType.indexOf(";"));
//                        }
//                    }
//                }catch (Exception e){
////                    Toast.makeText(mActivity, "Incorrect Barcode", Toast.LENGTH_SHORT).show();
//                }
//            }
            result!!.text = barCodeContent
            fixedTextView!!.text = barCodeContent
            contentText!!.visibility = View.GONE
            //buttonAction.setVisibility(View.GONE);
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            //
//            resultActionBtn5.setVisibility(View.GONE);
//            action_btn1 = Constants.SEARCH_IN_WEB;
//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_url_icon);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            CODE_TYPE = 1
        } else if (resultValues.getType() == Constants.TYPE_WIFI) {
            contentText!!.visibility = View.GONE
            // buttonAction.setVisibility(View.GONE);
            action_btn1 = Constants.WIFI_CONNECT
            if (!Constants.wifiName.isEmpty()) {
                nameWifi!!.text = Constants.wifiName
                fixedTextView!!.text = Constants.wifiName
            }
            if (!Constants.wifiSec.isEmpty()) {
                securityWifi!!.text = Constants.wifiSec
            }
            if (!Constants.wifiPass.isEmpty()) {
                passwordWifi!!.text = Constants.wifiPass
            }
            resultL!!.visibility = View.GONE
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            wifiR1!!.visibility = View.VISIBLE
            wifiR2!!.visibility = View.VISIBLE

//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_wifi_icon);
            fixedLayoutImage!!.setImageResource(R.drawable.wifi_scanner)
            //            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
        } else if (resultValues.getType() == Constants.TYPE_SMS) {
            resultTextIcon!!.setImageResource(R.drawable.call_scanner)
            fixedLayoutImage!!.setImageResource(R.drawable.sms_scanner)
            //buttonAction.setVisibility(View.GONE);
//            String number="",smsText="";
//            if(finalResult.contains("\n")){
//                finalResult=finalResult.replace("\n",";");
//
//            }
//            if(finalResult.contains(";")){
//                String[] smsArray=finalResult.split(";");
//                number=smsArray[0];
//                smsText=smsArray[1];
//                result.setText(number);
//                result2.setText(smsText);
//            }
            var number: String? = ""
            var message = ""
            lastResult = lastResult!!.replace("SMSTO:", "")
            lastResult = lastResult!!.replace("smsto:", "")
            //scannedResult = "rrrrr";
            number = lastResult
            if (lastResult!!.contains(":")) {
                val str =
                    lastResult!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                number = str[0]
                if (str.size > 1) {
                    for (i in 1 until str.size) {
                        if ((message == "")) {
                            message = str[i]
                        } else {
                            message = message + ":" + str[i]
                        }
                    }
                }
                //scannedResult = number + "\n" + message;
            }
            result!!.text = number
            fixedTextView!!.text = number
            result2!!.text = message
            contentText!!.text = "Sms to"
            contentText!!.visibility = View.VISIBLE
            resultTextIcon2!!.setImageResource(R.drawable.text_scanner)
            //            action_btn1 = Constants.ADD_CONTACT;
//            action_btn2 = Constants.SEND_SMS;
            resultL1!!.visibility = View.VISIBLE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE

//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_contact_icon);
//            resultActionBtn2.setVisibility(View.VISIBLE);
//            resultActionBtn2.setImageResource(R.drawable.ic_sms_icon);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_VCARD) {
            contentText!!.visibility = View.VISIBLE
            // buttonAction.setVisibility(View.GONE);
//            String[] contactArray=finalResult.split("\n");
//            contentText.setText(contactArray[0]);
//            result.setText(contactArray[1]);
//            result2.setText(contactArray[2]);
//            result3.setText(contactArray[3]);
            resultTextIcon!!.setImageResource(R.drawable.call_scanner)
            resultTextIcon2!!.setImageResource(R.drawable.email_scanner)
            resultTextIcon3!!.setImageResource(R.drawable.location_scanner)
            fixedLayoutImage!!.setImageResource(R.drawable.contacts_scanner)

//            resultL1.setVisibility(View.VISIBLE);
//            resultL2.setVisibility(View.VISIBLE);

//            resultActionBtn1.setVisibility(View.GONE);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setVisibility(View.GONE);
//            Toast.makeText(mActivity, Constants.name, Toast.LENGTH_SHORT).show();
            if (!Constants.name.isEmpty()) {
                contentText!!.text = Constants.name
                fixedTextView!!.text = Constants.name
            }
            if (!Constants.tel.isEmpty()) {
                Constants.tel = Constants.tel.replace("(^[\\r\\n]+|[\\r\\n]+$)".toRegex(), "")
                result!!.text = Constants.tel
                resultL!!.visibility = View.VISIBLE
            }
            if (!Constants.email.isEmpty()) {
                result2!!.text = Constants.email
                resultL1!!.visibility = View.VISIBLE
            }
            if (!Constants.address.isEmpty()) {
                Constants.address =
                    Constants.address.replace("(^[\\r\\n]+|[\\r\\n]+$)".toRegex(), "")
                result3!!.text = Constants.address
                resultL2!!.visibility = View.VISIBLE
            }
            val name = ""
            val org = ""
            val title = ""
            var tel = StringBuilder()
            var url: String = ""
            var email: String = ""
            val adr = ""
            val note = ""
            var m: Matcher
            if (lastResult!!.contains("BEGIN:VCARD") || lastResult!!.contains("begin:vcard")) {
                m = Pattern.compile("TEL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    tel.append("\n").append(m.group(1))
                    //                        action_btn1 = Constants.TO_CALL;
//                        action_btn2 = Constants.ADD_CONTACT;
//                        resultActionBtn1.setVisibility(View.VISIBLE);
//                        resultActionBtn1.setImageResource(R.drawable.ic_call_icon);
//                        resultActionBtn2.setVisibility(View.VISIBLE);
//                        resultActionBtn2.setImageResource(R.drawable.ic_contact_icon);
                }
                m = Pattern.compile("EMAIL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    email = m.group(1)
                    assert(email != null)
                    //                    if (!email.equals("")) {
////                        action_btn4 = Constants.SEND_EMAIL;
////                        resultActionBtn4.setVisibility(View.VISIBLE);
////                        resultActionBtn4.setImageResource(R.drawable.ic_email_icon);
//                    }
                }
            } else {
                m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    tel = StringBuilder(Objects.requireNonNull(m.group(1)))
                    tel = StringBuilder(tel.substring(0, tel.indexOf(";")))
                    //                    if (!tel.equals("")) {
////                        action_btn1 = Constants.TO_CALL;
////                        action_btn2 = Constants.ADD_CONTACT;
////                        resultActionBtn1.setVisibility(View.VISIBLE);
////                        resultActionBtn1.setImageResource(R.drawable.ic_call_icon);
////                        resultActionBtn2.setVisibility(View.VISIBLE);
////                        resultActionBtn2.setImageResource(R.drawable.ic_contact_icon);
//                    }
                }
                m = Pattern.compile("EMAIL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    email = m.group(1)
                    assert(email != null)
                    email = email.substring(0, email.indexOf(";"))
                    //                    if (!email.equals("")) {
////                        action_btn4 = Constants.SEND_EMAIL;
////                        resultActionBtn4.setVisibility(View.VISIBLE);
////                        resultActionBtn4.setImageResource(R.drawable.ic_email_icon);
//                    }
                }
            }
            m = Pattern.compile("URL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
            while (m.find()) {
                url = m.group(1)
                if (lastResult!!.contains("MECARD") || lastResult!!.contains("mecard")) {
                    assert(url != null)
                    url = url.substring(0, url.indexOf(";"))
                }
                assert(url != null)
                //                if (!url.equals("")) {
////                    action_btn3 = Constants.GO_URL;
////                    resultActionBtn3.setVisibility(View.VISIBLE);
////                    resultActionBtn3.setImageResource(R.drawable.ic_url_icon);
//                }
            }
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE

            /* m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult);
            while (m.find()) {
                tel = m.group(1);
                if (lastResult.contains("MECARD") || lastResult.contains("mecard")) {
                    tel = tel.substring(0, tel.indexOf(";"));
                }
                if (!tel.equals("")) {
                    action_btn1 = Constants.TO_CALL;
                    action_btn2 = Constants.ADD_CONTACT;
                    resultActionBtn1.setVisibility(View.VISIBLE);
                    resultActionBtn1.setImageResource(R.drawable.ic_call_white);
                    resultActionBtn2.setVisibility(View.VISIBLE);
                    resultActionBtn2.setImageResource(R.drawable.ic_contact_white);
                }
            }
            m = Pattern.compile("EMAIL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult);
            while (m.find()) {
                email = m.group(1);
                if (lastResult.contains("MECARD") || lastResult.contains("mecard")) {
                    email = email.substring(0, email.indexOf(";"));
                }
                if (!email.equals("")) {
                    action_btn4 = Constants.SEND_EMAIL;
                    resultActionBtn4.setVisibility(View.VISIBLE);
                    resultActionBtn4.setImageResource(R.drawable.ic_email_white);
                }
            }*/
        } else if (resultValues.getType() == Constants.TYPE_GEO) {
            someAction = 0

            //buttonAction.setVisibility(View.GONE);
//            action_btn1 = Constants.SEARCH_IN_WEB;
//
//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_url_icon);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setImageResource(R.drawable.ic_location_icon);
//            resultActionBtn5.setVisibility(View.VISIBLE);
            contentText!!.visibility = View.VISIBLE
            contentText!!.text = Constants.locationAddress
            fixedTextView!!.text = Constants.locationAddress
            result!!.text = "Geo: " + Constants.latitudeAddress + " : " + Constants.longitudeAddress
            resultTextIcon!!.setImageResource(R.drawable.location_scanner)
            fixedLayoutImage!!.setImageResource(R.drawable.location_scanner)
            resultL3!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_EVENT) {
            resultTextIcon!!.setImageResource(R.drawable.ic_baseline_access_time_24)
            resultTextIcon2!!.setImageResource(R.drawable.location_scanner)
            resultTextIcon3!!.setImageResource(R.drawable.text_scanner)
            fixedLayoutImage!!.setImageResource(R.drawable.event_scanner)
            someAction = 1
            //buttonAction.setVisibility(View.GONE);
//            action_btn1 = Constants.SEARCH_IN_WEB;
//            resultActionBtn1.setVisibility(View.VISIBLE);
//            resultActionBtn1.setImageResource(R.drawable.ic_url_icon);
//            resultActionBtn2.setVisibility(View.GONE);
//            resultActionBtn3.setVisibility(View.GONE);
//            resultActionBtn4.setVisibility(View.GONE);
//            resultActionBtn5.setImageResource(R.drawable.ic_event_icon);
//            resultActionBtn5.setVisibility(View.VISIBLE);
            if (!Constants.titleEvent.isEmpty()) {
                contentText!!.text = Constants.titleEvent
                contentText!!.visibility = View.VISIBLE
                fixedTextView!!.text = Constants.titleEvent
            }
            if (!Constants.eventStartTime.isEmpty() && !Constants.eventEndTime.isEmpty()) {
                eveStart = Constants.eventStartTime
                eveEnd = Constants.eventEndTime
                val strDate = "2013-05-15T10:00:00-0700"
                var formatter = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                try {
                    date = formatter.parse(eveStart)
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                try {
                    dateEnd = formatter.parse(eveEnd)
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                result!!.text = formatter.format(date) + "\n" + formatter.format(dateEnd)
                resultL!!.visibility = View.VISIBLE
            } else if (!Constants.eventStartTime.isEmpty()) {
                eveStart = Constants.eventStartTime
                var formatter = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                try {
                    date = formatter.parse(eveStart)
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                result!!.text = formatter.format(date)
                resultL!!.visibility = View.VISIBLE
            } else if (!Constants.eventEndTime.isEmpty()) {
                eveEnd = Constants.eventEndTime
                var formatter = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                try {
                    dateEnd = formatter.parse(eveEnd)
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                result!!.text = formatter.format(eveEnd)
                resultL!!.visibility = View.VISIBLE
            }
            if (!Constants.eventLocation.isEmpty()) {
                result2!!.text = Constants.eventLocation
                resultL1!!.visibility = View.VISIBLE
            }
            if (!Constants.eventDescription.isEmpty()) {
                result3!!.text = Constants.eventDescription
                resultL2!!.visibility = View.VISIBLE
            }
            resultTextIcon2!!.visibility = View.VISIBLE
            resultTextIcon3!!.visibility = View.VISIBLE
            resultL3!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_FACEBOOK) {
            resultTextIcon!!.setImageResource(R.drawable.facebook_icon)
            if (finalResult.contains("https://www.facebook.com/profile.php?id=")) {
                contentText!!.text = "Username"
            } else if (finalResult.contains("https://www.facebook.com/groups/")) {
                contentText!!.text = "Group"
            } else if (finalResult.contains("https://www.facebook.com/")) {
                contentText!!.text = "Page"
            }
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            fixedLayoutImage!!.setImageResource(R.drawable.facebook_icon)
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_TWITTER) {
            resultTextIcon!!.setImageResource(R.drawable.twitter_icon)
            fixedLayoutImage!!.setImageResource(R.drawable.twitter_icon)
            contentText!!.text = "Username"
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_LINKDEIN) {
            if (finalResult.contains("https://www.linkedin.com/in/")) {
                contentText!!.text = "Profile"
            } else if (finalResult.contains("https://www.linkedin.com/feed/")) {
                contentText!!.text = "Feed"
            } else if (finalResult.contains("https://www.linkedin.com/company/")) {
                contentText!!.text = "Company"
            } else if (finalResult.contains("https://www.linkedin.com/hiring/jobs/")) {
                contentText!!.text = "Job"
            }
            resultTextIcon!!.setImageResource(R.drawable.linkdein_icon)
            fixedLayoutImage!!.setImageResource(R.drawable.linkdein_icon)
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_WHATSAPP) {
            resultTextIcon!!.setImageResource(R.drawable.whatsapp_icon)
            fixedLayoutImage!!.setImageResource(R.drawable.whatsapp_icon)
            contentText!!.text = "Whatsapp"
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.GONE
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_INSTAGRAM) {
            resultTextIcon!!.setImageResource(R.drawable.instagram_icon)
            fixedLayoutImage!!.setImageResource(R.drawable.instagram_icon)
            contentText!!.text = "Username"
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            result!!.text = finalResult
            fixedTextView!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
        }
    }

    private fun initViews() {
        shareRel = findViewById(R.id.shareCode)
        saveRel = findViewById(R.id.saveCode)
        result = findViewById(R.id.result)
        result2 = findViewById(R.id.result2)
        result3 = findViewById(R.id.result3)
        result4 = findViewById(R.id.result4)
        resultL = findViewById(R.id.result_l1)
        resultL1 = findViewById(R.id.result_l2)
        resultL2 = findViewById(R.id.result_l3)
        resultL3 = findViewById(R.id.result_l4)
        resultTextIcon = findViewById(R.id.resultTextIcon)
        resultTextIcon2 = findViewById(R.id.result2TextIcon)
        resultTextIcon3 = findViewById(R.id.result3TextIcon)
        resultTextIcon4 = findViewById(R.id.result4TextIcon)
        cardView = findViewById(R.id.base_cardview)
        arrow = findViewById(R.id.arrow_button)
        hiddenView = findViewById(R.id.hidden_view)
        contentText = findViewById(R.id.content)
        fixedTextView = findViewById(R.id.fixedDropTextView)
        fixedLayoutImage = findViewById(R.id.fixedLayoutImage)
        fixed_layout = findViewById(R.id.fixed_layout)
        wifiR1 = findViewById(R.id.rel3)
        wifiR2 = findViewById(R.id.rel4)
        nameWifi = findViewById(R.id.wifiName)
        securityWifi = findViewById(R.id.wifiSec)
        passwordWifi = findViewById(R.id.wifiPass)
        edit = findViewById(R.id.edit_button)
        loadImage = findViewById(R.id.qr_img)
        backImg = findViewById(R.id.back_img)
    }

    fun dismissProgressDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    private fun initVars() {
        activity = this
        mContext = (activity as SaveQRCode).applicationContext
    }

    companion object {
        fun Swing(view: View?): AnimatorSet {
            val animatorSet = AnimatorSet()
            val `object` =
                ObjectAnimator.ofFloat(view, "rotation", 0f, 10f, -10f, 6f, -6f, 3f, -3f, 0f)
            animatorSet.playTogether(`object`)
            return animatorSet
        }
    }
}