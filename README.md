# Adiant Android Ads SDK

The Android Ads SDK exists to allow Android developers to integrate Adblade ads into their apps easily. It is compatible with Android 2.3.3 and newer. Currently, the SDK supports 3 types of ads: banner, interstitial, and native. 

This is an example Android app that uses the Adiant Ads SDK to display ads.

## Get the SDK

Currently, the SDK's binaries are hosted on Adiant's Maven repository.

### Gradle

Add Adiant's Maven repository to your project:

```groovy
repositories {
    maven {
        url 'http://ftp.adiant.com/artifactory/libs-release'
    }
}
```

And add the SDK's **aar** to your project's dependencies

```groovy
dependencies {
    compile 'com.adiant.android.ads:ads:1.0.0@aar'
}
```

### Maven

Add Adiant's Maven repository to your project:

```xml
<repositories>
    <repository>
        <id>adiant-repo</id>
        <url>http://ftp.adiant.com/artifactory/libs-release</url>
    </repository>
</repositories>
```

And add the SDK's **aar** to your project's dependencies

```xml
<dependencies>
    <dependency>
        <groupId>com.adiant.android.ads</groupId>
        <artifactId>ads</artifactId>
        <version>1.0.0</version>
        <type>aar</type>
    </dependency>
</dependencies>
```

## Integration

This information is also available on (our wiki)[https://github.com/adiant/android-sdk-example/wiki].

For all types of ads, an app must require the INTERNET and ACCESS_NETWORK_STATE permissions in its manifest:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.android.app">
<!-- ... -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- ... -->
</manifest>
```

### Banner ads

Add an **AdView** element to your layout XML. Add the **adiant** namespace as well.

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:adiant="http://schemas.android.com/apk/res-auto"
<!-- ... -->
    <com.adiant.android.ads.AdView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/adViewBannerAd"
        adiant:adunitid="7730-1157985988"
        adiant:adtype="BANNER_468_60" />
<!-- ... -->
```

Get a reference to the AdView element, give it your ad unit ID, and load an ad:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // load banner ad
    final AdView adView = (AdView) findViewById(R.id.adViewBannerAd);
    adView.loadAd();

    // rest of your code ...
}
```

#### Banner ad types

Type | Width | Height
---- | ----- | ------
BANNER_300_250 | 300 px | 250 px
BANNER_728_90 | 728 px | 90 px
BANNER_320_50 | 320 px | 50 px
BANNER_320_100 | 320 px | 100 px
BANNER_468_60 | 468 px | 60 px 

### Interstitial ads

Add the **AdActivity** to your AndroidManifest.xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.android.app" >
<!-- ... -->
       <activity android:name="com.adiant.android.ads.AdActivity"
           android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
           android:theme="@android:style/Theme.Translucent" />
<!-- ... -->
</manifest>
```

In your activity, instantiate the **InterstitialAd** class. Load an ad in the background, and show the add when the user proceeds forward in your app.

```java
private InterstitialAd interstitial;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // create interstial ad
    interstitial = new InterstitialAd(this);
    interstitial.setAdUnitId("7730-1157985988");
    interstitial.setAdListener(new AdListener() {
        @Override
        public void onAdClosed() {
            doTheNextThing();
        }
    });

    // load our first ad in the background
    interstitial.loadAd();

    // button listener to show ad
    final Button nextButton = (Button) findViewById(R.id.buttonNext);
    nextButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (interstitial.isLoaded()) {
                interstitial.show();
            } else {
                // handle case where as has not loaded
                doTheNextThing();
            }
        }
    });

    // rest of your code ...
}
```

Add a hook to clean-up resources in the the activity's onDestroy() method.

```java
@Override
protected void onDestroy() {
    if (interstitial != null) {
        interstitial.destroy();
    }
    super.onDestroy();
}
```

### Native ads

The Ads SDK supports displaying native ads with your custom layout in a ListView or GridView.

Create a layout for native ads, e.g. list_item_native_ad.xml:

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="wrap_content"
    android:paddingLeft="@dimen/list_item_horizontal_margin"
    android:paddingRight="@dimen/list_item_horizontal_margin"
    android:paddingTop="@dimen/list_item_vertical_margin"
    android:paddingBottom="@dimen/list_item_vertical_margin">

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingRight="@dimen/list_item_horizontal_margin"
        android:id="@+id/imageView"
        android:layout_centerVertical="true"
        android:contentDescription="@string/ads_description" />

    <TextView
        android:text="@string/interstitial_intro"
        android:textAppearance="?android:attr/textAppearanceMedium"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@+id/imageView"
        android:id="@+id/textViewTitle" />

    <TextView
        android:text="@string/interstitial_intro"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/textViewTitle"
        android:layout_toRightOf="@+id/imageView"
        android:id="@+id/textViewDescription" />
</RelativeLayout>
```

1. Create a **NativeAdFactory** instance as early as possible, and preload ads to show in the list.
2. Wrap your list adapter within a **NativeAdAdapter**.
3. Create a **NativeAdInflater** that will create the view for each native ad.

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // create ad factory
    NativeAdFactory adFactory = new NativeAdFactory(getActivity(), "7730-1157985988", new NativeAdArbiteur());

    // estimate total items shown
    adFactory.init(10);

    ListAdapter adapter;
    // setup your adapter ...

    // create a view binder that sets the resource IDs for each 
    ViewBinder binder = new ViewBinder.Builder()
                .setImageId(R.id.imageView)
                .setTitleId(R.id.textViewTitle)
                .setDescriptionId(R.id.textViewDescription)
                .setDisplayNameId(R.id.textViewDisplayName)
                .create();

    // create ad inflater with the resource ID for your native ad layout
    NativeAdInflater adInflater = new NativeAdInflater(getActivity(), binder, R.layout.list_item_native_ad);

    // wrap adapter inside adapter that inserts native ads
    adapterWithAds = new NativeAdAdapter(adapter, adInflater, adFactory);

    // use the adapter
    setListAdapter(adapterWithAds);
}
```

Add a hook to clean-up resources in the the fragment's onDestroy() method. This is necessary only for Android 2.3.3 (API level 10) and lower.

```java
@Override
public void onDestroy() {
    if (adapterWithAds != null) {
        adapterWithAds.destroy();
    }
    super.onDestroy();
}
```

## Issues

If you encounter issues with the SDK or with this example app, please open an issue on GitHub:

https://github.com/adiant/android-sdk-example/issues
