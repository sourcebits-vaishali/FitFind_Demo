package com.sourcebits.fitfind;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.sourcebits.fitfind.adapter.ImageAdapter;
import com.sourcebits.fitfind.custom.ExpandableTextView;
import com.sourcebits.fitfind.model.TrainerDetails;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/**
 * Created by vaishaliarora on 24/05/16.
 */
public class TrainerDetailActivity extends Activity implements View.OnClickListener{

    private static final String ACTIVITY_NUMBER = "activity_num";
    private static final String LOG_TAG = "iabv3";

    // PRODUCT & SUBSCRIPTION IDS
    private static final String PRODUCT_ID = "com.anjlab.test.iab.s2.p5";
    private static final String SUBSCRIPTION_ID = "com.anjlab.test.iab.subs1";
    private static final String LICENSE_KEY = null; // PUT YOUR MERCHANT KEY HERE;
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID=null;

    private BillingProcessor bp;
    private boolean readyToPurchase = false;

    Button mVideo;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_inapp);

        /*TrainerDetails model = (TrainerDetails) getIntent().getSerializableExtra("Details");
        TextView trainerName = (TextView)findViewById(R.id.trainer_name);
        trainerName.setVisibility(View.VISIBLE);
        trainerName.setText(model.getName());

        TextView chat = (TextView)findViewById(R.id.chat);
        chat.setVisibility(View.VISIBLE);

        ExpandableTextView description = (ExpandableTextView)findViewById(R.id.description);
        description.setText(getString(R.string.random_text));

        ArrayList<Bitmap> items = new ArrayList<>();
        for (int i=0;i<10;i++) {
            items.add(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher));
        }

        ImageAdapter adapter = new ImageAdapter(this , items);
        TwoWayView lvTest = (TwoWayView)findViewById(R.id.lvItems);
        lvTest.setItemMargin(10);
        lvTest.setAdapter(adapter);

        mVideo = (Button)findViewById(R.id.video);
        Drawable icon = ContextCompat.getDrawable(this, R.drawable.rectangle).mutate();
        icon.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(this, R.color.violet), PorterDuff.Mode.MULTIPLY));
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mVideo.setBackgroundDrawable(icon);
        } else {
            mVideo.setBackground(icon);
        }*/

        if(!BillingProcessor.isIabServiceAvailable(this)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }

        bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                showToast("onProductPurchased: " + productId);
                updateTextViews();
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
                showToast("onBillingError: " + Integer.toString(errorCode));
            }
            @Override
            public void onBillingInitialized() {
                showToast("onBillingInitialized");
                readyToPurchase = true;
                updateTextViews();
            }
            @Override
            public void onPurchaseHistoryRestored() {
                showToast("onPurchaseHistoryRestored");
                for(String sku : bp.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                for(String sku : bp.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku);
                updateTextViews();
            }
        });

//        chat.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateTextViews();
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateTextViews() {
        TextView text = (TextView)findViewById(R.id.productIdTextView);
        text.setText(String.format("%s is%s purchased", PRODUCT_ID, bp.isPurchased(PRODUCT_ID) ? "" : " not"));
        text = (TextView)findViewById(R.id.subscriptionIdTextView);
        text.setText(String.format("%s is%s subscribed", SUBSCRIPTION_ID, bp.isSubscribed(SUBSCRIPTION_ID) ? "" : " not"));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.chat:
                break;
            case R.id.purchaseButton:
                bp.purchase(this,PRODUCT_ID);
                break;
            case R.id.consumeButton:
                Boolean consumed = bp.consumePurchase(PRODUCT_ID);
                updateTextViews();
                if (consumed)
                    showToast("Successfully consumed");
                break;
            case R.id.productDetailsButton:
                SkuDetails sku = bp.getPurchaseListingDetails(PRODUCT_ID);
                showToast(sku != null ? sku.toString() : "Failed to load SKU details");
                break;
            case R.id.subscribeButton:
                bp.subscribe(this,SUBSCRIPTION_ID);
                break;
            case R.id.updateSubscriptionsButton:
                if (bp.loadOwnedPurchasesFromGoogle()) {
                    showToast("Subscriptions updated.");
                    updateTextViews();
                }
                break;
            case R.id.subsDetailsButton:
                SkuDetails subs = bp.getSubscriptionListingDetails(SUBSCRIPTION_ID);
                showToast(subs != null ? subs.toString() : "Failed to load subscription details");
                break;
            case R.id.launchMoreButton:
                startActivity(new Intent(this, MainActivity.class).putExtra(ACTIVITY_NUMBER, getIntent().getIntExtra(ACTIVITY_NUMBER, 1) + 1));
                break;
            default:
                break;
        }
    }
}
