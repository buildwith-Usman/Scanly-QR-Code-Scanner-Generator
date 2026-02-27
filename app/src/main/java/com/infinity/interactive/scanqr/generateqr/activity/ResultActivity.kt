package com.infinity.interactive.scanqr.generateqr.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils
import com.infinity.interactive.scanqr.generateqr.utility.CodeGenerator
import com.infinity.interactive.scanqr.generateqr.utility.ResultOfTypeAndValue
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.Objects
import java.util.regex.Matcher
import java.util.regex.Pattern

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class ResultActivity : AppCompatActivity() {
    var resultQrCodeImage: ImageView? = null
    private lateinit var arrayList: ArrayList<String>
    private lateinit var colorList: ArrayList<String>
    var lastResult: String? = null
    var colorResult = Color.BLACK
    var resultForFragment = 0
    var positionForHistoryFragment = 0
    private lateinit var resultValues: ResultOfTypeAndValue

    //    ColorPicker cp1;
    //    Button btnColor;
    var shouldShare = false
    var colorRed = 0
    var colorGreen = 0
    var colorBlue = 0
    var latitude = ""
    var longitude = ""
    var eveStart = ""
    var eveEnd = ""
    var infoResult: ImageView? = null
    var settingResult: ImageView? = null
    var removeAds: LinearLayout? = null
    var wifiR1: RelativeLayout? = null
    var wifiR2: LinearLayout? = null
    var nameWifi: TextView? = null
    var securityWifi: TextView? = null
    var passwordWifi: TextView? = null
    var someAction = 0
    var date = Date()
    var dateEnd = Date()
    var backResult: ImageView? = null
    var decorate: ImageView? = null
    var CODE_TYPE = 0
    var editInfo: ImageView? = null
    var editInfoWifi: ImageView? = null
    var copy1: ImageView? = null
    var copy2: ImageView? = null
    var copy3: ImageView? = null
    var copy4: ImageView? = null
    var storeSearch: LinearLayout? = null
    var barCodeContent: String = ""

    //    private StartAppAd mStartUpInterstitialAd;
    var barImage: ImageView? = null
    var qr_layout: CardView? = null
    var actionIcon: ImageView? = null
    var resultIcon: ImageView? = null
    var resultTextIcon: ImageView? = null
    var resultTextIcon2: ImageView? = null
    var resultTextIcon3: ImageView? = null
    var resultTextIcon4: ImageView? = null
    var searchAmazon: ImageView? = null
    var searchEbay: ImageView? = null

    //    Banner banner ;
    //    RelativeLayout bannerRel,banner_ad_loading;
    //private LinearLayout buttonCopy, buttonSearch, buttonShare, buttonAction;
    var codeColorBtn: ImageView? = null
    var codeSaveBtn: ImageView? = null
    var codeShareBtn: ImageView? = null
    var resultCopyBtn: ImageView? = null
    var resultShareBtn: ImageView? = null
    var resultActionBtn1: ImageView? = null
    var resultActionBtn2: ImageView? = null
    var resultActionBtn3: ImageView? = null
    var resultActionBtn4: ImageView? = null
    var resultActionBtn5: ImageView? = null
    var expensePreOrder: TextView? = null
    var adsRemoveImageView: ImageView? = null
    private var mActivity: Activity? = null
    private var mContext: Context? = null
    private var result: TextView? = null
    private var result2: TextView? = null
    private var result3: TextView? = null
    private var result4: TextView? = null
    private val actionText: TextView? = null
    private var scannedCodeType: TextView? = null
    private var tileOfResult: TextView? = null
    private var contentText: TextView? = null
    private var resultL: LinearLayout? = null
    private var resultL1: LinearLayout? = null
    private var resultL2: LinearLayout? = null
    private var resultL3: LinearLayout? = null
    private var output: Bitmap? = null
    private var action_btn1 = 0
    private var action_btn2 = 0
    private var action_btn3 = 0
    private var action_btn4 = 0

    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        Log.d("checkOnCreate", "onCreateCalled");
        initVars()
        initViews()
        initFunctionality()
        initListeners()
        val currentFocus = mActivity!!.currentFocus
        currentFocus?.clearFocus()
    }

    private fun initVars() {
        mActivity = this@ResultActivity
        mContext = (mActivity as ResultActivity).getApplicationContext()

    }

    private fun initViews() {
        setContentView(R.layout.activity_result)
        //        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
        //actionText = (TextView) findViewById(R.id.actionText);
        scannedCodeType = findViewById(R.id.scanned_result_type_of_code)
        tileOfResult = findViewById(R.id.scanned_result_tile)
        actionIcon = findViewById(R.id.actionIcon)
        resultIcon = findViewById(R.id.resultIcon)
        contentText = findViewById(R.id.content)
        //        mShimmerViewContainer=findViewById(R.id.shimmer_view_container);
        /* buttonCopy = (LinearLayout) findViewById(R.id.buttonCopy);
        buttonSearch = (LinearLayout) findViewById(R.id.buttonSearch);
        buttonShare = (LinearLayout) findViewById(R.id.buttonShare);
        buttonAction = (LinearLayout) findViewById(R.id.buttonAction);*/codeColorBtn =
            findViewById(R.id.color_of_result_qrcode_btn)
        codeSaveBtn = findViewById(R.id.save_of_result_qrcode_btn)
        codeShareBtn = findViewById(R.id.share_of_result_qrcode_btn)
        resultCopyBtn = findViewById(R.id.copy_result_btn)
        resultShareBtn = findViewById(R.id.share_result_btn)
        resultActionBtn1 = findViewById(R.id.action1_result_btn)
        resultActionBtn2 = findViewById(R.id.action2_result_btn)
        resultActionBtn3 = findViewById(R.id.action3_result_btn)
        resultActionBtn4 = findViewById(R.id.action4_result_btn)
        resultActionBtn5 = findViewById(R.id.action5_result_btn)
        resultQrCodeImage = findViewById(R.id.result_qr_code_img)
        wifiR1 = findViewById(R.id.rel1)
        wifiR2 = findViewById(R.id.rel2)
        nameWifi = findViewById(R.id.wifiName)
        securityWifi = findViewById(R.id.wifiSec)
        passwordWifi = findViewById(R.id.wifiPass)

//        cp1 = new ColorPicker(mActivity, colorRed, colorGreen, colorBlue);
//        btnColor = cp1.findViewById(R.id.okColorButton);
        backResult = findViewById(R.id.backButtonResult)
        decorate = findViewById(R.id.decorate)
        infoResult = findViewById(R.id.infoQRRes)
        settingResult = findViewById(R.id.settingsQRCreateResult)
        editInfo = findViewById(R.id.editDialog)
        editInfoWifi = findViewById(R.id.editDialogWifi)
        copy1 = findViewById(R.id.copy_1)
        copy2 = findViewById(R.id.copy_2)
        copy3 = findViewById(R.id.copy_3)
        copy4 = findViewById(R.id.copy_4)
        storeSearch = findViewById(R.id.search_on_store)
        searchAmazon = findViewById(R.id.search_on_amazon)
        searchEbay = findViewById(R.id.search_on_ebay)
        barImage = findViewById(R.id.result_bar_code_img)
        qr_layout = findViewById(R.id.qr_layout)
        removeAds = findViewById(R.id.remove)
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

        val anim = ValueAnimator.ofFloat(1f, 1.2f)
        anim.setDuration(1000)
        anim.addUpdateListener { animation: ValueAnimator ->
            decorate!!.scaleX = (animation.animatedValue as Float)
            decorate!!.scaleY = (animation.animatedValue as Float)
        }
        anim.repeatCount = ValueAnimator.INFINITE
        anim.repeatMode = ValueAnimator.REVERSE
        anim.start()
        val params = Bundle()
        val b = intent.extras
        if (b != null) {
            resultForFragment = b.getInt("key")
            positionForHistoryFragment = b.getInt("position")
        }
        try {
            arrayList =
                AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
            if (resultForFragment == Constants.HISTORY_SCAN_FRAGMENT) {


                //setResult
                arrayList = AppPreference.getInstance(mContext)
                    .getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
                Collections.reverse(arrayList)
                lastResult = arrayList.get(positionForHistoryFragment)
                //setColor
//            colorList = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED);
//            Collections.reverse(colorList);
//            colorResult = Integer.parseInt(colorList.get(positionForHistoryFragment));
            } else if (resultForFragment == Constants.HISTORY_GENERATE_FRAGMENT) {

                //setResult
                arrayList = AppPreference.getInstance(mContext)
                    .getStringArray(PrefKey.RESULT_LIST_OF_CREATED)
                Collections.reverse(arrayList)
                lastResult = arrayList.get(positionForHistoryFragment)
                //setColor
//            colorList = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_CREATED);
//            Collections.reverse(colorList);
//            colorResult = Integer.parseInt(colorList.get(positionForHistoryFragment));
            } else if (resultForFragment == Constants.SCAN_FRAGMENT) {
//                val alertDialog = AlertDialog.Builder(mActivity)
//                val vi = mActivity!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                val customLayoutpermission = vi.inflate(R.layout.scanned_dialog, null)
//                val alert = alertDialog.create()
//                alert.setView(customLayoutpermission)
//                val ok = customLayoutpermission.findViewById<ImageView>(R.id.img2)
//                ok.setOnClickListener { v: View? -> alert.dismiss() }
//                alert.setCancelable(false)
//                alert.show()
//                alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                val width =
//                    (resources.displayMetrics.widthPixels * 0.80).toInt() //<-- int width=400;
//                val height = (resources.displayMetrics.heightPixels * 0.20).toInt()
//                alert.window!!.setLayout(width, height)

                //setResult
                arrayList = AppPreference.getInstance(mContext)
                    .getStringArray(PrefKey.RESULT_LIST_OF_SCANNED)
                lastResult = arrayList.get(arrayList.size - 1)
                //setColor
//            colorList = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_SCANNED);
//            Collections.reverse(colorList);
//            colorResult = Integer.parseInt(colorList.get(colorList.size() - 1));
            }
        } catch (ignored: Exception) {
        }


//        Log.d("CCCC", colorList.toString());

        // TODO Sample banner Ad implementation
//        AdManager.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adViewResult));
//        Log.d("loci",lastResult);
        resultValues = AppUtils.getResourceType(lastResult)
        val finalResult = resultValues.getValue()
        //        Log.d("loci1",finalResult);
//        Log.d("barCodeResult",lastResult);
        CodeGenerator.setBLACK(colorResult)
        //        cp1.setColor(colorResult);


//        btnColor.setBackgroundColor(colorResult);
        //int type = AppUtils.getResourceType(lastResult);
        if (resultValues.getType() == Constants.TYPE_TEXT) {
            //buttonAction.setVisibility(View.GONE);
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_text)
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "text")
        } else if (resultValues.getType() == Constants.TYPE_WEB) {
            //actionIcon.setImageResource(R.drawable.ic_web);
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_url)
            resultIcon!!.setImageResource(R.drawable.url_scanner)
            //actionText.setText(getString(R.string.action_visit));
            action_btn1 = Constants.GO_URL
            contentText!!.visibility = View.VISIBLE
            contentText!!.text = resources.getString(R.string.website)
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultTextIcon!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "url")
        } else if (resultValues.getType() == Constants.TYPE_YOUTUBE) {
            //actionIcon.setImageResource(R.drawable.ic_video);
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_youtube)
            //            resultIcon.setImageResource(R.drawable.ic_video);
            //actionText.setText(getString(R.string.action_youtube));
            contentText!!.visibility = View.GONE
            action_btn1 = Constants.GO_URL
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            generateCode(lastResult) //for qr/barcode image
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
        } else if (resultValues.getType() == Constants.TYPE_PHONE) {
            // actionIcon.setImageResource(R.drawable.ic_call);
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_phone)
            resultIcon!!.setImageResource(R.drawable.call_scanner)
            contentText!!.text = resources.getString(R.string.phone_number)
            contentText!!.visibility = View.VISIBLE
            //actionText.setText(getString(R.string.action_call));
            action_btn1 = Constants.TO_CALL
            action_btn2 = Constants.ADD_CONTACT
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultTextIcon!!.setImageResource(R.drawable.call_scanner)
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.call_scanner)
            resultActionBtn2!!.visibility = View.VISIBLE
            resultActionBtn2!!.setImageResource(R.drawable.contacts_scanner)
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            result!!.text = finalResult
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "phone")
        } else if (resultValues.getType() == Constants.TYPE_EMAIL) {
            //actionIcon.setImageResource(R.drawable.ic_email);
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_email)
            resultIcon!!.setImageResource(R.drawable.email_scanner)
            resultTextIcon!!.setImageResource(R.drawable.email_scanner)


            //actionText.setText(getString(R.string.action_email));
            contentText!!.visibility = View.VISIBLE
            action_btn1 = Constants.SEND_EMAIL
            if (!Constants.emailType.isEmpty()) {
                contentText!!.visibility = View.VISIBLE
                contentText!!.text = Constants.emailType
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
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.email_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "email")
            //            result.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (resultValues.getType() == Constants.TYPE_BARCODE) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_barcode)
            resultIcon!!.setImageResource(R.drawable.ic_barcode_icon)
            resultTextIcon!!.setImageResource(R.drawable.ic_barcode_icon)


