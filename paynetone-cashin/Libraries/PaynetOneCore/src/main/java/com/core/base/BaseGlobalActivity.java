package com.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.core.delegate.OnPickContact;
import com.core.utils.ActivityUtils;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

/**
 * Base activity
 * Created by neo on 2/5/2016.
 */
public class BaseGlobalActivity extends AppCompatActivity {
  private static int PICK_CONTACT = 10;
  protected OnPickContact pickContactDelegate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == PICK_CONTACT && data != null) {
      Uri contactData = data.getData();
      final Cursor c = getContentResolver().query(
              ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
              new String[]{contactData.getLastPathSegment()}, null);
      if(c!=null) {
        int phoneIdx = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int phoneType = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
        if (c.getCount() > 1) { // contact has multiple phone numbers
          final CharSequence[] numbers = new CharSequence[c.getCount()];
          int i = 0;
          if (c.moveToFirst()) {
            final String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            while (!c.isAfterLast()) { // for each phone number, add it to the numbers array
              String type = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(this.getResources(), c.getInt(phoneType), ""); // insert a type string in front of the number
              String number = type + ": " + c.getString(phoneIdx);
              numbers[i++] = number;
              c.moveToNext();
            }

            // build and show a simple dialog that allows the user to select a number
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chọn số điện thoại");
            builder.setItems(numbers, new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int item) {
                String number = (String) numbers[item];
                int index = number.indexOf(':');
                number = number.substring(index + 2);
                if (pickContactDelegate != null)
                  pickContactDelegate.onPickContact(number, name);
              }
            });
            AlertDialog alert = builder.create();
            alert.setOwnerActivity(this);
            alert.show();

          }
        } else if (c.getCount() == 1 && c.moveToFirst()) {
          String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
          String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
          if (!TextUtils.isEmpty(hasPhone) && "1".equalsIgnoreCase(hasPhone)) {
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);
            if (phones != null) {
              phones.moveToFirst();
            }

            String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            String phoneNumber = c.getString(c.getColumnIndexOrThrow("data1"));
            phoneNumber = phoneNumber.replaceAll("-", "");
            phoneNumber = phoneNumber.replaceAll(" ", "");
            phoneNumber = phoneNumber.replaceAll(Pattern.quote("+84"), "84");
            if (pickContactDelegate != null)
              pickContactDelegate.onPickContact(phoneNumber, name);
          }
        }
      }
    }
  }
}
