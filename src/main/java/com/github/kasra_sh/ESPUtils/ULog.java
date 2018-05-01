package com.github.kasra_sh.ESPUtils;

import java.util.Calendar;

public class ULog {

    private static boolean logThr = true;

    private static Calendar calendar = Calendar.getInstance();

    private static OnLogListener logListener = new OnLogListener() {

        @Override
        public void onLogError(String tag, String text) {
            System.out.println(getTime() + " - (E) - " + tag + ": " + text);
        }

        @Override
        public void onLogInfo(String tag, String text) {
            System.out.println(getTime() + " - (I) - " + tag + ": " + text);
        }

        @Override
        public void onLogWarning(String tag, String text) {
            System.out.println(getTime() + " - (W) - " + tag + ": " + text);
        }

        @Override
        public void onLogDebug(String tag, String text) {
            System.out.println(getTime() + " - (D) - " + tag + ": " + text);
        }

        @Override
        public void onLogException(String tag, String text, Throwable throwable) {
            System.out.println(getTime() + " - (T) - " + tag + ": " + text);
            throwable.printStackTrace();
        }
    };

    public static class LogFlags {
        private static boolean logError = true;
        private static boolean logInfo = true;
        private static boolean logWarning = true;
        private static boolean logDebug = true;
        private static boolean logException = true;

        public static void setFlags(boolean info,
                                    boolean warning,
                                    boolean error,
                                    boolean exception,
                                    boolean debug)
        {
            logError = error;
            logDebug = debug;
            logInfo = info;
            logWarning = warning;
            logException = exception;
        }

        public static boolean getLogError() {
            return LogFlags.logError;
        }
        public static boolean getLogInfo() {
            return LogFlags.logInfo;
        }
        public static boolean getLogWarning() {
            return LogFlags.logWarning;
        }
        public static boolean getLogDebug() {
            return LogFlags.logDebug;
        }
        public static boolean getLogException() {
            return LogFlags.logException;
        }


        public static void setLogError(boolean logError) {
            LogFlags.logError = logError;
        }

        public static void setLogInfo(boolean logInfo) {
            LogFlags.logInfo = logInfo;
        }

        public static void setLogWarning(boolean logWarning) {
            LogFlags.logWarning = logWarning;
        }

        public static void setLogDebug(boolean logDebug) {
            LogFlags.logDebug = logDebug;
        }

        public static void setLogException(boolean logException) {
            LogFlags.logException = logException;
        }
    }

    public static synchronized void setLogExceptions(boolean logExceptions) {
        logThr = logExceptions;
    }

    public static synchronized void setOnLogListener(OnLogListener listener) {
        logListener = listener;
    }

    /**
     * Log Debug
     *
     * @param tag
     * @param text
     */
    public static void d(String tag, String text) {
        if (LogFlags.logDebug) {
            logListener.onLogDebug(tag, text);
        }
    }

    /**
     * Log Error
     *
     * @param tag
     * @param text
     */
    public static void e(String tag, String text) {
        if (LogFlags.logError)
            logListener.onLogError(tag, text);
    }

    /**
     * Log Info
     *
     * @param tag
     * @param text
     */
    public static void i(String tag, String text) {
        if (LogFlags.logInfo)
            logListener.onLogInfo(tag, text);
    }

    /**
     * Log Warning
     *
     * @param tag
     * @param text
     */
    public static void w(String tag, String text) {
        if (LogFlags.logWarning)
            logListener.onLogWarning(tag, text);
    }

    /**
     * @param tag
     * @param text
     * @param throwable
     */
    public static void thr(String tag, String text, Throwable throwable) {
        if (LogFlags.logException)
            logListener.onLogException(tag, text, throwable);
    }

    /**
     * @param tag
     * @param throwable
     */
    public static void wtf(String tag, Throwable throwable) {
        thr(tag, "", throwable);
    }

    private static String getTime() {
        return calendar.getTime().toString();
    }

    public interface OnLogListener {
        void onLogError(String tag, String text);

        void onLogInfo(String tag, String text);

        void onLogWarning(String tag, String text);

        void onLogDebug(String tag, String text);

        void onLogException(String tag, String text, Throwable throwable);
    }
}
