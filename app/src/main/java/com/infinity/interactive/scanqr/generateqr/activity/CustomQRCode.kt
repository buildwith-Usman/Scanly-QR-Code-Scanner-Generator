package com.infinity.interactive.scanqr.generateqr.activity


import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.withRotation
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.alexzhirkevich.customqrgenerator.QrCodeGenerator
import com.github.alexzhirkevich.customqrgenerator.QrErrorCorrectionLevel
import com.github.alexzhirkevich.customqrgenerator.QrOptions
import com.github.alexzhirkevich.customqrgenerator.ThreadPolicy
import com.github.alexzhirkevich.customqrgenerator.style.QrBallShape
import com.github.alexzhirkevich.customqrgenerator.style.QrCanvasColor
import com.github.alexzhirkevich.customqrgenerator.style.QrColor
import com.github.alexzhirkevich.customqrgenerator.style.QrColors
import com.github.alexzhirkevich.customqrgenerator.style.QrElementsShapes
import com.github.alexzhirkevich.customqrgenerator.style.QrFrameShape
import com.github.alexzhirkevich.customqrgenerator.style.QrPixelShape
import com.github.alexzhirkevich.customqrgenerator.style.toColor
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.adapter.EyesDotsAdapter
import com.infinity.interactive.scanqr.generateqr.adapter.GradientAdapter
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.databinding.ActivityCustomQrcodeBinding
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


//custom color for eyes


class CustomQRCode : AppCompatActivity() {


    private val binding by lazy {
        ActivityCustomQrcodeBinding.inflate(layoutInflater)
    }


    private val eyeColor = QrCanvasColor { canvas ->
        with(canvas) {
            val paint = Paint().apply {
                color = 0xff345288.toColor()
                isAntiAlias = true
            }
            withRotation(135f, width / 2f, height / 2f) {
                drawRect(
                    -width / 2f, -height / 2f,
                    1.5f * width, height / 2f, paint
                )
                paint.color = 0xff31b4d5.toColor()
                drawRect(
                    -width / 2f, height / 2f,
                    1.5f * width, 1.5f * height, paint
                )
            }
        }
    }


    var customOverlayColor: QrColor = QrColor
        .Solid(0xff000000.toColor())

    var customBackgroundColor: QrColor = QrColor
        .Solid(0xffffffff.toColor())

    var customFrame: QrFrameShape = QrFrameShape
        .Default

    var customBall: QrBallShape = QrBallShape.Default

    var customDots: QrPixelShape = QrPixelShape.Default


    lateinit var bmp: Bitmap
    lateinit var ss: String
    var mLastClickTime: Long = 0
    var dialog: Dialog? = null
    val backgroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    // 0xAARRGGBB long and convert it to color int.
    var options = QrOptions.Builder(1024, 1024)
        .setPadding(.1f)
        .setColors(
            QrColors(
                dark = customOverlayColor,
                highlighting = customBackgroundColor,
            )
        )
        .setElementsShapes(
            QrElementsShapes(
                darkPixel = customDots,
                ball = customBall,
                frame = customFrame,
            )
        )
        .setErrorCorrectionLevel(QrErrorCorrectionLevel.High)
        .build()


    // QR code generator thread policy.
// Use wisely. More threads doesn't mean more performance.
// It depends on device and QrOptions.size
    private val threadPolicy = when (Runtime.getRuntime().availableProcessors()) {
        in 1..3 -> ThreadPolicy.SingleThread
        in 4..6 -> ThreadPolicy.DoubleThread
        else -> ThreadPolicy.QuadThread
    }

    private val qrGenerator: QrCodeGenerator by lazy {
        QrCodeGenerator(this@CustomQRCode, threadPolicy)
    }


    private var eyesIdList = arrayOf<Int>(
        R.drawable.eye_01,
        R.drawable.eye_02,
        R.drawable.eye_03,
        R.drawable.eye_04,
        R.drawable.eye_05,
        R.drawable.eye_06,
        R.drawable.eye_07,
        R.drawable.eye_08
    )

    private var dotsIdList = arrayOf<Int>(
        R.drawable.dot_01,
        R.drawable.dot_02,
        R.drawable.dot_03,
        R.drawable.dot_04,
        R.drawable.dot_05,
        R.drawable.dot_07,
        R.drawable.dot_08
    )


