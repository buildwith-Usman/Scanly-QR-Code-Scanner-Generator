//package scanner.app.scan.qrcode.reader.activity;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.graphics.drawable.ColorDrawable;
//import android.media.ThumbnailUtils;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.CalendarContract;
//import android.provider.MediaStore;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.easystudio.rotateimageview.RotateZoomImageView;
//import com.google.android.gms.ads.AdSize;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.luck.picture.lib.PictureSelector;
//import com.luck.picture.lib.config.PictureConfig;
//import com.luck.picture.lib.config.PictureMimeType;
//import com.luck.picture.lib.entity.LocalMedia;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import scanner.app.scan.qrcode.reader.R;
//import scanner.app.scan.qrcode.reader.adapter.ColorAdapter;
//import scanner.app.scan.qrcode.reader.adapter.LogoAdapter;
//import scanner.app.scan.qrcode.reader.data.BackgroundImages;
//import scanner.app.scan.qrcode.reader.data.constant.Constants;
//import scanner.app.scan.qrcode.reader.data.preference.TemplateModel;
//
//
//public class QRCodeGenerate extends AppCompatActivity {
//
//
//    public static final int PICK_IMAGE = 1;
//
//    public static int CODE_TYPE;
//    public static String inputStr;
//
//    public static Bitmap editBitmap;
//
//    public static boolean templateSel = false;
//
//
//    ImageView backQR;
//    TextView editQR;
//
//
//    ArrayList<BackgroundImages> imagesList;
//    List<TemplateModel> templateImagesList;
//
//    Bitmap logo;
//    String qr_generate;
//    Bitmap bitmapAdapter;
//    String[] colorsTxt;
//    String[] colorsRecycler;
//
//    List<Integer> colors;
//    List<Integer> colorsRecyclerList;
//    List<Integer> logoRecyclerList;
//
//
//    Bitmap templateBitmap;
//
//    ImageView mapOpen;
//    int getLocationCode = 0;
//
//    int activityCode = 0;
//    float latitude, longitude;
//    Date date = null;
//    Date dateEnd = null;
//    Bitmap bitmapSaved;
//
//    RelativeLayout templateTab;
//    String geo = "", add = "", evTitle = "", eveDescription = "", eveLocation = "";
//    RelativeLayout templateQR, logoQR, bgQR, colorQR, customQR, editorQR, addLogoQR, stylishQR;
//
//
//    LinearLayout linearLayout1QR;
//    LinearLayout linearLayout2QR;
//    Button saveQR;
//    ImageView tickQR, logoBitmap, backgroundBitmap, tickTemplate;
//
//
//    ConstraintLayout templateRelative;
//    RotateZoomImageView iv;
////    Button saveButtonTemplate;
//    ImageView background;
//    CardView qrCard, barCodeCardCard;
//    RelativeLayout shareQrOnly;
//    ImageView barCodeCard, barCodebackground, barCodeLogo;
//    CardView qr_code_style;
//    RelativeLayout bar_code_style;
//    Bitmap BarCodeBitmap;
//    RelativeLayout barCodeRelative;
//
//    FrameLayout bannerFrame;
//
//    RelativeLayout adsRelative;
//
//
//    Dialog dialog;
//    CardView cardOfLogo;
//    RelativeLayout shareSquareTemplate;
//
//    TextView categoryTxt, templateTxt;
//    RelativeLayout topView;
//    boolean showDialog = false;
//    private ImageView outputBitmap;
//    private RecyclerView mRecyclerView;
////    private BackgroundAdapter adapter;
//    private ColorAdapter colorAdapter;
//    private LogoAdapter logoAdapter;
//    private Activity mActivity;
//    private Context mContext;
//
//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
//
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = 34;
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qrcode_generate);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
//        initVar();
//        initView();
//        initFunctionality();
//        initListener();
//
//    }
//
//
//    private void initVar() {
//        mActivity = this;
//        mContext = mActivity.getApplicationContext();
//        imagesList = new ArrayList<>();
//        templateImagesList = new ArrayList<>();
//        colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
//        colorsRecycler = getApplicationContext().getResources().getStringArray(R.array.colorsRecycler);
//        dialog = new Dialog(this);
//        colors = new ArrayList<Integer>();
//        colorsRecyclerList = new ArrayList<Integer>();
//        logoRecyclerList = new ArrayList<>();
//
//
//    }
//
//
//    private void initView() {
//
//
//        outputBitmap = findViewById(R.id.outputBitmap);
//
//
//        mapOpen = findViewById(R.id.open_in_map);
//
//
//        saveQR = findViewById(R.id.save_btn);
//
//        backQR = findViewById(R.id.backButtonFromQR);
//
//        editQR = findViewById(R.id.edit);
//
//        logoBitmap = findViewById(R.id.logoBitmap);
//        backgroundBitmap = findViewById(R.id.backgroundBitmap);
//
//
//        templateRelative = findViewById(R.id.temp_rel);
//        iv = findViewById(R.id.rotate);
//
////        saveButtonTemplate = findViewById(R.id.save_btn_template);
//
//        background = findViewById(R.id.templateBackgroundBitmap);
//
//
//        qrCard = findViewById(R.id.qrCard);
//        barCodeCardCard = findViewById(R.id.barcodeqrCard);
//        barCodeCard = findViewById(R.id.barcodeBitmap);
//
//        bar_code_style = findViewById(R.id.barcodeStyle);
//        qr_code_style = findViewById(R.id.qr_style);
//
//        barCodebackground = findViewById(R.id.barcodebackgroundBitmap);
//        barCodeLogo = findViewById(R.id.barcodelogoBitmap);
//
//        barCodeRelative = findViewById(R.id.barCodeRelative);
//        bannerFrame = findViewById(R.id.banner_adsview);
//
//        adsRelative = findViewById(R.id.ads_relative);
//
//        templateQR = findViewById(R.id.template);
//        logoQR = findViewById(R.id.add_logo);
//        colorQR = findViewById(R.id.colorPalette);
//        bgQR = findViewById(R.id.background);
//        customQR = findViewById(R.id.customRel);
//
//        editorQR = findViewById(R.id.editor);
//        linearLayout1QR = findViewById(R.id.l1QR);
//        linearLayout2QR = findViewById(R.id.l2QR);
//
//        templateTab = findViewById(R.id.templatesTab);
//        mRecyclerView = findViewById(R.id.recycler_view);
//
//        tickQR = findViewById(R.id.tick);
//        tickTemplate = findViewById(R.id.tickTemplate);
//
//        categoryTxt = findViewById(R.id.category_txt);
//        templateTxt = findViewById(R.id.template_txt);
//
//        cardOfLogo = findViewById(R.id.logoCard);
//
//        shareQrOnly = findViewById(R.id.share_qr_only);
//
//        shareSquareTemplate = findViewById(R.id.share_square_template);
//
//
//        topView = findViewById(R.id.top_view);
//
//    }
//
//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////frame_container is your layout name in xml file
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//    }
//
//    private void initListener() {
//
//
//        try {
//
//
//            if (iv != null) {
//
//                iv.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//
//                        if (iv.getDrawable() != null) {
//
//                            return iv.onTouch(v, event);
//
//                        }
//                        return true;
//                    }
//                });
//
//
//            }
//
//        } catch (Exception ignored) {
//
//        }
//
//
//        mapOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (getLocationCode == 1) {
//                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    try {
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        Toast.makeText(mActivity, "No Maps Installed", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else if (getLocationCode == 2) {
//
//
//                    if (date == null && dateEnd == null) {
//                        try {
//                            Intent intent = new Intent(Intent.ACTION_INSERT)
//                                    .setData(CalendarContract.Events.CONTENT_URI)
//                                    .putExtra(CalendarContract.Events.TITLE, evTitle)
//                                    .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
//                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
//                                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Toast.makeText(mActivity, "No Maps Installed", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else if (date == null) {
//                        try {
//                            Intent intent = new Intent(Intent.ACTION_INSERT)
//                                    .setData(CalendarContract.Events.CONTENT_URI)
//                                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd.getTime())
//                                    .putExtra(CalendarContract.Events.TITLE, evTitle)
//                                    .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
//                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
//                                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//
//
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (dateEnd == null) {
//                        try {
//                            Intent intent = new Intent(Intent.ACTION_INSERT)
//                                    .setData(CalendarContract.Events.CONTENT_URI)
//                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime())
//                                    .putExtra(CalendarContract.Events.TITLE, evTitle)
//                                    .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
//                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
//                                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Intent intent = new Intent(Intent.ACTION_INSERT)
//                                .setData(CalendarContract.Events.CONTENT_URI)
//                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime())
//                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd.getTime())
//                                .putExtra(CalendarContract.Events.TITLE, evTitle)
//                                .putExtra(CalendarContract.Events.DESCRIPTION, eveDescription)
//                                .putExtra(CalendarContract.Events.EVENT_LOCATION, eveLocation)
//                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//
//                        try {
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Toast.makeText(mActivity, "No Event Installed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                }
//
//            }
//        });
//
//
//        saveQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Constants.finalImageEditor = 0;
//
//
//                try {
//                    if (CODE_TYPE == 1) {
//                        captureImageQRCODE(barCodeRelative);
//
//                    } else {
//                        if (bitmapAdapter != null) {
//                            captureImageQRCODE(qr_code_style);
//                        } else {
//                            captureImageQRCODE(shareQrOnly);
//                        }
//
//                    }
//
//
//                } catch (Exception ignored) {
//
//                }
//
//                Intent in1 = new Intent(QRCodeGenerate.this, EditQRCode.class);
//                startActivity(in1);
//
//
//            }
//        });
//
////        saveButtonTemplate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                captureImageQRCODE(shareSquareTemplate);
////                Constants.finalImageEditor = 0;
////                Intent in1 = new Intent(QRCodeGenerate.this, EditQRCode.class);
////                startActivity(in1);
////
////            }
////        });
//
//
//        backQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.remove_customization),
//                        getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
//                            @Override
//                            public void onPositiveClick() {
//
//                                try {
//                                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//                                    View view1 = mActivity.getCurrentFocus();
////If no view currently has focus, create a new one, just so we can grab a window token from it
//                                    if (view1 == null) {
//                                        view1 = new View(mActivity);
//                                    }
//                                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
//                                } catch (Exception ignored) {
//
//                                }
//                                QRCodeGenerate.templateSel = false;
//                                finish();
//                            }
//
//
//                        });
//
//
//            }
//        });
//
//        adapter.setClickListener(new BackgroundAdapter.ClickListener() {
//            @Override
//            public void onItemClicked(int position) {
//
//
//                if (CODE_TYPE == 1) {
//                    if (position == 0) {
//
//                        if (bitmapAdapter != null) {
//                            barCodebackground.setImageBitmap(null);
//                            bitmapAdapter = null;
//                        }
//
//                    } else {
//                        bitmapAdapter = BitmapFactory.decodeResource(getResources(), imagesList.get(position).getQRBackground());
//                        bitmapAdapter = getRoundedCornerBitmap(bitmapAdapter);
//                        barCodebackground.setImageBitmap(bitmapAdapter);
//
//                    }
//
//                } else {
//                    if (position == 0) {
//
//                        if (bitmapAdapter != null) {
//                            backgroundBitmap.setImageBitmap(null);
//                            bitmapAdapter = null;
//
//                            if (templateSel) {
//
//
//                                iv.setImageBitmap(null);
////                                posterIv.setImageBitmap(null);
//
//                                if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                    iv.setImageBitmap(templateBitmap);
////                                    posterIv.setImageBitmap(templateBitmap);
//
//
//                                } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                    logo = getResizedBitmap(logo, 100);
//                                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
////                        templateBitmap=bitmapSavedOne;
//                                    iv.setImageBitmap(bitmapSavedOne);
////                                    posterIv.setImageBitmap(bitmapSavedOne);
//
//                                } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                                    templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
////                        templateBitmap=bitmapSavedOne;
//                                    iv.setImageBitmap(bitmapSavedOne);
////                                    posterIv.setImageBitmap(bitmapSavedOne);
//
//                                } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                                    logo = getResizedBitmap(logo, 100);
//                                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                                    bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                    bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                                    bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                    Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
////                        templateBitmap=bitmapSavedTWO;
//                                    iv.setImageBitmap(bitmapSavedTWO);
////                                    posterIv.setImageBitmap(bitmapSavedTWO);
//
//                                }
////                                templateRelative.setVisibility(View.VISIBLE);
//
//
//                            }
//                        }
//
//                    } else {
//                        bitmapAdapter = BitmapFactory.decodeResource(getResources(), imagesList.get(position).getQRBackground());
//                        bitmapAdapter = getRoundedCornerBitmap(bitmapAdapter);
//                        backgroundBitmap.setImageBitmap(bitmapAdapter);
//
//                        if (templateSel) {
//
//
//                            if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                                templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                iv.setImageBitmap(templateBitmap);
////                                posterIv.setImageBitmap(templateBitmap);
//
//                            } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                                templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                logo = getResizedBitmap(logo, 100);
//                                Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
////                        templateBitmap=bitmapSavedOne;
//                                iv.setImageBitmap(bitmapSavedOne);
////                                posterIv.setImageBitmap(bitmapSavedOne);
//
//                            } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                                templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                                templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
////                        templateBitmap=bitmapSavedOne;
//                                iv.setImageBitmap(bitmapSavedOne);
////                                posterIv.setImageBitmap(templateBitmap);
//
//                            } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                                logo = getResizedBitmap(logo, 100);
//                                Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                                bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                                bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
////                        templateBitmap=bitmapSavedTWO;
//                                iv.setImageBitmap(bitmapSavedTWO);
////                                posterIv.setImageBitmap(bitmapSavedTWO);
//
//
//                            }
//
//
//                        }
//
//
//                    }
//
//                }
//
//
//            }
//        });
//
//        colorAdapter.setClickListener(new ColorAdapter.ClickListener() {
//            @Override
//            public void onItemClicked(int position) {
//
//
//                CodeGenerator.setBLACK(colorsRecyclerList.get(position));
//                generateCode(qr_generate);
//
//
//            }
//        });
//
//        logoAdapter.setClickListener(new LogoAdapter.ClickListener() {
//            @Override
//            public void onItemClicked(int position) {
//
//
//                if (position == 0) {
//                    cardOfLogo.setVisibility(View.GONE);
//
//                    logoBitmap.setImageBitmap(null);
//
//                    logo = null;
//                    if (templateSel) {
//
//                        iv.setImageBitmap(null);
//
//
//                        if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            iv.setImageBitmap(templateBitmap);
//
//                        } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                            iv.setImageBitmap(bitmapSavedOne);
//
//                        } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                            templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
//                            iv.setImageBitmap(bitmapSavedOne);
//
//                        } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
//                            iv.setImageBitmap(bitmapSavedTWO);
//
//                        }
//
//                    }
//                } else if (position == 1) {
//                    galleryImage();
//                } else {
//                    cardOfLogo.setVisibility(View.VISIBLE);
//                    logo = BitmapFactory.decodeResource(getResources(),
//                            logoRecyclerList.get(position));
//
//                    logoBitmap.setImageResource(logoRecyclerList.get(position));
//
//
//                    if (templateSel) {
//
//                        if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(templateBitmap)
//                                    .centerCrop()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
////                                    iv.setImageBitmap(templateBitmap);
//
//                        } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
////                        templateBitmap=bitmapSavedOne;
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedOne)
//                                    .centerCrop()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
////                                    iv.setImageBitmap(bitmapSavedOne);
//
//                        } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                            templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
////                        templateBitmap=bitmapSavedOne;
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedOne)
//                                    .centerCrop()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
////                                    iv.setImageBitmap(bitmapSavedOne);
//
//                        } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
////                        templateBitmap=bitmapSavedTWO;
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedTWO)
//                                    .centerCrop()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
////                                    iv.setImageBitmap(bitmapSavedTWO);
//
//                        }
//
//                    }
//                }
//
//
//            }
//        });
//
//
//        templateQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (CODE_TYPE != 1) {
//
//
//                    if (!showDialog) {
//                        try {
//
//                            dialog.setCancelable(true);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            dialog.getWindow().setDimAmount(0);
//                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//                            dialog.setContentView(R.layout.drag_n_drop);
//
//                            dialog.show();
//
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                            if (dialog.getWindow() != null) {
//                                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // This flag is required to set otherwise the setDimAmount method will not show any effect
//                                dialog.getWindow().setDimAmount(0.7f); //0 for no dim to 1 for full dim
//                            }
//                            showDialog=true;
//
//                        } catch (Exception ignored) {
//
//                        }
//                    }
//
//
//                }
//
//
//                linearLayout1QR.setVisibility(View.GONE);
//                linearLayout2QR.setVisibility(View.GONE);
//                editorQR.setVisibility(View.GONE);
//                categoryTxt.setVisibility(View.GONE);
//                templateTxt.setVisibility(View.VISIBLE);
//                saveQR.setVisibility(View.GONE);
//                tickQR.setVisibility(View.GONE);
//                tickTemplate.setVisibility(View.VISIBLE);
//                templateTab.setVisibility(View.VISIBLE);
//                editQR.setText(getResources().getString(R.string.templates));
//                topView.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
////                saveButtonTemplate.setVisibility(View.VISIBLE);
//
//                if (templateSel) {
//                    templateRelative.setVisibility(View.VISIBLE);
//                    qr_code_style.setVisibility(View.GONE);
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                    saveQR.setVisibility(View.GONE);
//                    iv.setEnabled(true);
//                    iv.setImageBitmap(null);
//                } else {
//                    templateRelative.setVisibility(View.GONE);
//                    qr_code_style.setVisibility(View.VISIBLE);
////                    saveButtonTemplate.setVisibility(View.GONE);
//                    saveQR.setVisibility(View.VISIBLE);
//                    iv.setEnabled(false);
//                    iv.setImageBitmap(null);
//                }
//
//
//                if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                    iv.setImageBitmap(templateBitmap);
//
//                } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                    logo = getResizedBitmap(logo, 100);
//                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                    iv.setImageBitmap(bitmapSavedOne);
//
//                } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                    templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                    templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
//                    iv.setImageBitmap(bitmapSavedOne);
//
//                } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                    logo = getResizedBitmap(logo, 100);
//                    Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                    bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                    bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                    bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                    Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
//                    iv.setImageBitmap(bitmapSavedTWO);
//
//                }
//
//
//            }
//        });
//
//        colorQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mRecyclerView.setAdapter(colorAdapter);
//
//                linearLayout1QR.setVisibility(View.GONE);
//                linearLayout2QR.setVisibility(View.GONE);
//                editorQR.setVisibility(View.GONE);
//                categoryTxt.setVisibility(View.VISIBLE);
//                categoryTxt.setText(getResources().getString(R.string.solid_color));
//                templateTxt.setVisibility(View.GONE);
//                saveQR.setVisibility(View.GONE);
//                tickQR.setVisibility(View.VISIBLE);
//                tickTemplate.setVisibility(View.GONE);
//                templateTab.setVisibility(View.GONE);
//                editQR.setText(getResources().getString(R.string.solid_color));
//                topView.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.VISIBLE);
////                saveButtonTemplate.setVisibility(View.GONE);
//
//                if (templateSel) {
//                    templateRelative.setVisibility(View.VISIBLE);
//                    qr_code_style.setVisibility(View.GONE);
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                    saveQR.setVisibility(View.GONE);
//
//                } else {
//                    templateRelative.setVisibility(View.GONE);
//
//
//                    if (CODE_TYPE == 1) {
//                        qr_code_style.setVisibility(View.GONE);
//                    } else {
//                        qr_code_style.setVisibility(View.VISIBLE);
//                    }
//
////                    saveButtonTemplate.setVisibility(View.GONE);
//                    saveQR.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        bgQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                mRecyclerView.setAdapter(adapter);
//
//                background.invalidate();
//
//                linearLayout1QR.setVisibility(View.GONE);
//                linearLayout2QR.setVisibility(View.GONE);
//                editorQR.setVisibility(View.GONE);
//                categoryTxt.setVisibility(View.VISIBLE);
//                categoryTxt.setText(getResources().getString(R.string.background));
//                templateTxt.setVisibility(View.GONE);
//                saveQR.setVisibility(View.GONE);
//                tickQR.setVisibility(View.VISIBLE);
//                tickTemplate.setVisibility(View.GONE);
//                templateTab.setVisibility(View.GONE);
//                editQR.setText(getResources().getString(R.string.background));
//                topView.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.VISIBLE);
////                saveButtonTemplate.setVisibility(View.GONE);
//
//                if (templateSel) {
//                    templateRelative.setVisibility(View.VISIBLE);
//                    qr_code_style.setVisibility(View.GONE);
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                    saveQR.setVisibility(View.GONE);
//                } else {
//                    templateRelative.setVisibility(View.GONE);
//                    qr_code_style.setVisibility(View.VISIBLE);
////                    saveButtonTemplate.setVisibility(View.GONE);
//                    saveQR.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        logoQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mRecyclerView.setAdapter(logoAdapter);
//
//                linearLayout1QR.setVisibility(View.GONE);
//                linearLayout2QR.setVisibility(View.GONE);
//                editorQR.setVisibility(View.GONE);
//                categoryTxt.setText(getResources().getString(R.string.logo));
//                templateTxt.setVisibility(View.GONE);
//                categoryTxt.setVisibility(View.VISIBLE);
//                saveQR.setVisibility(View.GONE);
//                tickQR.setVisibility(View.VISIBLE);
//                tickTemplate.setVisibility(View.GONE);
//                templateTab.setVisibility(View.GONE);
//                editQR.setText(getResources().getString(R.string.logo));
//                topView.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.VISIBLE);
////                saveButtonTemplate.setVisibility(View.GONE);
//
////                saveButtonTemplate.setVisibility(View.GONE);
//
//                if (templateSel) {
//                    templateRelative.setVisibility(View.VISIBLE);
//                    qr_code_style.setVisibility(View.GONE);
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                    saveQR.setVisibility(View.GONE);
//                } else {
//                    templateRelative.setVisibility(View.GONE);
//                    qr_code_style.setVisibility(View.VISIBLE);
////                    saveButtonTemplate.setVisibility(View.GONE);
//                    saveQR.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        linearLayout2QR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent fancyQR = new Intent(QRCodeGenerate.this, CustomQRCode.class);
//                fancyQR.putExtra(PrefKey.FancyQRStr, qr_generate);
//                startActivity(fancyQR);
//            }
//        });
//
//        editorQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (checkWritePermission()) {
//                    Constants.isSelectingFile=true;
//                    galleryImageEditor();
//                }
//
//
//            }
//        });
//
//
//        tickQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linearLayout1QR.setVisibility(View.VISIBLE);
//                linearLayout2QR.setVisibility(View.VISIBLE);
//                editorQR.setVisibility(View.VISIBLE);
//                saveQR.setVisibility(View.VISIBLE);
//                tickQR.setVisibility(View.GONE);
//                tickTemplate.setVisibility(View.GONE);
//                topView.setVisibility(View.GONE);
//                mRecyclerView.setVisibility(View.GONE);
//                templateTab.setVisibility(View.GONE);
//                editQR.setText(getResources().getString(R.string.edit));
//
//                if (templateSel) {
//                    saveQR.setVisibility(View.GONE);
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                } else {
//
//                    if (CODE_TYPE == 1) {
//                        linearLayout2QR.setVisibility(View.GONE);
//                    } else {
//                        linearLayout2QR.setVisibility(View.VISIBLE);
//                    }
//                    saveQR.setVisibility(View.VISIBLE);
////                    saveButtonTemplate.setVisibility(View.GONE);
//                }
//
//            }
//        });
//
//        tickTemplate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                linearLayout1QR.setVisibility(View.VISIBLE);
//                linearLayout2QR.setVisibility(View.VISIBLE);
//                editorQR.setVisibility(View.VISIBLE);
//                topView.setVisibility(View.GONE);
//                mRecyclerView.setVisibility(View.GONE);
//                tickQR.setVisibility(View.GONE);
//                tickTemplate.setVisibility(View.GONE);
//                templateTab.setVisibility(View.GONE);
//                editQR.setText(getResources().getString(R.string.edit));
//
//                if (templateSel) {
////                    saveButtonTemplate.setVisibility(View.VISIBLE);
//                    saveQR.setVisibility(View.GONE);
//                } else {
//                    saveQR.setVisibility(View.VISIBLE);
////                    saveButtonTemplate.setVisibility(View.GONE);
//                }
//
//            }
//        });
//
//    }
//
//
//    private void initAdapter() {
//
//        adapter = new BackgroundAdapter(mContext, imagesList);
//        colorAdapter = new ColorAdapter(mContext, colorsRecyclerList);
//        logoAdapter = new LogoAdapter(mContext, logoRecyclerList);
//
//        mRecyclerView.setLayoutManager((new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)));
//        mRecyclerView.setNestedScrollingEnabled(true);
//        mRecyclerView.setHasFixedSize(true);
//
//
//    }
//
//
//    @Override
//    public void onBackPressed() {
//
//        DialogUtils.showDialogPrompt(mActivity, null, getString(R.string.remove_customization),
//                getString(R.string.yes), getString(R.string.no), true, new DialogUtils.DialogActionListener() {
//                    @Override
//                    public void onPositiveClick() {
//
//                        QRCodeGenerate.templateSel = false;
//                        finish();
//                    }
//
//
//                });
//
//    }
//
//    private void galleryImageEditor() {
//
////        Intent intent = new Intent();
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        //set intent type to image
//        intent.setType("image/*");
////        galleryActivityResultLauncher.launch(intent);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//    }
//
//
//    private void captureImageQRCODE(View v) {
//        // TODO Auto-generated method stub
//
//
//        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
//                Bitmap.Config.ARGB_8888);
//
//        bitmap = ThumbnailUtils.extractThumbnail(bitmap, v.getWidth(),
//                v.getHeight());
//
//        Canvas b = new Canvas(bitmap);
//        v.draw(b);
//
//        Constants.finalBitmap = bitmap;
//
//
//    }
//
//
//    public Bitmap bitmapOverlayToCenter(Bitmap bitmap1, Bitmap overlayBitmap) {
//        int bitmap1Width = bitmap1.getWidth();
//        int bitmap1Height = bitmap1.getHeight();
//        int bitmap2Width = overlayBitmap.getWidth();
//        int bitmap2Height = overlayBitmap.getHeight();
//
//        float marginLeft = (float) (bitmap1Width * 0.5 - bitmap2Width * 0.5);
//        float marginTop = (float) (bitmap1Height * 0.5 - bitmap2Height * 0.5);
//
//
//        Bitmap finalBitmap = Bitmap.createBitmap(bitmap1Width, bitmap1Height, bitmap1.getConfig());
//        Canvas canvas = new Canvas(finalBitmap);
//        canvas.drawBitmap(bitmap1, new Matrix(), null);
//        canvas.drawBitmap(overlayBitmap, marginLeft, marginTop, null);
//        return finalBitmap;
//    }
//
//    private void generateCode(final String input) {
//        CodeGenerator codeGenerator = new CodeGenerator();
//        if (CODE_TYPE == 1) {
//
//            codeGenerator.generateBarFor(input);
//
//        } else {
//            codeGenerator.generateQRFor(input);
//            Constants.finalImageEditor = 0;
//        }
//
//        codeGenerator.setResultListener(new CodeGenerator.ResultListener() {
//            @Override
//            public void onResult(Bitmap bitmap) {
//                //((BitmapDrawable)outputBitmap.getDrawable()).getBitmap().recycle();
//
//
//                if (CODE_TYPE == 1) {
//
//                    qr_code_style.setVisibility(View.GONE);
//                    bar_code_style.setVisibility(View.VISIBLE);
//
//                    inputStr = "barcode:" + input;
//
//                    logoQR.setVisibility(View.GONE);
//                    bgQR.setVisibility(View.GONE);
//                    colorQR.setVisibility(View.VISIBLE);
//                    linearLayout2QR.setVisibility(View.GONE);
//
//                    try {
//                        Glide.with(mContext)
//                                .load(bitmap)
//                                .centerInside()
//                                // resizes width to 100, preserves original height, does not respect aspect ratio
//                                .into(barCodeCard);
//
//                        bitmapSaved = bitmap;
//                    } catch (Exception ignored) {
//
//                    }
//
//
//                } else {
//
//
//                    qr_code_style.setVisibility(View.VISIBLE);
//                    bar_code_style.setVisibility(View.GONE);
//                    inputStr = input;
//
//                    try {
//                        bitmapSaved = bitmap;
//                        templateBitmap = bitmap;
//                        Glide.with(mContext)
//                                .load(bitmap)
//                                .centerInside()
//                                // resizes width to 100, preserves original height, does not respect aspect ratio
//                                .into(outputBitmap);
//
//                    } catch (Exception ignored) {
//
//                    }
//
//
//                    if (templateSel) {
//                        qr_code_style.setVisibility(View.GONE);
//
//                        if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(templateBitmap)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
//
//
//                        } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedOne)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
//
//
//                        } else if (logo == null && templateBitmap != null) {
//
//                            templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
//
//
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedOne)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
//
//
//                        } else if (logo != null && templateBitmap != null) {
//
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                            Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
//
//
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmapSavedTWO)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
//
//
//                        }
//
//
//                    }
//
//                    if (Regular.flag) {
//
//                        if (CODE_TYPE != 1) {
//
//                            try {
//
//                                dialog.setCancelable(true);
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                dialog.getWindow().setDimAmount(0);
//                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//                                dialog.setContentView(R.layout.drag_n_drop);
//
//                                dialog.show();
//
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                                if (dialog.getWindow() != null) {
//                                    dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // This flag is required to set otherwise the setDimAmount method will not show any effect
//                                    dialog.getWindow().setDimAmount(0.7f); //0 for no dim to 1 for full dim
//                                }
//
//
//                            } catch (Exception ignored) {
//
//                            }
//
//
//                            saveQR.setVisibility(View.GONE);
////                            saveButtonTemplate.setVisibility(View.VISIBLE);
//                            templateRelative.setVisibility(View.VISIBLE);
//
//                            qr_code_style.setVisibility(View.GONE);
//
//
//                            Glide.with(mContext)
//                                    .load(Regular.templatePosition)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(background);
//
//
//                            Glide.with(mContext)
//                                    .asBitmap()
//                                    .load(bitmap)
//                                    .centerInside()
//                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                    .into(iv);
//
//
//                            templateSel = true;
//
//
//                        }
//                    }
//                    Regular.flag = false;
//                }
//
//
//            }
//        });
//        codeGenerator.execute();
//    }
//
//
//    public void dismissProgressDialog() {
//
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//
//        }
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        dismissProgressDialog();
//        super.onDestroy();
//    }
//
//    private void galleryImage() {
//
//
//        PictureSelector.create(QRCodeGenerate.this)
//                .openGallery(PictureMimeType.ofImage())
//                .isCamera(false)
//                .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
//                .selectionMode(PictureConfig.SINGLE)
//                .previewImage(false)
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//
//    }
//
//    //for select of contact
//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//
//        if (resultCode != RESULT_CANCELED) {
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
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                InputStream baseStream;
//                                InputStream imageStream;
//                                try {
////                            Toast.makeText(mActivity, selectedImage+"", Toast.LENGTH_SHORT).show();
//                                    //getting the image
//                                    baseStream = getContentResolver().openInputStream(selectedImage);
//                                    imageStream = getContentResolver().openInputStream(selectedImage);
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
//                                    Bitmap bMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                                    Bitmap base = BitmapFactory.decodeStream(baseStream);
//                                    //set options for resize image
//                                    BitmapFactory.Options options = new BitmapFactory.Options();
//                                    options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))
//
//                                    //decoding bitmap
//                                    bMap = BitmapFactory.decodeStream(imageStream, null, options); //get image with options
//                                    bMap = getRoundedCornerBitmap(bMap);
//                                    logo = bMap;
//
//                                    cardOfLogo.setVisibility(View.VISIBLE);
//                                    Glide.with(mContext)
//                                            .asBitmap()
//                                            .load(bMap)
//                                            .centerCrop()
//                                            // resizes width to 100, preserves original height, does not respect aspect ratio
//                                            .into(logoBitmap);
//
//
//                                    if (templateSel) {
//
//                                        if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(templateBitmap)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            logo = getResizedBitmap(logo, 100);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedOne)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                                            templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedOne)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                                            logo = getResizedBitmap(logo, 100);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                            bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                            Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedTWO)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        }
//
//                                    }
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
//                                    Bitmap myBitmap = BitmapFactory.decodeFile(result.get(0).getPath());
//
//                                    cardOfLogo.setVisibility(View.VISIBLE);
//                                    logo = myBitmap;
//
//
//                                    Glide.with(mContext)
//                                            .asBitmap()
//                                            .load(myBitmap)
//                                            .centerCrop()
//                                            // resizes width to 100, preserves original height, does not respect aspect ratio
//                                            .into(logoBitmap);
//
//
//                                    if (templateSel) {
//
//                                        if (logo == null && bitmapAdapter == null && templateBitmap != null) {
//
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(templateBitmap)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo != null && templateBitmap != null && bitmapAdapter == null) {
//
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            logo = getResizedBitmap(logo, 100);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedOne)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo == null && templateBitmap != null && bitmapAdapter != null) {
//
//                                            templateBitmap = getResizedBitmap(templateBitmap, (int) (bitmapAdapter.getHeight() / 1.5));
//                                            templateBitmap = getRoundedCornerBitmap(templateBitmap);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapAdapter, templateBitmap);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedOne)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        } else if (logo != null && templateBitmap != null && bitmapAdapter != null) {
//
//                                            logo = getResizedBitmap(logo, 100);
//                                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(templateBitmap, logo);
//                                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                            bitmapSavedOne = getResizedBitmap(bitmapSavedOne, (int) (bitmapAdapter.getHeight() / 1.5));
//                                            bitmapSavedOne = getRoundedCornerBitmap(bitmapSavedOne);
//                                            Bitmap bitmapSavedTWO = bitmapOverlayToCenter(bitmapAdapter, bitmapSavedOne);
//
//                                            Glide.with(mContext)
//                                                    .asBitmap()
//                                                    .load(bitmapSavedTWO)
//                                                    .centerCrop()
//                                                    // resizes width to 100, preserves original height, does not respect aspect ratio
//                                                    .into(iv);
//
//
//                                        }
//
//                                    }
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
//                    }
//
//                }
//
//
//            } else if (reqCode == PICK_IMAGE) {
//                if (data != null) {
//
//                    Uri selectedImage = null;
//                    try {
//                        selectedImage = data.getData();
//                    } catch (Exception e) {
//                        Toast.makeText(mActivity, "File Corrupted", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }
//
//                    InputStream baseStream = null;
//                    InputStream imageStream = null;
//                    try {
//                        //getting the image
//                        baseStream = mActivity.getContentResolver().openInputStream(selectedImage);
//                        imageStream = mActivity.getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        Toast.makeText(mContext, getResources().getString(R.string.error_file), Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                        return;
//                    }
//
//                    // ChecksumException
//                    try {
//                        //decoding bitmap for get width
//                        Bitmap base = BitmapFactory.decodeStream(baseStream);
//                        //set options for resize image
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))
//
//                        //decoding bitmap
//
//
//                        Constants.editorBitmap = BitmapFactory.decodeStream(imageStream, null, options);
//
//                        if (logo == null && bitmapSaved != null) {
//
//                            if (CODE_TYPE == 1) {
//
//                                barCodeRelative.setDrawingCacheEnabled(true);
//
//                                BarCodeBitmap = barCodeRelative.getDrawingCache();
//                                BarCodeBitmap = Bitmap.createBitmap(barCodeRelative.getDrawingCache());
//
//                                barCodeRelative.setDrawingCacheEnabled(false);
//
//                                if (BarCodeBitmap != null) {
//                                    editBitmap = BarCodeBitmap;
//
//                                } else {
//                                    editBitmap = bitmapSaved;
//                                }
//
//                            } else {
//                                bitmapSaved = getRoundedCornerBitmap(bitmapSaved);
//                                editBitmap = bitmapSaved;
//                            }
//
//                        } else if (logo != null && bitmapSaved != null) {
//
//                            bitmapSaved = getRoundedCornerBitmap(bitmapSaved);
//                            logo = getResizedBitmap(logo, 100);
//                            Bitmap bitmapSavedOne = bitmapOverlayToCenter(bitmapSaved, logo);
//                            editBitmap = bitmapSavedOne;
//
//
//                        }
//
//                        startActivity(new Intent(mContext, CustomEditorActivity.class));
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }
//
//
//    }
//
//
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
//
//
//    private boolean checkWritePermission() {
//        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    Constants.PERMISSION_REQ);
//        } else {
//            return true;
//        }
//        return false;
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Constants.PERMISSION_REQ) {
//            for (int i = 0; i < permissions.length; i++) {
//                String permission = permissions[i];
//                int grantResult = grantResults[i];
//
//                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
//
//                        galleryImageEditor();
//
//
//                    } else {
//                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
//                    }
//                }
//            }
//        }
//    }
//
//    private void initFunctionality() {
//
//
//        Bundle paramsCreateDoing = new Bundle();
//        paramsCreateDoing.putString("QRDoing", "1");
//
//        try {
//            FirebaseAnalytics.getInstance(mContext).logEvent("QRCreateDoing", paramsCreateDoing);
//        } catch (Exception ignored) {
//
//        }
//
//        try {
//            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//            View view = mActivity.getCurrentFocus();
////If no view currently has focus, create a new one, just so we can grab a window token from it
//            if (view == null) {
//                view = new View(mActivity);
//            }
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        } catch (Exception ignored) {
//
//        }
//
//
//        if (!Constants.removeAds) {
//
//            AdsManagerQ.getInstance().loadFreshBannerAd(mContext, bannerFrame, adsRelative, AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, 350), getResources().getString(R.string.banner_ad_editors_sticky_unit_id));
//
//        } else {
//            adsRelative.setVisibility(View.GONE);
//        }
//
//        Constants.selectFromMain = false;
//        Fragment fragment;
////        fragment = new TemplateFragment();
//        fragment = new TemplateFragment(false);
//        loadFragment(fragment);
//
//        Configuration config = getResources().getConfiguration();
//        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//            //in Right To Left layout
//            backQR.setRotation(180);
//        }
//
//        BasicBackgroundColors();
//        initLogo();
//
//
//        for (String s : colorsTxt) {
//            int newColor = Color.parseColor(s);
//            colors.add(newColor);
//        }
//
//        for (String s : colorsRecycler) {
//            colorsRecyclerList.add(Color.parseColor(s));
//        }
//
//
//        Intent mIntent = getIntent();
//        qr_generate = mIntent.getStringExtra("generateQR");
//        CODE_TYPE = mIntent.getIntExtra("codeType", 0);
//        getLocationCode = mIntent.getIntExtra("location", 0);
//        activityCode = mIntent.getIntExtra("activity", 0);
//
//
//        if (CODE_TYPE == 1) {
//
//            templateQR.setVisibility(View.GONE);
//            bgQR.setVisibility(View.GONE);
//            logoQR.setVisibility(View.GONE);
//            colorQR.setVisibility(View.VISIBLE);
////            saveButtonTemplate.setVisibility(View.GONE);
//            templateSel = false;
//        }
//
//        initAdapter();
//
//
//        CodeGenerator.setBLACK(Color.BLACK);
//        generateCode(qr_generate);
//
//        ArrayList<String> previousResult = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST_OF_CREATED);
//
//        if (CODE_TYPE == 1) {
//            String barCodeStr = qr_generate;
//            barCodeStr = "barCodeType:" + Constants.format + ";" + "barcode:" + qr_generate + ";";
//            previousResult.add(barCodeStr);
//        } else {
//            previousResult.add(qr_generate);
//        }
//
//        AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST_OF_CREATED, previousResult);
//
//        //save date of generation
//        String currentDate;
//        if (Locale.getDefault().equals(Locale.US)) {
//            currentDate = new SimpleDateFormat("MM.dd.yyyy HH:mm").format(Calendar.getInstance().getTime());
//        } else {
//            currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
//        }
//
//        ArrayList<String> previousDate = AppPreference.getInstance(mContext).getStringArray(PrefKey.DATE_LIST_OF_CREATED);
//        previousDate.add(currentDate);
//
//        AppPreference.getInstance(mContext).setStringArray(PrefKey.DATE_LIST_OF_CREATED, previousDate);
//
//        //saving color (standard black)
////        ArrayList<String> previousColor = AppPreference.getInstance(mContext).getStringArray(PrefKey.COLOR_LIST_OF_CREATED);
////        previousColor.add(Integer.toString(Color.BLACK));
////        AppPreference.getInstance(mContext).setStringArray(PrefKey.COLOR_LIST_OF_CREATED, previousColor);
//
//
//        if (getLocationCode == 1) {
//            mapOpen.setVisibility(View.VISIBLE);
//
//            if (qr_generate.contains("?")) {
//                try {
//
//
//                    Matcher m = Pattern.compile("geo:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                    while (m.find()) {
//                        geo = m.group(1);
//                        geo = geo.substring(0, geo.indexOf("?"));
//
//
//                    }
//                    m = Pattern.compile("q=(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                    while (m.find()) {
//                        add = m.group(1);
//                        add = add.substring(0, add.indexOf(";"));
//
//                    }
//                } catch (Exception ignored) {
//
//
//                }
//            }
//
//            String[] latLongArray = geo.split(",");
//
//            String latS = latLongArray[0];
//            String longS = latLongArray[1];
//            latS = latS.trim();
//            longS = longS.trim();
//
//            try {
//                latitude = Float.parseFloat(latS);
//                longitude = Float.parseFloat(longS);
//            } catch (NumberFormatException ex) { // handle your exception
//
//            }
//
//
//        } else if (getLocationCode == 2) {
//            mapOpen.setVisibility(View.VISIBLE);
//
//            mapOpen.setImageResource(R.drawable.ic_event_icon);
//            qr_generate = qr_generate.replace("\n", ";");
//            String beginEvent = "", summary = "", description = "", location = "", dtstart = "", dtend = "";
//            try {
//
//
//                Matcher m = Pattern.compile("BEGIN:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//
//                    beginEvent = m.group(1);
//                    if (qr_generate.contains("BEGIN:") || qr_generate.contains("begin:")) {
////                        beginEvent = beginEvent.substring(0, beginEvent.indexOf("\n"));
//
//                        beginEvent = beginEvent.substring(0, beginEvent.indexOf(";"));
//
//                    }
//
//                }
//                m = Pattern.compile("SUMMARY:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//                    summary = m.group(1);
//                    if (qr_generate.contains("SUMMARY:") || qr_generate.contains("summary:")) {
//
//                        summary = summary.substring(0, summary.indexOf(";"));
//
//
//                    }
//                }
//                m = Pattern.compile("DTSTART:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//                    dtstart = m.group(1);
//                    if (qr_generate.contains("DTSTART:") || qr_generate.contains("dtstart:")) {
//
//                        dtstart = dtstart.substring(0, dtstart.indexOf(";"));
//
//
//                    }
//                }
//                m = Pattern.compile("DTEND:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//                    dtend = m.group(1);
//                    if (qr_generate.contains("DTEND:") || qr_generate.contains("dtend:")) {
//
//                        dtend = dtend.substring(0, dtend.indexOf(";"));
//
//
//                    }
//                }
//                m = Pattern.compile("LOCATION:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//                    location = m.group(1);
//                    if (qr_generate.contains("LOCATION:") || qr_generate.contains("location:")) {
//
//                        location = location.substring(0, location.indexOf(";"));
//
//
//                    }
//                }
//                m = Pattern.compile("DESCRIPTION:(.*)", Pattern.CASE_INSENSITIVE).matcher(qr_generate);
//                while (m.find()) {
//                    description = m.group(1);
//                    if (qr_generate.contains("DESCRIPTION:") || qr_generate.contains("description:")) {
//
//                        description = description.substring(0, description.indexOf(";"));
//
//
//                    }
//                }
//
//
//            } catch (Exception ignored) {
//
//            }
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//
//            try {
//                date = formatter.parse(dtstart);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//                dateEnd = formatter.parse(dtend);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//
//                e.printStackTrace();
//            }
//            evTitle = summary;
//            eveDescription = description;
//            eveLocation = location;
//
//        } else {
//            mapOpen.setVisibility(View.GONE);
//        }
//
//    }
//
//    private void initLogo() {
//
//        logoRecyclerList.add(R.drawable.traffic_icon);
//        logoRecyclerList.add(R.drawable.add_image_icon);
//        for (int i = 1; i <= 21; i++) {
//            int img = getResources().getIdentifier("logo_qr_" + i, "drawable", getPackageName());
//            logoRecyclerList.add(img);
//        }
//
//
//    }
//
//
//    private void BasicBackgroundColors() {
//
//        imagesList.add(new BackgroundImages(R.drawable.ic_sharp_do_not_disturb_alt_24, R.drawable.qr_color_1));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_1, R.drawable.qr_color_1));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_2, R.drawable.qr_color_2));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_3, R.drawable.qr_color_3));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_4, R.drawable.qr_color_4));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_5, R.drawable.qr_color_5));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_6, R.drawable.qr_color_6));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_7, R.drawable.qr_color_7));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_8, R.drawable.qr_color_8));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_9, R.drawable.qr_color_9));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_10, R.drawable.qr_color_10));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_11, R.drawable.qr_color_11));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_12, R.drawable.qr_color_12));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_13, R.drawable.qr_color_13));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_14, R.drawable.qr_color_1));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_15, R.drawable.qr_color_2));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_16, R.drawable.qr_color_3));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_17, R.drawable.qr_color_4));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_18, R.drawable.qr_color_5));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_19, R.drawable.qr_color_6));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_20, R.drawable.qr_color_7));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_21, R.drawable.qr_color_8));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_22, R.drawable.qr_color_9));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_23, R.drawable.qr_color_10));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_24, R.drawable.qr_color_11));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_25, R.drawable.qr_color_12));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_26, R.drawable.qr_color_13));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_27, R.drawable.qr_color_1));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_28, R.drawable.qr_color_2));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_29, R.drawable.qr_color_3));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_30, R.drawable.qr_color_4));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_31, R.drawable.qr_color_5));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_32, R.drawable.qr_color_6));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_33, R.drawable.qr_color_7));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_34, R.drawable.qr_color_8));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_35, R.drawable.qr_color_9));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_36, R.drawable.qr_color_10));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_37, R.drawable.qr_color_11));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_38, R.drawable.qr_color_12));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_39, R.drawable.qr_color_13));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_40, R.drawable.qr_color_1));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_41, R.drawable.qr_color_2));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_42, R.drawable.qr_color_3));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_43, R.drawable.qr_color_4));
//        imagesList.add(new BackgroundImages(R.drawable.qr_background_44, R.drawable.qr_color_5));
//
//    }
//
//
//}