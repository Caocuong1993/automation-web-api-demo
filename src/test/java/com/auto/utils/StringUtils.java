package com.auto.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isBlankOrEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (!(value instanceof String)) {
            if (value instanceof List) {
                List<?> lst = (List) value;
                return lst.isEmpty();
            } else if (value instanceof Map) {
                Map<?, ?> map = (Map) value;
                return map.isEmpty();
            } else {
                return false;
            }
        } else {
            return value.toString().trim().isEmpty() || value.toString().trim().equals("<<null>>");
        }
    }

    public static String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

    public static String numberFormat(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    public static String changeCurrencyFormatVND(String balance) {
        double balanceDouble = Double.parseDouble(balance);
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(balanceDouble);
    }

    public static ArrayList<String> removeSpaceFromArrayList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, ((String) list.get(i)).replaceAll(" ", ""));
        }

        return list;
    }

    public static ArrayList<String> formatCurrencyFromArrayList(ArrayList<String> list, String format) {
        DecimalFormat df = new DecimalFormat(format);

        for (int i = 0; i < list.size(); ++i) {
            double balanceDouble = Double.parseDouble((String) list.get(i));
            list.set(i, df.format(balanceDouble));
        }

        return list;
    }

    public static String generateRandomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateRandomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String generateRandomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generatePhoneNumber() {
        return "03" + generateRandomNumeric(8);
    }

    public static String generateEmailAddress() {
        return generateRandomAlphanumeric(10) + "@gmail.com";
    }

    public static boolean checkValueMatchRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