//            if(lastResult.contains("barcode:")){
//                lastResult=lastResult.replace("barcode:","");
//            }
            var barCodeType = ""
            if (lastResult!!.contains("barCodeType:")) {
                try {
                    val m = Pattern.compile("barCodeType:(.*)", Pattern.CASE_INSENSITIVE)
                        .matcher(lastResult)
                    while (m.find()) {
                        barCodeType = m.group(1)
                        assert(barCodeType != null)
                        if (lastResult!!.contains("CODE_39")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_39
                            params.putString("scan", "barcode_39")
                        } else if (lastResult!!.contains("CODE_128")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_128
                            params.putString("scan", "barcode_128")
                        } else if (lastResult!!.contains("CODABAR")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODABAR
                            params.putString("scan", "barcode_codabar")
                        } else if (lastResult!!.contains("CODE_93")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_93
                            params.putString("scan", "barcode_93")
                        } else if (lastResult!!.contains("EAN_8")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (PRODUCT)"
                            Constants.format = BarcodeFormat.EAN_8
                            storeSearch!!.visibility = View.VISIBLE
                            params.putString("scan", "barcode_ean_8")
                        } else if (lastResult!!.contains("EAN_13")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (PRODUCT)"
                            Constants.format = BarcodeFormat.EAN_13
                            storeSearch!!.visibility = View.VISIBLE
                            params.putString("scan", "barcode_ean_13")
                        } else if (lastResult!!.contains("ITF")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.ITF
                            params.putString("scan", "barcode_ITF")
                        } else if (lastResult!!.contains("UPC_A")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (PRODUCT)"
                            storeSearch!!.visibility = View.VISIBLE
                            Constants.format = BarcodeFormat.UPC_A
                            params.putString("scan", "barcode_UPC_A")
                        } else if (lastResult!!.contains("UPC_E")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (PRODUCT)"
                            storeSearch!!.visibility = View.VISIBLE
                            Constants.format = BarcodeFormat.UPC_E
                            params.putString("scan", "barcode_UPC_E")
                        } else if (lastResult!!.contains("UPC_EAN_EXTENSION")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.UPC_EAN_EXTENSION
                            params.putString("scan", "barcode_UPC_EAN_EXTENSION")
                        } else if (lastResult!!.contains("RSS_14")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_128
                            params.putString("scan", "barcode_RSS_14")
                        } else if (lastResult!!.contains("RSS_EXPANDED")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_128
                            params.putString("scan", "barcode_RSS_EXPANDED")
                        } else if (lastResult!!.contains("MAXICODE")) {
                            barCodeType = barCodeType.substring(0, barCodeType.indexOf(";"))
                            barCodeType = "$barCodeType (TEXT)"
                            Constants.format = BarcodeFormat.CODE_128
                            params.putString("scan", "barcode_MAXICODE")
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(mActivity, "Incorrect Barcode Type", Toast.LENGTH_SHORT).show()
                }
            }
            tileOfResult!!.text = barCodeType
            if (lastResult!!.contains("barcode:")) {
                try {
                    val m = Pattern.compile("barcode:(.*)", Pattern.CASE_INSENSITIVE)
                        .matcher(lastResult)
                    while (m.find()) {
                        barCodeContent = m.group(1)
                        if (lastResult!!.contains("barcode:")) {
                            assert(barCodeContent != null)
                            barCodeContent = barCodeContent.substring(0, barCodeType.indexOf(";"))
                        }
                    }
                } catch (e: Exception) {
//                    Toast.makeText(mActivity, "Incorrect Barcode", Toast.LENGTH_SHORT).show();
                }
            }
            assert(barCodeContent != null)
            barCodeContent = barCodeContent!!.replace(";", "")
            lastResult = barCodeContent
            result!!.text = barCodeContent
            contentText!!.visibility = View.GONE
            //buttonAction.setVisibility(View.GONE);
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            action_btn1 = Constants.SEARCH_IN_WEB
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            CODE_TYPE = 1
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            resultQrCodeImage!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.VISIBLE
        } else if (resultValues.getType() == Constants.TYPE_WIFI) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_wifi)
            resultIcon!!.setImageResource(R.drawable.wifi_scanner)
            contentText!!.visibility = View.GONE
            // buttonAction.setVisibility(View.GONE);
            action_btn1 = Constants.WIFI_CONNECT
            if (!Constants.wifiName.isEmpty()) {
                nameWifi!!.text = Constants.wifiName
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
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.wifi_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "wifi")
        } else if (resultValues.getType() == Constants.TYPE_SMS) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_sms)
            //            Log.d("sms",lastResult);
