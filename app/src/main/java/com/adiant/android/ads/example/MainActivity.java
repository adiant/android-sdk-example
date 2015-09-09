package com.adiant.android.ads.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adiant.android.ads.util.AdListener;
import com.adiant.android.ads.AdView;
import com.adiant.android.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ADBLADE_TEST";
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load banner ad
        final AdView adView = (AdView) findViewById(R.id.adViewBannerAd);
        adView.loadAd();

        // create interstial ad
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("13323-2709803565");
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                Log.i(LOG_TAG, "Ad clicked.");
            }

            @Override
            public void onAdLoaded(Object ad) {
                Toast.makeText(MainActivity.this, "Ad loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "Ad opened.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Ad closed.", Toast.LENGTH_SHORT).show();
            }
        });

        // load our first ad
        interstitial.loadAd();

        // button listener to show ad
        final Button buttonView = (Button) findViewById(R.id.buttonShowAd);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });

        // button listener to load a subsequent ads
        final Button buttonLoad = (Button) findViewById(R.id.buttonLoadAd);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitial.loadAd();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_native) {
            Intent intent = new Intent(this, ItemListActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (interstitial != null) {
            interstitial.destroy();
        }
        super.onDestroy();
    }
}
