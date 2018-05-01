package com.github.kasra_sh.ESPUtils;

public class Validators {

    public static final int VALID = 0;
    public static final int INVALID_NOCHARS = 1;
    public static final int INVALID_NONUMS = 2;
    public static final int INVALID_NOUPPERCASE = 3;
    public static final int INVALID_NOSYMBOL = 4;
    public static final int INVALID_MINLENGTH = 5;
    public static final int INVALID_MAXLENGTH = 6;
    public static final int INVALID_MINNUMS = 7;
    public static final int INVALID_MINCHARS = 8;


    public static boolean validateFilePath(String path) {
        if (path.contains("../")) {
            return false;
        }
        return true;
    }


    public static int validatePassword(
            String password,
            boolean haveChars,
            boolean haveNumbers,
            boolean haveUppercase,
            boolean haveSymbol,
            int minLength,
            int maxLength,
            int minNumbers,
            int minChars
    ) {
        EString utils = new EString(password);

        if (haveChars) {
            if (utils.countAlpha() <= 0) return 1;
        }

        if (haveNumbers) {
            if (utils.countNums() <= 0) return 2;
        }

        if (haveUppercase) {
            if (utils.countUpper() <= 0) return 3;
        }

        if (haveSymbol) {

        }

        if (minLength > 0) {
            if (password.length() < minLength) return 5;
        }

        if (maxLength > 0) {
            if (password.length() > maxLength) return 6;
        }

        if (minNumbers > 0) {
            if (utils.countNums() < minNumbers) return 7;
        }

        if (minChars > 0) {
            if (utils.countAlpha() < minChars) return 8;
        }
        return 0;
    }

}
