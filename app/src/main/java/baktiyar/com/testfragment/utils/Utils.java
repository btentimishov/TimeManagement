package baktiyar.com.testfragment.utils;

import android.app.Activity;
import android.widget.Toast;

import java.util.Objects;


/**
 * Created by admin on 23.04.2018.
 */

public class Utils {

    public static void makeToast(String message, Activity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static Boolean stringContainsNothing(String text) {
        if (Objects.equals(text, "")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean stringIsNull(String text) {
        if (text == null) {
            return true;
        } else {
            return false;
        }
    }
}
