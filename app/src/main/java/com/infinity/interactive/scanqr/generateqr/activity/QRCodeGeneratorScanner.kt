package com.infinity.interactive.scanqr.generateqr.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.adapter.ColorAdapter
import com.infinity.interactive.scanqr.generateqr.adapter.LogoAdapter
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.CodeGenerator
import com.infinity.interactive.scanqr.generateqr.utility.DialogUtils
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

class QRCodeGeneratorScanner : AppCompatActivity() {
    var mActivity: Activity? = null
    var mContext: Context? = null
    lateinit var colorsTxt: Array<String>
    lateinit var colorsRecycler: Array<String>
    var colors: MutableList<Int>? = null
    var colorsRecyclerList: MutableList<Int>? = null
    var logoRecyclerList: MutableList<Int>? = null
    var backQR: ImageView? = null
    var editQR: TextView? = null
    var logo: Bitmap? = null
    var qr_generate: String? = null
    var bitmapAdapter: Bitmap? = null
    var templateBitmap: Bitmap? = null
    var mapOpen: ImageView? = null
    var getLocationCode = 0
    var activityCode = 0
    var latitude = 0f
    var longitude = 0f
    var date: Date? = null
    var dateEnd: Date? = null
    var bitmapSaved: Bitmap? = null
    var geo = ""
    var add = ""
    var evTitle = ""
    var eveDescription = ""
    var eveLocation = ""
    var logoQR: RelativeLayout? = null
    var colorQR: RelativeLayout? = null
    var customQR: RelativeLayout? = null
    var linearLayout1QR: LinearLayout? = null
    var linearLayout2QR: RelativeLayout? = null
    var saveQR: Button? = null
    var tickQR: ImageView? = null
    var logoBitmap: ImageView? = null
    var colorAdapter: ColorAdapter? = null
    var logoAdapter: LogoAdapter? = null
    var qrCard: CardView? = null
    var barCodeCardCard: CardView? = null
    var shareQrOnly: RelativeLayout? = null
    var barCodeCard: ImageView? = null
    var barCodebackground: ImageView? = null
    var barCodeLogo: ImageView? = null
    var qr_code_style: CardView? = null
    var bar_code_style: RelativeLayout? = null
    var barCodeRelative: RelativeLayout? = null
    var cardOfLogo: CardView? = null
    var categoryTxt: TextView? = null
    var topView: RelativeLayout? = null
    var outputBitmap: ImageView? = null
    var mRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_generate)
        initVar()
        initView()
        initFunctionality()
        initListener()
    }

    private fun initVar() {
        mActivity = this
        mContext = (mActivity as QRCodeGeneratorScanner).getApplicationContext()
        colorsTxt = applicationContext.resources.getStringArray(R.array.colors)
        colorsRecycler = applicationContext.resources.getStringArray(R.array.colorsRecycler)
        colors = ArrayList()
        colorsRecyclerList = ArrayList()
        logoRecyclerList = ArrayList()
    }

    private fun initView() {
        outputBitmap = findViewById(R.id.outputBitmap)
        mapOpen = findViewById(R.id.open_in_map)
        saveQR = findViewById(R.id.save_btn)
        backQR = findViewById(R.id.backButtonFromQR)
        editQR = findViewById(R.id.edit)
        logoBitmap = findViewById(R.id.logoBitmap)
        qrCard = findViewById(R.id.qrCard)
        barCodeCardCard = findViewById(R.id.barcodeqrCard)
        barCodeCard = findViewById(R.id.barcodeBitmap)
        bar_code_style = findViewById(R.id.barcodeStyle)
        qr_code_style = findViewById(R.id.qr_style)
        barCodebackground = findViewById(R.id.barcodebackgroundBitmap)
        barCodeLogo = findViewById(R.id.barcodelogoBitmap)
        barCodeRelative = findViewById(R.id.barCodeRelative)
        logoQR = findViewById(R.id.add_logo)
        colorQR = findViewById(R.id.colorPalette)
        customQR = findViewById(R.id.customRel)
        linearLayout1QR = findViewById(R.id.l1QR)
        linearLayout2QR = findViewById(R.id.l2QR)
        mRecyclerView = findViewById(R.id.recycler_view)
        tickQR = findViewById(R.id.tick)
        categoryTxt = findViewById(R.id.category_txt)
        cardOfLogo = findViewById(R.id.logoCard)
        shareQrOnly = findViewById(R.id.share_qr_only)
        topView = findViewById(R.id.top_view)
    }

    private fun initFunctionality() {
        try {
            val imm = mActivity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            var view = mActivity!!.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(mActivity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (ignored: Exception) {
        }
        initLogo()
        for (s in colorsTxt) {
            val newColor = Color.parseColor(s)
            colors!!.add(newColor)
        }
        for (s in colorsRecycler) {
            colorsRecyclerList!!.add(Color.parseColor(s))
        }
        val mIntent = intent
        qr_generate = mIntent.getStringExtra("generateQR")
        CODE_TYPE = mIntent.getIntExtra("codeType", 0)
        getLocationCode = mIntent.getIntExtra("location", 0)
        activityCode = mIntent.getIntExtra("activity", 0)
        if (CODE_TYPE == 1) {
            logoQR!!.visibility = View.GONE
            colorQR!!.visibility = View.VISIBLE
        }
        initAdapter()
        CodeGenerator.setBLACK(Color.BLACK)
        generateCode(qr_generate)
        val previousResult =
            AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_CREATED)
        if (CODE_TYPE == 1) {
            var barCodeStr = qr_generate
            barCodeStr = "barCodeType:" + Constants.format + ";" + "barcode:" + qr_generate + ";"
            previousResult.add(barCodeStr)
        } else {
            previousResult.add(qr_generate)
        }
        AppPreference.getInstance(mContext)
            .setStringArray(PrefKey.RESULT_LIST_OF_CREATED, previousResult)

        //save date of generation
        val currentDate: String
        currentDate = if (Locale.getDefault() == Locale.US) {
            SimpleDateFormat("MM.dd.yyyy HH:mm").format(Calendar.getInstance().time)
        } else {
            SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().time)
        }
        val previousDate =
            AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_CREATED)
        previousDate.add(currentDate)
        AppPreference.getInstance(mContext)
            .setStringArray(PrefKey.DATE_LIST_OF_CREATED, previousDate)

        //saving color (standard black)
