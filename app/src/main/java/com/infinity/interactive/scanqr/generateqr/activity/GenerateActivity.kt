package com.infinity.interactive.scanqr.generateqr.activity

import android.Manifest
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.provider.Settings
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.jaredrummler.materialspinner.MaterialSpinner
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.databinding.ActivityGenerateBinding
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils
import java.io.IOException
import java.util.Calendar
import java.util.Locale

class GenerateActivity : AppCompatActivity(), NestedScrollView.OnScrollChangeListener,
    OnMapReadyCallback {

    //vars
    private var mActivity: Activity? = null
    private var mContext: Context? = null

    var input: String? = null
    var input2: String? = null
    var qr_category = 0
    var barCodeCategory = 0
    private var generate_action = 0
    var GpsStatus = false
    var getLocation = 0
    var startEvent = ""
    var endEvent = ""
    var eveStartYear = ""
    var eveStartMonth = ""
    var eveStartDay = ""
    var eveStartHour = ""
    var eveStartMin = ""
    var eveStartSec = "00"
    var eveEndYear = ""
    var eveEndMonth = ""
    var eveEndDay = ""
    var eveEndHour = ""
    var eveEndMin = ""
    var eveEndSec = "00"
    var lati = 0.0
    var longi = 0.0
    var countryCodeVal = "+1"
    var type = "WEP"
    var latitude: String? = null
    var longitude: String? = null

    //EditText
    private val inputText: EditText by lazy {
        findViewById(R.id.inputText)
    }
    private val inputText2: EditText by lazy {
        findViewById(R.id.inputText2)
    }
    private val inputText3: EditText by lazy {
        findViewById(R.id.inputText3)
    }
    private val inputText4: EditText by lazy {
        findViewById(R.id.inputText4)
    }
    private val inputText5: EditText by lazy {
        findViewById(R.id.inputText5)
    }
    private val inputText6: EditText by lazy {
        findViewById(R.id.inputText6)
    }
    private val inputText7: EditText by lazy {
        findViewById(R.id.inputText7)
    }
    private val inputWhatsapp: EditText by lazy {
        findViewById(R.id.inputWhatsapp)
    }
    private val countryCode: EditText by lazy {
        findViewById(R.id.country_code_edit_txt)
    }

    //Textview
    private val inputText8: TextView by lazy {
        findViewById(R.id.inputText8)
    }
    private val inputText9: TextView by lazy {
        findViewById(R.id.inputText9)
    }
    private val recipientText: TextView by lazy {
        findViewById(R.id.recipientText)
    }
    private val messageText: TextView by lazy {
        findViewById(R.id.messageText)
    }
    private val password: TextView by lazy {
        findViewById(R.id.passwordText)
    }
    private val company: TextView by lazy {
        findViewById(R.id.companyText)
    }
    private val jobTitle: TextView by lazy {
        findViewById(R.id.jobText)
    }
    private val address: TextView by lazy {
        findViewById(R.id.address)
    }
    private val generate_btn: TextView by lazy {
        findViewById(R.id.generate_btn)
    }
    private val appendUserName: TextView by lazy {
        findViewById(R.id.userNameText)
    }
    private val qr_cat_name: TextView by lazy {
        findViewById(R.id.QRCat)
    }
    private val startEventTime: TextView by lazy {
        findViewById(R.id.inputTextStartTime)
    }
    private val endEventTime: TextView by lazy {
        findViewById(R.id.inputTextEndTime)
    }

    //Image
    private val choose_btn: ImageButton by lazy {
        findViewById(R.id.choose_btn)
    }
    private val contactChooseFromContact: ImageButton by lazy {
        findViewById(R.id.contact_phone_btn)
    }
    private val email_choose_btn: ImageButton by lazy {
        findViewById(R.id.email_choose_btn)
    }
    private val backFromQrCat: ImageView by lazy {
        findViewById(R.id.backButtonQRCat)
    }
    private val inforQRCat: ImageView by lazy {
        findViewById(R.id.infoQRCat)
    }
    private val privacyPolicyQR: ImageView by lazy {
        findViewById(R.id.privacy_policy_qr)
    }
    private val refresh: ImageView by lazy {
        findViewById(R.id.refresh)
    }

    //Button
    private val switchButton: SwitchCompat by lazy {
        findViewById(R.id.switch_button)
    }
    private val w1: Button by lazy {
        findViewById(R.id.wpawpa2)
    }
    private val w2: Button by lazy {
        findViewById(R.id.wep)
    }
    private val w3: Button by lazy {
        findViewById(R.id.none)
    }

    //Views
    private val materialSpinner: MaterialSpinner by lazy {
        findViewById(R.id.material_spinner)
    }
    private val linearLayoutWifi: LinearLayout by lazy {
        findViewById(R.id.l1)
    }
    private val hidden_network: RelativeLayout by lazy {
        findViewById(R.id.hide)
    }
    private val memoRelative: RelativeLayout by lazy {
        findViewById(R.id.memoRel)
    }
    private val eventRel: RelativeLayout by lazy {
        findViewById(R.id.eventRel)
    }
    private val eventBTime: RelativeLayout by lazy {
        findViewById(R.id.beginTime)
    }
    private val eventETime: RelativeLayout by lazy {
        findViewById(R.id.endTime)
    }
    private val v1: View by lazy {
        findViewById(R.id.horizontalLine1)
    }
    private val v2: View by lazy {
        findViewById(R.id.horizontalLine2)
    }
    private val v3: View by lazy {
        findViewById(R.id.horizontalLine3)
    }
    private val v4: View by lazy {
        findViewById(R.id.horizontalLine4)
    }
    private val v5: View by lazy {
        findViewById(R.id.horizontalLine5)
    }
    private val v6: View by lazy {
        findViewById(R.id.horizontalLine6)
    }
    private val cardViewMap: CardView by lazy {
        findViewById(R.id.cardFrag)
    }

    //google maps
    private var googleApiClient: GoogleApiClient? = null
    var mapFragment: SupportMapFragment? = null
    var map: GoogleMap? = null
    var locationManager: LocationManager? = null

    //binding
    private lateinit var generateActivityBinding: ActivityGenerateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateActivityBinding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(generateActivityBinding.root)
        initVar()
        initFunctionality()
        initListener()
        val currentFocus = mActivity!!.currentFocus
        currentFocus?.clearFocus()
    }

    private fun initVar() {
        mActivity = this
        mContext = (mActivity as GenerateActivity).applicationContext
        mapFragment =
            supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
    }

    private fun initListener() {

        val params = Bundle()
        val paramsCreateDoing = Bundle()
        val firstRunGenerate =
            AppPreference.getInstance(this).getBoolean(PrefKey.GenerateActivityFirstRun, true)
        if (firstRunGenerate) {
            val paramsCycle = Bundle()
            paramsCycle.putString("QRStarted", "1")
            AppPreference.getInstance(this).setBoolean(PrefKey.GenerateActivityFirstRun, false)
        }
        generate_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                try {
                    val imm =
                        mActivity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    var view1 = mActivity!!.currentFocus
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view1 == null) {
                        view1 = View(mActivity)
                    }
                    imm.hideSoftInputFromWindow(view1.windowToken, 0)
                } catch (ignored: Exception) {
                }
                if (generate_action == 1) {
                    when (CURRENT_BTN) {
                        TEXT -> {
                            input = inputText!!.text.toString()
                            getCode = 0
                            params.putString("create", "text")
                            paramsCreateDoing.putString("QRTYPEDOING", "TEXT")
                        }

                        CONTACT -> {
                            input = """
                                BEGIN:VCARD
                                VERSION:3.0
                                N:${inputText!!.text}
                                TEL:${inputText2!!.text}
                                EMAIL:${inputText3!!.text}
                                ADR:${inputText6!!.text}
                                END:VCARD
                                """.trimIndent()
                            getCode = 0
                            params.putString("create", "contact")
                            paramsCreateDoing.putString("QRTYPEDOING", "CONTACT")
                        }

                        URL -> {
                            input = inputText!!.text.toString()
                            input = input!!.replace(" ", "")
                            getCode = 0
                            params.putString("create", "url")
                            paramsCreateDoing.putString("QRTYPEDOING", "URL")
                        }

                        EMAIL -> {
                            input = "MATMSG:TO:" + inputText!!.text.toString() +
                                    ";SUB:" + inputText2!!.text.toString() +
                                    ";BODY:" + inputText3!!.text.toString() + ";;"
                            getCode = 0
                            params.putString("create", "email")
                            paramsCreateDoing.putString("QRTYPEDOING", "EMAIL")
                        }

                        WIFI -> {
                            input = if (type == "") {
                                "WIFI:S:" + inputText!!.text.toString() +
                                        ";P:" + inputText2!!.text.toString() + ";H:" + switchButton!!.isChecked + ";"
                            } else {
                                "WIFI:S:" + inputText!!.text.toString() +
                                        ";T:" + type +
                                        ";P:" + inputText2!!.text.toString() + ";H:" + switchButton!!.isChecked + ";"
                            }
                            getCode = 0
                            params.putString("create", "wifi")
                            paramsCreateDoing.putString("QRTYPEDOING", "WIFI")
                        }

                        BARCODE -> {
                            input = inputText!!.text.toString()
                            input = input!!.replace(" ", "")
                            if (barCodeCategory == 11 || barCodeCategory == 12) {
                                input2 = inputText2!!.text.toString()
                                input2 = input2!!.replace(" ", input2!!)
                            }
                            getCode = 1
                            params.putString("create", "barcode")
                            paramsCreateDoing.putString("QRTYPEDOING", "BARCODE")
                        }

                        SMS -> {
                            input = "SMSTO:" + inputText!!.text.toString() +
                                    ":" + inputText2!!.text.toString()
                            getCode = 0
                            params.putString("create", "sms")
                            paramsCreateDoing.putString("QRTYPEDOING", "SMS")
                        }

                        LOCATION -> {
                            val add = inputText3!!.text.toString()
                            if (!inputText!!.text.toString()
                                    .isEmpty() && !inputText2!!.text.toString().isEmpty()
                            ) {
                                input = if (TextUtils.isEmpty(add)) {
                                    "GEO: " + inputText!!.text.toString() +
                                            "," + inputText2!!.text.toString() + "?q=" + ""
                                } else {
                                    "GEO: " + inputText!!.text.toString() +
                                            "," + inputText2!!.text.toString() + "?q=" + inputText3!!.text.toString()
                                }
                            }
                            getCode = 0
                            getLocation = 1
                            params.putString("create", "location")
                            paramsCreateDoing.putString("QRTYPEDOING", "LOCATION")
                        }

                        PHONE -> {
                            input = inputText!!.text.toString()
                            input = "tel: " + inputText!!.text.toString()
                            getCode = 0
                            params.putString("create", "phone")
                            paramsCreateDoing.putString("QRTYPEDOING", "PHONE")
                        }

                        EVENT -> {
                            startEvent =
                                eveStartYear + eveStartMonth + eveStartDay + "T" + eveStartHour + eveStartMin + eveStartSec
                            endEvent =
                                eveEndYear + eveEndMonth + eveEndDay + "T" + eveEndHour + eveEndMin + eveEndSec
                            input = """
                                BEGIN:VEVENT
                                SUMMARY:${inputText!!.text}
                                LOCATION:${inputText2!!.text}
                                DESCRIPTION:${inputText3!!.text}
                                DTSTART:$startEvent
                                DTEND:$endEvent
                                END:VEVENT
                                """.trimIndent()
                            getCode = 0
                            getLocation = 2
                            params.putString("create", "event")
                            paramsCreateDoing.putString("QRTYPEDOING", "EVENT")
                        }

                        FACEBOOK -> {
                            getCode = 0
                            input = inputText!!.text.toString()
                            //                            input="facebook://user?username="+input;
                            params.putString("create", "facebook")
                            paramsCreateDoing.putString("QRTYPEDOING", "FACEBOOK")
                        }

                        TWITTER -> {
                            getCode = 0
                            input = inputText!!.text.toString()
                            params.putString("create", "twitter")
                            paramsCreateDoing.putString("QRTYPEDOING", "TWITTER")
                        }

                        LINKDEIN -> {
                            getCode = 0
                            input = inputText!!.text.toString()
                            params.putString("create", "linkdein")
                            paramsCreateDoing.putString("QRTYPEDOING", "LINKDEIN")
                        }

                        INSTAGRAM -> {
                            getCode = 0
                            input = inputText!!.text.toString()
                            params.putString("create", "instagram")
                            paramsCreateDoing.putString("QRTYPEDOING", "INSTAGRAM")
                        }

                        WHATSAPP -> {
                            getCode = 0
                            input = inputWhatsapp!!.text.toString()
                            input = "whatsapp://send?phone=$countryCodeVal$input"
                            params.putString("create", "whatsapp")
                            paramsCreateDoing.putString("QRTYPEDOING", "WHATSAPP")
                        }
                    }
                    val digits = inputText!!.text.toString()
                    var count = 0
                    run {
                        var i = 0
                        val len = digits.length
                        while (i < len) {
                            if (Character.isDigit(digits[i])) {
                                count++
                            }
                            i++
                        }
                    }
                    val digits2 = inputText2!!.text.toString()
                    var count2 = 0
                    var i = 0
                    val len = digits2.length
                    while (i < len) {
                        if (Character.isDigit(digits2[i])) {
                            count2++
                        }
                        i++
                    }
                    if (CURRENT_BTN == BARCODE) {
                        if (barCodeCategory == 6) {
                            if (count % 2 == 0) {
                                val generateQR = Intent(
                                    this@GenerateActivity,
                                    QRCodeGeneratorScanner::class.java
                                )
                                generateQR.putExtra("generateQR", input)
                                generateQR.putExtra("codeType", getCode)
                                generateQR.putExtra("location", getLocation)
                                generateQR.putExtra("activity", 1)
                                startActivity(generateQR)
                            } else {
                                Snackbar.make(
                                    view,
                                    "Enter Even Number of digits ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else if (barCodeCategory == 7) {
                            if (count == 8) {
                                val substring = input!!.substring(input!!.length - 1)
                                val lastDigit = substring.toInt()
                                if (lastDigit == checkSum(input)) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else {
                                    Snackbar.make(
                                        view,
                                        "Enter Correct Checksum Digit ",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else if (count == 7) {
                                val generateQR = Intent(
                                    this@GenerateActivity,
                                    QRCodeGeneratorScanner::class.java
                                )
                                generateQR.putExtra("generateQR", input)
                                generateQR.putExtra("codeType", getCode)
                                generateQR.putExtra("location", getLocation)
                                generateQR.putExtra("activity", 1)
                                startActivity(generateQR)
                            } else if (count < 7) {
                                Snackbar.make(
                                    view,
                                    "Enter 8 digit or 7 digit EAN-8 Number ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else if (barCodeCategory == 8) {
                            if (count == 13) {
                                val substring = input!!.substring(input!!.length - 1)
                                val lastDigit = substring.toInt()
                                if (lastDigit == calculateChecksumDigit13(input)) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else {
                                    Snackbar.make(
                                        view,
                                        "Enter Correct Checksum Digit ",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else if (count == 12) {
                                val generateQR = Intent(
                                    this@GenerateActivity,
                                    QRCodeGeneratorScanner::class.java
                                )
                                generateQR.putExtra("generateQR", input)
                                generateQR.putExtra("codeType", getCode)
                                generateQR.putExtra("location", getLocation)
                                generateQR.putExtra("activity", 1)
                                startActivity(generateQR)
                            } else if (count < 12) {
                                Snackbar.make(
                                    view,
                                    "Enter 12 digit or 13 digit EAN-13 Number ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else if (barCodeCategory == 9) {
                            if (count == 12) {
                                val substring = input!!.substring(input!!.length - 1)
                                val lastDigit = substring.toInt()
                                if (lastDigit == calculateChecksumDigitUPCA(input)) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else {
                                    Snackbar.make(
                                        view,
                                        "Enter Correct UPC Checksum Digit ",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else if (count == 11) {
                                val generateQR = Intent(
                                    this@GenerateActivity,
                                    QRCodeGeneratorScanner::class.java
                                )
                                generateQR.putExtra("generateQR", input)
                                generateQR.putExtra("codeType", getCode)
                                generateQR.putExtra("location", getLocation)
                                generateQR.putExtra("activity", 1)
                                startActivity(generateQR)
                            } else if (count < 11) {
                                Snackbar.make(
                                    view,
                                    "Enter Correct UPC-A Number ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else if (barCodeCategory == 10) {
                            if (count == 8) {
                                val firstLetter = input!!.substring(0, 1)
                                val firstDigit = firstLetter.toInt()
                                if (firstDigit == 0 || firstDigit == 1) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else {
                                    Snackbar.make(
                                        view,
                                        "Number System must be 0 or 1",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else if (count == 7) {
                                val firstLetter = input!!.substring(0, 1)
                                val firstDigit = firstLetter.toInt()
                                if (firstDigit == 0 || firstDigit == 1) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else {
                                    Snackbar.make(
                                        view,
                                        "Number System must be 0 or 1",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else if (count < 7) {
                                Snackbar.make(
                                    view,
                                    "Enter Correct UPC-E Number ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else if (barCodeCategory == 11) {
                            if (!inputText!!.text.toString()
                                    .isEmpty() && !inputText2!!.text.toString()
                                    .isEmpty() && !inputText3!!.text.toString().isEmpty()
                            ) {
                                if (count2 == 12) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input2)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else if (count < 12) {
                                    Snackbar.make(
                                        view,
                                        "Enter 12 digit Product Number ",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(mActivity, "Empty Input", Toast.LENGTH_SHORT).show()
                            }
                        } else if (barCodeCategory == 12) {
                            if (!inputText!!.text.toString()
                                    .isEmpty() && !inputText2!!.text.toString()
                                    .isEmpty() && !inputText3!!.text.toString().isEmpty()
                            ) {
                                if (count2 == 12) {
                                    val generateQR = Intent(
                                        this@GenerateActivity,
                                        QRCodeGeneratorScanner::class.java
                                    )
                                    generateQR.putExtra("generateQR", input2)
                                    generateQR.putExtra("codeType", getCode)
                                    generateQR.putExtra("location", getLocation)
                                    generateQR.putExtra("activity", 1)
                                    startActivity(generateQR)
                                } else if (count < 12) {
                                    Snackbar.make(
                                        view,
                                        "Enter 12 digit Book Code ",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(mActivity, "Empty Input", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val generateQR =
                                Intent(this@GenerateActivity, QRCodeGeneratorScanner::class.java)
                            generateQR.putExtra("generateQR", input)
                            generateQR.putExtra("codeType", getCode)
                            generateQR.putExtra("location", getLocation)
                            generateQR.putExtra("activity", 1)
                            startActivity(generateQR)
                        }
                    } else {
                        try {
                            val charCount = input!!.length
                            if (charCount < 300) {
                                val generateQR = Intent(
                                    this@GenerateActivity,
                                    QRCodeGeneratorScanner::class.java
                                )
                                generateQR.putExtra("generateQR", input)
                                generateQR.putExtra("codeType", getCode)
                                generateQR.putExtra("location", getLocation)
                                generateQR.putExtra("activity", 1)
                                startActivity(generateQR)
                            } else {
//                                Snackbar.make(view, "Characters can't be more than 300", Snackbar.LENGTH_LONG).show();
                                Toast.makeText(
                                    mActivity,
                                    "Characters can't be more than 300",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (ignored: Exception) {
                        }
                    }
                } else if (generate_action == 0) {
                    Toast.makeText(mActivity, "Empty Input", Toast.LENGTH_SHORT).show()
                }
            }
        })
        w1!!.setOnClickListener {
            w1!!.background = resources.getDrawable(R.drawable.round_buttons)
            w1!!.setTextColor(getColor(R.color.white))
            w2!!.setTextColor(getColor(R.color.tt_black))
            w3!!.setTextColor(getColor(R.color.tt_black))
            w2!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            w3!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            type = "WPA"
        }
        w2!!.setOnClickListener {
            w2!!.background = resources.getDrawable(R.drawable.round_buttons)
            w1!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            w2!!.setTextColor(getColor(R.color.white))
            w1!!.setTextColor(getColor(R.color.tt_black))
            w3!!.setTextColor(getColor(R.color.tt_black))
            w3!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            type = "WEP"
        }
        w3!!.setOnClickListener {
            w3!!.background = resources.getDrawable(R.drawable.round_buttons)
            w2!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            w1!!.background = resources.getDrawable(R.drawable.rounded_buttons_bg)
            w3!!.setTextColor(getColor(R.color.white))
            w2!!.setTextColor(getColor(R.color.tt_black))
            w1!!.setTextColor(getColor(R.color.tt_black))
            type = ""
        }
        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                generate_action = if (s.length != 0) {
                    generate_btn.setBackgroundResource(R.drawable.round_buttons)
                    1
                } else {
                    generate_btn.setBackgroundResource(R.drawable.round_buttons)
                    0
                }
            }
        })
        inputWhatsapp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                generate_action = if (s.length != 0) {
                    generate_btn.setBackgroundResource(R.drawable.round_buttons)
                    1
                } else {
                    generate_btn.setBackgroundResource(R.drawable.round_buttons)
                    0
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        countryCode!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0) {
                    countryCodeVal = s.toString()
                } else {
                    countryCodeVal = "+1"
                    Toast.makeText(mContext, "Country Code can't be empty", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        choose_btn!!.setOnClickListener {
            if (CURRENT_BTN == PHONE || CURRENT_BTN == SMS || CURRENT_BTN == CONTACT) {
                if (ContextCompat.checkSelfPermission(
                        mActivity!!,
                        Manifest.permission.READ_CONTACTS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    checkReadContactsPermission()
                } else {
                    try {
                        val intent =
                            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                        startActivityForResult(intent, PICK_CONTACT)
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "Contact not found", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (CURRENT_BTN == EMAIL) {
                val accounts =
                    AccountManager.get(this@GenerateActivity).getAccountsByType("com.google")
                val accountName = arrayOf("")
                val selected = arrayOf<String?>("")
                val gUsernameList = ArrayList<String>()
                for (account in accounts) {
                    gUsernameList.add(account.name)
                }
                gUsernameList.add("Add Account")
                accountName[0] = gUsernameList[0]
                val builder = AlertDialog.Builder(this@GenerateActivity)
                builder.setTitle("Choose an Account")
                val selectedoption = arrayOfNulls<String>(gUsernameList.size)
                for (j in gUsernameList.indices) {
                    selectedoption[j] = gUsernameList[j]
                }
                val checkedItem = 0 // cow
                builder.setSingleChoiceItems(selectedoption, checkedItem) { dialog, which ->
                    // user checked an item
                    accountName[0] = gUsernameList[which]
                    selected[0] = selectedoption[which]
                    //here 'which' is the position selected
                }
                builder.setPositiveButton("OK") { dialog, which -> // user clicked OK
                    if (selected[0] == "Add Account") {
                        val addAccountIntent = Intent(Settings.ACTION_ADD_ACCOUNT)
                        addAccountIntent.putExtra(
                            Settings.EXTRA_ACCOUNT_TYPES,
                            arrayOf("com.google")
                        )
                        startActivity(addAccountIntent)
                    } else {
                        inputText.setText(accountName[0])
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    //                        builder.setCancelable(true);
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
        contactChooseFromContact!!.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    mActivity!!,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                checkReadContactsPermission()
            } else {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, PICK_CONTACT)
            }
        }
        email_choose_btn!!.setOnClickListener {
            val accounts = AccountManager.get(this@GenerateActivity).getAccountsByType("com.google")
            val accountName = arrayOf("")
            val selected = arrayOf<String?>("")
            val gUsernameList = ArrayList<String>()
            for (account in accounts) {
                gUsernameList.add(account.name)
            }
            gUsernameList.add("Add Account")
            accountName[0] = gUsernameList[0]
            val builder = AlertDialog.Builder(this@GenerateActivity)
            builder.setTitle("Choose an Account")
            val selectedoption = arrayOfNulls<String>(gUsernameList.size)
            for (j in gUsernameList.indices) {
                selectedoption[j] = gUsernameList[j]
            }
            val checkedItem = 0 // cow
            builder.setSingleChoiceItems(selectedoption, checkedItem) { dialog, which ->
                // user checked an item
                accountName[0] = gUsernameList[which]
                selected[0] = selectedoption[which]
                //here 'which' is the position selected
            }
            builder.setPositiveButton("OK") { dialog, which -> // user clicked OK
                if (selected[0] == "Add Account") {
                    val addAccountIntent = Intent(Settings.ACTION_ADD_ACCOUNT)
                    addAccountIntent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                    startActivity(addAccountIntent)
                } else {
                    inputText3!!.setText(accountName[0])
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> }
            val dialog = builder.create()
            dialog.show()
        }
        inputText8!!.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@GenerateActivity,
                { view, year, month, dayOfMonth ->
                    eveStartYear = year.toString()
                    eveStartMonth = (month + 1).toString()
                    eveStartDay = dayOfMonth.toString()
                    inputText8.text = "$eveStartDay/$eveStartMonth/$eveStartYear"
                },
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }
        inputText9.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@GenerateActivity,
                { view, year, month, dayOfMonth ->
                    eveEndYear = year.toString()
                    eveEndMonth = (month + 1).toString()
                    eveEndDay = (dayOfMonth + 1).toString()
                    inputText9.text = "$eveEndDay/$eveEndMonth/$eveEndYear"
                },
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }
        startEventTime!!.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c[Calendar.HOUR_OF_DAY]
            val minute = c[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(
                this@GenerateActivity,
                { view, hourOfDay, minute ->
                    eveStartHour = hourOfDay.toString()
                    eveStartMin = minute.toString()
                    startEventTime!!.text = "$eveStartHour:$eveStartMin"
                },
                hour,
                minute,
                DateFormat.is24HourFormat(this@GenerateActivity)
            )
            timePickerDialog.show()
        }
        endEventTime!!.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c[Calendar.HOUR_OF_DAY]
            val minute = c[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(
                this@GenerateActivity,
                { view, hourOfDay, minute ->
                    eveEndHour = hourOfDay.toString()
                    eveEndMin = minute.toString()
                    endEventTime!!.text = "$eveEndHour:$eveEndMin"
                },
                hour,
                minute,
                DateFormat.is24HourFormat(this@GenerateActivity)
            )
            timePickerDialog.show()
        }
        inforQRCat!!.setOnClickListener { AppUtils.showPermissionDialog(mActivity, mContext) }
        privacyPolicyQR!!.setOnClickListener { //                Intent i=new Intent(GenerateActivity.this,PrivacyPolicy.class);
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
        materialSpinner!!.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { view: MaterialSpinner?, position: Int, id: Long, item: String? ->
            inputText!!.setText(
                item
            )
        })
        backFromQrCat!!.setOnClickListener {
            try {
                val imm = mActivity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                var view1 = mActivity!!.currentFocus
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view1 == null) {
                    view1 = View(mActivity)
                }
                imm.hideSoftInputFromWindow(view1!!.windowToken, 0)
            } catch (ignored: Exception) {
            }
            finish()
        }

//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckGpsStatus();
//                if (GpsStatus) {
//                    LocationRequest locationRequest = new LocationRequest();
//                    locationRequest.setInterval(10000);
//                    locationRequest.setFastestInterval(3000);
//                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    LocationServices.getFusedLocationProviderClient(GenerateActivity.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//
//                                @Override
//                                public void onLocationResult(LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//                                    LocationServices.getFusedLocationProviderClient(getApplicationContext())
//                                            .removeLocationUpdates(this);
//                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
//                                        int latestlocIndex = locationResult.getLocations().size() - 1;
//                                        double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
//                                        double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
//                                        inputText.setText(lati + "");
//                                        inputText2.setText(longi + "");
//
////                                    Toast.makeText(mActivity, lati + longi + "", Toast.LENGTH_SHORT).show();
//                                        Location location = new Location("providerNA");
//                                        location.setLongitude(longi);
//                                        location.setLatitude(lati);
//                                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here...!!");
////                                    animateMarker(markerOptions.getIcon(),latLng,false);
//                                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
//                                        if (ActivityCompat.checkSelfPermission(GenerateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GenerateActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                            // TODO: Consider calling
//                                            //    ActivityCompat#requestPermissions
//                                            // here to request the missing permissions, and then overriding
//                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                            //                                          int[] grantResults)
//                                            // to handle the case where the user grants the permission. See the documentation
//                                            // for ActivityCompat#requestPermissions for more details.
//                                            return;
//                                        }
//
//                                        if (map != null) {
//                                            map.setMyLocationEnabled(true);
//                                            map.addMarker(markerOptions);
//                                            map.animateCamera(cameraUpdate);
//                                        }
//
//                                        if (isNetworkConnected()) {
//
//                                            try {
//                                                String address = getAddress(mContext, location.getLatitude(), location.getLongitude());
////                inputLayout3.setVisibility(View.VISIBLE);
//                                                inputText3.setVisibility(View.VISIBLE);
//                                                if (!address.isEmpty()) {
//                                                    inputText3.setText(address);
//                                                }
//
//                                                password.setVisibility(View.VISIBLE);
//                                                password.setText("Address");
//                                                v3.setVisibility(View.VISIBLE);
//                                            } catch (Exception ignored) {
//
//                                            }
//
//
//                                        } else {
////                inputLayout3.setVisibility(View.GONE);
//                                            Toast.makeText(mActivity, "Network Error", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                }
//                            }, Looper.getMainLooper());
//
//
//                }
//            }
//        });
    }

    override fun onBackPressed() {
//        super.onBackPressed();
        finish()
    }

    private fun calculateChecksumDigitUPCA(code: String?): Int {
        val substring = input!!.substring(input!!.length - 1)
        val lastDigit = substring.toInt()
        var odd_sum = 0
        var even_sum = 0
        var total_sum = 0
        for (i in 0 until code!!.length) {
            if (i % 2 != 0) {
                even_sum = even_sum + ("" + code[i]).toInt()
            } else {
                val i1 = ("" + code[i]).toInt()
                odd_sum = odd_sum + ("" + code[i]).toInt() * 3
            }
        }
        total_sum = even_sum + odd_sum
        return if (total_sum % 10 == 0) {
            lastDigit
        } else total_sum
    }

    private fun getCheckSum(str: String): Int {
        val code = CharArray(str.length)

        // Copy character by character into array
        for (i in 0 until str.length) {
            code[i] = str[i]
        }
        var odd = 0
        var even = 0
        for (i in 0 until str.length - 1) {
            val index = i + 1
            if (index % 2 != 0) odd += code[i].code else even += code[i].code
        }
        return (10 - (odd + even * 3) % 10) % 10
    }

    private fun calculateChecksumDigit13(mMembershipId: String?): Int {
        val substring = input!!.substring(input!!.length - 1)
        val lastDigit = substring.toInt()
        var odd_sum = 0
        var even_sum = 0
        var total_sum = 0
        for (i in 0 until mMembershipId!!.length) {
            val i1 = ("" + mMembershipId[i]).toInt()
            if (i % 2 != 0) {
                odd_sum = odd_sum + i1 * 3
            } else {
                even_sum = even_sum + i1
            }
        }
        even_sum = even_sum - lastDigit
        total_sum = even_sum + odd_sum
        return if (total_sum % 10 != 0) {
            10 - total_sum % 10
        } else total_sum
    }

    fun checkSum(str: String?): Int {
        val code = CharArray(str!!.length)

        // Copy character by character into array
        for (i in 0 until str.length) {
            code[i] = str[i]
        }
        val sum1 = code[1].code + code[3].code + code[5].code
        val sum2 = 3 * (code[0].code + code[2].code + code[4].code + code[6].code)
        val checksum_value = sum1 + sum2
        var checksum_digit = 10 - checksum_value % 10
        if (checksum_digit == 10) checksum_digit = 0
        return checksum_digit
    }

    //for select of contact
    public override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        when (reqCode) {
            PICK_CONTACT -> if (resultCode == RESULT_OK) {
                try {
                    val contactData = data!!.data
                    val c = mActivity!!.contentResolver.query(contactData!!, null, null, null, null)
                    if (c!!.moveToFirst()) {
                        val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                        @SuppressLint("Range") val hasPhone =
                            c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if (hasPhone.equals("1", ignoreCase = true)) {
                            val phones = mActivity!!.contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                null, null
                            )
                            phones!!.moveToFirst()
                            @SuppressLint("Range") val cNumber = phones.getString(
                                phones.getColumnIndex("data1")
                            )
                            //AppUtils.showToast(mContext, cNumber);

//                                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            if (qr_category == 7 || qr_category == 5) {
                                inputText!!.setText(cNumber)
                            } else if (qr_category == 1) {
                                inputText2!!.setText(cNumber)
                            }

//                            if (CURRENT_BTN == PHONE) {
//                                inputText.setText(cNumber);
//                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(mActivity, e.toString() + "", Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_LOCATION -> if (RESULT_OK == resultCode) {
                CheckGpsStatus()
                if (GpsStatus) {
                    val locationRequest = LocationRequest()
                    locationRequest.setInterval(10000)
                    locationRequest.setFastestInterval(3000)
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    LocationServices.getFusedLocationProviderClient(this@GenerateActivity)
                        .requestLocationUpdates(locationRequest, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                LocationServices.getFusedLocationProviderClient(applicationContext)
                                    .removeLocationUpdates(this)
                                if (locationResult != null && locationResult.locations.size > 0) {
                                    val latestlocIndex = locationResult.locations.size - 1
                                    lati = locationResult.locations[latestlocIndex].latitude
                                    longi = locationResult.locations[latestlocIndex].longitude
                                    latitude = lati.toString()
                                    longitude = longi.toString()
                                    inputText!!.setText(longitude)
                                    inputText2!!.setText(latitude)
                                    LocationOnMap()
                                    if (isNetworkConnected) {
                                        try {
                                            val location = Location("providerNA")
                                            location.longitude = longi
                                            location.latitude = lati
                                            val address = getAddress(
                                                mContext,
                                                location.latitude,
                                                location.longitude
                                            )
                                            //                                                inputLayout3.setVisibility(View.VISIBLE);
                                            inputText3!!.setText(address)
                                        } catch (ignored: Exception) {
                                        }
                                    } else {
//                                                inputLayout3.setVisibility(View.GONE);
                                        Toast.makeText(
                                            mActivity,
                                            "Network Error",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }, Looper.getMainLooper())

                }
            }
        }
    }

    private fun checkReadContactsPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                mActivity!!,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mActivity!!, arrayOf(Manifest.permission.READ_CONTACTS),
                Constants.PERMISSION_REQ
            )
        } else {
            return true
        }
        return false
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
                if (permission == Manifest.permission.READ_CONTACTS) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        try {
                            val intent =
                                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                            startActivityForResult(intent, PICK_CONTACT)
                        } catch (e: Exception) {
                            Toast.makeText(mActivity, "Contact not found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted))
                    }
                } else if (permission == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this@GenerateActivity,
                            "permission granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted))
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun initFunctionality() {
        try {
            materialSpinner!!.setBackgroundColor(resources.getColor(R.color.darkMainColor))
            materialSpinner!!.setTextColor(resources.getColor(R.color.white))
        } catch (ignored: Exception) {
        }
        val mIntent = intent
        val mSocialIntent = intent
        val barCodeIntent = intent
        qr_category = mIntent.getIntExtra("qr_code_cat", 0)
        //        qr_category_social = mSocialIntent.getIntExtra("qr_code_cat_social", 0);
        barCodeCategory = barCodeIntent.getIntExtra("bar_code_cat", 0)
        if (qr_category == 0) {
            CURRENT_BTN = TEXT
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.setHint(R.string.type_here_text)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(8)
            appendUserName!!.visibility = View.GONE
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE

//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.txt_generate)
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 1) {
            CURRENT_BTN = CONTACT
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            appendUserName!!.visibility = View.GONE
            choose_btn!!.visibility = View.GONE
            inputText!!.hint = ""
            inputText!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText2!!.hint = ""
            inputText2!!.inputType = InputType.TYPE_CLASS_PHONE
            inputText3!!.hint = ""
            inputText3!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText4!!.hint = ""
            inputText4!!.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            recipientText!!.text = mActivity!!.resources.getString(R.string.name)
            recipientText!!.visibility = View.VISIBLE
            messageText!!.text = mActivity!!.resources.getString(R.string.phone_number)
            password!!.text = mActivity!!.resources.getString(R.string.email)
            messageText!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.GONE
            inputText5!!.visibility = View.VISIBLE
            inputText5!!.hint = ""
            inputText6!!.hint = ""
            inputText6!!.visibility = View.VISIBLE
            inputText7!!.visibility = View.VISIBLE
            inputText7!!.setLines(4)
            memoRelative!!.visibility = View.VISIBLE
            company!!.visibility = View.VISIBLE
            jobTitle!!.visibility = View.VISIBLE
            address!!.visibility = View.VISIBLE
            contactChooseFromContact!!.visibility = View.VISIBLE
            v4!!.visibility = View.VISIBLE
            v5!!.visibility = View.VISIBLE
            v6!!.visibility = View.VISIBLE
            email_choose_btn!!.visibility = View.VISIBLE
            eventRel!!.visibility = View.GONE
            input = """
                BEGIN:VCARD
                VERSION:3.0
                N:${inputText!!.text}
                TEL:${inputText2!!.text}
                EMAIL:${inputText3!!.text}
                ADR:${inputText4!!.text}
                END:VCARD
                """.trimIndent()

//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.contact_generate)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 2) {
            CURRENT_BTN = URL
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            choose_btn!!.visibility = View.GONE
            inputText!!.hint = "Enter URL here"
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(6)
            appendUserName!!.visibility = View.GONE
            inputText!!.setText("https://")
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE

//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.txt_website)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 3) {
            CURRENT_BTN = WIFI
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.network)
            messageText!!.text = mActivity!!.resources.getString(R.string.security)
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.GONE
            appendUserName!!.visibility = View.GONE
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.type_here_wifi_ssid)
            inputText!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText3!!.setHint(R.string.type_here_wifi_password)
            inputText3!!.inputType = InputType.TYPE_CLASS_TEXT
            linearLayoutWifi!!.visibility = View.VISIBLE
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText2!!.setLines(0)
//            inputText2.setClickable(false);
//            inputText2.setEnabled(false);
//            inputText2.setFocusable(false);
            inputText3!!.visibility = View.VISIBLE
            inputText4!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.VISIBLE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE

//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.wifi_generate)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 4) {
            CURRENT_BTN = EMAIL
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.recipient_email)
            messageText!!.text = mActivity!!.resources.getString(R.string.subject)
            appendUserName!!.visibility = View.GONE
            choose_btn!!.visibility = View.VISIBLE
            inputText!!.setHint(R.string.type_here_email)
            inputText!!.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            inputText2!!.hint = ""
            inputText2!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText3!!.hint = ""
            inputText3!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText3!!.setLines(5)
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            password!!.text = mActivity!!.resources.getString(R.string.body)
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE


//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.email_generate)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 5) {
            CURRENT_BTN = SMS
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.VISIBLE
            appendUserName!!.visibility = View.GONE
            inputText!!.hint = ""
            inputText!!.inputType =
                InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(3)
            inputText2!!.hint = ""
            inputText2!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText2!!.setLines(6)
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.VISIBLE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.VISIBLE
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE


//            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.sms_generate)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 6) {

//            bannerFrame.setVisibility(View.VISIBLE);
//            frameLayout.setVisibility(View.GONE);
            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            CURRENT_BTN = LOCATION
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            //            inputText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
//            inputText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            choose_btn!!.visibility = View.GONE
            inputText!!.hint = ""
            inputText!!.isClickable = false
            inputText!!.isEnabled = false
            inputText!!.isFocusable = false
            inputText2!!.hint = ""
            appendUserName!!.visibility = View.GONE
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.longitude)
            messageText!!.text = mActivity!!.resources.getString(R.string.latitude)
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.location_generate)
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            inputText2!!.isClickable = false
            inputText2!!.isEnabled = false
            inputText2!!.isFocusable = false
            inputText3!!.isClickable = false
            inputText3!!.isEnabled = false
            inputText3!!.isFocusable = false
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText3!!.visibility = View.VISIBLE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            countryCode!!.visibility = View.GONE
            inputText3!!.hint = mActivity!!.resources.getString(R.string.enter_address)
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            password!!.text = mActivity!!.resources.getString(R.string.address)
            v3!!.visibility = View.VISIBLE
            cardViewMap!!.visibility = View.VISIBLE
            if (mapFragment != null) {
                mapFragment!!.getMapAsync(this)
            }
            GlobalScope.launch {
                FetchLocation()
            }
        } else if (qr_category == 7) {
            CURRENT_BTN = PHONE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.VISIBLE
            inputText!!.setHint(R.string.type_here_phone)
            inputText!!.inputType =
                InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(4)
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            appendUserName!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.phone_generate)
            countryCode!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 8) {
            CURRENT_BTN = EVENT
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.hint = ""
            inputText!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText2!!.hint = ""
            inputText2!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText3!!.hint = ""
            inputText3!!.inputType = InputType.TYPE_CLASS_TEXT
            inputText3!!.setLines(5)
            appendUserName!!.visibility = View.GONE
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.event_name)
            messageText!!.text = mActivity!!.resources.getString(R.string.location)
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            password!!.text = mActivity!!.resources.getString(R.string.description)
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.VISIBLE
            inputWhatsapp!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.event_generate)
            countryCode!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
        } else if (qr_category == 9) {
            CURRENT_BTN = FACEBOOK
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.hint = mActivity!!.resources.getString(R.string.facebook_url)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(6)
            appendUserName!!.text = "facebook://user?username="
            appendUserName!!.visibility = View.GONE
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.result_facebook)
            materialSpinner!!.visibility = View.VISIBLE
            inputText!!.setText("https://www.facebook.com/profile.php?id=")
            materialSpinner!!.setItems(
                "https://www.facebook.com/profile.php?id=",
                "https://www.facebook.com/",
                "https://www.facebook.com/groups/"
            )
        } else if (qr_category == 10) {
            CURRENT_BTN = TWITTER
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.setHint(R.string.twitter_url)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(6)
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            appendUserName!!.text = "https://twitter.com/"
            appendUserName!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.result_twitter)
            countryCode!!.visibility = View.GONE
            inputText!!.setText("https://twitter.com/")
            materialSpinner!!.visibility = View.VISIBLE
            materialSpinner!!.setItems("https://twitter.com/")
        } else if (qr_category == 11) {
            CURRENT_BTN = LINKDEIN
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.setHint(R.string.linkdein_url)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(6)
            appendUserName!!.text = "linkdein://user?username="
            appendUserName!!.visibility = View.GONE
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.result_linkdein)
            countryCode!!.visibility = View.GONE
            inputText!!.setText("https://www.linkedin.com/in/")
            materialSpinner!!.visibility = View.VISIBLE
            materialSpinner!!.setItems(
                "https://www.linkedin.com/in/",
                "https://www.linkedin.com/feed/",
                "https://www.linkedin.com/company/",
                "https://www.linkedin.com/hiring/jobs/"
            )
        } else if (qr_category == 12) {
            CURRENT_BTN = INSTAGRAM
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.setHint(R.string.username)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText!!.setLines(6)
            appendUserName!!.text = "instagram://user?username="
            appendUserName!!.visibility = View.GONE
            inputText!!.visibility = View.VISIBLE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            //            cardViewMap.setVisibility(View.GONE);
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.result_instagram)
            countryCode!!.visibility = View.GONE
            inputText!!.setText("https://www.instagram.com/")
            materialSpinner!!.visibility = View.VISIBLE
            materialSpinner!!.setItems("https://www.instagram.com/")
        } else if (qr_category == 13) {
            CURRENT_BTN = WHATSAPP
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            inputText!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            appendUserName!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            recipientText!!.text = mActivity!!.resources.getString(R.string.result_whatsapp)
            recipientText!!.visibility = View.VISIBLE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.VISIBLE
            inputWhatsapp!!.inputType =
                InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputWhatsapp!!.setLines(5)
            qr_cat_name!!.text = mContext!!.resources.getString(R.string.result_whatsapp)
            countryCode!!.visibility = View.VISIBLE
            materialSpinner!!.visibility = View.GONE
        }
        if (barCodeCategory == 2) {
            Constants.format = BarcodeFormat.CODE_128
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.enter_text_without_special_characters)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(70)
            inputText!!.filters = newFilters
            inputText!!.setLines(8)
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "CODE_128"
        } else if (barCodeCategory == 3) {
            Constants.format = BarcodeFormat.CODE_39
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.enter_text_in_uppercase_without_special_characters)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(10)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "CODE_39"
        } else if (barCodeCategory == 4) {
            Constants.format = BarcodeFormat.CODE_93
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.enter_text_in_uppercase_without_special_characters)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(20)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "CODE_93"
        } else if (barCodeCategory == 5) {
            Constants.format = BarcodeFormat.CODABAR
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setLines(8)
            inputText!!.setText("")
            inputText!!.setHint(R.string.only_digits)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(20)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "CODABAR"
        } else if (barCodeCategory == 6) {
            Constants.format = BarcodeFormat.ITF
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.only_even)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(40)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "ITF"
        } else if (barCodeCategory == 7) {
            Constants.format = BarcodeFormat.EAN_8
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.seven_digit_with_checksum)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(8)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "EAN_8"
        } else if (barCodeCategory == 8) {
            Constants.format = BarcodeFormat.EAN_13
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.twelve_digit_with_checksum)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(13)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "EAN_13"
        } else if (barCodeCategory == 9) {
            Constants.format = BarcodeFormat.UPC_A
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.eleven_digit_with_checksum)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(12)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "UPC_A"
        } else if (barCodeCategory == 10) {
            Constants.format = BarcodeFormat.UPC_E
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.seven_digit_with_checksum)
            inputText!!.setLines(8)
            val editFilters = inputText!!.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = LengthFilter(7)
            inputText!!.filters = newFilters
            inputText!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            appendUserName!!.visibility = View.GONE
            v1!!.visibility = View.GONE
            v2!!.visibility = View.GONE
            recipientText!!.visibility = View.GONE
            messageText!!.visibility = View.GONE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.GONE
            v3!!.visibility = View.GONE
            hidden_network!!.visibility = View.GONE
            inputText2!!.visibility = View.GONE
            inputText3!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "UPC_E"
        } else if (barCodeCategory == 11) {
            Constants.format = BarcodeFormat.EAN_13
            CURRENT_BTN = BARCODE

            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            inputText2!!.visibility = View.VISIBLE
            inputText3!!.visibility = View.VISIBLE
            recipientText!!.visibility = View.VISIBLE
            inputText2!!.setLines(2)
            inputText!!.setLines(2)
            inputText3!!.setLines(2)
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.product_name)
            messageText!!.text = mActivity!!.resources.getString(R.string.product_code)
            appendUserName!!.visibility = View.GONE
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.product_name)
            inputText2!!.setHint(R.string.product_code)
            inputText3!!.setHint(R.string.product_price)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText2!!.inputType = InputType.TYPE_CLASS_NUMBER
            inputText3!!.inputType = InputType.TYPE_CLASS_NUMBER
            try {
                val editFilters = inputText!!.filters
                val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
                System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
                newFilters[editFilters.size] = LengthFilter(40)
                inputText!!.filters = newFilters
            } catch (e: Exception) {
                inputText!!.setText("")
            }
            try {
                val editFilters1 = inputText2!!.filters
                val newFilters1 = arrayOfNulls<InputFilter>(editFilters1.size + 1)
                System.arraycopy(editFilters1, 0, newFilters1, 0, editFilters1.size)
                newFilters1[editFilters1.size] = LengthFilter(12)
                inputText2!!.filters = newFilters1
            } catch (e: Exception) {
                inputText2!!.setText("")
            }
            try {
                val editFilters2 = inputText3!!.filters
                val newFilters2 = arrayOfNulls<InputFilter>(editFilters2.size + 1)
                System.arraycopy(editFilters2, 0, newFilters2, 0, editFilters2.size)
                newFilters2[editFilters2.size] = LengthFilter(12)
                inputText3!!.filters = newFilters2
            } catch (e: Exception) {
                inputText3!!.setText("")
            }
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            password!!.text = mActivity!!.resources.getString(R.string.price)
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            qr_cat_name!!.text = mActivity!!.resources.getString(R.string.email)
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "Product"
        } else if (barCodeCategory == 12) {
            Constants.format = BarcodeFormat.EAN_13
            CURRENT_BTN = BARCODE
            inputText!!.setText("")
            inputText2!!.setText("")
            inputText3!!.setText("")
            inputText4!!.setText("")
            inputText2!!.visibility = View.VISIBLE
            inputText3!!.visibility = View.VISIBLE
            recipientText!!.visibility = View.VISIBLE
            inputText2!!.setLines(2)
            inputText!!.setLines(2)
            inputText3!!.setLines(2)
            messageText!!.visibility = View.VISIBLE
            recipientText!!.text = mActivity!!.resources.getString(R.string.book_name_1)
            messageText!!.text = mActivity!!.resources.getString(R.string.book_code_1)
            appendUserName!!.visibility = View.GONE
            choose_btn!!.visibility = View.GONE
            inputText!!.setHint(R.string.book_name)
            inputText2!!.setHint(R.string.book_code)
            inputText3!!.setHint(R.string.book_price)
            inputText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText2!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            inputText3!!.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
            try {
                val editFilters = inputText!!.filters
                val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
                System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
                newFilters[editFilters.size] = LengthFilter(40)
                inputText!!.filters = newFilters
            } catch (_: Exception) {
                //
            }
            try {
                val editFilters1 = inputText2!!.filters
                val newFilters1 = arrayOfNulls<InputFilter>(editFilters1.size + 1)
                System.arraycopy(editFilters1, 0, newFilters1, 0, editFilters1.size)
                newFilters1[editFilters1.size] = LengthFilter(12)
                inputText2!!.filters = newFilters1
            } catch (_: Exception) {
                //
            }
            try {
                val editFilters2 = inputText3!!.filters
                val newFilters2 = arrayOfNulls<InputFilter>(editFilters2.size + 1)
                System.arraycopy(editFilters2, 0, newFilters2, 0, editFilters2.size)
                newFilters2[editFilters2.size] = LengthFilter(12)
                inputText3!!.filters = newFilters2
            } catch (_: Exception) {
                //
            }
            v1!!.visibility = View.VISIBLE
            v2!!.visibility = View.VISIBLE
            linearLayoutWifi!!.visibility = View.GONE
            password!!.visibility = View.VISIBLE
            password!!.text = mActivity!!.resources.getString(R.string.book_price_1)
            v3!!.visibility = View.VISIBLE
            hidden_network!!.visibility = View.GONE
            inputText4!!.visibility = View.GONE
            inputText5!!.visibility = View.GONE
            inputText6!!.visibility = View.GONE
            inputText7!!.visibility = View.GONE
            memoRelative!!.visibility = View.GONE
            company!!.visibility = View.GONE
            jobTitle!!.visibility = View.GONE
            address!!.visibility = View.GONE
            contactChooseFromContact!!.visibility = View.GONE
            v4!!.visibility = View.GONE
            v5!!.visibility = View.GONE
            v6!!.visibility = View.GONE
            email_choose_btn!!.visibility = View.GONE
            eventRel!!.visibility = View.GONE
            countryCode!!.visibility = View.GONE
            inputWhatsapp!!.visibility = View.GONE
            materialSpinner!!.visibility = View.GONE
            qr_cat_name!!.text = "Product"
        }

    }

    private fun FetchLocation() {
        loadLocation()
        CheckGpsStatus()
        if (GpsStatus) {
            val locationRequest = LocationRequest()
            locationRequest.setInterval(10000)
            locationRequest.setFastestInterval(3000)
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            LocationServices.getFusedLocationProviderClient(this@GenerateActivity)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(applicationContext)
                            .removeLocationUpdates(this)
                        if (locationResult != null && locationResult.locations.size > 0) {
                            val latestlocIndex = locationResult.locations.size - 1
                            lati = locationResult.locations[latestlocIndex].latitude
                            longi = locationResult.locations[latestlocIndex].longitude
                            latitude = lati.toString()
                            longitude = longi.toString()
                            inputText!!.setText(longitude)
                            inputText2!!.setText(latitude)
                            LocationOnMap()
                            if (isNetworkConnected) {
                                try {
                                    val location = Location("providerNA")
                                    location.longitude = longi
                                    location.latitude = lati
                                    val address =
                                        getAddress(mContext, location.latitude, location.longitude)
                                    inputText3!!.visibility = View.VISIBLE
                                    inputText3!!.setText(address)
                                    password!!.visibility = View.VISIBLE
                                    password!!.text =
                                        mActivity!!.resources.getString(R.string.address)
                                    v3!!.visibility = View.VISIBLE
                                    refresh!!.visibility = View.GONE
                                } catch (ignored: Exception) {
                                }
                            } else {
                                Toast.makeText(mActivity, "Network Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }, Looper.getMainLooper())
        }
    }

    protected fun loadLocation() {
        Dexter.withContext(applicationContext)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    CheckGpsStatus()
                    if (GpsStatus) {
                        val locationRequest = LocationRequest()
                        locationRequest.setInterval(10000)
                        locationRequest.setFastestInterval(3000)
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        if (ActivityCompat.checkSelfPermission(
                                this@GenerateActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this@GenerateActivity,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return
                        }
                        LocationServices.getFusedLocationProviderClient(this@GenerateActivity)
                            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    LocationServices.getFusedLocationProviderClient(
                                        applicationContext
                                    )
                                        .removeLocationUpdates(this)
                                    if (locationResult != null && locationResult.locations.size > 0) {
                                        val latestlocIndex = locationResult.locations.size - 1
                                        lati = locationResult.locations[latestlocIndex].latitude
                                        longi = locationResult.locations[latestlocIndex].longitude
                                        latitude = lati.toString()
                                        longitude = longi.toString()
                                        inputText!!.setText(longitude)
                                        inputText2!!.setText(latitude)
                                        LocationOnMap()
                                        if (isNetworkConnected) {
                                            try {
                                                val location = Location("providerNA")
                                                location.longitude = longi
                                                location.latitude = lati
                                                val address = getAddress(
                                                    mContext,
                                                    location.latitude,
                                                    location.longitude
                                                )
                                                //                                                        inputLayout3.setVisibility(View.VISIBLE);
                                                inputText3!!.setText(address)
                                            } catch (ignored: Exception) {
                                            }
                                        } else {
                                            Toast.makeText(
                                                mActivity,
                                                "Network Error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }, Looper.getMainLooper())
                    } else {
                        enableLoc()
                    }
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            })
            .withErrorListener {
                //                        Log.e("Dexter", "There was an error: " + dexterError.toString());
            }
            .check()
    }

    private val isNetworkConnected: Boolean
        private get() {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }

    private fun enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(this@GenerateActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(bundle: Bundle?) {}
                    override fun onConnectionSuspended(i: Int) {
                        googleApiClient!!.connect()
                    }
                })
                .addOnConnectionFailedListener {}.build()
            googleApiClient!!.connect()
            val locationRequest = LocationRequest.create()
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setInterval((30 * 1000).toLong())
            locationRequest.setFastestInterval((5 * 1000).toLong())
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            val result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback { result ->
                val status = result.status
                if (status.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(mActivity!!, REQUEST_LOCATION)

//                                finish();
                    } catch (e: SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
        }
    }

    fun CheckGpsStatus() {
        try {
            locationManager = mContext!!.getSystemService(LOCATION_SERVICE) as LocationManager
            GpsStatus = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ignored: Exception) {
        }
    }

    fun getAddress(ctx: Context?, lat: Double, lng: Double): String {
        var fullAdd = ""
        try {
            val geocoder = ctx?.let { Geocoder(it, Locale.getDefault()) }
            val addresses = geocoder?.getFromLocation(lat, lng, 1)
            if (addresses != null) {
                if (addresses.size > 0) {
                    val address = addresses[0]
                    fullAdd = address.getAddressLine(0)


                    // if you want only city or pin code use following code //
                    /* String Location = address.getLocality();
                    String zip = address.getPostalCode();
                    String Country = address.getCountryName(); */
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return fullAdd
    }

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        try {
            val currentFocus = currentFocus
            currentFocus?.clearFocus()
        } catch (ignored: Exception) {
        }
    }

    fun LocationOnMap() {
        if (map != null) {
            val TutorialsPoint = LatLng(lati, longi)

            // Add a marker in Sydney and move the camera
            val markerOptions = MarkerOptions().position(TutorialsPoint).title("You are here...!!")
            //                                    animateMarker(markerOptions.getIcon(),latLng,false);
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(TutorialsPoint, 17f)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map!!.isMyLocationEnabled = true
            map!!.addMarker(markerOptions)
            map!!.animateCamera(cameraUpdate)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    companion object {
        const val PICK_CONTACT = 1
        const val REQUEST_LOCATION = 199
        private const val TEXT = 0
        private const val CONTACT = 1
        private const val URL = 2
        private const val EMAIL = 3
        private const val WIFI = 4
        private const val BARCODE = 5
        private const val SMS = 6
        private const val LOCATION = 7
        private const val PHONE = 8
        private const val EVENT = 9
        private const val FACEBOOK = 10
        private const val TWITTER = 11
        private const val LINKDEIN = 12
        private const val INSTAGRAM = 13
        private const val WHATSAPP = 14
        private val LOG_TAG = GenerateActivity::class.java.simpleName
        private var CURRENT_BTN = 0
        private var getCode = 0
    }
}