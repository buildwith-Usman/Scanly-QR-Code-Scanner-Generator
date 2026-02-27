//package scanner.app.scan.qrcode.reader.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.ads.AdSize;
//import com.theartofdev.edmodo.cropper.CropImageView;
//
//import scanner.app.scan.qrcode.reader.R;
//import scanner.app.scan.qrcode.reader.data.constant.Constants;
//import scanner.app.scan.qrcode.reader.utility.AdsManagerQ;
//import scanner.app.scan.qrcode.reader.utility.AppUtils;
//
//
//public class ShareAndCrop extends AppCompatActivity {
//
//
//    CropImageView cropImageView;
//
//    Button shareCrop;
//
//    ImageView back;
//
//    FrameLayout frameLayout;
//    RelativeLayout inLineBannerAds;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share_and_crop);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
//
//        cropImageView = findViewById(R.id.cropImageViewShare);
//        shareCrop = findViewById(R.id.share_crop);
//        back = findViewById(R.id.editBackButtonFromQR);
//        frameLayout = findViewById(R.id.bannerAdsFrame);
//        inLineBannerAds = findViewById(R.id.banner_ads_relative);
//
//        if (!Constants.removeAds) {
//
//
////            AdsManagerQ.getInstance().loadFreshBannerAd(ShareAndCrop.this, frameLayout, inLineBannerAds, AdSize.MEDIUM_RECTANGLE, getResources().getString(R.string.banner_ad_unit_id_inline));
//
//        } else {
//
//            inLineBannerAds.setVisibility(View.GONE);
//        }
//
//        if (Constants.finalBitmap != null) {
//            cropImageView.setImageBitmap(Constants.finalBitmap);
//        }
//
//        shareCrop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (cropImageView.getCroppedImage() != null) {
//
//                    Constants.finalBitmap = cropImageView.getCroppedImage();
//                    AppUtils.shareImage(ShareAndCrop.this, Constants.finalBitmap);
//
//                }
//            }
//        });
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
//}