//            Log.d("sms",finalResult);
            resultIcon!!.setImageResource(R.drawable.sms_scanner)
            resultTextIcon!!.setImageResource(R.drawable.call_scanner)
            //buttonAction.setVisibility(View.GONE);
//            Log.d("sms_result",finalResult);
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
            var message = StringBuilder()
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
                        if (message.toString() == "") {
                            message = StringBuilder(str[i])
                        } else {
                            message.append(":").append(str[i])
                        }
                    }
                }
                //scannedResult = number + "\n" + message;
            }
            result!!.text = number
            result2!!.text = message.toString()
            contentText!!.text = resources.getString(R.string.sms_to)
            contentText!!.visibility = View.VISIBLE
            resultTextIcon2!!.setImageResource(R.drawable.text_scanner)
            action_btn1 = Constants.ADD_CONTACT
            action_btn2 = Constants.SEND_SMS
            resultL1!!.visibility = View.VISIBLE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.contacts_scanner)
            resultActionBtn2!!.visibility = View.VISIBLE
            resultActionBtn2!!.setImageResource(R.drawable.sms_scanner)
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "sms")
        } else if (resultValues.getType() == Constants.TYPE_VCARD) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_vcard)
            resultIcon!!.setImageResource(R.drawable.contacts_scanner)
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


