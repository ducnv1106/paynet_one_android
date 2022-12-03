package com.paynetone.counter.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    public static String convertVietNamPhoneNumber(String phoneNumber) {
        String mPhoneNumber;
        if (phoneNumber.length() < 6) {
            return phoneNumber;
        }

        if (!StringUtils.isNumeric(phoneNumber)) {
            return phoneNumber;
        }

        if ("84".equals(phoneNumber.trim().substring(0, 2))) {
            mPhoneNumber = "0" + phoneNumber.trim().substring(2);
        } else if ("+84".equals(phoneNumber.trim().substring(0, 3))) {
            mPhoneNumber = "0" + phoneNumber.trim().substring(3);
        } else if (!"0".equals(phoneNumber.trim().substring(0, 1))) {
            mPhoneNumber = "0" + phoneNumber.trim();
        } else {
            mPhoneNumber = phoneNumber;
        }
        return mPhoneNumber;
    }

    public static String formatPriceNumber(long price) {
        NumberFormat nf = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ROOT));
        return nf.format(price);
    }
}
