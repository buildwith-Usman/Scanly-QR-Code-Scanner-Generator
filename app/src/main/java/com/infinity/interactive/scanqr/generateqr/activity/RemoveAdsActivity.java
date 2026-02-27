//package scanner.app.scan.qrcode.reader.activity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.billingclient.api.AcknowledgePurchaseParams;
//import com.android.billingclient.api.BillingClient;
//import com.android.billingclient.api.BillingClientStateListener;
//import com.android.billingclient.api.BillingFlowParams;
//import com.android.billingclient.api.BillingResult;
//import com.android.billingclient.api.ProductDetails;
//import com.android.billingclient.api.Purchase;
//import com.android.billingclient.api.QueryProductDetailsParams;
//import com.google.common.collect.ImmutableList;
//
//
//import scanner.app.scan.qrcode.reader.R;
//import scanner.app.scan.qrcode.reader.data.preference.AppPreference;
//import scanner.app.scan.qrcode.reader.data.preference.PrefKey;
//import scanner.app.scan.qrcode.reader.utility.ActivityUtils;
//
//
//public class RemoveAdsActivity extends AppCompatActivity {
//
//
//
//    TextView removeAds;
//    ImageView backButton;
//    TextView txtPrice;
//    TextView restoreInApps;
////    FirebaseAnalytics firebaseAnalytics;
//    Bundle params = new Bundle();
//    BillingClient billingClient;
//
////        String productKey = "android.test.purchased";
//    String productKey = "removeads";
//
//    boolean firstRun;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_remove_ads);
//        initViews();
////        firebaseEvents();
//        initializeBillingClient();
//        establishConnection();
//        initListener();
//
//    }
//
////    private void firebaseEvents() {
////
////        try {
////            params.putString("InApps", "Purchase");
////            firebaseAnalytics.logEvent("InAppsPurchases", params);
////        } catch (Exception ignored) {
////
////        }
////
////    }
//
//    void initViews() {
//
//        removeAds = findViewById(R.id.remove_ads);
//        backButton = findViewById(R.id.backButton);
//        txtPrice = findViewById(R.id.txt1);
//        restoreInApps = findViewById(R.id.restore);
////        firebaseAnalytics = FirebaseAnalytics.getInstance(RemoveAdsActivity.this);
//        firstRun = AppPreference.getInstance(this).getBoolean(PrefKey.ActivityFirstRun, true);
//    }
//
//    void initializeBillingClient() {
//        //Initialize a BillingClient with PurchasesUpdatedListener onCreate method
//
//        Log.d("InAPPS", "Initialization");
//        billingClient = BillingClient.newBuilder(getApplicationContext())
//                .setListener((billingResult, list) -> {
//                    Log.d("InAPPS01", billingResult + " " + list + " Initialization");
//
//                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
//                        for (Purchase purchase : list) {
////                            verifySubPurchase(purchase);
//                            verifyPayment(purchase);
//                        }
//                    }
//
//                })
//                .enablePendingPurchases()
//                .build();
//
//
//    }
//
//    private void initListener() {
//
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (firstRun) {
//                    startActivity();
//                } else {
//                    finish();
//                }
////                showAdIfAvailable();
//            }
//        });
//
//
//        restoreInApps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkSubscription();
//            }
//        });
//
//    }
//
//
//    void establishConnection() {
//
//        billingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    // The BillingClient is ready. You can query purchases here.
//                    showProducts();
//                }
//            }
//
//            @Override
//            public void onBillingServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//                establishConnection();
//            }
//        });
//    }
//
//
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//
//        if (firstRun) {
//            startActivity();
//        } else {
//            finish();
//        }
//    }
//
//    public void startActivity() {
//        AppPreference.getInstance(RemoveAdsActivity.this).setBoolean(PrefKey.ActivityFirstRun, false);
//        Intent scan = new Intent(RemoveAdsActivity.this, DashboardActivity.class);
//        scan.putExtra("fragmentVal", 0);
////        AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
//        scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(scan);
//    }
//
//    @SuppressLint("SetTextI18n")
//    void showProducts() {
//
//        ImmutableList<QueryProductDetailsParams.Product> productList = ImmutableList.of(
//                //Product 1
//                QueryProductDetailsParams.Product.newBuilder()
//                        .setProductId(productKey)
//                        .setProductType(BillingClient.ProductType.INAPP)
//                        .build()
//
//        );
//
//        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
//                .setProductList(productList)
//                .build();
//
//
//        billingClient.queryProductDetailsAsync(
//                params,
//                (billingResult, prodDetailsList) -> {
//                    // Process the result
//
//                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//
//
//                        for (ProductDetails skuDetails : prodDetailsList) {
//                            if (skuDetails.getProductId().equals(productKey)) {
//
//                                removeAds.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//
//                                        launchPurchaseFlow(skuDetails);
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                    }
//
//
//                }
//        );
//
//    }
//
//
//    void launchPurchaseFlow(ProductDetails productDetails) {
//
//        assert productDetails.getOneTimePurchaseOfferDetails() != null;
//        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
//                ImmutableList.of(
//                        BillingFlowParams.ProductDetailsParams.newBuilder()
//                                .setProductDetails(productDetails)
//                                .build()
//                );
//        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                .setProductDetailsParamsList(productDetailsParamsList)
//                .build();
//
//        BillingResult billingResult = billingClient.launchBillingFlow(RemoveAdsActivity.this, billingFlowParams);
//
//    }
//
//
//    void verifyPayment(Purchase purchase) {
//
//
//        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
//            if (!purchase.isAcknowledged()) {
//                AcknowledgePurchaseParams acknowledgePurchaseParams =
//                        AcknowledgePurchaseParams.newBuilder()
//                                .setPurchaseToken(purchase.getPurchaseToken())
//                                .build();
//                billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
//
//                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                        // 1 - True
//                        // 0 - False
////                        Log.d(inAppCheck,"ADs Remove");
//                        if (firstRun) {
////                            AppPreference.getInstance(RemoveAdsActivity.this).setBoolean(PrefKey.ActivityFirstRun, false);
//                            AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
//                        }
//                        AppPreference.getInstance(RemoveAdsActivity.this).setBoolean(PrefKey.RemoveAdsPrefs, true);
//                        ActivityUtils.getInstance().restartActivity(RemoveAdsActivity.this, EntryActivity.class, true);
//                    }
//
//                });
//            }
//        }
//
//
//    }
//
//
//
//
//    void checkSubscription() {
//
//
//        final BillingClient finalBillingClient = billingClient;
//
//
//        finalBillingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingServiceDisconnected() {
//                checkSubscription();
//            }
//
//            @Override
//            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
//
//
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    finalBillingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, (billingResult1, list) -> {
//
//                        if (list.size() == 0) {
//
//
//                        } else {
//                            for (Purchase purchase : list) {
//                                if (purchase.getSkus().get(0).equals(productKey)) {
//
//                                    if (firstRun) {
////                                        AppPreference.getInstance(RemoveAdsActivity.this).setBoolean(PrefKey.ActivityFirstRun, false);
//                                        AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
//                                    }
//
//                                    AppPreference.getInstance(RemoveAdsActivity.this).setBoolean(PrefKey.RemoveAdsPrefs, true);
//                                    ActivityUtils.getInstance().restartActivity(RemoveAdsActivity.this, EntryActivity.class, true);
//
//                                }
//                            }
//                        }
//                    });
//                }
//
//
//            }
//        });
//
//
//    }
//
//
//}