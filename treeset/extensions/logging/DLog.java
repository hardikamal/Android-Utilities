package treeset.extensions.logging;

import android.util.Log;


/**
 * Logger that should be used primary for debug, since it's displaying lot of useful (potentially harmful) info.
 * No external exception logging provided.
 *
 * Created by daemontus on 11/04/14.
 */
public class DLog {

    public static String tagPrefix = "";

    public static void error(String message) {
        error(null, message);
    }

    public static void error(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
        Log.e(tagPrefix + (tag == null ? className : tag)  + "." + methodName+"("+lineNumber+")", message);
    }

    public static void warning(String message) {
        warning(null, message);
    }

    public static void warning(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
        Log.w(tagPrefix + (tag == null ? className : tag) + "." + methodName+"("+lineNumber+")", message);
    }

    public static void info(String message) {
        info(null, message);
    }

    public static void info(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
        Log.d(tagPrefix + (tag == null ? className : tag) + "." + methodName+"("+lineNumber+")", message);
    }

    public static void exception(Exception e) {
        e.printStackTrace();
    }
}