//            Log.d("contactResult",lastResult);
//            resultL1.setVisibility(View.VISIBLE);
//            resultL2.setVisibility(View.VISIBLE);
            resultActionBtn1!!.visibility = View.GONE
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            //            Toast.makeText(mActivity, Constants.name, Toast.LENGTH_SHORT).show();
//            Log.d("nameOfContact",Constants.name);
            if (!Constants.name.isEmpty()) {
                contentText!!.text = Constants.name
            }
            if (!Constants.tel.isEmpty()) {
//                Log.d("telephoneFromVcard",Constants.tel);
                Constants.tel = Constants.tel.replace("(^[\\r\\n]+|[\\r\\n]+$)".toRegex(), "")
                result!!.text = Constants.tel
                resultL!!.visibility = View.VISIBLE
            }
            if (!Constants.email.isEmpty()) {
                result2!!.text = Constants.email
                resultL1!!.visibility = View.VISIBLE
            }
            if (!Constants.address.isEmpty()) {
//                Log.d("addressFromVcard",Constants.address);
                Constants.address =
                    Constants.address.replace("(^[\\r\\n]+|[\\r\\n]+$)".toRegex(), "")
                result3!!.text = Constants.address
                resultL2!!.visibility = View.VISIBLE
            }
            val name = ""
            val org = ""
            val title = ""
            var tel = StringBuilder()
            var url = ""
            var email = ""
            val adr = ""
            val note = ""
            var m: Matcher
            if (lastResult!!.contains("BEGIN:VCARD") || lastResult!!.contains("begin:vcard")) {
                m = Pattern.compile("TEL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    tel.append("\n").append(m.group(1))
                    action_btn1 = Constants.TO_CALL
                    action_btn2 = Constants.ADD_CONTACT
                    resultActionBtn1!!.visibility = View.VISIBLE
                    resultActionBtn1!!.setImageResource(R.drawable.call_scanner)
                    resultActionBtn2!!.visibility = View.VISIBLE
                    resultActionBtn2!!.setImageResource(R.drawable.contacts_scanner)
                }
                //                Log.d("TEEEE", tel);
                m = Pattern.compile("EMAIL.*:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    email = m.group(1)
                    assert(email != null)
                    if (email != "") {
                        action_btn4 = Constants.SEND_EMAIL
                        resultActionBtn4!!.visibility = View.VISIBLE
                        resultActionBtn4!!.setImageResource(R.drawable.email_scanner)
                    }
                }
            } else {
                m = Pattern.compile("TEL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    tel = StringBuilder(Objects.requireNonNull(m.group(1)))
                    tel = StringBuilder(tel.substring(0, tel.indexOf(";")))
                    if (tel.toString() != "") {
                        action_btn1 = Constants.TO_CALL
                        action_btn2 = Constants.ADD_CONTACT
                        resultActionBtn1!!.visibility = View.VISIBLE
                        resultActionBtn1!!.setImageResource(R.drawable.call_scanner)
                        resultActionBtn2!!.visibility = View.VISIBLE
                        resultActionBtn2!!.setImageResource(R.drawable.contacts_scanner)
                    }
                }
                m = Pattern.compile("EMAIL:(.*)", Pattern.CASE_INSENSITIVE).matcher(lastResult)
                while (m.find()) {
                    email = m.group(1)
                    assert(email != null)
                    email = email.substring(0, email.indexOf(";"))
                    if (email != "") {
                        action_btn4 = Constants.SEND_EMAIL
                        resultActionBtn4!!.visibility = View.VISIBLE
                        resultActionBtn4!!.setImageResource(R.drawable.email_scanner)
                    }
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
                if (url != "") {
                    action_btn3 = Constants.GO_URL
                    resultActionBtn3!!.visibility = View.VISIBLE
                    resultActionBtn3!!.setImageResource(R.drawable.url_scanner)
                }
            }
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE

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
            }*/params.putString("scan", "contact")
        } else if (resultValues.getType() == Constants.TYPE_GEO) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_geo)
            resultIcon!!.setImageResource(R.drawable.location_scanner)
            someAction = 0

            //buttonAction.setVisibility(View.GONE);
            action_btn1 = Constants.SEARCH_IN_WEB
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.setImageResource(R.drawable.location_scanner)
            resultActionBtn5!!.visibility = View.VISIBLE
            if (!Constants.locationAddress.isEmpty()) {
                contentText!!.visibility = View.VISIBLE
                contentText!!.text = Constants.locationAddress
            }
            result!!.text =
                resources.getString(R.string.geo) + Constants.latitudeAddress + " : " + Constants.longitudeAddress
            resultTextIcon!!.setImageResource(R.drawable.location_scanner)
            resultL3!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "location")
        } else if (resultValues.getType() == Constants.TYPE_EVENT) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_event)
            resultIcon!!.setImageResource(R.drawable.event_scanner)
            resultTextIcon!!.setImageResource(R.drawable.ic_baseline_access_time_24)
            resultTextIcon2!!.setImageResource(R.drawable.location_scanner)
            resultTextIcon3!!.setImageResource(R.drawable.text_scanner)
            someAction = 1
            //buttonAction.setVisibility(View.GONE);
            action_btn1 = Constants.SEARCH_IN_WEB
            resultActionBtn1!!.visibility = View.GONE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.setImageResource(R.drawable.event_scanner)
            resultActionBtn5!!.visibility = View.VISIBLE
            if (!Constants.titleEvent.isEmpty()) {
                contentText!!.text = Constants.titleEvent
                contentText!!.visibility = View.VISIBLE
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
                result!!.text = """
                    ${formatter.format(date)}
                    ${formatter.format(dateEnd)}
                    """.trimIndent()
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
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "event")
        } else if (resultValues.getType() == Constants.TYPE_FACEBOOK) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_facebook)
            resultIcon!!.setImageResource(R.drawable.facebook_icon)
            resultTextIcon!!.setImageResource(R.drawable.facebook_icon)
            if (finalResult.contains("https://www.facebook.com/profile.php?id=")) {
                contentText!!.text = resources.getString(R.string.username_only)
            } else if (finalResult.contains("https://www.facebook.com/groups/")) {
                contentText!!.text = resources.getString(R.string.group)
            } else if (finalResult.contains("https://www.facebook.com/")) {
                contentText!!.text = resources.getString(R.string.page)
            }
            //            contentText.setText("Username");
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "facebook")
        } else if (resultValues.getType() == Constants.TYPE_TWITTER) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_twitter)
            resultIcon!!.setImageResource(R.drawable.twitter_icon)
            resultTextIcon!!.setImageResource(R.drawable.twitter_icon)
            contentText!!.text = resources.getString(R.string.username_only)
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "twitter")
        } else if (resultValues.getType() == Constants.TYPE_LINKDEIN) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_linkdein)
            resultIcon!!.setImageResource(R.drawable.linkdein_icon)
            resultTextIcon!!.setImageResource(R.drawable.linkdein_icon)
            if (finalResult.contains("https://www.linkedin.com/in/")) {
                contentText!!.text = resources.getString(R.string.profile)
            } else if (finalResult.contains("https://www.linkedin.com/feed/")) {
                contentText!!.text = resources.getString(R.string.feed)
            } else if (finalResult.contains("https://www.linkedin.com/company/")) {
                contentText!!.text = resources.getString(R.string.company)
            } else if (finalResult.contains("https://www.linkedin.com/hiring/jobs/")) {
                contentText!!.text = resources.getString(R.string.job)
            }
            //            contentText.setText("Username");
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "linkdien")
        } else if (resultValues.getType() == Constants.TYPE_WHATSAPP) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_whatsapp)
            resultIcon!!.setImageResource(R.drawable.whatsapp_icon)
            resultTextIcon!!.setImageResource(R.drawable.whatsapp_icon)
            contentText!!.text = resources.getString(R.string.whatsapp)
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.GONE
            resultActionBtn1!!.visibility = View.GONE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "whatsapp")
        } else if (resultValues.getType() == Constants.TYPE_INSTAGRAM) {
            scannedCodeType!!.text = resources.getString(R.string.scanned_type_qrcode)
            tileOfResult!!.text = resources.getString(R.string.result_instagram)
            resultIcon!!.setImageResource(R.drawable.instagram_icon)
            resultTextIcon!!.setImageResource(R.drawable.instagram_icon)
            contentText!!.text = resources.getString(R.string.username_only)
            action_btn1 = Constants.SEARCH_IN_WEB
            resultL1!!.visibility = View.GONE
            resultL2!!.visibility = View.GONE
            resultL3!!.visibility = View.GONE
            contentText!!.visibility = View.VISIBLE
            resultActionBtn1!!.visibility = View.VISIBLE
            resultActionBtn1!!.setImageResource(R.drawable.url_scanner)
            resultActionBtn2!!.visibility = View.GONE
            resultActionBtn3!!.visibility = View.GONE
            resultActionBtn4!!.visibility = View.GONE
            resultActionBtn5!!.visibility = View.GONE
            result!!.text = finalResult
            wifiR1!!.visibility = View.GONE
            wifiR2!!.visibility = View.GONE
            editInfoWifi!!.visibility = View.GONE
            editInfo!!.visibility = View.GONE
            storeSearch!!.visibility = View.GONE
            qr_layout!!.visibility = View.VISIBLE
            barImage!!.visibility = View.GONE
            params.putString("scan", "instagram")
        }
        generateCode(lastResult) //for qr/barcode image
        ////        if(resultValues.getType() == Constants.)