    private var gradientIdList = arrayOf<Int>(
        R.drawable.gradient_01,
        R.drawable.gradient_02,
        R.drawable.gradient_03,
        R.drawable.gradient_04,
        R.drawable.gradient_05,
        R.drawable.gradient_06,
        R.drawable.gradient_07,
        R.drawable.gradient_08,
        R.drawable.gradient_09,
        R.drawable.gradient_10,


        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(binding.root)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )

        ss = intent.getStringExtra(PrefKey.FancyQRStr).toString()

        dialog = Dialog(this)


        // This will pass the ArrayList to our Adapter
        val adapterEyes = EyesDotsAdapter(this@CustomQRCode, eyesIdList.toMutableList())
        val adapterDots = EyesDotsAdapter(this@CustomQRCode, dotsIdList.toMutableList())
        val adapterGradient = GradientAdapter(this@CustomQRCode, gradientIdList.toMutableList())
        // Setting the Adapter with the recyclerview

        with(binding) {


//            if (!Constants.removeAds) {
//                try {
//
//
//                    AdsManagerQ.getInstance().loadFreshBannerAd(
//                        this@CustomQRCode,
//                        bannerAdsFrame,
//                        adsTxt,
//                        AdSize.LARGE_BANNER,
//                        resources.getString(R.string.banner_ad_home_main_sticky_unit_id)
//                    )
//
//                } catch (ignored: Exception) {
//
//                }
//            } else {
//                adLayout.visibility = View.GONE
//            }

            eyesRecyclerView.layoutManager =
                LinearLayoutManager(this@CustomQRCode, RecyclerView.HORIZONTAL, false)
            dotsRecyclerView.layoutManager =
                LinearLayoutManager(this@CustomQRCode, RecyclerView.HORIZONTAL, false)
            gradientRecyclerView.layoutManager =
                LinearLayoutManager(this@CustomQRCode, RecyclerView.HORIZONTAL, false)
            eyesRecyclerView.adapter = adapterEyes
            dotsRecyclerView.adapter = adapterDots
            gradientRecyclerView.adapter = adapterGradient


//            showProgressDialog()
//            changeQR()
            try {
                lifecycleScope.launchWhenStarted {
                    bmp = qrGenerator.generateQrCode(
                        com.github.alexzhirkevich.customqrgenerator.QrData.Url(ss),
                        options
                    )
                    ivQrcode.setImageBitmap(bmp)
                }
            } catch (exc: Exception) {

            }

            adapterEyes.setClickListener {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                    return@setClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()

                showProgressDialog()

                if (it == 0) {
                    customFrame = QrFrameShape.Default
                    customBall = QrBallShape.Default
                } else if (it == 1) {
                    customFrame = QrFrameShape.Circle(0.7f)
                    customBall = QrBallShape.Circle(1.0f)
                } else if (it == 2) {
                    customFrame = QrFrameShape.Default
                    customBall = QrBallShape.Circle(1.0f)
                } else if (it == 3) {
                    customFrame = QrFrameShape.Circle(1.0f)
                    customBall = QrBallShape.Rhombus
                } else if (it == 4) {
                    customFrame = QrFrameShape.AsPixelShape(QrPixelShape.Circle(1.0f))
                    customBall = QrBallShape.Default
                } else if (it == 5) {
                    customFrame = QrFrameShape.RoundCorners(0.25f)
                    customBall = QrBallShape.Circle(1.0f)
                } else if (it == 6) {
                    customFrame = QrFrameShape.RoundCorners(0.25f, true, false, false, true)
                    customBall = QrBallShape.RoundCorners(0.25f, false, false, false, true)
                } else if (it == 7) {
                    customFrame = QrFrameShape.AsPixelShape(QrPixelShape.Circle(1.0f))
                    customBall = QrBallShape.Circle(1.0f)
                }

                changeQR()

            }

            adapterDots.setClickListener {


                if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                    return@setClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()

                showProgressDialog()

                if (it == 0) {
                    customDots = QrPixelShape.RoundCorners(0.25f)


                } else if (it == 1) {

                    customDots = QrPixelShape.Circle(1.0f)


                } else if (it == 2) {
                    customDots = QrPixelShape.Default

                } else if (it == 3) {
                    customDots = QrPixelShape.Rhombus

                } else if (it == 4) {
                    customDots = QrPixelShape.Circle(0.5f)

                } else if (it == 5) {
                    customDots = QrPixelShape.RoundCornersHorizontal(0.1f)

                } else if (it == 6) {
                    customDots = QrPixelShape.Star

                }
                changeQR()
            }

            adapterGradient.setClickListener {


                if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                    return@setClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()

                showProgressDialog()


                if (it == 0) {

                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xffFF0076.toColor(),
                        endColor = 0xff590FB7.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 1) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xffff512f.toColor(),
                        endColor = 0xffdd2476.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 2) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xff0B63F6.toColor(),
                        endColor = 0xff003CC5.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 3) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xffE233FF.toColor(),
                        endColor = 0xffFF6B00.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 4) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xff402565.toColor(),
                        endColor = 0xff30bE96.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 5) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xff402662.toColor(),
                        endColor = 0xff3900A6.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 6) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xffF14658.toColor(),
                        endColor = 0xffDC2537.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 7) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xffF40076.toColor(),
                        endColor = 0xff342711.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 8) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xff000066.toColor(),
                        endColor = 0xff6699FF.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                } else if (it == 9) {
                    customOverlayColor = QrColor.LinearGradient(
                        startColor = 0xff141e30.toColor(),
                        endColor = 0xff243b55.toColor(),
                        QrColor.LinearGradient.Orientation.RightDiagonal
                    )

                }
                changeQR()
            }

            editBackButtonFromQR.setOnClickListener {
                finish()
            }

            shareCustom.setOnClickListener {
                captureImageQRCODE(ivQrcode)

                val in1 = Intent(this@CustomQRCode, SaveQRCode::class.java)
                startActivity(in1)

            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }


    private fun captureImageQRCODE(v: View) {
        // TODO Auto-generated method stub
//        OutputStream output;
//
//        Calendar cal = Calendar.getInstance();
//
        var bitmap = Bitmap.createBitmap(
            v.width, v.height,
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

    private fun changeQR() {


        with(binding) {
            options = QrOptions.Builder(1024, 1024)
                .setPadding(.1f)
                .setColors(
                    QrColors(
                        dark = customOverlayColor,
                        highlighting = customBackgroundColor,
                    )
                )
                .setElementsShapes(
                    QrElementsShapes(
                        darkPixel = customDots,
                        ball = customBall,
                        frame = customFrame,
                    )
                )
                .build()

            creatingText.visibility = View.VISIBLE
//            spinner.visibility=View.VISIBLE

            Handler().postDelayed({
                try {
                    lifecycleScope.launchWhenStarted {
                        bmp = qrGenerator.generateQrCode(
                            com.github.alexzhirkevich.customqrgenerator.QrData.Url(ss),
                            options
                        )
                        ivQrcode.setImageBitmap(bmp)
                    }
                } catch (exc: Exception) {

                }

            }, 500)

            if (dialog!!.isShowing) {
                backgroundExecutor.schedule({
                    // Your code logic goes here

                    dialog?.dismiss()
                }, 2, TimeUnit.SECONDS)

            }


            Handler().postDelayed({
                creatingText.visibility = View.INVISIBLE
//                    spinner.visibility=View.GONE
            }, 2000)

//            if (dialog!!.isShowing) {

//            }

//            try {
//
//
//                lifecycleScope.launch(Dispatchers.IO) {
//                    bmp = qrGenerator.generateQrCode(
//                        QrData.Url(ss),
//                        options
//                    )
//                }
//                runOnUiThread {
//                    ivQrcode.setImageBitmap(bmp)
//                }
//
////                lifecycleScope.launchWhenStarted {
////
////
////                }
//            } catch (exc: Exception) {
//
//            }
        }


//        if(Looper.myLooper() == Looper.getMainLooper()) {
//            // Current Thread is Main Thread.
//            Log.d("checkCurrentThread","UI Thread")
//        }

    }

    private fun showProgressDialog() {


        dialog!!.setCancelable(true)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog!!.window!!.setDimAmount(0f)
        dialog!!.setContentView(R.layout.progress_dialogue)


        dialog!!.show()
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        if (dialog!!.window != null) {
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // This flag is required to set otherwise the setDimAmount method will not show any effect
            dialog!!.window!!.setDimAmount(0.4f) //0 for no dim to 1 for full dim
        }


    }


}