//        ArrayList<String> previousColor = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_CREATED);
//        previousColor.add(Integer.toString(Color.BLACK));
//        AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_CREATED, previousColor);
        if (getLocationCode == 1) {
            mapOpen!!.visibility = View.VISIBLE
            if (qr_generate!!.contains("?")) {
                try {
                    var m =
                        Pattern.compile("geo:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                    while (m.find()) {
                        geo = m.group(1)
                        geo = geo.substring(0, geo.indexOf("?"))
                    }
                    m = Pattern.compile("q=(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                    while (m.find()) {
                        add = m.group(1)
                        add = add.substring(0, add.indexOf(";"))
                    }
                } catch (ignored: Exception) {
                }
            }
            val latLongArray =
                geo.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var latS = latLongArray[0]
            var longS = latLongArray[1]
            latS = latS.trim { it <= ' ' }
            longS = longS.trim { it <= ' ' }
            try {
                latitude = latS.toFloat()
                longitude = longS.toFloat()
            } catch (ex: NumberFormatException) { // handle your exception
            }
        } else if (getLocationCode == 2) {
            mapOpen!!.visibility = View.VISIBLE
            mapOpen!!.setImageResource(R.drawable.event_scanner)
            qr_generate = qr_generate!!.replace("\n", ";")
            var beginEvent = ""
            var summary = ""
            var description = ""
            var location = ""
            var dtstart = ""
            var dtend = ""
            try {
                var m = Pattern.compile("BEGIN:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                while (m.find()) {
                    beginEvent = m.group(1)
                    if (qr_generate!!.contains("BEGIN:") || qr_generate!!.contains("begin:")) {
//                        beginEvent = beginEvent.substring(0, beginEvent.indexOf("\n"));
                        beginEvent = beginEvent.substring(0, beginEvent.indexOf(";"))
                    }
                }
                m = Pattern.compile("SUMMARY:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                while (m.find()) {
                    summary = m.group(1)
                    if (qr_generate!!.contains("SUMMARY:") || qr_generate!!.contains("summary:")) {
                        summary = summary.substring(0, summary.indexOf(";"))
                    }
                }
                m = Pattern.compile("DTSTART:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                while (m.find()) {
                    dtstart = m.group(1)
                    if (qr_generate!!.contains("DTSTART:") || qr_generate!!.contains("dtstart:")) {
                        dtstart = dtstart.substring(0, dtstart.indexOf(";"))
                    }
                }
                m = Pattern.compile("DTEND:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                while (m.find()) {
                    dtend = m.group(1)
                    if (qr_generate!!.contains("DTEND:") || qr_generate!!.contains("dtend:")) {
                        dtend = dtend.substring(0, dtend.indexOf(";"))
                    }
                }
                m = Pattern.compile("LOCATION:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate)
                while (m.find()) {
                    location = m.group(1)
                    if (qr_generate!!.contains("LOCATION:") || qr_generate!!.contains("location:")) {
                        location = location.substring(0, location.indexOf(";"))
                    }
                }
                m = Pattern.compile("DESCRIPTION:(.*)", Pattern.CASE_INSENSITIVE)
                    .matcher(qr_generate)
                while (m.find()) {
                    description = m.group(1)
                    if (qr_generate!!.contains("DESCRIPTION:") || qr_generate!!.contains("description:")) {
                        description = description.substring(0, description.indexOf(";"))
                    }
                }
            } catch (ignored: Exception) {
            }
            val formatter = SimpleDateFormat("yyyyMMdd'T'HHmmss")
            try {
                date = formatter.parse(dtstart)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            try {
                dateEnd = formatter.parse(dtend)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            evTitle = summary
            eveDescription = description
            eveLocation = location
        } else {
            mapOpen!!.visibility = View.GONE
        }
    }

    private fun initLogo() {
        logoRecyclerList!!.add(R.drawable.traffic_icon)
//        logoRecyclerList!!.add(R.drawable.add_image_icon)
        for (i in 1..10) {
            val img = resources.getIdentifier("logo_qr_$i", "drawable", packageName)
            logoRecyclerList!!.add(img)
        }
    }

    private fun initAdapter() {
        colorAdapter = ColorAdapter(mContext, colorsRecyclerList)
        logoAdapter = LogoAdapter(mContext, logoRecyclerList)
        mRecyclerView!!.layoutManager =
            LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        mRecyclerView!!.isNestedScrollingEnabled = true
        mRecyclerView!!.setHasFixedSize(true)
    }

    private fun generateCode(input: String?) {
        val codeGenerator = CodeGenerator()
        if (CODE_TYPE == 1) {
            codeGenerator.generateBarFor(input)
        } else {
            codeGenerator.generateQRFor(input)
            Constants.finalImageEditor = 0
        }
        codeGenerator.setResultListener { bitmap ->
            //((BitmapDrawable)outputBitmap.getDrawable()).getBitmap().recycle();
            if (CODE_TYPE == 1) {
                qr_code_style!!.visibility = View.GONE
                bar_code_style!!.visibility = View.VISIBLE
                inputStr = "barcode:$input"
                logoQR!!.visibility = View.GONE
                colorQR!!.visibility = View.VISIBLE
                linearLayout2QR!!.visibility = View.GONE
                try {
                    Glide.with(mContext!!)
                        .load(bitmap)
                        .centerInside() // resizes width to 100, preserves original height, does not respect aspect ratio
                        .into(barCodeCard!!)
                    bitmapSaved = bitmap
                } catch (ignored: Exception) {
                }
            } else {
                qr_code_style!!.visibility = View.VISIBLE
                bar_code_style!!.visibility = View.GONE
                inputStr = input
                try {
                    bitmapSaved = bitmap
                    templateBitmap = bitmap
                    Glide.with(mContext!!)
                        .load(bitmap)
                        .centerInside() // resizes width to 100, preserves original height, does not respect aspect ratio
                        .into(outputBitmap!!)
                } catch (ignored: Exception) {
                }
            }
        }
        codeGenerator.execute()
    }

    private fun initListener() {
        mapOpen!!.setOnClickListener {
            if (getLocationCode == 1) {
                val uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(mActivity, "No Maps Installed", Toast.LENGTH_SHORT).show()
                }
            } else if (getLocationCode == 2) {
                if (date == null && dateEnd == null) {
                    try {
                        val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, evTitle)
                            .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
                            .putExtra(
                                CalendarContract.Events.AVAILABILITY,
                                CalendarContract.Events.AVAILABILITY_BUSY
                            )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "No Maps Installed", Toast.LENGTH_SHORT).show()
                    }
                } else if (date == null) {
                    try {
                        val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd!!.time)
                            .putExtra(CalendarContract.Events.TITLE, evTitle)
                            .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
                            .putExtra(
                                CalendarContract.Events.AVAILABILITY,
                                CalendarContract.Events.AVAILABILITY_BUSY
                            )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show()
                    }
                } else if (dateEnd == null) {
                    try {
                        val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date!!.time)
                            .putExtra(CalendarContract.Events.TITLE, evTitle)
                            .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
                            .putExtra(
                                CalendarContract.Events.AVAILABILITY,
                                CalendarContract.Events.AVAILABILITY_BUSY
                            )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date!!.time)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd!!.time)
                        .putExtra(CalendarContract.Events.TITLE, evTitle)
                        .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
                        .putExtra(
                            CalendarContract.Events.AVAILABILITY,
                            CalendarContract.Events.AVAILABILITY_BUSY
                        )
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        saveQR!!.setOnClickListener {
            Constants.finalImageEditor = 0
            try {
                if (CODE_TYPE == 1) {
                    captureImageQRCODE(barCodeRelative)
                } else {
                    if (bitmapAdapter != null) {
                        captureImageQRCODE(qr_code_style)
                    } else {
                        captureImageQRCODE(shareQrOnly)
                    }
                }
            } catch (ignored: Exception) {
            }
            val in1 = Intent(mContext, SaveQRCode::class.java)
            startActivity(in1)
        }
        backQR!!.setOnClickListener {
            DialogUtils.showDialogPrompt(
                mActivity, null, getString(R.string.remove_customization),
                getString(R.string.yes), getString(R.string.no), true
            ) {
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
                finish()
            }
        }
        colorAdapter!!.setClickListener { position ->
            CodeGenerator.setBLACK(colorsRecyclerList!![position])
            generateCode(qr_generate)
        }
        logoAdapter!!.setClickListener { position ->
            if (position == 0) {
                cardOfLogo!!.visibility = View.GONE
                logoBitmap!!.setImageBitmap(null)
                logo = null
            }
//            else if (position == 1) {
//                galleryImageLogo()
//            }
            else {
                cardOfLogo!!.visibility = View.VISIBLE
                logo = BitmapFactory.decodeResource(
                    resources,
                    logoRecyclerList!![position]
                )
                logoBitmap!!.setImageResource(logoRecyclerList!![position])
            }
        }
        colorQR!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                mRecyclerView!!.adapter = colorAdapter
                linearLayout1QR!!.visibility = View.GONE
                linearLayout2QR!!.visibility = View.GONE
                categoryTxt!!.visibility = View.VISIBLE
                categoryTxt!!.text = resources.getString(R.string.solid_color)
                saveQR!!.visibility = View.GONE
                tickQR!!.visibility = View.VISIBLE
                editQR!!.text = resources.getString(R.string.solid_color)
                topView!!.visibility = View.VISIBLE
                mRecyclerView!!.visibility = View.VISIBLE
                //                saveButtonTemplate.setVisibility(View.GONE);
                run {
                    if (CODE_TYPE == 1) {
                        qr_code_style!!.visibility = View.GONE
                    } else {
                        qr_code_style!!.visibility = View.VISIBLE
                    }

//                    saveButtonTemplate.setVisibility(View.GONE);
                    saveQR!!.visibility = View.VISIBLE
                }
            }
        })
        logoQR!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                mRecyclerView!!.adapter = logoAdapter
                linearLayout1QR!!.visibility = View.GONE
                linearLayout2QR!!.visibility = View.GONE
                categoryTxt!!.text = resources.getString(R.string.logo)
                categoryTxt!!.visibility = View.VISIBLE
                saveQR!!.visibility = View.GONE
                tickQR!!.visibility = View.VISIBLE
                editQR!!.text = resources.getString(R.string.logo)
                topView!!.visibility = View.VISIBLE
                mRecyclerView!!.visibility = View.VISIBLE
                run {
                    qr_code_style!!.visibility = View.VISIBLE
                    saveQR!!.visibility = View.VISIBLE
                }
            }
        })
        linearLayout2QR!!.setOnClickListener {
            val fancyQR = Intent(this@QRCodeGeneratorScanner, CustomQRCode::class.java)
            fancyQR.putExtra(PrefKey.FancyQRStr, qr_generate)
            startActivity(fancyQR)
        }
        tickQR!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                linearLayout1QR!!.visibility = View.VISIBLE
                linearLayout2QR!!.visibility = View.VISIBLE
                saveQR!!.visibility = View.VISIBLE
                tickQR!!.visibility = View.GONE
                topView!!.visibility = View.GONE
                mRecyclerView!!.visibility = View.GONE
                editQR!!.text = resources.getString(R.string.edit)
                run {
                    if (CODE_TYPE == 1) {
                        linearLayout2QR!!.visibility = View.GONE
                    } else {
                        linearLayout2QR!!.visibility = View.VISIBLE
                    }
                    saveQR!!.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun captureImageQRCODE(v: View?) {
        // TODO Auto-generated method stub
        var bitmap = Bitmap.createBitmap(
            v!!.width, v.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap = ThumbnailUtils.extractThumbnail(
            bitmap, v.width,
            v.height
        )
        val b = Canvas(bitmap)
        v.draw(b)
        Constants.finalBitmap = bitmap
    }

    //    private void galleryImage() {
    //
    //
    //        PictureSelector.create(QRCodeGeneratorScanner.this)
    //                .openGallery(PictureMimeType.ofImage())
    //                .isCamera(false)
    //                .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
    //                .selectionMode(PictureConfig.SINGLE)
    //                .previewImage(false)
    //                .forResult(PictureConfig.CHOOSE_REQUEST);
    //
    //    }
    public override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
//            if (reqCode == PictureConfig.CHOOSE_REQUEST) {
//                // onResult Callback
//
//                if (data != null) {
//
//                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
//
//
//                    try {
//                        if (result.size() == 1) {
//
//                            Uri selectedImage = Uri.parse(result.get(0).getPath());
//
//
//                        }
//                    } catch (Exception ignored) {
//
//                    }
//
//                }
//
//
//            } else
            if (reqCode == PICK_IMAGE) {
                if (data != null) {
                    var selectedImage: Uri? = null
                    selectedImage = try {
                        data.data
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
                        cardOfLogo!!.visibility = View.VISIBLE
                        logo = base
                        Glide.with(mContext!!)
                            .asBitmap()
                            .load(base)
                            .centerCrop() // resizes width to 100, preserves original height, does not respect aspect ratio
                            .into(logoBitmap!!)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun galleryImageLogo() {
        val intent = Intent(Intent.ACTION_PICK)
        //set intent type to image
        intent.setType("image/*")
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    companion object {
        const val PICK_IMAGE = 1
        var CODE_TYPE = 0

        @JvmField
        var inputStr: String? = null
    }
}