//        result.setText(finalResult);
//        result.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private fun initListeners() {
//        removeAds!!.setOnClickListener {
//            startActivity(
//                Intent(
//                    this@ResultActivity,
//                    RemoveAdsActivity::class.java
//                )
//            )
//        }
        codeSaveBtn!!.setOnClickListener {
            shouldShare = false
            saveAndShare(shouldShare, "scanQrcode", output)
        }
        codeShareBtn!!.setOnClickListener {
            shouldShare = true
            saveAndShare(shouldShare, "scanQRCode", output)
        }
        resultCopyBtn!!.setOnClickListener { AppUtils.copyToClipboard(mContext, lastResult) }
        resultShareBtn!!.setOnClickListener { //                AppUtils.share(mActivity, result.getText().toString());
            var plainText = result!!.text.toString() + ""
            if (!result2!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result2!!.text}
                    """.trimIndent()
            }
            if (!result3!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result3!!.text}
                    """.trimIndent()
            }
            if (!result4!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result4!!.text}
                    """.trimIndent()
            }
            AppUtils.share(mActivity, plainText)
        }
        resultActionBtn1!!.setOnClickListener {
            //ResultOfTypeAndValue resultValues = AppUtils.getResourceType(lastResult);
            if (resultValues!!.type == Constants.TYPE_WIFI) {
//                    AppUtils.executeAction(mActivity, nameWifi.getText().toString()+securityWifi.getText().toString()+passwordWifi.getText().toString() , lastResult, resultValues.getType(), action_btn1);
//                    Toast.makeText(mActivity, "wifi go wifi", Toast.LENGTH_SHORT).show();
                startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
            } else {
                AppUtils.executeAction(
                    mActivity,
                    result!!.text.toString(),
                    lastResult,
                    resultValues!!.type,
                    action_btn1
                )
            }
        }
        resultActionBtn2!!.setOnClickListener { // ResultOfTypeAndValue resultValues = AppUtils.getResourceType(lastResult);
            AppUtils.executeAction(
                mActivity,
                result!!.text.toString(),
                lastResult,
                resultValues!!.type,
                action_btn2
            )
        }
        resultActionBtn3!!.setOnClickListener { // ResultOfTypeAndValue resultValues = AppUtils.getResourceType(lastResult);
            AppUtils.executeAction(
                mActivity,
                result!!.text.toString(),
                lastResult,
                resultValues!!.type,
                action_btn3
            )
        }
        resultActionBtn4!!.setOnClickListener { // ResultOfTypeAndValue resultValues = AppUtils.getResourceType(lastResult);
            AppUtils.executeAction(
                mActivity,
                result!!.text.toString(),
                lastResult,
                resultValues!!.type,
                action_btn4
            )
        }
        resultActionBtn5!!.setOnClickListener {
            if (someAction == 0) {
                try {
                    val lat = Constants.latitudeAddress.toFloat()
                    val longi = Constants.longitudeAddress.toFloat()
                    val uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, longi)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        mActivity,
                        "An unexpected error has been occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (someAction == 1) {
                try {
                    val intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.time)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd.time)
                        .putExtra(CalendarContract.Events.TITLE, Constants.titleEvent)
                        .putExtra(CalendarContract.Events.DESCRIPTION, Constants.eventDescription)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, Constants.eventLocation)
                        .putExtra(
                            CalendarContract.Events.AVAILABILITY,
                            CalendarContract.Events.AVAILABILITY_BUSY
                        )
                    startActivity(intent)
                } catch (ignored: ActivityNotFoundException) {
                }
            }
        }
        contentText!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        result!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        result2!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result2!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        result3!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result3!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        result4!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result4!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        copy1!!.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
        }
        copy2!!.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result2!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
        }
        copy3!!.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result3!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
        }
        copy4!!.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result4!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
        }
        decorate!!.setOnClickListener {
            //                if(CODE_TYPE==1){
//                    Constants.finalImageEditor=1;
//                }
//                else{
//                    Constants.finalImageEditor=0;
//                }


//                mAdManagerInterstitialAd = AdsManagerQ.getInstance().getAd();
//
//                if (mAdManagerInterstitialAd != null) {
//
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
////                        dialogadload.dismiss();
////                        MyUtil.dialogadload.dismiss();
//                            // TODO Auto-generated method stub
//
//                            mAdManagerInterstitialAd.show(Objects.requireNonNull(ResultActivity.this));
//                            mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                                @Override
//                                public void onAdDismissedFullScreenContent() {
//                                    // Called when fullscreen content is dismissed.
////                                SaveView();
////                                initInterstitialAd();
//                                    AdsManagerQ.getInstance().createInterstitialstaticAd(mContext);
//                                    Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                                    generateQR.putExtra("generateQR",lastResult);
//                                    generateQR.putExtra("codeType",CODE_TYPE);
//                                    startActivity(generateQR);
//
//                                }
//
//                                @Override
//                                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                                    // Called when fullscreen content failed to show.
////                                SaveView();
//                                }
//
//                                @Override
//                                public void onAdShowedFullScreenContent() {
//                                    // Called when fullscreen content is shown.
//                                    // Make sure to set your reference to null so you don't
//                                    // show it a second time.
////                                mInterstitialAd = null;
////                                initInterstitialAd();
//
//                                }
//
//                            });
//
////                            Constants.adLogicResult=1;
//
//
//
//                        }
//
//                    }, 1500);
//                    AppUtils.showAdDialog(mActivity,mContext);
//
//                }
//
//                else {
//                    Constants.adLogicResult=1;
//                    Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                    generateQR.putExtra("generateQR",lastResult);
//                    generateQR.putExtra("codeType",CODE_TYPE);
//                    startActivity(generateQR);
//                }

//                Constants.adLogicResult = 1;
            val generateQR = Intent(this@ResultActivity, QRCodeGeneratorScanner::class.java)
            generateQR.putExtra("generateQR", lastResult)
            generateQR.putExtra("codeType", CODE_TYPE)
            startActivity(generateQR)

//                if (Constants.adLogicResult == 1) {
////                                        new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
//////                            Log.d("checkIntersetitial1",startAppAd.isReady()+"");
////
////
////
////
////                        }
////                    },1500);
////                    AppUtils.showAdDialog(mActivity,mContext);
//                    SplashActivity.startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC,new AdEventListener() {
//                        @Override
//                        public void onReceiveAd(Ad ad) {
//                            Log.d("checkAds","ad recieved");
////                                    Toast.makeText(mActivity, "Interestitial Loaded", Toast.LENGTH_SHORT).show();
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if(SplashActivity.startAppAd.isReady()){
//                                        Log.d("checkAds1","ad ready");
//
//                                        SplashActivity.startAppAd.showAd(new AdDisplayListener() {
//
//
//
//                                            @Override
//                                            public void adHidden(Ad ad) {
//                                                Constants.adLogicResult=2;
//                                                Log.d("checkAds2","ad hidden");
//                                                Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                                                generateQR.putExtra("generateQR",lastResult);
//                                                generateQR.putExtra("codeType",CODE_TYPE);
//                                                startActivity(generateQR);
//                                            }
//
//                                            @Override
//                                            public void adDisplayed(Ad ad) {
//                                                Log.d("checkAds3","ad displayed");
//                                                Constants.adLogicResult=2;
//                                            }
//
//                                            @Override
//                                            public void adClicked(Ad ad) {
//
//                                            }
//
//                                            @Override
//                                            public void adNotDisplayed(Ad ad) {
//                                                Log.d("checkAds4","ad not displayed");
//                                                Constants.adLogicResult=2;
//                                                Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                                                generateQR.putExtra("generateQR",lastResult);
//                                                generateQR.putExtra("codeType",CODE_TYPE);
//                                                startActivity(generateQR);
//                                            }
//                                        });
//
//
////                                        Log.d("checkIntersetitial",startAppAd.isReady()+"");
//
//                                    }
//                                    else {
//                                        Log.d("checkAds5","ad not ready");
//                                        Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                                        generateQR.putExtra("generateQR",lastResult);
//                                        generateQR.putExtra("codeType",CODE_TYPE);
//                                        startActivity(generateQR);
//                                    }
//                                }
//                            },1500);
//                            AppUtils.showAdDialog(mActivity,mContext);
//
//
//                        }
//
//                        @Override
//                        public void onFailedToReceiveAd(Ad ad) {
//                            Log.d("checkAds6","ad failed");
//                            Constants.adLogicResult=2;
//                            Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                            generateQR.putExtra("generateQR",lastResult);
//                            generateQR.putExtra("codeType",CODE_TYPE);
//                            startActivity(generateQR);
////                                    Toast.makeText(mActivity, "Interestitial Not Loaded", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else if(Constants.adLogicResult==2){
//
//                    if (mAdManagerInterstitialAd != null) {
//
//                        new Handler().postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
////                        dialogadload.dismiss();
////                        MyUtil.dialogadload.dismiss();
//                                // TODO Auto-generated method stub
//
//                                mAdManagerInterstitialAd.show(Objects.requireNonNull(ResultActivity.this));
//                                mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                                    @Override
//                                    public void onAdDismissedFullScreenContent() {
//                                        // Called when fullscreen content is dismissed.
////                                SaveView();
////                                initInterstitialAd();
//                                        AdsManagerQ.getInstance().createInterstitialstaticAd(mContext);
//                                        Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                                        generateQR.putExtra("generateQR",lastResult);
//                                        generateQR.putExtra("codeType",CODE_TYPE);
//                                        startActivity(generateQR);
//
//                                    }
//
//                                    @Override
//                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
//                                        // Called when fullscreen content failed to show.
////                                SaveView();
//                                    }
//
//                                    @Override
//                                    public void onAdShowedFullScreenContent() {
//                                        // Called when fullscreen content is shown.
//                                        // Make sure to set your reference to null so you don't
//                                        // show it a second time.
////                                mInterstitialAd = null;
////                                initInterstitialAd();
//
//                                    }
//
//                                });
//
//                                Constants.adLogicResult=1;
//
//
//
//                            }
//
//                        }, 1500);
//                        AppUtils.showAdDialog(mActivity,mContext);
//
//                    }
//
//                    else {
//                        Constants.adLogicResult=1;
//                        Intent generateQR=new Intent(ResultActivity.this,QRCodeGeneratorScanner.class);
//                        generateQR.putExtra("generateQR",lastResult);
//                        generateQR.putExtra("codeType",CODE_TYPE);
//                        startActivity(generateQR);
//                    }
//
////                    if(mStartUpInterstitialAd!=null){
////                        Toast.makeText(mActivity, "Not Null", Toast.LENGTH_SHORT).show();
////
////                        startAppAd.showAd(new AdDisplayListener() {
////                            @Override
////                            public void adHidden(Ad ad) {
////
////                            }
////
////                            @Override
////                            public void adDisplayed(Ad ad) {
////                                    Constants.adLogicResult=1;
////                            }
////
////                            @Override
////                            public void adClicked(Ad ad) {
////
////                            }
////
////                            @Override
////                            public void adNotDisplayed(Ad ad) {
////
////                            }
////                        });
////                    }
//
////                    startAppAd.loadAd(StartAppAd.AdMode.FULLPAGE);
//
//
//                }
        }
//        qr_layout!!.setOnClickListener {
//            val intent = Intent(mContext, QRImageActivity::class.java)
//            startActivity(intent)
//            //                Toast.makeText(mActivity, "qr layout clicked", Toast.LENGTH_SHORT).show();
//        }
        nameWifi!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result4!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        passwordWifi!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result4!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        securityWifi!!.setOnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val getString = result4!!.text.toString()
            val clip = ClipData.newPlainText("", getString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(mActivity, "Copy to Clipboard", Toast.LENGTH_SHORT).show()
            true
        }
        editInfo!!.setOnClickListener {
            var plainText = result!!.text.toString() + ""
            if (!result2!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result2!!.text}
                    """.trimIndent()
            }
            if (!result3!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result3!!.text}
                    """.trimIndent()
            }
            if (!result4!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${result4!!.text}
                    """.trimIndent()
            }
        }
        editInfoWifi!!.setOnClickListener {
            var plainText = nameWifi!!.text.toString() + ""
            if (!securityWifi!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${securityWifi!!.text}
                    """.trimIndent()
            }
            if (!passwordWifi!!.text.toString().contains("Result")) {
                plainText = """
                    $plainText
                    ${passwordWifi!!.text}
                    """.trimIndent()
            }
        }

        /*buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.copyToClipboard(mContext, result.getText().toString());
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.searchInWeb(mActivity, result.getText().toString());
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.share(mActivity, result.getText().toString());
            }
        });

        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultOfTypeAndValue resultValues = AppUtils.getResourceType(lastResult);
                AppUtils.executeAction(mActivity, result.getText().toString(), resultValues.getType());
            }
        });*/backResult!!.setOnClickListener { finish() }
        infoResult!!.setOnClickListener { AppUtils.showPermissionDialog(mActivity, mContext) }
        settingResult!!.setOnClickListener { //                Intent i=new Intent(ResultActivity.this,PrivacyPolicy.class);
//                startActivity(i);
            try {
                val url =
                    resources.getString(R.string.pp_link)
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(url))
                startActivity(i)
            } catch (ignored: ActivityNotFoundException) {
            }
        }
        searchAmazon!!.setOnClickListener {
            try {
                val url = "https://www.amazon.com/s?k=$barCodeContent"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (ignored: Exception) {
            }
        }
        searchEbay!!.setOnClickListener {
            try {
                val url = "https://www.ebay.com/sch/i.html?_nkw=$barCodeContent"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (ignored: Exception) {
            }
        }
    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case android.R.id.home:
    //                finish();
    //                return true;
    //
    //            default:
    //                return super.onOptionsItemSelected(item);
    //        }
    //    }
    private fun generateCode(input: String?) {
        val codeGenerator = CodeGenerator()
        if (resultValues!!.type == Constants.TYPE_BARCODE) {
//            String dummyInput="1234567890128";
//            codeGenerator.generateBarFor(dummyInput);
//            Log.d("codeGenerateInfo",input);
            codeGenerator.generateBarFor(input)
        } else {
//            Toast.makeText(mActivity, "createQR", Toast.LENGTH_SHORT).show();
            codeGenerator.generateQRFor(input)
        }
        codeGenerator.setResultListener { bitmap ->
            //((BitmapDrawable)outputBitmap.getDrawable()).getBitmap().recycle();
            output = bitmap
            Constants.finalBitmap = bitmap
            if (resultValues!!.type == Constants.TYPE_BARCODE) {
                Glide.with(mContext!!)
                    .asBitmap()
                    .load(bitmap)
                    .into(barImage!!)

//                    barImage.setImageBitmap(bitmap);
                resultQrCodeImage!!.visibility = View.GONE
                barImage!!.visibility = View.VISIBLE
                Constants.finalImageEditor = 1
            } else {
                Glide.with(mContext!!)
                    .asBitmap()
                    .load(bitmap)
                    .into(resultQrCodeImage!!)
                //                    resultQrCodeImage.setImageBitmap(bitmap);
                resultQrCodeImage!!.visibility = View.VISIBLE
                barImage!!.visibility = View.GONE
            }
        }
        codeGenerator.execute()
    }


    private fun saveAndShare(shouldShare: Boolean, input: String?, bitmap: Bitmap?) {
        if (checkWritePermission(mActivity,mContext!!)) {
            if (shouldShare) {
                AppUtils.shareImage(mActivity, bitmap)
            } else {
                AppUtils.saveImage(mActivity, input, bitmap)
            }
        }
    }


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
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        saveAndShare(shouldShare, lastResult, output)
                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted))
                    }
                }
            }
        }
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
