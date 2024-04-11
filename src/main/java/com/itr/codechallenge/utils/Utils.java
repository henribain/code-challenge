package com.itr.codechallenge.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Boolean passwordValidation(String password) {
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(password).matches();
    }

    public static Boolean emailValidation(String email) {
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email);
    }

}
