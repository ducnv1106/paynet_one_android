package com.core.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

import com.core.base.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Device Utils
 * Created by neo on 2/16/2016.
 */
public class DeviceUtils {

  public static ContentResolver contentResolver;
  private DeviceUtils() {

  }



  public static Point getDeviceSize(Activity context) {
    Display display = context.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  public static Point getDeviceSizePortrait(Activity context) {
    Display display = context.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);

    int x = Math.min(size.x, size.y);
    int y = Math.max(size.x, size.y);
    return new Point(x, y);
  }

  public static int getDpi(Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return (int) (metrics.density * 160f);
  }

  public static boolean isLandscape(Activity activity) {
    return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
  }

  public static boolean isActivityAutoRotate(Activity activity) {
    return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_UNDEFINED;
  }

  /**
   * Force set the orientation of activity
   *
   * @param activity    target activity
   * @param orientation 1 of those values
   *                    Configuration.ORIENTATION_LANDSCAPE
   *                    or Configuration.ORIENTATION_PORTRAIT
   *                    or Configuration.ORIENTATION_UNDEFINED
   */
  public static void forceRotateScreen(Activity activity, int orientation) {
    switch (orientation) {
      case Configuration.ORIENTATION_LANDSCAPE:
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        break;
      case Configuration.ORIENTATION_PORTRAIT:
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        break;
      case Configuration.ORIENTATION_UNDEFINED:
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        break;
      default:
        break;
    }
  }

  public static boolean isDeviceLockRotate(Context context) {
    final int rotationState = Settings.System.getInt(
        context.getContentResolver(),
        Settings.System.ACCELEROMETER_ROTATION, 0
    );

    return rotationState == 0;
  }



  public static void openAppInStore(Context context) {
    final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
    try {
      context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      Logger.e("Error", "ActivityNotFoundException", anfe);
      context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
  }

  // A method to find height of the status bar
  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static int getActionBarHeight(Context context) {
    // Calculate ActionBar height
    TypedValue tv = new TypedValue();
    if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
    }

    return 0;
  }

  public static int dpToPx(Context context, int dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int pxToDp(Context context, int px){
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static String getContactDisplayNameByNumber(Context context ,String number) {
    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
    String name = number;

//    ContentResolver contentResolver = context.getContentResolver();
    if (contentResolver == null){
      contentResolver = context.getContentResolver();
    }

    Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
        ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

    try {
      if (contactLookup != null && contactLookup.getCount() > 0) {
        contactLookup.moveToNext();
        name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
        //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
      }
    } finally {
      if (contactLookup != null) {
        contactLookup.close();
      }
    }

    return name;
  }

  public static boolean isNougat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
  }


  public static String uriToBase64(Activity viewContext, String frontImgIdNo) {
    if(viewContext == null) {
      return "";
    }
    else {
      InputStream imageStream = null;
      try {
        imageStream = viewContext.getContentResolver().openInputStream(Uri.parse(frontImgIdNo));
      } catch (FileNotFoundException e) {
        java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "an exception was thrown", e);
      }
      try {
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        for (int quality = 80; quality >= 10; quality -= 10) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          selectedImage.compress(Bitmap.CompressFormat.JPEG, quality, baos);
          final byte[] dataF = baos.toByteArray();
          if (dataF.length <= 500000) {
            return "data:image/jpeg;base64," + Base64.encodeToString(dataF, Base64.DEFAULT);
          }
        }

        return null;
      }catch (Exception ex)
      {
        Logger.w(ex);
        return "";
      }
    }
  }
  public static boolean isTablet(Context ctx){
    return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
  }

}
